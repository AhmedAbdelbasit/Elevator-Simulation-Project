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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
//import java.util.Timer;
import javafx.scene.paint.Color;
//import java.util.Date;
import java.util.Timer;
//import java.util.TimerTask;

/**
 * @author : Ahmed Abdelbasit Mohamed
 * emial : ahmed_abdelbasit94@hotmail.com
 */

public class Elevator_Project extends Application {
    
    public static Brain mainBrain;
    
    public static Rectangle Build ;
    public static Building mainBuilding;
    public static int buildingWidth = 500;
    public static int buildingHeight = 600;
    
    public static Rectangle[] F ;
    public static int floorWidth = buildingWidth -20;
    public static int numOfFloors = 6;
    public static int floorHeight = (buildingHeight-20)/numOfFloors;
    
    public static Rectangle[] E ;
    public static int numOfPersonElevators = 5;
    public static int elevatorWidth = 40;
    public static int elevatorHeight = floorHeight*3/4;
    public static int elevatorCapacity = 5;
    
    public static int keyButtonWidth = 30;
    public static int keyButtonHeight = 15;
    
    public static Pane root;
    public static Scene scene;
    
    private static MyTimerTask timerTask;
    private static Timer timer;
    
    public int eState = 0;
    
    @Override
    public void start(Stage primaryStage) {
                
        root = new Pane();
                
        Build = new Rectangle(10,10,buildingWidth, buildingHeight-0);
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
        timer.scheduleAtFixedRate(timerTask, 0, 20);
        for(int i=0 ; i< numOfPersonElevators ; i++){
            timerTask.addTarget(0,i);
        }
        
        Button btnUp = new Button();
        btnUp.setText("Move Up");
        btnUp.setLayoutY(buildingHeight + 10);
        btnUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int T = getElevatorFloor((int)E[eState].getY()) +1;
                if(T<numOfFloors){
                    timerTask.addTarget(T, eState);
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
                    timerTask.addTarget(T, eState);
                    System.out.println("TimerTask started");
                }
                eState += 1;
                    if(eState == numOfPersonElevators)
                        eState = 0;
            }
        });
        
        HBox[] keypads = new HBox[numOfFloors];
        Button[][] B = new Button[numOfFloors][numOfFloors];
        
        for(int j=0 ; j<numOfFloors ; j++){
            keypads[j] = new HBox();
            for(int i=0 ; i<numOfFloors ; i++){
                
                B[j][i] = new Button();
                B[j][i].setText("" + (i));
                B[j][i].setPrefSize(keyButtonWidth, keyButtonHeight);
                
                B[j][i].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        
                        int T = getElevatorFloor( (int)((Button)(event.getSource())).getParent().getLayoutY() );
                        timerTask.addTarget(T, eState);
                        T = Integer.parseInt(((Button)(event.getSource())).getText()) ;
                        timerTask.addTarget(T, eState);
                        System.out.println("TimerTask started");
                        
                        eState += 1;
                        if(eState == numOfPersonElevators){
                            eState = 0;
                        }
                    }
                });
                if(j!=i){
                    keypads[j].getChildren().add(B[j][i]);
                }
                
            }
            keypads[j].setLayoutX(buildingWidth + 20);
            keypads[j].setLayoutY(20 + (numOfFloors-j-1)*floorHeight);
            root.getChildren().add(keypads[j]);
        }
        
        
        
        
        root.getChildren().add(btnUp);
        root.getChildren().add(btnDown);
        
        
        
        scene = new Scene(root, buildingWidth + 40 + numOfFloors*keyButtonWidth, buildingHeight + 40);
        
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
        
        PersonElevator EL;
        for(int i=0 ; i<numOfPersonElevators ; i++){
//            System.out.println("new elevator " + i);
            EL = new PersonElevator(mainBuilding, i, elevatorCapacity);
            //System.out.println(EL.getCapacity());
            mainBuilding.addElevator(EL);
        }
        System.out.println("Number of Elevators = " + mainBuilding.getNumOfElevators());
        
        Floor FL;
        for(int i=0; i<numOfFloors ; i++){
//            System.out.println("new Floor " + i);
            FL = new Floor(mainBuilding, i, numOfPersonElevators);
            mainBuilding.addFloor(FL);
        }
        System.out.println("Number of Floors = " + mainBuilding.getNumOfFloors());
        
        launch(args); 
    }
}
