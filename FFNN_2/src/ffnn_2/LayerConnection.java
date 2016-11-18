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
public class LayerConnection {
    List<Edge> ListEdge;
    
    public LayerConnection() {
        ListEdge = new ArrayList();
    }
    
    public List<Edge> GetListEdge() {
        return ListEdge;
    }
    
    public void AddEdge(Edge E) {
        ListEdge.add(E);
    }
    
    public int Size() {
        return ListEdge.size();
    }
    
    public List<Edge> GetListWithFirstEdge(Neuron N){
        List<Edge> L = new ArrayList();
        for (int i = 0; i < ListEdge.size(); i++) {
            if (Neuron.IsSame(N, ListEdge.get(i).GetNeuron1())) {
                L.add(ListEdge.get(i));
            }
        }
        return L;
    }

    public List<Edge> GetListWithSecondEdge(Neuron N){
        List<Edge> L = new ArrayList();
        for (int i = 0; i < ListEdge.size(); i++) {
            if (Neuron.IsSame(N, ListEdge.get(i).GetNeuron2())) {
                L.add(ListEdge.get(i));
            } 
        }
        return L;
    }

    public Edge GetEdge(Neuron N1, Neuron N2) {
        for (int i = 0; i < ListEdge.size(); i++) {
            if (Neuron.IsSame(N1, ListEdge.get(i).GetNeuron1())) {
                if (Neuron.IsSame(N2, ListEdge.get(i).GetNeuron2())) {
                    return ListEdge.get(i);
                }
            } 
        }
        return null;
    }
    
    public Edge GetEdge(int i) {
        return ListEdge.get(i);
    }
   
    public void updateLayerWeight() {
        for (int i = 0; i < this.Size(); i++) {
            this.GetEdge(i).updateWeight();
        }
    }    

}