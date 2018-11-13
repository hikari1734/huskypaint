package com.huskypaint.app;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Window - This is the JFrame that contains the HuskyPaint programs GUI.
 *
 * @author Matthew Finzel
 *
 */
public class Window extends JFrame{
    public static Window frame;
    private static Controller ctrl;
    public static JPanel drawPanel;
    public static Container pane;

    public static ColorPicker picker  = new ColorPicker(Color.BLACK);
    public Window() {
        pane = getContentPane();
        pane.setLayout(new BorderLayout());
    }
    /**
     * Main method. Sets basic things about the window that contains the program.
     */
    public static void main(String[] args) {
        frame = new Window();


        //Make the window default to a size of 800 pixels wide and 600 pixels high
        frame.setSize(20,20);

        //Set the name of the window
        frame.setTitle("Husky Paint");

        //Set the icon of the window
        frame.setIconImage(FileIO.loadImage("/Husky.png"));

        drawPanel = new DrawPanel();
        drawPanel.setBackground(Color.BLACK);
        drawPanel.add(picker);
        FileIO file = new FileIO(drawPanel);
        FileExport png = new FileExport("png");
        png.setText("Export as PNG");
        file.setText("Import");
        //drawPanel.add(file);
       // drawPanel.add(png);

        FileExport jpg = new FileExport("jpg");
        jpg.setText("Export as JPG");

        JButton increaseSize = new JButton("Increase Paintbrush Size");
        increaseSize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Controller.diam += 10;
            }
        });

        JButton decreaseSize = new JButton("Decrease Paintbrush Size");
        decreaseSize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Controller.diam -= 10;
            }
        });

        //drawPanel.add(increaseSize);
        //drawPanel.add(decreaseSize);

        //create menubar
        JMenuBar menubar = new JMenuBar();

        //file import export
        JMenu menu = new JMenu("File");
        menu.add(file);
        menu.add(png);
        menu.add(jpg);
        menubar.add(menu);

        //brush sizes
        JMenu brush = new JMenu("Brush Size");
        brush.add(increaseSize);
        brush.add(decreaseSize);
        menubar.add(brush);

        frame.setJMenuBar(menubar);


        pane.add(drawPanel);

        frame.setFocusable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ctrl = new Controller();
        ctrl.setDrawPanel(drawPanel);

        //Make the window show up on top of whatever other windows are on the screen when the program starts.
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
