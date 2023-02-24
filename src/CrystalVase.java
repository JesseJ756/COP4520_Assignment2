import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class CrystalVase {
    public static int numOfGuests = 0;
    public static volatile boolean[] guestCheck;
    public static Thread[] threads; //Update later??

    public static void main(String[] args){
        int guestNum;
        Random rand = new Random();
        ReentrantLock reLock = new ReentrantLock();
        AtomicInteger count = new AtomicInteger(0);
        AtomicInteger showroomBUSY = new AtomicInteger(0);
        AtomicInteger allGuestsEntered = new AtomicInteger(0);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of guests: ");

        // Get user input
        numOfGuests = scanner.nextInt();  

        threads = new Thread[numOfGuests];
        guestCheck = new boolean[numOfGuests];
        //Arrays.fill(guestCheck, false);

        long startTime = System.currentTimeMillis();

        while (true) {
            //System.out.println("allGuestsEntered.get(): " + allGuestsEntered.get());
            if(allGuestsEntered.get() >= 1){
                //System.out.println("all Guests Entered");
                break;
            }

            guestNum = rand.nextInt(numOfGuests);
            //System.out.println("guestNum: " + guestNum);

            if (showroomBUSY.get() == 1){
                //System.out.println("Showroom BUSY");
                continue;
            }

            threads[guestNum] = new Thread(new CheckSign(reLock, guestNum, numOfGuests, count, showroomBUSY, guestCheck, allGuestsEntered));
            threads[guestNum].start();
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

        System.out.println("All guests have seen the vase :)");
        System.out.println("totTime: " + totTime);

    }
}