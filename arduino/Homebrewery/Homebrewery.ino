#include <PID_v1.h>
#include <OneWire.h>
#include <LiquidCrystal.h>

//PID regulator
#define RelayPin 6
#define TenPin 2
#define OneWirePin 11

// DS18S20 Temperature chip i/o
OneWire Ds(OneWirePin);
int HighByte, LowByte, TReading, SignBit, Tc_100, Whole, Fract, oldTc_100; //Readings from OneWire
byte Addr[8]; //Adres of OneWire temperature sensor
//Display
LiquidCrystal Lcd(8, 9, 4, 5, 6, 7); 
//PID for heater
double Setpoint, Input, Output;
//Specify the links and initial tuning parameters
PID myPID(&Input, &Output, &Setpoint,2,5,1, DIRECT);
int WindowSize = 5000;
unsigned long windowStartTime;

String OutputString; //String fo output on Serial
int LowTemp, HighTemp; //Values for thermostat
int Mode, t1,t2, pt; //Current workflow
int ErrorCode; //Different error codes:
//100 - No temperature sensors
String InputString = "";         // a string to hold incoming data
boolean StringComplete = false;  // whether the string is complete
boolean TenTurnedOn;
unsigned long now;
unsigned long timeForSensor;
boolean SensorConversionStarted = false;
boolean sensorFound = false;


void setup(void) {
  // initialize inputs/outputs
  // start serial port
  ErrorCode = 0; //not in use
  Mode = 0;
  Serial.begin(9600);
  InputString.reserve(200);
  Lcd.begin(16, 2);
  if (!sensorFound){
    if ( !Ds.search(Addr)) {
      Ds.reset_search();
      if (!Ds.search(Addr)) {
        Serial.print("Error=Sensor not found!;");  
        Lcd.clear();
        Lcd.print("Error=Sensor not found!;");
        sensorFound = false;
        return;
      }else{
        sensorFound = true; 
      }
    }
  }
  
  pinMode(TenPin, OUTPUT);
  digitalWrite(TenPin,HIGH);
  TenTurnedOn = false;
  
  //PID
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
  
   //Checking for input data
  if (StringComplete == true)
  {
    int eop;
    String part, command, value;
  
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
      else if (command == "reset_sensor")
      {
        sensorFound = false;
      }
    }
    StringComplete = false;
  }
  
  //Main workflow
  if (Mode == 0){
    //Turn off heater
    digitalWrite(TenPin, HIGH);
    TenTurnedOn = false;
  } else if (Mode == 1) {
    //Turn on heater
    digitalWrite(TenPin, LOW);
    TenTurnedOn = true;
  } else if (Mode == 2) {
    Input = Tc_100;
    Setpoint = t1*100;
    myPID.Compute();

    if(now - windowStartTime>WindowSize)  { 
    //time to shift the Relay Window
      windowStartTime += WindowSize;
    } if(Output > now - windowStartTime) {
      digitalWrite(TenPin,LOW);
      TenTurnedOn = true;
    } else {
      digitalWrite(TenPin,HIGH);
      TenTurnedOn = false;
    }
  } else if (Mode == 3) {
	  if (t1 * 100 > Tc_100) {
	    digitalWrite(TenPin, LOW);
	    TenTurnedOn = true;
	  } else {
	    digitalWrite(TenPin, HIGH);
	    TenTurnedOn = false;
      } 
  }

  
  if (!sensorFound){
    if ( !Ds.search(Addr)) {
      Ds.reset_search();
      if (!Ds.search(Addr)) {
        Serial.print("Error=Sensor not found!;");  
        Lcd.clear();
        Lcd.print("Error=Sensor not found!;");
        sensorFound = false;
        return;
      }else{
        sensorFound = true; 
      }
    }
  }
  
  if (OneWire::crc8( Addr, 7) != Addr[7]) {
    Serial.print("Error=CRC is not valid!;");
    Lcd.clear();
    Lcd.print("Error=CRC is not valid!;");
    return;
  }
  
  if (timeForSensor > now) {
    return;
  } else {
    if (!SensorConversionStarted) {
      Ds.reset();
      Ds.select(Addr);
      Ds.write(0x44,1);         // start conversion, with parasite power on at the end
      timeForSensor = now + 2000;
      SensorConversionStarted = true;
      return;
    } else {
      SensorConversionStarted = false;
    }
  }

  
  
  //delay(2000);     // maybe 750ms is enough, maybe not //there's no need in parasite power

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
  oldTc_100 = Tc_100;
  Tc_100 = (6 * TReading) + TReading / 4;    // multiply by (100 * 0.0625) or 6.25
  if (Tc_100 == 8500){
    Tc_100 = oldTc_100;
    Serial.print("Error: no answer from serial;");
  }
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
  } 
  else {
    Serial.print("0");
  }
  Serial.print(";\n");
}