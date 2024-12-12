package com.memo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@SpringBootTest
class MemoApplicationTests {
	
	//@Test
	void 테스트() {
		String str1 = "";
		String str2 = null;
		assertEquals(true, ObjectUtils.isEmpty(str1));
		assertEquals(true, ObjectUtils.isEmpty(str2));
		
		List<Integer> list1 = new ArrayList<>();
		List<Integer> list2 = null;
		assertEquals(true, ObjectUtils.isEmpty(list1));
		assertEquals(true, ObjectUtils.isEmpty(list2));
	}

}




