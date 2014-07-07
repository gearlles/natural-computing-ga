package com.gearlles.ga.core.crossover;

import java.util.List;
import java.util.Random;

import br.poli.ecomp.nestor.cn.graph.NodeVrp;

import com.gearlles.ga.core.Chromosome;
import com.gearlles.ga.core.Route;

public class VrpCrossover implements CrossoverInterface {
	
	private static Random rand = new Random();

	public Chromosome[] mate(Chromosome dad, Chromosome mom) {
		List<Route> dadGene = dad.getGene();
		List<Route> momGene = mom.getGene();
		
		
		int randomRoute = rand.nextInt(dadGene.size());
		
		// 0, 1, 2, 3, 4, 5   size = 6
		int randomSubRoutStart = rand.nextInt(dadGene.size() - 1); // 0 até 4
		int randomSubRoutEnd = randInt(randomSubRoutStart + 1, dadGene.size() - 1); // do número escolhido acima, até o fim (inicio, fim]
		
		List<NodeVrp> subRoute = dadGene.get(randomRoute).getNodes().subList(randomSubRoutStart, randomSubRoutEnd);
		NodeVrp a1 = subRoute.get(0);
		
		int momCloserRouteIndex = 0;
		int momCloserRouteNode = 0;
		double closerDistance = Double.MAX_VALUE;
		for (int i = 0; i < momGene.size(); i++) {
			Route momRoute = momGene.get(i);
			List<NodeVrp> nodes = momRoute.getNodes();
			for (int j = 0; j < nodes.size(); j++) {
				NodeVrp node = nodes.get(j);
				if (!(node.getX() == a1.getX() && node.getY() == a1.getY()))  { // não forem iguais 
					
				}
			}
		}
		
		return null;
	}
	
	public static int randInt(int min, int max) {
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

}
