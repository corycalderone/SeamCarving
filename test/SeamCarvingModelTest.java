import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SeamCarvingModelTest {

  SeamCarvingModel s;
  List<List<Pixel>> img;

  @Before
  public void setUp() {
    s = new SeamCarvingModel("img1.png");
    img = s.getImage();
  }

  @Test
  public void testTopLeftPixelInit() {
    assertEquals(255, this.img.get(0).get(0).getColor().getRed());
  }

  @Test
  public void testTopLeftPixel() {
    assertEquals(255, this.img.get(0).get(0).getRight().getColor().getGreen());
  }

  @Test
  public void testCenterPixel() {
    assertTrue(this.img.get(1).get(1).getUp().getLeft().getColor().equals
            (this.img.get(1).get(1).getLeft().getUp().getColor()));
  }

}