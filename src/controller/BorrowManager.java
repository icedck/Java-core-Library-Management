package controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import exception.BorrowException;
import model.Book;
import model.BorrowRecord;
import model.Reader;

public class BorrowManager {
    public List<BorrowRecord> records = new ArrayList<>();
    private static final int MAX_BORROWED_BOOKS = 3;  // Giới hạn số lượng sách mượn

    public void borrow(BookManager bm, ReaderManager rm, String bookId, String readerId, LocalDate dueDate) throws BorrowException {
        Book book = bm.findId(bookId);
        Reader reader = rm.findId(readerId);


        if (book == null) throw new BorrowException("Không tìm thấy sách.");
        if (reader == null) throw new BorrowException("Không tìm thấy độc giả.");
        if (book.getQuantity() <= 0) throw new BorrowException("Sách đã hết hàng.");
        if (reader.getBorrowedCount() >= MAX_BORROWED_BOOKS) throw new BorrowException("Độc giả đã mượn quá số lượng sách cho phép (" + MAX_BORROWED_BOOKS + " cuốn).");

        for (BorrowRecord r : records) {
            if (r.getBookId().equals(bookId) && r.getReaderId().equals(readerId) && r.getReturnDate() == null) {
                throw new BorrowException("Độc giả này đang mượn cuốn sách này.");
            }
        }

        book.setQuantity(book.getQuantity() - 1);
        reader.incrementBorrowedCount();
        records.add(new BorrowRecord(readerId, bookId, LocalDate.now(), dueDate));
    }

    public void returnBook(BookManager bm, ReaderManager rm, String bookId, String readerId) throws BorrowException {
        BorrowRecord record = null;
        for (BorrowRecord r : records) {
            if (r.getBookId().equals(bookId) && r.getReaderId().equals(readerId) && r.getReturnDate() == null) {
                record = r;
                break;
            }
        }

        if (record == null) throw new BorrowException("Không tìm thấy lượt mượn phù hợp.");

        record.setReturnDate(LocalDate.now());
        Book book = bm.findId(bookId);
        Reader reader = rm.findId(readerId);
        if (book != null) book.setQuantity(book.getQuantity() + 1);
        if (reader != null) reader.decrementBorrowedCount();
    }

    public void listRecords() {
        if (records.isEmpty()) System.out.println("Không có lịch sử mượn trả nào.");
        else {
            System.out.println("--- Danh sách lượt mượn ---");
            for (BorrowRecord r : records) System.out.println(r);
            System.out.println("--------------------------");
        }
    }

    public void listBorrowedByReader(String readerId) {
        List<BorrowRecord> borrowed = new ArrayList<>();
        for (BorrowRecord r : records) {
            if (r.getReaderId().equals(readerId) && r.getReturnDate() == null) {
                borrowed.add(r);
            }
        }

        if (borrowed.isEmpty()) {
            System.out.println("Độc giả có ID " + readerId + " hiện không mượn cuốn sách nào.");
        } else {
            System.out.println("--- Sách đang được mượn bởi độc giả ID " + readerId + " ---");
            for (BorrowRecord r : borrowed) {
                System.out.println(r);
            }
            System.out.println("--------------------------------------------------");
        }
    }

    public List<BorrowRecord> getBorrowRecordsByReader(String readerId) {
        List<BorrowRecord> result = new ArrayList<>();
        for (BorrowRecord r : records) {
            if (r.getReaderId().equals(readerId)) {
                result.add(r);
            }
        }
        return result;
    }

    public void displayBorrowHistory(String readerId, BookManager bm) {
        List<BorrowRecord> history = getBorrowRecordsByReader(readerId);

        if (history.isEmpty()) {
            System.out.println("Không có lịch sử mượn trả cho độc giả có ID " + readerId + ".");
        }else{
            System.out.println("--- Lịch sử mượn trả của độc giả ID " + readerId + " ---");
            System.out.println("----------------------------------------------------");
            System.out.printf("%-10s %-40s %-12s %-12s %-12s%n", "Book ID", "Tên sách", "Ngày mượn", "Hạn trả", "Ngày trả");
            System.out.println("----------------------------------------------------");
            for (BorrowRecord r : history) {
                Book book = bm.findId(r.getBookId());
                String bookTitle = (book != null) ? book.getTitle() : "Không tìm thấy sách.";
                String returnDateStr = (r.getReturnDate() != null) ? r.getReturnDate().toString() : "--";
                System.out.printf("%-10s %-40s %-12s %-12s %-12s%n", r.getBookId(), bookTitle, r.getBorrowDate(), r.getDueDate(), returnDateStr);
            }
            System.out.println("----------------------------------------------------");
        }
    }

    public List<BorrowRecord> getOverdueBooks() {
        LocalDate today = LocalDate.now();
        List<BorrowRecord> overdue = new ArrayList<>();
        for (BorrowRecord r : records) {
            if (r.getReturnDate() == null && r.getDueDate().isBefore(today)) {
                overdue.add(r);
            }
        }
        return overdue;
    }

    public void displayOverdueBooks(BookManager bm, ReaderManager rm) {
        List<BorrowRecord> overdue = getOverdueBooks();

        if (overdue.isEmpty()) {
            System.out.println("Không có sách nào đang bị quá hạn.");
        } else {
            System.out.println("\n--- Danh sách sách quá hạn ---");
            System.out.println("------------------------------------------------------------------");
            System.out.printf("%-10s %-40s %-15s %-12s %-15s%n", "Book ID", "Tên sách", "Độc giả ID", "Tên độc giả", "Ngày quá hạn");
            System.out.println("------------------------------------------------------------------");
            for (BorrowRecord record : overdue) {
                Book book = bm.findId(record.getBookId());
                Reader reader = rm.findId(record.getReaderId());
                String bookTitle = (book != null) ? book.getTitle() : "Không tìm thấy";
                String readerName = (reader != null) ? reader.getName() : "Không tìm thấy";
                long daysOverdue = ChronoUnit.DAYS.between(record.getDueDate(), LocalDate.now());
                System.out.printf("%-10s %-40s %-15s %-12s %-15s%n",
                        record.getBookId(), bookTitle, record.getReaderId(), readerName, record.getDueDate().plusDays(daysOverdue).toString());
            }
            System.out.println("------------------------------------------------------------------");
        }
    }

    public List<BorrowRecord> getRecords() {
        return records;
    }

    public void setRecords(List<BorrowRecord> records) {
        this.records = records;
    }
}
