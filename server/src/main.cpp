#include <Arduino.h>
#include <Wire.h>
#include <SPI.h>
#include <SparkFun_VEML7700_Arduino_Library.h>
#include "SparkFun_SCD4x_Arduino_Library.h" //Click here to get the library: http://librarymanager/All#SparkFun_SCD4x
#include "format.pb.h"
#include "pb_encode.h"
#include <lmic.h>
#include <hal/hal.h>
#include <RTCZero.h>


#define COMMAND_LED_OFF     0x00
#define COMMAND_LED_ON      0x01
#define COMMAND_GET_VALUE   0x05
#define COMMAND_NOTHING_NEW 0x99

int del = 1000;

const byte qwiicAddress = 0x38;     //Default Address
uint16_t ADC_VALUE=0;

#define PIN_GATE_IN 2
#define IRQ_GATE_IN  0
#define PIN_LED_OUT 13
#define PIN_ANALOG_IN A0

constexpr uint32_t ledPin = LED_BUILTIN;
volatile byte state = LOW;

SCD4x mySensor;
RTCZero rtc;



const lmic_pinmap lmic_pins = {
    .nss = 12,//RFM Chip Select
    .rxtx = LMIC_UNUSED_PIN,
    .rst = 7,//RFM Reset
    .dio = {6, 10, 11}, //RFM Interrupt, RFM LoRa pin, RFM LoRa pin
};


