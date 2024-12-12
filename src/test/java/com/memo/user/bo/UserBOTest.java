package com.memo.user.bo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class UserBOTest {
	
	@Autowired
	UserBO userBO;

	@Transactional // rollback
	@Test
	void 회원가입() {
		userBO.addUser("테스트22", "패스워드222", "테스트이름222", "테스트이메일222");
	}

}
