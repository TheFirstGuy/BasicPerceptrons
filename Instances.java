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

    private ArrayList<double[]> instances = new ArrayList();
    private ArrayList<Double> classifications = new ArrayList();
    
    
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
    
    public void add(double[] instance, double classification) throws InstanceNormalizedException, InstanceMissMatchException{
        //Check normalization
        if(isNorm){ throw new InstanceNormalizedException(); }
        //Check if instance size matches
        else if(instances.size() != 0 && instanceSize() != instance.length){
            throw new InstanceMissMatchException(this, instance);
        }
        //Add instance
        else{
            instances.add(instance);
            classifications.add(classification);
        }
    }
    
    /*
    Returns instance size contained in instances. Returns -1 if no instances contained.
    */
    public int instanceSize(){
        if(instances.isEmpty()){ return -1;}
        else{ return instances.get(0).length;}
    }
    
    /*
    Returns number of instances member.
    */
    public int size(){
        return instances.size();
    }
    
    /*
    Returns instance vector without classification
    */
    public double[] getInstance(int index){
        return instances.get(index);
    }
    
    /*
    Returns instance classification
    */
    public double getClassification(int index){
        return classifications.get(index);
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
                for(double[] instance: instances){
                    norm = (instance[i] - mean)/stdev; // z = (x - u)/o
                    instance[i] = norm;
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
    private double mean(ArrayList<double[]> instances , int index) {
        double sum = 0;
        for(double[] instance: instances){
            sum += instance[index];
        }
        return sum / instances.size();
    }
    
    /*
    Returns standard deviation given index of doubles and means. Assumes that all instances are of same
    size.
    */
    private double stdev(ArrayList<double[]> instances, int index, double mean) {
        double sum = 0;
        for(double[] instance: instances){
            sum += Math.pow( instance[index] - mean, 2); // (x - u)^2
        }
        return Math.sqrt(sum/instances.size());
    }

    public static class InstanceNormalizedException extends Exception {

        public InstanceNormalizedException() {
            super("Attempted to add instance to normalized instances object.");
        }
        
    }
    
    
    
}
