package com.memo.post.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {
	
	// input: X    output: List<Map<String, Object>>
	public List<Map<String, Object>> selectPostList();
}
