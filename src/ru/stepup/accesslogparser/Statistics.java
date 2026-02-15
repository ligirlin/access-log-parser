package ru.stepup.accesslogparser;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Statistics {
    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;

    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = null;
        this.maxTime = null;
    }

    public void addEntry(LogEntry entry) {
        this.totalTraffic += entry.getDataSize();

        if (minTime == null || entry.getDateTime().isBefore(minTime)) {
            minTime = entry.getDateTime();
        }
        if (maxTime == null || entry.getDateTime().isAfter(maxTime)) {
            maxTime = entry.getDateTime();
        }
    }

    public double getTrafficRate() {
        if (minTime == null || maxTime == null) return 0;
        long hours = ChronoUnit.HOURS.between(minTime, maxTime);
        if (hours == 0) hours = 1; // Защита от деления на 0, если лог за период < 1 часа
        return (double) totalTraffic / hours;
    }
}
