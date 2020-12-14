package org.springframework.boot.loader.archive;

import java.net.*;
import java.util.jar.*;
import java.io.*;
import java.util.*;

public interface Archive extends Iterable<Entry>
{
    URL getUrl() throws MalformedURLException;
    
    Manifest getManifest() throws IOException;
    
    List<Archive> getNestedArchives(final EntryFilter filter) throws IOException;
    
    public interface EntryFilter
    {
        boolean matches(final Entry entry);
    }
    
    public interface Entry
    {
        boolean isDirectory();
        
        String getName();
    }
}
