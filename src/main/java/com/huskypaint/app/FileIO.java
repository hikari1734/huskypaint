package com.huskypaint.app;

import java.awt.Graphics;
import java.awt.Label;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

public class FileIO extends JButton {
    public static File selectedFile;

    public FileIO(JPanel j) {
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

                BufferedImage origImage = null;

                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        origImage = ImageIO.read(selectedFile);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    //DrawPanel d = new DrawPanel(origImage);
                    //Window.pane.add(d);
                    //DrawPanel.imageBeingWorkedOn = origImage;
                    DrawPanel.setImageBeingWorkedOn(origImage);
                    Window.frame.setTitle(selectedFile.getName()+" - Husky Paint");
                }
            }
        });

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