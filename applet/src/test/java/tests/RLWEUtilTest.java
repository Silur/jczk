package tests;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import applet.NTT;
import applet.RLWEUtil;

import java.util.Arrays;
import java.util.Random;
public class RLWEUtilTest {
    private Random rand;

    public RLWEUtilTest () {
        rand = new Random();
    }

    @Test
    public void encode_decode_invariant() throws Exception {
        byte[] m = new byte[32];
        rand.nextBytes(m);
        short[] encoded = RLWEUtil.encode(m);
        assertEquals(encoded.length, 256);
        byte[] decoded = RLWEUtil.decode(encoded);
        assertArrayEquals(m, decoded);
    }
}
