package com.hqyj.javaDemo.algorithm.gaTsp2;

import java.util.Random;

/**
 * -遗传算法类-网上算法
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
public class GeneticAlgorithmBack {
	
	// 进化代数
	public static final int DEVELOP_NUM = 4;
	// 交叉概率
	public static final float PCL = 0.6f, PCH = 0.95f;
	// 变异概率
	public static final float PM = 0.4f;
	
	
	/**
	 * 通过遗传算法获取最优物种
	 * @param speciesPopulation		初代物种群
	 * @return
	 */
	public SpeciesIndividual getSpeciesByGA(SpeciesPopulation speciesPopulation) {
		for (int i = 1; i <= DEVELOP_NUM; i++) {
			System.out.println("==============开始第" + i + "次迭代===========");
			// 选择函数
			selectFunction(speciesPopulation);
			// 交叉
			crossover(speciesPopulation);
			// 变异
			mutate(speciesPopulation);
		}

		return getBest(speciesPopulation);
	}

	// 选择优秀物种（轮盘赌）
	public void selectFunction(SpeciesPopulation speciesPopulation) {
		// 找出最大适应度物种
		float talentDis = Float.MAX_VALUE;
		SpeciesIndividual talentSpecies = null;
		SpeciesIndividual point = speciesPopulation.head.next;// 游标

		while (point != null) {
			if (talentDis > point.distance) {
				talentDis = point.distance;
				talentSpecies = point;
			}
			point = point.next;
		}

		// 将最大适应度物种复制talentNum个
		SpeciesPopulation newSpeciesPopulation = new SpeciesPopulation();
		int talentNum = (int) (SpeciesPopulation.SPECIES_INITIALIZE_COUNT / 4);
		for (int i = 1; i <= talentNum; i++) {
			// 复制物种至新表
			SpeciesIndividual newSpecies = talentSpecies.clone();
			newSpeciesPopulation.add(newSpecies);
		}

		// 轮盘赌list.speciesNum-talentNum次
		int roundNum = SpeciesPopulation.SPECIES_INITIALIZE_COUNT - talentNum;
		for (int i = 1; i <= roundNum; i++) {
			// 产生0-1的概率
			float rate = (float) Math.random();

			SpeciesIndividual oldPoint = speciesPopulation.head.next;// 游标
			while (oldPoint != null && oldPoint != talentSpecies)// 寻找表尾结点
			{
				if (rate <= oldPoint.rate) {
					SpeciesIndividual newSpecies = oldPoint.clone();
					newSpeciesPopulation.add(newSpecies);

					break;
				} else {
					rate = rate - oldPoint.rate;
				}
				oldPoint = oldPoint.next;
			}
			if (oldPoint == null || oldPoint == talentSpecies) {
				// 复制最后一个
				point = speciesPopulation.head;// 游标
				while (point.next != null)// 寻找表尾结点
					point = point.next;
				SpeciesIndividual newSpecies = point.clone();
				newSpeciesPopulation.add(newSpecies);
			}

		}
		speciesPopulation.head = newSpeciesPopulation.head;
	}

	// 交叉操作
	void crossover(SpeciesPopulation list) {
		// 以概率pcl~pch进行
		float rate = (float) Math.random();
		if (rate > PCL && rate < PCH) {
			SpeciesIndividual point = list.head.next;// 游标
			Random rand = new Random();
			int find = rand.nextInt(SpeciesPopulation.SPECIES_INITIALIZE_COUNT);
			while (point != null && find != 0)// 寻找表尾结点
			{
				point = point.next;
				find--;
			}

			if (point.next != null) {
				int begin = rand.nextInt(TSPData.CITY_NUM);

				// 取point和point.next进行交叉，形成新的两个染色体
				for (int i = begin; i < TSPData.CITY_NUM; i++) {
					// 找出point.genes中与point.next.genes[i]相等的位置fir
					// 找出point.next.genes中与point.genes[i]相等的位置sec
					int fir, sec;
					for (fir = 0; !point.genes[fir].equals(point.next.genes[i]); fir++)
						;
					for (sec = 0; !point.next.genes[sec].equals(point.genes[i]); sec++)
						;
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
