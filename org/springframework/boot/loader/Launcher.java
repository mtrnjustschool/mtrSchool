package org.springframework.boot.loader;

import org.springframework.boot.loader.jar.*;
import java.util.*;
import java.io.*;
import org.springframework.boot.loader.archive.*;
import java.security.*;
import java.net.*;

public abstract class Launcher
{
    protected void launch(final String[] args) throws Exception {
        JarFile.registerUrlProtocolHandler();
        final ClassLoader classLoader = this.createClassLoader(this.getClassPathArchives());
        this.launch(args, this.getMainClass(), classLoader);
    }
    
    protected ClassLoader createClassLoader(final List<Archive> archives) throws Exception {
        final List<URL> urls = new ArrayList<URL>(archives.size());
        for (final Archive archive : archives) {
            urls.add(archive.getUrl());
        }
        return this.createClassLoader(urls.toArray(new URL[0]));
    }
    
    protected ClassLoader createClassLoader(final URL[] urls) throws Exception {
        return new LaunchedURLClassLoader(urls, this.getClass().getClassLoader());
    }
    
    protected void launch(final String[] args, final String mainClass, final ClassLoader classLoader) throws Exception {
        Thread.currentThread().setContextClassLoader(classLoader);
        this.createMainMethodRunner(mainClass, args, classLoader).run();
    }
    
    protected MainMethodRunner createMainMethodRunner(final String mainClass, final String[] args, final ClassLoader classLoader) {
        return new MainMethodRunner(mainClass, args);
    }
    
    protected abstract String getMainClass() throws Exception;
    
    protected abstract List<Archive> getClassPathArchives() throws Exception;
    
    protected final Archive createArchive() throws Exception {
        final ProtectionDomain protectionDomain = this.getClass().getProtectionDomain();
        final CodeSource codeSource = protectionDomain.getCodeSource();
        final URI location = (codeSource != null) ? codeSource.getLocation().toURI() : null;
        final String path = (location != null) ? location.getSchemeSpecificPart() : null;
        if (path == null) {
            throw new IllegalStateException("Unable to determine code source archive");
        }
        final File root = new File(path);
        if (!root.exists()) {
            throw new IllegalStateException("Unable to determine code source archive from " + root);
        }
        return root.isDirectory() ? new ExplodedArchive(root) : new JarFileArchive(root);
    }
}
