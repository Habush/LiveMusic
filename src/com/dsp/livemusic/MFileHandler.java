package com.dsp.livemusic;

import org.apache.commons.io.input.CountingInputStream;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.AudioFrame;
import org.apache.tika.parser.mp3.MP3Frame;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Xabush Semrie on 2/4/2016.
 * This class handles reading from an mp3 file at the server side.
 */
public class MFileHandler {

    private String fileName;
    private int currentFrame;
    private int totalFrame;
    private int frameSize;
    private long totalMs;
    private final int MAX_SIZE = 2300000;
    private boolean eof = false;
    private CountingInputStream input;

    //apache tika parser
    private AudioFrame audioFrame;

    public MFileHandler(String name) throws IOException, TikaException, SAXException
    {
        fileName = name;
        input = new CountingInputStream(new FileInputStream(fileName));
        calculateFrameSize();
    }

    public byte[] decode() throws IOException
    {
        byte[] buffer = new byte[frameSize];
        input.skip(input.getByteCount());
        int read = input.read(buffer, 0, buffer.length);
        if(read == -1) {
            setEof(true);
        }
        totalFrame += frameSize;
        currentFrame++;
        return buffer;
    }

    private void calculateFrameSize() throws TikaException, SAXException, IOException {

//        int bitRate, sampleRate;
        InputStream in = new FileInputStream(fileName);
        BodyContentHandler handler = new BodyContentHandler();
        Metadata  metadata = new Metadata();
        ParseContext context = new ParseContext();
        Mp3Parser parser = new Mp3Parser();
        parser.parse(in,handler,metadata,context);
//        totalMs = (long)Math.ceil(Float.valueOf("xmpDM:duration"));
        frameSize = 144 * (320000)/(44100);
        in.close();
    }

    public int getTotalFrame() {
        return totalFrame;
    }

    public int getFrameSize() {
        return frameSize;
    }

    public long getTotalMs() {
        return totalMs;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isEof() {
        return eof;
    }

    public void setEof(boolean eof) {
        this.eof = eof;
    }
}
