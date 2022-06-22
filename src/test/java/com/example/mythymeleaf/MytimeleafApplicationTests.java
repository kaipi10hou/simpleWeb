package com.example.mythymeleaf;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MytimeleafApplicationTests {

	@Test
	void contextLoads() {
	}


	@Test
	void jasypt() {
		String url = "jdbc:mariadb://localhost:3306/mydb";
		String username = "myadmin";
		String password = "12345";

		System.out.println(jasyptEncoding(url));
		System.out.println(jasyptEncoding(username));
		System.out.println(jasyptEncoding(password));
	}

	public String jasyptEncoding(String value) {

		String key = "openit";
		StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
		pbeEnc.setAlgorithm("PBEWITHMD5ANDDES");
		pbeEnc.setPassword(key);
		return pbeEnc.encrypt(value);
	}
}


