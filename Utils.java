
package basicperceptrons;

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
}
