/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator_project;

/**
 * @author : Ahmed Abdelbasit Mohamed
 * emial : ahmed_abdelbasit94@hotmail.com
 */

public class Screen {
    private final Keypad attachedKeypad;
    private String currentText;
    
    public Screen(Keypad K){
        attachedKeypad = K;
        currentText = " ";
    }
    
    public void setText(String S){
        currentText = S;
    }
    
    public void display(int n){
        
    }
}
