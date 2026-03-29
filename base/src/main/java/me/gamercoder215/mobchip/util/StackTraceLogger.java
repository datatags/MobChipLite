package me.gamercoder215.mobchip.util;

import org.bukkit.Bukkit;

public class StackTraceLogger {
    private StackTraceLogger() { throw new RuntimeException("Utility class"); }

    public static void printStackTrace(Throwable e) {
        Bukkit.getLogger().severe(e.getClass().getName() + ": " + e.getMessage());
        for (StackTraceElement s : e.getStackTrace()) {
            Bukkit.getLogger().severe("    " + s);
        }
        if (e.getCause() != null) {
            Bukkit.getLogger().severe("Caused by:");
            printStackTrace(e.getCause());
        }
    }
}
