package tests;

import cz.muni.fi.crocs.rcard.client.CardType;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javacard.framework.*;
import javacard.security.RandomData;

/**
 * Example test class for the applet
 * Note: If simulator cannot be started try adding "-noverify" JVM parameter
 *
 * @author xsvenda, Dusan Klinec (ph4r05)
 */
public class AppletTest extends BaseTest {
    private RandomData random;
    public AppletTest() {
        random = RandomData.getInstance(RandomData.ALG_TRNG);
        // Change card type here if you want to use physical card
        setCardType(CardType.JCARDSIMLOCAL);
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUpMethod() throws Exception {
    }

    @AfterEach
    public void tearDownMethod() throws Exception {
    }

    // Example test
    @Test
    public void hello() throws Exception {
        //byte[] tmpBuffer = new byte[250];
        //random.nextBytes(tmpBuffer, (short)0, (short)250);
        //final CommandAPDU cmd = new CommandAPDU(0x00, 0x90, 0, 0, tmpBuffer, (short)250);
        //System.out.println("b");
        //final ResponseAPDU responseAPDU = connect().transmit(cmd);
        //Assert.assertNotNull(responseAPDU);
        //Assert.assertEquals(0x9000, responseAPDU.getSW());
        //Assert.assertNotNull(responseAPDU.getBytes());
        return;
    }
}
