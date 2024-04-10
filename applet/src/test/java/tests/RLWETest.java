package tests;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import applet.NTT;
import applet.RLWECiphertext;
import applet.RLWESecretKey;
import applet.RLWEPublicKey;
import applet.RLWEUtil;

import java.util.Arrays;
import java.util.Random;
public class RLWETest {
    private Random rand;

    public RLWETest () {
        rand = new Random();
    }

    @Test
    public void encrypt_decrypt_invariant() throws Exception {
        byte[] m = new byte[32];
        rand.nextBytes(m);
        System.out.printf("pt: %s\n", Arrays.toString(m));

        RLWESecretKey sk = new RLWESecretKey();
        RLWEPublicKey pk = sk.toPublicKey();

        RLWECiphertext ct = pk.encrypt(m);

        byte[] decrypted = sk.decrypt(ct);
        System.out.printf("dt: %s\n", Arrays.toString(decrypted));
        assertArrayEquals(m, decrypted);
    }
}
