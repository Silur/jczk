package applet;
import java.util.Arrays;
import java.util.Random;
import applet.RSIS;
import applet.RLWESecretKey;

public class Prover {
    private RLWESecretKey sk;
    private byte[] u;
    private short[] rho;
    private Random rand;
    private byte[] w;
    public Prover(RLWESecretKey sk) {
        this.sk = sk;
        rand = new Random();
        u = new byte[16];
        rho = new short[2432];
        reset();
    }

    public void reset() {
        rand.nextBytes(u);
        rho = RSIS.sampleShort(2432);
    }

    public short[] commit() throws Exception {
        byte[] rho_bytes = RLWEUtil.bitsToBytes(rho);
        System.out.printf("prover u: %s\n", Arrays.toString(u));
        System.out.printf("prover rho: %s\n", Arrays.toString(rho_bytes));
        byte[] preimage = Arrays.copyOf(u, 320);
        System.arraycopy(rho_bytes, 0, preimage, u.length, rho_bytes.length);
        short[] ret = RSIS.hash(preimage);
        return ret;
    }

    public byte[] prove(RLWECiphertext challenge) {
        byte[] ret = new byte[u.length];
        try {
            w = sk.decrypt(challenge);
            for(int i=0; i<u.length; i++) {
                ret[i] = (byte)(u[i]^w[i]);
            }
            return ret;
        } catch (Exception e) {
            System.out.printf("EXCEPTION %s\n", e.toString());
            return u;
        }
    }

    public byte[] prove2(byte[] z) {
        if(Arrays.equals(w,z)) {
            return RLWEUtil.bitsToBytes(rho);
        }
        return null;
    }
}
