package com.lithium;

public class SoundPlayer {
  // for swiping left
  public final String LEFT = "sound.wav";
  private Clip left_clip;
  // for swiping right
  public final String RIGHT = "sound2.wav";
  private Clip right_clip;
  // ...
  
  public SoundPlayer() {
    // initialize Clip objects for wav files
    // left_clip = new Clip(LEFT);
    // (...)
    // initialize output device
    // (...)
  }
  
  public void playSound(String sound) {
    // if (sound == LEFT) {
    //  playClip(leftClip);
    // } else {
    //   ...
    // }
  }
  
  private void playClip(Clip c) {
    // os-specific method to play an audio clip
  }
}