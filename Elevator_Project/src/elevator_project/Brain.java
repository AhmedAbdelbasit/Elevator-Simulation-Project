/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator_project;

import java.util.ArrayList;
/**
 * @author : Ahmed Abdelbasit Mohamed
 * emial : ahmed_abdelbasit94@hotmail.com
 */
public class Brain implements IObserver {
    private ArrayList<Request> requestsQueue;
    private ArrayList<BrainBuilding> buildingsList;
    
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
        // get best fit elevator
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
    
    
    
    
    
    private class BrainBuilding{
        private ArrayList<BrainElevator> registeredElevators;
        private int numOfFloors;
        
        public BrainBuilding(int n){
            numOfFloors = n;
            registeredElevators = new ArrayList();
        }
        
        public void addElevator(Elevator E){
            char s = E.getStatus();
            int l = E.getLocation();
            int n = E.getNumOfPersonsInside();
            
            BrainElevator BE = new BrainElevator(s, l, n);
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
        
        public BrainElevator(char s, int l, int n){
            status = s;
            location = l;
            numberOfPersonsInside = n;
        }
        
        private int getFitness(Request R){
            
            return 0;
        }
        
        public void updateData(char s, int l, int n){
            status = s;
            location = l;
            numberOfPersonsInside = n;
        }
    }
}
