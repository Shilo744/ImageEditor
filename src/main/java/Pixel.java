import java.awt.*;

public class Pixel{
    private final int PIXEL_SIZE=1;
    private final int X;
    private final int Y;
    private Color color;

    public void paint(Graphics graphics) {
        graphics.setColor(this.color);
        graphics.fillRect(this.X, this.Y, PIXEL_SIZE,PIXEL_SIZE);
    }

    public Pixel(int x, int y, Color color) {
        this.X = x;
        this.Y = y;
        this.color = color;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String toString() {
        return "CostumeRectangle{" +
                "x=" + X +
                ", y=" + Y +
                ", color=" + color +
                '}';
    }
}