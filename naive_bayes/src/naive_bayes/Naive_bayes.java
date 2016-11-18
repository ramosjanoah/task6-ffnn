/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package naive_bayes;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Scanner;
import weka.classifiers.AbstractClassifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.supervised.attribute.Discretize;

/**
 *
 * @author Scarletta's
 */

public class Naive_bayes extends AbstractClassifier {
    /**
     * @param args the command line arguments
     */
    
    private static int classidx=0;
    private ArrayList<ListElement>[][] M;
    private ListElement[] arrayOfClass;
    
    /* READ DATASET */
    public Instances readFile () throws Exception {
        ConverterUtils.DataSource source = new ConverterUtils.DataSource("C:\\Users\\acer\\Desktop\\mush.arff");
        Instances data = source.getDataSet();
        if (data.classIndex() == -1) {
            data.setClassIndex(classidx);
        }
        return data;
    }
    
    /* FILTER */
    public Instances filterData (Instances data) throws Exception {                
        // Apply filter
        Discretize filter;
        filter = new Discretize();
        filter.setInputFormat(data);

        Instances newData;
        newData = Filter.useFilter(data, filter);
        //System.out.println(newData);
        return newData;
    }
  
    
      @Override
    public void buildClassifier(Instances newData) throws Exception {
        
        int countAttr = newData.numAttributes();
        int distinctClassValue = newData.attribute(classidx).numValues();
        
        /* Inisialisasi Model */
        M = new ArrayList[countAttr][distinctClassValue];
        for (int i=0; i<countAttr; i++) {
            for (int j=0; j< distinctClassValue; j++) {
                M[i][j] = new ArrayList<ListElement>();
            }
        }
        boolean add;
        ListElement le = new ListElement();
        Attribute ab;
        for (int i=0; i<countAttr; i++) {
            if (i!=classidx) {
                for (int j=0; j<distinctClassValue; j++) {

                    for(int k=0; k<newData.attribute(i).numValues(); k++) {
                        ab = newData.attribute(i);
                        String c = ab.value((int) newData.instance(149).value(i));
                        add=M[i][j].add(new ListElement());
                    }
                }
            }
        }
        
        /* Membuat array yang menghitung banyak nilai pada masing-masing kelas */
        Attribute a;
        String c;
        arrayOfClass = new ListElement[newData.numClasses()];
        
        for (int idx = 0; idx < newData.numClasses(); idx++) {
            arrayOfClass[idx] = new ListElement();
            a = newData.classAttribute();
            c = a.value(idx);
            arrayOfClass[idx].setDisAttrName(c);
        }
        for (int i = 0; i < newData.numInstances(); i++) {
            double z = newData.instance(i).classValue();
            int zz = (int) z;
            arrayOfClass[zz].setCount(arrayOfClass[zz].getCount()+1);
        }
        
        //Masukan frekuensi masing-masing atribut
        for (int i=0; i<newData.numInstances(); i++) {
            for (int j=0; j<newData.numAttributes(); j++) {
                if(j!=classidx) { //bukan atribut kelas
                    a = newData.attribute(classidx);
                    c = a.value((int) newData.instance(i).value(classidx));

                    //Mengambil indeks kelas
                    double z = newData.instance(i).classValue();
                    int zz = (int) z;
                    le.setDisAttrName(c);
                    
                    //Mengambil indeks valueDistinct
                    double x = newData.instance(i).value(j);
                    int xx = (int) x;

                    //Menambahkan frekuensi kemunculan nilai per kelas per atribut
                    le.setCount(M[j][zz].get(xx).getCount()+1);
                    M[j][zz].set(xx, new ListElement(M[j][zz].get(xx).getDisAttrName(), M[j][zz].get(xx).getCount()+1));
                } 
            }
        }
        
        /* Menghitung probabilitas masing-masing nilai distinct atribut per kelas */
        for (int j=0; j<newData.numAttributes(); j++) {
            if (j!=classidx) {
                for (int zz = 0; zz < newData.numClasses(); zz++) {
                    for (int xx = 0; xx < newData.attribute(j).numValues(); xx++) {
                        M[j][zz].set(xx, new ListElement(M[j][zz].get(xx).getDisAttrName(), M[j][zz].get(xx).getCount()/arrayOfClass[zz].getCount()));


                    }
                }
            }    
        }
    } 
    
    public double classifyInstance(Instances newData) throws Exception {
         DenseInstance newInstance = new DenseInstance(newData.instance(2));
                 float[] prob = new float[newData.numClasses()];
        for (int i = 0; i < newData.numClasses(); i++) {
            
            prob[i] = (float) arrayOfClass[i].getCount() / (float) newData.numInstances();
            System.out.println("ii = "+ prob[i]);
              
        }
        
        for (int i = 0; i < newData.numClasses(); i++) {
            for (int j = 0; j < newData.numAttributes(); j++) {
                if (j!=classidx) {
                    System.out.println("j ="+j);
                    double x = newInstance.value(j);
                    System.out.println("x = " + x);
                    int xx = (int) x;
                    System.out.println("xx = "+ xx);
                    prob[i] *= M[j][i].get(xx).getCount();
                    System.out.println("lala = "+prob[i]);
                }    
            }
        }
        
        int indeksmaks = 0;
        double max = prob[indeksmaks];
        System.out.println("prob 0 = "+ prob[0] );
        for (int i = 1; i < newData.numClasses(); i++) {
            if (max < prob[i]) {
                indeksmaks = i;
                max = prob[i];
                System.out.println("prob "+ i + prob[i]);
                
            }
        }
        
        String tata = arrayOfClass[indeksmaks].getDisAttrName();
        System.out.println("kelas instance = "+ tata);
        return indeksmaks;
    }
    
    
    
   


    
}

     
