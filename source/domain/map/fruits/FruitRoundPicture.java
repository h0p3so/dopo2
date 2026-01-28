package domain.map.fruits;

import java.awt.Image;

import javax.swing.ImageIcon;

public class FruitRoundPicture {
	private Image colorful;
	private Image imageWalterWhite;

	public FruitRoundPicture (final String colorPath, final String blackAndWhitePath) {
		this.colorful = new ImageIcon(colorPath).getImage();
		this.imageWalterWhite = new ImageIcon(blackAndWhitePath).getImage();
	}
	
	public Image getColorful () {
		return this.colorful;
	}
	
	public Image getBlackAndWhite () {
		return this.imageWalterWhite;
	}
}
