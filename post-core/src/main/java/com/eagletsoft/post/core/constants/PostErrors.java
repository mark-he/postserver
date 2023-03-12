package com.eagletsoft.post.core.constants;

import com.eagletsoft.boot.framework.common.errors.StandardErrors;

public class PostErrors extends StandardErrors {
	public static StandardErrors.ServerError DUPLICATED_MESSAGE = new ServerError(2002, "Duplicated messages");
}
