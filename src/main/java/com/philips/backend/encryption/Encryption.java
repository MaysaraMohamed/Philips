package com.philips.backend.encryption;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class to encrypt passwords into DB.
 * 
 * @author maysara.mohamed
 *
 */
public class Encryption {
	/**
	 * encrypt method take string and return encrypted string
	 * 
	 * @param password
	 * @return
	 */

	public static String encrypt(String password) {
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(password.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			String hashtext = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32 chars.
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Comparing method to compare original password with encrypted password.
	 * 
	 * @param encryptedPassword
	 * @param originalPassword
	 * @return
	 */
	public static boolean comparePassword(String encryptedPassword, String originalPassword) {
		String originalEncrypted = encrypt(originalPassword);
		if (originalEncrypted.equals(encryptedPassword)) {
			return true;
		} else {
			return false;
		}
	}
}
