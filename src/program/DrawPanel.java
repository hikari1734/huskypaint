package program;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * DrawPanel - This is the JPanel that the window's graphics are drawn on top of.
 * 
 * @author Matthew Finzel
 *
 */
public class DrawPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	public static BufferedImage imageBeingWorkedOn;//The image that is currently being worked on.
	public static Point cameraCoords;//The coordinates of the top left corner of the "camera"

	public DrawPanel() {

		//the image being worked on defaults to a blank image that is 400x400 pixels
		imageBeingWorkedOn = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
		Graphics tmp = imageBeingWorkedOn.getGraphics();

		//The image is white(blank) by default.
		tmp.setColor(Color.WHITE);
		tmp.fillRect(0, 0, imageBeingWorkedOn.getWidth(), imageBeingWorkedOn.getHeight());
		tmp.dispose();

		//the camera defaults to being centered on the image
		centerCameraOnImage();
	}

	/**
	 * Moves the "camera" so that the image will be displayed in the center of it.
	 */
	public void centerCameraOnImage() {
		int windowTitleHeight = 30;
		int cameraX = (Window.frame.getWidth()/2)-(imageBeingWorkedOn.getWidth()/2);
		int cameraY = ((Window.frame.getHeight()-windowTitleHeight)/2)-(imageBeingWorkedOn.getHeight()/2);

		cameraCoords = new Point(cameraX, cameraY);
	}

	/**
	 * Gets this JPanel's Graphics object, casts it to a Graphics2D object to give it more functionality,
	 * then calls the Draw() method using that as a parameter.
	 * 
	 * Called using repaint()
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Draw((Graphics2D)g);
	}

	/**
	 * Draw a dot at the specified coordinate
	 * @param coordinates
	 */
	public static void paint(Point coordinates) {
		//If the mouse is inside the image being edited
		if(coordinates.getX()>=DrawPanel.cameraCoords.x&&coordinates.getX()<DrawPanel.cameraCoords.x+DrawPanel.imageBeingWorkedOn.getWidth()) {
			if(coordinates.getY()>=DrawPanel.cameraCoords.y&&coordinates.getY()<DrawPanel.cameraCoords.y+DrawPanel.imageBeingWorkedOn.getHeight()) {

				//Draw a black dot on the image where it was clicked
				imageBeingWorkedOn.setRGB((int)coordinates.getX()-(int)cameraCoords.x, (int)coordinates.getY()-(int)cameraCoords.y, Color.BLACK.getRGB());
			}
		}
	}
	/**
	 * Update the graphics displayed by the program.
	 * 
	 * @param g - The Graphics2D object to draw with.
	 */
	public void Draw(Graphics2D g) {

		//Draw the image on the canvas (This JPanel)
		g.drawImage(imageBeingWorkedOn, cameraCoords.x, cameraCoords.y, null);

	}
}

