#include <Arduino.h>
#include <Wire.h>
#include <SPI.h>
#include <SparkFun_VEML7700_Arduino_Library.h>
#include "SparkFun_SCD4x_Arduino_Library.h" //Click here to get the library: http://librarymanager/All#SparkFun_SCD4x


SCD4x mySensor;

void setup()
{
  SerialUSB.begin(9600);
  SerialUSB.println(F("SCD4x Example"));
  Wire.begin();

  //mySensor.enableDebugging(); // Uncomment this line to get helpful debug messages on SerialUSB

  //.begin will start periodic measurements for us (see the later examples for details on how to override this)

  if (mySensor.begin() == false)
  {
    SerialUSB.println(F("Sensor not detected. Please check wiring. Freezing..."));
    while (1)
      ;
  }else{
    SerialUSB.println(F("Sensor detected"));
  }

}

void loop()
{
  
  if (mySensor.readMeasurement()) // readMeasurement will return true when fresh data is available
  {
    SerialUSB.println();

    SerialUSB.print(F("CO2(ppm):"));
    SerialUSB.print(mySensor.getCO2());

    SerialUSB.print(F("\tTemperature(C):"));
    SerialUSB.print(mySensor.getTemperature(), 1);

    SerialUSB.print(F("\tHumidity(%RH):"));
    SerialUSB.print(mySensor.getHumidity(), 1);

    SerialUSB.println();
  }
  else
    SerialUSB.print(F("."));

  delay(500);
}