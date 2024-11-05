package com.memo.post.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.memo.post.domain.Post;

@Mapper
public interface PostMapper {
	
	// input: X    output: List<Map<String, Object>>
	public List<Map<String, Object>> selectPostList();
	
	// input:int(userId) output: List<Post>
	public List<Post> selectPostListByUserId(int userId);
	
	// input:params    output:int or void
	public int insertPost(
			@Param("userId") int userId,
			@Param("subject") String subject,
			@Param("content") String content,
			@Param("imagePath") String imagePath);
}






