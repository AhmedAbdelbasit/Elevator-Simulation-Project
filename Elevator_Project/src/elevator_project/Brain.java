/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator_project;

import static elevator_project.Elevator_Project.numOfPersonElevators;
import java.util.ArrayList;
/**
 * @author : Ahmed Abdelbasit Mohamed
 * emial : ahmed_abdelbasit94@hotmail.com
 */
public class Brain implements IObserver {
    private ArrayList<Request> requestsQueue;
    private ArrayList<BrainBuilding> buildingsList;
    
    private int eState = 0;
    
    public Brain(){
        requestsQueue = new ArrayList();
        buildingsList = new ArrayList();
    }
    
    public void request(Request R){
        requestsQueue.add(R);
        manageRequests();
    }
    
    private void manageRequests(){
        Request R;
        while( requestsQueue.size() > 0){
            R = requestsQueue.get(0);
            requestsQueue.remove(0);
            applyRequest(R);
        }
    }
    
    private void applyRequest(Request R){
        
        // get request details
        Keypad k = R.getSourceKeypad();
        int buildingNumber = k.getBuilding().getBuildingNumber();
        int s = R.getSource();
        int d = R.getDestination();
        
        // get best fit elevator
        int eNum = getBestFit(R);
        
        // send response
        eNum = eState;
        eState += 1;
        if(eState == numOfPersonElevators){
            eState = 0;
        }
        k.displayResponse(eNum);
        k.getBuilding().addElevatorTask(eNum, s);
        k.getBuilding().addElevatorTask(eNum, d);
    }
    
    private int getBestFit(Request R){
        
        int elevatorNumber = 0;
        return elevatorNumber;
    }
    
    public void registerBuilding(Building mB){
        BrainBuilding B = new BrainBuilding(mB);
        buildingsList.add(B);
        System.out.println("Building number " + mB.getBuildingNumber() + " with " + mB.getNumOfElevators() + " Elevators");
    }
    
    @Override
    public void addStation(IObservable S){
        Elevator E = (PersonElevator)S;
        int Bnum = E.getParentBuilding().getBuildingNumber();
        buildingsList.get(Bnum).addElevator(E);
    }
    
    @Override
    public void removeStation(IObservable S){
        
    }
    
    @Override
    public void update(IObservable S){
        Elevator E = (PersonElevator)S;
        int Bnum = E.getParentBuilding().getBuildingNumber();
        buildingsList.get(Bnum).updateElevator(E);
    }
    
    public void updateElevatorLocation(Elevator E, int f){
        int b = E.getParentBuilding().getBuildingNumber();
        int e = E.getElevatorNumber();
        buildingsList.get(b).registeredElevators.get(e).setLocation(f);
    }
    
    
    
    
    
    private class BrainBuilding{
        private ArrayList<BrainElevator> registeredElevators;
        private final int numOfFloors;
        private final int numOfElevators;
        
        public BrainBuilding(Building B){
            numOfFloors = B.getNumOfFloors();
            numOfElevators = B.getNumOfElevators();
            registeredElevators = new ArrayList();
            for(int i=0 ; i<numOfElevators ; i++){
                addElevator(B.getElevator(i));
                registeredElevators.get(i).printData();
            }
        }
        
        public void addElevator(Elevator E){
            char s = E.getStatus();
            int l = E.getLocation();
            int n = E.getNumOfPersonsInside();
            int eN = E.getElevatorNumber();
            
            BrainElevator BE = new BrainElevator(eN,s, l, n);
            registeredElevators.add(BE);
        }
        
        public void updateElevator(Elevator E){
            char s = E.getStatus();
            int l = E.getLocation();
            int n = E.getNumOfPersonsInside();
            registeredElevators.get(E.getElevatorNumber()).updateData(s, l, n);
        }
        
        
    }
    
    
    
    private class BrainElevator{
        private char status;
        private int location;
        private int numberOfPersonsInside;
        private int elevatorNumber;
        
        public BrainElevator(int eNum, char s, int l, int nP){
            elevatorNumber = eNum;
            status = s;
            location = l;
            numberOfPersonsInside = nP;
            
        }
        
        private int getFitness(Request R){
            
            int numOfFloorsTravelled = 0;
            return numOfFloorsTravelled;
        }
        
        public void updateData(char s, int l, int n){
            status = s;
            location = l;
            numberOfPersonsInside = n;
        }

        public void setNumberOfPersonsInside(int n) {
            numberOfPersonsInside = n;
        }
        public void setStatus(char s){
            status = s;
        }
        public void setLocation(int f){
            location = f;
        }
        
        public void printData(){
            System.out.print("\nRegistered Elevator " + elevatorNumber);
            System.out.print("\n\t\tStatus : " + status + "\n\t\tlocation : " + location + "\n\t\tnumberOfPersonsInside : " + numberOfPersonsInside);
            System.out.println();
        }
    }
}
