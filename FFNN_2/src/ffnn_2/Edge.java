/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffnn_2;

/**
 * @author Noah
 */
public class Edge {
    double Weight;
    Neuron Neuron1;
    Neuron Neuron2;
    
    public Edge() {
        Weight = 0;
        Neuron1 = new Neuron();
        Neuron2 = new Neuron();
    }
    
    public Edge(Neuron N1, Neuron N2) {
        Neuron1 = N1;
        Neuron2 = N2;
        Weight = 0;
    }

    public Edge(Neuron N1, Neuron N2, double W) {
        Neuron1 = N1;
        Neuron2 = N2;
        Weight = W;
    }
    
    public void SetNeuron1(Neuron N1) {
        Neuron1 = N1;
    }
    
    public Neuron GetNeuron1() {
        return Neuron1;
    }

    public void SetNeuron2(Neuron N2) {
        Neuron1 = N2;
    }
    
    public Neuron GetNeuron2() {
        return Neuron2;
    }    

    public void SetWeight(double W) {
        Weight = W;
    }
    
    public double GetWeight() {
        return Weight;
    }
    
    public void updateWeight() {
        Weight = Weight + Neuron1.GetOutput()*Neuron2.GetError();
    }
    
    public void updateWeight(double LearningRate) {
        Weight = Weight + Neuron1.GetOutput()*Neuron2.GetError()*LearningRate;
    }
}
