package com.gearlles.ga.core.fitness;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sphere extends FitnessFunction<Double> {
	
	private static final Random rand = new Random();

	public Sphere(int dimensions) {
		this.dimensions = dimensions;
		
		LOWER_LIMIT = new Double[dimensions];
		for (int i = 0; i < LOWER_LIMIT.length; i++) {
			LOWER_LIMIT[i] = -10d;
		}
		
		UPPER_LIMIT = new Double[dimensions];
		for (int i = 0; i < UPPER_LIMIT.length; i++) {
			UPPER_LIMIT[i] = 10d;
		}
	}

	public double evaluate(List<Double> gene) {
		double res = 0;
		for (int i = 0; i < gene.size(); i++)
			res += Math.pow(gene.get(i), 2);
		return res;
	}

	@Override
	public List<Double> getRandom() {
		List<Double> arr = new ArrayList<Double>();
		for (int i = 0; i < arr.size(); i++) {
			arr.set(i, LOWER_LIMIT[i] + (UPPER_LIMIT[i] - LOWER_LIMIT[i])
					* rand.nextDouble());
		}
		return arr;
	}

	@Override
	public List<Double> getRandom(Double lower, Double upper) {
		List<Double> arr = new ArrayList<Double>();
		for (int i = 0; i < arr.size(); i++) {
			arr.set(i, lower + (upper - lower)
					* rand.nextDouble());
		}
		return arr;
	}

}
