package com.eagletsoft.post.core.data.repo.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.eagletsoft.post.core.data.PostTask;

@Repository
public class PostTaskRepoImpl {
	@Autowired
	private MongoTemplate mongoTemplate;
	public PostTask saveUnique(PostTask task) {
		if (StringUtils.isEmpty(task.getUniqueKey())) {
			mongoTemplate.save(task);
			return task;
		}
		else {
			Query q = new Query();
			Criteria criteria = Criteria.where("uniqueKey").is(task.getUniqueKey());
			q.addCriteria(criteria);
			PostTask ret = mongoTemplate.findOne(q, PostTask.class);
			if (null == ret) {
				mongoTemplate.save(task);
				return task;
			}
			else {
				return ret;
			}
		}
	}
}
