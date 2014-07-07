package com.gearlles.ga.core.mutation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.util.logging.resources.logging;
import br.poli.ecomp.nestor.cn.graph.NodeVrp;

import com.gearlles.ga.App;
import com.gearlles.ga.core.Chromosome;
import com.gearlles.ga.core.Population;
import com.gearlles.ga.core.Route;

public class InsertionMutation implements MutationInterface
{
	private static final Logger log = LoggerFactory.getLogger(InsertionMutation.class);

	private static Random rand = new Random();

	public Chromosome mutate(Chromosome chromosome)
	{
		List<Route> gene = chromosome.getGene();
		int genesSize = gene.size();
		for (int i = 0; i < genesSize; i++)
		{
			Route r = gene.get(i);
			for (NodeVrp node : r.getNodes())
			{
				if (rand.nextDouble() > Population.mutationRatio)
				{
					continue;
				}

				r.getNodes().remove(node);

				int V = chromosome.getMap().getFleetSize();

				// Se ainda puder criar uma nova rota e a prob for escolhida,
				// cria
				if (rand.nextDouble() < 1.0 / (V * 2) && genesSize < chromosome.getMap().getFleetSize())
				{
					addNodeAsNewRoute(chromosome, node);
				}
				else
				{
					// Se nao, tenta escolher uma aleatoriamente
					int newRouteIndex = rand.nextInt(genesSize - 1);
					Route selectedRoute = gene.get(newRouteIndex == i ? newRouteIndex + 1 : newRouteIndex);

					if (selectedRoute.getLoad() + node.getDemand() <= selectedRoute.getMaxCapacity())
					{
						selectedRoute.addNode(node);
					}
					else
					{
						// Caso nao caiba nela, tenta primeiro criar uma nova
						// rota
						if (genesSize < chromosome.getMap().getFleetSize())
						{
							addNodeAsNewRoute(chromosome, node);
						}
						else
						{
							// Se nao puder criar uma nova, fica tentando achar
							// uma existente que caiba
							do
							{
								newRouteIndex = rand.nextInt(genesSize - 1);
								selectedRoute = gene.get(newRouteIndex == i ? newRouteIndex + 1 : newRouteIndex);
								if (selectedRoute.getLoad() + node.getDemand() <= selectedRoute.getMaxCapacity())
								{
									selectedRoute.addNode(node);
									break;
								}
								
								log.debug("Tentando novamente (se essa mensagem se repetir infinita, problema!)");
							} while (true);
						}
					}
				}
			}
		}

		return new Chromosome(gene);
	}

	private void addNodeAsNewRoute(Chromosome chromosome, NodeVrp node)
	{
		Route newRoute = new Route(new ArrayList<NodeVrp>(), chromosome.getMap().getVehicleCapacity(), chromosome
				.getMap().getFunction());
		newRoute.addNode(node);
		chromosome.getGene().add(newRoute);
	}

}
