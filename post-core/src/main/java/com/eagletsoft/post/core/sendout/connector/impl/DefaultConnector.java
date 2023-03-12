package com.eagletsoft.post.core.sendout.connector.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.eagletsoft.boot.framework.common.utils.JsonUtils;
import com.eagletsoft.post.core.sendout.SendOutMessage;
import com.eagletsoft.post.core.template.TemplatePresenter;

@Component
public class DefaultConnector extends AbstractConnector {
	private static Logger LOG = LoggerFactory.getLogger(DefaultConnector.class);

	@Override
	public String getName() {
		return "DEFAULT";
	}

	@Override
	public void send(SendOutMessage sm) {

		TemplatePresenter presenter = new TemplatePresenter(sm.getContent());

		String title = presenter.apply(sm.getTemplate().getProps().get("subject"));
		String message = presenter.apply(sm.getTemplate().getProps().get("content"));
		String extras = JsonUtils.writeValue(sm.getContent().get("extras"));

		LOG.warn(title);
		LOG.warn(message);
		LOG.warn(extras);
	}

}
