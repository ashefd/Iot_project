#include <Arduino.h>
#include <Wire.h>
#include <SPI.h>
#include <SparkFun_VEML7700_Arduino_Library.h>
#include "SparkFun_SCD4x_Arduino_Library.h" //Click here to get the library: http://librarymanager/All#SparkFun_SCD4x

#define COMMAND_LED_OFF     0x00
#define COMMAND_LED_ON      0x01
#define COMMAND_GET_VALUE   0x05
#define COMMAND_NOTHING_NEW 0x99

const byte qwiicAddress = 0x38;     //Default Address
uint16_t ADC_VALUE=0;


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


void setup()
{
  SerialUSB.begin(9600);
  Wire.begin();

  getVoltage();

  //mySensor.enableDebugging(); // Uncomment this line to get helpful debug messages on SerialUSB

  //.begin will start periodic measurements for us (see the later examples for details on how to override this)

  if (mySensor.begin() == false)
  {
    SerialUSB.println(F("Sensor not detected"));
  }else{
    SerialUSB.println(F("Sensor detected"));
  }

  testForConnectivity();
  ledOn();
  delay(1000);
}

void loop()
{
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
  delay(1000);

  //delay(500);
}
