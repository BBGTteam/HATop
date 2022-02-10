import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class ArduinoSerial {
    SerialPort activePort;
    //SerialPort[] ports = SerialPort.getCommPorts();

    public void setPort(String port) {
        activePort = SerialPort.getCommPort(port);

        if (activePort.openPort())
            System.out.println(activePort.getPortDescription() + " port opened.");

        activePort.addDataListener(new SerialPortDataListener() {

            @Override
            public void serialEvent(SerialPortEvent event) {
                int size = event.getSerialPort().bytesAvailable();
                byte[] buffer = new byte[size];
                event.getSerialPort().readBytes(buffer, size);
                for(byte b:buffer)
                    System.out.print((char)b);
            }

            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }
        });
    }

    public void start() {
        setPort("COM4");
    }

    public static void main(String[] args) {
        ArduinoSerial mainClass = new ArduinoSerial();
        mainClass.start();
    }
}
