import com.dsp.livemusic.control.MFileHandler;
import com.dsp.livemusic.model.Metadata;
import org.junit.Assert;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Xabush Semrie on 2/7/2016.
 */
public class MFileHandlerTest {

    MFileHandler mFileHandler;

    @Before
    public void setUp() throws Exception {
        mFileHandler = new MFileHandler("res/test.mp3");
        mFileHandler.handleMetadata();
    }


    @Test
    public void handleMetadataTest() throws Exception {

        Metadata mdata = mFileHandler.getMetadata();
        System.out.println("Title: " + mdata.getTitle());
        System.out.println("Artist: " + mdata.getArtist());
        System.out.println("Album: " + mdata.getAlbum());
        System.out.println("Year: " + mdata.getYear());
        System.out.println("Bitrate: " + mdata.getBitRate());
        System.out.println("SampleRate: " + mdata.getSampleRate() + "Hz");
        System.out.println("Length of this mp3 is: " + mdata.getTime() + "seconds");

    }

    @Test
    public void calculateFrameSizeTest() throws Exception
    {
        int expected = 144 * 320000 / 44100;
        int actual = mFileHandler.getFrameSize();
        Assert.assertThat("Frame expected", actual == expected,is(true));
        System.out.println("Frame size: " + actual + " bytes");
    }
}
