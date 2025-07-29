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
    // We will need a way to store our song0s details, so we will be creating a song class
    private Song currentSong;
    
    private AdvancedPlayer advancedPlayer;
    
    // pause boolean flag used to indicate wheteher the player has been paused
    private boolean isPaused;
    
    // Constructor
    public MusicPlayer(){
        
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
        try{
            FileInputStream fileInputStream = new FileInputStream(currentSong.getFilePath());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            
            // crreate a new advanced player
            advancedPlayer = new AdvancedPlayer(bufferedInputStream);
            advancedPlayer.setPlayBackListener(this);
            
            
            // start music
            startMusicThread();
            
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void startMusicThread(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    advancedPlayer.play();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
 
    @Override
    public void playbackStarted(PlaybackEvent evt){
        super.playbackStarted(evt);
    }
    
    @Override
    public void playbackFinished(PlaybackEvent evt){
        super.playbackFinished(evt);
    }
}
