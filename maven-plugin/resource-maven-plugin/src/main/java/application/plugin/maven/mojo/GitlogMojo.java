package application.plugin.maven.mojo;

import java.io.File;
import java.io.IOException;

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
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import application.io.spring.common.utils.command.CommandFactory;

/**
 * Goal which hello a gitlog file.
 * 
 */
@Mojo(name = "gitlog", requiresDependencyResolution = ResolutionScope.COMPILE, inheritByDefault = false)
@Execute(phase = LifecyclePhase.PREPARE_PACKAGE)
public class GitlogMojo extends AbstractMojo {

	@Parameter(defaultValue = "${project.build.directory}", required = true)
	private File outputDirectory;
	
	@Parameter(defaultValue = "${project}", readonly = true, required = true)
	private MavenProject project;

	public void execute() throws MojoExecutionException {
		try {
			String artifactId = project.getArtifactId();
			CommandFactory.getCurrentCommand().getCmdExecResultStrList("git log>target/classes/gitlog-" + artifactId + ".log", project.getBasedir());
			getLog().info("git log package success");
		} catch (IOException e) {
			e.printStackTrace();
			throw new MojoExecutionException("git log excute fail");
		}
	}
	
}
