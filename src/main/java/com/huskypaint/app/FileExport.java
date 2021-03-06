package com.huskypaint.app;

import com.huskypaint.app.DrawPanel;

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

public class FileExport extends JButton {
    //public static File selectedFile;

    public FileExport(String type) {
        final String t = type;
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                JFileChooser chooser = new JFileChooser();
                
                int retrival = chooser.showSaveDialog(null);
                if (retrival == JFileChooser.APPROVE_OPTION) {
                    try {
                        BufferedImage img = DrawPanel.getImageBeingWorkedOn();
                    File outputFile = new File(chooser.getSelectedFile() + "." + t);
                    ImageIO.write(img, t, outputFile);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
               

            }
        });

    }

}
