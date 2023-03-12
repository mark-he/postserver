package com.eagletsoft.post.api;

import com.eagletsoft.boot.framework.api.utils.ApiExceptionResolver;
import com.eagletsoft.post.CoreFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProjectApiExceptionResolver extends ApiExceptionResolver {
    private static Logger LOG = LoggerFactory.getLogger(ProjectApiExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        CoreFramework.getInstance().rollback();
        ModelAndView ret = super.resolveException(request, response, handler, ex);
        ex.printStackTrace();
        return ret;
    }
}
