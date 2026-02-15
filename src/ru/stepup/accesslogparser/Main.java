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
            System.out.println("Введите путь к файлу (или 'exit' для выхода): ");
            String filePath = scanner.nextLine();

            if (filePath.equalsIgnoreCase("exit")) {
                break;
            }

            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Указанный файл не существует.");
                continue;
            }
            if (file.isDirectory()) {
                System.out.println("Указанный путь является путём к папке, а не к файлу.");
                continue;
            }

            correctFilesCount++;
            System.out.println("Путь указан верно. Это файл номер " + correctFilesCount);

            try {
                analyzeFile(filePath);
            } catch (RuntimeException e) {
                System.out.println("Ошибка при анализе файла: " + e.getMessage());
                // Можно убрать вывод стека, если не хотите прерывать цикл while
                e.printStackTrace();
            }
        }
    }

    private static void analyzeFile(String path) {
        Statistics statistics = new Statistics();

        try (FileReader fileReader = new FileReader(path); BufferedReader reader = new BufferedReader(fileReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Пропускаем пустые строки, чтобы не ловить StringIndexOutOfBoundsException
                if (line.trim().isEmpty()) {
                    continue;
                }

                if (line.length() > 1024) {
                    throw new RuntimeException("Строка в файле длиннее 1024 символов!");
                }

                // Создаем объект записи. Если формат неверный, LogEntry сам выбросит исключение
                LogEntry entry = new LogEntry(line);

                // Добавляем запись в статистику
                statistics.addEntry(entry);
            }

            // Вывод итоговых данных
            System.out.println("" +
                    "Статистика для файла: " + path + " ---");
            System.out.println("Средний объём трафика в час: " + statistics.getTrafficRate());
            System.out.println("--------------------------------------------------");

        } catch (Exception ex) {
            // Оборачиваем любое исключение (IO или парсинг) в RuntimeException
            throw new RuntimeException(ex);
        }
    }
}