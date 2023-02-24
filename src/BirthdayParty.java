import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class BirthdayParty {
    public static int numOfGuests = 0;
    public static volatile boolean[] guestCheck;
    public static Thread[] threads; //Update later??
    //AtomicInteger count = new AtomicInteger(0);;

    public static void main(String[] args){
        int guestNum;
        Random rand = new Random();
        ReentrantLock reLock = new ReentrantLock();
        AtomicInteger count = new AtomicInteger(0);
        AtomicInteger isCakeThere = new AtomicInteger(1);
        AtomicInteger allGuestsEntered = new AtomicInteger(0);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of guests: ");

        // Read and display number of guests
        numOfGuests = scanner.nextInt();  

        threads = new Thread[numOfGuests];
        guestCheck = new boolean[numOfGuests];
        Arrays.fill(guestCheck, false);

        long startTime = System.currentTimeMillis();

        while (true) {
            try{
                reLock.lock();
                guestNum = rand.nextInt(numOfGuests);
                //System.out.println("guestNum: " + guestNum);

                threads[guestNum] = new Thread(new EnterLabrinth(reLock, guestNum, numOfGuests, count, isCakeThere, guestCheck, allGuestsEntered));
                threads[guestNum].start();

                if(guestNum == 0){
                    if(allGuestsEntered.get() == 1){
                        //System.out.println("count: " + count.get());
                        break;
                    }
                }
            }
            finally{
                reLock.unlock();
            }

        }


        for (int j = 0; j < numOfGuests; j = j + 1) {
            try {
                if (threads[j] != null) {
                    threads[j].join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();
        long totTime = endTime - startTime;

        System.out.println("All guests have entered the cave :)");
        System.out.println("totTime: " + totTime);

    }
}