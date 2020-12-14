package org.springframework.boot.loader;

import org.springframework.boot.loader.jar.*;
import java.util.*;
import java.io.*;
import java.security.*;
import java.net.*;
import java.util.jar.*;

public class LaunchedURLClassLoader extends URLClassLoader
{
    public LaunchedURLClassLoader(final URL[] urls, final ClassLoader parent) {
        super(urls, parent);
    }
    
    @Override
    public URL findResource(final String name) {
        Handler.setUseFastConnectionExceptions(true);
        try {
            return super.findResource(name);
        }
        finally {
            Handler.setUseFastConnectionExceptions(false);
        }
    }
    
    @Override
    public Enumeration<URL> findResources(final String name) throws IOException {
        Handler.setUseFastConnectionExceptions(true);
        try {
            return new UseFastConnectionExceptionsEnumeration(super.findResources(name));
        }
        finally {
            Handler.setUseFastConnectionExceptions(false);
        }
    }
    
    @Override
    protected Class<?> loadClass(final String name, final boolean resolve) throws ClassNotFoundException {
        Handler.setUseFastConnectionExceptions(true);
        try {
            try {
                this.definePackageIfNecessary(name);
            }
            catch (IllegalArgumentException ex) {
                if (this.getPackage(name) == null) {
                    throw new AssertionError((Object)("Package " + name + " has already been defined but it could not be found"));
                }
            }
            return super.loadClass(name, resolve);
        }
        finally {
            Handler.setUseFastConnectionExceptions(false);
        }
    }
    
    private void definePackageIfNecessary(final String className) {
        final int lastDot = className.lastIndexOf(46);
        if (lastDot >= 0) {
            final String packageName = className.substring(0, lastDot);
            if (this.getPackage(packageName) == null) {
                try {
                    this.definePackage(className, packageName);
                }
                catch (IllegalArgumentException ex) {
                    if (this.getPackage(packageName) == null) {
                        throw new AssertionError((Object)("Package " + packageName + " has already been defined but it could not be found"));
                    }
                }
            }
        }
    }
    
    private void definePackage(final String className, final String packageName) {
        try {
            final String packageEntryName;
            final String classEntryName;
            final URL[] array;
            int length;
            int i = 0;
            URL url;
            URLConnection connection;
            JarFile jarFile;
            AccessController.doPrivileged(() -> {
                packageEntryName = packageName.replace('.', '/') + "/";
                classEntryName = className.replace('.', '/') + ".class";
                this.getURLs();
                for (length = array.length; i < length; ++i) {
                    url = array[i];
                    try {
                        connection = url.openConnection();
                        if (connection instanceof JarURLConnection) {
                            jarFile = ((JarURLConnection)connection).getJarFile();
                            if (jarFile.getEntry(classEntryName) != null && jarFile.getEntry(packageEntryName) != null && jarFile.getManifest() != null) {
                                this.definePackage(packageName, jarFile.getManifest(), url);
                                return null;
                            }
                        }
                    }
                    catch (IOException ex) {}
                }
                return null;
            }, AccessController.getContext());
        }
        catch (PrivilegedActionException ex2) {}
    }
    
    public void clearCache() {
        for (final URL url : this.getURLs()) {
            try {
                final URLConnection connection = url.openConnection();
                if (connection instanceof JarURLConnection) {
                    this.clearCache(connection);
                }
            }
            catch (IOException ex) {}
        }
    }
    
    private void clearCache(final URLConnection connection) throws IOException {
        final Object jarFile = ((JarURLConnection)connection).getJarFile();
        if (jarFile instanceof org.springframework.boot.loader.jar.JarFile) {
            ((org.springframework.boot.loader.jar.JarFile)jarFile).clearCache();
        }
    }
    
    static {
        ClassLoader.registerAsParallelCapable();
    }
    
    private static class UseFastConnectionExceptionsEnumeration implements Enumeration<URL>
    {
        private final Enumeration<URL> delegate;
        
        UseFastConnectionExceptionsEnumeration(final Enumeration<URL> delegate) {
            this.delegate = delegate;
        }
        
        @Override
        public boolean hasMoreElements() {
            Handler.setUseFastConnectionExceptions(true);
            try {
                return this.delegate.hasMoreElements();
            }
            finally {
                Handler.setUseFastConnectionExceptions(false);
            }
        }
        
        @Override
        public URL nextElement() {
            Handler.setUseFastConnectionExceptions(true);
            try {
                return this.delegate.nextElement();
            }
            finally {
                Handler.setUseFastConnectionExceptions(false);
            }
        }
    }
}
