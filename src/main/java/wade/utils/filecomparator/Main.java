package wade.utils.filecomparator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import wade.utils.filecomparator.support.Arguments;
import wade.utils.filecomparator.support.Parameter;
import wade.utils.filecomparator.utils.DisplayUtils;
import wade.utils.filecomparator.utils.FileUtils;

/**
 * 
 * @author Wade Pei <br>
 * @date 2015年12月9日
 */
public class Main {

	public static void main(String[] args) {
		Arguments arguments=Arguments.create(Arrays.asList(args));
		if(arguments.isShowHelp()){
			System.out.println(usage());
			return;
		}
		if(!arguments.isValid()){
			System.out.println(arguments.getErrMsg());
			System.out.println("Type 'java -jar Diff.jar -h' for the usage info (assume you have packaged this program into a executable jar Diff.jar).");
			return;
		}
		List<Map<String, String>> result = new FileUtils(arguments).findDiff();
		String separator = arguments.getAdditionalParams().get(Parameter.SEPARATOR.getName());
		DisplayUtils displayUtils=new DisplayUtils(arguments.getWriter(), null==separator?" ":separator);
		displayUtils.displayDiff(result, arguments.getFile1().toString(), arguments.getFile2().toString());
		arguments.getWriter().close();
	}

	private static String usage() {
		StringBuilder sb = new StringBuilder("This tool is used to compare the differences between two text files within which each line is a key-value pair.\n");
		sb.append("The default separator of the key-value in a line is white space, which includes both space character or tab character. You may specify any other string as the separator by define -Dseparator=...\n")
		.append("Usage (assume you have packaged this program into an executable jar Diff.jar):\n")
		.append("[1] java -jar Diff.jar [options] <file1> <file2>\n")
		.append("[2] java -jar Diff.jar [options] <directory>\n")
		.append("Note: the directory you choose to compare must contains exactly TWO files. \n")
		.append("Options include:\n")
		.append("-h -help --help\t: show usage info.\n")
		.append("-Dseparator=...\t: defines the separator other than the default space character.\n")
		.append("-Dout=...\t: defines the output file to store the result.\n")
		.append("Examples:\n")
		.append("java -jar Diff.jar a.txt b.txt \t// Compare the two files a.txt and b.txt\n")
		.append("java -jar Diff.jar foo/bar \t// Compare the two files in the directory foo/bar\n")
		.append("java -jar Diff.jar . \t\t// Compare the two files in current directory\n")
		.append("java -jar Diff.jar . -Dseparator=: \t// Defines the separator of ':'\n")
		.append("java -jar Diff.jar . -Dout=result.txt \t// Defines the output file 'result.txt' to store the comparing result\n").toString();
		return sb.toString();
	}

}
