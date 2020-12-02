//package test;
//
//import java.math.BigInteger;
//import java.security.SecureRandom;
//
//import org.bouncycastle.asn1.sec.SECNamedCurves;
//import org.bouncycastle.asn1.x9.X9ECParameters;
//import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
//import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
//import org.bouncycastle.crypto.params.ECDomainParameters;
//import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
//import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
//import org.bouncycastle.crypto.params.ECPublicKeyParameters;
//import org.bouncycastle.math.ec.ECPoint;
//
//import com.google.common.io.BaseEncoding;
//
//
//public class ECKey {
//	public static final BaseEncoding HEX = BaseEncoding.base16().lowerCase();
//		public BigInteger priv;
//		public LazyECPoint pub;
//		protected long creationTimeSeconds;
//		
//	    // The parameters of the secp256k1 curve that Bitcoin uses.
//		
//		//同じ・・？
////	    private static final X9ECParameters CURVE_PARAMS = CustomNamedCurves.getByName("secp256k1");
//	    private static final X9ECParameters CURVE_PARAMS = SECNamedCurves.getByName("secp256k1");
//
//	    /** The parameters of the secp256k1 curve that Bitcoin uses. */
//		public static final ECDomainParameters CURVE = new ECDomainParameters(CURVE_PARAMS.getCurve(), CURVE_PARAMS.getG(), CURVE_PARAMS.getN(),
//                CURVE_PARAMS.getH());
//	
//	
//	    public ECKey(SecureRandom secureRandom) {
//	        ECKeyPairGenerator generator = new ECKeyPairGenerator();
//	        ECKeyGenerationParameters keygenParams = new ECKeyGenerationParameters(CURVE, secureRandom);
//	        generator.init(keygenParams);
//	        AsymmetricCipherKeyPair keypair = generator.generateKeyPair();
//	        ECPrivateKeyParameters privParams = (ECPrivateKeyParameters) keypair.getPrivate();
//	        ECPublicKeyParameters pubParams = (ECPublicKeyParameters) keypair.getPublic();
//	        this.priv = privParams.getD();
//	        this.pub = getPointWithCompression(pubParams.getQ(), true);
//	        this.creationTimeSeconds = System.currentTimeMillis();//現在時刻
//	    
//	}
//	    private static LazyECPoint getPointWithCompression(ECPoint point, boolean compressed) {
//	        return new LazyECPoint(point, compressed);
//	    }
//	    
//	    /**
//	     * Returns true if the given pubkey is in its compressed form.
//	     */
//	    public static boolean isPubKeyCompressed(byte[] encoded) {
//	        if (encoded.length == 33 && (encoded[0] == 0x02 || encoded[0] == 0x03))
//	            return true;
//	        else if (encoded.length == 65 && encoded[0] == 0x04)
//	            return false;
//	        else
//	            throw new IllegalArgumentException(HEX.encode(encoded));
//	    }
//}
