package program;

import java.awt.Color;
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
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//Update the canvas so changes will be visible
		drawPanel.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//Draw a dot at the coordinates of this event
		DrawPanel.paint(e.getPoint());
		
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
		//Draw a dot at the coordinates of this event
		DrawPanel.paint(e.getPoint());
		
		//Update the canvas so changes will be visible
		drawPanel.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//Update the canvas so changes will be visible
		drawPanel.repaint();
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
