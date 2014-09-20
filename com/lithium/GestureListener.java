package com.lithium;

import com.leapmotion.leap.*;

public class GestureListener extends Listener {
  private static SoundPlayer sp;
  
  public static void main(String[] args) {
    // set up listener
    // sp = new SoundPlayer();
  }
  
  private void gestureRecieved(Gesture gesture) {
    // we got a gesture.
    // we want to play a sound.
    // if (gesture == Gesture.LEFT) {
    //   sp.playSound(SoundPlayer.LEFT);
    // }
    // (et c.)
  }
  
  public void onConnect(Controller c) {
    System.out.println("connected");
    //controller.enableGesture();
  }
  
  public void onFrame(Controller c) {
    System.out.println("frame available");
  }
}