package com.eagletsoft.post.core.data.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eagletsoft.post.core.data.PostTask;

public interface PostTaskRepo extends MongoRepository<PostTask, String> {
	PostTask saveUnique(PostTask task);
}
