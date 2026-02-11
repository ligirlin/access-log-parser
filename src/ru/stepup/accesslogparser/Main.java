package ru.stepup.accesslogparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
                System.out.println("Указанный файл не существует.");
                continue;
            } else {
                if (!isFile) {
                    System.out.println("Указанный путь является путём к папке, а не к файлу.");
                    continue;
                } else {
                    correctFilesCount++;
                    System.out.println("Путь указан верно");
                    System.out.println("Это файл номер " + correctFilesCount);

                    // Начинаем чтение и анализ файла
                    analyzeFile(filePath);
                }
            }
        }
    }

    private static void analyzeFile(String path) {
        int totalLines = 0;
        int maxLength = 0;
        int minLength = Integer.MAX_VALUE;

        try (FileReader fileReader = new FileReader(path);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                int length = line.length();

                // Проверка на превышение длины 1024 символа
                if (length > 1024) {
                    throw new RuntimeException("В файле обнаружена строка длиннее 1024 символов (" + length + " симв.)");
                }

                totalLines++;

                if (length > maxLength) {
                    maxLength = length;
                }

                if (length < minLength) {
                    minLength = length;
                }
            }

            // Вывод статистики после успешного прочтения всех строк
            System.out.println("Анализ завершен успешно:");
            System.out.println("Общее количество строк: " + totalLines);
            System.out.println("Длина самой длинной строки: " + maxLength);
            // Если файл был пустой, minLength останется MAX_VALUE, обработаем это
            System.out.println("Длина самой короткой строки: " + (totalLines > 0 ? minLength : 0));
            System.out.println("-----------------------------------");

        } catch (Exception ex) {
            // Печать стека ошибок согласно заданию
            ex.printStackTrace();
        }
    }
}
