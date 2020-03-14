package com.hqyj.javaDemo.algorithm.gaTsp2;

import java.util.Random;

/**
 * -遗传算法类
 * ----run 开始跑算法
 * ----createBeginningSpecies 创建种群
 * ----calRate 计算每一种物种被选中的概率
 * ----select  轮盘策略 选择适应度高的物种
 * ----crossover 染色体交叉
 * ----mutate 染色体变异
 * ----getBest 获得适应度最大的物种
 * @author: HymanHu
 * @date: 2020年3月12日
 */
public class GeneticAlgorithm {
	
	// 进化代数
	public static final int DEVELOP_NUM = 1;
	// 交叉概率
	public static final float PCL = 0.2f, PCH = 0.95f;
	// 变异概率
	public static final float PM = 0.4f;
	
	/**
	 * 通过遗传算法获取最优物种
	 * @param speciesPopulation		初代物种群
	 * @return
	 */
	public SpeciesIndividual getSpeciesByGA(SpeciesPopulation speciesPopulation) {
		for (int i = 1; i <= DEVELOP_NUM; i++) {
			System.out.println("==============开始第 " + i + " 次进化===========");
			// 选择函数
			selectFunction(speciesPopulation);
			// 交叉
			crossoverFunction(speciesPopulation);
			// 变异
//			mutate(speciesPopulation);
		}

		return getBest(speciesPopulation);
	}

	// 选择函数（轮盘赌）
	public void selectFunction(SpeciesPopulation speciesPopulation) {
		// 找出物种群中最优物种、最大选中率、最小选中率
		SpeciesIndividual point = speciesPopulation.head.next;
		SpeciesIndividual talentSpecies = speciesPopulation.head.next;
		float maxRate = point.rate;
		float minRate = point.rate;
		while (point != null) {
			maxRate = maxRate <= point.rate ? point.rate : maxRate;
			minRate = minRate >= point.rate ? point.rate : minRate;
			talentSpecies = talentSpecies.distance > point.distance ? point : talentSpecies;
			point = point.next;
		}
		
		// 创建新物种群装载通过选择后的物种
		SpeciesPopulation newSpeciesPopulation = new SpeciesPopulation();
		int speciesNumber = 0;
		// 初始化游标
		point = speciesPopulation.head.next;
		while (point != null) {
			// 针对每个物种，随机产生产生minRate-maxRate的概率
			// 如果随机概率小，则表明该物种被留下，反之则淘汰该物种
			float rate = (float) (Math.random()*(maxRate - minRate) + minRate);
			if (rate <= point.rate) {
				SpeciesIndividual newSpecies = point.clone();
				newSpeciesPopulation.add(newSpecies);
				speciesNumber ++;
			}
			point = point.next;
		}
		
		// 将选择后的物种群赋值回去
		speciesPopulation.head = newSpeciesPopulation.head;
		speciesPopulation.speciesNumber = speciesNumber;
		
		// 打印物种群信息
		speciesPopulation.printPopulationInfo("选择函数后物种群");
	}
	
	// 补全物种群
	public void completionPopulation(SpeciesPopulation speciesPopulation) {
		SpeciesIndividual point = speciesPopulation.head.next;
		int index = 1;
		while (point != null) {
			if (index > speciesPopulation.speciesNumber) {
				
			}
			point = point.next;
			index ++;
		}

		// 打印物种群信息
		speciesPopulation.printPopulationInfo("交叉函数后物种群");
	}
	
	// 交叉函数（交叉后的结果作为son）
	public SpeciesIndividual crossoverFunction(SpeciesPopulation speciesPopulation) {
		// 以概率pcl~pch进行
		float rate = (float) Math.random();
		if (rate > PCL && rate < PCH) {
			System.out.println("######");
			// 寻找物种群随机位
			Random rand = new Random();
			int startIndex = rand.nextInt(speciesPopulation.speciesNumber);
			
			// 找出startIndex位上的物种
			SpeciesIndividual point = speciesPopulation.head.next;
			while (point != null && startIndex != 0) {
				point = point.next;
				startIndex--;
			}

			// 从startIndex位开始交叉
			if (point.next != null) {
				// 找出该物种genes随机位
				int begin = rand.nextInt(TSPData.CITY_NUM);

				// 取point和point.next进行交叉，形成新的两个染色体
				for (int i = begin; i < TSPData.CITY_NUM; i++) {
					// 找出point.genes中与point.next.genes[i]相等的位置fir
					// 找出point.next.genes中与point.genes[i]相等的位置sec
					int fir, sec;
					for (fir = 0; fir < 31 && !point.genes[fir].equals(point.next.genes[i]); fir++);
					for (sec = 0; sec < 31 && !point.next.genes[sec].equals(point.genes[i]); sec++);
					// 两个基因互换
					String tmp;
					tmp = point.genes[i];
					point.genes[i] = point.next.genes[i];
					point.next.genes[i] = tmp;

					// 消去互换后重复的那个基因
					point.genes[fir] = point.next.genes[i];
					point.next.genes[sec] = point.genes[i];
				}
			}
			return point;
		} else {
			return null;
		}
	}

	// 变异操作
	void mutate(SpeciesPopulation list) {
		// 每一物种均有变异的机会,以概率pm进行
		SpeciesIndividual point = list.head.next;
		while (point != null) {
			float rate = (float) Math.random();
			if (rate < PM) {
				// 寻找逆转左右端点
				Random rand = new Random();
				int left = rand.nextInt(TSPData.CITY_NUM);
				int right = rand.nextInt(TSPData.CITY_NUM);
				if (left > right) {
					int tmp;
					tmp = left;
					left = right;
					right = tmp;
				}

				// 逆转left-right下标元素
				while (left < right) {
					String tmp;
					tmp = point.genes[left];
					point.genes[left] = point.genes[right];
					point.genes[right] = tmp;

					left++;
					right--;
				}
			}
			point = point.next;
		}
	}

	// 获得适应度最大的物种
	SpeciesIndividual getBest(SpeciesPopulation list) {
		float distance = Float.MAX_VALUE;
		SpeciesIndividual bestSpecies = null;
		SpeciesIndividual point = list.head.next;// 游标
		while (point != null)// 寻找表尾结点
		{
			if (distance > point.distance) {
				bestSpecies = point;
				distance = point.distance;
			}

			point = point.next;
		}

		return bestSpecies;
	}
}
