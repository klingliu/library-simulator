import java.io.IOException;
import java.util.Scanner;

// choose your own adventure library experience

public class Project {
    public static Scanner keyboard = new Scanner(System.in);
    public static int numBooks = 25;
    public static Book[] books = new Book[numBooks];
    public static String booklist = "";

    public static void main(String[] args) throws java.io.IOException {
        int numPatrons = Integer.parseInt(args[0]);
        if (args.length != 1) {
            System.err.println("Sorry, please only input one command-line argument.");
            System.exit(1);
        }
        if (!(numPatrons > 0 && numPatrons <= 5)) {
            System.err.println("There must be 1-5 patrons.");
            System.exit(1);
        }

        setBooks();
        for (int i = 0; i < books.length; i++) {
            booklist = booklist + (i + 1) + ". " + books[i].getTitle() + " by " + books[i].getAuthor() + "\n";
        }

        Patron[] patrons = new Patron[numPatrons];
        for (int i = 0; i < numPatrons; i++) {
            patrons[i] = new Patron();
        }

        for(int i = 0; i < numPatrons; i++) {

            System.out.println("Welcome to the library!\n");
            play("welcome.wav");
            play("silence.wav");

            System.out.println("Please give us a moment to process your account number.\n");
            play("pleasegive.wav");
            play("scan.wav");
            play("scan.wav");
            play("silence.wav");

            System.out.println("It should show up below on your monitor right about now.");
            System.out.println(patrons[i].getAccountNumber());
            System.out.println("\nPlease take a moment to read over your account number.\n");
            play("accnum.wav");
            play("silence.wav");

            System.out.println("What is your purpose for coming to the library today? " +
                    "Please provide your answer in all lowercase. Your options will show up below.\n");
            play("purpose.wav");
            System.out.println("returning  |  borrowing  |  renewing  |  placing a hold  |  paying a fine | checking readability");
            System.out.print("Type your purpose here: ");
            String purpose = keyboard.nextLine();
            while (true) {
                if (!(purpose.equals("returning") || purpose.equals("borrowing") || purpose.equals("renewing") ||
                        purpose.equals("placing a hold") || purpose.equals("paying a fine") || purpose.equals("checking readability"))) {
                    System.out.println("We couldn't seem to understand your answer! Please take a moment to rediscover your purpose.");
                    play("3.wav");
                }
                else break;
            }

            // main actions start here
            doStuff(patrons[i], purpose);

            if (i != numPatrons - 1) {
                play("bell.wav");
                System.out.println("Can the next patron in line please come forward?\n\n");
                play("4.wav");
                play("silence.wav");
            }

        }

    }

