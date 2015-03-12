/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cornell.mchci;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import diet.client.ConnectionToServer;

/**
 *
 * @author gabeculbertson
 */
public class TestBorderLayout extends javax.swing.JFrame {

    private ConnectionToServer cts;
    public static boolean RIGHT_TO_LEFT = false;
    JFrame frame;
    Container pane;
    int screenWidth;
    int screenHeight;
    
    /**
     * Creates new form TestPanel
     */
    public TestBorderLayout(ConnectionToServer cts) {
        this.cts = cts;
        
        screenWidth = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
        screenHeight = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
        
       // setSize(new Dimension(screenWidth,screenHeight)); //Sets the starting size of the layout
        frame = new JFrame("TestBorderLayout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToPane(frame.getContentPane());
        frame.setExtendedState(frame.MAXIMIZED_BOTH);
        
  
        setContentPane(frame.getContentPane()); 
        pack();
    }
    
    public void addComponentsToPane(Container pane) {
        
        if (!(pane.getLayout() instanceof BorderLayout)) {
            pane.add(new JLabel("Container doesn't use BorderLayout!"));
            return;
        }
         
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(
                    java.awt.ComponentOrientation.RIGHT_TO_LEFT);
        }
        
        JButton taskInfo = new JButton("Task information goes here");
        JButton chatWindow = new JButton ("Chat window goes here");
        JButton surveyPopup = new JButton ("Survey popup goes here");
        
        taskInfo.setPreferredSize(new Dimension(screenWidth*3/11,screenHeight));
        pane.add(taskInfo, BorderLayout.WEST);
        chatWindow.setPreferredSize(new Dimension(screenWidth*5/11,screenHeight));
        pane.add(chatWindow, BorderLayout.CENTER);
       surveyPopup.setPreferredSize(new Dimension(screenWidth*3/11,screenHeight));
        pane.add(surveyPopup, BorderLayout.EAST);
        
//        JButton button = new JButton("Button 1 (PAGE_START)");
//        pane.add(button, BorderLayout.PAGE_START);
//         
//        //Make the center component big, since that's the
//        //typical usage of BorderLayout.
//        button = new JButton("Button 2 (CENTER)");
//        button.setPreferredSize(new Dimension(800, 500));
//        pane.add(button, BorderLayout.CENTER);
//         
//        button = new JButton("Button 3 (LINE_START)");
//        pane.add(button, BorderLayout.LINE_START);
//         
//        button = new JButton("Long-Named Button 4 (PAGE_END)");
//        pane.add(button, BorderLayout.PAGE_END);
//         
//        button = new JButton("5 (LINE_END)");
//        pane.add(button, BorderLayout.LINE_END);
        
        
        
    }
    


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify                     

    // End of variables declaration                   
}
