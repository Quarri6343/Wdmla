package com.gtnewhorizons.wdmla.api.ui;

public final class ColorPalette {

    public static final int NORMAL = 0xFFA0A0A0;
    public static final int INFO = 0xFFFFFF;
    public static final int TITLE = 0xFFFFFF;
    public static final int SUCCESS = 0xFF55FF55;
    public static final int WARNING = 0xFFFFF3CD;
    public static final int DANGER = 0xFFFF5555;
    public static final int FAILURE = 0xFFAA0000;
    public static final int MOD_NAME = 0xFF5555FF;

    // other color(has alpha)
    public static final int NO_BORDER = 0x00000000;
    // TODO:config
    public static final int AMOUNT_DEFAULT_BORDER = 0xFF555555;
    public static final int AMOUNT_DEFAULT_BACKGROUND = 0xFFFFFFFF;
    public static final int AMOUNT_DEFAULT_FILLED = 0xFFD7D7D7;
    public static final int AMOUNT_DEFAULT_FILLED_ALTERNATE = 0xFFD7D7D7;
    public static final int BREAK_PROGRESS_DEFAULT = 0xFFA0A0A0;
    public static final int BREAK_PROGRESS_FAILURE = 0xFFAA0000;

    public static int get(MessageType type) {
        return switch (type) {
            case INFO -> INFO;
            case TITLE -> TITLE;
            case SUCCESS -> SUCCESS;
            case WARNING -> WARNING;
            case DANGER -> DANGER;
            case FAILURE -> FAILURE;
            default -> NORMAL;
        };
    }
}
