package com.dsp.livemusic.model;

import org.jgroups.util.Streamable;

import java.io.DataInput;
import java.io.DataOutput;

/**
 * Created by Xabush Semrie on 2/7/2016.
 */
public class Metadata implements Streamable{

    private String title;
    private String artist;
    private String album;
    private String year;
    private long time;
    private int bitRate;
    private int sampleRate;

    public Metadata(){//for deserilization
     }

    public Metadata(String title, String artist, String album, String year, long time, int bitRate, int sampleRate) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.year = year;
        this.time = time;
        this.bitRate = bitRate;
        this.sampleRate = sampleRate;
    }

    @Override
    public void writeTo(DataOutput dataOutput) throws Exception {
        dataOutput.writeUTF(title);
        dataOutput.writeUTF(artist);
        dataOutput.writeUTF(album);
        dataOutput.writeUTF(year);
        dataOutput.writeLong(time);
        dataOutput.writeInt(bitRate);
        dataOutput.writeInt(sampleRate);
    }

    @Override
    public void readFrom(DataInput dataInput) throws Exception {
        title = dataInput.readUTF();
        artist = dataInput.readUTF();
        album = dataInput.readUTF();
        year = dataInput.readUTF();
        time = dataInput.readLong();
        bitRate = dataInput.readInt();
        sampleRate = dataInput.readInt();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getBitRate() {
        return bitRate;
    }

    public void setBitRate(int bitRate) {
        this.bitRate = bitRate;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }
}
