package applet;

import java.nio.ShortBuffer;
import java.util.Arrays;

import javacard.framework.*;
import javacard.security.RandomData;
import javacardx.framework.nio.ByteBuffer;
import javacardx.framework.nio.ByteOrder;

public class MainApplet extends Applet implements MultiSelectable
{
	private static final short BUFFER_SIZE = 256;
	private byte[] tmpBuffer = JCSystem.makeTransientByteArray(BUFFER_SIZE, JCSystem.CLEAR_ON_DESELECT);
	private short[] tmpShortBuffer = JCSystem.makeTransientShortArray((short)(BUFFER_SIZE/2), JCSystem.CLEAR_ON_DESELECT);
	private RandomData random;

	public static void install(byte[] bArray, short bOffset, byte bLength) 
	{
		new MainApplet(bArray, bOffset, bLength);
	}
	
	public MainApplet(byte[] buffer, short offset, byte length)
	{
		random = RandomData.getInstance(RandomData.ALG_FAST);
		register();
	}

	public void process(APDU apdu)
	{
		// FIXME
		byte[] apduBuffer = apdu.getBuffer();
		
		byte cla = apduBuffer[ISO7816.OFFSET_CLA];
		byte ins = apduBuffer[ISO7816.OFFSET_INS];
		short lc = (short)apduBuffer[ISO7816.OFFSET_LC];
		short p1 = (short)apduBuffer[ISO7816.OFFSET_P1];
		short p2 = (short)apduBuffer[ISO7816.OFFSET_P2];
		short dataOffset = (short)apduBuffer[ISO7816.OFFSET_CDATA];
		Util.arrayCopyNonAtomic(apduBuffer, (short)dataOffset, tmpBuffer, (short)0, (short)(BUFFER_SIZE));
		//random.nextBytes(tmpBuffer, (short)0, BUFFER_SIZE);
		ShortBuffer sb = java.nio.ByteBuffer.wrap(tmpBuffer).order(java.nio.ByteOrder.LITTLE_ENDIAN).asShortBuffer();

		sb.get(tmpShortBuffer);
		short[] cloneBuffer = tmpShortBuffer.clone();
		NTT.rlwe_forward(tmpShortBuffer);
		NTT.rlwe_backward(tmpShortBuffer);
		java.nio.ByteBuffer bb = java.nio.ByteBuffer.allocate(BUFFER_SIZE*2);

		for(int i = 0; i < BUFFER_SIZE/2; i++) 			{
				bb.putShort(tmpShortBuffer[i]);
		}
		
		Util.arrayCopyNonAtomic(bb.array(), (short)0, apduBuffer, (short)0, BUFFER_SIZE);
		apdu.setOutgoingAndSend((short)0, BUFFER_SIZE);

	}

	public boolean select(boolean b) {
		return true;
	}

	public void deselect(boolean b) {

	}
}
