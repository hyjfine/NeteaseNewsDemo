package utils;

/**
 * @author daiyan
 * 
 *         2015-8-19
 */
public class StringUtils {
	/**
	 * 截取<……>sss</……>尖括号中间的内容
	 * @param str
	 * @return
	 */
	public static String subSource(String str) {
		if (str.length() != 0) {
			int first = str.indexOf(">");
			int last = str.lastIndexOf("<");
			str = str.substring(first + 1, last);
		}
		return str;
	}
}
