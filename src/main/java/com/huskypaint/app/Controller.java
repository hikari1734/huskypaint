package com.huskypaint.app;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.*;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Controller - This is the class that listens for Mouse Input inside the the DrawPanel.
 *
 * @author Matthew Finzel
 */
public class Controller implements MouseListener,MouseMotionListener{
    JPanel drawPanel;//We need a reference to the DrawPanel used by the program.
    public static Point coordinatesOfPreviousMouseEvent = null;
    public static int diam = 10;
    Color paintBrush = new Color(0,0,0);
    Boolean filling = false;
    Path2D.Double fillPath = new Path2D.Double();
    ArrayList<Path2D> shapes = new ArrayList<>();
    //Rectangle2D rect = new Rectangle(200, 200, 100, 100);


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
        filling = FillButton.getFilling();
        if(!filling) {
            //Draw a black circle with a diameter of 10 at the coordinates of the mouse on the image
            Point coordinatesOfEventRelativeToImage = new Point(e.getPoint().x - DrawPanel.cameraCoords.x, e.getPoint().y - DrawPanel.cameraCoords.y);
            Window.drawPoints.add(coordinatesOfEventRelativeToImage);
            Window.picker.addColorChangeListener(new ColorPicker.ColorChangeListener() {
                public void colorChange(Color newColor) {
                    paintBrush = newColor;
                }
            });
            DrawPanel.applyPaintBrush(coordinatesOfEventRelativeToImage, diam, paintBrush);

            //Update the canvas so changes will be visible
            drawPanel.repaint();

            //Update the coordinates of the previous mouse event
            coordinatesOfPreviousMouseEvent = e.getPoint();
        }
    }

    public void mouseMoved(MouseEvent e) {

        //Update the canvas so changes will be visible
        drawPanel.repaint();
    }

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

    /*
    public void mousePressed(MouseEvent e) {
        //Draw a black circle with a diameter of 10 at the coordinates of the mouse event
        Point coordinatesOfEventRelativeToImage = new Point(e.getPoint().x - DrawPanel.cameraCoords.x, e.getPoint().y - DrawPanel.cameraCoords.y);
        DrawPanel.applyPaintBrush(coordinatesOfEventRelativeToImage, 10, paintBrush);
        System.out.println((int) coordinatesOfEventRelativeToImage.getX() + ", " + (int) coordinatesOfEventRelativeToImage.getY());
    }
    */

    void fillShape(int x, int y, Color tofill, Graphics2D g2){


        Path2D shapeToFill = null;
        for(Path2D shape:shapes){

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

    public void mousePressed(MouseEvent e) {
        filling = FillButton.getFilling();
        if(!filling) {
            //Draw a black circle with a diameter of 10 at the coordinates of the mouse event
            Point coordinatesOfEventRelativeToImage = new Point(e.getPoint().x - DrawPanel.cameraCoords.x, e.getPoint().y - DrawPanel.cameraCoords.y);
            DrawPanel.applyPaintBrush(coordinatesOfEventRelativeToImage, diam, paintBrush);
            Window.drawPoints.add(coordinatesOfEventRelativeToImage);
            //Update the canvas so changes will be visible
            drawPanel.repaint();

            //Update the coordinates of the previous mouse event
            coordinatesOfPreviousMouseEvent = e.getPoint();
        }
    }

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

            //Update the canvas so changes will be visible
            drawPanel.repaint();
            //fillPath.reset();
        }
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
