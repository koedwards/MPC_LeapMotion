package com.lithium;

import java.io.IOException;

public class SoundTester {
  public static void main(String[] args) {
    SoundPlayer sp = new SoundPlayer();
    try {
      System.out.println("left");
      sp.playSound(SoundPlayer.LEFT);
      Thread.sleep(1000);
	  
	  System.out.println("left");
      sp.playSound(SoundPlayer.LEFT);
      Thread.sleep(1000);
	  
	  System.out.println("left");
      sp.playSound(SoundPlayer.LEFT);
      Thread.sleep(1000);
	  
	  System.out.println("left");
      sp.playSound(SoundPlayer.LEFT);
      Thread.sleep(1000);
      
      System.out.println("right");
      sp.playSound(SoundPlayer.RIGHT);
      Thread.sleep(1000);
      
      System.out.println("up");
      sp.playSound(SoundPlayer.UP);
      Thread.sleep(1000);
      
      System.out.println("down");
      sp.playSound(SoundPlayer.DOWN);
      Thread.sleep(1000);
      
      System.out.println("tap");
      sp.playSound(SoundPlayer.TAP);
      Thread.sleep(1000);
    } catch (InterruptedException e1) {
      e1.printStackTrace();
    }
  }
}