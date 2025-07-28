/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mp3musicplayer;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 *
 * @author joshuaarnaocanessa
 */
public class MusicPlayer {
    // We will need a way to store our song0s details, so we will be creating a song class
    private Song currentSong;
    
    private AdvancedPlayer advancedPlayer;
    
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
    
    public void playCurrentSong(){
        try{
            FileInputStream fileInputStream = new FileInputStream(currentSong.getFilePath());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            
            advancedPlayer = new AdvancedPlayer(bufferedInputStream);
            
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
    
}
