import com.dsp.livemusic.control.PlayerHandler;
import com.dsp.livemusic.control.PlayerState;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * Created by Xabush Semrie on 2/7/2016.
 */
public class PlayerHandlerTest  {

    @Test
    public void testPlayerHander() throws Exception
    {
        FileInputStream input = new FileInputStream("src/com/dsp/livemusic/test.mp3");
        PlayerHandler plHandler = new PlayerHandler(input);

        //start playing
        plHandler.play();

        Assert.assertThat("Player is PLAYING",plHandler.getPlayerState() == PlayerState.PLAY , is(true));

        //after 5 secs, pause
        Thread.sleep(10000);
        boolean pause = plHandler.pause();
        Assert.assertThat("Player is PAUSED", pause, is(true));

        //after 5 secs, resume
        Thread.sleep(10000);
        boolean res = plHandler.resume();
        Assert.assertThat("Player is RESUMED", res, is(true));

        Thread.sleep(10000);
        //after 10 sec, stop
        plHandler.stop();
        plHandler.close();
    }

    @Test
    public void remoteMethodStatement() throws Exception
    {
        Method m = getClass().getMethod("print", null);
    }

    public void print()
    {
        System.out.println("Who dis?");
    }
}
