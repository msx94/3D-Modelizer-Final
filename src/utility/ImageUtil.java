package utility;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;

import algebra.Matrix;

public final class ImageUtil {
	/**
	 * Converts a java.awt.Image into an matrix of pixels
	 * 
	 * @param img The image to be converted
	 * @return the pixels array of the image
	 */
	
	public static int[] convertToPixels(Image img) {
		int width = img.getWidth(null);
		int height = img.getHeight(null);
		int[] pixels = new int[width * height];
		PixelGrabber pg = new PixelGrabber(img, 0, 0, width, height, pixels, 0, width);

		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
			throw new IllegalStateException("Error : Interrupted waiting for Pixels");
		}
		if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
			throw new IllegalStateException("Error : Image Fetch Aborted");
		}
		
		return pixels;
	}
	
	/**
	 * Converts a given Image into a BufferedImage
	 *
	 * @param img The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
	
	/**
	 * Convert a rgb image into a gray level image
	 * 
	 * @param img
	 */
	public static Matrix rgb2gray(Image img) {
		
		Matrix pixels = null ;
		
		try {
			
			BufferedImage bi = toBufferedImage(img);
			int width = img.getWidth(null);
			int height = img.getHeight(null);
			pixels = new Matrix("matrice de gris", width, height);
			
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					int pix = bi.getRGB(i, j);
					
					Color rgbColorPix = new Color(pix);
					double newPix = 0.2125 * rgbColorPix.getRed() + 0.7154 * rgbColorPix.getGreen() + 0.0721 * rgbColorPix.getBlue();
					
					pixels.set(i, j, newPix/255);
				}
			}
			
		} catch (InstantiationException e) {

			e.printStackTrace();
			
		}
		
		return pixels;
	}
	
}
