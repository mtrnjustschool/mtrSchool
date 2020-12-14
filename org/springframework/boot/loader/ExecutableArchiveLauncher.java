package org.springframework.boot.loader;

import org.springframework.boot.loader.archive.*;
import java.util.jar.*;
import java.util.*;

public abstract class ExecutableArchiveLauncher extends Launcher
{
    private final Archive archive;
    
    public ExecutableArchiveLauncher() {
        try {
            this.archive = this.createArchive();
        }
        catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }
    
    protected ExecutableArchiveLauncher(final Archive archive) {
        this.archive = archive;
    }
    
    protected final Archive getArchive() {
        return this.archive;
    }
    
    @Override
    protected String getMainClass() throws Exception {
        final Manifest manifest = this.archive.getManifest();
        String mainClass = null;
        if (manifest != null) {
            mainClass = manifest.getMainAttributes().getValue("Start-Class");
        }
        if (mainClass == null) {
            throw new IllegalStateException("No 'Start-Class' manifest entry specified in " + this);
        }
        return mainClass;
    }
    
    @Override
    protected List<Archive> getClassPathArchives() throws Exception {
        final List<Archive> archives = new ArrayList<Archive>(this.archive.getNestedArchives(this::isNestedArchive));
        this.postProcessClassPathArchives(archives);
        return archives;
    }
    
    protected abstract boolean isNestedArchive(final Archive.Entry entry);
    
    protected void postProcessClassPathArchives(final List<Archive> archives) throws Exception {
    }
}
