/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffnn_2;

/**
 *
 * @author Fina
 */
public class FFNN_2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        ANN A = new ANN(2,2,1);
        A.ShowSummary();
        A.getLayerConnection1().GetEdge(0).SetWeight(0.1);
        A.getLayerConnection1().GetEdge(2).SetWeight(0.8);
        A.getLayerConnection1().GetEdge(1).SetWeight(0.4);
        A.getLayerConnection1().GetEdge(3).SetWeight(0.6);
        A.getLayerConnection2().GetEdge(0).SetWeight(0.3);
        A.getLayerConnection2().GetEdge(1).SetWeight(0.9);
        A.classifyInstance();
//        System.out.println("1. THE RESULT IS : " + 
//            A.getHiddenLayer().get(0).GetOutput());
//        System.out.println("2. THE RESULT IS : " + 
//            A.getHiddenLayer().get(1).GetOutput());
        System.out.println("THE RESULT IS : " + 
        A.getOutputLayer().get(0).GetOutput());
        
        A.getOutputLayer().get(0).countErrorOutputLayer(0.5);
        System.out.println(A.getOutputLayer().get(0).GetError());
        
        for (int i = 0 ; i < A.getHiddenLayer().size() ; i++ ) {
            A.getHiddenLayer().get(i).countError(A.LayerConnection2);
            System.out.println(A.getHiddenLayer().get(i).GetError());
        }
    }
}
