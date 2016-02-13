package wade.utils.filecomparator.support;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Wade Pei <br>
 * @date 2015年12月9日
 */
public class Arguments {

	// Required arguments
	private Path file1;
	private Path file2;
	private boolean showHelp = false;

	// Additional parameters
	private Map<String, String> additionalParams = new HashMap<String, String>();
	private PrintWriter writer;

	// Is valid and Error message
	private boolean valid = true;
	private String errMsg = null;

	public static Arguments create(List<String> params) {
		Arguments arguments = new Arguments();
		List<String> requiredParams = arguments.processParams(params);
		if (arguments.isShowHelp()) {
			return arguments;
		} else {
			arguments.processRequiredParams(requiredParams);
			arguments.initWriter();
			return arguments;
		}
	}

	private void processRequiredParams(List<String> params) {
		if (params.size() < 2) {
			processWhenRequiredLessThanTwo(params);
			return;
		}
		file1 = Paths.get(params.get(0));
		file2 = Paths.get(params.get(1));
	}

	private void processWhenRequiredLessThanTwo(List<String> params) {
		Path currPath = params.size() < 1 ? Paths.get(".") : Paths.get(params.get(0));
		final List<Path> files = new ArrayList<>();
		try {
			Files.walkFileTree(currPath, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					if (!attrs.isDirectory()) {
						files.add(file);
					}
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (null == files || files.size() != 2) {
			valid = false;
			errMsg = String.format(
					"Error: the number of files in this directory is %d. The directory should contains exactly TWO files.",
					files.size());
			// System.err.println(errMsg);
			return;
		}
		file1 = files.get(0);
		file2 = files.get(1);
	}

	private List<String> processParams(List<String> params) {
		List<String> requiredParams = new ArrayList<String>();
		for (String param : params) {
			if (processShowHelp(param)) {
				break;
			} else if (param.startsWith("-D")) {
				parseAdditionalParam(param.substring(2));
			} else {
				requiredParams.add(param);
			}
		}
		return requiredParams;
	}

	private boolean processShowHelp(String param) {
		if (param.equals("-h") || param.equals("-help") || param.equals("--help")) {
			showHelp = true;
			return true;
		}
		return false;
	}

	private void parseAdditionalParam(String param) {
		int idx = param.indexOf('=');
		if (idx > 0) {
			String paramKey = param.substring(0, idx);
			String paramVal = param.substring(idx + 1);
			additionalParams.put(paramKey, paramVal);
		}
	}

	private void initWriter() {
		String outFile = getAdditionalParams().get(Parameter.OUT.getName());
		if (null != outFile) {
			Charset charset = Charset.forName(Constants.DEFAULT_CHARSET);
			try {
				writer = new PrintWriter(Files.newBufferedWriter(Paths.get(outFile), charset));
			} catch (IOException e) {
				valid = false;
				errMsg = String.format("Error: initialize the writer to %s failed.", outFile);
				// System.err.println(errMsg);
			}
		} else {
			writer = new PrintWriter(System.out);
		}

	}

	public Map<String, String> getAdditionalParams() {
		return additionalParams;
	}

	public boolean isShowHelp() {
		return showHelp;
	}

	public boolean isValid() {
		return valid;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public Path getFile1() {
		return file1;
	}

	public Path getFile2() {
		return file2;
	}

	public PrintWriter getWriter() {
		if (null == writer) {
			writer = new PrintWriter(System.out);
		}
		return writer;
	}

}
