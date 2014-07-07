package com.gearlles.ga.core.fitness;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rastrigin extends FitnessFunction<Double> {
	
	private static final Random rand = new Random();

	public Rastrigin(int dimensions) {
		this.dimensions = dimensions;
		
		LOWER_LIMIT = new Double[dimensions];
		for (int i = 0; i < LOWER_LIMIT.length; i++) {
			LOWER_LIMIT[i] = -5.12d;
		}
		
		UPPER_LIMIT = new Double[dimensions];
		for (int i = 0; i < UPPER_LIMIT.length; i++) {
			UPPER_LIMIT[i] = 5.12d;
		}
	}

	public double evaluate(List<Double> gene) {
		double res = 10 * gene.size();
		for (int i = 0; i < gene.size(); i++)
			res += gene.get(i) * gene.get(i) - 10 * Math.cos(2 * Math.PI * gene.get(i));
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
