package com.eagletsoft.post.api.v1;

import com.eagletsoft.boot.framework.api.utils.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "v1")
public class UserApi {

	@RequestMapping(value = "register")
	public @ResponseBody Object register() {
		return ApiResponse.make("Server is working");
	}

	@RequestMapping(value = "tag/add")
	public @ResponseBody Object addTag() {
		return ApiResponse.make("Server is working");
	}

	@RequestMapping(value = "tag/remove")
	public @ResponseBody Object removeTag()  {
		return ApiResponse.make("Server is working");
	}

	@RequestMapping(value = "tag/user/remove")
	public @ResponseBody Object removeUserFromTag() {
		return ApiResponse.make("Server is working");
	}
}
