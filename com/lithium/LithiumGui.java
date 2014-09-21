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
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class LithiumGui extends JFrame {
	static JTextPane textArea;
	public static boolean useImages = false;
	public static boolean playBGMusic = false;
	static Clip clip = null;
	public static final String bg = "res/sounds/ascension.wav";
	static HashMap<String, String> textToImages = new HashMap<String, String>();
	
	
	
	public static void addComponentsToPane(Container pane) {
		pane.setLayout(new BorderLayout());
		
		textArea = new JTextPane();
		textArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setPreferredSize(new Dimension(300,300));
		pane.add(scroll, BorderLayout.CENTER);
	}
	
	private static void initMenuBar(JFrame frame) {
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;
		JCheckBoxMenuItem useImagesOption, playBackgroundMusic;
		
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
		
		playBackgroundMusic = new JCheckBoxMenuItem("Play BG Music");
		playBackgroundMusic.setMnemonic(KeyEvent.VK_B);
		playBackgroundMusic.addItemListener(new ActionResponder());
		menu.add(playBackgroundMusic);
		
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
		
		updateTextArea("Welcome to Lithium! \n\nLithium is a virtual MPC that allows you to play a variety of 808 instruments. Explore the tools menu for more options. By default, no images are enabled.\n\nTo get started, wiggle your fingers above the attached Leap Motion sensor.");
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
	
	public static void playBackgroundMusic() {
		AudioInputStream bga = null;

			try {
				bga = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(bg)));
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();	
			}	
			AudioFormat fmt = bga.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, fmt);
			
			
			try {
				clip = (Clip) AudioSystem.getLine(info);
				clip.open(bga);
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			if (clip == null) {
				return;
			}
    
			clip.setMicrosecondPosition(0L);
			clip.start();
		
	}
	
	public static void stopBackgroundMusic() {
		clip.stop();
	}
	
	public static void main(String[] args) {
		textToImages.put("CLAP", 	"res/img/stock-footage-clapping-hands-silhouette-v-white.png");
		textToImages.put("COWBELL", "res/img/cow-bell-md.png");
		textToImages.put("HIHAT", 	"res/img/highhat.png");
		textToImages.put("KICK", 	"res/img/kick.png");
		textToImages.put("SNARE", 	"res/img/snare.png");
		textToImages.put("VOLUME_UP", 	"res/img/uparrow.png");
		textToImages.put("VOLUME_DOWN", 	"res/img/downarrow.png");

	
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
		if (src.getText() == "Use images") {
			LithiumGui.useImages = (e.getStateChange() == ItemEvent.SELECTED);
		}
		else if (src.getText() == "Play BG Music") {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				LithiumGui.playBackgroundMusic();
			}
			else {
				LithiumGui.stopBackgroundMusic();
			}
		}
	}
}