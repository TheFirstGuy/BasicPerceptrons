
package basicperceptrons;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
MultiLayerPerceptron class allows for the classifications of nonlinear classes.
This class uses bipolar sigmoid function and back propigation. Binary classification
supported right now.
 */
public class MultiLayerPerceptron {
    private double[] inputNeurons;
    private Neuron[][] hiddenNeurons;
    private double[] biases;
    private final double threshold;
    private final double learningRate;
    private final double trueCase;
    private final double falseCase;
    //private double [][] lastActivations; 
    
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
            inputNeurons[i] = 1.0;
        }
        // Initialize hidden Neurons
        hiddenNeurons = new Neuron[hiddenLayers.length][];
        for(int i = 0; i < hiddenLayers.length; i++){
            hiddenNeurons[i] = new Neuron[hiddenLayers[i]];
            for(int j = 0; j < hiddenLayers[i]; j++){
                // Initialize first layer with number of inputs
                if(i == 0){
                    hiddenNeurons[i][j] = new Neuron(numInputs, i, j, learningRate, false);
                }
                // Otherwise remaining layers are initialized with previous number
                // of hidden neurons
                else{
                    if(i + 1 == hiddenLayers.length){// Check if output neuron
                        hiddenNeurons[i][j] = new Neuron(hiddenLayers[i-1], i, j, learningRate, true);
                    }
                        hiddenNeurons[i][j] = new Neuron(hiddenLayers[i-1], i, j,learningRate, false);
                }
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
    run through activation function. Repeat until last hidden layer.Assumes one output neuron.
    */
    public double classifyInstance(double[] instance) throws InstanceMissMatchException{
        double[] inputs = instance;
        for(int i = 0; i < hiddenNeurons.length; i++){
            // Activate each neuron in layer i
            for(int j = 0; j < hiddenNeurons[i].length; j++){
                hiddenNeurons[i][j].activate(inputs, biases[i]);
            }
            // Get result of this hidden layer
            inputs = getActivationArray(i);
        }
        // Assumes one output neuron
        if(inputs[0] >= threshold){ return trueCase;}
        else{ return falseCase;}
    }
    
    /*
    Back propagates through network, given classification.  
    */
    public void backPropagate(double[] classification){
        int index = hiddenNeurons.length - 1;
        for(int i = 0; i < hiddenNeurons[index].length; i++){
            calcOutputDelta( hiddenNeurons[index][i], classification[i]);
        }
        index--;
        while(index >= 0 ){
            for(int i = 0; i < hiddenNeurons[index].length; i++){
                calcDelta( hiddenNeurons[index][i], hiddenNeurons[index + 1]);
            }
            index--;
        }
        for(Neuron[] layer: hiddenNeurons){
            for(Neuron n: layer){
                n.updateWeights();
            }
        }
    }
    
    /*
    Calculates classification error.
    */
    private static double calcError(double correct, double classification){
        double error = correct - classification;
        return 0.5 * Math.pow(error, 2);
    }
    
    /*
    Calculates outer delta error for back propigation.
    If it is the output neuron then...
    (o_j - t)o_j(1 - o_j) where o_j is the last activation
    layer as an argument.
    
    */
    private double calcOutputDelta(Neuron n, double classification){
        n.delta = (n.lastActivation - classification) * n.lastActivation * (1 - n.lastActivation);
        return n.delta;
    }
    
    /*
    Calculates delta for hidden layers
    (Sum(delta_l * w_jl))o_j(1 - o_j)
    */
    private double calcDelta(Neuron lower, Neuron[] highers){
        double sum = 0;
        for(Neuron n: highers){
            for(double d: n.inputWeights){// Sum(delta_k * W_jk)
                sum += n.delta * d;
            }
        }
        lower.delta = lower.lastActivation * (1 - lower.lastActivation) * sum;
        return lower.delta;
    }
    

    /*
    Creates an array of outputs as doubles from a given hidden layer of neurons
    by collecting their lastActivations.
    */
    private double[] getActivationArray(int layer){
        double[] output = new double[hiddenNeurons[layer].length];
        for(int i = 0; i < output.length; i++){
            output[i] = hiddenNeurons[layer][i].lastActivation;
        }
        return output;
    }
    
    private class Neuron{
        public int layer;
        public int id;
        public double[] inputWeights;
        public double biasWeight;
        public double lastActivation;
        private double delta;
        private double learningRate;
        private boolean isOut;
        
        
        public Neuron(int numInputs, int layer, int id, double learningRate, boolean isOut){
            inputWeights = new double[numInputs];
            Random rand = new Random();
            for(int i = 0; i< numInputs; i++){
                inputWeights[i] = rand.nextDouble();
            }
            biasWeight = rand.nextDouble();
            this.layer = layer;
            this.id = id;
            this.learningRate = learningRate;
            this.isOut = isOut;
        }
        
        /*
        Returns the activation of this neuron. Takes the dot product of input vector
        (including the bias) with the input weights of each neuron. 
        */
        public double activate(double[] inputs, double bias) throws InstanceMissMatchException{
            try {
                lastActivation = Utils.dotprod( inputs, inputWeights);
            } catch (InstanceMissMatchException ex) {
                throw new InstanceMissMatchException("Miss match at layer: " + this.layer +
                        " neuron: " + this.id);
            }
            lastActivation += biasWeight * bias;
            lastActivation = Utils.sigmoid(lastActivation);
            return lastActivation;
        }
        
        
        
        
        /*
        Updates weights of all inputs. Assumes that correct delta has already been calculated.
        */
        private void updateWeights(){
            for(double d: inputWeights){
                d = d - learningRate * delta * lastActivation;
            }
            biasWeight = biasWeight + learningRate * delta;
        }
    }
    
}
