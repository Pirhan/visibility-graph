package pison.visibilitygraph.styles;

import org.newdawn.slick.Color;

public class LineStyle {
    private final Color c;
    private final float width;

    public LineStyle(Color c, float width) {
        this.c = c;
        this.width = width;
    }

    public Color getColor() {
        return c;
    }

    public float getWidth() {
        return width;
    }
}
