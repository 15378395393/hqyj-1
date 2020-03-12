package com.hqyj.javaDemo.algorithm.gaTsp;

/**
 * 用来计算各客户点间的欧氏距离,计算距离矩阵
 * @author: HymanHu
 * @date: 2020年3月12日
 */
public class DistanceMatrix {
	public static double[][] DistMatrix(double[][] xy){
        int N = xy.length;
        double[][] DM = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                DM[i][j] = Math.hypot(xy[i][0] - xy[j][0],xy[i][1] - xy[j][1]);
            }
        }
        return DM;
    }
}
