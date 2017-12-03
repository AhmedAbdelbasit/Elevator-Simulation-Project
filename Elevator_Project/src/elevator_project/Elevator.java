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

public abstract class Elevator implements IObservable{
    
    private Building parentBuilding;
    private int elevatorNumber;
    private int currentPosition = 0;
    private char motionStatus = 'i';
    private int numOfPersonsInside = 0;
    
    public Elevator(){
        
    }
    
    protected void setElevatorNumber(int n){
        elevatorNumber = n;
    }
    public int getElevatorNumber(){
        return elevatorNumber;
    }
    
    protected void setParentBuilding(Building B){
        parentBuilding = B;
    }
    public Building getParentBuilding(){
        return parentBuilding;
    }
    
    public char getStatus(){
        return motionStatus;
    }
    
    public int getLocation(){
        return currentPosition;
    }
    
    public int getNumOfPersonsInside(){
        return numOfPersonsInside;
    }
    
    @Override
    public void add(IObserver O){
        
    }
    
    @Override
    public void remove(IObserver O){
        
    }
    
    @Override
    public void notifyObservers(){
        
    }
    
    public void moveTo(int floorNumber){
        
    }
    
    public void openDoor(){
        
    }
    
    public void enable(){
        
    }
    
    public void disable(){
        
    }
    
    
    
}
