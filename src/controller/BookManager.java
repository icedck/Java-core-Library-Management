package controller;

import java.util.ArrayList;
import java.util.List;

import model.Book;

public class BookManager implements Manageable<Book> {
    public List<Book> books = new ArrayList<>();

    @Override
    public void add(Book book) {
        if (findId(book.getId()) == null) {
            books.add(book);
            System.out.println("Đã thêm sách: " + book.getTitle() + " (ID: " + book.getId() + ")");
        } else {
            System.out.println("Lỗi: Sách có ID " + book.getId() + " đã tồn tại.");
        }    }

    @Override
    public Book findId(String id) {
        for (Book book : books) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }

    public List<Book> searchByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }
    
    public List<Book> searchByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }
    
    public List<Book> searchByCategory(String category) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getCategory().toLowerCase().equals(category.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public void listAll() {
        if (books.isEmpty()) System.out.println("Không có sách nào trong thư viện.");
        else {
            System.out.println("--- Danh sách tất cả sách ---");
            for (Book b : books) System.out.println(b);
            System.out.println("---------------------------");
        }
    }

    @Override
    public void remove(String id) {
        boolean removed = false;
        for (Book b : books) {
            if (b.getId().equals(id)) {
                books.remove(b);
                removed = true;
                break;
            }
        }
        if (removed) {
            System.out.println("Đã xóa sách có ID: " + id);
        } else {
            System.out.println("Không tìm thấy sách có ID: " + id);
        }    
    }

    public void updateBook(Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(updatedBook.getId())) {
                books.set(i, updatedBook);
                System.out.println("Đã cập nhật thông tin sách có ID: " + updatedBook.getId());
                return;
            }
        }
        System.out.println("Không tìm thấy sách có ID: " + updatedBook.getId());
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }
}
