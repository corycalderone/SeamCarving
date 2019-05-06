import java.awt.*;

public class Pixel {
  private Color color;
  private Pixel up;
  private Pixel down;
  private Pixel left;
  private Pixel right;
  private double brightness;
  private Double weight;


  public Pixel(Color color) { //todo account for weight and
    this.color = color;
    this.up = null;
    this.down = null;
    this.left = null;
    this.right = null;
    this.brightness = ((color.getGreen() + color.getBlue() + color.getRed()) / 3.0) / 255.0;
  }

  public Pixel(Color color, Pixel up, Pixel down, Pixel left, Pixel right) {
    this.color = color;
    this.up = up;
    this.down = down;
    this.left = left;
    this.right = right;
  }

  public Double getWeight() {
    if (this.weight != null) {
      return this.weight;
    } else {
      //todo compute info
      // What i THink is an invariant: this will only ever be called during a seam search, which I
      // believe can only be called *after* neighbors are initialized, meaning seam energies are computable.
      return null; // todo did you get a NPE?
    }
  }

  public void setUp(Pixel up) {
    this.up = up;
  }

  public void setDown(Pixel down) {
    this.down = down;
  }

  public void setLeft(Pixel left) {
    this.left = left;
  }

  public void setRight(Pixel right) {
    this.right = right;
  }

  public Color getColor() {
    return color;
  }

  public Pixel getUp() {
    return up;
  }

  public Pixel getDown() {
    return down;
  }

  public Pixel getLeft() {
    return left;
  }

  public Pixel getRight() {
    return right;
  }

}
