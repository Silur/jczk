package applet;

import java.util.Arrays;
import java.util.Random;

public class RLWEUtil {
    public final static short[] A = {5315, 1686, 7406, 328, 429, 7644, 7589, 3308, 3894, 3384, 2305, 481, 4777, 3607, 55, 4813, 4279, 7333, 6493, 2105, 7372, 1038, 3508, 6884, 4100, 5842, 3210, 3422, 5741, 448, 2675, 2151, 2362, 354, 22, 5360, 1750, 4084, 5284, 5656, 675, 2592, 2281, 1984, 6842, 4175, 6531, 1265, 5057, 242, 1156, 923, 6669, 1898, 1563, 7621, 4386, 7399, 1957, 1906, 5022, 888, 5137, 3188, 2353, 4157, 6828, 7567, 530, 1443, 3521, 4582, 4957, 7404, 5949, 3371, 1051, 6774, 1970, 4968, 7538, 123, 2208, 3584, 6872, 1558, 245, 6214, 7488, 4721, 1893, 6155, 7385, 6145, 1506, 2121, 6885, 5894, 3635, 43, 5292, 5123, 6228, 5589, 6031, 5380, 3239, 283, 4124, 4858, 1532, 1701, 5178, 4686, 4979, 5321, 171, 2853, 3661, 3369, 4382, 3755, 417, 430, 5143, 5570, 3510, 6209, 4323, 4647, 5149, 2317, 692, 4655, 1833, 6571, 3528, 5955, 6163, 587, 5268, 4819, 1055, 2141, 6215, 7442, 598, 2276, 5082, 6528, 5253, 720, 6674, 2944, 2065, 2923, 3321, 4929, 2799, 6129, 2228, 3350, 6367, 6549, 5324, 5323, 1977, 5199, 643, 7414, 4057, 1058, 1528, 568, 3802, 715, 649, 6837, 7021, 6756, 5526, 1607, 1420, 3760, 5750, 2091, 2231, 6570, 5859, 6634, 2153, 2189, 2793, 920, 798, 6774, 1881, 7107, 1534, 5355, 3632, 3780, 6756, 2915, 6082, 3673, 2042, 2253, 4312, 6555, 3915, 4973, 1564, 919, 1565, 5017, 437, 2017, 1073, 7370, 2880, 1567, 793, 517, 5902, 6205, 6019, 467, 1868, 6749, 3896, 403, 4393, 649, 4461, 1872, 5462, 1447, 3953, 2262, 4723, 6448, 5516, 5199, 7192, 1041, 6182, 1784, 979, 4876, 5969, 1010, 1877, 1935, 7230, 4837};

    // TODO implement a true discrete gaussian sampler.
    // While there're no known attacks against a rounded gaussian
    // the formal security proofs to gapSVP only stand with the assumption
    // of a discrete sampler.
    public static short[] sampleShort(int len) {
        Random rand = new Random(); // FIXME
        short[] ret = new short[len];
        short[] trivial = new short[len];
        while(!Arrays.equals(ret, trivial)) {
            for (int i = 0; i < len; i++) {
                ret[i] = (short)(Math.round(rand.nextGaussian(0, 11.31))); // FIXME
            }
        }
        //System.out.printf("%s\n", Arrays.toString(ret));
        return ret;
    }


    public static short[] encode(byte[] m) {
        short n = (short)Math.floorDiv(NTT.RLWE_Q, 2);
        short[] encoded = new short[m.length*8];
        for (int i=0; i<m.length; i++) {
            for (int j=0; j<8; j++) {
                boolean bit = (m[i]&(1<<j))!=0;
                encoded[i*8+j] = bit ? n : 0;
            }
        }
        return encoded;
    }


    public static byte[] decode(short[] m) {
        short u = (short)Math.floorDiv(NTT.RLWE_Q, (short)4);
        short l = (short)(-u);
        byte[] decoded = new byte[Math.floorDiv(m.length, 8)];
        for(int i=0; i<Math.floorDiv(m.length, 8); i++) {
            for(int j=0; j<8; j++) {
                boolean bit = !(m[i*8+j] >= l && m[i*8+j] < u);
                decoded[i] |= bit ? 1<<j : 0;
            }
        }

        return decoded;
    }


    public static short[] bytesToBits(byte[] x) {
        short[] ret = new short[x.length*8];
        for (int i=0; i<x.length; i++) {
            for (int j=0; j<8; j++) {
                boolean bit = (x[i]&(1<<j))!=0;
                ret[i*8+j] = bit ? (short)1 : (short)0;
            }
        }
        return ret;
    }

    public static byte[] bitsToBytes(short[] x) {
        byte[] ret = new byte[x.length/8];
        for(int i=0; i<Math.floorDiv(x.length, 8); i++) {
            for(int j=0; j<8; j++) {
                ret[i] |= x[i*8+j]== 1 ? 1<<j : 0;
            }
        }
        return ret;
    }
}
