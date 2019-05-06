import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javalib.worldimages.FromFileImage;

public class SeamCarvingModel implements ISeamCarverModel {

  // pixel grid
  private List<List<Pixel>> image;
  private List<List<SeamInfo>> seamData;

  // constructor
  public SeamCarvingModel(String fileName) {
    this.image = initGraph(new FromFileImage(fileName));
    this.seamData = null;
  }

  @Override
  public List<List<Pixel>> initGraph(FromFileImage fileImage) {

    List<List<Pixel>> graph = new ArrayList<>();

    for (int row = 0; row < fileImage.getHeight(); row++) {

      // first, we initialize the row list, which will be added to the overall graph later.
      List<Pixel> rowList = new ArrayList<>();

      // next, let's make sure this grid is full of pixels. we'll worry about neighbors later.
      for (int col = 0; col < fileImage.getWidth(); col++) {
        rowList.add(new Pixel(fileImage.getColorAt(row, col)));
      }

      // now we add the row:
      graph.add(rowList);
    }

    //                       u, d,  l, r
    int[] rowDisplacement = {-1, 1,  0, 0};
    int[] colDisplacement = { 0, 0, -1, 1};

    // time to add neighbor references!
    for (int row = 0; row < graph.size(); row++) {
      for (int col = 0; col < graph.get(0).size(); col++) {

        for (int i = 0; i < 4; i++) {
          int neighborRow = row + rowDisplacement[i];
          int neighborCol = col + colDisplacement[i];
          Pixel p = graph.get(row).get(col);

          if (neighborRow >= 0 && neighborRow < graph.size() &&
                  neighborCol >= 0 && neighborCol < graph.get(0).size()) {
            switch(i){
              case 0:
                p.setUp(graph.get(neighborRow).get(neighborCol));
                p.getUp().setDown(p);
                break;
              case 1:
                p.setDown(graph.get(neighborRow).get(neighborCol));
                p.getDown().setUp(p);
                break;
              case 2:
                p.setLeft(graph.get(neighborRow).get(neighborCol));
                p.getLeft().setRight(p);
                break;
              case 3:
                p.setRight(graph.get(neighborRow).get(neighborCol));
                p.getRight().setLeft(p);
                break;
              default:
                throw new IllegalStateException("Something went wrong in the addition of " +
                        "neighbors to R: " + row + ", C: "+ col);
            }
          } else {
            Pixel q = new Pixel(Color.BLACK);
            switch(i){
              case 0:
                p.setUp(q);
                p.getUp().setDown(p);
                break;
              case 1:
                p.setDown(q);
                p.getDown().setUp(p);
                break;
              case 2:
                p.setLeft(q);
                p.getLeft().setRight(p);
                break;
              case 3:
                p.setRight(q);
                p.getRight().setLeft(p);
                break;
              default:
                throw new IllegalStateException("Something went wrong in the addition of " +
                        "neighbors to R: " + row + ", C: "+ col);
            }
          }
        }
      }
    }
    return graph;
  }

  @Override
  public double computePixelEnergy(Pixel p) {
    return 0;
  }

  @Override
  public SeamInfo findVerticalSeam() {

    this.seamData = new LinkedList<>();

    for (int row = 0; row < this.image.size(); row++) {
      //todo potentially add preliminary black pixel border here, we're winging it
      ArrayList<SeamInfo> rowList = new ArrayList<>();
      for (int pixel = 0; pixel < this.image.get(row).size(); pixel++) {

        Pixel p = this.image.get(row).get(pixel);

        //                        l   t   r
        int[] rowDisplacement = {-1, -1, -1};
        int[] colDisplacement = {-1,  0,  1};

        Double minSeamWeight = Double.MIN_VALUE;
        SeamInfo minInfo = null; //todo should you be null?

        for (int i = 0; i < 3; i++) {

          int neighborRow = row + rowDisplacement[i];
          int neighborCol = pixel + colDisplacement[i];
          SeamInfo neighbor = seamData.get(neighborRow).get(neighborCol);

          if (neighbor.getTotalWeight() < minSeamWeight) {
            minSeamWeight = neighbor.getTotalWeight();
            minInfo = neighbor;
          }
        }

        rowList.add(new SeamInfo(p, minSeamWeight + p.getWeight(), minInfo));
      }
      seamData.add(rowList);
    }


    double minWeight = Double.MAX_VALUE;
    SeamInfo minSeam = null;

    for (int i = 0; i < this.image.get(0).size(); i++) {
      SeamInfo curSeam = seamData.get(seamData.size() - 1).get(i);
      if (curSeam.getTotalWeight() < minWeight) {
        minWeight = curSeam.getTotalWeight();
        minSeam = curSeam;
      }
    }

    return minSeam;
  }

  @Override
  public void removeSeam() {
    SeamInfo seam = findVerticalSeam();

    if (seam == null) { // it can't *start* as null. I don't think.
      throw new IllegalStateException("Seam is null.");
    }

    while (seam != null) {


    }
  }

  @Override
  public List<List<Pixel>> getImage() {
    return image;
  }
}