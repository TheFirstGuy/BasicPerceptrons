/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicperceptrons;

import java.util.ArrayList;

/**
 *
 * @author Urs Mobile
 */
public class Instances {
    private boolean isNorm;
    private ArrayList<ArrayList<Double>> instances = new ArrayList();
    
    
    // Default constructor. Initializes all class members to 0 and false
    public Instances(){
        isNorm = false;
    }
    
    /* 
    Adds an ArrayList of doubles to the Instances Arraylist. If Instances is 
    empty then instance is added. Otherwise, an exception is thrown if passed 
    ArrayList is not the same size. Exception thrown when called and Instances 
    are already normalized.
    */
    
    public void add(ArrayList<Double> instance) throws InstanceNormalizedException, InstanceMissMatchException{
        //Check normalization
        if(isNorm){ throw new InstanceNormalizedException(); }
        //Check if instance size matches
        else if(instances.size() != 0 && instanceSize() != instance.size()){
            throw new InstanceMissMatchException(this, instance);
        }
        //Add instance
        else{
            instances.add(instance);
        }
    }
    
    /*
    Returns instance size contained in instances. Returns -1 if no instances contained.
    */
    public int instanceSize(){
        if(instances.isEmpty()){ return -1;}
        else{ return instances.get(0).size();}
    }
    
    /*
    Returns number of instances member.
    */
    public int size(){
        return instances.size();
    }
    
    /*
    Returns instance (ArrayList of Double)
    */
    public ArrayList<Double> getInstance(int index){
        return instances.get(index);
    }
    
    /*
    Normalize instances. If the object is not already normalized, using 
    standard score normalization. Otherwise, empty return.
    */
    public void normalize(){
        double mean;
        double stdev;
        double norm;
        if(!isNorm){
            for(int i = 0; i < instanceSize(); i++){
                mean = mean(instances, i);
                stdev = stdev(instances, i, mean);
                for(ArrayList<Double> instance: instances){
                    norm = (instance.get(i) - mean)/stdev; // z = (x - u)/o
                    instance.set(i, norm);
                }
            }
        }
        else{
            return;
        }
    }
    
    /*
    Returns the mean of a given index of doubles. Assumes that all instances are of 
    the same size.
    */
    private double mean(ArrayList<ArrayList<Double>> instances , int index) {
        double sum = 0;
        for(ArrayList<Double> instance: instances){
            sum += instance.get(index);
        }
        return sum / instances.size();
    }
    
    /*
    Returns standard deviation given index of doubles and means. Assumes that all instances are of same
    size.
    */
    private double stdev(ArrayList<ArrayList<Double>> instances, int index, double mean) {
        double sum = 0;
        for(ArrayList<Double> instance: instances){
            sum += Math.pow( instance.get(index) - mean, 2); // (x - u)^2
        }
        return Math.sqrt(sum/instances.size());
    }

    private static class InstanceNormalizedException extends Exception {

        public InstanceNormalizedException() {
            super("Attempted to add instance to normalized instances object.");
        }
        
    }
    
    
    
}