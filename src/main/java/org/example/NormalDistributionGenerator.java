package org.example;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.example.interfaces.DataGenerator;

public class NormalDistributionGenerator implements DataGenerator {
    @Override
    public Double[] generateData(int dataSize, double mean, double variance) {
        NormalDistribution distribution = new NormalDistribution(mean, Math.sqrt(variance));
        Double[] data = new Double[dataSize];
        for (int i = 0; i < dataSize; i++) {
            data[i] = distribution.sample();
        }
        return data;
    }
}
