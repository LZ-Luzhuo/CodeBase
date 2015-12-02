package utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-5 上午12:09:45
 * 
 * 描述:拼音工具类,如获取字符串首字母,拼音
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class PinYin {
	/**
	 * 获取拼音
	 * </p>案例:"小闲"-->xiăo xián 
	 * @param inputString 字符串
	 * @return 拼音 / ""
	 */
	public static String getPinYin(String inputString) {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
		format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);

		char[] input = inputString.trim().toCharArray();
		StringBuffer output = new StringBuffer("");
		try {
			for (int i = 0; i < input.length; i++) {
				if (Character.toString(input[i]).matches("[\\\u4E00-\\\u9FA5]+")) {
					String[] temp = PinyinHelper.toHanyuPinyinStringArray(
							input[i], format);
					output.append(temp[0]);
					output.append(" ");
				} else
					output.append(Character.toString(input[i]));
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return output.toString();
	}

	/**
	 * 获取字符串首字母(大写)
	 * 
	 * </p>案例:"小闲"-->XIAO3;getPinYin-->X.
	 * @param inputString 字符串 / 拼音
	 * @return 首字母(大写) / ""
	 */
	public static String getFirstAlpha(String inputString) {
		if (inputString != null && !inputString.equals("")) {
			String[] array = PinyinHelper.toHanyuPinyinStringArray(inputString.charAt(0));

			if (array == null) {
				return inputString.substring(0, 1).toUpperCase();
			} else {
				return array[0].toUpperCase();
			}
		}
		return "";
	}
	
	/**
	 * 根据传入的字符串(包含汉字),得到拼音;不对特殊字符进行处理
	 * <b>拼音 --> PINYIN;拼 音-->PINYIN;拼音aa-->PINYINaa;拼音@#-->PINYIN@#</b>
	 * @param str 字符串
	 * @return
	 */
	public static String getPinyin(String str) {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		
		StringBuilder sb = new StringBuilder();
		
		char[] charArray = str.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			// 如果是空格, 跳过
			if(Character.isWhitespace(c)){
				continue;
			}
			if(c >= -127 && c < 128){
				// 肯定不是汉字
				sb.append(c);
			}else {
				String s = "";
				try {
					s = PinyinHelper.toHanyuPinyinStringArray(c, format)[0];
					sb.append(s);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
					sb.append(s);
				}
			}
		}
		return sb.toString();
	}
}
