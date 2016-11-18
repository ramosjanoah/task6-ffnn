/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffnn_2;

import java.util.*;
import weka.classifiers.AbstractClassifier;
import weka.core.Instances;

/**
 *
 * List pertanyaan terhadap model: 
 * - Bagaimana cara evaluate model? 
 *      Harus bikin fungsi classifyInstance dan buildClassifier
 * 
 * - Bagaimana cara tahu model tersebut bagus atau tidak? Apa ukurannya? 
 *      Persen2ann
 * 
 * - Cara menentukan jumlah output neuron? Tergantung dari jenis kelasnya.
 *          Kalau cuman 1, 1 aja. sisanya sesuai dengan jenis jumlah kelas nya.
 *   
 * - Harus implement Serializeable
 * 
 * - 10-fold dan split test itu dari weka atau bikin sendiri? -
 *  
 *  - Cara nyari weight yang bagus: Cari di internet.
 *  - Kalo bias ada, lebih bagus.
 *  - Cari learning rate yang bagus, harus di-adjust
 *  - Normalisasi input!!!!
 * 
 * 
 * 
 * @author Noah
 */
public final class ANN extends AbstractClassifier {
    List<Neuron> InputLayer;
    List<Neuron> HiddenLayer;
    List<Neuron> OutputLayer;

    LayerConnection LayerConnection1;
    LayerConnection LayerConnection2;

    boolean IsUseHiddenLayer;
    double DefaultWeight = 1;
    double LearningRate = 1;

    public ANN(int InputNeuron,
            int HiddenNeuron,
            int OutputNeuron) {
        IsUseHiddenLayer = true;

        // Inisialisasi InputLayer
        InputLayer = new ArrayList();
        for (int i = 0; i < InputNeuron; i++) {
            Neuron N = new Neuron();
            InputLayer.add(N);
        }

        // Inisialisasi HiddenLayer 
        HiddenLayer = new ArrayList();
        for (int i = 0; i < HiddenNeuron; i++) {
            Neuron N = new Neuron();
            HiddenLayer.add(N);
        }

        OutputLayer = new ArrayList();
        for (int i = 0; i < OutputNeuron; i++) {
            Neuron N = new Neuron();
            OutputLayer.add(N);
        }

        // Buat koneksi antar layer, dengan LayerConnection
        // Antara Input dan Hidden Layer
        LayerConnection1 = new LayerConnection();
        for (int i = 0; i < InputNeuron; i++) {
            for (int j = 0; j < HiddenNeuron; j++) {
                Edge E = new Edge(InputLayer.get(i), HiddenLayer.get(j), RandomWeight());
                LayerConnection1.AddEdge(E);
            }
        }

        // Antara Hidden dan Output Layer
        LayerConnection2 = new LayerConnection();
        for (int i = 0; i < HiddenNeuron; i++) {
            for (int j = 0; j < OutputNeuron; j++) {
                Edge E = new Edge(HiddenLayer.get(i), OutputLayer.get(j), RandomWeight());
                LayerConnection2.AddEdge(E);
            }
        }
    }

    float RandomWeight() {
        return 1;
    }

    public void ShowSummary() {
        System.out.println("-- CLASSIFIER SUMMARY --");
        System.out.println("\nisUsingHiddenLayer : " + IsUseHiddenLayer);
        System.out.println("\nJumlah Neuron InputLayer : " + InputLayer.size());
        if (IsUseHiddenLayer) {
            System.out.println("\nJumlah Neuron HiddenLayer : " + HiddenLayer.size());
        }
        System.out.println("\nJumlah Neuron InputLayer : " + OutputLayer.size());
        System.out.println("\nJumlah Edge antara InputLayer "
                + "dan OuputLayer : " + LayerConnection1.Size());
        System.out.println("\nJumlah Edge antara HiddenLayer "
                + "dan OuputLayer : " + LayerConnection2.Size());
    }

    public void Testing() {
        System.out.println("----- Show Connection ------ ");
        System.out.println("Input Layer\n");

        Neuron N1 = InputLayer.get(0);
        Neuron N2 = InputLayer.get(1);

        Neuron N1x = LayerConnection1.GetListWithFirstEdge(N1).get(0).GetNeuron2();
        Neuron N2x = LayerConnection1.GetListWithFirstEdge(N2).get(0).GetNeuron2();

        if (Neuron.IsSame(N1x, N2x)) {
            System.out.println("SEMANGAT");
            System.out.println(N1x);
            System.out.println(N2x);
        } else {
            System.out.println("SUCCES");
        }

        // System.out.println("Input Layer\n");
        // System.out.println("Input Layer\n")                
    }

    @Override
    public void buildClassifier(Instances i) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Neuron> getInputLayer() {
        return InputLayer;
    }

    public List<Neuron> getHiddenLayer() {
        return HiddenLayer;
    }

    public List<Neuron> getOutputLayer() {
        return OutputLayer;
    }

    public LayerConnection getLayerConnection1() {
        return LayerConnection1;
    }

    public LayerConnection getLayerConnection2() {
        return LayerConnection2;
    }

    public boolean IsUseHiddenLayer() {
        return IsUseHiddenLayer;
    }

    public void SetLearningRate(float L) {
        LearningRate = L;
    }

    public void ProcessingInstance() {
        // Minta input dari pengguna
        Scanner S = new Scanner(System.in);
        double input;
        for (int i = 0; i < InputLayer.size(); i++) {
            input = Double.parseDouble(S.nextLine());
            // Nantinya akan diganti dengan menerima input berupa instance
            InputLayer.get(i).SetOutput(input);
        }
        // Olah di hidden Layer
        for (int i = 0; i < HiddenLayer.size(); i++) {
            HiddenLayer.get(i).SetOutputSigmoid(LayerConnection1);
        }
        // Olah di output Layer
        for (int i = 0; i < OutputLayer.size(); i++) {
            OutputLayer.get(i).SetOutputSigmoid(LayerConnection2);
        }
        // double result = OutputLayer.get(0).GetOutput();
        // Hasilnya adalah 
    }
        
    /**
     *
     * @param ListTarget
     */
    public void UpdateErrorOfModel(List<Double> ListTarget) {
        // Update Error OutputLayer
        for (int i = 0; i < OutputLayer.size(); i++) {
            OutputLayer.get(i).countErrorOutputLayer(ListTarget.get(i));
        }

        // Update Error HiddenLayer
        for (int i = 0; i < HiddenLayer.size(); i++) {
            HiddenLayer.get(i).countError(LayerConnection2);
        }
    }

    public void UpdateWeightOfModel() {
        for (int i = 0; i < LayerConnection1.Size(); i++) {
            LayerConnection1.GetEdge(i).updateWeight(LearningRate);
        }
        for (int i = 0; i < LayerConnection2.Size(); i++) {
            LayerConnection2.GetEdge(i).updateWeight(LearningRate);
        }
    }
    
    
}
