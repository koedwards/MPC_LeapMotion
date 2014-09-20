package com.lithium;

import javax.swing.*;
import java.util.*;
import java.awt.*;

import java.awt.event.KeyEvent;

import com.leapmotion.leap.*;
import java.io.IOException;

public class LithiumGui extends JFrame {
	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	final static boolean RIGHT_TO_LEFT = false;
	static JTextArea textArea;
	
	public static void addComponentsToPane(Container pane) {
		if (RIGHT_TO_LEFT) {
			pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		}

		JButton button;
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		if (shouldFill) {
			//natural height, maximum width
			c.fill = GridBagConstraints.HORIZONTAL;
		}
 
		button = new JButton("Button 1");
		if (shouldWeightX) {
			c.weightx = 0.5;
		}
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		pane.add(button, c);
 
		button = new JButton("Button 2");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		pane.add(button, c);
 
		button = new JButton("Button 3");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 0;
		pane.add(button, c);
 
		textArea = new JTextArea(5, 20);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		pane.add(textArea, c);
 
		button = new JButton("5");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;			 //reset to default
		c.weighty = 1.0;	 //request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		c.insets = new Insets(10,0,0,0);	//top padding
		c.gridx = 1;			 //aligned with button 2
		c.gridwidth = 2;	 //2 columns wide
		c.gridy = 2;			 //third row
		pane.add(button, c);
		}

	private static void createAndShowGUI() {
		//Create and set up window
		JFrame frame = new JFrame("Lithium");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		//Set up the content pane.
		addComponentsToPane(frame.getContentPane());
	
		//Display the window.
		frame.pack();
		frame.setVisible(true);
		
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;
		JCheckBoxMenuItem spectrogramEnable;
		
		menuBar = new JMenuBar();
		
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(menu);
		
		menuItem = new JMenuItem("Exit", KeyEvent.VK_Q);
		menuItem.getAccessibleContext().setAccessibleDescription("Exits the program");
		menu.add(menuItem);
		
		menu = new JMenu("Tools");
		menu.setMnemonic(KeyEvent.VK_T);
		menu.getAccessibleContext().setAccessibleDescription("Contains tools for the Lithium program");
		menuBar.add(menu);
		
		spectrogramEnable = new JCheckBoxMenuItem("Enable Spectrogram");
		spectrogramEnable.setMnemonic(KeyEvent.VK_S);
		menu.add(spectrogramEnable);
		
		frame.setJMenuBar(menuBar);
	}
	
	public static void updateTextArea(String s) {
		textArea.setText(s);
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	
		Controller controller = new Controller();
		GestureListener gl = new GestureListener();
		
		controller.addListener(gl);
		
		System.out.println("press enter to quit");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		controller.removeListener(gl);	
	}
}