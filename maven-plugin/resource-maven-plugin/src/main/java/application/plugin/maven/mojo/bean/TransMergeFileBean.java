package application.plugin.maven.mojo.bean;

import java.io.File;
import java.util.regex.Pattern;

public class TransMergeFileBean {

	private Pattern mergFilePattern;
	
	private File targetMergeFile;

	public Pattern getMergFilePattern() {
		return mergFilePattern;
	}

	public void setMergFilePattern(Pattern mergFilePattern) {
		this.mergFilePattern = mergFilePattern;
	}

	public File getTargetMergeFile() {
		return targetMergeFile;
	}

	public void setTargetMergeFile(File targetMergeFile) {
		this.targetMergeFile = targetMergeFile;
	}
	
	
	
}
