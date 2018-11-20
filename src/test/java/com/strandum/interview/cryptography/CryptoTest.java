package com.strandum.interview.cryptography;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Test;

public class CryptoTest {
	
	private final String privateKeyPath = "strandum.private.asc";
	private final String publicKeyPath = "strandum.pub.asc";
	private final String identity = "strandum";
	private final String passwd = "strandum";
	private final int    keysize = 2048;
	private final String encryptIn = "strandum.txt";
	private final String encryptOut = "strandum.enc";
	private boolean isArmor = true;
	
	@Before
	public void createFile() throws FileNotFoundException, IOException {
		createTextFile();
	}

	private void createTextFile() throws IOException, FileNotFoundException {
		try(FileOutputStream fos = new FileOutputStream(encryptIn)){
			fos.write(new String("Testing the signed file at Strandum!!!").getBytes(StandardCharsets.UTF_8));
		}
	}
	
	@Test
	public void pgpCryptoTest() throws Exception{
		
		PGPCrypt crypt = new PGPCrypt();
		generateKeyPar(crypt);
		encrypt(crypt);
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