package utils;



import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

	
	public static String encode(String oldStr){
		try {
			MessageDigest md5 = MessageDigest.getInstance("md5");
			byte []afterStr = md5.digest(oldStr.getBytes());
			
			BigInteger bigInt = new BigInteger(1, afterStr);
			return bigInt.toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	

}
