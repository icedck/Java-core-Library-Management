package model;

public class Magazine extends Book{
    private int issueNumber;
    private String releaseMonth;

    public Magazine(String id, String title, String author, String category, int qty, int issueNumber, String releaseMonth) {
        super(id, title, author, category, qty);
        this.issueNumber = issueNumber;
        this.releaseMonth = releaseMonth;
    }

    public int getIssueNumber() {
        return issueNumber;
    }


    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }


    public String getReleaseMonth() {
        return releaseMonth;
    }


    public void setReleaseMonth(String releaseMonth) {
        this.releaseMonth = releaseMonth;
    }


    @Override
    public String toString() {
        return super.toString() + ", Issue: " + issueNumber + ", Month: " + releaseMonth;
    }
}
