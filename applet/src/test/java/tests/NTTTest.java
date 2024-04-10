package tests;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import applet.NTT;

import java.util.Arrays;
import java.util.Random;
public class NTTTest {
    private Random rand;

    public NTTTest () {
        rand = new Random();
    }

    @Test
    public void ntt_forward_backward_invariant() throws Exception {
        short[] tmpShortBuffer = new short[NTT.RLWE_N];
        for (int i = 0; i < NTT.RLWE_N; i++) {
            tmpShortBuffer[i] = (short)rand.nextInt(NTT.RLWE_Q);
            //tmpShortBuffer[i] = (short)0x10;
        }
        short[] checkBuffer = tmpShortBuffer.clone();
        NTT.rlwe_forward(tmpShortBuffer);
        NTT.rlwe_backward(tmpShortBuffer);

        assertArrayEquals(tmpShortBuffer, checkBuffer);
    }
}
