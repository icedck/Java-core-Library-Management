package view;

import java.util.List;
import java.util.Scanner;

import controller.AdminManager;
import controller.BookManager;
import controller.ReaderManager;
import controller.BorrowManager;
import model.Book;
import model.BorrowRecord;
import model.Reader;
import storage.FileHelper;

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookManager bookManager = new BookManager();
        ReaderManager readerManager = new ReaderManager();
        BorrowManager borrowManager = new BorrowManager();

        getLoadedRecords(bookManager, readerManager, borrowManager);

        BookMenuView bookMenuView = new BookMenuView();
        ReaderMenuView readerMenuView = new ReaderMenuView();
        BorrowMenuView borrowMenuView = new BorrowMenuView();
        AdminManager adminManager = new AdminManager();
        LoginMenuView loginMenuView = new LoginMenuView(adminManager);

        boolean exit = false;

        while (!exit) {
            boolean loggedIn = loginMenuView.showLoginMenu(sc);
            if (!loggedIn) {
                sc.close();
                return;
            }
            while (loggedIn) {
                System.out.println("\n==== QUẢN LÝ THƯ VIỆN ====");
                System.out.println("1. Quản lý sách");
                System.out.println("2. Quản lý độc giả");
                System.out.println("3. Mượn/Trả sách");
                System.out.println("4. Lưu dữ liệu quản lý");
                System.out.println("0. Đăng xuất");
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
                        bookMenuView.showBookMenu(sc, bookManager);
                        break;
                    case 2:
                        readerMenuView.showReaderMenu(sc, readerManager);
                        break;
                    case 3:
                        borrowMenuView.showBorrowMenu(sc, bookManager, readerManager, borrowManager);
                        break;
                    case 4:
                        FileHelper.writeToFile("data/books.dat", bookManager.getBooks());
                        FileHelper.writeToFile("data/readers.dat", readerManager.getReaders());
                        FileHelper.writeToFile("data/records.dat", borrowManager.getRecords());
                        System.out.println("Đã lưu.");
                        break;
                    case 0:
                        adminManager.logout();
                        loggedIn = false;
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ.");
                }
            }
        }
    }

    private static void getLoadedRecords(BookManager bookManager, ReaderManager readerManager,
            BorrowManager borrowManager) {
        List<Book> loadedBooks = FileHelper.readFromFile("data/books.dat");
        if (loadedBooks != null) {
            bookManager.setBooks(loadedBooks);
        } else {
            bookManager.setBooks(new java.util.ArrayList<>());
        }

        List<Reader> loadedReaders = FileHelper.readFromFile("data/readers.dat");
        if (loadedReaders != null) {
            readerManager.setReaders(loadedReaders);
        } else {
            readerManager.setReaders(new java.util.ArrayList<>());
        }

        List<BorrowRecord> loadedRecords = FileHelper.readFromFile("data/records.dat");
        if (loadedRecords != null) {
            borrowManager.setRecords(loadedRecords);
        } else {
            borrowManager.setRecords(new java.util.ArrayList<>());
        }
    }

}