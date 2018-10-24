package com.huskypaint.app;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;

public class ColorPicker extends JButton{
    private Color curColor;

    public ColorPicker(Color c){
        setColor(c);
        addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                Color newC = JColorChooser.showDialog(null, "Color", curColor);
                setColor(newC);
            }
        });

    }

    public Color getCurrentColor(){
        return curColor;
    }
    public void setColor(Color newColor){
        setSelectedColor(newColor, true);
    }
    public void setSelectedColor(Color newColor, boolean notifyListeners){
        if(newColor == null){
            return;
        }
        curColor = newColor;
        setIcon(createIcon(curColor, 16, 16));
        repaint();

        if(notifyListeners){
            for(ColorChangeListener l : listeners){
                l.colorChange(newColor);
            }
        }
    }

    public static interface ColorChangeListener{
        public void colorChange(Color newColor);
    }

    private List<ColorChangeListener> listeners = new ArrayList<ColorChangeListener>();

    public void addColorChangeListener(ColorChangeListener listenerToAdd){
        listeners.add(listenerToAdd);
    }

    public static ImageIcon createIcon(Color iconColor, int width, int height){
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(iconColor);
        g.fillRect(0, 0, 10, 10);
        g.setXORMode(Color.DARK_GRAY);
        g.drawRect(0, 0, 10, 10);
        image.flush();
        ImageIcon icon = new ImageIcon(image);
        return icon;

    }
}