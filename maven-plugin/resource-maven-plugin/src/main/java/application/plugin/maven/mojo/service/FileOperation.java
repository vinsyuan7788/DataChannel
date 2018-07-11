package application.plugin.maven.mojo.service;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.codehaus.plexus.archiver.ArchiverException;
import org.codehaus.plexus.archiver.UnArchiver;
import org.codehaus.plexus.archiver.manager.ArchiverManager;

import application.plugin.maven.mojo.bean.TransMergeFileBean;

public class FileOperation {
	private Log log;

	public void mergeFolder(File src, List<TransMergeFileBean> transMergeFileBeanList) throws IOException{
		if(src.isDirectory()){
			File[] subFiles = src.listFiles();
			for(File subFile : subFiles){
				this.mergeFolder(subFile, transMergeFileBeanList);				
			}
		}else if(src.isFile()){
			this.mergeFile(src, transMergeFileBeanList);
		}
	}
	
	public void mergeWorkFolder(String[] mergeDirectorys, String workDirectory, File outputDirectory, List<TransMergeFileBean> transMergeFileBeanList){
		if (mergeDirectorys == null) {
			return;
		}
		getLog().info("mergeDirectorySize:" + mergeDirectorys.length);
		
		File workFile = new File(workDirectory);
		File[] files = workFile.listFiles();
		if(files == null || files.length == 0){
			return;
		}
		
		for(File file : files){
			
			for (String value : mergeDirectorys) {
				getLog().info("dir:" + value);
				try {
					this.mergeFolder(file, outputDirectory, value, transMergeFileBeanList);
				} catch (IOException e) {
					e.printStackTrace();
					getLog().error("合并文件出错：", e);
				}
			}
			
		}
	}
	
	public void mergeFolder(File src, File dest, String mergeFileName, List<TransMergeFileBean> transMergeFileBeanList) throws IOException{
		File subSrcFile = new File(src.getAbsolutePath() + "/" + mergeFileName);
		if(!subSrcFile.exists()){
			return;
		}
		File subDestFile = new File(dest.getAbsolutePath() + "/" + mergeFileName);
		if(subSrcFile.isDirectory()){
			if(!subDestFile.exists()){
				subDestFile.mkdirs();
			}
			String[] subMergeNames = subSrcFile.list();
			for(String subName : subMergeNames){
				this.mergeFolder(subSrcFile, subDestFile, subName, transMergeFileBeanList);				
			}
		}else if(subSrcFile.isFile()){
			this.mergeFile(subSrcFile, transMergeFileBeanList);
			this.mergeFile(subSrcFile, subDestFile);
		}
	}
	
	private void mergeFile(File srcFile, List<TransMergeFileBean> transMergeFileBeanList) throws IOException{
		if(transMergeFileBeanList == null || transMergeFileBeanList.isEmpty()){
			return;
		}
		for(TransMergeFileBean transMergeFileBean : transMergeFileBeanList){
			File targetMergeFile = transMergeFileBean.getTargetMergeFile();
			
			Pattern pattern = transMergeFileBean.getMergFilePattern();
			
			Matcher matcher = pattern.matcher(srcFile.getAbsolutePath());
			if(!matcher.matches()){
				String absolutePath = srcFile.getAbsolutePath().replaceAll("\\\\", "/"); 
				matcher = pattern.matcher(absolutePath);
				if(!matcher.matches()){
					getLog().info("正则未匹配："+pattern.pattern() + ";" + absolutePath);	
					continue;
				}
			}
			
			getLog().info("正则匹配："+pattern.pattern() + ";" + srcFile.getAbsolutePath() + "; targetMergeFile:" +targetMergeFile);
			
			
			String srcFileStr = FileUtils.readFileToString(srcFile);
			
			if(!targetMergeFile.getParentFile().exists()){
				FileUtils.forceMkdir(targetMergeFile.getParentFile());;
			}
			
			FileUtils.writeStringToFile(targetMergeFile, "\n" + srcFileStr + "\n", true);
		}
	}
	
	private void mergeFile(File srcFile, File destFile){
		if(destFile.exists()){
			getLog().info("配置文件已存在，无需覆盖:" + srcFile.getAbsolutePath());
			return;
		}
		try {
			FileUtils.copyFile(srcFile, destFile);
			getLog().info("复制配置:" + srcFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
			getLog().error("复制配置文件：" + srcFile.getAbsolutePath(), e);
		}
	}
	
	@SuppressWarnings("resource")
	public boolean existFile(File src, String[] mergeDirectorys){
		if(mergeDirectorys == null || mergeDirectorys.length == 0){
			return false;
		}
		try {
			JarFile jarFile = new JarFile(src);
			Enumeration<JarEntry> entries = jarFile.entries();
			while(entries.hasMoreElements()){
				JarEntry jarEntry = entries.nextElement();
				String name = jarEntry.getName();
				for(String mergeName : mergeDirectorys){
					if(name.equals(mergeName) || name.substring(0, name.length() - 1).equals(mergeName)){
						return true;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			getLog().error("", e);
		}
		return false;
	}
	
	public void unpack(File source, File destDir, ArchiverManager archiverManager) throws Exception {
		try {
			UnArchiver unArchiver = archiverManager.getUnArchiver(source);

			unArchiver.setSourceFile(source);

			unArchiver.setDestDirectory(destDir);

			unArchiver.extract();
		} catch (ArchiverException e) {
			throw new Exception("Error unpacking file: " + source + "to: " + destDir, e);
		}
	}
	
	public Log getLog()
    {
        if ( log == null )
        {
            log = new SystemStreamLog();
        }

        return log;
    }
	
//	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		String absolutePath = "G:\\software\\apache-maven-3.3.3\\repo\\commons-codec\\commons-codec\\1.10\\commons-codec-1.10.jar".replaceAll("\\\\", "/"); 
		System.out.println(absolutePath);
		
		
//		Pattern namePattern = Pattern.compile(".*/dict/dict-.*.properties");
//		Matcher matcher = namePattern.matcher("test/dict/dict-bus.properties");
//		if(matcher.matches()){
//			System.out.println("match");
//		}
		
//		File file = new File("G:/software/apache-maven-3.3.3/repo/com/royalnu/cm-bus/0.0.1-SNAPSHOT/cm-bus-0.0.1-SNAPSHOT.jar");
//		JarFile jarFile = new JarFile(file);
//		Enumeration<JarEntry> entries = jarFile.entries();
//		Pattern namePattern = Pattern.compile("dict/dict-.*.properties");
//		
//		File dest = new File("G:/dict-all.properties");
//		while(entries.hasMoreElements()){
//			JarEntry jarEntry = entries.nextElement();
//			String name = jarEntry.getName();
//			System.out.println(name);
//			Matcher matcher = namePattern.matcher(name);
//			if(matcher.matches()){
//				InputStream inputStream = jarFile.getInputStream(jarEntry);
//				List<String> lineList = IOUtils.readLines(inputStream);
//				FileUtils.writeLines(dest, lineList);
//			}
//		}
	}
	
}
