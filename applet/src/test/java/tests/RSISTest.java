package tests;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import applet.RSIS;
import java.util.Arrays;
import java.util.Random;

public class RSISTest {
    private Random rand;

    public RSISTest () {
        rand = new Random();
    }

    @Test
    public void rsis_hash() throws Exception {
        byte[] m = new byte[RSIS.M/8];
        rand.nextBytes(m);
        short[] h = RSIS.hash(m);
        System.out.printf("hash value (%d): %s\n", h.length, Arrays.toString(h));
    }
}
