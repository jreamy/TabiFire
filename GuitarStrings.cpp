



#include "Arduino.h"
#include "GuitarStrings.h"


/**
 * Initialize the memory space
 */
GuitarStrings::GuitarStrings()
{
  // Initialize the arrays, set defaults
  for (int i = 0; i < SAMPLES; i++)
  {
    _dataPoints[i] = 0;
  }
  
  _pin = A0;
  _startSample = millis();
  _endSample = millis();
}


/**
 * Set the pin number to sample
 */
void GuitarStrings::setSamplePin(int pinNumber)
{
  _pin = pinNumber;
}

/**
 * Samples the pin 256 times, and sets the start and end times
 */
void GuitarStrings::sample()
{
  // Set the initial time of the sampling
  _startSample = micros();

  // Iterate through all the data
  for (int i = 0; i < SAMPLES; i++)
  {
    // Set each data point to the pin value
    _dataPoints[i] = analogRead(_pin);
  }
  
  // Set the time the sampling ended
  _endSample = micros();
}

/**
 * Returns the time it took to get the data sample
 */
unsigned long GuitarStrings::getSampleTime()
{
  return _endSample - _startSample;
}

void GuitarStrings::printSample()
{
  Serial.println(-4);
  Serial.println((int) (_startSample / 1000));
  Serial.println(_endSample - _startSample);
  for (int i = 0; i < SAMPLES; i++)
  {
    Serial.println(_dataPoints[i]);
  }
  Serial.println(-3);
}

void GuitarStrings::printPrettySample()
{
  Serial.println(-4);
  Serial.print("[");
  Serial.print((int) (_startSample / 1000));
  Serial.print(", ");
  Serial.print(_endSample - _startSample);
  for (int i = 0; i < SAMPLES; i++)
  {
    Serial.print(", ");
    Serial.print(_dataPoints[i]);
  }
  Serial.println("]");
  Serial.println(-3);
}


