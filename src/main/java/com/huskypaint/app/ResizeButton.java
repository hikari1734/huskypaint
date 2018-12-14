package com.huskypaint.app;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.huskypaint.app.Resizer.*;

public class ResizeButton extends JButton {

    public ResizeButton() {

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                createAndShowResizer();
            }

        });

    }
}
