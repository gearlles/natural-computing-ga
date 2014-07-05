package com.gearlles.ga.core.fitness;

public class Sphere extends FitnessFunction {

	public Sphere(int dimensions) {
		this.dimensions = dimensions;
		
		LOWER_LIMIT = new double[dimensions];
		for (int i = 0; i < LOWER_LIMIT.length; i++) {
			LOWER_LIMIT[i] = -10d;
		}
		
		UPPER_LIMIT = new double[dimensions];
		for (int i = 0; i < UPPER_LIMIT.length; i++) {
			UPPER_LIMIT[i] = 10d;
		}
	}

	public double evaluate(double[] gene) {
		double res = 0;
		for (int i = 0; i < gene.length; i++)
			res += Math.pow(gene[i], 2);
		return res;
	}

}
