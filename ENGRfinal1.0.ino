GuitarStrings strings;
Buttons buttons;


string1=pin
string2=pin
string3=pin
string4=pin
string5=pin
string6=pin
 //set the guitar strings =
    int guitarStrings[6] = {string1, string2, string3, string4, string5, string6};

void setup() {
  Serial.begin(

  for (int thisString = 0; thisString < 5 : thisString++) {
  pinMode(guitarStrings[thisString], OUTPUT);
  }
buttons.attachbuttons(pinNumbers);
    
//set state
int state = 0;

//set the LED output pins
pinMode(blueLED, OUTPUT);
pinMode(yellowLED, OUTPUT);
pinMode(redLED, OUTPUT);

}

void loop() {
  switch (state) {
    case 0:
      //wait state

    case 1:
      //connect state
      
      //initilze communication between computer and arduino
      Serial.println("c");

      //wait for computer to send r
      if (Serial.read() == c) {

        //turn on blue LED light
        digital.Write(blueLED, HIGH);
        
        state=0;
        break;

      case 2:
        //recordprep state
        
        //initilze communication between computer and arduino
        Serial.println("r);
                       
         do{
            //turn on blue LED light
           digitalWrite(redLED, HIGH);
           
         } while Serial.read() == r);  
        state=3;
          break;
          
          case 3:
          //record state
          
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

  

      case 4:
        //tune state


      }


