package com.hqyj.javaDemo.algorithm.gaTsp2;

/**
 * -物种群
 * ----add 添加物种 
 * ----traverse 遍历
 * @author: HymanHu
 * @date: 2020年3月12日
 */
public class SpeciesPopulation {
	// 物种数量
	public static final int SPECIES_COUNT = 4;
	// 起点物种
	public SpeciesIndividual head;

	public SpeciesPopulation() {
		head = new SpeciesIndividual();
	}

	/**
	 * 以物种next属性依次添加物种，行程物种链条
	 */
	public void add(SpeciesIndividual species) {
		// 设置游标
		SpeciesIndividual point = head;
		
		// 移动游标到next上，寻找链条末端next
		while (point.next != null) {
			point = point.next;
		}
		
		// 末端物种next属性添加新物种
		point.next = species;
	}
	
	/**
	 * 遍历物种链条
	 */
	public void traversal() {
		System.out.println("=====初始化物种群链条=====");
		SpeciesIndividual.printArrays("物种链条head:", head.genes);
		// 设置游标
		SpeciesIndividual point = head.next;
		// 移动游标到next上，寻找链条末端next
		int i = 1;
		while (point != null) {
			SpeciesIndividual.printArrays("物种链条next" + i + ":", point.genes);
			point = point.next;
			i ++;
		}
	}

	/**
	 * 初始化物种群
	 */
	public static SpeciesPopulation initSpeciesPopulation() {
		SpeciesPopulation speciesPopulation = new SpeciesPopulation();
		
		// 随机模式添加物种
		for (int i = 0; i < SpeciesPopulation.SPECIES_COUNT; i++) {
			SpeciesIndividual species = new SpeciesIndividual();
			species.createByRandomGenes();
			speciesPopulation.add(species);
		}
		
		 // 贪婪模式添加物种
//		 for(int i = 0; i < SpeciesPopulation.SPECIES_COUNT; i++) {
//			 SpeciesIndividual species=new SpeciesIndividual();
//			 species.createByGreedyGenes();
//			 list.add(species);
//		 }
		
		// 计算每个物种适应度和被选中的几率
		calFitnessAndRate(speciesPopulation);
		
		// 遍历物种链条
		speciesPopulation.traversal();
		
		return speciesPopulation;
	}
	
	/**
	 * 遍历物种群链条，计算每个物种的适应度、被选中的概率
	 * @param speciesPopulation
	 */
	public static void calFitnessAndRate(SpeciesPopulation speciesPopulation) {
		// 计算物种群总适应度
		float totalFitness = 0.0f;
		SpeciesIndividual point = speciesPopulation.head.next;
		while (point != null) {
			point.calFitness();
			totalFitness += point.fitness;
			point = point.next;
		}

		// 计算单个物种选中概率
		point = speciesPopulation.head.next;
		while (point != null) {
			point.rate = point.fitness / totalFitness;
			point = point.next;
		}
	}
}
