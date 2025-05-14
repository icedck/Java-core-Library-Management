package controller;

import java.util.ArrayList;
import java.util.List;

import model.Reader;

public class ReaderManager implements Manageable<Reader> {
    public List<Reader> readers = new ArrayList<>();

    @Override
    public void add(Reader reader) {
        if (findId(reader.getId()) == null) {
            readers.add(reader);
            System.out.println("Đã thêm độc giả: " + reader.getName() + " (ID: " + reader.getId() + ")");
        } else {
            System.out.println("Lỗi: Độc giả có ID " + reader.getId() + " đã tồn tại.");
        }    }

    @Override
    public Reader findId(String id) {
        for (Reader reader : readers) {
            if (reader.getId().equals(id)) {
                return reader;
            }
        }
        return null;
    }

    @Override
    public void listAll() {
        if (readers.isEmpty()) System.out.println("Không có độc giả nào.");
        else {
            System.out.println("--- Danh sách tất cả độc giả ---");
            for (Reader r : readers) System.out.println(r);
            System.out.println("----------------------------");
        }
    }

    public List<Reader> searchByName(String name) {
        List<Reader> result = new ArrayList<>();
        for (Reader reader : readers) {
            if (reader.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(reader);
            }
        }
        return result;
    }

    @Override
    public void remove(String id) {
        boolean removed = false;
        for (Reader r : readers) {
            if (r.getId().equals(id)) {
                readers.remove(r);
                removed = true;
                break;
            }
        }
        if (removed) {
            System.out.println("Đã xóa độc giả có ID: " + id);
        } else {
            System.out.println("Không tìm thấy độc giả có ID: " + id);
        }    
    }

    public void updateReader(Reader updatedReader) {
        for (int i = 0; i < readers.size(); i++) {
            if (readers.get(i).getId().equals(updatedReader.getId())) {
                readers.set(i, updatedReader);
                System.out.println("Đã cập nhật thông tin độc giả có ID: " + updatedReader.getId());
                return;
            }
        }
        System.out.println("Không tìm thấy độc giả có ID: " + updatedReader.getId());
    }

    public List<Reader> getReaders() {
        return readers;
    }

    public void setReaders(List<Reader> readers) {
        this.readers = readers;
    }    
}
