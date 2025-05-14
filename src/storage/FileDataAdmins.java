package storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

public class FileDataAdmins {
    public static <K, V> void writeToFile(String fileName, Map<K, V> map) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(map);
        }catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> readFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Map<K, V>) ois.readObject();
        }catch (IOException | ClassNotFoundException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
            return null;
        }
    }
}