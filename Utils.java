
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
}
