package program;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;


import javax.swing.JPanel;

/**
 * DrawPanel - This is the JPanel that the window's graphics are drawn on top of.
 * 
 * @author Matthew Finzel, Marissa Walther
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

	public DrawPanel(BufferedImage s) {
		//resize based on image size
		Window.frame = new Window();
		Window.frame.setSize(s.getWidth() + 100, s.getHeight() + 100);
		Window.pane.setSize(s.getWidth(), s.getHeight());
		
		System.out.println(s.getWidth() + " " + Window.frame.getWidth());
		imageBeingWorkedOn = new BufferedImage(s.getWidth(), s.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics tmp = imageBeingWorkedOn.getGraphics();

		//The image is white(blank) by default.
		//tmp.setColor(Color.WHITE);
		//tmp.fillRect(0, 0, imageBeingWorkedOn.getWidth(), imageBeingWorkedOn.getHeight());
		tmp.drawImage(s, 0, 0, null);
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
	 * Draw a dot at the specified coordinate on the image that's being worked on.
	 * 
	 * @param coordinate - The center of the dot of color to be painted.
	 * @param diameter - The diameter of the dot.
	 * @param color - The color of the dot.
	 */
	public static void applyPaintBrush(Point coordinate, int diameter, Color color) {
		Graphics2D g = (Graphics2D)imageBeingWorkedOn.getGraphics();
		int radius = diameter/2;
		//If the mouse is inside the image being edited
		if(coordinate.getX()>=0&&coordinate.getX()<imageBeingWorkedOn.getWidth()) {
			if(coordinate.getY()>=0&&coordinate.getY()<imageBeingWorkedOn.getHeight()) {
				
				g.setColor(color);
				
				//if the mouse has been dragged
				if(Controller.coordinatesOfPreviousMouseEvent!=null) {
					//Draw a line between the previous mouse position and the current mouse position.
					BasicStroke stroke = new BasicStroke(diameter, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);//The line should have a width equal to diameter and rounded edges
					g.setStroke(stroke);
					g.draw(new Line2D.Float(coordinate.x,coordinate.y, Controller.coordinatesOfPreviousMouseEvent.x-cameraCoords.x, Controller.coordinatesOfPreviousMouseEvent.y-cameraCoords.y));
				}
				//if the mouse has just been pressed instead of being dragged
				else {
					g.setColor(color);
					g.fillOval(coordinate.x-radius, coordinate.y-radius, diameter, diameter);
				}
			}
		}
	}
	/**
	 * Update the graphics displayed by the program.
	 * 
	 * @param g - The Graphics2D object to draw with.
	 */
	public void Draw(Graphics2D g) {
		//imageBeingWorkedOn = FileIO.loadImage("/textures/Husky.png");
		//Draw the image on the canvas (This JPanel)
		g.drawImage(imageBeingWorkedOn, cameraCoords.x, cameraCoords.y, null);

	}
}

