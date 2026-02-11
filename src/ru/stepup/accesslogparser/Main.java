package ru.stepup.accesslogparser;

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

            // 1. Проверяем, существует ли объект по этому пути
            boolean fileExists = file.exists();

            // 2. Проверяем, является ли объект именно ФАЙЛОМ
            boolean isFile = file.isFile();

            if (!fileExists) {
                // Если файл вообще не найден
                System.out.println("Указанный файл не существует.");
                continue;
            } else {
                // Если объект существует, проверяем, файл это или папка
                if (!isFile) {
                    // Если isFile == false, значит это папка (или другое устройство)
                    System.out.println("Указанный путь является путём к папке, а не к файлу.");
                    continue;
                } else {
                    // Если это именно файл (isFile == true)
                    correctFilesCount++;
                    System.out.println("Путь указан верно");
                    System.out.println("Это файл номер " + correctFilesCount);
                }
            }
        }
    }
}