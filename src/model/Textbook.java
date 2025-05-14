package model;

public class Textbook extends Book {
    private String subject;
    private String level;

    public Textbook(String id, String title, String author, String category, int qty, String subject, String level) {
        super(id, title, author, category, qty);
        this.subject = subject;
        this.level = level;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return super.toString() + ", Subject: " + subject + ", Level: " + level;
    }
}
