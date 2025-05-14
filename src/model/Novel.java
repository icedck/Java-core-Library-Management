package model;

public class Novel extends Book {
    private String genre;
    private boolean isTranslated;

    public Novel(String id, String title, String author, String category, int qty, String genre, boolean isTranslated) {
        super(id, title, author, category, qty);
        this.genre = genre;
        this.isTranslated = isTranslated;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isTranslated() {
        return isTranslated;
    }

    public void setTranslated(boolean isTranslated) {
        this.isTranslated = isTranslated;
    }

    @Override
    public String toString() {
        return super.toString() + ", Genre: " + genre + ", Translated: " + (isTranslated ? "Yes" : "No");
    }
}
