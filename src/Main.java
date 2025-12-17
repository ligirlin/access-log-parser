import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int correctFilesCount = 0;

        while (true) {
            System.out.println("Введите путь к файлу: ");
            String filePath = scanner.nextLine();

            File file = new File(filePath);
            boolean fileExists = file.exists();
            boolean isFile = file.isFile();

            if (!fileExists) {
                System.out.println("Файл не существует");
                continue;
            }
            if (isFile) {
                System.out.println("Указан путь к папке, а не к файлу!");
                continue;
            }
            correctFilesCount++;
            System.out.println("Путь указан верно");
            System.out.println("Это файл номер " + correctFilesCount);
        }
    }
}