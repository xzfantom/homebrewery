#include <PID_v1.h>
#include <OneWire.h>
#include <LiquidCrystal.h>

// DS18S20 Temperature chip i/o
OneWire Ds(11);  // on pin 11
int TenPin = 2;
LiquidCrystal Lcd(8, 9, 4, 5, 6, 7); //Display
int HighByte, LowByte, TReading, SignBit, Tc_100, Whole, Fract; //Readings from OneWire
byte Addr[8]; //Adres of OneWire temperature sensor
String OutputString; //String fo output on Serial
int LowTemp, HighTemp; //Values for thermostat
int Mode, t1,t2, pt; //Current workflow
int ErrorCode; //Different error codes:
//100 - No temperature sensors
String InputString = "";         // a string to hold incoming data
boolean StringComplete = false;  // whether the string is complete
boolean TenTurnedOn;
//PID regulator
#define RelayPin 6
//Define Variables we'll be connecting to
double Setpoint, Input, Output;
//Specify the links and initial tuning parameters
PID myPID(&Input, &Output, &Setpoint,2,5,1, DIRECT);
int WindowSize = 5000;
unsigned long windowStartTime;
unsigned long now;


void setup(void) {
  // initialize inputs/outputs
  // start serial port
  ErrorCode = 0;
  Mode = 0;
  Serial.begin(9600);
  InputString.reserve(200);
  Lcd.begin(16, 2);
  //if ( !Ds.search(Addr)) {
  Ds.reset_search();
  // ErrorCode = 100;
  //}
  TenTurnedOn = false;
  pinMode(TenPin, OUTPUT); 

  //PID regulator
  windowStartTime = millis();
  //initialize the variables we're linked to
  Setpoint = 100;
  //tell the PID to range between 0 and the full window size
  myPID.SetOutputLimits(0, WindowSize);
  //turn the PID on
  myPID.SetMode(AUTOMATIC);
}

void loop(void) {
  byte i;
  byte present = 0;
  byte data[12];
  
  now = millis();

  if ( OneWire::crc8( Addr, 7) != Addr[7]) {
    Serial.print("CRC is not valid!\n");
    return;
  }
  if (ErrorCode == 100)
  {
    Serial.print("No temperature sensors!\n");
    if ( !Ds.search(Addr)) 
    {
      Ds.reset_search();
      ErrorCode = 100;
      // return;
    }
    else
    {
      ErrorCode = 0;
    }
  }
  if ( !Ds.search(Addr)) {
    Ds.reset_search();
  }
  Ds.reset();
  Ds.select(Addr);
  Ds.write(0x44,1);         // start conversion, with parasite power on at the end

  //delay(1000);     // maybe 750ms is enough, maybe not //there's no need in parasite power

  present = Ds.reset();
  Ds.select(Addr);    
  Ds.write(0xBE);         // Read Scratchpad

  for ( i = 0; i < 9; i++) {           // we need 9 bytes
    data[i] = Ds.read();
  }
  LowByte = data[0];
  HighByte = data[1];
  TReading = (HighByte << 8) + LowByte;
  SignBit = TReading & 0x8000;  // test most sig bit
  if (SignBit) // negative
  {
    TReading = (TReading ^ 0xffff) + 1; // 2's comp
  }
  Tc_100 = (6 * TReading) + TReading / 4;    // multiply by (100 * 0.0625) or 6.25
  Whole = Tc_100 / 100;  // separate off the whole and fractional portions
  Fract = Tc_100 % 100;

  //Formatting output information
  OutputString = String("");
  OutputString = OutputString + ":";

  if (SignBit) // If its negative
  {
    OutputString = OutputString +"-";
  }
  OutputString = OutputString + Whole;
  OutputString = OutputString + ".";
  if (Fract < 10)
  {
    OutputString = OutputString +"0";
  }
  OutputString = OutputString + Fract;
  OutputString = OutputString + ";\n";
  Lcd.clear();
  Lcd.print(OutputString);
  //Serial.print(OutputString);

  //Checking for input data
  if (StringComplete == true)
  {
    int eop;
    Serial.print(InputString);
    String part, command, value;
    //Getting information from input string    
    while (InputString != "")
    {
      value = "";
      part = "";
      command = "";

      eop = InputString.indexOf(";");
      if (eop == -1)
      {
        part = InputString;
        InputString = "";
      }
      else
      {
        part = InputString.substring(0, eop);
        InputString = InputString.substring(eop+1);
      }
      eop = part.indexOf("=");
      if (eop == -1)
      {
        command = part;
      }
      else
      {
        command = part.substring(0, eop);
        value = part.substring(eop+1);
      }
      if (command == "mode")
      {
        Mode = value.toInt();
      }
      else if (command == "t1")
      {
        t1 = value.toInt();
      }
      else if (command == "t2")
      {
        t2 = value.toInt();
      }
      else if (command == "pt")
      {
        pt = value.toInt();
      }
      else if (command == "status")
      {
        sendStatus();
      }
    }
    StringComplete = false;
  }
  //Main workflow
  if (Mode == 0)
  {
    //Turn off heater
    digitalWrite(TenPin, HIGH);
  }
  else if (Mode == 1)
  {
    //Set thermostat
    digitalWrite(TenPin, LOW);
    TenTurnedOn = true;
    Serial.print("Mode = 2");
  } 
  else if (Mode == 2)
  {
    /*if (t1 * 100 > Tc_100)
    {
      digitalWrite(TenPin, LOW);
      TenTurnedOn = true;
    }
    else
    {
      digitalWrite(TenPin, HIGH);
      TenTurnedOn = false;
    } */   
    Input = Tc_100;
    Setpoint = t1*100;
    myPID.Compute();
    
    if(now - windowStartTime>WindowSize)
    { //time to shift the Relay Window
      windowStartTime += WindowSize;
    }
    if(Output > now - windowStartTime) 
    {
      digitalWrite(TenPin,LOW);
      TenTurnedOn = true;
    }
    else 
    {
      digitalWrite(TenPin,HIGH);
      TenTurnedOn = false;
    }
  }

}

