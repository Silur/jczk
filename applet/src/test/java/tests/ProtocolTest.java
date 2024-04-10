package tests;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import applet.NTT;
import applet.Prover;
import applet.Verifier;
import applet.RLWESecretKey;
import applet.RLWEPublicKey;
import applet.RLWECiphertext;
import java.util.Arrays;
import java.util.Random;
public class ProtocolTest {
    private Random rand;

    public ProtocolTest () {
        rand = new Random();
    }

    @Test
    public void prove_and_verify() throws Exception {
        RLWESecretKey sk = new RLWESecretKey();
        RLWEPublicKey pk = sk.toPublicKey();
        Prover peggy = new Prover(sk);
        Verifier victor = new Verifier(pk);
        short[] commitment = peggy.commit();
        //System.out.printf("peggy commitment (%d): %s\n", commitment.length, Arrays.toString(commitment));
        RLWECiphertext challenge = victor.challenge(commitment);
        //System.out.printf("victor challenge %s\n", challenge.toString());
        byte[] proof1 = peggy.prove(challenge);
        //System.out.printf("peggy proof1 (%d): %s\n", proof1.length, Arrays.toString(proof1));
        byte[] victor_challenge = victor.reveal();
        //System.out.printf("victor revealed (%d): %s\n", victor_challenge.length, Arrays.toString(victor_challenge));
        byte[] proof2 = peggy.prove2(victor_challenge);
        //System.out.printf("peggy proof2 (%d): %s\n", proof2.length, Arrays.toString(proof2));
        boolean result = victor.verify(proof1, proof2);
        System.out.printf("Verification suceeded: %s\n", result);

    }
}
