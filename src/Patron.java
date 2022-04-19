import java.util.Date;

public class Patron {
    public static int numBooks = 25;

    private String accountNumber;
    private double fines;
    private int[] numRenewalsLeft;

    public Patron() {
        accountNumber = "111181";
        fines = Math.random() * 100 - 50; // around a 50% chance of having a fine; if you do have a fine, max is $49.99
        if (fines < 0) fines = 0;
        numRenewalsLeft = new int[numBooks];
        for (int i = 0; i < numRenewalsLeft.length; i++) {
            numRenewalsLeft[i] = (int) (Math.random() * 4); // 0 - 3 renewals left
        }
    }


    public String getAccountNumber() {
        for (int i = 0; i < 7; i++) {
            this.accountNumber = accountNumber + (int) (Math.random() * 10);
        }
        return accountNumber;
    }

    public boolean borrow(Book a, int choice) {
        boolean isSuccessful = false;
        if (a.getAvailability()) {  // if the book is available, set the book's availability successfully
            isSuccessful = true;
            a.setAvailability(true);
            this.setNumRenewalsLeft(false, choice);
            a.setDates();
            Date[] dates = a.getDates();
            System.out.println("Borrow date: " + dates[0]);
            System.out.println("Due date: " + dates[1]);
        }
        return isSuccessful;
    }

    public void returns(Book a, int choice) {
        a.setAvailability(false);
        this.setNumRenewalsLeft(false, choice);
    }

    public void renew(Book a, int choice) {
        if (this.numRenewalsLeft[choice] == 0) {
            System.out.println("We're sorry; you have no more renewals left. Please return the book on the due date.");
            Project.play("1.wav");
        }
        else {
            this.setNumRenewalsLeft(true, choice);
            a.setDates();
            Date[] dates = a.getDates();
            System.out.println("Date of renewal: " + dates[0]);
            System.out.println("New due date: " + dates[1]);
            System.out.println("You have " + this.getNumRenewalsLeft(choice) + " renewals left.");
        }
    }

    public void setNumRenewalsLeft(boolean renewing, int choice) {
        if (renewing) {
            numRenewalsLeft[choice]--;
        }
        else numRenewalsLeft[choice] = 3;
    }

    public int getNumRenewalsLeft(int choice) {
        return this.numRenewalsLeft[choice];
    }

    public void placeHold(Book a) {
        a.setHoldStatus(true);
        System.out.print("Your hold was successful!\nYour place in the hold queue is " + a.getHoldQueue() + ". ");
        System.out.println("We estimate it will take around " + a.getHoldQueue() * 2 + " weeks for the book to reach you.");
    }

    public void removeHold(Book a) {
        a.setHoldStatus(false);
    }

    public double getFines() {
        this.fines = Math.abs(this.fines);
        return this.fines;
    }

    public void payFines(double amount) {
        double change = this.fines - amount;
        Project.play("cashrustle.wav");
        if (change < 0) {
            System.out.printf("Thank you for paying in cash! Here is $" + "%.2f", Math.abs(change));
            System.out.println(" in change.");
            Project.play("silence.wav");
            this.fines = 0;
        }
        else this.fines = this.fines - amount;
    }

    public void payFines() {
        Project.play("card.wav");
        if (Math.random() * 100 - 50 < 0) {
            System.out.println("I'm so sorry, but your card was declined.");
            Project.play("2.wav");
        }
        else {
            System.out.printf("Thank you for your payment of $" + "%.2f", this.fines + 1.50);
            System.out.println(".");
            Project.play("silence.wav");
            this.fines = 0;
        }
    }
}
