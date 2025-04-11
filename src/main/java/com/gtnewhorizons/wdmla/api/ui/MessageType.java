package com.gtnewhorizons.wdmla.api.ui;

/**
 * the type of message in tooltip.<br>
 * This is mostly used to categorize tooltip elements color.
 */
public enum MessageType {
    /**
     * If no message type is specified, every object will be default
     */
    NORMAL,
    /**
     * information message such as crop growth %
     */
    INFO,
    /**
     * message usually at the top of tooltip
     */
    TITLE,
    /**
     * something is succeeded or on or enabled
     */
    SUCCESS,
    WARNING,
    DANGER,
    /**
     * something is failed or off or disabled
     */
    FAILURE,
    /**
     * Mod name text. It is familiar to blue and Italic in text form
     */
    MOD_NAME;
}
