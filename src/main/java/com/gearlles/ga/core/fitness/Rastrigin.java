package com.gearlles.ga.core.fitness;

public class Rastrigin extends FitnessFunction {

	public Rastrigin(int dimensions) {
		this.dimensions = dimensions;
		
		LOWER_LIMIT = new double[dimensions];
		for (int i = 0; i < LOWER_LIMIT.length; i++) {
			LOWER_LIMIT[i] = -5.12d;
		}
		
		UPPER_LIMIT = new double[dimensions];
		for (int i = 0; i < UPPER_LIMIT.length; i++) {
			UPPER_LIMIT[i] = 5.12d;
		}
	}

	public double evaluate(double[] gene) {
		double res = 10 * gene.length;
		for (int i = 0; i < gene.length; i++)
			res += gene[i] * gene[i] - 10 * Math.cos(2 * Math.PI * gene[i]);
		return res;
	}

}
