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
	
	// input:postId, userId    output: Post or null
	public Post selectPostByPostIdUserId(
			@Param("postId") int postId,
			@Param("userId") int userId);
	
	// input:postId, subject, content, imagePath     
	// output:int or void
	public void updatePostByPostId(
			@Param("postId") int postId,
			@Param("subject") String subject,
			@Param("content") String content,
			@Param("imagePath") String imagePath);
	
	// input: postId
	// output: int(삭제된 행 개수)
	public int deletePostByPostId(int postId);
}






