/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mp3musicplayer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

/**
 *
 * @author joshuaarnaocanessa
 */
public class MusicPlayerGUI extends JFrame {
    // Color configurations
    public static final Color FRAME_COLOR = Color.BLACK;
    public static final Color TEXT_COLOR = Color.WHITE;
    
    
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
        
        
        addGuiComponents();
    }
    
    private void addGuiComponents(){
        addToolbar();
        
        // Load record imag
        JLabel songImage = new JLabel(loadImage("record.png"));
        songImage.setBounds(0,50, getWidth() - 20, 225);
        add(songImage);
        
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