    public static void doStuff(Patron patron, String purpose) throws java.io.IOException {
        int choice;
        switch (purpose) {
            case "returning":
                System.out.println("\nGreat! Which book are you returning from our catalogue?\n");
                play("5.wav");
                System.out.println(booklist + "\nNumber:");
                choice = keyboard.nextInt() - 1;
                keyboard.nextLine();
                patron.returns(books[choice], choice);
                System.out.print("Returning " + books[choice].getTitle() + "...");
                scanAndThump(true);
                System.out.println("The book has been successfully returned!");
                play("6.wav");
                play("silence.wav");
                break;

            case "borrowing":
                System.out.println("\nWonderful! Here is a list of books for you to choose. Please type in the number of your choice.");
                play("7.wav");
                System.out.println("\n" + booklist + "\nChoice: ");
                choice = keyboard.nextInt() - 1;
                keyboard.nextLine();
                scanner(choice);
                books[choice].getReadability();
                System.out.println("Would you still like to borrow this book? y/n");
                play("8.wav");
                while (true) {
                    String command = keyboard.nextLine();
                    if (command.equals("y")) {
                        System.out.print("Borrowing " + books[choice].getTitle() + "...");
                        scanAndThump(false);
                        boolean isSuccessful = patron.borrow(books[choice], choice);
                        if (isSuccessful) {
                            play("bookthump.wav");
                            System.out.println("You have successfully borrowed the book! Please enjoy your read.");
                            play("9.wav");
                        } else {
                            System.out.println("Unfortunately, this book has already been borrowed. Would you like to place a hold? y/n");
                            play("10.wav");
                            while (true) {
                                command = keyboard.nextLine();
                                if (command.equals("y")) {
                                    System.out.print("Placing a hold on " + books[choice].getTitle() + "...");
                                    play("clicking.wav");
                                    patron.placeHold(books[choice]);
                                    play("silence.wav");
                                    break;
                                }
                                else if (command.equals("n")) {
                                    System.out.println("Of course.");
                                    play("ofcourse.wav");
                                    break;
                                }
                                else {
                                    System.out.println("Please type your answer again. A lowercase 'y' or 'n' would suffice.");
                                    play("11.wav");
                                }
                            }
                        }
                        break;
                    }
                    else if (command.equals("n")) {
                        System.out.println("Your wishes have been heard. Would you like to choose another book? y/n");
                        play("12.wav");
                        while (true) {
                            command = keyboard.nextLine();
                            if (command.equals("y")) {
                                // borrow another book; recursion?
                                doStuff(patron, "borrowing");
                                break;
                            } else if (command.equals("n")) {
                                System.out.println("Understood. Thank you for coming to the library!");
                                play("13.wav");
                                break;
                            } else {
                                System.out.println("Please type your answer again. A lowercase 'y' or 'n' would suffice.");
                                play("11.wav");
                            }
                        }
                        break;
                    } else {
                        System.out.println("Please type your answer again. A lowercase 'y' or 'n' would suffice.");
                        play("11.wav");
                    }
                }
                break;

            case "renewing":
                System.out.println("\nGreat! Please type the number of the book you would like the renew.");
                play("14.wav");
                System.out.println("\n" + booklist + "\nChoice: ");
                choice = keyboard.nextInt() - 1;
                keyboard.nextLine();
                System.out.println("Renewing " + books[choice].getTitle() + "...");
                play("clicking.wav");
                patron.renew(books[choice], choice);
                play("silence.wav");
                break;

            case "placing a hold":
                System.out.println("\nGreat! Please type the number of the book you would like to place a hold for.");
                play("15.wav");
                System.out.println("\n" + booklist + "\nChoice: ");
                choice = keyboard.nextInt() - 1;
                keyboard.nextLine();
                System.out.println("Placing a hold on " + books[choice].getTitle() + "...");
                play("clicking.wav");
                patron.placeHold(books[choice]);
                play("silence.wav");
                break;

            case "paying a fine":
                if (patron.getFines() == 0) {
                    System.out.println("You have no fines in your balance to pay!");
                    play("16.wav");
                }
                else {
                    System.out.print("\nThank you for coming to pay your fine. This is your balance: $");
                    System.out.printf("%.2f", patron.getFines());
                    play("17.wav");
                    System.out.println("");
                    System.out.println("Would you like to pay with cash or card?");
                    play("18.wav");
                    String command = keyboard.nextLine();
                    while (true) {
                        if (command.equals("cash")) {
                            System.out.println("Type in how many dollars you want to pay (integer amount): ");
                            play("19.wav");
                            int money = keyboard.nextInt();
                            keyboard.nextLine();
                            patron.payFines(money);
                            System.out.print("\nThank you for coming to pay your fine. This is your balance: $");
                            System.out.printf("%.2f", patron.getFines());
                            play("17.wav");
                            play("silence.wav");
                            break;
                        }
                        else if (command.equals("card")) {
                            System.out.println("Card payments come with a $1.00 service charge. Would you like to continue? y/n");
                            play("20.wav");
                            String command2 = keyboard.nextLine();
                            while (true) {
                                if (command2.equals("y")) {
                                    System.out.println("Wonderful.");
                                    play("21.wav");
                                    patron.payFines();
                                    play("silence.wav");
                                    break;
                                } else if (command2.equals("n")) {
                                    System.out.println("Of course! Type in how many dollars you want to pay (integer amount): ");
                                    play("22.wav");
                                    int money = keyboard.nextInt();
                                    keyboard.nextLine();
                                    patron.payFines(money);
                                    play("silence.wav");
                                    break;
                                } else {
                                    System.out.println("Please type your answer again. A lowercase 'y' or 'n' would suffice.");
                                    play("11.wav");
                                }
                            }
                            break;
                        }
                        else {
                            System.out.println("Please type your answer again; \"cash\" or \"card\" in all lowercase would suffice.");
                            play("27.wav");
                        }
                    }
                }
                break;

            case "checking readability":
                System.out.println("\nExcellent! Please type in the number of the book you would like to check.");
                play("23.wav");
                System.out.println("\n" + booklist + "Choice: ");
                choice = keyboard.nextInt() - 1;
                keyboard.nextLine();
                scanner(choice);
                System.out.println("Checking " + books[choice].getTitle() + "...");
                play("clicking.wav");
                books[choice].getReadability();
                play("silence.wav");
                break;
        }

        System.out.println("\nIs there anything else I can help you with today? y/n");
        play("24.wav");
        while(true) {
            String command = keyboard.nextLine();
            if (command.equals("y")) {
                System.out.println("\nWonderful! What else do you want to do?");
                play("25.wav");
                System.out.println("returning  |  borrowing  |  renewing  |  placing a hold  |  paying a fine | checking readability");
                System.out.print("Type here: ");
                purpose = keyboard.nextLine();
                doStuff(patron, purpose);
                break;
            }
            else if (command.equals("n")) {
                System.out.println("\nThank you so much for your patronage. We hope you have a good day.\n");
                play("26.wav");
                play("silence.wav");
                break;
            }
            else {
                System.out.println("\nPlease type your answer again. A lowercase 'y' or 'n' would suffice.");
                play("11.wav");
            }
        }
    }

