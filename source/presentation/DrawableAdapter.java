package presentation;

// TODO: mover a presentacion

import java.awt.Image;
import javax.swing.ImageIcon;
import presentation.constants.Paths;

public class DrawableAdapter implements Drawable {
	protected Image frontSprite;
	protected Image backSprite;
	protected Image leftSprite;
	protected Image rightSprite;
	protected Image alternativeSprite;
	
    private static Image loadImage(final String path) {
        return new ImageIcon(path != null ? path : "").getImage();
    }

    private DrawableAdapter (final String frontPath, final String backPath, final String leftPath, final String rightPath, final String brokenPath) { 
        this.frontSprite = loadImage(frontPath);
        this.backSprite = loadImage(backPath);
        this.leftSprite = loadImage(leftPath);
        this.rightSprite = loadImage(rightPath);
        this.alternativeSprite = loadImage(brokenPath);
    }
	
	public DrawableAdapter (final String frontPath, final String backPath, final String leftPath, final String rightPath) {
		this(frontPath, backPath, leftPath, rightPath,"");
	}
    
	public DrawableAdapter (final String frontPath, final String brokenPath) {
		this(frontPath, "", "", "", brokenPath);
	}

	public DrawableAdapter (final String frontPath) {
		this(frontPath, "", "", "", "");
	}

	public DrawableAdapter () {
		this(""); 
	}
	
	@Override public Image getFrontSideSprite () {
		return frontSprite;
	}
	
	@Override public Image getBackSideSprite () {
		return backSprite;
	}
	
	@Override public Image getRightSideSprite () {
		return rightSprite;
	}
	
	@Override public Image getLeftSideSprite () {
		return leftSprite;
	}
	
	@Override public Image getAlternativeSprite () {
		return alternativeSprite;
	}
	
	public void swapBetweenFrontAndAlternative () {
		Image temp = this.frontSprite;
		this.frontSprite = this.alternativeSprite;
		this.alternativeSprite = temp;
	}

	public void swapBetweenFrontAndAlternative (Image for_) {
		Image temp = this.frontSprite;
		this.frontSprite = for_;
		for_ = temp;
	}
}
