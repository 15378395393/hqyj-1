package com.hqyj.javaDemo.util;

/**
 * @Description: Double值转换成钱币大写值
 * @author: HymanHu
 * @date: 2020年3月15日
 */
public class MoneyConvertUtil {
	public static String convertCapitalMoney(double money){
		//防止浮点数四舍五入造成误差
		money = money + 0.005;
		String result = "";
		StringBuffer sbResult = new StringBuffer();
		String capitalLetter = "零壹贰叁肆伍陆柒捌玖";
		String moneyUnit = "分角圆拾佰仟万拾佰仟亿拾佰仟万拾佰仟亿拾佰仟";
		String tempCapital,tempUnit;
		
		int integer = (int)money;//整数部分
		int point = (int) ((money - (double)integer) * 100);//小数部分
		int tempValue;//每一位的值
		
		//货币整数部分转换
		for(int i = 1;integer > 0;i ++){
			tempValue = integer % 10;
			tempCapital = capitalLetter.substring(tempValue, tempValue + 1);
			tempUnit = moneyUnit.substring(i + 1, i + 2);
			result = tempCapital + tempUnit + result;
			sbResult.insert(0, tempUnit).insert(0, tempCapital);
			integer = integer / 10;
		}
		
		//货币小数部分
		tempValue = point / 10;
		if(tempValue != 0){
			for(int i = 1;i > -1;i --){
				tempCapital = capitalLetter.substring(tempValue, tempValue + 1);
				tempUnit = moneyUnit.substring(i, i + 1);
				result = result + tempCapital + tempUnit;
				sbResult.append(tempCapital).append(tempUnit);
				tempValue = point % 10;
			}
		}
		while(sbResult.indexOf("零拾") != -1){
			sbResult.replace(sbResult.indexOf("零拾"), sbResult.indexOf("零拾") + 2, "零");
		}
		while(sbResult.indexOf("零佰") != -1){
			sbResult.replace(sbResult.indexOf("零佰"), sbResult.indexOf("零佰") + 2, "零");
		}
		while(sbResult.indexOf("零仟") != -1){
			sbResult.replace(sbResult.indexOf("零仟"), sbResult.indexOf("零仟") + 2, "零");
		}
		while(sbResult.indexOf("零万") != -1){
			sbResult.replace(sbResult.indexOf("零万"), sbResult.indexOf("零万") + 2, "万");
		}
		while(sbResult.indexOf("零亿") != -1){
			sbResult.replace(sbResult.indexOf("零亿"), sbResult.indexOf("零亿") + 2, "亿");
		}
		while(sbResult.indexOf("零零") != -1){
			sbResult.replace(sbResult.indexOf("零零"), sbResult.indexOf("零零") + 2, "零");
		}
		while(sbResult.indexOf("亿万") != -1){
			sbResult.replace(sbResult.indexOf("亿万"), sbResult.indexOf("亿万") + 2, "亿");
		}
		while(sbResult.indexOf("零圆") != -1){
			sbResult.replace(sbResult.indexOf("零圆"), sbResult.indexOf("零圆") + 2, "圆");
		}
		while(sbResult.indexOf("壹拾") != -1){
			sbResult.replace(sbResult.indexOf("壹拾"), sbResult.indexOf("壹拾") + 2, "拾");
		}
		while(sbResult.lastIndexOf("零") == sbResult.length() - 2){
			sbResult.delete(sbResult.lastIndexOf("零"), sbResult.lastIndexOf("零") + 2);
		}
		if(sbResult.length() == 0){
			sbResult.append("零圆");
			result = "零圆";
		}
		return sbResult.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(MoneyConvertUtil.convertCapitalMoney(101230567.9));
	}
}
