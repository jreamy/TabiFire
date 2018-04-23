


#include "Arduino.h"
#include "GuitarStrings.h"

/**
 * Initialize the memory space
 */

 GuitarStrings::GuitarStrings()
 {
  //Initialize the arrays, set defaults
  for (int i=0; i< SAMPLES; i++)
  {
    _dataPoints[i]=0;
  }
  _pin=A0;
  _startsSample=millis();
  _endSample=millis();
 }

/**
 * Set the pin number to sample
 */

 void GuitarString::sample()
 {
  //Set the intial time of the sampling
  _startSample = micros();

  //Interate through time of the sampling
  _startSample = micros();

  // Iterate through all the sata
  for (int i=0; i<SAMPLES; i++0
  { 
    //Set each data point to the pin value
    _dataPoints[i] = analogRead(_pin);
  }

  //Set the time the sampling ended
  _endSample = micos();
  }
  /**
   * Returns the time it took to get the data sample
   */
   unsigned long GuitarStrings::getSampleTime()
   {
    return _endSample-_startSample;
   }

   void GuitarSrings::printSample;
   {
    Serial.println(-4);
    Serial.println(_startSample);
    Serial.println(_endSample);
    for (in i=0; i< SAMPLES; i++)
    {
      Serial.println(_dataPoints[i])'
    }
    Serial.println(-3);
   }
   void GuitarStrings:printPrettySample()
   {
    Serial.println(-4);
    Serial.print("["0;
    Serial.print(_startSample);
    Serial.print(", ");
    Serial.print(_endSample);
    for (int i=0, i<SAMPLES; i++)
    {
      Serial.print(", ");
      Serial.print (_dataPoints[i]);
    }
    Serial.println("]");
    Serial.println(-3);
   }
 }

