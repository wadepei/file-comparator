package wade.utils.filecomparator.utils;

import static wade.utils.filecomparator.support.Constants.DEFAULT_CHARSET;
import static wade.utils.filecomparator.support.Constants.DEFAULT_SEPARATOR;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wade.utils.filecomparator.support.Arguments;
import wade.utils.filecomparator.support.Parameter;

public class FileUtils {
	private PrintWriter writer;
	private String separator;
	private Arguments arguments;
	

	public FileUtils(Arguments arguments) {
		super();
		this.arguments = arguments;
		init();
	}
	
	public FileUtils(PrintWriter writer) {
		super();
		this.writer = writer;
	}

	private void init(){
		separator=arguments.getAdditionalParams().get(Parameter.SEPARATOR.getName());
		if(null==separator||"".equals(separator)){
			separator=DEFAULT_SEPARATOR;
		}
		writer=arguments.getWriter();
		if(null==writer){
			writer=new PrintWriter(System.out);
		}
	}

	/**
	 *  Read a file that separate by DEFAULT_SEPARATOR to a Map, eg. "0100102392 288"
	 * @param file
	 * @return
	 */
	public Map<String, String> readFileToMap(String file){
		return readFileToMap(Paths.get(file));
	}
	
	public Map<String, String> readFileToMap(Path path){
		return readFileToMap(path, separator);
	}
	
	public Map<String, String> readFileToMap(Path path, String separator){
		if(!isValidFile(path)){
			return null;
		}
		if(null==separator||"".equals(separator)){
			separator=DEFAULT_SEPARATOR;
		}
		if(!DEFAULT_SEPARATOR.equals(separator)&&!" ".equals(separator)&&!"\t".equals(separator)){
			separator="\\s*"+separator+"\\s*";
		}
		Map<String, String> map=new HashMap<String, String> ();
		Charset charset = Charset.forName(DEFAULT_CHARSET);
		try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
		    String line = null;
		    String[] strs = null;
		    while ((line = reader.readLine()) != null) {
		    	strs=line.split(separator);
		    	if(null==strs||strs.length!=2){
		    		continue;
		    	}
		    	map.put(strs[0], strs[1]);
		    }
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
		writer.println(String.format("Read %d rows in file %s.", map.size(), path));
//		new DisplayUtils(arguments.getWriter()).printMap(map);
		return map;
	}
	
	public List<Map<String, String>> findDiff(){
		return findDiff(arguments.getFile1(), arguments.getFile2());
	}

	
	/**
	 * Find different records between two files.
	 * @param file1
	 * @param file2
	 * @return
	 */
	public List<Map<String, String>> findDiff(String file1, String file2){
		return findDiff(Paths.get(file1),Paths.get(file2));
	}
	
	private boolean isValidFile(Path path){
		if(null==path||!Files.exists(path)||Files.isDirectory(path)){
			System.err.println(String.format("Error: %s not existed or it is not a file.", path));
			return false;
		}
		return true;
	}
	
	public List<Map<String, String>> findDiff(Path path1, Path path2){
		if(!isValidFile(path1)||!isValidFile(path2)){
			return null;
		}
		Map<String, String> map1=readFileToMap(path1);
		Map<String, String> map2=readFileToMap(path2);
		return CompareUtils.findDiff(map1, map2);
	}
}
