import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicInteger;

public class CheckSign implements Runnable {
    public int guestNum, numOfGuests;
    public boolean[] guestCheck;
    ReentrantLock reLock;
    private AtomicInteger count = null;
    private AtomicInteger showroomBUSY = null;
    private AtomicInteger allGuestsEntered = null;

    public CheckSign(ReentrantLock reLock, int guestNum, int numOfGuests, AtomicInteger count, AtomicInteger showroomBUSY, boolean[] guestCheck, AtomicInteger allGuestsEntered) {
        this.reLock = reLock;
        this.guestNum = guestNum;
        this.numOfGuests = numOfGuests;
        this.count = count;
        this.showroomBUSY = showroomBUSY;
        this.guestCheck = guestCheck;
        this.allGuestsEntered = allGuestsEntered;
    }

    @Override
    public void run() {
        try{
            reLock.lock();
            //System.out.println("guestNum(" + guestNum + ") enters showroom: count: " + count.get() + " allGuestsEntered: " + allGuestsEntered.get() + " guestCheck: " + guestCheck[guestNum]);

            showroomBUSY.set(1);

            if(guestCheck[guestNum] == false){
                guestCheck[guestNum] = true;
                count.getAndIncrement();
            }

            if (numOfGuests == count.get()){
                //System.out.println("All guests went into room. count: " + count.get() + ", numOfGuests: " + numOfGuests);
                allGuestsEntered.set(1);
            }

            showroomBUSY.set(0);
            //System.out.println("Count(" + count.get() + ") guest is in cave and incremented count... not all guests have been in here :(");
        }
        finally{
            //System.out.println("Unlocking guest: " + guestNum);
            reLock.unlock();
        }
    }
}