import com.fazecast.jSerialComm.*;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Proxy;
import java.util.Scanner;

public class FazecastExamples{

    final public SerialPort comPort = SerialPort.getCommPort("COM4");
    public Arduino a = new Arduino(comPort);


    public static void main(String[] args) {
        FazecastExamples mainClass = new FazecastExamples();
        //mainClass.nonblockingReadingUsageExample();
        //mainClass.blockingandSemiblockingReadingUsageExample();
        //mainClass.javaInputStreamandOutputStreamInterfacingUsageExample();

        //mainClass.serialOut();
        mainClass.dataAvailableforReadingExample();

    }

    public void nonblockingReadingUsageExample(){
        comPort.openPort();
        try {
            while (true)
            {
                while (comPort.bytesAvailable() == 0)
                    Thread.sleep(20);

                byte[] readBuffer = new byte[comPort.bytesAvailable()];
                int numRead = comPort.readBytes(readBuffer, readBuffer.length);
                System.out.println("Read " + numRead + " bytes.");
            }
        } catch (Exception e) { e.printStackTrace(); }
        comPort.closePort();
    }

    public void blockingandSemiblockingReadingUsageExample(){
        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 2000, 0);
        try {
            while (true)
            {
                byte[] readBuffer = new byte[1024];
                int numRead = comPort.readBytes(readBuffer, readBuffer.length);
                System.out.println("Read " + numRead + " bytes.");
            }
        } catch (Exception e) { e.printStackTrace(); }
        comPort.closePort();
    }

    public void javaInputStreamandOutputStreamInterfacingUsageExample(){
        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        InputStream in = comPort.getInputStream();
        try
        {
            for (int j = 0; j < 1000; ++j)
                System.out.print((char)in.read());
            in.close();
        } catch (Exception e) { e.printStackTrace(); }
        comPort.closePort();
    }

    public void serialOut(){
        String command = "";
        Scanner sc = new Scanner(System.in);
        //a.setPortDescription("COM4");

        //a.openConnection();
        command = sc.nextLine();
        System.out.println(command);
        a.serialWrite(command);
/*        while (command != "5") {

            //System.out.println(a.serialRead());
            //a.serialWrite("0");

        }

 */
        //a.closeConnection();
    }

    public void dataAvailableforReadingExample(){
        //a.setPortDescription("COM4");
        //a.openConnection();
        //Scanner sc = new Scanner(System.in);
        comPort.openPort();

        comPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {

                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE){
                    System.out.println(event.getEventType());
                    return;
                }
                serialOut();
                System.out.println(event.getEventType());
                int size = event.getSerialPort().bytesAvailable();
                byte[] buffer = new byte[size];
                event.getSerialPort().readBytes(buffer, size);
                for(byte b:buffer)
                    System.out.print((char)b);
            }
        });
    }

    public void serialWrite(String s){
        //writes the entire string at once.
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        try{Thread.sleep(5);} catch(Exception e){}
        PrintWriter pout = new PrintWriter(comPort.getOutputStream());
        pout.print(s);
        pout.flush();

    }


}
