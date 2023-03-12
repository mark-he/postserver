package com.eagletsoft.post.core.data.repo.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.eagletsoft.post.core.data.SendOut;
import com.mongodb.client.result.UpdateResult;

@Repository
public class SendOutRepoImpl {

	@Autowired
	private MongoTemplate mongoTemplate;

	public boolean saveUnique(SendOut rec) {
		Query q = new Query();
		Criteria criteria = Criteria.where("taskId").is(rec.getTaskId()).and("receiver").is(rec.getReceiver());
		q.addCriteria(criteria);
		SendOut x = mongoTemplate.findOne(q, SendOut.class);

		if (null == x) {
			mongoTemplate.save(rec);
			return true;
		}
		return false;
	}

	public SendOut findPending() {
		Date now = new Date();
		Query q = new Query();

		q.addCriteria(Criteria.where("state").is(SendOut.STATE.PENDING).and("scheduledTime").lte(now));
		Update u = new Update();
		u.set("state", SendOut.STATE.PROCESSING);
		u.set("processedTime", now);

		FindAndModifyOptions o = new FindAndModifyOptions().returnNew(false);
		SendOut ret = mongoTemplate.findAndModify(q, u, o, SendOut.class);
		return ret;
	}

	public long renewTimeoutForNormalTry() {
		Date now = new Date();
		Calendar timeoutCal = Calendar.getInstance();
		timeoutCal.add(Calendar.SECOND, -1 * SendOut.SETTING.TIMEOUT_SECS);

		Query q = new Query();
		q.addCriteria(Criteria.where("processedTime").lte(timeoutCal.getTime()).and("state")
				.in(SendOut.STATE.PROCESSING, SendOut.STATE.ERROR, SendOut.STATE.EXTERNAL_ERROR).and("retry")
				.lt(SendOut.SETTING.RETRY_MAX));

		Update u = new Update();
		u.set("state", SendOut.STATE.PENDING);
		u.set("scheduledTime", now);
		u.inc("retry", Integer.valueOf(1));

		UpdateResult wr = mongoTemplate.updateMulti(q, u, SendOut.class);
		return wr.getModifiedCount();
	}

	public long renewTimeoutForLastTry() {
		Date now = new Date();
		Calendar timeoutCal = Calendar.getInstance();
		timeoutCal.add(Calendar.SECOND, -1 * SendOut.SETTING.LAST_TRY_SECS);

		Query q = new Query();

		q.addCriteria(Criteria.where("processedTime").lte(timeoutCal.getTime()).and("state")
				.in(SendOut.STATE.PROCESSING, SendOut.STATE.ERROR, SendOut.STATE.EXTERNAL_ERROR).and("retry")
				.lt(SendOut.SETTING.RETRY_MAX));

		Update u = new Update();

		u.set("state", SendOut.STATE.PENDING);
		u.set("scheduledTime", now);
		u.inc("retry", Integer.valueOf(1));

		UpdateResult wr = mongoTemplate.updateMulti(q, u, SendOut.class);
		return wr.getModifiedCount();
	}

	public void updateResult(String id, String state, String description) {

		Criteria criteria = Criteria.where("id").is(id);
		if (SendOut.STATE.READ.equals(state)) {

		} else if (SendOut.STATE.SENT.equals(state)) {
			criteria.and("state").ne(SendOut.STATE.READ);
		} else {
			criteria.and("state").nin(SendOut.STATE.SENT, SendOut.STATE.READ, SendOut.STATE.FAILED);
		}

		Query q = new Query();
		q.addCriteria(criteria);

		Update u = new Update();
		u.set("state", state);

		SendOut.History history = new SendOut.History();
		history.setState(state);
		history.setDescription(description);
		history.setCreatedTime(new Date());

		u.push("histories", history);
		UpdateResult wr = mongoTemplate.updateMulti(q, u, SendOut.class);
	}

	public void updateResultByCondition(String channel,String sender, String receiver, String state, String description) {

		Criteria criteria = Criteria.where("channel").is(channel).and("receiver").is(receiver).and("sender").is(sender);


		if (SendOut.STATE.READ.equals(state)) {
			criteria.and("state").is(SendOut.STATE.SENT);
		} else if (SendOut.STATE.SENT.equals(state)) {
			criteria.and("state").ne(SendOut.STATE.READ);
		} else {
			criteria.and("state").nin(SendOut.STATE.SENT, SendOut.STATE.READ, SendOut.STATE.FAILED);
		}

		Query q = new Query();
		q.addCriteria(criteria);

		Update u = new Update();
		u.set("state", state);

		SendOut.History history = new SendOut.History();
		history.setState(state);
		history.setDescription(description);
		history.setCreatedTime(new Date());

		u.push("histories", history);
		UpdateResult wr = mongoTemplate.updateMulti(q, u, SendOut.class);
	}

	public List<SendOut> findByReceiver(String channel, String receiver,long skip, int limit) {
		Criteria criteria = Criteria.where("channel").is(channel).and("receiver").is(receiver);

		Query query = new Query();
		query.addCriteria(criteria);
		query.skip(skip);
		query.limit(limit);
		query.with(Sort.by(Sort.Order.desc("processedTime")));

		List<SendOut> ret = mongoTemplate.find(query, SendOut.class);
		return ret;
	}

	public int countUnreadMsgByCondition(String channel, String receiver, String sender) {
		Criteria criteria = Criteria.where("channel").is(channel).and("receiver").is(receiver);

		if (null  != sender) {
            criteria.and("sender").is(sender);
        }

		criteria.and("state").is(SendOut.STATE.SENT);
		Query query = new Query();
		query.addCriteria(criteria);

		int count = (int)mongoTemplate.count(query, SendOut.class);
		return count;
	}

}
