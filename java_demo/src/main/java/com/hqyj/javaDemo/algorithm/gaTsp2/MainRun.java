package com.hqyj.javaDemo.algorithm.gaTsp2;

public class MainRun {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// 创建遗传算法驱动对象
		GeneticAlgorithm GA = new GeneticAlgorithm();

		// 创建初始种群
		SpeciesPopulation speciesPopulation = SpeciesPopulation.initSpeciesPopulation();

		// 开始遗传算法（选择算子、交叉算子、变异算子）
		SpeciesIndividual bestRate = GA.getSpeciesByGA(speciesPopulation);

		// 打印路径与最短距离
		bestRate.printResult();

	}
}
