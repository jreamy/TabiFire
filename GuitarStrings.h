
/* ================================================ *
 *                                                  *
                 Guitar Stirng Library
 *                                                  *
        Designed to be a dynamic set of stirngs
             Holds up to 6 buttons at once
 *                                                  *
        Calculates relative frequency of string
           Calculates frequency of one string
 *                                                  *
                Written by: Jack Reamy
 *                                                  *
   ================================================ */

#ifndef GuitarStrings_h
#define GuitarStrings_h

#include "Arduino.h"

#define SAMPLES 256

class GuitarStrings
{
  private:
    int _pin;
    int _dataPoints[SAMPLES];
    unsigned long _startSample;
    unsigned long _endSample;

  public:
    GuitarStrings();
    void setSamplePin(int pinNumber);
    void sample();
    unsigned long getSampleTime();
    void printSample();
    void printPrettySample();
};

#endif
}

