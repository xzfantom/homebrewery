package com.xzfantom.homebrewery;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class COMTalker {
	private static SerialPort serialPort;

	public COMTalker(String SerialPortNumber) {
		// ������� � ����������� ��� �����
		serialPort = new SerialPort(SerialPortNumber);
		try {
			// ��������� ����
			serialPort.openPort();
			// ���������� ���������
			serialPort.setParams(SerialPort.BAUDRATE_9600,
					SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			// �������� ���������� ���������� �������
			serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN
					| SerialPort.FLOWCONTROL_RTSCTS_OUT);
			// ������������� ����� ������� � �����
			serialPort.addEventListener(new PortReader(),
					SerialPort.MASK_RXCHAR);
			// ���������� ������ ����������
			serialPort.writeString("Get data");
		} catch (SerialPortException ex) {
			System.out.println(ex);
		}
	}

	private static class PortReader implements SerialPortEventListener {

		public void serialEvent(SerialPortEvent event) {
			if (event.isRXCHAR() && event.getEventValue() > 0) {
				try {
					// �������� ����� �� ����������, ������������ ������ � �.�.
					String data = serialPort.readString(event.getEventValue());
					// � ����� ���������� ������
					serialPort.writeString("Get data");
					System.out.println(data);
				} catch (SerialPortException ex) {
					System.out.println(ex);
				}
			}
		}
	}
}
