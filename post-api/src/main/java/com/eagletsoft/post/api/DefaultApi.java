package com.eagletsoft.post.api;

import com.eagletsoft.boot.framework.api.utils.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "")
public class DefaultApi {
	
	@RequestMapping(value = "")
	public @ResponseBody Object index() throws Exception {
		return ApiResponse.make("Server is working");
	}

	@RequestMapping(value = "echo")
	public @ResponseBody Object echo() throws Exception {
		return ApiResponse.make();
	}
	
}
