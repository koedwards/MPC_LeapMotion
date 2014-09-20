package com.lithium;

import javax.swing.*;
import com.leapmotion.leap.*;
import java.io.IOException;

public class LithiumGui extends JFrame {

  public LithiumGui() {
  
    setTitle("Lithium");
	setSize(500, 300);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public static void main(String[] args) {
    LithiumGui gui = new LithiumGui();
	gui.setVisible(true);
	
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
