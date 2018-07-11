package application.plugin.maven.mojo.bean;

public class MergeFileBean {

	private String mergeFilePattern;

	private String targetMergeName;

	public String getMergeFilePattern() {
		return mergeFilePattern;
	}

	public void setMergeFilePattern(String mergeFilePattern) {
		this.mergeFilePattern = mergeFilePattern;
	}

	public String getTargetMergeName() {
		return targetMergeName;
	}

	public void setTargetMergeName(String targetMergeName) {
		this.targetMergeName = targetMergeName;
	}

	
}
