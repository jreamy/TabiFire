/* ================================================ *
 *                                                  *
                   Button Library
 *                                                  *
        Designed to be a dynamic set of buttons
            Holds up to 10 buttons at once
 *                                                  *
                Written by: Jack Reamy
 *                                                  *
   ================================================ */

#include "Arduino.h"
#include "Buttons.h"

/* ================================================ *
             Public Buttons Class Functions
   ================================================ */

Buttons::Buttons()
{
  // Initialize arrays to be blank
  for (int i = 0; i < 10; i++)
  {
    _pins[i] = 0;
    _lastButtonState[i] = 0;
    _buttonState[i] = 0;
    _previousMillis[i] = millis();
    _lastDebounceTime[i] = millis();
  }

  // Set the other variables
  _numberOfButtons = 0;
  _debounceDelay = 50;
}

void Buttons::attachButton(int pinNumber)
{
  // If there's room
  if (_numberOfButtons < 10)
  {
    // Add the pin number
    _pins[_numberOfButtons] = pinNumber;

    // Initialize the pin
    pinMode(pinNumber, INPUT);

    // Set the initial value (& last value)
    _lastButtonState[_numberOfButtons] = digitalRead(pinNumber);
    _buttonState[_numberOfButtons] = digitalRead(pinNumber);

    // Set the last debounce time
    _previousMillis[_numberOfButtons] = 0;
    _lastDebounceTime[_numberOfButtons] = 0;

    // Increase the number of buttons
    _numberOfButtons++;
  }
}

void Buttons::detachButton(int pinNumber)
{
  // The position of the pin in the list
  int pinPosition;

  // Make sure the pin is actually attached
  if (isAttached(pinNumber))
  {
    // Get the pin position to get rid of
    pinPosition = getPosition(pinNumber);

    // Iterate through all the data arrays, data at the position
    for (int i = pinPosition; i < 9; i++)
    {
      // Set the data to the data in the next position
      _pins[i] = _pins[i + 1];
      _lastButtonState[i] = _lastButtonState[i + 1];
      _buttonState[i] = _buttonState[i + 1];
      _previousMillis[i] = _previousMillis[i + 1];
      _lastDebounceTime[i] = _lastDebounceTime[i + 1];
    }

    // Decrease the number of buttons
    _numberOfButtons--;
  }
}

boolean Buttons::isAttached(int pinNumber)
{
  boolean alreadyAttached = false;

  // Iterate through the list of button numbers and return true if it is there
  for (int i = 0; i < _numberOfButtons; i++)
  {
    // Check if the current pin in the list of pins is the input number
    if (pinNumber == _pins[i])
    {
      alreadyAttached = true;
    }
  }

  // Return if the pin is in the list of pins or not
  return alreadyAttached;
}

void Buttons::setDebounceDelay(unsigned long newDelay)
{
  _debounceDelay = newDelay;
}

/**
   Returns -1 if there are no buttons pressed
   Returns -2 if there are multiple buttons pressed
   Returns the pin number of the pressed button
*/
int Buttons::getState()
{
  // Local variables
  int numberHigh = 0;
  int highPin = -1;

  // Iterate through the buttons, and increase the number of high
  for (int i = 0; i < _numberOfButtons; i++)
  {
    // If the button is high
    if (_buttonState[i])
    {
      // Increase the number of buttons that are high
      numberHigh++;

      // Set the button that is high to the pin of the current location
      highPin = _pins[i];
    }

    // If there are multiple buttons pressed, set return to -2
    if (numberHigh > 1)
    {
      highPin = -2;
    }
  }
  // Return
  return (int) highPin;
}

int Buttons::getButtonState(int pinNumber)
{
  // Set the default return to false
  int buttonState = LOW;

  // Check if the pin is attached
  if (isAttached(pinNumber))
  {
    // Get the position of the button pin
    int locationInData = getPosition(pinNumber);

    // Set the button state to the state of the button
    buttonState = _buttonState[locationInData];
  }

  // Returns the button state
  return buttonState;
}

int Buttons::waitForPress()
{
  // Set up local variable
  int buttonState;

  // Wait until a button is pressed
  do
  {
    // Update the buttons
    this->updateButtonStates();

    // Get the state of the buttons
    buttonState = this->getState();
  } while (buttonState < 0);

  // Return
  return buttonState;
}

int Buttons::waitForCompletePress()
{
  // Set up local variables
  int buttonToReturn = this->waitForPress();
  int buttonState;

  // Wait for the button to be released
  do
  {
    // Update the buttons
    this->updateButtonStates();

    // Get the state of the buttons
    buttonState = this->getState();
  } while (buttonState != NO_PRESSED);

  // Return
  return buttonToReturn;
}

void Buttons::updateButtonStates()
{
  // Local variables
  int reading;

  // Iterate through the buttons to update
  for (int i = 0; i < _numberOfButtons; i++)
  {
    // Get the initial reading
    reading = digitalRead(_pins[i]);

    // Check if reading has changed
    if (reading != _lastButtonState[i])
    {
      _lastDebounceTime[i] = millis();
    }

    // If the state has been pressed longer than the debounce time
    if ((millis() - _lastDebounceTime[i]) > _debounceDelay)
    {
      // If the reading is not what the button state was
      if (reading != _buttonState[i])
      {
        // Update the button state
        _buttonState[i] = (int) reading;
      }
    }

    // Update the previous reading
    _lastButtonState[i] = reading;
  }
}


/* ================================================ *
             Private Buttons Class Functions
   ================================================ */

int Buttons::getPosition(int pinNumber)
{
  int pinPosition = 9;

  // Iterate through the list of button numbers and store the location of it
  for (int i = 0; i < _numberOfButtons; i++)
  {
    // Check if the current pin in the list of pins is the input number
    if (pinNumber == _pins[i])
    {
      pinPosition = i;
    }
  }

  // Return if the pin is in the list of pins or not
  return pinPosition;
}



