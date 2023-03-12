package com.eagletsoft.post.core.service;

import com.eagletsoft.post.core.data.Payload;
import com.eagletsoft.post.core.data.PostTask;

public interface PostTaskService {
	PostTask createTaskIfNotPresent(Payload payload);
	PostTask find(String id);
}
