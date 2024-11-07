package com.memo.post.bo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.domain.Post;
import com.memo.post.mapper.PostMapper;

@Service
public class PostBO {

	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private FileManagerService fileManager;
	
	public List<Post> getPostListByUserId(int userId) {
		return postMapper.selectPostListByUserId(userId);
	}
	
	// input: userId, userLoginId, subject, content, file
	// output: int(성공한 행 개수)
	public int addPost(int userId, 
			String userLoginId, String subject, 
			String content, MultipartFile file) {
		
		String imagePath = null;
		
		// file to imagePath
		// file이 있을 때만 업로드 => imagePath를 얻어냄
		if (file != null) {
			imagePath = fileManager.uploadFile(file, userLoginId);
		}
		
		return postMapper.insertPost(userId, subject, content, imagePath);
	}
	
	public Post getPostByPostIdUserId(int postId, int userId) {
		return postMapper.selectPostByPostIdUserId(postId, userId);
	}
}





