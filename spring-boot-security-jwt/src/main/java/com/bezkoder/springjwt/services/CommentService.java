package com.bezkoder.springjwt.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bezkoder.springjwt.models.Comment;
import com.bezkoder.springjwt.models.Post;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.CommentCreateRequest;
import com.bezkoder.springjwt.payload.request.CommentUpdateRequest;
import com.bezkoder.springjwt.repository.CommentRepository;

import jakarta.transaction.Transactional;

@Service
public class CommentService {

	private CommentRepository commentRepository;
	private UserService userService;
	private PostService postService;

	public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
		super();
		this.commentRepository = commentRepository;
		this.userService = userService;
		this.postService = postService;
	}

	@Transactional
	public List<Comment> getAllCommentsWithParam(Optional<Long> userId, Optional<Long> postId) {
		try {
			if (userId.isPresent() && postId.isPresent()) {
				return commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
			} else if (userId.isPresent()) {
				return commentRepository.findByUserId(userId.get());
			} else if (postId.isPresent()) {
				return commentRepository.findByPostId(postId.get());
			} else {
				return commentRepository.findAll();
			}
		} catch (Exception e) {
			// Hata durumunda loglama yapabilir veya isteğe bağlı olarak istisnayı tekrar
			// fırlatabilirsiniz.
			// Ancak, büyük nesne işlemleri ve otomatik taahhüt modu ile ilgili özel
			// durumları ele almak önemlidir.
			// Eğer bu noktada bir hata oluşursa, büyük nesne işlemleri ve otomatik taahhüt
			// modu üzerinde durmanız gerekebilir.
			throw new RuntimeException("Error retrieving comments", e);
		}
	}

	public Comment getOneCommentById(Long commentId) {
		return commentRepository.findById(commentId).orElse(null);
	}

	public Comment createOneComment(CommentCreateRequest request) {
		User user = userService.getOneUserById(request.getUserId());
		Post post = postService.getOnePostById(request.getPostId());
		if (user != null && post != null) {
			Comment commentToSave = new Comment();
			commentToSave.setId(request.getId());
			commentToSave.setPost(post);
			commentToSave.setUser(user);
			commentToSave.setText(request.getText());
			return commentRepository.save(commentToSave);
		} else
			return null;
	}

	public Comment updateOneCommentById(Long commentId, CommentUpdateRequest request) {
		Optional<Comment> comment = commentRepository.findById(commentId);
		if (comment.isPresent()) {
			Comment commentToUpdate = comment.get();
			commentToUpdate.setText(request.getText());
			return commentRepository.save(commentToUpdate);

		} else
			return null;
	}

	public void deleteOneCommentById(Long commentId) {
		commentRepository.deleteById(commentId);

	}

}
