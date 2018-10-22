package program;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class FileIO {
	public static File selectedFile;

	public FileIO(JPanel j) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

		BufferedImage origImage = null;
		// You should use the parent component instead of null
		// but it was impossible to tell from the code snippet what that was.
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try {
				origImage = ImageIO.read(selectedFile);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			DrawPanel d = new DrawPanel(origImage);
			Window.pane.add(d);
		}

	}

	/*
	 * Loads an image from an image file
	 * 
	 * @param imageName - the file path to the image
	 * 
	 * @return the image loaded
	 */
	public static BufferedImage loadImage(String imageName) {
		BufferedImage image;
		try {
			image = ImageIO.read(FileIO.class.getResourceAsStream(imageName));
			BufferedImage img = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics g = img.getGraphics();
			g.drawImage(image, 0, 0, null);
			g.dispose();
			image = img;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return image;
	}

}
