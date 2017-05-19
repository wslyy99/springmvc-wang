package com.wang.common.util;

/**
 * 
 * @ClassName: IntDigitPadded
 * 
 * @Description: 数字位数补齐，如果此数小于定义总位数，则前面补零
 * @author PineTree
 * @date 2014年12月19日 下午3:57:38
 * @version
 */
public class IntDigitPadded {
    
    /**
     * 如果数字相加(减)后的值小于定义的数字总长度时前面补零
     * 
     * @param i int类型i
     * @param j int类型j
     * @param digits 数字总位数
     * 如：0001 + 1 = 0002；digits值为4
     * 如：01 + 1 = 02；digits值为2
     * digits值是根据你的数字总长度来决定 
     * @return
     */
    public static String formatNum(int i, int j, int digits){
        //String.format("%0 + 总位数 + d", 数字1 + 数字2)
        return String.format("%0" + digits + "d", i + j);
    }
    
    /**
     * 如果数字位数小于定义总长度，前面补零
     * 
     * @param i 需要补齐的数字
     * @param digits 数字总位数
     * @return String
     */
    public static String formatNumber(int i, int digits) {
        //String.format("%0 + 总位数 + d", 需转换数字)
        return  String.format("%0" + digits + "d", i);
    }
    
    /**
     * 获取X-Y之间的数字，如果X-Y之间小于定义总长度，那么此数字前用零补齐
     * 
     * @param startNum 开始数字
     * @param EndNum 结束数字
     * @return String[]
     */
    public static String[] formatNumbers(int startNum,int EndNum, int digits){
        int len = (EndNum - startNum) + 1; // 获取要循环的数组长度
        System.out.println("length = " + len);
        String[] numbers = new String[len];
        String str = ""; // 第i个数值
        for(int i = 0; i < len; i++){
            System.out.println("i = " + i);
            str = String.format("%0" + digits + "d", startNum++);
            System.out.println(str);
            numbers[i] = str;
        }
        return numbers;
    }
    
    public static void main(String[] args) {
    	System.out.println(IntDigitPadded.formatNumber(305,7));
	}
}
