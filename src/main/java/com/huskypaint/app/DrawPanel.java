package com.huskypaint.app;

import java.awt.*;
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
	public static int toolbarOffset = 50;
	public static int windowTitleHeight = 30;
	public static Rectangle selection;
	public static Point selectionCoordinate;
	static int width = 800;
	static int height = 600;

	public DrawPanel() {

		//the image being worked on defaults to a blank image that is 400x400 pixels
		setImageBeingWorkedOn(createBlankImage(800,600));
	}
	
	/**
	 * Creates a white image with the specified dimensions.
	 * @param width - The width of the image to be created.
	 * @param height - The height of the image to be created.
	 * @return A blank white image with the specified dimensions.
	 */
	public static BufferedImage createBlankImage(int width, int height) {
		BufferedImage tmp = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics tmpG = tmp.getGraphics();

		//The image is white(blank) by default.
		tmpG.setColor(Color.WHITE);
		tmpG.fillRect(0, 0, tmp.getWidth(), tmp.getHeight());
		tmpG.dispose();
		return tmp;
	}

	/**
	 * Moves the "camera" so that the image will be displayed in the center of it.
	 */
	public static void centerCameraOnImage() {
		
		System.out.println("Centering image, width is: "+Window.pane.getWidth()+","+imageBeingWorkedOn.getWidth());
		Point centerOfImage = new Point(imageBeingWorkedOn.getWidth()/2,imageBeingWorkedOn.getHeight()/2);
		Point centerOfCanvas = new Point(Window.frame.getWidth()/2,toolbarOffset+(Window.frame.getHeight()-(toolbarOffset+30))/2);
		int cameraX = centerOfCanvas.x-(centerOfImage.x+8);
		int cameraY = (centerOfCanvas.y)-(centerOfImage.y+8);

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
		//if(coordinate.getX()>=0&&coordinate.getX()<imageBeingWorkedOn.getWidth()) {
			//if(coordinate.getY()>=0&&coordinate.getY()<imageBeingWorkedOn.getHeight()) {

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
				//System.out.println("painting");
			//}
		//}
	}
	/**
	 * Setter for imageBeingWorkedOn that also resizes the HuskyPaint window to fit the image and centers the image inside of it.
	 * 
	 * @param img - The image that imageBeingWorkedOn should be replaced with/set to.
	 */
	public static void setImageBeingWorkedOn(BufferedImage img) {
		
		//resize the window to fit the image
		Window.frame.setSize(img.getWidth()+30, img.getHeight()+windowTitleHeight+toolbarOffset+30);
		
		imageBeingWorkedOn = img;
		
		//center the camera on the new image
		centerCameraOnImage();
	}
	public static BufferedImage getImageBeingWorkedOn() {
		return imageBeingWorkedOn;
	}
	/**
	 * Update the graphics displayed by the program.
	 * 
	 * @param g - The Graphics2D object to draw with.
	 */
	public void Draw(Graphics2D g) {
		//imageBeingWorkedOn = FileIO.loadImage("/textures/Husky.png");
		g.setColor(new Color(60,60,60));
		g.fillRect(0, 0, Window.frame.getWidth(), 50);
		//Draw the image on the canvas (This JPanel)
		g.drawImage(imageBeingWorkedOn, cameraCoords.x, cameraCoords.y, null);

		if(selection!=null) {
			g.setColor(new Color(50,50,200));
			g.drawRect(selection.x+cameraCoords.x, selection.y+cameraCoords.y, selection.width, selection.height);
		}
	}
	static void setWidth(int w){
		width = w;
	}
	static void setHeight(int h){
		height = h;
	}
	static void resize(){
		BufferedImage g = imageBeingWorkedOn;

		BufferedImage newimage = createBlankImage(width, height);
		Graphics g2 = newimage.createGraphics();
		Image lmao = g.getScaledInstance(width, height, g.SCALE_DEFAULT);
		newimage.getGraphics().drawImage(lmao, 0, 0, null);
		setImageBeingWorkedOn(newimage);
	}
}

