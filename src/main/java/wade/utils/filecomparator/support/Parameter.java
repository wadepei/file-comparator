package wade.utils.filecomparator.support;

/**
 * 
 * @author Wade Pei <br>
 * @date 2015年12月9日
 */
public enum Parameter {
	OUT("out"), SEPARATOR("separator");

	private Parameter(String name) {
		this.name = name;
	}

	private String name;

	public String getName() {
		return name;
	}
	
}
