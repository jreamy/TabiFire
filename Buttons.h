/* ================================================ *
 *                                                  *
 *                 Button Library                   *
 *                                                  *
 *      Designed to be a dynamic set of buttons     *
 *          Holds up to 10 buttons at once          *
 *                                                  *
 *              Written by: Jack Reamy              *
 *                                                  *
 * ================================================ */

#ifndef Buttons_h
#define Buttons_h

#include "Arduino.h"

class Buttons
{
  private:
    int _pins[10];
    int _numberOfButtons;
    int _lastButtonState[10];
    int _buttonState[10];
    unsigned long _previousMillis[10];
    unsigned long _lastDebounceTime[10];
    unsigned long _debounceDelay;
    int getPosition(int pinNumber);
  public:
    static const int NO_PRESSED = -1;
    static const int MULTIPLE_PRESSED = -2;
    Buttons();
    void attachButton(int pinNumber);
    void detachButton(int pinNumber);
    boolean isAttached(int pinNumber);
    void setDebounceDelay(unsigned long newDelay);
    int getState();
    int getButtonState(int pinNumber);
    int waitForPress();
    int waitForCompletePress();
    void updateButtonStates();
};

#endif

