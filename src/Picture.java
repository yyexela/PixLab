import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture. This class inherits from SimplePicture and
 * allows the student to add functionality to the Picture class.
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture {
	///////////////////// constructors //////////////////////////////////

	/**
	 * Constructor that takes no arguments
	 */
	public Picture() {
		/*
		 * not needed but use it to show students the implicit call to super()
		 * child constructors always call a parent constructor
		 */
		super();
	}

	/**
	 * Constructor that takes a file name and creates the picture
	 * 
	 * @param fileName
	 *            the name of the file to create the picture from
	 */
	public Picture(String fileName) {
		// let the parent class handle this fileName
		super(fileName);
	}

	/**
	 * Constructor that takes the width and height
	 * 
	 * @param height
	 *            the height of the desired picture
	 * @param width
	 *            the width of the desired picture
	 */
	public Picture(int height, int width) {
		// let the parent class handle this width and height
		super(width, height);
	}

	/**
	 * Constructor that takes a picture and creates a copy of that picture
	 * 
	 * @param copyPicture
	 *            the picture to copy
	 */
	public Picture(Picture copyPicture) {
		// let the parent class do the copy
		super(copyPicture);
	}

	/**
	 * Constructor that takes a buffered image
	 * 
	 * @param image
	 *            the buffered image to use
	 */
	public Picture(BufferedImage image) {
		super(image);
	}

	////////////////////// methods ///////////////////////////////////////

	/**
	 * Method to return a string with information about this picture.
	 * 
	 * @return a string with information about the picture such as fileName,
	 *         height and width.
	 */
	public String toString() {
		String output = "Picture, filename " + getFileName() + " height " + getHeight() + " width " + getWidth();
		return output;

	}

	/** Method to set the blue to 0 */
	public void zeroBlue() {
		Pixel[][] pixels = this.getPixels2D();
		for (Pixel[] rowArray : pixels) {
			for (Pixel pixelObj : rowArray) {
				pixelObj.setBlue(0);
			}
		}
	}

	/** Method to set the blue to 255 */
	public void allBlue() {
		Pixel[][] pixels = this.getPixels2D();
		for (Pixel[] rowArray : pixels) {
			for (Pixel pixelObj : rowArray) {
				pixelObj.setRed(0);
				pixelObj.setGreen(0);
			}
		}
	}
	
	/** Method to set all values average */
	public void fixUnderwater() {
		Pixel[][] pixels = this.getPixels2D();
		for (Pixel[] rowArray : pixels) {
			for (Pixel pixelObj : rowArray) {
				//int maxValue = 0; //red = 0, green = 1, blue = 2;
				if(pixelObj.getRed() >= pixelObj.getBlue() && pixelObj.getRed() >= pixelObj.getGreen()) pixelObj.setRed(255);
				else if(pixelObj.getGreen() >= pixelObj.getRed() && pixelObj.getGreen() >= pixelObj.getBlue()) pixelObj.setGreen(255);
				else if(pixelObj.getBlue() >= pixelObj.getRed() && pixelObj.getBlue() >= pixelObj.getGreen()) pixelObj.setBlue(255);

			}
		}
	}

	/** Method to set all values average */
	public void grayscale() {
		Pixel[][] pixels = this.getPixels2D();
		for (Pixel[] rowArray : pixels) {
			for (Pixel pixelObj : rowArray) {
				int average = (pixelObj.getRed() + pixelObj.getGreen() + pixelObj.getBlue())/3;
				pixelObj.setRed(average);
				pixelObj.setGreen(average);
				pixelObj.setBlue(average);
			}
		}
	}

	/** Method to set all values to 255 - them */
	public void negate() {
		Pixel[][] pixels = this.getPixels2D();
		for (Pixel[] rowArray : pixels) {
			for (Pixel pixelObj : rowArray) {
				pixelObj.setRed(255 - pixelObj.getRed());
				pixelObj.setGreen(255 - pixelObj.getGreen());
				pixelObj.setBlue(255 - pixelObj.getBlue());
			}
		}
	}

	/**
	 * Method that mirrors the picture around a vertical mirror in the center of
	 * the picture from right to left
	 */
	public void mirrorVerticalRightToLeft() {
		Pixel[][] pixels = this.getPixels2D();
		Pixel leftPixel = null;
		Pixel rightPixel = null;
		int width = pixels[0].length;
		for (int row = 0; row < pixels.length; row++) {
			for (int col = 0; col < width / 2; col++) {
				leftPixel = pixels[row][col];
				rightPixel = pixels[row][width - 1 - col];
				leftPixel.setColor(rightPixel.getColor());
			}
		}
	}

	/**
	 * Method that mirrors the picture around a vertical mirror in the center of
	 * the picture from left to right
	 */
	public void mirrorVertical() {
		Pixel[][] pixels = this.getPixels2D();
		Pixel leftPixel = null;
		Pixel rightPixel = null;
		int width = pixels[0].length;
		for (int row = 0; row < pixels.length; row++) {
			for (int col = 0; col < width / 2; col++) {
				leftPixel = pixels[row][col];
				rightPixel = pixels[row][width - 1 - col];
				rightPixel.setColor(leftPixel.getColor());
			}
		}
	}

	/**
	 * Method that mirrors the picture around a vertical mirror in the center of
	 * the picture from top to bottom
	 */
	public void mirrorHorizontal() {
		Pixel[][] pixels = this.getPixels2D();
		Pixel topPixel = null;
		Pixel bottomPixel = null;
		int height = pixels.length;
		int width = pixels[0].length;
		for (int row = 0; row < pixels.length; row++) {
			for (int col = 0; col < width; col++) {
				topPixel = pixels[row][col];
				bottomPixel = pixels[height - 1 - row][col];
				bottomPixel.setColor(topPixel.getColor());
			}
		}
	}

	/**
	 * Method that mirrors the picture around a diagonal mirror in the center of
	 * the picture from left to right
	 */
	public void mirrorDiagonal() {
		Pixel[][] pixels = this.getPixels2D();
		Pixel refPixel = null;
		Pixel mirrorPixel = null;
		int height = pixels.length;
		int width = pixels[0].length;
		int refLength = 1;
		for(int refHeight = 0; refHeight < height; refHeight++){
			for(int col = 0; col <= refHeight; col++){
				refPixel = pixels[refHeight][col];
				mirrorPixel = pixels[col][refHeight];
				mirrorPixel.setColor(refPixel.getColor());
			}
		}
	}

	/**
	 * Method that mirrors the picture around a vertical mirror in the center of
	 * the picture from top to bottom
	 */
	public void mirrorHorizontalBotToTop() {
		Pixel[][] pixels = this.getPixels2D();
		Pixel topPixel = null;
		Pixel bottomPixel = null;
		int height = pixels.length;
		int width = pixels[0].length;
		for (int row = 0; row < pixels.length; row++) {
			for (int col = 0; col < width; col++) {
				topPixel = pixels[row][col];
				bottomPixel = pixels[height - 1 - row][col];
				topPixel.setColor(bottomPixel.getColor());
			}
		}
	}

	/** mirror the left arm of the snowman 4 times */
	public void mirrorArms(){
		//Area to be covered
		int leftCol = 239; int rightCol = 294;
		int botRow = 196; int topRow = 156;

		Pixel leftPixel = null; Pixel rightPixel = null;
		Pixel[][] pixels = this.getPixels2D();
		//loop through rows
		for(int i = topRow; i <= botRow; i++){
			//loop through columns
			for(int j = leftCol; j <= rightCol; j++){
				leftPixel = pixels[i][pixels[0].length - j];
				rightPixel = pixels[i][j];
				rightPixel.setColor(leftPixel.getColor());

				rightPixel = pixels[i + 70][j + 5];
				rightPixel.setColor(leftPixel.getColor());

				rightPixel = pixels[i + 70][pixels[0].length - j + 2];
				rightPixel.setColor(leftPixel.getColor());
			}
		}
	}

	/** mirror the left arm of the snowman */
	public void mirrorGulls(){
		//Area to be copied
		int leftCol = 235; int rightCol = 350;
		int botRow = 329; int topRow = 230;

		Pixel leftPixel = null; Pixel rightPixel = null;
		Pixel[][] pixels = this.getPixels2D();
		//loop through rows
		for(int i = topRow; i <= botRow; i++){
			//loop through columns
			for(int j = leftCol; j <= rightCol; j++){
				leftPixel = pixels[i][j];
				rightPixel = pixels[i - 20][j - (rightCol - leftCol) - 15];
				rightPixel.setColor(leftPixel.getColor());;
			}
		}
	}

	/** Mirror just part of a picture of a temple */
	public void mirrorTemple() {
		int mirrorPoint = 276;
		Pixel leftPixel = null;
		Pixel rightPixel = null;
		int count = 0;
		Pixel[][] pixels = this.getPixels2D();

		// loop through the rows
		for (int row = 27; row < 97; row++) {
			// loop from 13 to just before the mirror point
			for (int col = 13; col < mirrorPoint; col++) {
				count++;
				leftPixel = pixels[row][col];
				rightPixel = pixels[row][mirrorPoint - col + mirrorPoint];
				rightPixel.setColor(leftPixel.getColor());
			}
		}
		System.out.println(count);
	}

	/**
	 * copy from the passed fromPic to the specified startRow and startCol in
	 * the current picture
	 * 
	 * @param fromPic
	 *            the picture to copy from
	 * @param startRow
	 *            the start row to copy to
	 * @param startCol
	 *            the start col to copy to
	 */
	public void copy(Picture fromPic, int startRow, int startCol) {
		Pixel fromPixel = null;
		Pixel toPixel = null;
		Pixel[][] toPixels = this.getPixels2D();
		Pixel[][] fromPixels = fromPic.getPixels2D();
		for (int fromRow = 0, toRow = startRow; fromRow < fromPixels.length
				&& toRow < toPixels.length; fromRow++, toRow++) {
			for (int fromCol = 0, toCol = startCol; fromCol < fromPixels[0].length
					&& toCol < toPixels[0].length; fromCol++, toCol++) {
				fromPixel = fromPixels[fromRow][fromCol];
				toPixel = toPixels[toRow][toCol];
				toPixel.setColor(fromPixel.getColor());
			}
		}
	}

	public void copy(Picture fromPic, int leftRow, int rightRow, int leftCol, int rightCol, int startRow, int startCol) {
		Pixel fromPixel = null;
		Pixel toPixel = null;
		Pixel[][] toPixels = this.getPixels2D();
		Pixel[][] fromPixels = fromPic.getPixels2D();
		for (int fromRow = leftRow, toRow = startRow; fromRow < rightRow
				&& toRow < toPixels.length; fromRow++, toRow++) {
			for (int fromCol = leftCol, toCol = startCol; fromCol < rightCol
					&& toCol < toPixels[0].length; fromCol++, toCol++) {
				fromPixel = fromPixels[fromRow][fromCol];
				toPixel = toPixels[toRow][toCol];
				toPixel.setColor(fromPixel.getColor());
			}
		}
	}

	/** Method to create a collage of several pictures */
	public void myCollage() {
		Picture beach = new Picture("beach.jpg");
		Picture snowman = new Picture("snowman.jpg");
		Picture snowmanNegative = new Picture("snowman.jpg");
		Picture snowmanGrayscale = new Picture("snowman.jpg");
		snowmanNegative.negate();
		snowmanGrayscale.grayscale();

		int vertOffset = 100;

		this.copy(beach, 0, 0);

		this.copy(snowman, 70, 129, 165, 245, 26 + vertOffset, 180);
		this.copy(snowmanNegative, 129, 140, 165, 245, 86 + vertOffset, 180);
		this.copy(snowmanGrayscale, 140, 300, 165, 245, 98 + vertOffset, 180);

		this.copy(snowmanNegative, 70, 129, 165, 245, 26 + vertOffset, 260);
		this.copy(snowmanGrayscale, 129, 140, 165, 245, 86 + vertOffset, 260);
		this.copy(snowmanNegative, 140, 300, 165, 245, 98 + vertOffset, 260);

		this.mirrorVertical();


		this.write("collage.jpg");
	}

	/** Method to create a collage of several pictures */
	public void createCollage() {
		Picture flower1 = new Picture("flower1.jpg");
		Picture flower2 = new Picture("flower2.jpg");
		this.copy(flower1, 20, 70, 20, 70, 0, 0);
		this.copy(flower2, 100, 0);
		this.copy(flower1, 200, 0);
		Picture flowerNoBlue = new Picture(flower2);
		flowerNoBlue.zeroBlue();
		this.copy(flowerNoBlue, 300, 0);
		this.copy(flower1, 400, 0);
		this.copy(flower2, 500, 0);
		this.mirrorVertical();
		this.write("collage.jpg");
	}

	/**
	 * Method to show large changes in color
	 * 
	 * @param edgeDist
	 *            the distance for finding edges
	 */
	public void edgeDetection(int edgeDist) {
		Pixel leftPixel = null;
		Pixel rightPixel = null;
		Pixel[][] pixels = this.getPixels2D();
		Color rightColor = null;
	
		for (int row = 0; row < pixels.length; row++) {
			for (int col = 0; col < pixels[0].length - 1; col++) {
				leftPixel = pixels[row][col];
				rightPixel = pixels[row][col + 1];
				rightColor = rightPixel.getColor();
				if (leftPixel.colorDistance(rightColor) > edgeDist)
					leftPixel.setColor(Color.BLACK);
			}
		}

		
		for (int row = 0; row < pixels.length - 1; row++) {
			for (int col = 0; col < pixels[0].length; col++) {
				if( pixels[row][col].getRed() != 255 && pixels[row][col].getBlue() != 255 && pixels[row][col].getGreen() != 255 && 
					pixels[row + 1][col].getRed() != 255 && pixels[row + 1][col].getBlue() != 255 && pixels[row + 1][col].getGreen() != 255
				){
					leftPixel = pixels[row][col]; // topPixel
					rightPixel = pixels[row + 1][col]; // bottomPixel
					rightColor = rightPixel.getColor();
					if (leftPixel.colorDistance(rightColor) > edgeDist)
						leftPixel.setColor(Color.BLACK);
					else
						leftPixel.setColor(Color.WHITE);
				}
			}
		}
	}

	/** 
	 * Edge detection 2
	 */

	public void edgeDetection2(int edgeDist) {
		Pixel leftPixel = null;
		Pixel rightPixel = null;
		Pixel bottomPixel = null;
		Pixel[][] pixels = this.getPixels2D();
		Color rightColor = null;
		Color bottomColor = null;
	
		for (int row = 0; row < pixels.length - 1; row++) {
			for (int col = 0; col < pixels[0].length - 1; col++) {
				leftPixel = pixels[row][col];
				rightPixel = pixels[row][col + 1];
				bottomPixel = pixels[row + 1][col];
				rightColor = rightPixel.getColor();
				bottomColor = bottomPixel.getColor();
				if (leftPixel.colorDistance(rightColor) > edgeDist || bottomPixel.colorDistance(rightColor) > edgeDist)
					leftPixel.setColor(Color.BLACK);
				else leftPixel.setColor(Color.WHITE);
			}
		}
	}

	/*
	 * Main method for testing - each class in Java can have a main method
	 */
	public static void main(String[] args) {
		Picture beach = new Picture("beach.jpg");
		beach.explore();
		beach.zeroBlue();
		beach.explore();
	}

} // this } is the end of class Picture, put all new methods before this
