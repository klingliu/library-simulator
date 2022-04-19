import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.String;

public class Book {

    private String title;
    private String fileName;
    private String author;
    private int readingLevel;
    private boolean available;
    private boolean onHold;
    private int holdQueue;
    private Date dueDate;
    private Date borDate;
    private char[][] content;
    private int linecount;

    // Constructor
    public Book() {
        if ((int) (Math.random() * 2) == 1) available = true;
        holdQueue = (int) (Math.random() * 5);
        if (holdQueue == 0) onHold = true;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setContent(String[] lines) {
        content = new char[lines.length][];
        for (int i = 0; i < linecount; i++) {
            content[i] = lines[i].toCharArray();
        }
    }

    public void setLineCount(int lineCount) {
        this.linecount = lineCount;
    }

    // availability
    public void setAvailability(boolean value) {
        this.available = !value;
    }
    public boolean getAvailability() {
        return this.available;
    }

    // hold statuses
    public void setHoldStatus(boolean wantHold) {
        if (wantHold) {
            this.onHold = true;
            holdQueue++;
        }
        else {
            this.onHold = false;
            holdQueue--;
        }
    }
    public boolean getHoldStatus() {
        return this.onHold;
    }
    public int getHoldQueue() {
        return this.holdQueue;
    }

    // dates
    public void setDates() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        borDate = new Date(System.currentTimeMillis());
        dueDate = new Date(System.currentTimeMillis() + 1209600000);
    }
    public Date[] getDates() {
        Date[] dates = new Date[2];
        dates[0] = borDate;
        dates[1] = dueDate;
        return dates;
    }

    // readability analysis ; mostly set to private because I don't want this section to be messed with, in theory
    private void checkReadability() {
        int l = this.countLetters();
        int w = this.countWords();
        int s = this.countSentences();
        double L = ((double) l) / w * 100;  // average # of letters per 100 words
        double S = ((double) s) / w * 100;  // average # of sentences per 100 words
        this.readingLevel = (int) Math.round(0.0588 * L - 0.296 * S - 15.8);
    }

    private int countLetters() {
        int l = 0;
        for (int i = 0; i < linecount; i++) {
            for (int j = 0; j < this.content[i].length; j++) {
                if(Character.isLetterOrDigit(this.content[i][j])) {
                    l++;
                }
            }
        }
        return l;
    }
    private int countWords() {
        int w = 0;
        for (int i = 0; i < linecount; i++) {
            if (this.content[i].length > 0 && Character.isLetterOrDigit(this.content[i][0]))  {
                w++;
                for (int j = 0; j < this.content[i].length; j++) {
                    if (Character.isWhitespace(this.content[i][j])) {
                            w++;
                    }
                }
            }
        }
        return w;
    }
    private int countSentences() {
        int s = 0;
        for (int i = 0; i < linecount; i++) {
            for (int j = 0; j < this.content[i].length; j++) {
                if ((this.content[i][j] == '.' && (j == this.content[i].length - 1 || Character.isWhitespace(this.content[i][j+1]))) ||
                        this.content[i][j] == '!' || this.content[i][j] == '?') {
                    s++; //this counts only periods (and exclamation / question marks), ignoring ellipses
                }
            }
        }
        return s;
    }

    public void getReadability() {
        this.checkReadability();
        if (this.readingLevel < 1) {
            System.out.println("This book has a pre-elementary reading level. " + "(Grade " + this.readingLevel + ")");
            Project.play("silence.wav");
        }
        else if (this.readingLevel <= 5) {
            System.out.println("This book has an elementary school reading level." + "(Grade " + this.readingLevel + ")");
            Project.play("silence.wav");
        }
        else if (this.readingLevel <= 8) {
            System.out.println("This book has a middle school reading level." + "(Grade " + this.readingLevel + ")");
            Project.play("silence.wav");
        }
        else if (this.readingLevel <= 12) {
            System.out.println("This book has a high school reading level." + "(Grade " + this.readingLevel + ")");
            Project.play("silence.wav");
        }
        else if (this.readingLevel <= 16) {
            System.out.println("This book has a college student reading level." + "(Grade " + this.readingLevel + ")");
            Project.play("silence.wav");
        }
        else {
            System.out.println("This book has a college graduate reading level." + "(Grade " + this.readingLevel + ")");
            Project.play("silence.wav");
        }
    }



}
