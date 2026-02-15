package ru.stepup.accesslogparser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogEntry {
    private final String ipAddress;
    private final LocalDateTime dateTime;
    private final HttpMethod method;
    private final String path;
    private final int responseCode;
    private final int dataSize;
    private final String referer;
    private final UserAgent userAgent;

    // Регулярное выражение для разбора строки лога
    // Группы: 1:IP, 4:DateTime, 5:Method, 6:Path, 8:Code, 9:Size, 10:Referer, 11:User-Agent
    private static final String LOG_PATTERN = "^(\\S+) (\\S+) (\\S+) \\[(.*?)\\] \"(\\S+) (\\S+) (\\S+)\" (\\d{3}) (\\d+|-)(?: \"(.*?)\" \"(.*?)\")?$";
    private static final Pattern PATTERN = Pattern.compile(LOG_PATTERN);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);

    public LogEntry(String line) {
        Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            this.ipAddress = matcher.group(1);

            // Парсинг даты и времени
            this.dateTime = LocalDateTime.parse(matcher.group(4), DATE_FORMATTER);

            // Парсинг HTTP метода (преобразуем строку в enum)
            this.method = HttpMethod.valueOf(matcher.group(5).toUpperCase());

            this.path = matcher.group(6);
            this.responseCode = Integer.parseInt(matcher.group(8));

            // Обработка размера данных: если "-", то 0, иначе число
            String sizeGroup = matcher.group(9);
            this.dataSize = sizeGroup.equals("-") ? 0 : Integer.parseInt(sizeGroup);

            this.referer = matcher.group(10);

            // Создание объекта UserAgent на основе строки
            this.userAgent = new UserAgent(matcher.group(11));
        } else {
            throw new RuntimeException("Ошибка парсинга строки лога. Неверный формат: " + line);
        }
    }

    // Геттеры для всех полей
    public String getIpAddress() {
        return ipAddress;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getDataSize() {
        return dataSize;
    }

    public String getReferer() {
        return referer;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }
}