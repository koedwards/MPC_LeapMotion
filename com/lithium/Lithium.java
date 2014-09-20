package com.lithium;

import com.leapmotion.leap.*;

import java.io.IOException;

public class Lithium {
  public static void main(String[] args) {
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