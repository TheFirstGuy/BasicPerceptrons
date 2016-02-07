
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
