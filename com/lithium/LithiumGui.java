package com.lithium;

import javax.swing.*;
import java.util.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import com.leapmotion.leap.*;
import java.io.IOException;

public class LithiumGui extends JFrame {
	static JTextArea textArea;
	
	public static void addComponentsToPane(Container pane) {
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
 
		textArea = new JTextArea(5, 20);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		pane.add(textArea, c);
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
		menuItem.addActionListener(new ActionResponder());
		menu.add(menuItem);
		
		menu = new JMenu("Tools");
		menu.setMnemonic(KeyEvent.VK_T);
		menu.getAccessibleContext().setAccessibleDescription("Contains tools for the Lithium program");
		menuBar.add(menu);
		
		spectrogramEnable = new JCheckBoxMenuItem("Enable Spectrogram");
		spectrogramEnable.setMnemonic(KeyEvent.VK_S);
		spectrogramEnable.addItemListener(new ActionResponder());
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

class ActionResponder implements ActionListener, ItemListener {
	public void actionPerformed(ActionEvent e) {
		JMenuItem src = (JMenuItem)(e.getSource());
		if (src.getText() == "Exit") {
			System.exit(0);
		}
	}
	
	public void itemStateChanged(ItemEvent e) {
		JMenuItem src = (JMenuItem)(e.getSource());
		System.out.println("itemstate: " + src.getText() + "/" + ((e.getStateChange() == ItemEvent.SELECTED) ? "selected" : "unselected"));
	}
}