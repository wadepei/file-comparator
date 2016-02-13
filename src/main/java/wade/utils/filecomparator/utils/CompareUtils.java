package wade.utils.filecomparator.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Wade Pei <br>
 * @date 2015年12月10日
 */
public class CompareUtils {

	/**
	 * Find the differences between map map1 and map2. The returned List consists of three Maps, the first is the map of
	 * all the records with the same keys but the different values; The second map is the records that have the keys
	 * only exist in the first map; The third map is the records that have the keys only exist in the second map;
	 * 
	 * @param map1 the first map to be compared
	 * @param map2 the second map to be compared
	 * @return the compare result
	 */
	public static List<Map<String, String>> findDiff(Map<String, String> map1, Map<String, String> map2) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>(3);
		if (null == map1 || null == map2) {
			return result;
		}
		if (null == map1 || map1.isEmpty()) {
			result.add(2, map2);
			return result;
		}
		if (null == map2 || map2.isEmpty()) {
			result.add(1, map1);
			return result;
		}
		Map<String, String> result1 = new HashMap<String, String>();
		Map<String, String> result2 = new HashMap<String, String>();
		Map<String, String> result3 = new HashMap<String, String>();
		result.add(result1);
		result.add(result2);
		result.add(result3);
		String val1 = null, val2 = null;
		for (String key : map1.keySet()) {
			if (map2.containsKey(key)) {
				val1 = map1.get(key);
				val2 = map2.get(key);
				if (null != val1 && !val1.equals(val2)) {
					// Separate val1 and val2 by ' '.
					result1.put(key, val1 + "\t" + val2);
				}
			} else {
				result2.put(key, val1);
			}
		}
		for (String key : map2.keySet()) {
			if (!map1.containsKey(key)) {
				result3.put(key, map2.get(key));
			}
		}
		return result;
	}

	public static Map<String, String> findRecordsBelowValue(Map<String, String> map, String value){
		return findRecordsBelowValue(map, value, true);
	}

	/**
	 * Find the records whose value is smaller than the specified argument value.
	 * @param map
	 * @param value
	 * @param intValue
	 * @return
	 */
	public static Map<String, String> findRecordsBelowValue(Map<String, String> map, String value, boolean intValue){
		if(null==map||null==value){
			return null;
		}
		Map<String, String> result=new HashMap<String, String>();
		String val=null;
		for(String key:map.keySet()){
			val=map.get(key);
			if(intValue){
				int val1=Integer.parseInt(val);
				int val2=Integer.parseInt(value);
				if(val1<val2){
					result.put(key, val);
				}
			}else{
				if(null!=val&&val.compareTo(value)<0){
					result.put(key, val);
				}
			}
		}
		return result;
	}

}
