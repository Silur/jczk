package applet;
import java.util.Arrays;
import java.util.Random;
import applet.RSIS;
import applet.RLWEPublicKey;
public class Verifier {
    private byte[] challenge;
    private Random rand;
    private short[] prover_c;
    enum State {
        INIT,
        CHALLENGE,
        INVERT,
        VERIFY
    }
    RLWEPublicKey pk;
    public Verifier(RLWEPublicKey pk) {
        rand = new Random();
        this.pk = pk;
    }

    public RLWECiphertext challenge(short[] prover_c) {
        this.prover_c = prover_c;
        challenge = new byte[32];
        rand.nextBytes(challenge);
        return pk.encrypt(challenge);
    }

    public byte[] reveal() {
        return challenge;
    }

    public boolean verify(byte[] proof1, byte[] proof2) throws Exception {
        byte[] v = new byte[16];
        for(int i=0; i<v.length; i++) {
            v[i] = (byte)(challenge[i] ^ proof1[i]);
        }

        byte[] preimage = Arrays.copyOf(v, 320);
        System.out.printf("recovered u: %s\n", Arrays.toString(v));
        System.out.printf("recovered rho: %s\n", Arrays.toString(proof2));
        System.arraycopy(proof2, 0, preimage, v.length, proof1.length);
        short[] ret = RSIS.hash(preimage);
        System.out.printf("verify c1: %s\n", Arrays.toString(prover_c));
        System.out.printf("verify c2: %s\n", Arrays.toString(ret));
        return Arrays.equals(ret, prover_c);
    }
}
