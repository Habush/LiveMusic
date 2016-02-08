package com.dsp.livemusic.control;

import com.dsp.livemusic.model.Metadata;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.IOException;

/**
 * Created by Xabush Semrie on 2/4/2016.
 * This class handles reading from an mp3 file at the server side.
 */
public class MFileHandler {

    private Mp3File mp3File;
    private Metadata metadata;
    private int frameSize;

    public MFileHandler(String name) throws InvalidDataException, IOException, UnsupportedTagException {
        mp3File = new Mp3File(name);
        metadata = new Metadata();
    }

    public void handleMetadata()
    {
        if (mp3File.hasId3v2Tag()){
            ID3v2 id3v2 = mp3File.getId3v2Tag();
            metadata.setTitle(id3v2.getTitle());
            metadata.setArtist(id3v2.getArtist());
            metadata.setAlbum(id3v2.getAlbum());
            metadata.setYear(id3v2.getYear());
        }
        else{
            metadata.setTitle("Track 01");
            metadata.setArtist("Unknown artist");
            metadata.setAlbum("Unknown album");
            metadata.setYear("");
        }
        metadata.setBitRate(mp3File.getBitrate());
        metadata.setSampleRate(mp3File.getSampleRate());
        metadata.setTime(mp3File.getLengthInMilliseconds());
    }

    private void calculateFrameSize()
    {

        frameSize = 144 * metadata.getBitRate() * 1000 / metadata.getSampleRate();
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public int getFrameSize() {
        calculateFrameSize();
        return frameSize;
    }
}
