/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator_project;

import static elevator_project.Elevator_Project.floorWidth;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
//import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

/**
 * @author : Ahmed Abdelbasit Mohamed
 * emial : ahmed_abdelbasit94@hotmail.com
 */

public class Keypad {
    private final Brain brain;
    private final Floor locatedFloor;
    private final Screen attachedScreen;
    
    private VBox guiKeypad;
    private HBox guiKeyHolder;
    private ArrayList<Button> listOfButtons;
    
    
    public Keypad(Floor F){
        locatedFloor = F;
        Screen S = new Screen(this);
        attachedScreen = S;
        brain = this.locatedFloor.getBuilding().getBrain();
        
        int nOfFloors = F.getBuilding().getNumOfFloors();
        listOfButtons = new ArrayList();
        Button B;
        guiKeyHolder = new HBox();
        for(int i=0 ; i<nOfFloors ; i++){
            B = new Button();
            
            B.setText("" + i);
            B.setPrefWidth(25);
            B.setPrefHeight(2);
            B.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int f = Integer.parseInt(((Button)(event.getSource())).getText());
//                    int x = Integer.parseInt(listOfButtons.get(i).getText());
                    System.out.println("from " + F.getFloorNumber() + " to " + f);
                    F.getBuilding().addElevatorTask(0,F.getFloorNumber());
                    F.getBuilding().addElevatorTask(0,f);
                }
            });
            listOfButtons.add(B);
            if(i != F.getFloorNumber()){
                guiKeyHolder.getChildren().add(B);
            }
        }
        
        guiKeypad = new VBox();
        guiKeypad.getChildren().add(guiKeyHolder);
        
        guiKeypad.setLayoutX(floorWidth + 10);
    }
    
    public VBox getGuiContainer(){
        return guiKeypad;
    }
    
    public void sendRequest(Keypad K, Request R){
        
    }
    
    public void displayResponse(int R){
        attachedScreen.display(R);
    }
}
