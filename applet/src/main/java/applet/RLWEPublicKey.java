package applet;
import java.util.Arrays;

public class RLWEPublicKey {
    private final short[] p;
    public RLWEPublicKey(short[] p) {
        this.p = p;
    }

    public RLWECiphertext encrypt(byte[] m) {
        short[] e1 = RLWEUtil.sampleShort(NTT.RLWE_N);
        short[] e3 = RLWEUtil.sampleShort(NTT.RLWE_N);
        short[] e2 = RLWEUtil.sampleShort(NTT.RLWE_N);
        short[] me = RLWEUtil.encode(m);
        //System.out.printf("encoded message %s\n", Arrays.toString(me));
        assert me.length == NTT.RLWE_N;

        short[] c1 = new short[NTT.RLWE_N];
        short[] c2 = new short[NTT.RLWE_N];

        short[] e1_fft = e1.clone();
        short[] p_fft = p.clone();

        NTT.rlwe_forward(e1_fft);
        NTT.rlwe_forward(p_fft);

        for (int i = 0; i < NTT.RLWE_N; i++) {
            c1[i] = NTT.mul_mod(RLWEUtil.A[i], e1_fft[i], NTT.RLWE_Q);
            c2[i] = NTT.mul_mod(p_fft[i], e1_fft[i], NTT.RLWE_Q);
        }

        NTT.rlwe_backward(c1);
        NTT.rlwe_backward(c2);
        //System.out.printf("c1 before %s\n", Arrays.toString(c1));
        for (int i = 0; i < NTT.RLWE_N; i++) {
            c1[i] = NTT.add_mod(c1[i], e2[i], NTT.RLWE_Q);
            c2[i] = NTT.add_mod(c2[i], e3[i], NTT.RLWE_Q);
            c2[i] = NTT.add_mod(c2[i], me[i], NTT.RLWE_Q);
        }

        return new RLWECiphertext(c1, c2);
    }

}
