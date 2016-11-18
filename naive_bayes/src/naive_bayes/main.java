/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package naive_bayes;

import weka.core.Instances;

/**
 *
 * @author Scarletta's
 */
public class main {
    public static void main (String[] args) throws Exception {
        Naive_bayes nb = new Naive_bayes();
        Instances data = nb.readFile();
        Instances newData = nb.filterData(data);
        nb.buildClassifier(newData);
        double hasil = nb.classifyInstance(newData);
      
        
        
    }

}