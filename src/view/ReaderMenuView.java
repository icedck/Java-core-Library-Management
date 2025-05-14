package view;

import java.util.List;
import java.util.Scanner;
import controller.ReaderManager;
import model.Reader;

public class ReaderMenuView {
    public void showReaderMenu(Scanner sc, ReaderManager rm) {
        while (true) {
            System.out.println("\n--- Quản lý độc giả ---");
            System.out.println("1. Thêm độc giả");
            System.out.println("2. Xóa độc giả");
            System.out.println("3. Danh sách độc giả");
            System.out.println("4. Tìm kiếm độc giả");
            System.out.println("5. Cập nhật thông tin độc giả");
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
                    addReader(sc, rm);
                    break;
                case 2:
                    removeReader(sc, rm);
                    break;
                case 3:
                    rm.listAll();
                    break;
                case 4:
                    showReaderSearchMenu(sc, rm);
                    break;
                case 5:
                    showReaderUpdateMenu(sc, rm);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }

    private void addReader(Scanner sc, ReaderManager rm) {
        String id;
        do {
            System.out.println("ID: ");
            id = sc.nextLine().trim();
            if (id.isEmpty()) {
                System.out.println("Lỗi: ID không được để trống.");
            }else if (!id.matches("[a-zA-Z0-9]+$")){
                System.out.println("Lỗi: ID chỉ được chứa chữ và số.");
            }
        }while (!id.matches("[a-zA-Z0-9]+$") || id.isEmpty());

        String name;
        do {
            System.out.println("Tên: ");
            name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Lỗi: Tên không được để trống.");
            }
        }while (name.isEmpty());

        String email;
        do {
            System.out.println("Email: ");
            email = sc.nextLine().trim();
            if (email.isEmpty()) {
                System.out.println("Lỗi: Email không được để trống.");
            }else if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
                System.out.println("Lỗi: Định dạng email không hợp lệ.");
            }
        }while (email.isEmpty() || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"));

        String phone;
        do {
            System.out.println("Phone: ");
            phone = sc.nextLine().trim();
            if (phone.isEmpty()) {
                System.out.println("Lỗi: Số điện thoại không được để trống.");
            }else if (!phone.matches("^\\d{10,11}$")) {
                System.out.println("Lỗi: Số điện thoại phải là dãy số từ 10 đến 11 chữ số.");
            }
        }while (phone.isEmpty() || !phone.matches("^\\d{10,11}$"));
        rm.add(new Reader(id, name, email, phone));
        System.out.println("Đã thêm độc giả thành công.");
    }

    private void removeReader(Scanner sc, ReaderManager rm) {
        String id;
        do {
            System.out.println("Nhập ID độc giả cần xóa: ");
            id = sc.nextLine().trim();
            if (id.isEmpty()) {
                System.out.println("Lỗi: ID không được để trống.");
            }
        }while (id.isEmpty());
        rm.remove(id);
    }

    private void showReaderSearchMenu(Scanner sc, ReaderManager rm) {
        System.out.println("\n--- Tìm kiếm độc giả theo ---");
        System.out.println("1. Tên");
        System.out.println("0. Quay lại menu quản lý độc giả");
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
                System.out.print("Nhập tên độc giả cần tìm: ");
                String name = sc.nextLine();
                List<Reader> results = rm.searchByName(name);
                if (results.isEmpty())
                    System.out.println("Không tìm thấy độc giả nào có tên chứa '" + name + "'.");
                else {
                    System.out.println("--- Kết quả tìm kiếm theo tên ---");
                    for (Reader reader : results) {
                        System.out.println(reader);
                    }
                    System.out.println("----------------------------------");
                }
                break;
            case 0:
                return;
            default:
                System.out.println("Lựa chọn không hợp lệ.");
        }
    }

    private void showReaderUpdateMenu(Scanner sc, ReaderManager rm) {
        String idToUpdate;
        do {
            System.out.print("Nhập ID độc giả cần cập nhật: ");
            idToUpdate = sc.nextLine().trim();
            if (idToUpdate.isEmpty()) {
                System.out.println("Lỗi: ID không được để trống.");
            }
        } while (idToUpdate.isEmpty());
        Reader existingReader = rm.findId(idToUpdate);
        if (existingReader == null) {
            System.out.println("Không tìm thấy độc giả có ID: " + idToUpdate);
            return;
        }

        System.out.println("--- Nhập thông tin mới (để trống nếu không muốn thay đổi) ---");
        System.out.print("Tên (" + existingReader.getName() + "): ");
        String name = sc.nextLine().trim();
        if (name.isEmpty())
            name = existingReader.getName();

        String email = existingReader.getEmail();
        System.out.print("Email (" + email + "): ");
        String newEmail = sc.nextLine().trim();
        if (!newEmail.isEmpty()) {
            if (!newEmail.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                System.out.println("Lỗi: Định dạng email không hợp lệ. Giữ nguyên giá trị cũ.");
            } else {
                email = newEmail;
            }
        }

        String phone = existingReader.getPhone();
        System.out.print("Phone (" + phone + "): ");
        String newPhone = sc.nextLine().trim();
        if (!newPhone.isEmpty()) {
            if (!newPhone.matches("^\\d{10,11}$")) {
                System.out.println("Lỗi: Số điện thoại phải là dãy số từ 10 đến 11 chữ số. Giữ nguyên giá trị cũ.");
            } else {
                phone = newPhone;
            }
        }

        Reader updatedReader = new Reader(idToUpdate, name, email, phone);
        rm.updateReader(updatedReader);
    }
}