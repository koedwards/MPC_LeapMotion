package com.lithium;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer {
  public static final String LEFT  = "res/sounds/clap.wav";
  public static final String RIGHT = "res/sounds/cowbell.wav";
  public static final String UP    = "res/sounds/hihat.wav";
  public static final String DOWN  = "res/sounds/kick.wav";
  public static final String TAP   = "res/sounds/snare.wav";
  
  String[] count = {LEFT, RIGHT, UP, DOWN, TAP};
  int i;
  
  HashMap<String,Clip> clips = new HashMap<String,Clip>();

  public SoundPlayer() {
    HashMap<String, AudioInputStream> ais = new HashMap<String, AudioInputStream>();
    try {
      ais.put(LEFT,   AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(LEFT))));
      ais.put(RIGHT,  AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(RIGHT))));
      ais.put(UP,     AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(UP))));
      ais.put(DOWN,   AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(DOWN))));
      ais.put(TAP,    AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(TAP))));
    } catch (UnsupportedAudioFileException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    } catch (IOException e2) {
      e2.printStackTrace();
    }
    
    for (Map.Entry<String, AudioInputStream> entry : ais.entrySet()) {
      String name = entry.getKey();
      AudioInputStream theAis = entry.getValue();
      
      AudioFormat fmt = theAis.getFormat();
      DataLine.Info info = new DataLine.Info(Clip.class, fmt);
      Clip clip = null;
      try {
        clip = (Clip) AudioSystem.getLine(info);
        clip.open(theAis);
      } catch (LineUnavailableException e) {
        e.printStackTrace();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      clips.put(name, clip);
    }
  }
  
  public void adjustVolume(float vel) {
		while (i < count.length-1) {
			FloatControl volume = (FloatControl) clips.get(count[i]).getControl(FloatControl.Type.MASTER_GAIN);
			// all this weirdness is due to the fact that log(0.5) is negative
			// so everything that follows is definitely non-intuitive
			float dB = (float) (Math.log(.5) / Math.log(10.0) * 20.0);
			if ((int)vel < 0) {
				if ((volume.getValue() - dB) > volume.getMaximum()) {
					volume.setValue(volume.getMaximum());
				} else {
					volume.setValue(volume.getValue() - dB);
				}
				i++;
			}
			else {
				if ((volume.getValue() + dB) < volume.getMinimum()) {
					volume.setValue(volume.getMinimum());
				} else {
					volume.setValue(volume.getValue() + dB);
				}
				i++;
			}
		}
		System.out.println("Called adjustVolume");
		i = 0;
	}
	
	public float getGain() {
		FloatControl volume = (FloatControl) clips.get(count[1]).getControl(FloatControl.Type.MASTER_GAIN);
		return volume.getValue();
	}
  
  public void playSound(String sound) {
    Clip theClip = clips.get(sound);
    if (theClip == null) {
      return;
    }
	
	FloatControl volume = (FloatControl) theClip.getControl(FloatControl.Type.MASTER_GAIN);
	System.out.println("volume: " + volume.getMaximum() + "///" + volume.getValue() + "///" + volume.getMinimum());
    
    theClip.setMicrosecondPosition(0L);
    theClip.start();
  }
}