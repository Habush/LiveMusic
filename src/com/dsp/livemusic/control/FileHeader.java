package com.dsp.livemusic;

import org.jgroups.Global;
import org.jgroups.Header;
import org.jgroups.util.Util;

import java.io.DataInput;
import java.io.DataOutput;

/**
 * Created by Natnael Zeleke on 2/3/2016.
 */
public class FileHeader extends Header {
    protected String filename;
    protected boolean eof;
    protected int frame;

    public FileHeader() {
    } // for de-serialization

    public FileHeader(String filename, boolean eof, int fr) {
        this.filename = filename;
        this.eof = eof;
        this.frame = fr;
    }

    public int size() {
        return Util.size(filename) + Global.BYTE_SIZE;
    }

    public void writeTo(DataOutput out) throws Exception {
        Util.writeObject(filename, out);
        out.writeBoolean(eof);
        out.writeInt(frame);
    }

    public void readFrom(DataInput in) throws Exception {
        filename = (String) Util.readObject(in);
        eof = in.readBoolean();
        frame = in.readInt();
    }
}