package com.dsp.livemusic.model;

import javafx.beans.property.*;
import javafx.scene.image.Image;
import javafx.util.Duration;
import org.jgroups.util.Streamable;

import java.io.DataInput;
import java.io.DataOutput;

/**
 * Created by Xabush on 5/9/2015 19:31.
 * Project: neXt Player
 */
public class SongModel{

//    private  final String DEFAULT_IMG_URL = SongModel.class.getResource("res/images/album_cover.png").toString();
    //private  final Image DEFAULT_ALBUM_COVER = new Image(DEFAULT_IMG_URL);

    private final StringProperty album = new SimpleStringProperty(this,"album", "Unknown Album");
    private final StringProperty artist = new SimpleStringProperty(this,"artist", "Unknown Artist");
    private final StringProperty title = new SimpleStringProperty(this, "title", "Track 01");
    private final StringProperty time = new SimpleStringProperty(this, "time");

    private final StringProperty year = new SimpleStringProperty(this, "year");
    private final StringProperty bitRate = new SimpleStringProperty(this, "bitrate");
    private final StringProperty sampleRate = new SimpleStringProperty(this, "sampleRate");
    private Duration duration;
    private Metadata metadata;

    public SongModel(Metadata metadata)
    {
        this.metadata = metadata;
        handleSongMetadata();
    }

    public String getAlbum() {
        return album.get();
    }

    public StringProperty albumProperty() {
        return album;
    }

    public void setAlbum(String album) {
        this.album.set(album);
    }

    public String getArtist() {
        return artist.get();
    }

    public StringProperty artistProperty() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist.set(artist);
    }

    public String fromDurationToString(Duration duration) {
        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60
                    - durationMinutes * 60;

            if (durationHours > 0) {
                return String.format("%d:%02d:%02d",
                        durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d", durationMinutes,
                        durationSeconds);
            }
        }

        return "";
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getYear() {
        return year.get();
    }

    public StringProperty yearProperty() {
        return year;
    }

    public void setYear(String year) {
        this.year.set(year);
    }

    public String getBitRate() {
        return bitRate.get();
    }

    public StringProperty bitRateProperty() {
        return bitRate;
    }

    public void setBitRate(int bitRate) {
        this.bitRate.set(String.valueOf(bitRate) + "kps");
    }

    public String getSampleRate() {
        return sampleRate.get();
    }

    public StringProperty sampleRateProperty() {
        return sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate.set(String.valueOf(sampleRate / 1000) + "kHz");
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    private void handleSongMetadata()
    {
        setTitle(metadata.getTitle());
        setAlbum(metadata.getAlbum());
        setArtist(metadata.getArtist());
        setYear(metadata.getYear());
        setBitRate(metadata.getBitRate());
        setSampleRate(metadata.getSampleRate());
        setTime(fromDurationToString(Duration.millis(metadata.getTime())));
    }
}
