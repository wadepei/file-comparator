package wade.utils.filecomparator.utils;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Wade Pei <br>
 * @date 2015年12月8日
 */
public class FileUtilsTest {
	PrintWriter writer;
	FileUtils fileUtils;
	DisplayUtils displayUtils;
	String file = "C:/tmp/Data/20151206/20151206_sh.txt";
	String file2 = "C:/tmp/Data/20151206/20151206_zw.txt";

	@Before
	public void setUp() throws Exception {
		writer = new PrintWriter(System.out);
		fileUtils = new FileUtils(writer);
		displayUtils = new DisplayUtils(writer);
	}

	@After
	public void tearDown() throws Exception {
		writer.close();
	}

	@Test
	public void testReadFileToMap() {
		fileUtils.readFileToMap(file);
	}

	@Test
	public void testFindDiff() {
		List<Map<String, String>> result = fileUtils.findDiff(file, file2);
		displayUtils.displayDiff(result, file, file2);
	}

	@Test
	public void testFindRecordsBelowValue() {
		Map<String, String> map1 = fileUtils.readFileToMap(file);
		Map<String, String> map2 = fileUtils.readFileToMap(file2);
		String value = "288";
		Map<String, String> result1 = CompareUtils.findRecordsBelowValue(map1, value);
		Map<String, String> result2 = CompareUtils.findRecordsBelowValue(map2, value);
		displayUtils.displayRecordsBelowValue(result1, file, value);
		displayUtils.displayRecordsBelowValue(result2, file2, value);
	}

}
