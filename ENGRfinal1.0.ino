void setup() {
  Serial.begin(
    //set the buttons as inputs
    pinMode(yellowButton, INPUT);
    pinMode(blueButton, INPUT);
    pinMode(redButton, INPUT);

    //set the guitar strings =
    int guitarStrings[5] = {0, 0, 0, 0, 0};

  for (int i = 0; thisString < 5 : thisString++) {
  pinMode(guitarStrings[thisString], OUTPUT);
  }
}


/
//set state
int state = 0;

//set the LED output pins
pinMode(blueLED, OUTPUT);
pinMode(yellowLED, OUTPUT);
pinMode(redLED, OUTPUT);

}

void loop() {
  switch (state) {
    case WAIT:

    case CONNECT:
      //initilze communication between computer and arduino
      Serial.println("c");

      //wait for computer to send r
      if (Serial.read() == c) {

        //turn on blue LED light
        digital.Write(blueLED, HIGH);


      case RECORD:
        //initilze communication between computer and arduino
        Serial.println("r");

        //wait for computer to send r
        if (Serial.read() == r) {

          //turn on blue LED light
          digitalWrite(redLED, HIGH);

          //check the string for frequency
          for (int thisString = 0; thisString < 5 : thisString++) {
            strings.sample() = Buttons.checkStrings420();

            //if the string is played send data
            Serial.println(strings.printsample());
          }
          //if string is not played
          if (currentState = LOW) {
            //tell computer to stop recieving data
            Serial.println(-3);

            //update the state
            state = WAIT;
            break;
          }
        }

        GuitarStrings strings



      case TUNE:


      }


