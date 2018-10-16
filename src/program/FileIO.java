package program;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FileIO {
	/*
	 * Loads an image from an image file
	 * 
	 * @param imageName - the file path to the image
	 * 
	 * @return the image loaded
	 */
	public static BufferedImage loadImage(String imageName){
		BufferedImage image;
		try
		{
			image = ImageIO.read(FileIO.class.getResourceAsStream(imageName));
			BufferedImage img = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics g = img.getGraphics();
			g.drawImage(image, 0, 0, null);
			g.dispose();
			image = img;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
		return image;
	}
	
}
