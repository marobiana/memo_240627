package com.memo.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memo.post.mapper.PostMapper;

@Controller
public class TestController {
	
	@Autowired
	PostMapper postMapper;

	@ResponseBody
	@GetMapping("/test1")
	public String test1() {
		return "<h2>스트링 테스트</h2>";
	}
	
	@ResponseBody
	@GetMapping("/test2")
	public Map<String, Object> test2() {
		Map<String, Object> map = new HashMap<>();
		map.put("aaa", 111);
		map.put("bb", 111);
		map.put("ccc", 22);
		return map;
	}
	
	@GetMapping("/test3")
	public String test3() {
		return "test/test";
	}
	
	@ResponseBody
	@GetMapping("/test4")
	public List<Map<String, Object>> test4() {
		return postMapper.selectPostList();
	}
}



