/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mp3musicplayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

/**
 *
 * @author joshuaarnaocanessa
 */

// Class used to describe a song
public class Song {
    private String songTitle;
    private String songArtist;
    private String songLength;
    private String filePath;
    
    public Song(String resourceName){
        // this.filePath = filePath;
        try{
            // use the jaudiotagger library to create an audiofile object to read mp3 file's information
            InputStream input = getClass().getClassLoader().getResourceAsStream(resourceName);
            if(input == null ||  input.equals("")){
                System.out.println("Archivo no encontrado en recursos: " + resourceName);
                songTitle = "N/A";
                songArtist = "N/A";
            }
            
            File tempFile = File.createTempFile("Song-", ".mp3");
            tempFile.deleteOnExit();
            try(FileOutputStream out = new FileOutputStream(tempFile)){
                byte[] buffer = new byte[1024];
                int length;
                
                while((length = input.read(buffer))>0){
                    out.write(buffer, 0, length);
                }
            }
            
            AudioFile audioFile = AudioFileIO.read(tempFile);
            
            // read through the meta data of the audio file
            Tag tag = audioFile.getTag();
            if(tag != null){
                songTitle = tag.getFirst(FieldKey.TITLE);
                songArtist = tag.getFirst(FieldKey.ARTIST);
            } else {
                // could not read through mp3 files's
                songTitle = "N/A";
                songArtist = "N/A";
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public String getSongLength() {
        return songLength;
    }

    public void setSongLength(String songLength) {
        this.songLength = songLength;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
