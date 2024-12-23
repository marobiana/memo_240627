package com.memo.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.memo.post.bo.PostBO;
import com.memo.post.domain.Post;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@Controller
public class PostController {
	// 주석 추가 ++
	
	@Autowired
	private PostBO postBO;

	@GetMapping("/post-list-view")
	public String postListView(
			@RequestParam(value = "prevId", required = false) Integer prevIdParam,
			@RequestParam(value = "nextId", required = false) Integer nextIdParam,
			Model model, HttpSession session) {
		// 로그인 여부 확인(권한 검사)
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			// 로그인 페이지로 이동
			return "redirect:/user/sign-in-view";
		}
		
		// db select => 로그인 된 사람이 쓴 글
		List<Post> postList = postBO.getPostListByUserId(userId, prevIdParam, nextIdParam);
		int prevId = 0;
		int nextId = 0;
		
		if (postList.isEmpty() == false) { // postList가 비어있지 않을 때 페이징 정보 세팅
			nextId = postList.get(postList.size() - 1).getId(); // 마지막칸 id
			prevId = postList.get(0).getId(); // 첫번째칸 id
			
			// 이전이 없는가? => 그렇다면 0
			// 유저가 쓴 글들 중 제일 큰 숫자 하나가 prevId와 같을 때 없음
			if (postBO.isPrevLastPageByUserId(userId, prevId)) {
				prevId = 0;
			}
			
			// 다음이 없는가? => 그렇다면 0
			// 유저가 쓴 글들 중 제일 작은 숫자 하나가 nextId와 같을 때 없음
			if (postBO.isNextLastPageByUserId(userId, nextId)) {
				nextId = 0;
			}
		}
		
		// model 담기
		model.addAttribute("nextId", nextId);
		model.addAttribute("prevId", prevId);
		model.addAttribute("postList", postList);
		
		return "post/postList";
	}
	
	/**
	 * 글쓰기 화면
	 * @return
	 */
	@GetMapping("/post-create-view")
	public String postCreateView() {
		return "post/postCreate";
	}
	
	/**
	 * 글 상세 화면
	 * @param postId
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/post-detail-view")
	public String postDetailView(
			@RequestParam("postId") int postId,
			HttpSession session,
			Model model) {
		
		// db select - postId, userId
		int userId = (int)session.getAttribute("userId");
		Post post = postBO.getPostByPostIdUserId(postId, userId);
		
		// model 담기
		model.addAttribute("post", post);
		
		return "post/postDetail";
	}
}





