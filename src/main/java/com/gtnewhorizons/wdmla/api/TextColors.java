package com.gtnewhorizons.wdmla.api;

/**
 * A record holds colors of text
 * 
 * @see com.gtnewhorizons.wdmla.api.ui.MessageType
 */
public class TextColors {

    public int _default;
    public int info;
    public int title;
    public int success;
    public int warning;
    public int danger;
    public int failure;
    public int modName;

    public TextColors(int _default, int info, int title, int success, int warning, int danger, int failure,
            int modName) {
        this._default = _default;
        this.info = info;
        this.title = title;
        this.success = success;
        this.warning = warning;
        this.danger = danger;
        this.failure = failure;
        this.modName = modName;
    }
}
