package io.youcham.common.entity;

import java.math.BigDecimal;

/**
 * 数字转换为汉语中人民币的大写；
 */
public class NumberToCN {
	/**
	 * 汉语中数字大写；
	 */
	private static final String[] CN_UPPER_NUMBER = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
	/**
	 * 汉语中货币机构大写，这样的设计类似于占位符；
	 */
	private static final String[] CN_UPPER_MONETRAY_UNIT = { "分", "角", "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾", "佰", "仟" };
	/**
	 * 特殊字符：整；
	 */
	private static final String CN_FULL = "整";
	/**
	 * 特殊字符：负；
	 */
	private static final String CN_NEGATIVE = "负";
	/**
	 * 金额的精度，默认值为2；
	 */
	private static final int MONEY_PRECISION = 2;
	/**
	 * 特殊字符：零元整；
	 */
	private static final String CN_ZEOR_FULL = "零元" + CN_FULL;

	/**
	 * 把输入的金额转换为汉语中人民币的大写；
	 * 
	 * @param money
	 *            ： 输入的金额；
	 * @return：对应的汉语大写；
	 */
	public static String number2CNMontrayUnit(double money) {
		BigDecimal numberOfMoney = new BigDecimal(String.valueOf(money));
		StringBuffer sb = new StringBuffer();
		int signum = numberOfMoney.signum();
		// 零元整的情况；
		if (signum == 0) {
			return CN_ZEOR_FULL;
		}
		// 这里会进行金额的四舍五入；
		Long number = numberOfMoney.movePointRight(MONEY_PRECISION).setScale(0, 4).abs().longValue();
		// 得到小数点后两位值；
		Long scale = number % 100;
		int numUnit = 0;
		int numIndex = 0;
		boolean getZero = false;
		// 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11；
		if (!(scale > 0)) {
			numIndex = 2;
			number = number / 100;
			getZero = true;
		}
		if ((scale > 0) && (!(scale % 10 > 0))) {
			numIndex = 1;
			number = number / 10;
			getZero = true;
		}
		int zeroSize = 0;
		while (true) {
			if (number <= 0) {
				break;
			}
			// 每次获取到最后一个数；
			numUnit = (int) (number % 10);
			if (numUnit > 0) {
				if ((numIndex == 9) && (zeroSize >= 3)) {
					sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
				}
				if ((numIndex == 13) && (zeroSize >= 3)) {
					sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
				}
				sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
				sb.insert(0, CN_UPPER_NUMBER[numUnit]);
				getZero = false;
				zeroSize = 0;
			} else {
				++zeroSize;
				if (!(getZero) && numIndex != 2) {
					sb.insert(0, CN_UPPER_NUMBER[numUnit]);
				}
				if (numIndex == 2) {
					if (number > 0) {
						sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
					}
				} else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
					sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
				}
				getZero = true;
			}
			// 让number每次都去掉最后一个数；
			number = number / 10;
			++numIndex;
		}
		// 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负；
		if (signum == -1) {
			sb.insert(0, CN_NEGATIVE);
		}
		// 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整；
		if (!(scale > 0)) {
			sb.append(CN_FULL);
		}
		return sb.toString();
	}

	/**
	 * 数字转换成中文大写；
	 * 
	 * @param num
	 *            ：需处理的数字；
	 * @return：处理完毕的数字；
	 */
	public static String toChinese(int num) {
		String content = String.valueOf(num);
		String text = "";
		if (content.length() == 1) {
			text += getChinese(num);
			return text;
		} else if (content.length() == 2) {
			if (content.substring(0, 1).equals("1")) {
				text += "十";
			} else {
				text += (getChinese(num / 10) + "十");
			}
			text += toChinese(num % 10);
		} else if (content.length() == 3) {
			text += (getChinese(num / 100) + "百");
			if (String.valueOf(num % 100).length() < 2) {
				text += "零";
			}
			text += toChinese(num % 100);
		} else if (content.length() == 4) {
			text += (getChinese(num / 1000) + "千");
			if (String.valueOf(num % 1000).length() < 3) {
				text += "零";
			}
			text += toChinese(num % 1000);
		} else if (content.length() == 5) {
			text += (getChinese(num / 10000) + "萬");
			if (String.valueOf(num % 10000).length() < 4) {
				text += "零";
			}
			text += toChinese(num % 10000);
		}

		return text;
	}

	/**
	 * 获取数字对应的汉字；
	 * 
	 * @param num
	 *            ：数字内容；
	 * @return：对应的汉字大写；
	 */
	private static String getChinese(int num) {
		String text = "";
		switch (num) {
		case 1:
			text = "一";
			break;
		case 2:
			text = "二";
			break;
		case 3:
			text = "三";
			break;
		case 4:
			text = "四";
			break;
		case 5:
			text = "五";
			break;
		case 6:
			text = "六";
			break;
		case 7:
			text = "七";
			break;
		case 8:
			text = "八";
			break;
		case 9:
			text = "九";
			break;
		default:
			text = "零";
			break;
		}
		return text;
	}
}