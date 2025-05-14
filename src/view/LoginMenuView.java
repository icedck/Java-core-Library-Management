package view;

import java.util.Scanner;
import controller.AdminManager;

public class LoginMenuView {
    private final AdminManager adminManager;

    public LoginMenuView(AdminManager adminManager) {
        this.adminManager = adminManager;
    }

    public boolean showLoginMenu(Scanner sc) {
        while (true) {
            System.out.println("\n--- Đăng ký / Đăng nhập ---");
            System.out.println("1. Đăng ký");
            System.out.println("2. Đăng nhập");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            String choiceStr = sc.nextLine().trim();

            int choice = -1;
            try {
                choice = Integer.parseInt(choiceStr);
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập số.");
                continue;
            }

            switch (choice) {
                case 1:
                    String registerUsername;
                    do {
                        System.out.print("Tên người dùng: ");
                        registerUsername = sc.nextLine().trim();
                        if (!registerUsername.matches("^[a-zA-Z0-9_]{3,16}$")) {
                            System.out.println("Lỗi: Tên chỉ chứa chữ, số, gạch dưới, độ dài 4-20 ký tự.");
                        }
                    }while (!registerUsername.matches("^[a-zA-Z0-9_]{3,16}$"));

                    String registerPassword;
                    String confirmPassword;
                    boolean isValid;
                    do {
                        isValid = true;
                        System.out.print("Mật khẩu: ");
                        registerPassword = sc.nextLine().trim();
                        if (registerPassword.length() < 6 || registerPassword.length() > 16) {
                            System.out.println("Lỗi: Mật khẩu phải từ 6-20 ký tự.");
                            isValid = false;
                        }else if (!registerPassword.matches(".*[a-zA-Z].*") || !registerPassword.matches(".*\\d.*")) {
                            System.out.println("Lỗi: Mật khẩu phải chứa ít nhất một chữ cái và một chữ số.");
                            isValid = false;
                        }

                        System.out.print("Nhập lại mật khẩu: ");
                        confirmPassword = sc.nextLine().trim();
                        if (!registerPassword.equals(confirmPassword)) {
                            System.out.println("Lỗi: Mật khẩu xác nhận không khớp.");
                            isValid = false;
                        }
                    }while (!isValid);

                    adminManager.register(registerUsername, registerPassword);
                    break;
                case 2:
                    System.out.print("Tên người dùng: ");
                    String loginUsername = sc.nextLine().trim();
                    System.out.print("Mật khẩu: ");
                    String loginPassword = sc.nextLine().trim();
                    if (adminManager.login(loginUsername, loginPassword)) {
                        return true;
                    }
                    break;
                case 0:
                    System.out.println("Thoát chương trình.");
                    return false;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }
}
