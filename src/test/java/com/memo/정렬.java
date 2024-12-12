package com.memo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.memo.정렬.Data;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class 정렬 {

	@ToString
	@AllArgsConstructor
	class Data {
		int count;
	}
	
	@Test
	void 테스트() {
		Data d1 = new Data(3);
		Data d2 = new Data(5);
		Data d3 = new Data(2);
		List<Data> list = new ArrayList<>(List.of(d1, d2, d3));
		log.info("as-is: {}", list.toString());
		
		// 정렬
		//정렬_고전(list);
		정렬_람다(list);
		log.info("to-be: {}", list.toString());
	}
	
	void 정렬_람다(List<Data> list) {
		Collections.sort(list, (d1, d2) -> Integer.compare(d1.count, d2.count));
	}
	
	
	// 고전
	void 정렬_고전(List<Data> list) {
		Collections.sort(list, new Comparator<>() {
			@Override
			public int compare(Data d1, Data d2) {
				// 1 0 -1
				return Integer.compare(d1.count, d2.count);
			}
		});
	}
}








