package utils;

/**
 * @author daiyan
 * 
 *         2015-8-19
 */
public class StringUtils {
	/**
	 * ��ȡ<����>sss</����>�������м������
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
