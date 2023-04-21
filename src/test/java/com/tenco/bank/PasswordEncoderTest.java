package com.tenco.bank;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {

	public static void main(String[] args) {

//	기능 확인

		String password = "p1234";

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);

		System.out.println("원래 비밀번호" + password);
		System.out.println("암호화된 비밀번호"+hashedPassword);
		
//		사용자 요청 값 : 1234
//		DB 기록되어 있는 값 : $2a$10$9scxpUFm7O9RHb.73ItxiObXqe0rP01f6v14IcLBIAXVviXNOLnmy
		
		boolean isMatched = passwordEncoder.matches(password, hashedPassword);
		System.out.println("비밀번호 일치 여부 : " + isMatched);
//		$2a$10$OasE5Jy/rN05T2o/b.Nil.ApTV.d8vddhDCETtVuIhcxhyW/6ZRKu
	}

}
