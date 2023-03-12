package com.eagletsoft.post.core.sendout.connector.impl;

import java.util.*;

import com.eagletsoft.boot.framework.common.utils.JsonUtils;
import com.eagletsoft.post.core.route.Channel;
import com.eagletsoft.post.core.route.impl.Router;
import com.eagletsoft.post.core.sendout.SendOutException;
import com.eagletsoft.post.core.sendout.SendOutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import net.sf.json.JSONObject;

@Component
public class JPushConnector extends AbstractConnector {
	private static Logger LOG = LoggerFactory.getLogger(JPushConnector.class);

	@Autowired
	private Router router;

	@Override
	public String getName() {
		return "JPUSH";
	}

	@Value("${apns.production}")
	private boolean apns = false;

	@Override
	public void send(SendOutMessage sm) {
		Channel channel = router.findChannel(sm.getChannel());
		Map<String, Object> props = channel.getProps();
		String master_secret = props.get("masterSecret").toString();
		String appKey = props.get("appkey").toString();
		ClientConfig clientConfig = ClientConfig.getInstance();
		final JPushClient jpushClient = new JPushClient(master_secret, appKey, null, clientConfig);

		// TemplatePresenter presenter = new TemplatePresenter(sm.getContent());
		// String title = presenter.apply(sm.getTemplate().getProps().get("subject"));
		// String message = presenter.apply(sm.getTemplate().getProps().get("content"));
		String title = (String) sm.getContent().get("title");
		String message = (String) sm.getContent().get("text");
		String extras = null;
		if (null != sm.getContent().get("extras")) {
			extras = JsonUtils.writeValue(sm.getContent().get("extras"));
		}
		List<String> reList = new ArrayList<String>();
		reList.add(sm.getReceiver());
		String[] receiver = reList.toArray(new String[] {});

		final PushPayload payload = buildPushObject(apns, title, message, extras, receiver);
		try {
			PushResult result = jpushClient.sendPush(payload);
			if (result.statusCode != 0) {
				throw new SendOutException("Send fail", "发送失败");
			}
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);

		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
		}
	}

	/**
	 * 构建推送接收对象
	 * 
	 * @param message
	 *            推送给的消息
	 * @param extra
	 *            附加的信息(JSON的字符串)
	 * @param receiver
	 *            接收人（设备registrationId）
	 * @return
	 */
	public static PushPayload buildPushObject(boolean apns, String title, String message, String extra, String... receiver) {

		Map<String, String> extras = new HashMap<String, String>();
		if (null != extra) {
			JSONObject jsonObj = JSONObject.fromObject(extra);
			Iterator it = jsonObj.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = jsonObj.getString(key);
				extras.put(key, value);
			}
		}
		return PushPayload.newBuilder()
				// Platform.all()默认发送ios的生产环境
				.setPlatform(Platform.all()).setOptions(Options.newBuilder().setApnsProduction(apns).build())
				.setAudience(Audience.registrationId(receiver))
				.setNotification(Notification.newBuilder().setAlert(message)
						.addPlatformNotification(
								AndroidNotification.newBuilder().setTitle(title).addExtras(extras).build())
						.addPlatformNotification(IosNotification.newBuilder().incrBadge(1).addExtras(extras).build())
						.build())
				.build();
	}
}
