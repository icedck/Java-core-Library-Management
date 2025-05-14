package view;

import java.util.List;
import java.util.Scanner;
import controller.BookManager;
import model.Book;
import model.Textbook;
import model.Novel;
import model.Magazine;

public class BookMenuView {
    public void showBookMenu(Scanner sc, BookManager bm) {
        while (true) {
            System.out.println("\n--- Quản lý sách ---");
            System.out.println("1. Thêm sách");
            System.out.println("2. Xóa sách");
            System.out.println("3. Danh sách sách");
            System.out.println("4. Tìm kiếm sách");
            System.out.println("5. Cập nhật thông tin sách");
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
                    addBook(sc, bm);
                    break;
                case 2:
                    removeBook(sc, bm);
                    break;
                case 3:
                    bm.listAll();
                    break;
                case 4:
                    showBookSearchMenu(sc, bm);
                    break;
                case 5:
                    showBookUpdateMenu(sc, bm);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }

    private void addBook(Scanner sc, BookManager bm) {
        System.out.println("Loại sách: 1.Textbook 2.Novel 3.Magazine");
        String typeStr;
        int type = -1;
        do{
            System.out.print("Nhập loại sách: ");
            typeStr = sc.nextLine().trim();
            if (!typeStr.matches("[1-3]")){
                System.out.println("Lỗi: Vui lòng nhập số từ 1 đến 3.");
            }else {
                type = Integer.parseInt(typeStr);
            }
        }while (type == -1);

        String id;
        do {
            System.out.print("ID: ");
            id = sc.nextLine().trim();
            if (id.isEmpty()) {
                System.out.println("Lỗi: ID không được để trống.");
            } else if (!id.matches("^[a-zA-Z0-9]+$")) {
                System.out.println("Lỗi: ID chỉ được chứa chữ và số.");
            }
        } while (id.isEmpty() || !id.matches("^[a-zA-Z0-9]+$"));

        String title;
        do {
            System.out.print("Tên: ");
            title = sc.nextLine().trim();
            if (title.isEmpty()) {
                System.out.println("Lỗi: Tên không được để trống.");
            }
        } while (title.isEmpty());

        String author;
        do {
            System.out.print("Tác giả: ");
            author = sc.nextLine().trim();
            if (author.isEmpty()) {
                System.out.println("Lỗi: Tác giả không được để trống.");
            }
        } while (author.isEmpty());

        String category;
        do {
            System.out.print("Thể loại: ");
            category = sc.nextLine().trim();
            if (category.isEmpty()) {
                System.out.println("Lỗi: Thể loại không được để trống.");
            }
        } while (category.isEmpty());

        int qty = -1;
        do {
            System.out.print("Số lượng: ");
            String qtyStr = sc.nextLine().trim();
            try {
                qty = Integer.parseInt(qtyStr);
                if (qty < 0) {
                    System.out.println("Lỗi: Số lượng phải là số không âm.");
                    qty = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Số lượng phải là số nguyên.");
            }
        } while (qty == -1);

        Book b = null;
        if (type == 1) {
            System.out.print("Môn học: ");
            String subj = sc.nextLine().trim();
            System.out.print("Cấp độ: ");
            String lvl = sc.nextLine().trim();
            b = new Textbook(id, title, author, category, qty, subj, lvl);
        } else if (type == 2) {
            System.out.print("Thể loại tiểu thuyết: ");
            String genre = sc.nextLine().trim();
            String translatedStr;
            boolean translated = false;
            do {
                System.out.print("Đã dịch? (true/false): ");
                translatedStr = sc.nextLine().trim().toLowerCase();
                if (!translatedStr.equals("true") && !translatedStr.equals("false")) {
                    System.out.println("Lỗi: Vui lòng nhập 'true' hoặc 'false'.");
                } else {
                    translated = Boolean.parseBoolean(translatedStr);
                }
            } while (!translatedStr.equals("true") && !translatedStr.equals("false"));
            b = new Novel(id, title, author, category, qty, genre, translated);
        } else if (type == 3) {
            int issue = -1;
            do {
                System.out.print("Số phát hành: ");
                String issueStr = sc.nextLine().trim();
                try {
                    issue = Integer.parseInt(issueStr);
                    if (issue <= 0) {
                        System.out.println("Lỗi: Số phát hành phải lớn hơn 0.");
                        issue = -1;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Lỗi: Số phát hành phải là số nguyên.");
                }
            } while (issue == -1);

            System.out.print("Tháng phát hành: "); String mon = sc.nextLine().trim();
            b = new Magazine(id, title, author, category, qty, issue, mon);
        }
        if (b != null) {
            bm.add(b);
        }
    }

    private void removeBook(Scanner sc, BookManager bm) {
        String idRemove;
        do {
            System.out.print("Nhập ID sách cần xóa: ");
            idRemove = sc.nextLine().trim();
            if (idRemove.isEmpty()) {
                System.out.println("Lỗi: ID không được để trống.");
            }
        } while (idRemove.isEmpty());
        bm.remove(idRemove);
    }

    private void showBookSearchMenu(Scanner sc, BookManager bm) {
        System.out.println("\n--- Tìm kiếm sách theo ---");
        System.out.println("1. Tên sách");
        System.out.println("2. Tác giả");
        System.out.println("3. Thể loại");
        System.out.println("0. Quay lại menu quản lý sách");
        System.out.print("Chọn: ");
        String choiceStr = sc.nextLine();
        int choice = -1;
        try {
            choice = Integer.parseInt(choiceStr);
        } catch (NumberFormatException e) {
            System.out.println("Lỗi: Vui lòng nhập số.");
            return;
        }

        switch (choice) {
            case 1:
                System.out.print("Nhập tên sách cần tìm: ");
                String title = sc.nextLine();
                List<Book> results = bm.searchByTitle(title);
                if (results.isEmpty())
                    System.out.println("Không tìm thấy sách nào có tên chứa '" + title + "'.");
                else {
                    System.out.println("--- Kết quả tìm kiếm theo tên ---");
                    for (Book book : results) {
                        System.out.println(book);
                    }
                    System.out.println("----------------------------------");
                }
                break;
            case 2:
                System.out.print("Nhập tên tác giả cần tìm: ");
                String author = sc.nextLine();
                List<Book> resultsAuthor = bm.searchByAuthor(author);
                if (resultsAuthor.isEmpty())
                    System.out.println("Không tìm thấy sách nào của tác giả chứa '" + author + "'.");
                else {
                    System.out.println("--- Kết quả tìm kiếm theo tác giả ---");
                    for (Book book : resultsAuthor) {
                        System.out.println(book);
                    }
                    System.out.println("-------------------------------------");
                }
                break;
            case 3:
                System.out.print("Nhập thể loại sách cần tìm: ");
                String category = sc.nextLine();
                List<Book> resultsCateory = bm.searchByCategory(category);
                if (resultsCateory.isEmpty())
                    System.out.println("Không tìm thấy sách nào thuộc thể loại '" + category + "'.");
                else {
                    System.out.println("--- Kết quả tìm kiếm theo thể loại ---");
                    for (Book book : resultsCateory) {
                        System.out.println(book);
                    }
                    System.out.println("--------------------------------------");
                }
                break;
            case 0:
                return;
            default:
                System.out.println("Lựa chọn không hợp lệ.");
        }
    }

    private void showBookUpdateMenu(Scanner sc, BookManager bm) {
        String idToUpdate;
        do {
            System.out.print("Nhập ID sách cần cập nhật: ");
            idToUpdate = sc.nextLine().trim();
            if (idToUpdate.isEmpty()) {
                System.out.println("Lỗi: ID không được để trống.");
            }
        } while (idToUpdate.isEmpty());

        Book existingBook = bm.findId(idToUpdate);
        if (existingBook == null) {
            System.out.println("Không tìm thấy sách có ID: " + idToUpdate);
            return;
        }

        System.out.println("--- Nhập thông tin mới (để trống nếu không muốn thay đổi) ---");
        System.out.print("Tên (" + existingBook.getTitle() + "): ");
        String title = sc.nextLine().trim();
        if (title.isEmpty())
            title = existingBook.getTitle();

        System.out.print("Tác giả (" + existingBook.getAuthor() + "): ");
        String author = sc.nextLine();
        if (author.isEmpty())
            author = existingBook.getAuthor();

        System.out.print("Thể loại (" + existingBook.getCategory() + "): ");
        String category = sc.nextLine();
        if (category.isEmpty())
            category = existingBook.getCategory();

        int quantity = existingBook.getQuantity();
        System.out.println("Số lượng (" + existingBook.getQuantity() + "): ");
        String qtyStr = sc.nextLine().trim();
        if (!qtyStr.isEmpty()) {
            try {
                int newQuantity = Integer.parseInt(qtyStr);
                if (newQuantity < 0) {
                    System.out.println("Lỗi: Số lượng phải là số không âm. Giữ nguyên giá trị cũ.");
                } else {
                    quantity = newQuantity;
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Số lượng phải là số nguyên. Giữ nguyên giá trị cũ.");
            }
        }

        Book updatedBook = null;
        if (existingBook instanceof Textbook) {
            Textbook tb = (Textbook) existingBook;
            System.out.print("Môn học (" + tb.getSubject() + "): ");
            String subject = sc.nextLine().trim();
            if (subject.isEmpty())
                subject = tb.getSubject();
            System.out.print("Cấp độ (" + tb.getLevel() + "): ");
            String level = sc.nextLine().trim();
            if (level.isEmpty())
                level = tb.getLevel();
            updatedBook = new Textbook(idToUpdate, title, author, category, quantity, subject, level);
        } else if (existingBook instanceof Novel) {
            Novel nv = (Novel) existingBook;
            System.out.print("Thể loại tiểu thuyết (" + nv.getGenre() + "): ");
            String genre = sc.nextLine().trim();
            if (genre.isEmpty())
                genre = nv.getGenre();
            System.out.print("Đã dịch? (" + nv.isTranslated() + " - true/false): ");
            String translatedStr = sc.nextLine().trim().toLowerCase();
            boolean isTranslated = nv.isTranslated();
            if (!translatedStr.isEmpty()) {
                if (!translatedStr.equals("true") && !translatedStr.equals("false")) {
                    System.out.println("Lỗi: Vui lòng nhập 'true' hoặc 'false'. Giữ nguyên giá trị cũ.");
                }else {
                    isTranslated = Boolean.parseBoolean(translatedStr);
                }
            }
            updatedBook = new Novel(idToUpdate, title, author, category, quantity, genre, isTranslated);
        } else if (existingBook instanceof Magazine) {
            int issueNumber = ((Magazine) existingBook).getIssueNumber();
            Magazine mg = (Magazine) existingBook;
            System.out.print("Số phát hành (" + mg.getIssueNumber() + "): ");
            String issueStr = sc.nextLine().trim();
            if (!issueStr.isEmpty()) {
                try {
                    int newIssueNum = Integer.parseInt(issueStr);
                    if (newIssueNum <= 0) {
                        System.out.println("Lỗi: Số phát hành phải lớn hơn 0. Giữ nguyên giá trị cũ.");
                    } else {
                        issueNumber = newIssueNum;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Lỗi: Số phát hành phải là số nguyên. Giữ nguyên giá trị cũ.");
                }
            }

            System.out.print("Tháng phát hành (" + mg.getReleaseMonth() + "): ");
            String releaseMonth = sc.nextLine().trim();
            if (releaseMonth.isEmpty())
                releaseMonth = mg.getReleaseMonth();
            updatedBook = new Magazine(idToUpdate, title, author, category, quantity, issueNumber, releaseMonth);
        }

        if (updatedBook != null) {
            bm.updateBook(updatedBook);
        } else {
            System.out.println("Lỗi: Không xác định được loại sách để cập nhật.");
        }
    }
}