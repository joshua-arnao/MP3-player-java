/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mp3musicplayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author joshuaarnaocanessa
 */
public class MusicPlayerGUI extends JFrame {
    // Color configurations
    public static final Color FRAME_COLOR = Color.BLACK;
    public static final Color TEXT_COLOR = Color.WHITE;
    
    private MusicPlayer musicPlayer;
    
    // allow us to use file explorer in our app
    private JFileChooser jFileChooser;
    
    private JLabel songTitle, songArtist;
    private JPanel playbackBtns;
    private JSlider playbackSlider;
    
    
    public MusicPlayerGUI(){
        // Calls JFrama constructor to configure out GUI and set the titile header to "Music Player"
        super("Music Player");
        
        // Set the width and height
        setSize(400, 700);
        
        // End process when app is closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Launch the app at the center of the screen
        setLocationRelativeTo(null);
        
        // Prevent the app from beign resized
        setResizable(false);
        
        // Set layout tu null which allows us to control the (x,y) codinate of the our components 
        // and also set the height and width
        setLayout(null);
        
        // CHANGE FRAME
        getContentPane().setBackground(FRAME_COLOR);
        
        musicPlayer = new MusicPlayer(this);
        jFileChooser = new JFileChooser();
        
        // Set a default path for file explorer
        jFileChooser.setCurrentDirectory(new File("src/resources"));
        
        jFileChooser.setFileFilter(new FileNameExtensionFilter("MP3", "mp3"));
        
        addGuiComponents();
        
        
    }
    
    private void addGuiComponents(){
        addToolbar();
        
        // Load record imag
        JLabel songImage = new JLabel(loadImage("record.png"));
        songImage.setBounds(0,50, getWidth() - 20, 225);
        add(songImage);
        
        // song titlte
        songTitle = new JLabel("Song Title");
        songTitle.setBounds(0, 285, getWidth() - 20, 30);
        songTitle.setFont(new Font("SF Pro", Font.BOLD, 24));
        songTitle.setForeground(TEXT_COLOR);
        songTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(songTitle);
        
        // Song Arrtist
        songArtist = new JLabel("Song Artist");
        songArtist.setBounds(0, 320, getWidth() - 20, 30);
        songArtist.setFont(new Font("SF Pro", Font.PLAIN , 20));
        songArtist.setForeground(TEXT_COLOR);
        songArtist.setHorizontalAlignment(SwingConstants.CENTER);
        add(songArtist);
        
        // Playback slider
        playbackSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        playbackSlider.setBounds(getWidth()/2 - 300/2, 365, 300, 40);
        playbackSlider.setBackground(null);
        add(playbackSlider);
        
        //
        addPlaybackBtns();
    }
    
