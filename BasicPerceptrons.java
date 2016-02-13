package basicperceptrons;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Urs Mobile
 */
public class BasicPerceptrons {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        testMLP();
    }
    
    
    public static void testOr(){
        try {
            Instances logicOr = new Instances();
            double[] temp = { 0.0, 0.0 };
            logicOr.add( temp.clone(), 0.0);
            temp[1] = 1.0;
            logicOr.add(temp.clone(), 1.0);
            temp[0] = 1.0;
            logicOr.add(temp.clone(), 1.0);
            temp[1] = 0.0;
            logicOr.add(temp.clone(), 1.0);
            Perceptron p_or = new Perceptron(logicOr.instanceSize(), 0.0, .5, 0.2, 1.0, 0.0);
            p_or.train(logicOr, 1, 10000, true);
            temp[0] = 0.0;
            System.out.println(p_or.binClassifyInstance(temp));
            temp[0] = 1.0;
            System.out.println(p_or.binClassifyInstance(temp));
            temp[1] = 1.0;
            System.out.println(p_or.binClassifyInstance(temp));
            temp[0] = 0.0;
            System.out.println(p_or.binClassifyInstance(temp));
            System.out.print(p_or.toStringWeights());
            for(int i = 0 ; i < logicOr.size(); i++){
                double[] inst = logicOr.getInstance(i);
                for(double d: inst){
                    System.out.print(d + " ");
                }
                System.out.println();
            }
        } catch (Instances.InstanceNormalizedException ex) {
            Logger.getLogger(BasicPerceptrons.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstanceMissMatchException ex) {
            Logger.getLogger(BasicPerceptrons.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void testMLP(){
        double[] biases = {1.0,1.0};
        int[] hl = { 3 };
        MultiLayerPerceptron mlp = new MultiLayerPerceptron(2, hl, biases, 0.5, .2, 1.0, 0.0);
        try {
            System.out.println(mlp.classifyInstance(biases));
        } catch (InstanceMissMatchException ex) {
            Logger.getLogger(BasicPerceptrons.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
