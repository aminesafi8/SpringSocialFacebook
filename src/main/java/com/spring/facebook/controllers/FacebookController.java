package com.spring.facebook.controllers;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class FacebookController {

	private Facebook facebook;
	private ConnectionRepository connectionRepository;

	public FacebookController(Facebook facebook, ConnectionRepository connectionRepository) {
		super();
		this.facebook = facebook;
		this.connectionRepository = connectionRepository;
	}

	/*
	 * 
	 * http://localhost:8080/connect/facebook
	 * Change in applications.properties ( facebook credentials ) By Amine SAFI
	 */
	@GetMapping
	public String getAllFeeds(Model model) {
		if (connectionRepository.getPrimaryConnection(Facebook.class) == null) {
			return "redirect:/connect/facebook";
		}
		PagedList<Post> posts = facebook.feedOperations().getFeed();

		Connection<Facebook> connection = connectionRepository.findPrimaryConnection(Facebook.class);
		connection.createData().getImageUrl();
		model.addAttribute("posts", posts);
		model.addAttribute("profileName", posts.get(0).getFrom().getName());

		System.out.println(" ==> " + posts.get(0).getLink());

		return "profile";
	}

}
