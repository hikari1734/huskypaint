package com.huskypaint.app;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FillButton extends JButton {
    static Boolean filling = false;
    public FillButton() {

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (getFilling()) {
                    setFilling(false);
                    setText("Fill");

                } else {
                    setFilling(true);
                    setText("Fill is On");

                }
            }

        });

    }
    void setFilling(Boolean fill){
        filling = fill;
    }
    public static Boolean getFilling(){
        return filling;
    }
}

