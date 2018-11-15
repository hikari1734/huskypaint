package com.huskypaint.app;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

/**
 * Controller - This is the class that listens for Mouse Input inside the the DrawPanel.
 * 
 * @author Matthew Finzel
 */
public class Controller implements MouseListener,MouseMotionListener{
	JPanel drawPanel;//We need a reference to the DrawPanel used by the program.
	public static Point coordinatesOfPreviousMouseEvent = null;
	Color paintBrush = new Color(0,0,0);
	public Controller() {

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
		//Draw a black circle with a diameter of 10 at the coordinates of the mouse on the image
		Point coordinatesOfEventRelativeToImage = new Point(e.getPoint().x-DrawPanel.cameraCoords.x, e.getPoint().y-DrawPanel.cameraCoords.y);

		if(SwingUtilities.isLeftMouseButton(e)) {
			DrawPanel.applyPaintBrush(coordinatesOfEventRelativeToImage, 10, paintBrush);
		}
		else if(SwingUtilities.isRightMouseButton(e)) {
			//System.out.println("right mouse dragged");
			int x = e.getX()-DrawPanel.cameraCoords.x;
			int y = e.getY()-DrawPanel.cameraCoords.y;

			//make sure the coordinates are within the bounds of the image being worked on
			if(x<0) x = 0;
			if(y<0) y = 0;
			if(x>DrawPanel.imageBeingWorkedOn.getWidth()-1) x = DrawPanel.imageBeingWorkedOn.getWidth()-1;
			if(y>DrawPanel.imageBeingWorkedOn.getHeight()-1) y = DrawPanel.imageBeingWorkedOn.getHeight()-1;

			//update the selection rectangle
			if(DrawPanel.selection!=null) {
				//System.out.println("here");
				if(x<DrawPanel.selectionCoordinate.x) {
					DrawPanel.selection.width = DrawPanel.selectionCoordinate.x-x;
					DrawPanel.selection.x = x;
				}
				else if(x>DrawPanel.selectionCoordinate.x){
					DrawPanel.selection.x = DrawPanel.selectionCoordinate.x;
					DrawPanel.selection.width = x-DrawPanel.selectionCoordinate.x;
				}
				if(y<DrawPanel.selectionCoordinate.y) {
					DrawPanel.selection.height = DrawPanel.selectionCoordinate.y-y;
					DrawPanel.selection.y = y;
				}
				else if(y>DrawPanel.selectionCoordinate.y){
					DrawPanel.selection.y = DrawPanel.selectionCoordinate.y;
					DrawPanel.selection.height = y-DrawPanel.selectionCoordinate.y;
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
		//Update the canvas so changes will be visible
		drawPanel.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX()-DrawPanel.cameraCoords.x;
		int y = e.getY()-DrawPanel.cameraCoords.y;

		//make sure the coordinates are within the bounds of the image being worked on
		if(x<0) x = 0;
		if(y<0) y = 0;
		if(x>DrawPanel.imageBeingWorkedOn.getWidth()-1) x = DrawPanel.imageBeingWorkedOn.getWidth()-1;
		if(y>DrawPanel.imageBeingWorkedOn.getHeight()-1) y = DrawPanel.imageBeingWorkedOn.getHeight()-1;

		if(SwingUtilities.isLeftMouseButton(e)) {
			//remove selection box from the image
			DrawPanel.selection=null;

			//Draw a black circle with a diameter of 10 at the coordinates of the mouse event
			Point coordinatesOfEventRelativeToImage = new Point(x, y);
			DrawPanel.applyPaintBrush(coordinatesOfEventRelativeToImage, 10, paintBrush);

		}
		else if(SwingUtilities.isRightMouseButton(e)) {
			if(DrawPanel.selection==null) {
				DrawPanel.selection = new Rectangle(x, y, 1,1);
				DrawPanel.selectionCoordinate = new Point(x, y);
			}

		}
		//Update the canvas so changes will be visible
		drawPanel.repaint();

		//Update the coordinates of the previous mouse event
		coordinatesOfPreviousMouseEvent = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {

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

}
