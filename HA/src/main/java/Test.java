import java.util.Scanner;
public class Test {



        private Thread t1;
        private Thread t2;

        public static void main(String[] args){
            new Test();
        }

        private class TOne implements Runnable{
            public void run(){
                int incrementBy = 0;

                for (int i = 0; i < 1000; i++) {

                    i = i + incrementBy;

                    //Pause for 1 seconds
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("TEXT OUTPUT INTERUPTED");
                    }
                    //Print text
                    System.out.println(i);
                }
            }
        }

        private class TTwo implements Runnable{
            public void run(){//Code for Thread 2
                try{
                    Scanner scr = new Scanner(System.in);
                    System.out.println(scr.next());
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
        public Test(){
            t1 = new Thread(new TOne());
            t1.run();
            t2 = new Thread(new TTwo());
            t2.run();
        }
    }

