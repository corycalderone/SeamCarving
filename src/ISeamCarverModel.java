import java.util.List;

import javalib.worldimages.FromFileImage;

public interface ISeamCarverModel {

  public List<List<Pixel>> getImage();

  public List<List<Pixel>> initGraph(FromFileImage fileImage);

  public double computePixelEnergy(Pixel p);

  public SeamInfo findVerticalSeam();

  public void removeSeam();

}
