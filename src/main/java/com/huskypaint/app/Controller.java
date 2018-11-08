package com.huskypaint.app;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

/**
 * Controller - This is the class that listens for Mouse Input inside the the DrawPanel.
 *
 * @author Matthew Finzel
 */
public class Controller implements MouseListener,MouseMotionListener{
    JPanel drawPanel;//We need a reference to the DrawPanel used by the program.
    public static Point coordinatesOfPreviousMouseEvent = null;
    Color paintBrush = new Color(0,0,0);


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
    }

    public void mouseDragged(MouseEvent e) {
        //Draw a black circle with a diameter of 10 at the coordinates of the mouse on the image
        Point coordinatesOfEventRelativeToImage = new Point(e.getPoint().x-DrawPanel.cameraCoords.x, e.getPoint().y-DrawPanel.cameraCoords.y);

        Window.picker.addColorChangeListener(new ColorPicker.ColorChangeListener(){
            public void colorChange(Color newColor){
                paintBrush = newColor;
            }
        });
        DrawPanel.applyPaintBrush(coordinatesOfEventRelativeToImage, 10, paintBrush);

        //Update the canvas so changes will be visible
        drawPanel.repaint();

        //Update the coordinates of the previous mouse event
        coordinatesOfPreviousMouseEvent = e.getPoint();
    }

    public void mouseMoved(MouseEvent e) {

        //Update the canvas so changes will be visible
        drawPanel.repaint();
    }

    public void mouseClicked(MouseEvent e) {
        //Update the canvas so changes will be visible
        drawPanel.repaint();
    }

    public void mousePressed(MouseEvent e) {
        //Draw a black circle with a diameter of 10 at the coordinates of the mouse event
        Point coordinatesOfEventRelativeToImage = new Point(e.getPoint().x-DrawPanel.cameraCoords.x, e.getPoint().y-DrawPanel.cameraCoords.y);
        DrawPanel.applyPaintBrush(coordinatesOfEventRelativeToImage, 10, paintBrush);

        //Update the canvas so changes will be visible
        drawPanel.repaint();

        //Update the coordinates of the previous mouse event
        coordinatesOfPreviousMouseEvent = e.getPoint();
    }

    public void mouseReleased(MouseEvent e) {
        //Update the canvas so changes will be visible
        drawPanel.repaint();

        //Update the coordinates of the previous mouse event to null since the button has been released since then
        coordinatesOfPreviousMouseEvent = null;
    }

    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

}