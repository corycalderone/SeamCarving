public class SeamInfo {
  private Pixel pixel;
  private double totalWeight;
  private SeamInfo cameFrom;

  public SeamInfo(Pixel pixel, double totalWeight, SeamInfo cameFrom) {
    this.pixel = pixel;
    this.totalWeight = totalWeight;
    this.cameFrom = cameFrom;
  }

  public Pixel getPixel() {
    return pixel;
  }

  public double getTotalWeight() {
    return totalWeight;
  }

  public SeamInfo getCameFrom() {
    return cameFrom;
  }

}