    private void addToolbar(){
        JToolBar toolBar = new JToolBar();
        toolBar.setBounds(0, 0 , getWidth(),20);
        
        // prevente toolbar from being moved
        toolBar.setFloatable(false);
        
        // Create a menu
        JMenuBar menuBar = new JMenuBar();
        toolBar.add(menuBar);
        
        // Create a Item 1 to menu
        JMenu songMenu = new JMenu("Song");
        menuBar.add(songMenu);
        
        // create a option 1 to Item of menu
        JMenuItem loadSong = new JMenuItem("Load Song");
        loadSong.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int result = jFileChooser.showOpenDialog(MusicPlayerGUI.this);
                File seletedFile = jFileChooser.getSelectedFile();
                
                if(result == JFileChooser.APPROVE_OPTION && seletedFile != null){
                    // Create a song obj based on selected file
                    Song song = new Song(seletedFile.getPath());
                    
                    // load song in music player
                    musicPlayer.loadSong(song);
                    
                    // update song title and artist
                    updateSongTitleAndArtist(song);
                    
                    // update playback slider
                    updatePlaybackSlider(song);
                    
                    // Toggle on pause button and toggle off play button
                    enablePauseButtonDisablePlayButton();
                }
            }
        });
        songMenu.add(loadSong);
        
        // Create a Item 2 to menu
        JMenu playListMenu = new JMenu("Playlist");
        menuBar.add(playListMenu);
        
        // create a option 1 to Item 2 of menu
        JMenuItem createPlayList = new JMenuItem("Create Playlist");
        playListMenu.add(createPlayList);
        
        // create a option 2 to Item 2 of menu
        JMenuItem loadPlaylist = new JMenuItem("Load Playlist");
        playListMenu.add(loadPlaylist);
        
        
        add(toolBar);
    }
    
    private void addPlaybackBtns(){
        playbackBtns = new JPanel();
        playbackBtns.setBounds(0, 435, getWidth() - 10, 80);
        playbackBtns.setBackground(null);
        
        // previous button
        JButton prevButton = new JButton(loadImage("previous.png"));
        prevButton.setBorderPainted(false);
        prevButton.setBackground(null);
        playbackBtns.add(prevButton);
        
        // PlayButton
        JButton playButton = new JButton(loadImage("play.png"));
        playButton.setBorderPainted(false);
        playButton.setBackground(null);
        playButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                // toggle off play button and toggle on pause button
                enablePauseButtonDisablePlayButton();
                
                // Play or resume song
                musicPlayer.playCurrentSong();
            }
        });
        playbackBtns.add(playButton);
        
        // PauseButton
        JButton pauseButton = new JButton(loadImage("pause.png"));
        pauseButton.setBorderPainted(false);
        pauseButton.setBackground(null);
        pauseButton.setVisible(false);
        pauseButton.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e){
               // Toggle off
               enablePauseButtonDisablePauseButton();
               
               // Pause the song
               musicPlayer.pauseSong();
           }
        });
        playbackBtns.add(pauseButton);
        
        // NextButton
        JButton nextButton = new JButton(loadImage("next.png"));
        nextButton.setBorderPainted(false);
        nextButton.setBackground(null);
        playbackBtns.add(nextButton);
        
        add(playbackBtns);
       
    }
    
    // This will be used to update our slider from the music player class
    public void setPlaybackSliderValue(int frame){
        playbackSlider.setValue(frame);
    }
    
    private void updateSongTitleAndArtist(Song song){
        songTitle.setText(song.getSongTitle());
        songArtist.setText(song.getSongArtist());
    }
    
    private void updatePlaybackSlider(Song song){
        // update max count for slider
        playbackSlider.setMaximum(song.getMp3File().getFrameCount());
        
        // Create the song length label
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        
        // beginning will be 00:00
        JLabel labelBeginnig = new JLabel("00:00");
        labelBeginnig.setFont(new Font("SF Pro", Font.BOLD, 18));
        labelBeginnig.setForeground(TEXT_COLOR);
        
        // end will vary depending on the song
        JLabel labelEnd = new JLabel(song.getSongLength());
        labelEnd.setFont(new Font("SF Pro", Font.BOLD, 18));
        labelEnd.setForeground(TEXT_COLOR);
        
        labelTable.put(0, labelBeginnig);
        labelTable.put(song.getMp3File().getFrameCount(), labelEnd);
        
        playbackSlider.setLabelTable(labelTable);
        playbackSlider.setPaintLabels(true);
        
    }
    
    private void enablePauseButtonDisablePlayButton(){
        // retrieve reference to play button from playbackBtns panel
        JButton playButton = (JButton) playbackBtns.getComponent(1);
        JButton pauseButton = (JButton) playbackBtns.getComponent(2);
        
        // turn off play button
        playButton.setVisible(false);
        playButton.setEnabled(false);
        
        // turn on pause button
        pauseButton.setVisible(true);
        pauseButton.setEnabled(true);   
    }
    
    private void enablePauseButtonDisablePauseButton(){
        // retrieve reference to play button from playbackBtns panel
        JButton playButton = (JButton) playbackBtns.getComponent(1);
        JButton pauseButton = (JButton) playbackBtns.getComponent(2);
        
        // turn off play button
        playButton.setVisible(true);
        playButton.setEnabled(true);
        
        // turn on pause button
        pauseButton.setVisible(false);
        pauseButton.setEnabled(false);   
    }
    
    private ImageIcon loadImage(String imagePath){
        try{
            // Read the image file from the given path
            InputStream imageStream = getClass().getClassLoader().getResourceAsStream(imagePath);
            BufferedImage image = ImageIO.read(imageStream);
            
            // returns an image icon so that our component con render the image
            return new ImageIcon(image);
            
        } catch(Exception e){
            e.printStackTrace();
        }
        
        // Coul not find resourse
        return null;
    }
    
    
}


