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

    public static Building mainBuilding[];
    public static int numOfBuildings = 1;
    public static int numOfFloors = 6;
    public static int buildingWidth = 400 + 26*(numOfFloors -1);
    public static int buildingFloorWidth = 400 ;
    public static int buildingHeight = 400;

    public static int floorWidth = (buildingFloorWidth -20)*2/3;
    public static int floorHeight = (buildingHeight-20)/numOfFloors;

    public static int numOfPersonElevators = 2;
    public static int elevatorWidth = floorWidth/(2*numOfPersonElevators + 1);
    public static int elevatorHeight = floorHeight*2/3;
    public static int elevatorCapacity = 5;
    
    public static int keyButtonWidth = 30;
    public static int keyButtonHeight = 15;
    
    public static Pane treePane;
    public static Pane graphicsPane;
    public static Pane root;
    public static Scene scene;

    public int eState = 0;
    
    @Override
    public void start(Stage primaryStage) {
        
        root = new Pane();
        
        treePane = new Pane();
        Rectangle R = new Rectangle(0,0,200,buildingHeight);
        treePane.getChildren().add(R);
        treePane.setLayoutX(10);
        treePane.setLayoutY(10);
        
        graphicsPane = new Pane();
        graphicsPane.setLayoutX(220);
        
        mainBrain = new Brain();
        mainBuilding = new Building[numOfBuildings];
        for(int i=0 ; i<numOfBuildings ; i++){
            mainBuilding[i] = new Building(mainBrain, i,numOfFloors, 1, numOfPersonElevators, elevatorCapacity);
            graphicsPane.getChildren().add(mainBuilding[i].getBuildingPane());
        }
        
        
        
        root.getChildren().add(treePane);
        root.getChildren().add(graphicsPane);
        
        Button btnUp = new Button();
        btnUp.setText("Show Next");
        btnUp.setLayoutY(buildingHeight + 20);
        btnUp.setLayoutX(10);
        btnUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                graphicsPane.getChildren().get(0).toFront();
            }
        });
        
        Button btnDown = new Button();
        btnDown.setText("Show Previous");
        btnDown.setLayoutY(buildingHeight + 20);
        btnDown.setLayoutX(115);
        btnDown.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                graphicsPane.getChildren().get(numOfBuildings-1).toBack();
            }
        });
        
        root.getChildren().add(btnUp);
        root.getChildren().add(btnDown);
        
        scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Elevator Project");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

 
    public static void main(String[] args) {
        launch(args); 
    }
}





//        Button btnUp = new Button();
//        btnUp.setText("Move Up");
//        btnUp.setLayoutY(buildingHeight + 20);
//        btnUp.setLayoutX(20);
//        btnUp.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                mainBuilding[0].addElevatorTask(eState, 5);
//                eState += 1;
//                    if(eState == numOfPersonElevators)
//                        eState = 0;
//            }
//        });
//        
//        Button btnDown = new Button();
//        btnDown.setText("Move Down");
//        btnDown.setLayoutY(buildingHeight + 20);
//        btnDown.setLayoutX(150);
//        btnDown.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                mainBuilding[0].addElevatorTask(eState, 2);
//                eState += 1;
//                    if(eState == numOfPersonElevators)
//                        eState = 0;
//            }
//        });
//
//        root.getChildren().add(btnUp);
//        root.getChildren().add(btnDown);

//        
//        HBox[] keypads = new HBox[numOfFloors];
//        Button[][] B = new Button[numOfFloors][numOfFloors];
//        
//        for(int j=0 ; j<numOfFloors ; j++){
//            keypads[j] = new HBox();
//            for(int i=0 ; i<numOfFloors ; i++){
//                
//                B[j][i] = new Button();
//                B[j][i].setText("" + (i));
//                B[j][i].setPrefSize(keyButtonWidth, keyButtonHeight);
//                
//                B[j][i].setOnAction(new EventHandler<ActionEvent>() {
//                    @Override
//                    public void handle(ActionEvent event) {
//                        
//                        int T = getElevatorFloor( (int)((Button)(event.getSource())).getParent().getLayoutY() );
//                        timerTask.addTarget(T, eState);
//                        T = Integer.parseInt(((Button)(event.getSource())).getText()) ;
//                        timerTask.addTarget(T, eState);
//                        System.out.println("TimerTask started");
//                        
//                        eState += 1;
//                        if(eState == numOfPersonElevators){
//                            eState = 0;
//                        }
//                    }
//                });
//                if(j!=i){
//                    keypads[j].getChildren().add(B[j][i]);
//                }
//                
//            }
//            keypads[j].setLayoutX(buildingWidth + 20);
//            keypads[j].setLayoutY(20 + (numOfFloors-j-1)*floorHeight);
//            root.getChildren().add(keypads[j]);
//        }
//
//        root.getChildren().add(btnUp);
//        root.getChildren().add(btnDown);
//        

//        Build = new Rectangle(10,10,buildingWidth, buildingHeight-0);
//        root.getChildren().add(Build);
//        
//        F = new Rectangle[numOfFloors];
//        for(int i=0 ; i<numOfFloors ; i++){
//            F[i] = new Rectangle(20,20+i*floorHeight,floorWidth, floorHeight-2);
//            F[i].setFill(Color.WHITE);
//            root.getChildren().add(F[i]);
//        }
//        
//        E = new Rectangle[numOfPersonElevators];
//        for(int i=0 ; i<numOfPersonElevators ; i++){
//            E[i] = new Rectangle(getElevatorX(i),getElevatorY(0),elevatorWidth, elevatorHeight);
//            E[i].setFill(Color.BLUE);
//            root.getChildren().add(E[i]);
//        }
// 



/**
 * OLD main()
 */

//        mainBrain = new Brain();
//        mainBuilding = new Building(mainBrain);
//        root.getChildren().add(mainBuilding.getBuildingPane());
        
//        System.out.println("AAAAAA");
//        PersonElevator EL;
//        for(int i=0 ; i<numOfPersonElevators ; i++){
////            System.out.println("new elevator " + i);
//            EL = new PersonElevator(mainBuilding, i, elevatorCapacity);
//            //System.out.println(EL.getCapacity());
//            mainBuilding.addElevator(EL);
//        }
//        System.out.println("Number of Elevators = " + mainBuilding.getNumOfElevators());
//        
//        Floor FL;
//        for(int i=0; i<numOfFloors ; i++){
////            System.out.println("new Floor " + i);
//            FL = new Floor(mainBuilding, i, numOfPersonElevators);
//            mainBuilding.addFloor(FL);
//        }
//        System.out.println("Number of Floors = " + mainBuilding.getNumOfFloors());
//