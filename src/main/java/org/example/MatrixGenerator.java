package org.example;

import org.example.chart.SortResultSpiderChart;
import org.example.inout.StdIn;
import org.example.inout.StdOut;
import org.example.interfaces.DataGenerator;
import org.example.interfaces.Sorter;
import org.example.mergeSort.MergeSort;

import java.util.*;

public class MatrixGenerator {
    Map<String, SortResult> resultsMap;
    ResultSaver saver;

    // matrix Parameters
    List<Double> means = new ArrayList<>();
    List<Double> variances = new ArrayList<>();
    List<Integer> dataSizes = new ArrayList<>();

    //Initialization of mean array
    public void meanInstanciation(){
        StdOut.printf("+ add mean : ");
        while (!StdIn.isEmpty()){
            String text = StdIn.readString();
            if(text.equals("-")){
                break;
            }
            means.add(Double.parseDouble(text));
            System.out.println(means);
        }
    }

    //Initialization of mean array
    public void variancesInstanciation(){
        StdOut.print("+ add variance : ");
        while (!StdIn.isEmpty() ){
            String text = StdIn.readString();
            if(text.equals("-")){
                break;
            }
            variances.add(Double.parseDouble(text));
            System.out.println(variances);
        }
    }

    //Initialization of mean array
    public void dataSizesInstanciation(){
        StdOut.print("+ add dataSizes : ");
        while (!StdIn.isEmpty()){
            String text = StdIn.readString();
            if(text.equals("-")){
                break;
            }
            dataSizes.add(Integer.parseInt(text));
            System.out.println(dataSizes);
        }
    }

    // warmup and numberOfExperiments
    int warmUpRounds = 100;
    int numberOfExperiments = 10;

    // data generation and algorithm selected
    DataGenerator generator;
    Sorter sorter;

    public MatrixGenerator(){
        generator = new NormalDistributionGenerator();
        sorter = new MergeSort();

        resultsMap = new HashMap<>();
        saver = new ResultSaver("ResultFile");
    }


    public void generateOrganizedRandomMatrices(){

        // Warm-up JVM
        Benchmark benchmark = new Benchmark(sorter);

        // JVM Warm-up
        benchmark.warmUpJVM(warmUpRounds, 1000);

        // Execute benchmarks
        for (int dataSize : dataSizes) {
            for (double mean : means) {
                for (double variance : variances) {
                    Double[] data = generator.generateData(dataSize, mean, variance);
                    String key = "Size:" + data.length + " Mean:" + mean + " Variance:" + variance;
                    resultsMap.put(key,benchmark.benchmark(data, numberOfExperiments,warmUpRounds,dataSize, mean, variance));

                    //System.out.println("Data Size: " + dataSize + ", Mean: " + mean + ", Variance: " + variance + ", Avg Time: " + averageTime + " ns");
                }
            }
        }
    }

    public void show(){

        for (String key : resultsMap.keySet()) {
            SortResult result = resultsMap.get(key);
            System.out.println("Key: " + key + ", Value: " + result);
        }
    }


    public void saveDataSet(){
        for (Map.Entry<String, SortResult> entry : resultsMap.entrySet()) {
            saver.saveResult(entry.getKey(), entry.getValue());
        }

    }

    public void visualizationAndAnalysis(){
        SortResultSpiderChart demo = new SortResultSpiderChart("Sort Algorithm Performance", resultsMap);
        demo.pack();
        demo.setVisible(true);
    }


    public static void main(String[] args) {
        MatrixGenerator matrixGenerator = new MatrixGenerator();

        matrixGenerator.meanInstanciation();
        matrixGenerator.variancesInstanciation();
        matrixGenerator.dataSizesInstanciation();


        matrixGenerator.generateOrganizedRandomMatrices();

        matrixGenerator.show();

        matrixGenerator.visualizationAndAnalysis();
    }

}
