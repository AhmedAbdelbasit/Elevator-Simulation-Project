/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator_project;

/**
 *
 * @author Poto
 */
import static elevator_project.Elevator_Project.elevatorWidth;
import java.util.ArrayList;

//import java.util.Date;
//import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;

public class DoorTimer extends TimerTask {
    private ArrayList<Integer>[] targets;
    private Floor floor;
    private ArrayList<ElevatorDoor> guiDoorList;
    private int nOfElevators;
    
    public DoorTimer(Floor F){
        floor  = F;
        nOfElevators = F.getBuilding().getNumOfElevators();
        guiDoorList = new ArrayList();
        
        for(int i=0 ; i<nOfElevators ; i++){
            guiDoorList.add(F.getElevatorDoor(i));
        }
        targets = new ArrayList[nOfElevators];
        for(int i=0 ; i<nOfElevators ; i++){
            targets[i] = new ArrayList();
        }

    }
    
    public void addTarget(int order, int nDoor){
        targets[nDoor].add(order);
    }
    
    public void addTarget(int i, int order, int nDoor){
        if(targets[nDoor].size() > 0){
            if(targets[nDoor].get(0) == 0){
                targets[nDoor].add(i, order);
            }
        }
    }
    
    @Override
    public void run() {
        completeTask();
    }

    private void completeTask() {
        try {
            for(int doorNum=0 ; doorNum<nOfElevators ; doorNum++){
                
                if(targets[doorNum].size() > 0){
                    double p = guiDoorList.get(doorNum).getLeftDoorX();
//                    System.out.println(p);
                    if((targets[doorNum].get(0) == 0) && (p<0)){
                        guiDoorList.get(doorNum).closeStep();
                    }else if((targets[doorNum].get(0) == 1) && (p>(-1*elevatorWidth/2))){
                        guiDoorList.get(doorNum).openStep();
                    }else{
                        if(targets[doorNum].get(0) == 0){
                            floor.getBuilding().getElevator(doorNum).applyNext();
                        }
                        targets[doorNum].remove(0);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//TimerTask timerTask = new MyTimerTask();
//        //running timer task as daemon thread
//        Timer timer = new Timer(true);
//        timer.scheduleAtFixedRate(timerTask, 0, 10*1000);
//        System.out.println("TimerTask started");
//        //cancel after sometime
//        try {
//            Thread.sleep(120000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        timer.cancel();
//        System.out.println("TimerTask cancelled");
//        try {
//            Thread.sleep(30000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }