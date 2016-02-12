
package basicperceptrons;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Urs Mobile
 */
public class Utils {
    
    /*
    Sigmoid activation function.
    */
    public static double sigmoid(double x){
        return 1/(1 + Math.exp(-x));
    }
    
    /*
    Returns the dot product of two arrays. Typically used for one layer of perceptron.
    Throws error is arrays are of different weights.
    */
    public static double dotprod(double[] left , double[] right) throws InstanceMissMatchException{
        if(left.length != right.length){
            throw new InstanceMissMatchException("Number of inputs does not match "
                    + "number of outputs.");
        }
        else{
            double sum = 0;
            for(int i = 0; i < left.length; i++){
                sum += left[i] * right[i];
            }
            return sum;
        }
        
    }
    
    /*
    Returns an array of double as the product of multiplying the passed array by
    a scalar. Typically, the vector array will be the weights of the hidden layer of neurons.
    */
    public static double[] scalarMulti(double[] vector, double scalar){
        double[] result = new double[vector.length];
        for(int i = 0; i < vector.length; i++){
            result[i] = vector[i] * scalar;
        }
        return result;
    }
    
    /*
    Modifies the sums array of double by using each element as the input of the
    sigmoid function. Typically, the passed in vector will be the resulting vector of an 
    activation of a hidden layer.
    */
    public static double vectorSigmoid(double[] vector){
        double sum = 0;
        for(int i = 0; i < vector.length; i++){
            sum += (double)1/(double)(sigmoid(vector[i]));
        }
        return sum;
    }
    
    /*
    Generates a range of numbers that is then shuffled in a random order. This
    is used to make the training set more diverse and hopefully exit its occilation.
    Size is the number of elements required, but is non inclusive for the range.
    */
    public static int[] randomRange(int size){
        int[] range = new int[size];
        for(int i = 0; i < range.length; i++){
            range[i] = i;
        }
        Random rand = new Random();
        int left, right;
        for(int i = 0; i < size; i++){
            left = rand.nextInt(size);
            right = rand.nextInt(size);
            int temp = range[left];
            range[left] = range[right];
            range[right] = temp;
        }
        return range;
    }
}
