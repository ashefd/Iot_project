#include <Arduino.h>
#include <Wire.h>
#include <SPI.h>
#include <SparkFun_VEML7700_Arduino_Library.h>
#include "SparkFun_SCD4x_Arduino_Library.h" //Click here to get the library: http://librarymanager/All#SparkFun_SCD4x
#include "format.pb.h"
#include "pb_encode.h"
#include <lmic.h>
#include <hal/hal.h>

#define COMMAND_LED_OFF     0x00
#define COMMAND_LED_ON      0x01
#define COMMAND_GET_VALUE   0x05
#define COMMAND_NOTHING_NEW 0x99

const byte qwiicAddress = 0x38;     //Default Address
uint16_t ADC_VALUE=0;

#define PIN_GATE_IN 2
#define IRQ_GATE_IN  0
#define PIN_LED_OUT 13
#define PIN_ANALOG_IN A0

#define Console SerialUSB

const lmic_pinmap lmic_pins = {
    .nss = 12,//RFM Chip Select
    .rxtx = LMIC_UNUSED_PIN,
    .rst = 7,//RFM Reset
    .dio = {6, 10, 11}, //RFM Interrupt, RFM LoRa pin, RFM LoRa pin
};

// Identifiants TTN
static const u1_t PROGMEM APPEUI[8] = { 0x02, 0x06, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
void os_getArtEui (u1_t* buf) { memcpy_P(buf, APPEUI, 8);}

static const u1_t PROGMEM DEVEUI[8] = { 0xE9, 0xCB, 0x04, 0xD0, 0x7E, 0xD5, 0xB3, 0x70 };
void os_getDevEui (u1_t* buf) { memcpy_P(buf, DEVEUI, 8);}

static const u1_t PROGMEM APPKEY[16] = { 
  0xC2, 0x86, 0xAC, 0x92, 0xC0, 0xD5, 0x41, 0xE4, 0x39, 0x47, 0x91, 0xF4, 0xDB, 0xB6, 0xCF, 0x10  
};
void os_getDevKey (u1_t* buf) {  memcpy_P(buf, APPKEY, 16);}


SCD4x mySensor;

void get_value() {
  Wire.beginTransmission(qwiicAddress);
  Wire.write(COMMAND_GET_VALUE); // command for status
  Wire.endTransmission();    // stop transmitting //this looks like it was essential.

  Wire.requestFrom(qwiicAddress, 2);    // request 1 bytes from slave device qwiicAddress

  while (Wire.available()) { // slave may send less than requested
  uint8_t ADC_VALUE_L = Wire.read(); 
//  SerialUSBprintln("ADC_VALUE_L:  ");
//  SerialUSBprintln(ADC_VALUE_L,DEC);
  uint8_t ADC_VALUE_H = Wire.read();
//  SerialUSBprintln("ADC_VALUE_H:  ");
//  SerialUSBprintln(ADC_VALUE_H,DEC);
  uint8_t ADC_VALUE=ADC_VALUE_H;
  ADC_VALUE<<=8;
  ADC_VALUE|=ADC_VALUE_L;
  SerialUSB.println("ADC_VALUE:  ");
  SerialUSB.println(ADC_VALUE,DEC);
  }
  uint16_t x=Wire.read(); 
}

void ledOn() {
  Wire.beginTransmission(qwiicAddress);
  Wire.write(COMMAND_LED_ON);
  Wire.endTransmission();
}

void ledOff() {
  Wire.beginTransmission(qwiicAddress);
  Wire.write(COMMAND_LED_OFF);
  Wire.endTransmission();
}

template <uint16_t PIN=PIN_ANALOG_IN>
uint16_t getVoltage() {
    return (uint16_t)roundf((3300.0f / 1023.0f) * (4.7f + 10.0f) / 10.0f * (float)analogRead(PIN));
}

// testForConnectivity() checks for an ACK from an Sensor. If no ACK
// program freezes and notifies user.
void testForConnectivity() {
  Wire.beginTransmission(qwiicAddress);
  //check here for an ACK from the slave, if no ACK don't allow change?
  if (Wire.endTransmission() != 0) {
    SerialUSB.println("Check connections. No slave attached.");
  }
}

// Méthodes envois TTN 
static osjob_t sendjob;

const unsigned TX_INTERVAL = 30;

void printHex2(unsigned v) {
    v &= 0xff;
    if (v < 16)
        Console.print('0');
    Console.print(v, HEX);
}

void do_send(osjob_t* j);

void onEvent (ev_t ev) {
    Console.print(os_getTime());
    Console.print(": ");
    switch(ev) {
        case EV_SCAN_TIMEOUT:
            Console.println(F("EV_SCAN_TIMEOUT"));
            break;
        case EV_BEACON_FOUND:
            Console.println(F("EV_BEACON_FOUND"));
            break;
        case EV_BEACON_MISSED:
            Console.println(F("EV_BEACON_MISSED"));
            break;
        case EV_BEACON_TRACKED:
            Console.println(F("EV_BEACON_TRACKED"));
            break;
        case EV_JOINING:
            Console.println(F("EV_JOINING"));
            break;
        case EV_JOINED:
            Console.println(F("EV_JOINED"));
            {
              u4_t netid = 0;
              devaddr_t devaddr = 0;
              u1_t nwkKey[16];
              u1_t artKey[16];
              LMIC_getSessionKeys(&netid, &devaddr, nwkKey, artKey);
              Console.print("netid: ");
              Console.println(netid, DEC);
              Console.print("devaddr: ");
              Console.println(devaddr, HEX);
              Console.print("AppSKey: ");
              for (size_t i=0; i<sizeof(artKey); ++i) {
                if (i != 0)
                  Console.print("-");
                printHex2(artKey[i]);
              }
              Console.println("");
              Console.print("NwkSKey: ");
              for (size_t i=0; i<sizeof(nwkKey); ++i) {
                      if (i != 0)
                              Console.print("-");
                      printHex2(nwkKey[i]);
              }
              Console.println();
            }
            os_setTimedCallback(&sendjob, os_getTime()+sec2osticks(TX_INTERVAL), do_send);
            // Disable link check validation (automatically enabled
            // during join, but because slow data rates change max TX
	        // size, we don't use it in this example.
            LMIC_setLinkCheckMode(0);
            break;
        case EV_RFU1:
             Console.println(F("EV_RFU1"));
             break;
        case EV_JOIN_FAILED:
            Console.println(F("EV_JOIN_FAILED"));
            break;
        case EV_REJOIN_FAILED:
            Console.println(F("EV_REJOIN_FAILED"));
            break;
        case EV_TXCOMPLETE:
            Console.println(F("EV_TXCOMPLETE (includes waiting for RX windows)"));
            if (LMIC.txrxFlags & TXRX_ACK)
              Console.println(F("Received ack"));
            if (LMIC.dataLen) {
              Console.print(F("Received "));
              Console.print(LMIC.dataLen);
              Console.println(F(" bytes of payload"));
            }
            // Schedule next transmission
            os_setTimedCallback(&sendjob, os_getTime()+sec2osticks(TX_INTERVAL), do_send);
            break;
        case EV_LOST_TSYNC:
            Console.println(F("EV_LOST_TSYNC"));
            break;
        case EV_RESET:
            Console.println(F("EV_RESET"));
            break;
        case EV_RXCOMPLETE:
            // data received in ping slot
            Console.println(F("EV_RXCOMPLETE"));
            break;
        case EV_LINK_DEAD:
            Console.println(F("EV_LINK_DEAD"));
            break;
        case EV_LINK_ALIVE:
            Console.println(F("EV_LINK_ALIVE"));
            break;
        case EV_SCAN_FOUND:
            Console.println(F("EV_SCAN_FOUND"));
            break;
        case EV_TXSTART:
            Console.println(F("EV_TXSTART"));
            break;
        case EV_TXCANCELED:
            Console.println(F("EV_TXCANCELED"));
            break;
        case EV_RXSTART:
            break;
        case EV_JOIN_TXCOMPLETE:
            Console.println(F("EV_JOIN_TXCOMPLETE: no JoinAccept"));
            break;
        default:
            Console.print(F("Unknown event: "));
            Console.println((unsigned) ev);
            break;
    }
}

void do_send(osjob_t* j){
    // Check if there is not a current TX/RX job running
    if (LMIC.opmode & OP_TXRXPEND) {
        Console.println(F("OP_TXRXPEND, not sending"));
    } else {
      // Get measures
      get_value();
      ledOn();
      ledOff();
      
      if (mySensor.readMeasurement()) // readMeasurement will return true when fresh data is available
      {
        SerialUSB.println();

        SerialUSB.println(F("CO2(ppm):"));
        SerialUSB.println(mySensor.getCO2());

        SerialUSB.println(F("\tTemperature(C):"));
        SerialUSB.println(mySensor.getTemperature(), 1);

        SerialUSB.println(F("\tHumidity(%RH):"));
        SerialUSB.println(mySensor.getHumidity(), 1);

        SerialUSB.println();
      }
      else
        SerialUSB.println(F("."));


      // Send data
      unsigned char msg[myobject_Uplink_size];

      myobject_Uplink message;
      message.co2 = 2.f;
      message.temperature = 2.f;
      message.humidity = 2.f;
      message.battery = 5;
      message.timestamp = millis();

      pb_ostream_t stream = pb_ostream_from_buffer(msg, sizeof(msg));
      pb_encode(&stream, myobject_Uplink_fields, &message);

      // Prepare upstream data transmission at the next possible time.
      LMIC_setTxData2(1, msg, stream.bytes_written, 0);
      Console.println(F("Packet queued"));
    }
    // Next TX is scheduled after TX_COMPLETE event.
}


void setup()
{
  SerialUSB.begin(9600);
  while (!SerialUSB) delay(10);
  SerialUSB.println(F("SCD4x Example"));
  Wire.begin();

  getVoltage();

  //mySensor.enableDebugging(); // Uncomment this line to get helpful debug messages on SerialUSB

  //.begin will start periodic measurements for us (see the later examples for details on how to override this)

  // LMIC init
  os_init_ex(&lmic_pins);

  // Reset the MAC state. Session and pending data transfers will be discarded.
  LMIC_reset();
  LMIC_setAdrMode(1);
  LMIC_startJoining();

  if (mySensor.begin() == false)
  {
    SerialUSB.println(F("Sensor not detected"));
  } else{
    SerialUSB.println(F("Sensor detected"));
  }

  testForConnectivity();
  ledOn();
  //delay(1000);
}

void loop()
{
  os_runloop_once();
}
