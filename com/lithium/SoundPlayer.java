package com.lithium;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer {
  public static final String LEFT  = "res/sounds/clap.wav";
  public static final String RIGHT = "res/sounds/cowbell.wav";
  public static final String UP    = "res/sounds/hihat.wav";
  public static final String DOWN  = "res/sounds/kick.wav";
  public static final String TAP   = "res/sounds/snare.wav";
  
  private AudioInputStream leftClip;
  private AudioInputStream rightClip;
  private AudioInputStream upClip;
  private AudioInputStream downClip;
  private AudioInputStream tapClip;

  public SoundPlayer() {
    try {
      leftClip  = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(LEFT)));
      rightClip = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(RIGHT)));
      upClip    = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(UP)));
      downClip  = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(DOWN)));
      tapClip   = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(TAP)));
    } catch (UnsupportedAudioFileException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    } catch (IOException e2) {
      e2.printStackTrace();
    }
  }
  
  public void playSound(String sound) {
    AudioInputStream theClip;
    switch (sound) {
      case LEFT:
        theClip = leftClip;
        break;
      
      case RIGHT:
        theClip = rightClip;
        break;
      
      case UP:
        theClip = upClip;
        break;
      
      case DOWN:
        theClip = downClip;
        break;
      
      case TAP:
        theClip = tapClip;
        break;
        
      default:
        return;
    }
    
    AudioFormat format = theClip.getFormat();
    DataLine.Info info = new DataLine.Info(Clip.class, format);
    try {
      Clip audioClip = (Clip)AudioSystem.getLine(info);
      audioClip.open(theClip);
      audioClip.start();
    } catch (LineUnavailableException e) {
      e.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }
}