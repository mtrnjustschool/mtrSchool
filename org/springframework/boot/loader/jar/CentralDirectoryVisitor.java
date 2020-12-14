package org.springframework.boot.loader.jar;

import org.springframework.boot.loader.data.*;

interface CentralDirectoryVisitor
{
    void visitStart(final CentralDirectoryEndRecord endRecord, final RandomAccessData centralDirectoryData);
    
    void visitFileHeader(final CentralDirectoryFileHeader fileHeader, final int dataOffset);
    
    void visitEnd();
}
