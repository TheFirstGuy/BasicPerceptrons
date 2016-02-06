
package basicperceptrons;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Urs Mobile
 */
public class Perceptron {
    private double[] neurons;
    private final double bias;
    private boolean isTrained;
    private final double threshold;
    private final double learningRate;
    private final double trueCase;
    private final double falseCase;
   
    
    public Perceptron(int size, double bias, double threshold, double learningRate, double trueCase, double falseCase){
        isTrained = false;
        neurons = new double[size];
        this.bias = bias;
        this.threshold = threshold;
        this.learningRate = learningRate;
        this.trueCase = trueCase;
        this.falseCase = falseCase;
        Random r = new Random();
        //Initialize random weights for neurons
        for(int i = 0;  i < size; i++){ 
            neurons[i] = r.nextDouble();
        }
    }
   
    /*
    Classifies an instance of the same size as input neurons. Returns classification
    as a double defined during initialization. Uses a simple binary threshold to classify
    Throws exception is instance size does not match neuron size. 
    */
    public double binClassifyInstance(double[] instance) throws InstanceMissMatchException{
        double x = Utils.dotprod(neurons, instance);
        x += bias;
        if( x > threshold){ return trueCase;}
        else{ return falseCase;}
    }
    
    /*
    Trains the perceptron. Desired accuracy is the required accuracy (as a fraction ) to terminate
    training. Max iterations is the maximum number of times training will occur over
    the whole training set before it terminates, regardless of accuracy. 
    */
    public void train(Instances trainingSet, double desiredAccuracy, int maxIter) throws InstanceMissMatchException{
        int numcorrect = 0;
        int iteration = 0;
        int index = 0;
        double classification;
        double currentAccuracy = 0;
        double error;
        while(iteration < maxIter && currentAccuracy < desiredAccuracy){
            // Wrap index around if at the end of a training set
            if(index >= trainingSet.size()){ 
                index = 0;
                numcorrect = 0;
                iteration++;
                System.out.println("Iteration: " + iteration + " Accuracy: " +
                        currentAccuracy + "%");
            }
            // If missclassified, learn...
            classification = binClassifyInstance(trainingSet.getInstance(index));
            if( classification != trainingSet.getClassification(index)){
                error = trainingSet.getClassification(index) - classification;
                for(int i = 0; i < neurons.length; i++){
                    // w(t + 1) = w(t) + a(d - y(t))x
                    neurons[i] = neurons[i] + learningRate * error * trainingSet.getInstance(index)[i];
                }
            }
            else{
                numcorrect++;
            }
            currentAccuracy = trainingSet.size()/numcorrect;
        }
    }
    
}
