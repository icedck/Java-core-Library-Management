package controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import storage.FileDataAdmins;

public class AdminManager implements Serializable {
    private Map<String, String> admins;
    private boolean isLoggedIn = false;
    private String loggedInUsername = null;
    private static final String ADMIN_FILE_PATH = "data/admins.dat";

    public AdminManager() {
        loadAdmins();
        if (admins == null || admins.isEmpty()) {
            admins = new HashMap<>();
            admins.put("admin", "password");
            saveAdmins();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadAdmins() {
        admins = FileDataAdmins.readFromFile(ADMIN_FILE_PATH);
        if (admins == null) {
            admins = new HashMap<>();
        }
    }

    private void saveAdmins() {
        FileDataAdmins.writeToFile(ADMIN_FILE_PATH, admins);
    }

    public boolean register(String username, String password) {
        if (admins.containsKey(username)) {
            System.out.println("Lỗi: Tên người dùng đã tồn tại.");
            return false;
        }
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            System.out.println("Lỗi: Tên người dùng và mật khẩu không được để trống.");
            return false;
        }
        admins.put(username, password);
        saveAdmins(); // Lưu lại sau khi đăng ký
        System.out.println("Đăng ký thành công!");
        return true;
    }

    public boolean login(String username, String password) {
        if (admins != null && admins.containsKey(username) && admins.get(username).equals(password)) {
            System.out.println("Đăng nhập thành công!");
            isLoggedIn = true;
            loggedInUsername = username;
            return true;
        } else {
            System.out.println("Đăng nhập thất bại. Tên người dùng hoặc mật khẩu không đúng.");
            isLoggedIn = false;
            loggedInUsername = null;
            return false;
        }
    }

    public void logout() {
        isLoggedIn = false;
        loggedInUsername = null;
        System.out.println("Đã đăng xuất.");
    }

    public boolean isAdminLoggedIn() {
        return isLoggedIn;
    }

    public String getLoggedInUsername() {
        return loggedInUsername;
    }
}