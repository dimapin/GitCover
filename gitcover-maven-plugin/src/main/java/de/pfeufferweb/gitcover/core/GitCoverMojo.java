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
     *
     */
    private String ignoreFile;

    /**
     *
     */
    private String coverageTool;

    public void execute()
        throws MojoExecutionException
    {
    }
}
