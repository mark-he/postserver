package com.eagletsoft.post.core.template;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class TemplateManager {

	private static Logger LOG = LoggerFactory.getLogger(TemplateManager.class);
	private Resource[] resources = {};

	private Map<String, Template> templateMap = new HashMap<>();

	public Template findTemplate(String name, String channel) {
		Template template = templateMap.get(name + "." + channel);
		if (null == template) {
			template = templateMap.get(name);
		}
		return template;
	}

	@PostConstruct
	public void init() throws Exception {
		LOG.warn("Started to load templates");
		loadFileRecursive(resources);
	}

	private void loadFileRecursive(Resource[] resources) throws Exception {
		for (Resource resource : resources) {
			Template template = new Template();
			template.setName(resource.getFilename().substring(0, resource.getFilename().length() - ".xml".length()));
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			try {
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(new InputSource(resource.getInputStream()));
				Element rootElement = document.getDocumentElement();

				NodeList list = rootElement.getChildNodes();

				Node subNode;
				for (int i = 0; i < list.getLength(); i++) {
					subNode = list.item(i);
					if (subNode.getNodeType() == Node.ELEMENT_NODE) {

						String value = StringUtils.defaultIfEmpty(subNode.getNodeValue(), "");
						NodeList nList = subNode.getChildNodes();
						for (int k = 0; k < nList.getLength(); k++) {
							Node kn = nList.item(k);
							if (kn.getNodeType() == Node.CDATA_SECTION_NODE) {
								value += ((CDATASection) kn).getData();
							} else {
								value += kn.getNodeValue();
							}
						}
						template.getProps().put(subNode.getNodeName(), value);
					}
				}
				templateMap.put(template.getName(), template);
				LOG.warn("Loaded template: " + template.getName());
			} catch (Exception e) {
				LOG.error("Error in reading tempalte configuration!");
				throw e;
			}
		}
	}

	public Resource[] getResources() {
		return resources;
	}

	public void setResources(Resource[] resources) {
		this.resources = resources;
	}
}
