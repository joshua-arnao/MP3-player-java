/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mp3musicplayer;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

/**
 *
 * @author joshuaarnaocanessa
 */
public class MusicPlayer extends PlaybackListener{
    // Need Reference so thath we can update the gui in this class
    private MusicPlayerGUI musicPlayerGUI;
    
    // We will need a way to store our song0s details, so we will be creating a song class
    private Song currentSong;
    
    private AdvancedPlayer advancedPlayer;
    
    // pause boolean flag used to indicate wheteher the player has been paused
    private boolean isPaused;
    
    // Stores in the last frame when the playback is finished(used for pausin and retorm the song)
    private int currentFrame;
    
    // Track how many millisecond has passed since playing the song (used for updating the slider)
    private int currentTimeInMilli;
    
    // Constructor
    public MusicPlayer(MusicPlayerGUI musicPlayerGUI){
        this.musicPlayerGUI = musicPlayerGUI;
    }
    
    public void loadSong(Song song){
        currentSong = song;
        
        // Play the current song if a not null
        if(currentSong != null){
            playCurrentSong();
        }
    }
    
    public void pauseSong(){
        if(advancedPlayer != null){
            // update isPaused flag
            isPaused = true;
            
            // Then we wannt to stop the player
            stopSong();
        }
    }
    
    public void stopSong(){
        if(advancedPlayer != null){
            advancedPlayer.stop();
            advancedPlayer.close();
            advancedPlayer = null;
        }
    }
    
    public void playCurrentSong(){
        if(currentSong == null) return ;
        try{
            FileInputStream fileInputStream = new FileInputStream(currentSong.getFilePath());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            
            // crreate a new advanced player
            advancedPlayer = new AdvancedPlayer(bufferedInputStream);
            advancedPlayer.setPlayBackListener(this);
            
            
            // start music
            startMusicThread();
            
            // start playback slider
            startPlaybackSliderThread();
            
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void startMusicThread(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    if(isPaused){
                        // resume music form last frame
                        advancedPlayer.play(currentFrame, Integer.MAX_VALUE);
                    } else {
                        // play music form the beginning
                        advancedPlayer.play();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
    // Create a thread that will handle updating the slider
    private void startPlaybackSliderThread(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                while(!isPaused){
                    try{
                        // Increment current time milli;
                        currentTimeInMilli++;

                        // Calculate into frame value
                        int calculatedFrame = (int)((double) currentTimeInMilli * currentSong.getFrameRatePerMilliseconds());

                        // update gui
                        musicPlayerGUI.setPlaybackSliderValue(calculatedFrame);

                        // mimic 1 millisecond using thread.sleep
                        Thread.sleep(1);
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
 
    @Override
    public void playbackStarted(PlaybackEvent evt){
        super.playbackStarted(evt);
        System.out.println("Playback Started @" + currentFrame);
    }
    
    @Override
    public void playbackFinished(PlaybackEvent evt){
        super.playbackFinished(evt);
        System.out.println("Playback Finished");
        
        if(isPaused){
            currentFrame += (int) ((double) evt.getFrame() * currentSong.getFrameRatePerMilliseconds());
            
        }
        System.out.println("Stopped @" + currentFrame);
    }
}
