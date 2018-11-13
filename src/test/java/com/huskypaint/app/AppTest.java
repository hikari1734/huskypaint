package com.huskypaint.app;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static com.huskypaint.app.DrawPanel.createBlankImage;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private Robot robot;
    private Window window;

    /* Global Variables */
    public static Window frame;
    private static Controller ctrl;
    public static JPanel drawPanel;
    public static Container pane;
    public static ColorPicker picker  = new ColorPicker(Color.BLACK);

    @Before
    public void setUp() {
        try {
            robot = new Robot();
            window = new Window();

        } catch (AWTException e) {
            e.printStackTrace();
            return;
        }
    }

    @Test
    public void alwaysTrue() {
        assert(true);
    }

    @Test
    public void testColorPicker() {
        ColorPicker cp = new ColorPicker(Color.red);
        assert(cp.getCurrentColor().equals(Color.red));
    }

    @Test
    public void testLoadImage() {
        Image img = FileIO.loadImage("/Husky.png");
        assert(img != null);
    }

    @Test
    public void testBlankCanvas() {
        DrawPanel.imageBeingWorkedOn = createBlankImage(800, 600);
        BufferedImage res = DrawPanel.imageBeingWorkedOn;
        assert(res.getWidth() == 800);
        assert(res.getHeight() == 600);
    }

    @Test
    public void testDrawSmile() {
        //initProgram();

        drawPanel = new DrawPanel();

        ArrayList<Point> pointList = readPointsFromFile("smilePoints.txt");

        for(Point point : pointList) {
            DrawPanel.applyPaintBrush(point, 10, Color.black);
            drawPanel.repaint();
        }

        BufferedImage testImage = loadImageFromFile("output.txt");

        assert(compareBufferedImages(testImage, DrawPanel.imageBeingWorkedOn));
    }


    /* TEST SUPPORT METHODS */
    private ArrayList<Point> readPointsFromFile(String fileName) {
        ArrayList<Point> pointList = new ArrayList<Point>();

        File pointsFile = new File(fileName);
        Scanner scan = null;
        try {
            scan = new Scanner(pointsFile);
        } catch(IOException e) {
            e.printStackTrace();
        }

        int x;
        int y;
        String[] line;
        while(scan.hasNextLine()) {
            line = scan.nextLine().split(", ");
            x = Integer.parseInt(line[0]);
            y = Integer.parseInt(line[1]);
            pointList.add(new Point(x, y));
        }

        return pointList;
    }

    private BufferedImage loadImageFromFile(String fileName) {

        BufferedImage image = createBlankImage(800, 600);

        File file = new File(fileName);
        Scanner scan = null;
        try {
            scan = new Scanner(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] line;
        int rgbVal;
        int x = 0;
        int y = 0;
        while(scan.hasNextLine()) {
            line = scan.nextLine().split(" ");
            for(String rgbString : line) {
                if(rgbString.equals("")) continue;
                rgbVal = Integer.parseInt(rgbString);
                image.setRGB(x, y, rgbVal);
                //System.out.println("x = " + x + ", y = " + y);
                y++;
            }
            x++;
            y = 0;
        }

        return image;
    }

    private boolean compareBufferedImages(BufferedImage img1, BufferedImage img2) {
        if(img1.getHeight() != img2.getHeight() || img1.getWidth() != img2.getWidth()) return false;
        for(int x = 0; x < img1.getWidth(); x++) {
            for(int y = 0; y < img1.getHeight(); y++) {
                if(img1.getRGB(x,y) != img2.getRGB(x,y)) {
                    System.out.println("Failed at <"+x+","+y+"> img1 rgb = "+img1.getRGB(x,y)+", img2 rgb = "+img2.getRGB(x,y));
                    return false;
                }
            }
        }

        return true;
    }

    private void initProgram() {
        frame = new Window();

        pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());


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
        file.setText("Import");
        ExportFile exportFile = new ExportFile();
        exportFile.setText("Export");
        drawPanel.add(file);
        drawPanel.add(exportFile);
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
