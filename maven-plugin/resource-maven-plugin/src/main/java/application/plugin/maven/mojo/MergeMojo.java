package application.plugin.maven.mojo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.maven.artifact.Artifact;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.archiver.manager.ArchiverManager;
import org.codehaus.plexus.archiver.manager.NoSuchArchiverException;

import application.plugin.maven.mojo.bean.MergeFileBean;
import application.plugin.maven.mojo.bean.TransMergeFileBean;
import application.plugin.maven.mojo.service.FileOperation;

/**
 * Goal which hello a timestamp file.
 * 
 */
@Mojo(name = "merge", requiresDependencyResolution = ResolutionScope.COMPILE, inheritByDefault = false)
@Execute(phase = LifecyclePhase.PREPARE_PACKAGE)
public class MergeMojo extends AbstractMojo {

	@Parameter(defaultValue = "${project.build.directory}", required = true)
	private File outputDirectory;

	@Parameter(defaultValue = "${project}", readonly = true, required = true)
	private MavenProject project;

	@Parameter
	private String[] mergeDirectorys;

	@Parameter
	private String workDirectory;

	@Component
	private ArchiverManager archiverManager;
	
	@Parameter
	private MergeFileBean[] mergeFileBeans;
	
	private FileOperation fileOperation = new FileOperation();

	@SuppressWarnings("unchecked")
	public void execute() throws MojoExecutionException {
		getLog().info("Hello world:" + outputDirectory.getAbsolutePath());

		final Set<Artifact> dependencies = new LinkedHashSet<Artifact>();

		if (project != null && project.getArtifact() != null && project.getArtifact().getFile() != null) {
			dependencies.add(project.getArtifact());
		}

		if (project != null) {
			final Set<Artifact> projectArtifacts = project.getArtifacts();
			if (projectArtifacts != null) {
				dependencies.addAll(projectArtifacts);
			}
		}

		getLog().info("size:" + dependencies.size());

		for (final Artifact artifact : dependencies) {
			final String name = artifact.getFile().getName();
			getLog().info("name:" +  artifact.getFile().getAbsolutePath());
			if(name.lastIndexOf('.') == -1){
				continue;
			}
			final File file = artifact.getFile();
			if(!fileOperation.existFile(file, mergeDirectorys)){
				getLog().info("无需合并，不存在合并的配置文件");
				continue;
			}
			
			final File tempLocation = new File(workDirectory, name.substring(0, name.lastIndexOf('.')));
			boolean process = false;
			if (!tempLocation.exists()) {
				tempLocation.mkdirs();
				process = true;
			} else if (artifact.getFile().lastModified() > tempLocation.lastModified()) {
				process = true;
			}

			if (process) {
				try {
					fileOperation.unpack(file, tempLocation, archiverManager);
				} catch (final NoSuchArchiverException e) {
					getLog().info("Skip unpacking dependency file with unknown extension: " + file.getPath());
				} catch (final Exception e) {
					throw new MojoExecutionException("Error unpacking dependency file: " + file, e);
				}
			}
		}
		
		List<TransMergeFileBean> transMergeFileBeanList = null;
		if(mergeFileBeans != null && mergeFileBeans.length > 0){
			transMergeFileBeanList = new ArrayList<TransMergeFileBean>();
			for(MergeFileBean mergeFileBean : mergeFileBeans){
				String mergeFilePattern = mergeFileBean.getMergeFilePattern();
				String targetMergeName = mergeFileBean.getTargetMergeName();
				if(mergeFilePattern == null || mergeFilePattern.length() == 0 
						|| targetMergeName == null || targetMergeName.length() == 0){
					continue;
				}
				File targetMergeFile = new File(targetMergeName);
				Pattern mergFilePattern = Pattern.compile(mergeFilePattern);
				TransMergeFileBean transMergeFileBean = new TransMergeFileBean();
				transMergeFileBean.setMergFilePattern(mergFilePattern);
				transMergeFileBean.setTargetMergeFile(targetMergeFile);
				transMergeFileBeanList.add(transMergeFileBean);
			}
		}
		
		fileOperation.mergeWorkFolder(mergeDirectorys, workDirectory, outputDirectory, transMergeFileBeanList);
		
		try {
			fileOperation.mergeFolder(outputDirectory, transMergeFileBeanList);
		} catch (IOException e) {
			e.printStackTrace();
			getLog().error("合并outputDirectory文件失败:", e);
		}
		
		
	}
	
}
