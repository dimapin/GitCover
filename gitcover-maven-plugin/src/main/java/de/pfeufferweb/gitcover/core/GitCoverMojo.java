package de.pfeufferweb.gitcover.core;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Goal which checks the coverage of new code.
 *
 * @goal gitcover
 * 
 * @phase verify
 *
 * @requiresProject true
 */
public class GitCoverMojo extends AbstractMojo
{

    /**
     * Exclude modified files.
     * @parameter expression="${gitcover.excludeModified}"
     */
    private boolean excludeModified;

    /**
     * Exclude added files.
     * @parameter expression="${gitcover.excludeAdded}"
     */
    private boolean excludeAdded;

    /**
     * File containing patterns of files to be ignored.
     * @paramter expression="${gitcover.ignoreFile}"
     */
    private String ignoreFile;

    /**
     * Use this to select between cobertura and jacoco as coverage tool
     * @parameter expression="${gitcover.coverageTool}"
     */
    private String coverageTool;

    public void execute()
        throws MojoExecutionException
    {
    }
}
