
package basicperceptrons;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Urs Mobile
 */
public class Perceptron {
    private double[] neurons;
    private double bias;
    private boolean isTrained;
    
    public Perceptron(int size, double bias){
        isTrained = false;
        neurons = new double[size];
        this.bias = bias;
        Random r = new Random();
        //Initialize random weights for neurons
        for(int i = 0;  i < size; i++){ 
            neurons[i] = r.nextDouble();
        }
    }
   
    
    
}
