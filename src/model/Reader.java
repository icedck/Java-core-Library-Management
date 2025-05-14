package model;

public class Reader extends User {
    private String email;
    private String phone;
    private int borrowedCount;
    
    public Reader(String id, String name, String email, String phone) {
        super(id, name);
        this.email = email;
        this.phone = phone;
        this.borrowedCount = 0;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getBorrowedCount() {
        return borrowedCount;
    }

    public void incrementBorrowedCount() {
        this.borrowedCount++;
    }

    public void decrementBorrowedCount() {
        if (this.borrowedCount > 0) {
            this.borrowedCount--;
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", Email: " + email + ", Phone: " + phone + ", Đang mượn: " + borrowedCount + " cuốn";    
    }
}
