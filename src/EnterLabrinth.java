import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicInteger;

public class EnterLabrinth implements Runnable {
    public int guestNum, numOfGuests;
    public boolean[] guestCheck;
    ReentrantLock reLock;
    private AtomicInteger count = null;
    private AtomicInteger isCakeThere = null;
    private AtomicInteger allGuestsEntered = null;

    public EnterLabrinth(ReentrantLock reLock, int guestNum, int numOfGuests, AtomicInteger count, AtomicInteger isCakeThere, boolean[] guestCheck, AtomicInteger allGuestsEntered) {
        this.reLock = reLock;
        this.guestNum = guestNum;
        this.numOfGuests = numOfGuests;
        this.count = count;
        this.isCakeThere = isCakeThere;
        this.guestCheck = guestCheck;
        this.allGuestsEntered = allGuestsEntered;
    }

    @Override
    public void run() {
        try{
            reLock.lock();
            //System.out.println("guestNum(" + guestNum + ") enters cave: count: " + count.get() + " isCakeThere: " + isCakeThere.get() + " guestCheck: " + guestCheck[guestNum]);

            if(guestCheck[guestNum] == false && isCakeThere.get() == 1 && guestNum != 0){
                guestCheck[guestNum] = true;
                isCakeThere.getAndDecrement();
                //System.out.println("guestNum(" + guestNum + ") eats cupcake, keep count same: " + count.get() + ", isCakeThere: " + isCakeThere.get());
            }

            // Guest leader that checks if count is equal to total number of guests
            if (guestNum == 0){
                if(guestCheck[guestNum] == false){
                    guestCheck[guestNum] = true;
                    count.getAndIncrement();
                    //System.out.println("guest 0 enters cave first time. count: " + count.get());
                }
                if(isCakeThere.get() == 0){
                    count.getAndIncrement();

                    if (numOfGuests == count.get()){
                        //System.out.println("All guests went into cave. count: " + count.get() + ", guestNum: " + guestNum);
                        allGuestsEntered.getAndIncrement();
                    }
                    else{
                        isCakeThere.getAndIncrement();
                        //System.out.println("Count(" + count.get() + ") guest is in cave and incremented count... not all guests have been in here :(");
                    }
                }
            }
        }
        finally{
            //System.out.println("Unlocking guest: " + guestNum);
            reLock.unlock();
        }
    }
}