/*
  SerialEvent occurs whenever a new data comes in the
 hardware serial RX.  This routine is run between each
 time loop() runs, so using delay inside loop can delay
 response.  Multiple bytes of data may be available.
 */
void serialEvent() {
  while (Serial.available()) 
  {
    // get the new byte:
    char inChar = (char)Serial.read();
    //Serial.print(inChar);
    // add it to the inputString:
    InputString += inChar;
    // if the incoming character is a newline, set a flag
    // so the main loop can do something about it:
    if (inChar == ';') 
    {
      StringComplete = true;
    }
  }
}

void sendStatus() {
  Serial.print("Mode = ");
  Serial.print(Mode);
  Serial.print("; t1 = ");
  Serial.print(t1);
  Serial.print("; t2 = ");
  Serial.print(t2);
  Serial.print("; pt = ");
  Serial.print(pt);
  Serial.print("; Tc_100 = ");
  Serial.print(Tc_100);
  Serial.print("; time = ");
  Serial.print(now);
  Serial.print("; Ten = ");
  if (TenTurnedOn) {
    Serial.print("1");
  } else {
    Serial.print("0");
  }
  Serial.print(';\n');
}

/*
#include <PID_v1.h>
 #define RelayPin 6
 
 //Define Variables we'll be connecting to
 double Setpoint, Input, Output;
 
 //Specify the links and initial tuning parameters
 PID myPID(&Input, &Output, &Setpoint,2,5,1, DIRECT);
 
 int WindowSize = 5000;
 unsigned long windowStartTime;
 void setup()
 {
 windowStartTime = millis();
 
 //initialize the variables we're linked to
 Setpoint = 100;
 
 //tell the PID to range between 0 and the full window size
 myPID.SetOutputLimits(0, WindowSize);
 
 //turn the PID on
 myPID.SetMode(AUTOMATIC);
 }
 
 void loop()
 {
 Input = analogRead(0);
 myPID.Compute();
 
 unsigned long now = millis();
 if(now - windowStartTime>WindowSize)
 { //time to shift the Relay Window
 windowStartTime += WindowSize;
 }
 if(Output > now - windowStartTime) digitalWrite(RelayPin,HIGH);
 else digitalWrite(RelayPin,LOW);
 
 }
 */

