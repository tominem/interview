package com.strandum.interview.cryptography;

import org.junit.Test;

public class CryptoTest {
	
	private final String privateKeyPath = "strandum.private.asc";
	private final String publicKeyPath = "strandum.pub.asc";
	private final String identity = "strandum";
	private final String passwd = "strandum";
	private final int    keysize = 2048;
	private final String encryptIn = "book.pdf";
	private final String encryptOut = "English_Grammar_in_Use_4ed_SB_www.frenglish.ru.enc";
	private boolean isArmor = true;
	
	@Test
	public void encryptDecrypt() throws Exception{
		
		PGPCrypt crypt = new PGPCrypt();
//		generateKeyPar(crypt);
//		encrypt(crypt);
		decrypt(crypt);
		
	}

	private void decrypt(PGPCrypt crypt) throws Exception {
		crypt.decryptFile(encryptOut, privateKeyPath, passwd.toCharArray(), encryptIn);
	}
	
	private void encrypt(PGPCrypt crypt) throws Exception {
		crypt.encryptFile(
				encryptOut,
				encryptIn,
				publicKeyPath,
				isArmor,
				true);
	}


	private void generateKeyPar(PGPCrypt crypt) throws Exception {
		crypt.generateKeyPar(
				privateKeyPath,
				publicKeyPath,
		    	identity,
		    	passwd,
		    	keysize,
		    	true);
	}

}