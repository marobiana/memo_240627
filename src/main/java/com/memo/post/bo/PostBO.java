package com.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.domain.Post;
import com.memo.post.mapper.PostMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j // slf4j logger
@Service
public class PostBO {
	//private Logger log = LoggerFactory.getLogger(PostBO.class);
	//private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private FileManagerService fileManager;
	
	// 페이징 정보 필드(limit)
	private static final int POST_MAX_SIZE = 3;
	
	public List<Post> getPostListByUserId(int userId, Integer prevId, Integer nextId) {
		// 게시글 번호: 10 9 8 | 7 6 5 | 4 3 2 | 1
		// 만약 4 3 2 페이지에 있을 때
		// 1) 다음(nextId가 있음): 2보다 작은 3개 desc 
		// 2) 이전(prevId가 있음): 4보다 큰 3개 asc => 5 6 7 => BO에서 리스트 reverse
		// 3) 페이징 없음(next, prevId 모두 없음): 최신순 3개 desc
		
		// xml에서 하나의 쿼리로 만들기 위해 변수를 정제해본다.
		Integer standardId = null; // 기준 id(prev or next)
		String direction = null; // 방향
		
		if (prevId != null) { // 2) 이전
			standardId = prevId;
			direction = "prev";
			
			// TODO
		} else if (nextId != null) { // 1) 다음
			standardId = nextId;
			direction = "next";
			// TODO
		}
		
		// 3) 페이징 없음
		return postMapper.selectPostListByUserId(userId, standardId, direction, POST_MAX_SIZE);
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
	
	// input: userLoginId(파일), postId, userId, subject, content, file
	// output: X
	public void updatePostByPostIdUserId(String loginId, int postId,
			int userId, String subject, String content, MultipartFile file) {
		
		// 기존글을 가져온다.(1. 이미지 교체시 기존 이미지 삭제를 위해, 2. 업데이트 대상 존재 확인)
		Post post = postMapper.selectPostByPostIdUserId(postId, userId);
		if (post == null) {
			log.info("[####글 수정] post is null. postId:{}, userId:{}", postId, userId);
			return;
		}
		
		log.info("[####글 수정 테스트] postId:{}, userId:{}", postId, userId);
		// 파일 존재 시 이미지 업로드
		String imagePath = null;
		if (file != null) {
			// 새이미지 업로드
			// 업로드가 성공하면 기존 이미지 존재하면 삭제
			imagePath = fileManager.uploadFile(file, loginId);
			
			// 업로드 성공 && 기존 이미지 존재 시 삭제
			if (imagePath != null && post.getImagePath() != null) {
				// 폴더, 이미지 제거(컴퓨터-서버에서)
				fileManager.deleteFile(post.getImagePath());
			}
		}
		
		// DB update
		postMapper.updatePostByPostId(postId, subject, content, imagePath);
	}
	
	// 글 1개 삭제
	// input: postId, userId
	// output: X
	public void deletePostByPostIdUserId(int postId, int userId) {
		// 기존글 가져오기(postId, userId) => 이미지 존재 시 삭제 위해서
		Post post = postMapper.selectPostByPostIdUserId(postId, userId);
		if (post == null) {
			log.info("[글 삭제] post is null. postId:{}, userId:{}", postId, userId);
			return;
		}
		
		// DB 행 삭제
		int rowCount = postMapper.deletePostByPostId(postId);
		
		// 기존글에 이미지가 있다면 폴더/파일 삭제
		if (rowCount > 0 && post.getImagePath() != null) {
			// DB삭제 완료 && 기존글 이미지 존재 => 이미지 삭제
			fileManager.deleteFile(post.getImagePath());
		}
	}
}





