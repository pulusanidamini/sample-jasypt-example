package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.vault.core.VaultKeyValueOperationsSupport.KeyValueBackend;
import org.springframework.vault.core.VaultSysOperations;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.VaultTransitOperations;
import org.springframework.vault.support.VaultMount;
import org.springframework.vault.support.VaultResponse;


@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private VaultTemplate vaultTemplate;
	
	public static void main(String[] args) {
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(DemoApplication.class, args);
		MyTest myTest = configurableApplicationContext.getBean(MyTest.class);
		myTest.testPrint();
	}

	@Override
	public void run(String... strings) throws Exception {

		// You usually would not print a secret to stdout
		VaultResponse response = vaultTemplate.opsForKeyValue("secret", KeyValueBackend.KV_2).get("sample-jasypt");
		System.out.println("-------------------------------");
		System.out.println(response.getData().get("jasypt.encryptor.password"));
		System.out.println("-------------------------------");
		System.out.println();
		
		// Let's encrypt some data using the Transit backend.
		VaultTransitOperations transitOperations = vaultTemplate.opsForTransit();

		// We need to setup transit first (assuming you didn't set up it yet).
		VaultSysOperations sysOperations = vaultTemplate.opsForSys();

		if (!sysOperations.getMounts().containsKey("transit/")) {

			sysOperations.mount("transit", VaultMount.create("transit"));

//			transitOperations.createKey("foo-key");
		}

		// Encrypt a plain-text value
		String ciphertext = transitOperations.encrypt("damini", "damini");

		System.out.println("Encrypted value");
		System.out.println("-------------------------------");
		System.out.println(ciphertext);
		System.out.println("-------------------------------");
		System.out.println();

		// Decrypt

		String plaintext = transitOperations.decrypt("damini", ciphertext);

		System.out.println("Decrypted value");
		System.out.println("-------------------------------");
		System.out.println(plaintext);
		System.out.println("-------------------------------");
		System.out.println();
	}
}