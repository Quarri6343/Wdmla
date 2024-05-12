package mcp.mobius.wdmla.impl.values;

/**
 * Alpha value of the color
 */
public class Alpha {

    private final float value;
    private static long initMilliSecond;

    public Alpha(float value) {
        if (value < 0 || value > 1) {
            throw new IllegalArgumentException("Alpha value must be between 0 and 1");
        }

        initMilliSecond = System.currentTimeMillis();
        this.value = value;
    }

    /**
     * Decrease value depends on elapsed Second
     */
    public Alpha fade() {
        float elapsedSecond = (float) (System.currentTimeMillis() - initMilliSecond) / 1000;
        float fadedAlpha = value - elapsedSecond;
        if (fadedAlpha < 0) {
            fadedAlpha = 0;
        }

        return new Alpha(fadedAlpha);
    }

    public boolean isTransparent() {
        return value < 0;
    }

    /**
     * Apply alpha value to a color
     */
    public int apply(int color) {
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;
        int appliedAlpha = (int) (value * 255); // アルファ値を0〜255の範囲に変換
        return (appliedAlpha << 24) | (red << 16) | (green << 8) | blue;
    }
}
