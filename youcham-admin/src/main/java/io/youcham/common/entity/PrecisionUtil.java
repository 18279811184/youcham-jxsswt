package io.youcham.common.entity;

import java.math.BigDecimal;


/**
 * 浮点数计算的精度处理；
 */
public class PrecisionUtil {
	// 默认精度；
	private static final int DEF_DIV_SCALE = 10;

	// 求和；
	public static double add(double d1, double d2) {
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		return b1.add(b2).doubleValue();

	}

	// 求和；
	public static double add(double[] dd) {
		BigDecimal res = new BigDecimal(0);
		for (double d : dd) {
			BigDecimal d1 = new BigDecimal(Double.toString(d));
			res = res.add(d1);
		}
		
		return res.doubleValue();

	}
	
	// 求差；
	public static double sub(double d1, double d2) {
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		return b1.subtract(b2).doubleValue();

	}

	
	// 求差；
	public static double sub(double[] dd) {
		BigDecimal res = new BigDecimal(0);
		for (double d : dd) {
			BigDecimal d1 = new BigDecimal(Double.toString(d));
			res = res.subtract(d1);
		}
		
		return res.doubleValue();

	}
	
	// 求积；
	public static double mul(double d1, double d2) {
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		return b1.multiply(b2).doubleValue();

	}

	// 求余；
	public static double div(double d1, double d2) {
		return div(d1, d2, DEF_DIV_SCALE);
	}

	// 自定义精度的求余；* @param v1 被除数  * @param v2 除数  
	public static double div(double d1, double d2, int scale) {
		if (scale < 0) {
			//LogUtil.print(LogContent.PRECISIONERROR.getValue());
			return 0;
		} else {
			BigDecimal b1 = new BigDecimal(Double.toString(d1));
			BigDecimal b2 = new BigDecimal(Double.toString(d2));
			return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
	}
}