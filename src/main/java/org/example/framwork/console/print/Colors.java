package org.example.framwork.console.print;

import java.util.Map;

public class Colors {
    protected static String tempColor;
    protected static String tempEmoji;
    protected static String tempStatus;
    protected static final Map<String, String> colorsCode = Map.of(
            "red", "\033[1;31m",
            "green", "\033[1;32m",
            "yellow", "\033[1;33m",
            "blue", "\033[1;34m",
            "purple", "\033[1;35m",
            "cyan", "\033[1;36m",
            "white", "\033[1;37m",
            "black", "\033[1;30m",
            "reset", "\033[0m"

    );

    protected static final Map<String, String> emoji = Map.of(
            "info", "\uD83D\uDCAC",
            "warning", "\u26A0\uFE0F",
            "error", "\u274C",
            "success", "\u2705"
    );
}
