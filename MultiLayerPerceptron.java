
package basicperceptrons;

import java.util.Random;


/*
MultiLayerPerceptron class allows for the classifications of nonlinear classes.
This class uses bipolar sigmoid function and back propigation. Binary classification
supported right now.
 */
public class MultiLayerPerceptron {
    private double[] inputNeurons;
    private double[][] hiddenNeurons;
    private double[] biases;
    private final double threshold;
    private final double learningRate;
    private final double trueCase;
    private final double falseCase;
    
    /*
    Constructor for multiLayer Perceptron.
    numInputs: Enter the number of inputs (number of attributes per instance) 
    HiddenLayers: Enter an array of ints. The index is the layer, and the int represents the number
    of neurons in that layer
    biases: Biases for hidden layers
    Threshold: minimum number required for trueCase classification
    LearningRate: Learning rate for the perceptron. (enter between 0.8-0.2)
    TrueCase: Value of a trueCase
    FalseCase: Value of the false case
    */
    public MultiLayerPerceptron(int numInputs, int[] hiddenLayers, double[] biases,
            double threshold, double learningRate, double trueCase, double falseCase){
        Random rand = new Random();
        // Initialize input Neurons
        inputNeurons = new double[numInputs];
        for(int i = 0; i < numInputs; i++){
            inputNeurons[i] = rand.nextDouble();
        }
        // Initialize hidden Neurons
        hiddenNeurons = new double[hiddenLayers.length][];
        for(int i = 0; i < hiddenLayers.length; i++){
            hiddenNeurons[i] = new double[hiddenLayers[i]];
            for(int j = 0; j < hiddenLayers[i]; j++){
                hiddenNeurons[i][j] = rand.nextDouble();
            }
        }
        this.biases = biases;
        this.threshold = threshold;
        this.learningRate = learningRate;
        this.trueCase = trueCase;
        this.falseCase = falseCase;
    }
    
    /*
    Classifies an input vector. Input layer is treated as a single layer perceptron,
    sum is transfered to hidden layer. Hidden layers scale inputs, and results are 
    run through activation function. Repeat until last hidden layer.
    */
    public double classifyInstance(double[] instance) throws InstanceMissMatchException{
        // Input layer forward activation
        double[] temp;
        double z = Utils.dotprod(inputNeurons, instance);
        z += biases[0];
        for(int i = 0; i < hiddenNeurons.length; i++){
            // Calculate weighted activation of hidden layer
            temp = Utils.scalarMulti(hiddenNeurons[i], z);
            // Modify resulting vector with nonlinear function
            z = Utils.vectorSigmoid(temp);
            z += biases[i+1];
        }
        if(z >= threshold){ return trueCase;}
        else{ return falseCase;}
    }
    
}
