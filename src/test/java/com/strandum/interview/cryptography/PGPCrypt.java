package com.strandum.interview.cryptography;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class PGPCrypt {

	private KeyBasedFileProcessor keyBasedFileProcessor;
	
	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	
	public PGPCrypt(){
		keyBasedFileProcessor = new KeyBasedFileProcessor();
	}

	public void generateKeyPar(String privateKeyPath, String publicKeyPath, String identity, String passwd, int keysize, boolean armor) throws Exception {
		RSAKeyPairGenerator kpg = new RSAKeyPairGenerator();
		kpg.generateKeyPar(privateKeyPath, publicKeyPath, identity, passwd, keysize, armor);
	}

	public void encryptFile(String encryptOut, String encryptIn, String publicKeyPath, boolean armor, boolean withIntegrityCheck) throws Exception {
		keyBasedFileProcessor.encryptFile(encryptOut, encryptIn, publicKeyPath, armor, withIntegrityCheck);
	}

	public void decryptFile(String fileEncrypted, String keyFileName, char[] passwd, String defaultFileName) throws Exception {
		keyBasedFileProcessor.decryptFile(fileEncrypted, keyFileName, passwd, defaultFileName);
	}

}
