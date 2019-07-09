import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class ImagePanel extends JPanel {

	Image bgImage;

	public ImagePanel(BufferedImage bi) {
		bgImage = bi;
	}

	@Override
	  protected void paintComponent(Graphics g) {

	    super.paintComponent(g);
	        g.drawImage(bgImage, 0, 0, null);
	}
}