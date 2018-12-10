package com.huskypaint.app;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class Resizer extends JPanel implements PropertyChangeListener {
    private int width = 800;
    private int height = 600;
    private JFormattedTextField widthField;
    private JFormattedTextField heightField;
    private JLabel widthLabel;
    private JLabel heightLabel;
    private NumberFormat widthFormat;
    private NumberFormat heightFormat;

    public Resizer(){
        super(new BorderLayout());
        formatSetup();
        widthLabel = new JLabel("width");
        heightLabel = new JLabel("height");
        widthField =  new JFormattedTextField(widthFormat);
        widthField.setValue(new Integer(width));
        widthField.setColumns(10);
        widthField.addPropertyChangeListener("value", this);
        heightField = new JFormattedTextField(heightFormat);
        heightField.setValue(new Integer(height));
        heightField.setColumns(10);
        heightField.addPropertyChangeListener("value", this);
        heightLabel.setLabelFor(heightField);
        widthLabel.setLabelFor(widthField);
        JPanel labelPane =  new JPanel(new GridLayout(0,1));
        labelPane.add(widthLabel);
        labelPane.add(heightLabel);
        JPanel fieldPane =  new JPanel(new GridLayout(0,1));
        fieldPane.add(widthField);
        fieldPane.add(heightField);
        JPanel buttonPane = new JPanel(new GridLayout(0,1));
        applyResize app = new applyResize();
        app.setText("Apply");
        buttonPane.add(app);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        add(labelPane, BorderLayout.CENTER);
        add(fieldPane, BorderLayout.LINE_END);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    private void formatSetup(){
        heightFormat = NumberFormat.getNumberInstance();
        widthFormat = NumberFormat.getNumberInstance();
    }
    public static void createAndShowResizer(){
        JFrame frame = new JFrame("Resizer");
        frame.add(new Resizer());
        frame.pack();
        frame.setVisible(true);
    }

    public class applyResize extends JButton {

        public applyResize() {

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    DrawPanel.setWidth(width);
                    DrawPanel.setHeight(height);
                    DrawPanel.resize();
                }

            });

        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        Object src = e.getSource();
        if(src == widthField){
            width = ((Number)widthField.getValue()).intValue();
        } else if(src == heightField){
            height = ((Number)heightField.getValue()).intValue();
        }
    }
}
