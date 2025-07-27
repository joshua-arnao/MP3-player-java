/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mp3musicplayer;
import javax.swing.SwingUtilities;

/**
 *
 * @author joshuaarnaocanessa
 */
public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                new MusicPlayerGUI().setVisible(true);
            }
        
        });
        

    }
    

}
