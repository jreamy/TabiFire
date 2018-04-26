GuitarStrings strings;
Buttons buttons;

int redButton = 2;
int blueButton = 3;
int yellowButton = 4;

int redLED = 11;
int blueLED = 12;
int yellowLED = 13;

int string1 = 5;
int string2 = 6;
int string3 = 7;
int string4 = 8;
int string5 = 9;
int string6 = 10;
//set the guitar strings =
int guitarStrings[6] = {string1, string2, string3, string4, string5, string6};

void setup() {
  Serial.begin(

  for (int thisString = 0; thisString < 5 : thisString++) {
  pinMode(guitarStrings[thisString], OUTPUT);
  }

  //set state
  int state = 0;
              //set the LED output pins
              pinMode(blueLED, OUTPUT);
              pinMode(yellowLED, OUTPUT);
              pinMode(redLED, OUTPUT);

              //attach buttons
              buttons.attachButton(redButton);
              buttons.attachButton(blueButton);
              buttons.attachButton(yellowButton);

              //attach strings as buttons
              buttons.attachButton(string1);
              buttons.attachButton(string2);
              buttons.attachButton(string3);
              buttons.attachButton(string4);
              buttons.attachButton(string5);
              uttons.attachButton(string6);
              uttons.attachButton(string6);


              //update the state
              state = 0;



}

void loop() {
  switch (state) {
    case 0:
      //wait state
      if (buttons.waitForCompletePress(redButton) == HIGH) {
        state = 2;
      }
      if (buttons.waitForCompletePress(blueButton) == HIGH) {
        state = 1;
      }
      if (buttons.waitForCompletePress(yellowButton) == HIGH) {
        state = 4;
      }

    case 1:
      //connect state

      //initilze communication between computer and arduino
      Serial.println("c");

      //wait for computer to send r
      if (Serial.read() == c) {

        //turn on blue LED light
        digital.Write(blueLED, HIGH);

        state = 0;
        break;

      case 2:
        //recordprep state

        //initilze communication between computer and arduino
        Serial.println("r");

        do {
          //turn on blue LED light
          digitalWrite(redLED, HIGH);

        } while Serial.read() == r);
          state = 3;
          break;

        case 3:
            //record state

            return String = ("-3 ");

            //check if strings are played
for (int thisString = 0; thisString < 5 : thisString++) {
        Buttons.checkStrings420(thisString) = x;

          return String = (return String , thisString , x);

          if (return String length () > 3) {
            delay(150);
            buttons.updateButtonStates(guitarStrings[i]) {
              for (int thisString = 0; thisString < 5 : thisString++) {
              }
            }
            //check if strings are played
            for (int thisString = 0; thisString < 5 : thisString++) {
              Buttons.checkStrings420(thisString) = x;

              return String = (return String , thisString , x);

            }
          }
        }
        //send to computer
        Serial.println(return String, "-1")
        break;
      }

    case 4:
      //tune state
      //initilze communication between computer and arduino
      Serial.println("t");

      do {
        //turn on yellow LED light
        digitalWrite(yellowLED, HIGH);

      } while Serial.read() == r);
        state = 3;
        break;

      case 3:
          //record state

          return String = ("-3 ");

          //check if strings are played
for (int thisString = 0; thisString < 5 : thisString++) {
      Buttons.checkStrings420(thisString) = x;

        return String = (return String , thisString , x);

        if (return String length () > 3) {
          delay(150);
          buttons.updateButtonStates(guitarStrings[i]) {
            for (int thisString = 0; thisString < 5 : thisString++) {
            }
          }
          //check if strings are played
          for (int thisString = 0; thisString < 5 : thisString++) {
            Buttons.checkStrings420(thisString) = x;

            return String = (return String , thisString , x);

          }
        }
      }
      //send to computer
      Serial.println(return String, "-1")
      break;
  }


}
