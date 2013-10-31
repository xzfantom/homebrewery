package com.xzfantom.homebrewery;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class COMTalker {
	private static SerialPort serialPort = null;
	private static Dispatcher dispatcher;
	private String SerialPortNumber="COM10";

	public COMTalker() {
		// Do nothing yet
	}

	public COMTalker(String SPN, Dispatcher ds) {
		SerialPortNumber = SPN;
		dispatcher = ds;
	}

	public void setSPN(String SPN) {
		SerialPortNumber = SPN;
	}

	public void setDispatcher(Dispatcher ds) {
		dispatcher = ds;
	}

	public boolean IsConnected() {
		return serialPort.isOpened();
	}

	public boolean Connect(String SPN) {
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
			System.out.println("Opened serial connection successfully");
		} catch (SerialPortException ex) {
			System.out.println(ex);
		}
		return serialPort.isOpened();
	}

	public boolean Connect() {
		return Connect(SerialPortNumber);
	}

	public boolean Disconnect() {
		if (serialPort.isOpened()) {
			try {
				serialPort.closePort();
			} catch (SerialPortException ex) {
				System.out.println(ex);
			}
		}
		return !serialPort.isOpened();
	}
	
	public boolean SendMessage(String S) {
		boolean result = false;
		if (null != serialPort && serialPort.isOpened()) {
			try{
				result = serialPort.writeString(S);
			} catch (SerialPortException ex) {
				System.err.println(ex);
				result = false;
			}
		} 
		
		return result;
	}

	private static class PortReader implements SerialPortEventListener {

		public void serialEvent(SerialPortEvent event) {
			if (event.isRXCHAR() && event.getEventValue() > 0) {
				try {
					String data = serialPort.readString(event.getEventValue());
					// serialPort.writeString("status;");
					//System.out.println(data);
					System.out.println(String.valueOf(event.getEventValue())+ " " + data);
					dispatcher.processCOMMessage(data);
				} catch (SerialPortException ex) {
					System.out.println(ex);
				}
			}
		}
	}
}
