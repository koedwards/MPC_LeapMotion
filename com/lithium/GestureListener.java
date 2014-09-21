package com.lithium;

import com.leapmotion.leap.*;

public class GestureListener extends Listener {
	private SoundPlayer sp;
	String currentGesture = null;
	private LithiumGui lg;

	public GestureListener() {
		sp = new SoundPlayer();
	}
  
	public void onConnect(Controller c) {
		System.out.println("connected");
		c.enableGesture(Gesture.Type.TYPE_KEY_TAP);
		c.enableGesture(Gesture.Type.TYPE_SWIPE);
	}

	public void onFrame(Controller c) {
		//System.out.println("frame available");
		GestureList gesture = c.frame().gestures();
		if (gesture.get(0).hands().get(0).isRight()) {
		if (gesture.count() > 0) {
			if (currentGesture == null) {
<<<<<<< HEAD
					currentGesture = gesture.get(0).type().toString();
					/*if (gesture.get(0).type() == Gesture.Type.TYPE_SWIPE) {
						SwipeGesture swipeGesture = new SwipeGesture(gesture.get(0));
						Vector swipeDirection = swipeGesture.direction();
						if (swipeDirection.getX() >= swipeDirection.getY()) {
							if (swipeDirection.getX() < 0) {
								sp.playSound(SoundPlayer.LEFT);
							} else {
								sp.playSound(SoundPlayer.RIGHT);
							}
						} else {
							if (swipeDirection.getY() < 0) {
								sp.playSound(SoundPlayer.DOWN);
							} else {
								sp.playSound(SoundPlayer.UP);
							}
						}
					}*/
			
=======
				currentGesture = gesture.get(0).type().toString();
				
>>>>>>> origin/master
				if (gesture.get(0).type() == Gesture.Type.TYPE_KEY_TAP) {
					Finger finger = (new Finger(new KeyTapGesture(gesture.get(0)).pointable()));
					switch (finger.type()) {
						case TYPE_INDEX:
							sp.playSound(SoundPlayer.LEFT);
							lg.updateTextArea("CLAP");
							break;
						
						case TYPE_THUMB:
							sp.playSound(SoundPlayer.RIGHT);
							lg.updateTextArea("COWBELL");
							break;
							
						case TYPE_MIDDLE:
							sp.playSound(SoundPlayer.UP);
							lg.updateTextArea("HIHAT");
							break;
						
						case TYPE_RING:
							sp.playSound(SoundPlayer.DOWN);
							lg.updateTextArea("KICK");
							break;
						
						case TYPE_PINKY:
							sp.playSound(SoundPlayer.TAP);
							lg.updateTextArea("SNARE");
							break;
						
						default:
							break;
					}
					
					System.out.println("type: " + finger.type());
				}
			}
		} else {
			currentGesture = null;
		}
		}
		if (gesture.get(0).hands().get(0).isLeft()) {
			Vector handCenter = gesture.get(0).hands().get(0).palmVelocity();
			if (handCenter.getY() > 0) {
				lg.updateTextArea("Increase Volume " + handCenter.getY());
				sp.adjustVolume(handCenter.getY());
			}
			else {
				lg.updateTextArea("Decrease Volume " + handCenter.getY());
				sp.adjustVolume(handCenter.getY());
			}
		}
		
	}
}