package ru.stepup.accesslogparser;

public class UserAgent {
    private final String os;
    private final String browser;

    public UserAgent(String userAgentString) {
        String detectedOs = "Other";
        if (userAgentString.contains("Windows")) detectedOs = "Windows";
        else if (userAgentString.contains("Macintosh") || userAgentString.contains("Mac OS")) detectedOs = "macOS";
        else if (userAgentString.contains("Linux")) detectedOs = "Linux";
        this.os = detectedOs;

        String detectedBrowser = "Other";
        if (userAgentString.contains("Edge") || userAgentString.contains("Edg")) detectedBrowser = "Edge";
        else if (userAgentString.contains("Firefox")) detectedBrowser = "Firefox";
        else if (userAgentString.contains("Chrome") && !userAgentString.contains("Edg") && !userAgentString.contains("OPR"))
            detectedBrowser = "Chrome";
        else if (userAgentString.contains("OPR") || userAgentString.contains("Opera")) detectedBrowser = "Opera";
        this.browser = detectedBrowser;
    }

    public String getOs() {
        return os;
    }

    public String getBrowser() {
        return browser;
    }
}