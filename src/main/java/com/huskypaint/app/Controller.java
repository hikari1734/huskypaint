package com.huskypaint.app;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.awt.datatransfer.*;


import java.awt.datatransfer.ClipboardOwner;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Controller - This is the class that listens for Mouse Input inside the the DrawPanel.
 * 
 * @author Matthew Finzel
 */
public class Controller implements MouseListener,MouseMotionListener, KeyListener, ClipboardOwner {

	JPanel drawPanel;//We need a reference to the DrawPanel used by the program.
	public static Point coordinatesOfPreviousMouseEvent = null;
	public static int diam = 10;
	Color paintBrush = new Color(0,0,0);
	Boolean filling = false;
	Path2D.Double fillPath = new Path2D.Double();
	ArrayList<Path2D> shapes = new ArrayList<Path2D>();

	public Controller() {

	}
    public void lostOwnership( Clipboard clip, Transferable trans ) {
        System.out.println( "Lost Clipboard Ownership" );
    }

	public void keyPressed(KeyEvent e){
		if (e.getKeyCode() == KeyEvent.VK_C&&((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)){
			System.out.println("woot!");
			int x = DrawPanel.selection.x;
			int y = DrawPanel.selection.y;
			int w = DrawPanel.selection.width;
			int h = DrawPanel.selection.height;
            TransferableImage trans = new TransferableImage( DrawPanel.imageBeingWorkedOn.getSubimage(x, y, w, h) );
            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            c.setContents( trans, this );
		}

        if (e.getKeyCode() == KeyEvent.VK_V&&((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)){
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            try {
                DrawPanel.setImageBeingWorkedOn((BufferedImage) clipboard.getData(DataFlavor.imageFlavor));
            }
            catch(Exception ex){}
        }
	}

	public void keyReleased(KeyEvent e){
        //System.out.println("Key released!");
	}

	public void keyTyped(KeyEvent e){
        //System.out.println("Key typed!");
	}

	/**
	 * Used to set the local variable "drawPanel" so that it refers to the DrawPanel 
	 * used by the program and to set the JFrame used by the program to use this class 
	 * as it's MouseListener and MouseMotionListener.
	 *
	 * @param panelRef - A reference to the JPanel used by the program (DrawPanel).
	 */
	public void setDrawPanel(JPanel panelRef) {
		drawPanel = panelRef;
        drawPanel.addKeyListener(this);
		drawPanel.addMouseListener(this);
		drawPanel.addMouseMotionListener(this);

		Window.picker.addColorChangeListener(new ColorPicker.ColorChangeListener(){
			@Override
			public void colorChange(Color newColor){
				paintBrush = newColor;
			}
		});
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		filling = FillButton.getFilling();
		if(!filling) {
			//Draw a black circle with a diameter of 10 at the coordinates of the mouse on the image
			Point coordinatesOfEventRelativeToImage = new Point(e.getPoint().x - DrawPanel.cameraCoords.x, e.getPoint().y - DrawPanel.cameraCoords.y);
			Window.drawPoints.add(coordinatesOfEventRelativeToImage);

			if (SwingUtilities.isLeftMouseButton(e)) {
				DrawPanel.applyPaintBrush(coordinatesOfEventRelativeToImage, diam, paintBrush);
			} else if (SwingUtilities.isRightMouseButton(e)) {
				//System.out.println("right mouse dragged");
				int x = e.getX() - DrawPanel.cameraCoords.x;
				int y = e.getY() - DrawPanel.cameraCoords.y;

				//make sure the coordinates are within the bounds of the image being worked on
				if (x < 0) x = 0;
				if (y < 0) y = 0;
				if (x > DrawPanel.imageBeingWorkedOn.getWidth() - 1) x = DrawPanel.imageBeingWorkedOn.getWidth() - 1;
				if (y > DrawPanel.imageBeingWorkedOn.getHeight() - 1) y = DrawPanel.imageBeingWorkedOn.getHeight() - 1;

				//update the selection rectangle
				if (DrawPanel.selection != null) {
					//System.out.println("here");
					if (x < DrawPanel.selectionCoordinate.x) {
						DrawPanel.selection.width = DrawPanel.selectionCoordinate.x - x;
						DrawPanel.selection.x = x;
					} else if (x > DrawPanel.selectionCoordinate.x) {
						DrawPanel.selection.x = DrawPanel.selectionCoordinate.x;
						DrawPanel.selection.width = x - DrawPanel.selectionCoordinate.x;
					}
					if (y < DrawPanel.selectionCoordinate.y) {
						DrawPanel.selection.height = DrawPanel.selectionCoordinate.y - y;
						DrawPanel.selection.y = y;
					} else if (y > DrawPanel.selectionCoordinate.y) {
						DrawPanel.selection.y = DrawPanel.selectionCoordinate.y;
						DrawPanel.selection.height = y - DrawPanel.selectionCoordinate.y;
					}

				}
			}
		}


		//Update the canvas so changes will be visible
		drawPanel.repaint();

		//Update the coordinates of the previous mouse event
		coordinatesOfPreviousMouseEvent = e.getPoint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		//Update the canvas so changes will be visible
		drawPanel.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		filling = FillButton.getFilling();
		Color fillColor = Window.picker.getCurrentColor();
		int x = e.getX();
		int y = e.getY();

		Graphics2D g2 = (Graphics2D) DrawPanel.imageBeingWorkedOn.getGraphics();

		if(filling) {
			fillShape(x, y, fillColor, g2);
		} else {
			return;
		}
		//Update the canvas so changes will be visible
		drawPanel.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		filling = FillButton.getFilling();
		if(!filling) {
			int x = e.getX() - DrawPanel.cameraCoords.x;
			int y = e.getY() - DrawPanel.cameraCoords.y;

			//make sure the coordinates are within the bounds of the image being worked on
			if (x < 0) x = 0;
			if (y < 0) y = 0;
			if (x > DrawPanel.imageBeingWorkedOn.getWidth() - 1) x = DrawPanel.imageBeingWorkedOn.getWidth() - 1;
			if (y > DrawPanel.imageBeingWorkedOn.getHeight() - 1) y = DrawPanel.imageBeingWorkedOn.getHeight() - 1;

			if (SwingUtilities.isLeftMouseButton(e)) {
				//remove selection box from the image
				DrawPanel.selection = null;

				//Draw a black circle with a diameter of 10 at the coordinates of the mouse event
				Point coordinatesOfEventRelativeToImage = new Point(x, y);
				DrawPanel.applyPaintBrush(coordinatesOfEventRelativeToImage, diam, paintBrush);

			} else if (SwingUtilities.isRightMouseButton(e)) {
				if (DrawPanel.selection == null) {
					DrawPanel.selection = new Rectangle(x, y, 1, 1);
					DrawPanel.selectionCoordinate = new Point(x, y);
				}

			}
		}
		//Update the canvas so changes will be visible
		drawPanel.repaint();

		//Update the coordinates of the previous mouse event
		coordinatesOfPreviousMouseEvent = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		filling = FillButton.getFilling();
		if(!filling) {
			if (Window.drawPoints.size() > 0) {
				fillPath = new Path2D.Double();
				Point startpoint = Window.drawPoints.remove(0);
				fillPath.moveTo(startpoint.getX(), startpoint.getY());
				while (Window.drawPoints.size() > 0) {
					Point point = Window.drawPoints.remove(0);
					fillPath.lineTo(point.getX(), point.getY());
				}
				//fillPath.lineTo(startpoint.getX(), startpoint.getY());

				fillPath.closePath();

				shapes.add(fillPath);

			}

			//fillPath.reset();
		}

		//Update the canvas so changes will be visible
		drawPanel.repaint();

		//Update the coordinates of the previous mouse event to null since the button has been released since then
		coordinatesOfPreviousMouseEvent = null;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	void fillShape(int x, int y, Color tofill, Graphics2D g2){


		Path2D shapeToFill = null;
		for(Path2D shape:shapes){
			System.out.println("filling a shape");
			Rectangle rect = shape.getBounds();
			Point p = rect.getLocation();
			int w = rect.width;
			int h = rect.height;

			if(x > p.getX() && y > p.getY() && y < (p.getY() + h) && x < (p.getX() + w)){

				g2.setColor(tofill);
				shapeToFill = shape;
				g2.fill(shape);

			}
		}
		g2.setColor(tofill);
		if(shapeToFill != null) {
			g2.fill(shapeToFill);
		}
	}

}