    public static void scanner(int choice) throws java.io.IOException {
        java.util.Scanner scan = new java.util.Scanner(new java.io.FileReader(books[choice].getFileName()));
        int lineCount = 0;
        while (scan.hasNextLine()) {
            lineCount++;
            scan.nextLine();
        }
        java.util.Scanner scanAgain = new java.util.Scanner(new java.io.FileReader(books[choice].getFileName()));
        String[] lines = new String[lineCount];
        for (int i = 0; i < lineCount; i++) {
            lines[i] = scanAgain.nextLine();
        }
        books[choice].setLineCount(lineCount);
        books[choice].setContent(lines);
    }

    public static void play(String fileName) {
        double[] audio = StdAudio.read(fileName);
        StdAudio.play(audio);
    }

    public static void scanAndThump(boolean thump) {
        if (thump) {
            play("scan.wav");
            System.out.print("...");
            play("scan.wav");
            System.out.print("...");
            play("scan.wav");
            System.out.println("...");
            play("bookthump.wav");
        }
        else {
            play("scan.wav");
            System.out.print("...");
            play("scan.wav");
            System.out.print("...");
            play("scan.wav");
            System.out.println("...");
        }

    }

    public static void setBooks() {
        for (int i = 0; i < books.length; i++) {
            books[i] = new Book();
        }
        setBooksHelper(0, "achristmascarol.txt", "A Christmas Carol in Prose; Being a Ghost Story of Christmas", "Charles Dickens");
        setBooksHelper(1, "aliceinwonderland.txt", "Alice's Adventures in Wonderland", "Lewis Carroll");
        setBooksHelper(2, "anneofgreengables.txt", "Anne of Green Gables", "L. M. Montgomery");
        setBooksHelper(3, "brotherskaramazov.txt", "The Brothers Karamozov", "Fyodor Dostoevsky");
        setBooksHelper(4, "callofthewild.txt", "The Call of the Wild", "Jack London");
        setBooksHelper(5, "divinecomedy.txt", "Divine Comedy, Longfellow's Translation, Hell", "Dante Alighieri");
        setBooksHelper(6, "dracula.txt", "Dracula", "Bram Stoker");
        setBooksHelper(7, "frankenstein.txt", "Frankenstein; Or, The Modern Prometheus", "Mary Wollstonecraft");
        setBooksHelper(8, "frederickdouglass.txt", "Narrative of the Life of Frederick Douglass, an American Slave", "Frederick Douglass");
        setBooksHelper(9, "lesmis.txt", "Les Miserables", "Victor Hugo");
        setBooksHelper(10, "metamorphosis.txt", "Metamorphosis", "Franz Kafka");
        setBooksHelper(11, "montaigne.txt", "The Essays of Michel de Montaigne", "Michel de Montaigne");
        setBooksHelper(12, "nurseryrhymes.txt", "The Nursery Rhymes of England", "J. O. Halliwell-Phillipps and William Bell Scott");
        setBooksHelper(13, "peterpan.txt", "Peter Pan", "J. M. Barrie");
        setBooksHelper(14, "pictureofdoriangray.txt", "The Picture of Dorian Gray", "Oscar Wilde");
        setBooksHelper(15, "secretgarden.txt", "The Secret Garden", "Frances Hodgson Burnett");
        setBooksHelper(16, "sherlock.txt", "The Adventures of Sherlock Holmes", "Arthur Conan Doyle");
        setBooksHelper(17, "taleoftwocities.txt", "A Tale of Two Cities", "Charles Dickens");
        setBooksHelper(18, "theprince.txt", "The Prince", "Niccolo Machiavelli");
        setBooksHelper(19, "therepublic.txt", "The Republic", "Plato");
        setBooksHelper(20, "warandpeace.txt", "War and Peace", "Leo Tolstoy");
        setBooksHelper(21, "waroftheworlds.txt", "The War of the Worlds", "H. G. Wells");
        setBooksHelper(22, "wizardofoz.txt", "The Wonderful Wizard of Oz", "L. Frank Baum");
        setBooksHelper(23, "wollstonecraft.txt", "A Vindication of the Rights of Woman", "Mary Wollstonecraft");
        setBooksHelper(24, "wutheringheights.txt", "Wuthering Heights", "Emily Bronte");
    }

    public static void setBooksHelper(int i, String fileName, String title, String author) {
        books[i].setFileName(fileName);
        books[i].setTitle(title);
        books[i].setAuthor(author);
    }







}
