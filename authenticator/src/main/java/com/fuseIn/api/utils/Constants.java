package com.fuseIn.api.utils;

import java.security.SecureRandom;
import java.util.Random;

public class Constants {

	public static final String REGISTER_API_MAPPING="/api/register";
	public static final String REGISTER_USER="/user";
	static final String URI ="http://localhost:8080/authenticator/api/verify_user";
	static final Random RANDOM = new SecureRandom();
	static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static final int ITERATIONS = 1000;
	static final int KEY_LENGTH = 256;
	static final String JWT_SECRET = "Fuse@PijWtT2@k";
	static final long TOKEN_EXPIRATION_TIME = 36_00_000;
}
