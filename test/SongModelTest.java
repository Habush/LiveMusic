import com.dsp.livemusic.control.MFileHandler;
import com.dsp.livemusic.model.SongModel;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Xabush Semrie on 2/7/2016.
 * Test the song duration class
 */
public class SongModelTest {

    MFileHandler mFileHandler;
    Logger logger;

    @Before
    public void setUp() throws Exception
    {
        mFileHandler = new MFileHandler("res/test.mp3");
        mFileHandler.handleMetadata();
        logger = Logger.getLogger(SongModelTest.class);
    }

    @Test
    public void fromDurationTest() throws Exception
    {
        SongModel model = new SongModel(mFileHandler.getMetadata());

        logger.debug(String.format("Bitrate: %s\nSample rate: %s\nTime: %s", model.getBitRate(), model.getSampleRate(),model.getTime()));
    }
}
