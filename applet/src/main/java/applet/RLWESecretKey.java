package applet;

import java.util.Arrays;

public class RLWESecretKey {
    private final short[] r1, r2;

    public RLWESecretKey() {
        r1 = RLWEUtil.sampleShort(NTT.RLWE_N);
        r2 = RLWEUtil.sampleShort(NTT.RLWE_N);
    }

    public RLWEPublicKey toPublicKey() {

        final short[] p = new short[NTT.RLWE_N];
        short[] r2_fft = r2.clone();
        NTT.rlwe_forward(r2_fft);
        for(int i=0; i<NTT.RLWE_N; i++) {
            p[i] = NTT.mul_mod(RLWEUtil.A[i], r2_fft[i], NTT.RLWE_Q);
        }
        NTT.rlwe_backward(p);
        for(int i=0; i<NTT.RLWE_N; i++) {
            p[i] = NTT.sub_mod(r1[i], p[i], NTT.RLWE_Q);
        }

        return new RLWEPublicKey(p);
    }

    public byte[] decrypt(RLWECiphertext ct) {
        short[] pt = new short[NTT.RLWE_N];
        short[] r2_fft = r2.clone();
        short[] c1_fft = ct.c1.clone();
        NTT.rlwe_forward(r2_fft);
        NTT.rlwe_forward(c1_fft);
        for(int i=0; i<NTT.RLWE_N; i++) {
            pt[i] = NTT.mul_mod(c1_fft[i], r2_fft[i], NTT.RLWE_Q);
        }
        NTT.rlwe_backward(pt);
        for(int i=0; i<NTT.RLWE_N; i++) {
            pt[i] = NTT.add_mod(pt[i], ct.c2[i], NTT.RLWE_Q);
        }
        //System.out.printf("before decode %s\n", Arrays.toString(pt));
        return RLWEUtil.decode(pt);
    }
}
