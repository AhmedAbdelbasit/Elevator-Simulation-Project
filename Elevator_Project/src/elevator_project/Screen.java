/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator_project;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 * @author : Ahmed Abdelbasit Mohamed
 * emial : ahmed_abdelbasit94@hotmail.com
 */

public class Screen {
    private final Keypad attachedKeypad;
    private String currentText;
    
    private Pane guiScreen;
    private Label screenDisplay;
    
    public Screen(Keypad K){
        
        attachedKeypad = K;
        guiScreen = new Pane();
        screenDisplay = new Label();
//        screenDisplay.setWrapText(false);
//        screenDisplay.setPrefWidth(30);
//        screenDisplay.setPrefHeight(25);
//        screenDisplay.setTextAlignment(TextAlignment.CENTER);
//        screenDisplay.setBorder();
        screenDisplay.setLayoutX(10);
        screenDisplay.setText("--");
//        screenDisplay.setTextFill(Color.WHITE);
        screenDisplay.setStyle(""
                + "-fx-background-color: #FFFFFF;"
                + "-fx-font: 20px \"Serif\";"
                + "-fx-text-alignment : \"center\";"
                + "");
        guiScreen.getChildren().add(screenDisplay);
        
        currentText = "-";
    }
    
    public void setText(String S){
        currentText = S;
    }
    
    public void display(int n){
        screenDisplay.setText(""+ (char)(65+n));
    }
    
    public Pane getGuiContainer(){
        return guiScreen;
    }
}
