package org.springframework.boot.loader.jar;

import org.springframework.boot.loader.data.*;
import java.io.*;
import java.time.*;

final class CentralDirectoryFileHeader implements FileHeader
{
    private static final AsciiBytes SLASH;
    private static final byte[] NO_EXTRA;
    private static final AsciiBytes NO_COMMENT;
    private byte[] header;
    private int headerOffset;
    private AsciiBytes name;
    private byte[] extra;
    private AsciiBytes comment;
    private long localHeaderOffset;
    
    CentralDirectoryFileHeader() {
    }
    
    CentralDirectoryFileHeader(final byte[] header, final int headerOffset, final AsciiBytes name, final byte[] extra, final AsciiBytes comment, final long localHeaderOffset) {
        this.header = header;
        this.headerOffset = headerOffset;
        this.name = name;
        this.extra = extra;
        this.comment = comment;
        this.localHeaderOffset = localHeaderOffset;
    }
    
    void load(byte[] data, int dataOffset, final RandomAccessData variableData, final int variableOffset, final JarEntryFilter filter) throws IOException {
        this.header = data;
        this.headerOffset = dataOffset;
        final long nameLength = Bytes.littleEndianValue(data, dataOffset + 28, 2);
        final long extraLength = Bytes.littleEndianValue(data, dataOffset + 30, 2);
        final long commentLength = Bytes.littleEndianValue(data, dataOffset + 32, 2);
        this.localHeaderOffset = Bytes.littleEndianValue(data, dataOffset + 42, 4);
        dataOffset += 46;
        if (variableData != null) {
            data = variableData.read(variableOffset + 46, nameLength + extraLength + commentLength);
            dataOffset = 0;
        }
        this.name = new AsciiBytes(data, dataOffset, (int)nameLength);
        if (filter != null) {
            this.name = filter.apply(this.name);
        }
        this.extra = CentralDirectoryFileHeader.NO_EXTRA;
        this.comment = CentralDirectoryFileHeader.NO_COMMENT;
        if (extraLength > 0L) {
            this.extra = new byte[(int)extraLength];
            System.arraycopy(data, (int)(dataOffset + nameLength), this.extra, 0, this.extra.length);
        }
        if (commentLength > 0L) {
            this.comment = new AsciiBytes(data, (int)(dataOffset + nameLength + extraLength), (int)commentLength);
        }
    }
    
    public AsciiBytes getName() {
        return this.name;
    }
    
    @Override
    public boolean hasName(final CharSequence name, final char suffix) {
        return this.name.matches(name, suffix);
    }
    
    public boolean isDirectory() {
        return this.name.endsWith(CentralDirectoryFileHeader.SLASH);
    }
    
    @Override
    public int getMethod() {
        return (int)Bytes.littleEndianValue(this.header, this.headerOffset + 10, 2);
    }
    
    public long getTime() {
        final long datetime = Bytes.littleEndianValue(this.header, this.headerOffset + 12, 4);
        return this.decodeMsDosFormatDateTime(datetime);
    }
    
    private long decodeMsDosFormatDateTime(final long datetime) {
        final LocalDateTime localDateTime = LocalDateTime.of((int)((datetime >> 25 & 0x7FL) + 1980L), (int)(datetime >> 21 & 0xFL), (int)(datetime >> 16 & 0x1FL), (int)(datetime >> 11 & 0x1FL), (int)(datetime >> 5 & 0x3FL), (int)(datetime << 1 & 0x3EL));
        return localDateTime.toEpochSecond(ZoneId.systemDefault().getRules().getOffset(localDateTime)) * 1000L;
    }
    
    public long getCrc() {
        return Bytes.littleEndianValue(this.header, this.headerOffset + 16, 4);
    }
    
    @Override
    public long getCompressedSize() {
        return Bytes.littleEndianValue(this.header, this.headerOffset + 20, 4);
    }
    
    @Override
    public long getSize() {
        return Bytes.littleEndianValue(this.header, this.headerOffset + 24, 4);
    }
    
    public byte[] getExtra() {
        return this.extra;
    }
    
    public AsciiBytes getComment() {
        return this.comment;
    }
    
    @Override
    public long getLocalHeaderOffset() {
        return this.localHeaderOffset;
    }
    
    public CentralDirectoryFileHeader clone() {
        final byte[] header = new byte[46];
        System.arraycopy(this.header, this.headerOffset, header, 0, header.length);
        return new CentralDirectoryFileHeader(header, 0, this.name, header, this.comment, this.localHeaderOffset);
    }
    
    public static CentralDirectoryFileHeader fromRandomAccessData(final RandomAccessData data, final int offset, final JarEntryFilter filter) throws IOException {
        final CentralDirectoryFileHeader fileHeader = new CentralDirectoryFileHeader();
        final byte[] bytes = data.read(offset, 46L);
        fileHeader.load(bytes, 0, data, offset, filter);
        return fileHeader;
    }
    
    static {
        SLASH = new AsciiBytes("/");
        NO_EXTRA = new byte[0];
        NO_COMMENT = new AsciiBytes("");
    }
}
