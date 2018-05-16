package com.fuseIn.api.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Encoder {
	private static Logger logger = LogManager.getLogger();
	
	public Map<String, String> encodePassword(String password) throws NoSuchAlgorithmException {

		String salt = saltValueGenerator(15);

		String hashCodeGenerated = generateSecurePassword(password, salt);

		Map<String, String> credentialsHashMap = new HashMap<String, String>();
		credentialsHashMap.put("salt", salt);
		credentialsHashMap.put("password_hash", hashCodeGenerated);

		return credentialsHashMap;
	}

	public static String saltValueGenerator(int length) {
		StringBuilder returnValue = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			returnValue.append(Constants.ALPHABET.charAt(Constants.RANDOM.nextInt(Constants.ALPHABET.length())));
		}
		return new String(returnValue);
	}

	public static byte[] hash(char[] password, byte[] salt) {
		PBEKeySpec spec = new PBEKeySpec(password, salt, Constants.ITERATIONS, Constants.KEY_LENGTH);
		Arrays.fill(password, Character.MIN_VALUE);
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			return skf.generateSecret(spec).getEncoded();
		} catch (Exception e) {
			throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
		} finally {
			spec.clearPassword();
		}
	}

	public static String generateSecurePassword(String password, String salt) {
		String returnValue = null;
		byte[] securePassword = hash(password.toCharArray(), salt.getBytes());

		returnValue = Base64.getEncoder().encodeToString(securePassword);

		return returnValue;
	}

	public static boolean verifyPasswordAuthenticity(String providedPassword, String securedPassword, String salt) {
		boolean returnValue = false;

		String newSecurePassword = generateSecurePassword(providedPassword, salt);
		returnValue = newSecurePassword.equalsIgnoreCase(securedPassword);

		return returnValue;
	}

	public String generateJWTTokenforUserVerification(String identifier) {
		String jwtToken = Jwts.builder().setSubject(identifier)
				.setExpiration(new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, Constants.JWT_SECRET.getBytes()).compact();
		return jwtToken;
	}

	public String verifyJwtTokenForUser(String token) {
		
		String userSubject = "";
		try {
	  userSubject = Jwts.parser().setSigningKey(Constants.JWT_SECRET.getBytes())
	 .parseClaimsJws(token).getBody().getSubject();
		}
		catch(ExpiredJwtException exceptn) {
			logger.error(exceptn.getMessage());
		}
	  return userSubject;
	}
}
