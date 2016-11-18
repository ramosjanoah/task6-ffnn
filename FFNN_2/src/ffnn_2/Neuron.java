/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffnn_2;

import java.util.*;

/**
 *
 * @author Noah
 */
public class Neuron {
    private double Output;
    private double Error;
    
    public Neuron() {
        Output = 0;
        Error = 0;
    }
        
    public void SetOutput(double O) {
        Output = O;
    }
    
    public double GetOutput() {
        return Output;
    }

    public void SetError(double E) {
        Error = E;
    }   

    public double GetError() {
        return Error;
    }
    
    public void ShowInfoNeuron() {
        System.out.println("--- Neuron Information ---");
        System.out.println("Output : " + Output);
        System.out.println("Error : " + Error);
    }
  
    public double SigmoidActivationFunction(LayerConnection LC) {
        List<Edge> ListEdge = LC.GetListWithSecondEdge(this);
        double x = 0;
        for (int i = 0; i < ListEdge.size(); i++) {
            x = x + ListEdge.get(i).GetWeight()*ListEdge.get(i).GetNeuron1().GetOutput();
        }
        x = x*(-1);
        double n = Math.exp(x);
        double result = (1)/(1+n);
        return result;
    }
    
    public double SigmoidActivationFunction(List<Edge> ListEdge) {
        double x = 0;
        for (int i = 0; i < ListEdge.size(); i++) {
            x = x + ListEdge.get(i).GetWeight()*ListEdge.get(i).GetNeuron1().GetOutput();
        }
        x = x*(-1);
        double n = Math.exp(x);
        double result = (1)/(1+n);
        return result;        
    }
    
    public void SetOutputSigmoid(LayerConnection LC) {
        Output = SigmoidActivationFunction(LC);
    }

    public void SetOutputSigmoid(List<Edge> ListEdge) {
        Output = SigmoidActivationFunction(ListEdge);
    }    

    // MASIH SANGAT BISA BERUBAH
    public void countErrorOutputLayer(double target) {
        Error = Output*(1-Output)*(target-Output);
    }

    public void countError(LayerConnection LC) {
        List<Edge> ListEdge = LC.GetListWithFirstEdge(this);
        double X = 0;
        for (int i = 0; i < ListEdge.size() ; i++ ) {
            X = X + ListEdge.get(i).GetWeight() 
                    * ListEdge.get(i).GetNeuron2().GetError();
        }
        Error = Output*(1-Output)*X;
    }
    
    public static boolean IsSame(Neuron N1, Neuron N2) {
        return N1 == N2;
    }
}