package com.xzfantom.homebrewery;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class COMTalker {
	private static SerialPort serialPort;
	private boolean isConnected = false;
	private static DataStorage dataStorage;
	private String SerialPortNumber;

	public COMTalker() {
		//Do nothing yet
	}
	
	public COMTalker(String SPN, DataStorage ds) {
		SerialPortNumber = SPN;
		dataStorage = ds;
	}
	
	public void setSPN (String SPN) {
		SerialPortNumber = SPN;
	}
	
	public void setDS (DataStorage ds) {
		dataStorage = ds;
	}
	
	public boolean IsConnected() {
		return isConnected;
	}
	
	public boolean Connect (String SPN)
	{
		isConnected = false;
		SerialPortNumber = SPN;
		
		serialPort = new SerialPort(SerialPortNumber);
		try {
			serialPort.openPort();
			serialPort.setParams(SerialPort.BAUDRATE_9600,
					SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN
					| SerialPort.FLOWCONTROL_RTSCTS_OUT);
			serialPort.addEventListener(new PortReader(),
					SerialPort.MASK_RXCHAR);
			serialPort.writeString("status;");
			isConnected = true;
			System.out.println("Opened serial connection successfully");
		} catch (SerialPortException ex) {
			System.out.println(ex);
		}	
		return isConnected;
	}
	
	public boolean Connect () {
		return Connect (SerialPortNumber);
	}
	
	public boolean Disconnect() {
		if (isConnected){
			try {
				isConnected = !serialPort.closePort();
			} catch (SerialPortException ex) {
				System.out.println(ex);
			}			
		}
		return isConnected;
	}
	


	private static class PortReader implements SerialPortEventListener {

		public void serialEvent(SerialPortEvent event) {
			if (event.isRXCHAR() && event.getEventValue() > 0) {
				try {
					String data = serialPort.readString(event.getEventValue());
					//serialPort.writeString("status;");
					System.out.println(data);
					dataStorage.GetData(data);
				} catch (SerialPortException ex) {
					System.out.println(ex);
				}
			}
		}
	}
}
