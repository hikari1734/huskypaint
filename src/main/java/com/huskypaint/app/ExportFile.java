package com.huskypaint.app;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;

public class ExportFile extends JButton {

    public ExportFile() {
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                Window.paintFromPointsFile("smilePoints.txt");

                PrintWriter pw = null;
                try {
                    pw = new PrintWriter("output.txt", "UTF-8");
                } catch(IOException e) {
                    e.printStackTrace();
                }

                BufferedImage img = DrawPanel.imageBeingWorkedOn;
                for(int x = 0; x < img.getWidth(); x++) {
                    for(int y = 0; y < img.getHeight(); y++) {
                        pw.printf("%d ", img.getRGB(x,y));
                    }
                    pw.println();
                }

                pw.close();

                //Window.paintFromPointsFile("smilePoints.txt");

            }
        });
    }

}
