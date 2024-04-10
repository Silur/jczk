package applet;

import java.util.Arrays;

import javacard.framework.*;
import javacard.security.RandomData;

public class RLWECiphertext {
    public final short[] c1;
    public final short[] c2;

    public RLWECiphertext(short[] c1, short[] c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public String toString() {
        return "RLWECiphertext [c1=" + Arrays.toString(c1) + ", c2=" + Arrays.toString(c2) + "]";
    }


}