// This EUI must be in little-endian format, so least-significant-byte
// first. When copying an EUI from ttnctl output, this means to reverse
// the bytes. For TTN issued EUIs the last bytes should be 0xD5, 0xB3,
// 0x70.
static const u1_t PROGMEM APPEUI[8]={ 0xFA, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

void os_getArtEui (u1_t* buf) { memcpy_P(buf, APPEUI, 8);}

// This should also be in little endian format, see above.
// 70B3D57ED004CB51
static const u1_t PROGMEM DEVEUI[8]={ 0xEE, 0xCB, 0x04, 0xD0, 0x7E, 0xD5, 0xB3, 0x70 };

void os_getDevEui (u1_t* buf) { memcpy_P(buf, DEVEUI, 8);}

// This key should be in big endian format (or, since it is not really a
// number but a block of memory, endianness does not really apply). In
// practice, a key taken from ttnctl can be copied as-is.

// FFFFFFFFFFFFFFFFFFFFFFFFFFFFFF09
static const u1_t PROGMEM APPKEY[16] = { 
  0x8F, 0x82, 0xA0, 0xDF, 0xC1, 0x2F, 0xA8, 0x0B, 0xEC, 0x8C, 0xA5, 0x22, 0xEF, 0x47, 0x81, 0xAA  
};

void os_getDevKey (u1_t* buf) {  memcpy_P(buf, APPKEY, 16);}

static uint8_t mydata[] = "Salam les khouyas!";
static osjob_t sendjob;

// Schedule TX every this many seconds (might become longer due to duty
// cycle limitations).
const unsigned TX_INTERVAL = 60;

void printHex2(unsigned v) {
    v &= 0xff;
    if (v < 16)
        SerialUSB.print('0');
    SerialUSB.print(v, HEX);
}

void do_send(osjob_t* j);

void onEvent (ev_t ev) {
    SerialUSB.print(os_getTime());
    SerialUSB.print(": ");
    switch(ev) {
        case EV_SCAN_TIMEOUT:
            SerialUSB.println(F("EV_SCAN_TIMEOUT"));
            break;
        case EV_BEACON_FOUND:
            SerialUSB.println(F("EV_BEACON_FOUND"));
            break;
        case EV_BEACON_MISSED:
            SerialUSB.println(F("EV_BEACON_MISSED"));
            break;
        case EV_BEACON_TRACKED:
            SerialUSB.println(F("EV_BEACON_TRACKED"));
            break;
        case EV_JOINING:
            SerialUSB.println(F("EV_JOINING"));
            break;
        case EV_JOINED:
            SerialUSB.println(F("EV_JOINED"));
            {
              u4_t netid = 0;
              devaddr_t devaddr = 0;
              u1_t nwkKey[16];
              u1_t artKey[16];
              LMIC_getSessionKeys(&netid, &devaddr, nwkKey, artKey);
              SerialUSB.print("netid: ");
              SerialUSB.println(netid, DEC);
              SerialUSB.print("devaddr: ");
              SerialUSB.println(devaddr, HEX);
              SerialUSB.print("AppSKey: ");
              for (size_t i=0; i<sizeof(artKey); ++i) {
                if (i != 0)
                  SerialUSB.print("-");
                printHex2(artKey[i]);
              }
              SerialUSB.println("");
              SerialUSB.print("NwkSKey: ");
              for (size_t i=0; i<sizeof(nwkKey); ++i) {
                      if (i != 0)
                              SerialUSB.print("-");
                      printHex2(nwkKey[i]);
              }
              SerialUSB.println();
            }
            os_setTimedCallback(&sendjob, os_getTime()+sec2osticks(TX_INTERVAL), do_send);
            // Disable link check validation (automatically enabled
            // during join, but because slow data rates change max TX
	        // size, we don't use it in this example.
            LMIC_setLinkCheckMode(0);
            break;
        case EV_RFU1:
             SerialUSB.println(F("EV_RFU1"));
             break;
        case EV_JOIN_FAILED:
            SerialUSB.println(F("EV_JOIN_FAILED"));
            break;
        case EV_REJOIN_FAILED:
            SerialUSB.println(F("EV_REJOIN_FAILED"));
            break;
        case EV_TXCOMPLETE:
            SerialUSB.println(F("EV_TXCOMPLETE (includes waiting for RX windows)"));
            if (LMIC.txrxFlags & TXRX_ACK)
              SerialUSB.println(F("Received ack"));
            if (LMIC.dataLen) {
              SerialUSB.print(F("Received "));
              SerialUSB.print(LMIC.dataLen);
              SerialUSB.println(F(" bytes of payload"));
            }
            // Schedule next transmission
            os_setTimedCallback(&sendjob, os_getTime()+sec2osticks(TX_INTERVAL), do_send);
            break;
        case EV_LOST_TSYNC:
            SerialUSB.println(F("EV_LOST_TSYNC"));
            break;
        case EV_RESET:
            SerialUSB.println(F("EV_RESET"));
            break;
        case EV_RXCOMPLETE:
            // data received in ping slot
            SerialUSB.println(F("EV_RXCOMPLETE"));
            break;
        case EV_LINK_DEAD:
            SerialUSB.println(F("EV_LINK_DEAD"));
            break;
        case EV_LINK_ALIVE:
            SerialUSB.println(F("EV_LINK_ALIVE"));
            break;
        case EV_SCAN_FOUND:
            SerialUSB.println(F("EV_SCAN_FOUND"));
            break;
        case EV_TXSTART:
            SerialUSB.println(F("EV_TXSTART"));
            break;
        case EV_TXCANCELED:
            SerialUSB.println(F("EV_TXCANCELED"));
            break;
        case EV_RXSTART:
            break;
        case EV_JOIN_TXCOMPLETE:
            SerialUSB.println(F("EV_JOIN_TXCOMPLETE: no JoinAccept"));
            break;
        default:
            SerialUSB.print(F("Unknown event: "));
            SerialUSB.println((unsigned) ev);
            break;
    }
}

void do_send(osjob_t* j){
    // Check if there is not a current TX/RX job running
    if (LMIC.opmode & OP_TXRXPEND) {
        SerialUSB.println(F("OP_TXRXPEND, not sending"));
    } else {
              SerialUSB.println(F(mydata));
        // Prepare upstream data transmission at the next possible time.
        LMIC_setTxData2(1, mydata, sizeof(mydata)-1, 0);
        SerialUSB.println(F("Packet queued"));
    }
    // Next TX is scheduled after TX_COMPLETE event.
}

void changeDelay(bool bcp)
{
  if(bcp)
  {
    del = 1000;
  }
  else del = 10000;
}

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
/*
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
*/

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


void alarmInterrupt() {
  state = !state;
  digitalWrite(ledPin, state);
  SerialUSB.println("Level of battery : ");
  SerialUSB.println( getVoltage());
  get_value();

  //ledOn();
  //delay(200);
  //ledOff();
  
  SerialUSB.println("Level of battery : ");
  SerialUSB.println( getVoltage());
  
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
  rtc.setSeconds(0); // A chaque interruption, 
}

void setup()
{
  SerialUSB.begin(9600);
  while (!SerialUSB) delay(10);
  SerialUSB.println(F("Okay !"));
  Wire.begin();


  //mySensor.enableDebugging(); // Uncomment this line to get helpful debug messages on SerialUSB

  //.begin will start periodic measurements for us (see the later examples for details on how to override this)

  if (mySensor.begin() == false)
  {
    SerialUSB.println(F("Sensor not detected"));

    unsigned char msg[myobject_Uplink_size];

    myobject_Uplink message;
    message.co2 = 2.f;
    message.temperature = 2.f;
    message.humidity = 2.f;
    message.battery = 5;
    message.timestamp = millis();

    pb_ostream_t stream = pb_ostream_from_buffer(msg, sizeof(msg));
    pb_encode_delimited(&stream, myobject_Uplink_fields, &message);

    SerialUSB.println(message.co2);

  } else{
    SerialUSB.println(F("Sensor detected"));
  }

  testForConnectivity();
  //ledOn();
  rtc.begin(); // initialize RTC 24H format
  rtc.setSeconds(0);
  rtc.setAlarmSeconds(7);
  rtc.enableAlarm(rtc.MATCH_SS);
  rtc.attachInterrupt(alarmInterrupt);
}


void loop()
{
  rtc.standbyMode(); 
}
