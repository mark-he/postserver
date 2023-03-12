package com.eagletsoft.post.api.v1;

import java.util.ArrayList;
import java.util.List;

import com.eagletsoft.post.core.service.SendOutService;
import com.eagletsoft.post.core.service.bo.AckConditionRequest;
import com.eagletsoft.post.core.service.bo.MsgPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eagletsoft.boot.framework.api.utils.ApiResponse;
import com.eagletsoft.post.core.data.Payload;
import com.eagletsoft.post.core.service.PostService;
import com.eagletsoft.post.core.service.bo.AckRequest;

@Controller
@RequestMapping(value = "/v1")
public class PostApi {

	@Autowired
	private PostService postService;

	@Autowired
	private SendOutService sendOutService;

	@RequestMapping(value = "/send")
	public @ResponseBody Object send(@RequestBody Payload envelope) {
		String id = postService.post(envelope);
		return ApiResponse.make(id);
	}
	
	@RequestMapping(value = "/send/batch")
	public @ResponseBody Object sendBatch(@RequestBody List<Payload> list) {
		List<String> ids = new ArrayList<String>();
		for(Payload p : list) {
			String id = postService.post(p);
			ids.add(id);
		}
		return ApiResponse.make(ids);
	}

	@RequestMapping(value = "send_tag")
	public @ResponseBody Object sendTag()  {
		return ApiResponse.make("Server is working");
	}

	@RequestMapping(value = "ack")
	public @ResponseBody Object ack(@RequestBody AckRequest request)  {
		postService.ack(request);
		return ApiResponse.make();
	}

	@RequestMapping(value = "ack/group")
	public @ResponseBody Object ackAll(@RequestBody AckConditionRequest request)  {
		postService.ackByCondition(request);
		return ApiResponse.make();
	}


	@RequestMapping(value = "/msg/unread/count")
	public @ResponseBody
	Object findUnreadMsgCount(@RequestBody MsgPageRequest request)  {
		int count =  sendOutService.countUnreadMsg(request.getChannel(), request.getReceiver(), request.getSender());
		return ApiResponse.make(count);
	}


	@RequestMapping(value = "report")
	public @ResponseBody Object report()  {
		return ApiResponse.make("Server is working");
	}


}
