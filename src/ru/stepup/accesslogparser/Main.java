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
            analyzeFile(filePath);
        }
    }

    private static void analyzeFile(String path) {
        int totalLines = 0;
        int yandexBots = 0;
        int googleBots = 0;

        try (FileReader fileReader = new FileReader(path); BufferedReader reader = new BufferedReader(fileReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                int length = line.length();
                if (length > 1024) {
                    throw new RuntimeException("Строка длиннее 1024 символов!");
                }
                totalLines++;
                String[] quotes = line.split("\"");
                if (quotes.length >= 6) {
                    String userAgent = quotes[5];
                    int openBracket = userAgent.indexOf("(");
                    int closeBracket = userAgent.indexOf(")");

                    if (openBracket != -1 && closeBracket != -1) {
                        String firstBrackets = userAgent.substring(openBracket + 1, closeBracket);

                        String[] parts = firstBrackets.split(";");
                        if (parts.length >= 2) {

                            String fragment = parts[1].trim();


                            String botName = fragment.split("/")[0];


                            if (botName.equalsIgnoreCase("YandexBot")) {
                                yandexBots++;
                            } else if (botName.equalsIgnoreCase("Googlebot")) {
                                googleBots++;
                            }
                        }
                    }
                }
            }

            System.out.println("Всего запросов: " + totalLines);
            System.out.println("Запросов от YandexBot: " + yandexBots + " (доля: " + (double) yandexBots / totalLines + ")");
            System.out.println("Запросов от Googlebot: " + googleBots + " (доля: " + (double) googleBots / totalLines + ")");
            System.out.println("-----------------------------------");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
