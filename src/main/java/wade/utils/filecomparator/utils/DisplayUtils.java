package wade.utils.filecomparator.utils;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Wade Pei <br>
 * @date 2015年12月8日
 */
public class DisplayUtils {
	private PrintWriter writer;
	private String separator;

	public DisplayUtils(PrintWriter writer) {
		this(writer, " ");
	}

	public DisplayUtils(PrintWriter writer, String separator) {
		super();
		this.writer = writer;
		this.separator = separator;
	}

	public void displayDiff(List<Map<String, String>> result, String file1, String file2) {
		if (null == result || result.isEmpty() || null == file1 || null == file2) {
			return;
		}
		if (null != result.get(0) && !result.get(0).isEmpty()) {
			writer.println(String.format("The records of the same key with different values are (total number %d):", result.get(0).size()));
			writer.println(String.format("The first column is key, the second is value from %s, the third is value from %s",
					file1, file2));
			printMap(result.get(0));
		}
		String formatOfOneSide="The records that only in file %s are (total number %d):";
		if (result.size()>1 && null != result.get(1) && !result.get(1).isEmpty()) {
			writer.println();
			writer.println(String.format(formatOfOneSide, file1, result.get(1).size()));
			printMap(result.get(1));
		}
		if (result.size()>2 && null != result.get(2) && !result.get(2).isEmpty()) {
			writer.println();
			writer.println(String.format(formatOfOneSide, file2, result.get(2).size()));
			printMap(result.get(2));
		}
	}

	public void displayRecordsBelowValue(Map<String, String> result, String file, String value) {
		if (null == result || result.isEmpty() || null == file || null == value) {
			return;
		}
		writer.println(String.format("File %s's records below value %s are:", file, value));
		printMap(result);
	}

	public void printMap(Map<String, String> map) {
		printMap(map, separator);
	}

	public void printMap(Map<String, String> map, String separator) {
		if(null==map||map.isEmpty()){
			return;
		}
		if(null==separator||"".equals(separator)){
			separator=" ";
		}
		for (String key : map.keySet()) {
			writer.println(String.format("%s%s%s", key, separator, map.get(key)));
		}
	}

}
