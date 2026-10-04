package me.gamercoder215.mobchip.abstraction;

import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;

public class ChipUtilFactory {
    private static ChipUtil instance = null;
    private ChipUtilFactory() {
        throw new RuntimeException("Utility class");
    }

    public static ChipUtil getChipUtil() {
        if (instance == null) {
            instance = getWrapper();
        }
        return instance;
    }

    /**
     * @throws IllegalStateException if the server version is not supported
     */
    private static String bukkitToCraftBukkit() {
        String bukkit = Bukkit.getServer().getBukkitVersion().split("-")[0];
        if (bukkit.startsWith("1.")) {
            bukkit = bukkit.substring(2);
        }
        String[] parts = bukkit.split("\\.");
        int major = Integer.parseInt(parts[0]);
        int minor = 0;
        if (parts.length > 1) {
            minor = Integer.parseInt(parts[1]);
        }
        if (major == 20) {
            switch (minor) {
                case 0:
                case 1:
                    return "1_20_R1";
                case 2:
                    return "1_20_R2";
                case 3:
                case 4:
                    return "1_20_R3";
                case 5:
                case 6:
                    return "1_20_R4";
            }
        } else if (major == 21) {
            switch (minor) {
                case 0:
                case 1:
                    return "1_21_R1";
                case 2:
                case 3:
                    return "1_21_R2";
                case 4:
                    return "1_21_R3";
                case 5:
                    return "1_21_R4";
                case 6:
                case 7:
                case 8:
                    return "1_21_R5";
                case 9:
                case 10:
                    return "1_21_R6";
                case 11:
                    return "1_21_R7";
            }
        } else if (major >= 26) {
            switch (minor) {
                case 1:
                    return "26_1";
                case 2:
                    return "26_2";
                case 3:
                default: // optimism
                    return "26_3";
            }
        }
        throw new IllegalStateException("Unsupported version: " + bukkit);
    }

    private static String getServerVersion() {
        try {
            return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
        } catch (IndexOutOfBoundsException e) {
            // Using CraftBukkit Relocation
            return bukkitToCraftBukkit();
        }
    }

    private static ChipUtil getWrapper() {
        String pkg = ChipUtil.class.getPackage().getName() + ".v" + getServerVersion();
        try {
            Constructor<? extends ChipUtil> constr = Class.forName(pkg + ".ChipUtil" + getServerVersion())
                    .asSubclass(ChipUtil.class)
                    .getDeclaredConstructor();
            constr.setAccessible(true);
            return constr.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Invalid Version: " + getServerVersion() + " (Could not load " + pkg + ".ChipUtil" + getServerVersion() + ")", e);
        }
    }
}
