/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator_project;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.shape.*;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.Timer;
import javafx.scene.paint.Color;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author : Ahmed Abdelbasit Mohamed
 * emial : ahmed_abdelbasit94@hotmail.com
 */

public class Elevator_Project extends Application {
    
    public static Brain mainBrain;
    
    public static Rectangle Build ;
    public static Building mainBuilding;
    public static int buildingWidth = 500;
    public static int buildingHeight = 450;
    
    public static Rectangle[] F ;
    public static Floor[] FL;
    public static int floorWidth = buildingWidth -20;
    public static int numOfFloors = 5;
    public static int floorHeight = (buildingHeight-20)/numOfFloors;
    
    public static Rectangle[] E ;
    public static PersonElevator[] EL;
    public static int numOfPersonElevators = 4;
    public static int elevatorWidth = 40;
    public static int elevatorHeight = floorHeight*2/3;
    public static int elevatorCapacity = 5;
    
    public static Pane root;
    public static Scene scene;
    
    private static MyTimerTask timerTask;
    private static Timer timer;
    
    public int eState = 0;
    
    @Override
    public void start(Stage primaryStage) {
                
        root = new Pane();
                
        Build = new Rectangle(10,10,buildingWidth, buildingHeight);
        root.getChildren().add(Build);
        
        F = new Rectangle[numOfFloors];
        for(int i=0 ; i<numOfFloors ; i++){
            F[i] = new Rectangle(20,20+i*floorHeight,floorWidth, floorHeight-2);
            F[i].setFill(Color.WHITE);
            root.getChildren().add(F[i]);
        }
        
        E = new Rectangle[numOfPersonElevators];
        for(int i=0 ; i<numOfPersonElevators ; i++){
            E[i] = new Rectangle(getElevatorX(i),getElevatorY(0),elevatorWidth, elevatorHeight);
            E[i].setFill(Color.BLUE);
            root.getChildren().add(E[i]);
        }
              
        timerTask = new MyTimerTask();
        timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, 50);
        for(int i=0 ; i< numOfPersonElevators ; i++){
            timerTask.setTarget(0,i);
        }
        
        Button btnUp = new Button();
        btnUp.setText("Move Up");
        btnUp.setLayoutY(buildingHeight + 10);
        btnUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int T = getElevatorFloor((int)E[eState].getY()) +1;
                if(T<numOfFloors){
                    timerTask.setTarget(T, eState);
                    System.out.println("TimerTask started");
                }
                eState += 1;
                    if(eState == numOfPersonElevators)
                        eState = 0;
            }
        });
        
        Button btnDown = new Button();
        btnDown.setText("Move Down");
        btnDown.setLayoutY(buildingHeight + 10);
        btnDown.setLayoutX(150);
        btnDown.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int T = getElevatorFloor((int)E[eState].getY()) -1;
                if(T>=0){
                    timerTask.setTarget(T, eState);
                    System.out.println("TimerTask started");
                }
                eState += 1;
                    if(eState == numOfPersonElevators)
                        eState = 0;
            }
        });
        
        root.getChildren().add(btnUp);
        root.getChildren().add(btnDown);
        
        
        scene = new Scene(root, 600, 600);
        
        primaryStage.setTitle("Elevator Project");
        primaryStage.setScene(scene);
        primaryStage.show();
        
               
    }
    
    public static int getElevatorX(int i){
        return (20+(floorWidth/(numOfPersonElevators+1))*(i+1)-elevatorWidth/2);
    }
    
    public static int getElevatorY(int f){
        return (20+(numOfFloors)*floorHeight-elevatorHeight);
    }
    
    public static int getElevatorFloor(int y){
        int floor = numOfFloors - (y/floorHeight) - 1;
        return floor;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        mainBrain = new Brain();
        mainBuilding = new Building(mainBrain);
        int numOfFloors = 10;
        int numOfPersonElevators = 2;
        int elevatorCapacity = 5;
        
        EL = new PersonElevator[numOfPersonElevators];
        for(int i=0 ; i<numOfPersonElevators ; i++){
            System.out.println("new elevator");
            EL[i] = new PersonElevator(mainBuilding, 0, elevatorCapacity);
            System.out.println(EL[i].getCapacity());
            mainBuilding.addElevator(EL[i]);
        }
        
        
        FL = new Floor[numOfFloors];
        for(int i=0; i<numOfFloors ; i++){
            FL[i] = new Floor(mainBuilding, i, numOfPersonElevators);
            mainBuilding.addFloor(FL[i]);
        }
        
        launch(args); 
    }
}
