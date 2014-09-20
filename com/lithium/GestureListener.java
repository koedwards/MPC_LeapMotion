package com.lithium;

import com.leapmotion.leap.*;

public class GestureListener extends Listener {
  //private SoundPlayer sp;
  
  public GestureListener() {
    //sp = new SoundPlayer();
  }
  
  public void onConnect(Controller c) {
    System.out.println("connected");
    c.enableGesture(Gesture.Type.TYPE_KEY_TAP);
	c.enableGesture(Gesture.Type.TYPE_SWIPE);
  }
  
  public void onFrame(Controller c) {
    //System.out.println("frame available");
	GestureList gesture = c.frame().gestures();
	if (gesture.count() > 0) {
	  if (gesture.get(0).type() == Gesture.Type.TYPE_SWIPE) {
		SwipeGesture swipeGesture = new SwipeGesture(gesture.get(0));
		Vector swipeDirection = swipeGesture.direction();
		  if (swipeDirection.getX() < 0) {
		    System.out.println("Playing Sound 1");
		  }
		  else {
		    System.out.println("Playing Sound 3");
		  }
	  }
	  if (gesture.get(0).type() == Gesture.Type.TYPE_KEY_TAP) {
	    System.out.println("Playing Sound 2");
	  }
	}
	
  }
}