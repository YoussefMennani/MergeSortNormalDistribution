package org.example;

import org.example.chart.SortResultSpiderChart;
import org.example.interfaces.DataGenerator;
import org.example.interfaces.Sorter;
import org.example.mergeSort.MergeSort;

import java.util.HashMap;
import java.util.Map;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {


        Map<String, SortResult> resultsMap = new HashMap<>();
        ResultSaver saver = new ResultSaver("ResultFile");


        // Define means, variances, and data sizes
        double[] means = {50,4};
        double[] variances = {20, 50};
        int[] dataSizes = {500, 1000, 5000};
        int warmUpRounds = 100;
        int numberOfExperiments = 10;

        DataGenerator generator = new NormalDistributionGenerator();
        Sorter sorter = new MergeSort();

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



        for (String key : resultsMap.keySet()) {
            SortResult result = resultsMap.get(key);
            System.out.println("Key: " + key + ", Value: " + result);
        }


        // After filling the resultsMap with benchmark results
/*        for (Map.Entry<String, SortResult> entry : resultsMap.entrySet()) {
            saver.saveResult(entry.getKey(), entry.getValue());
        }*/


        //data
        SortResultSpiderChart demo = new SortResultSpiderChart("Sort Algorithm Performance", resultsMap);
        demo.pack();
        demo.setVisible(true);


    }
}