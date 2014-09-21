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
	static JTextPane textArea;
	public static boolean useImages = false;
	static HashMap<String, String> textToImages = new HashMap<String, String>();
	
	
	
	public static void addComponentsToPane(Container pane) {
		pane.setLayout(new BorderLayout());
		
		textArea = new JTextPane();
		textArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setPreferredSize(new Dimension(250,200));
		pane.add(scroll, BorderLayout.CENTER);
	}
	
	private static void initMenuBar(JFrame frame) {
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;
		JCheckBoxMenuItem useImagesOption;
		
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
		
		useImagesOption = new JCheckBoxMenuItem("Use images");
		useImagesOption.setMnemonic(KeyEvent.VK_S);
		useImagesOption.addItemListener(new ActionResponder());
		menu.add(useImagesOption);
		
		frame.setJMenuBar(menuBar);
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
		
		initMenuBar(frame);
		
		updateTextArea("this is a test string. test. test. test. test.");
	}
	
	public static void updateTextArea(String s) {
		if (useImages) {
			textArea.setText("");
			textArea.setContentType("text/html");
			ImageIcon ico = new ImageIcon(textToImages.get(s));
			textArea.insertIcon(ico);
		} else {
			textArea.setContentType("text/plain");
			textArea.setText(s);
		}
	}
	
	public static void main(String[] args) {
		textToImages.put("CLAP", 	"res/img/stock-footage-clapping-hands-silhouette-v-white.png");
		textToImages.put("COWBELL", "res/img/cow-bell-md.png");
		textToImages.put("HIHAT", 	"res/img/highhat.png");
		textToImages.put("KICK", 	"res/img/kick.png");
		textToImages.put("SNARE", 	"res/img/snare.png");
	
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
		LithiumGui.useImages = (e.getStateChange() == ItemEvent.SELECTED);
	}
}