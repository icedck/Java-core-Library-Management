package view;

import java.time.LocalDate;
import java.util.Scanner;
import controller.BookManager;
import controller.ReaderManager;
import controller.BorrowManager;
import exception.BorrowException;

public class BorrowMenuView {
    public void showBorrowMenu(Scanner sc, BookManager bm, ReaderManager rm, BorrowManager brm) {
        while (true) {
            System.out.println("\n--- Mượn/Trả sách ---");
            System.out.println("1. Mượn sách");
            System.out.println("2. Trả sách");
            System.out.println("3. Danh sách lượt mượn");
            System.out.println("4. Xem sách đang mượn của độc giả");
            System.out.println("5. Xem lịch sử mượn trả của độc giả");
            System.out.println("6. Xem sách quá hạn");
            System.out.println("0. Quay lại menu chính");
            System.out.print("Chọn: ");
            String choiceStr = sc.nextLine();
            int choice = -1;
            try {
                choice = Integer.parseInt(choiceStr);
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập số.");
                continue;
            }

            switch (choice) {
                case 1:
                    borrowBook(sc, bm, rm, brm);
                    break;
                case 2:
                    returnBook(sc, bm, rm, brm);
                    break;
                case 3:
                    brm.listRecords();
                    break;
                case 4:
                    listBorrowedByReader(sc, brm);
                    break;
                case 5:
                    displayBorrowHistory(sc, bm, brm);
                    break;
                case 6:
                    brm.displayOverdueBooks(bm, rm);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }

    private void borrowBook(Scanner sc, BookManager bm, ReaderManager rm, BorrowManager brm) {
        String bid;
        do {
            System.out.print("ID sách: ");
            bid = sc.nextLine().trim();
            if (bid.isEmpty()) {
                System.out.println("Lỗi: ID sách không được để trống.");
            }
        } while (bid.isEmpty());

        String rid;
        do {
            System.out.print("ID độc giả: ");
            rid = sc.nextLine().trim();
            if (rid.isEmpty()) {
                System.out.println("Lỗi: ID độc giả không được để trống.");
            }
        } while (rid.isEmpty());

        LocalDate dueDate = null;
        boolean validDate = false;
        do {
            System.out.print("Ngày hẹn trả (yyyy-MM-dd): ");
            String dueDateStr = sc.nextLine().trim();
            if (dueDateStr.isEmpty()) {
                System.out.println("Lỗi: Ngày hẹn trả không được để trống.");
            } else {
                try {
                    dueDate = LocalDate.parse(dueDateStr);
                    if (dueDate.isBefore(LocalDate.now())) {
                        System.out.println("Lỗi: Ngày hẹn trả không được là ngày trong quá khứ.");
                    } else {
                        validDate = true;
                    }
                } catch (java.time.format.DateTimeParseException e) {
                    System.out.println("Lỗi: Định dạng ngày không hợp lệ (yyyy-MM-dd).");
                }
            }
        } while (!validDate);

        try {
            brm.borrow(bm, rm, bid, rid, dueDate);
            System.out.println("Đã mượn thành công. Hạn trả: " + dueDate);
        } catch (BorrowException e) {
            System.out.println("Lỗi mượn sách: " + e.getMessage());
        }
    }

    private void returnBook(Scanner sc, BookManager bm, ReaderManager rm, BorrowManager brm) {
        String bid;
        do {
            System.out.print("ID sách trả: ");
            bid = sc.nextLine().trim();
            if (bid.isEmpty()) {
                System.out.println("Lỗi: ID sách không được để trống.");
            }
        } while (bid.isEmpty());

        String rid;
        do {
            System.out.print("ID độc giả trả: ");
            rid = sc.nextLine().trim();
            if (rid.isEmpty()) {
                System.out.println("Lỗi: ID độc giả không được để trống.");
            }
        } while (rid.isEmpty());

        try {
            brm.returnBook(bm, rm, bid, rid);
            System.out.println("Đã trả sách thành công.");
        } catch (BorrowException e) {
            System.out.println("Lỗi trả sách: " + e.getMessage());
        }
    }

    private void listBorrowedByReader(Scanner sc, BorrowManager brm) {
        String readerId;
        do {
            System.out.print("Nhập ID độc giả để xem sách đang mượn: ");
            readerId = sc.nextLine().trim();
            if (readerId.isEmpty()) {
                System.out.println("Lỗi: ID độc giả không được để trống.");
            }
        } while (readerId.isEmpty());

        brm.listBorrowedByReader(readerId);
    }

    private void displayBorrowHistory(Scanner sc, BookManager bm, BorrowManager brm) {
        String readerId;
        do {
            System.out.print("Nhập ID độc giả để xem lịch sử mượn trả: ");
            readerId = sc.nextLine().trim();
            if (readerId.isEmpty()) {
                System.out.println("Lỗi: ID độc giả không được để trống.");
            }
        }while (readerId.isEmpty());
        brm.displayBorrowHistory(readerId, bm);
    }
}