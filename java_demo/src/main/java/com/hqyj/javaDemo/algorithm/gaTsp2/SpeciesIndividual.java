package com.hqyj.javaDemo.algorithm.gaTsp2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * -个体类 
 * ----createByRandomGenes 初始物种基因(随机) 基因直接用城市序列编码
 * ----calFitness 计算物种适应度
 * ----printRate 打印路径
 * @author: HymanHu
 * @date: 2020年3月12日
 */
public class SpeciesIndividual {
	// 基因序列
	public String[] genes;
	// 计算基因序列总路程
	public float distance;
	// 下一个物种
	public SpeciesIndividual next;
	// 适应度
	public float fitness;
	// 当前物种被选中的概率(this.fitness / 物种群所有物种fitness之和)
	public float rate;

	public SpeciesIndividual() {
		this.genes = new String[TSPData.CITY_NUM];
		this.fitness = 0.0f;
		this.distance = 0.0f;
		this.next = null;
		this.rate = 0.0f;
	}
	
	/**
	 * -打印基因染色体信息
	 */
	public static void printIndividualInfo(String desc, SpeciesIndividual individual) {
		if (individual == null) {
			return;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(desc)
			.append("[").append(String.join("-", individual.genes)).append("]").append("--")
			.append(individual.distance).append("--")
			.append(individual.fitness).append("--")
			.append(individual.rate);
		System.out.println(sb.toString());
	}
	
	/**
	 * -打印染色体计算的结果
	 */
	public void printResult() {
		List<String> list = new ArrayList<String>(Arrays.asList(genes));
		list.add(genes[0]);
		System.out.println("最短路线：" + String.join("-", list.toArray(new String[genes.length + 1])));
		System.out.print("最短距离：" + distance);
	}

	/**
	 * -初始化基因，随机模式
	 */
	void createByRandomGenes(int startCityId) {
		// 初始化基因为1-CITY_NUM序列
		for (int i = 0; i < genes.length; i++) {
			genes[i] = Integer.toString(i + 1);
		}

		// 随机交换顺序
		Random rand = new Random();
		IntStream.range(0, genes.length).forEach(i -> {
			int num = i + rand.nextInt(genes.length - i);
			String tmp = genes[num];
			genes[num] = genes[i];
			genes[i] = tmp;
		});
		
		// 将startCityId提到第一位
		IntStream.range(0, genes.length).forEach(i -> {
			if (genes[i].equals(String.valueOf(startCityId))) {
				String tmp = genes[0];
				genes[0] = genes[i];
				genes[i] = tmp;
			}
		});
		
//		printIndividualInfo("初始化城市基因序列(随机模式):", this);
	}

	/**
	 * -初始化基因，贪婪模式
	 */
	void createByGreedyGenes(int startCityId) {
		Random rand = new Random();
		// 随机产生一个城市作为起点
		int i = rand.nextInt(TSPData.CITY_NUM);
		genes[0] = Integer.toString(i + 1);
		// 终点
		int j;
		int cityNum = 0;
		do {
			cityNum++;

			// 选出单源最短城市
			float minDis = Integer.MAX_VALUE;
			int minCity = 0;
			for (j = 0; j < TSPData.CITY_NUM; j++) {
				if (j != i) {
					// 判是否和已有重复
					boolean repeat = false;
					for (int n = 0; n < cityNum; n++) {
						if (Integer.parseInt(genes[n]) == j + 1) {
							repeat = true;// 重了
							break;
						}
					}
					if (repeat == false)// 没重
					{
						// 判长度
						if (TSPData.disMap[i][j] < minDis) {
							minDis = TSPData.disMap[i][j];
							minCity = j;
						}
					}
				}
			}

			// 加入到染色体
			genes[cityNum] = Integer.toString(minCity + 1);
			i = minCity;
		} while (cityNum < TSPData.CITY_NUM - 1);
		
		// 将startCityId提到第一位
		IntStream.range(0, genes.length).forEach(item -> {
			if (genes[item].equals(String.valueOf(startCityId))) {
				String tmp = genes[0];
				genes[0] = genes[item];
				genes[item] = tmp;
			}
		});
		
//		printIndividualInfo("初始化城市基因序列(贪婪模式)：", this);
	}

	/**
	 * -计算物种总距离、适应度
	 */
	public void calFitness() {
		float totalDis = 0.0f;
		int nextCity = 0;
		for (int i = 0; i < TSPData.CITY_NUM; i++) {
			int curCity = Integer.parseInt(this.genes[i]) - 1;
			nextCity = Integer.parseInt(this.genes[(i + 1) % TSPData.CITY_NUM]) - 1;

			totalDis += TSPData.disMap[curCity][nextCity];
		}
		
		// 累加回到原点的距离
		totalDis += TSPData.disMap[nextCity][Integer.parseInt(this.genes[0]) - 1];

		this.distance = totalDis;
		this.fitness = 1.0f / totalDis;
	}

	/**
	 * -物种克隆
	 */
	public SpeciesIndividual clone() {
		SpeciesIndividual species = new SpeciesIndividual();

		for (int i = 0; i < this.genes.length; i++) {
			species.genes[i] = this.genes[i];
		}
		species.distance = this.distance;
		species.fitness = this.fitness;

		return species;
	}
}
