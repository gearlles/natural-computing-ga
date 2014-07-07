package com.gearlles.ga.core.crossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.poli.ecomp.nestor.cn.graph.NodeVrp;
import br.poli.ecomp.nestor.cn.graph.distance.DistanceFunction;

import com.gearlles.ga.core.Chromosome;
import com.gearlles.ga.core.Route;

public class VrpCrossover implements CrossoverInterface {
	
	private static Random rand = new Random();

	public Chromosome[] mate(Chromosome dad, Chromosome mom) {
		List<Route> dadGene = dad.getGene();
		List<Route> momGene = mom.getGene();
		List<Route> momGeneCopy = new ArrayList<Route>(momGene);
		
		DistanceFunction distanceFunction = dad.getMap().getFunction();		
		int randomRoute = rand.nextInt(dadGene.size());
		
		// 0, 1, 2, 3, 4, 5   size = 6
		int randomSubRoutStart = rand.nextInt(dadGene.size() - 1); // 0 até 4
		int randomSubRoutEnd = randInt(randomSubRoutStart + 1, dadGene.size() - 1); // do número escolhido acima, até o fim (inicio, fim]
		
		List<NodeVrp> subRoute = dadGene.get(randomRoute).getNodes().subList(randomSubRoutStart, randomSubRoutEnd);
		NodeVrp a1 = subRoute.get(0);
		
		// achar o nó mais próximo de a1
		int momCloserRouteIndex = 0;
		int momCloserRouteNodeIndex = 0;
		double closerDistance = Double.MAX_VALUE;
		for (int i = 0; i < momGeneCopy.size(); i++) {
			Route momRoute = momGeneCopy.get(i);
			List<NodeVrp> nodes = momRoute.getNodes();
			for (int j = 0; j < nodes.size(); j++) {
				NodeVrp node = nodes.get(j);
				if (!node.equals(a1))  { //  equals é sobrescrito verificando se estão na mesma posição
					double dist = distanceFunction.calculate(node.getX(), node.getY(), a1.getX(), a1.getY());
					if (dist < closerDistance) {
						closerDistance = dist;
						momCloserRouteIndex = i;
						momCloserRouteNodeIndex = j;
					}
				}
			}
		}
		
		// achamos a posição do nó mais próximo, vamos concatenar
		Route momSelectedRoute = momGeneCopy.get(momCloserRouteIndex);
		momSelectedRoute.getNodes().addAll(momCloserRouteNodeIndex + 1, subRoute);
		
		// remover nós duplicados
		for (int i = 0; i < momGeneCopy.size(); i++) {
			Route momRoute = momGeneCopy.get(i);
			List<NodeVrp> nodes = momRoute.getNodes();
			for (int j = 0; j < nodes.size(); j++) {
				NodeVrp node = nodes.get(j);
				if (subRoute.contains(node))  { // contains usa equals, que é sobrescrito verificando se estão na mesma posição
					nodes.remove(j);
				}
			}
			
			// se não sobrou nós da rota, remove a rota
			if (momRoute.getNodes().isEmpty()) {
				momGeneCopy.remove(momRoute);
			}
		}
		
		return new Chromosome[]{new Chromosome(momGeneCopy)};
	}
	
	public static int randInt(int min, int max) {
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

}
