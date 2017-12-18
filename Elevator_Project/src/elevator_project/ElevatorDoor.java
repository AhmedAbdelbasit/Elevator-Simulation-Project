/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator_project;

import static elevator_project.Elevator_Project.elevatorHeight;
import static elevator_project.Elevator_Project.elevatorWidth;
import static elevator_project.Elevator_Project.floorHeight;
import static elevator_project.Elevator_Project.floorWidth;
import static elevator_project.Elevator_Project.numOfPersonElevators;
import java.util.ArrayList;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javafx.event.EventHandler;
import javafx.scene.image.Image;
//import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Poto
 */
public class ElevatorDoor {
    private Floor locatedFloor;
    private Elevator relatedElevator;
    private int doorNumber;

    private Thread doorMotor;
    private ArrayList<Integer> targets;
    
    private static int elevatorDoorWidth = elevatorWidth/2;
    
    private Pane guiElevatorDoor;
    private Rectangle leftDoor;
    private Rectangle rightDoor;
    
    private final int delayTime = 40;
    
    public ElevatorDoor(Floor F, Elevator E, int eNum){
        locatedFloor = F;
        relatedElevator = E;
        doorNumber = eNum;
        
        guiElevatorDoor = new Pane();
        guiElevatorDoor.setLayoutX(getElevatorX(eNum));
        guiElevatorDoor.setLayoutY(getElevatorY());
        Rectangle R = new Rectangle(0,0,elevatorWidth, elevatorHeight-2);
        Image img = new Image("/image/insideElevator.png");
        R.setFill(new ImagePattern(img));
//        R.setFill(Color.BLUE);
        leftDoor = new Rectangle(0,0,elevatorWidth/2, elevatorHeight-2);
        img = new Image("/image/leftDoor.png");
        leftDoor.setFill(new ImagePattern(img));
//        leftDoor.setFill(Color.BROWN);
//        leftDoor.setOpacity(0.5);
        rightDoor = new Rectangle(getRightDoorX(),0,elevatorWidth/2, elevatorHeight-2);
        img = new Image("/image/rightDoor.png");
        rightDoor.setFill(new ImagePattern(img));
//        rightDoor.setFill(Color.BROWN);
        guiElevatorDoor.getChildren().add(R);
        guiElevatorDoor.getChildren().add(leftDoor);
        guiElevatorDoor.getChildren().add(rightDoor);
        
        targets = new ArrayList();
        initiateThread();
        
    }
    
    private void initiateThread(){
        doorMotor = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    while(targets.size() > 0){
                        double p = getLeftDoorX();
    //                    System.out.println(p);
                        if((targets.get(0) == 0) && (p<0)){
                            closeStep();
                        }else if((targets.get(0) == 1) && (p>(-1*elevatorWidth/2))){
                            openStep();
                        }else{
                            if(targets.get(0) == 0){
                                locatedFloor.getBuilding().getElevator(doorNumber).applyNext();
                            }
                            targets.remove(0);
                        }
                        Thread.sleep(delayTime);
                    }
                } catch (Exception e) {
//                    e.printStackTrace();
                }
            }
            
        });
    }
    
    public void addTarget(int order, int nDoor){
        if(targets.size() == 1){
            if(targets.get(0) == 0){
                targets.add(0, order);
            }
        }else if(targets.size() == 0){
            targets.add(order);
            targets.add(0);
        }
        if (doorMotor.isAlive()){
            System.out.println("Alive");
        }else{
            System.out.println("Dead");
//            elevatorMotor.start();
            initiateThread();
            doorMotor.start();
        }
    }
    
    public void addTarget(int i, int order, int nDoor){
        if(targets.size() == 1){
            if(targets.get(0) == 0){
                targets.add(i, order);
            }
        }
        if (doorMotor.isAlive()){
            System.out.println("Alive");
        }else{
            System.out.println("Dead");
//            elevatorMotor.start();
            initiateThread();
            doorMotor.start();
        }
    }
    
    public Pane getGuiContainer(){
        return guiElevatorDoor;
    }
    
    private static int getRightDoorX(){
        return (elevatorWidth - elevatorDoorWidth);
    }
    
    private static int getElevatorX(int i){
                return ((2*elevatorWidth)*(i)+elevatorWidth);
//        return ((floorWidth/(numOfPersonElevators+1))*(i+1)-elevatorWidth/2);
    }
    
    private static int getElevatorY(){
        return (floorHeight - elevatorHeight);
    }
    
    
    public void openDoor(){
        System.out.println("open door");
    }
    
    public void closeDoor(){
        System.out.println("close door");
    }
    
    public double getLeftDoorX(){
//        System.out.println(leftDoor.getLayoutX());
        return leftDoor.getLayoutX();
    }
    
    public void closeStep(){
        leftDoor.setLayoutX(leftDoor.getLayoutX()+1);
        rightDoor.setLayoutX(rightDoor.getLayoutX()-1);
    }
    
    public void openStep(){
        leftDoor.setLayoutX(leftDoor.getLayoutX()-1);
        rightDoor.setLayoutX(rightDoor.getLayoutX()+1);
    }
}
