package src;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class MusicManager {
    private Clip currentClip;
    private Clip nextClip;
    private Thread fadeThread;
    private static final double FADE_DURATION = 1.5; // seconds
    private static final float MAX_VOLUME = -10.0f; // dB (0 is max, -80 is min)
    
    /**
     * Play music with fade in effect
     */
    public void playMusic(String musicPath) {
        playMusic(musicPath, true);
    }
    
    /**
     * Play music with optional fade in
     */
    public void playMusic(String musicPath, boolean fadeIn) {
        try {
            File audioFile = new File(musicPath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            
            Clip newClip = AudioSystem.getClip();
            newClip.open(audioStream);
            newClip.loop(Clip.LOOP_CONTINUOUSLY);
            
            // If there's currently playing music, fade it out and switch
            if (currentClip != null && currentClip.isRunning()) {
                nextClip = newClip;
                fadeOutAndSwitch(fadeIn);
            } else {
                currentClip = newClip;
                if (fadeIn) {
                    fadeIn(currentClip);
                } else {
                    setVolume(currentClip, MAX_VOLUME);
                    currentClip.start();
                }
            }
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error playing music: " + musicPath);
            e.printStackTrace();
        }
    }
    
    /**
     * Set volume for a clip in decibels
     */
    private void setVolume(Clip clip, float db) {
        try {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            // Clamp between min and max
            float clampedDb = Math.max(volumeControl.getMinimum(), Math.min(db, volumeControl.getMaximum()));
            volumeControl.setValue(clampedDb);
        } catch (Exception e) {
            // Volume control not available
        }
    }
    
    /**
     * Get current volume of a clip in decibels
     */
    private float getVolume(Clip clip) {
        try {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            return volumeControl.getValue();
        } catch (Exception e) {
            return MAX_VOLUME;
        }
    }
    
    /**
     * Fade in the given clip
     */
    private void fadeIn(Clip clip) {
        setVolume(clip, -80.0f); // Start at minimum
        clip.start();
        
        fadeThread = new Thread(() -> {
            try {
                int steps = (int)(FADE_DURATION * 20); // 20 steps per second
                float volumeRange = MAX_VOLUME - (-80.0f);
                float step = volumeRange / steps;
                
                for (int i = 0; i < steps; i++) {
                    float currentVolume = getVolume(clip);
                    float newVolume = Math.min(currentVolume + step, MAX_VOLUME);
                    setVolume(clip, newVolume);
                    Thread.sleep(50); // 50ms per step
                }
                setVolume(clip, MAX_VOLUME);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        fadeThread.setDaemon(true);
        fadeThread.start();
    }
    
    /**
     * Fade out current clip and switch to next
     */
    private void fadeOutAndSwitch(boolean fadeInNext) {
        Clip oldClip = currentClip;
        
        fadeThread = new Thread(() -> {
            try {
                // Fade out old clip
                int steps = (int)(FADE_DURATION * 20);
                float volumeRange = MAX_VOLUME - (-80.0f);
                float step = volumeRange / steps;
                
                for (int i = 0; i < steps; i++) {
                    float currentVolume = getVolume(oldClip);
                    float newVolume = Math.max(currentVolume - step, -80.0f);
                    setVolume(oldClip, newVolume);
                    Thread.sleep(50);
                }
                
                oldClip.stop();
                oldClip.close();
                
                // Switch to next clip
                if (nextClip != null) {
                    currentClip = nextClip;
                    nextClip = null;
                    
                    if (fadeInNext) {
                        fadeIn(currentClip);
                    } else {
                        setVolume(currentClip, MAX_VOLUME);
                        currentClip.start();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        fadeThread.setDaemon(true);
        fadeThread.start();
    }
    
    /**
     * Stop music with fade out
     */
    public void stopMusic() {
        if (currentClip != null && currentClip.isRunning()) {
            fadeOut(currentClip);
        }
    }
    
    /**
     * Fade out and stop the given clip
     */
    private void fadeOut(Clip clip) {
        fadeThread = new Thread(() -> {
            try {
                int steps = (int)(FADE_DURATION * 20);
                float volumeRange = MAX_VOLUME - (-80.0f);
                float step = volumeRange / steps;
                
                for (int i = 0; i < steps; i++) {
                    float currentVolume = getVolume(clip);
                    float newVolume = Math.max(currentVolume - step, -80.0f);
                    setVolume(clip, newVolume);
                    Thread.sleep(50);
                }
                
                clip.stop();
                clip.close();
                currentClip = null;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        fadeThread.setDaemon(true);
        fadeThread.start();
    }
    
    /**
     * Clean up resources
     */
    public void dispose() {
        if (fadeThread != null && fadeThread.isAlive()) {
            fadeThread.interrupt();
        }
        if (currentClip != null) {
            currentClip.stop();
            currentClip.close();
        }
        if (nextClip != null) {
            nextClip.stop();
            nextClip.close();
        }
    }
}