package com.caozj.util.send;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.caozj.framework.util.json.FastJsonUtil;
import com.caozj.model.wx.group.GroupResult;
import com.caozj.model.wx.group.filter.GroupImage;
import com.caozj.model.wx.group.filter.GroupMpNews;
import com.caozj.model.wx.group.filter.GroupText;
import com.caozj.model.wx.group.filter.GroupVideo;
import com.caozj.model.wx.group.filter.GroupVoice;
import com.caozj.util.common.AccessTokenUtil;
import com.caozj.util.common.PostJsonUtil;
import com.caozj.util.common.WxConstantData;

/**
 * 群发接口(在公众平台网站上，为订阅号提供了每天一条的群发权限，为服务号提供每月（自然月）4条的群发权限。1、对于认证订阅号，群发接口每天可成功调用1次，
 * 此次群发可选择发送给全部用户或某个标签；2、对于认证服务号虽然开发者使用高级群发接口的每日调用限制为100次，但是用户每月只能接收4条，
 * 无论在公众平台网站上，还是使用接口群发，用户每月只能接收4条群发消息，多于4条的群发将对该用户发送失败；)
 * 
 * @author caozhejun
 *
 */
@Component
public class SendGroupMessageByTagWxUtil {

	@Autowired
	private AccessTokenUtil accessTokenUtil;

	@Autowired
	private PostJsonUtil postJsonUtil;

	/**
	 * 根据标签进行群发(图文消息)
	 * 
	 * @param msg
	 */
	public GroupResult send(GroupMpNews msg) {
		String url = WxConstantData.sendGroupByTag + "?access_token=" + accessTokenUtil.getAccessToken();
		String json = FastJsonUtil.toJson(msg);
		return postJsonUtil.postJson(url, json, "根据标签进行群发", GroupResult.class);
	}

	/**
	 * 根据标签进行群发(文本)
	 * 
	 * @param msg
	 */
	public GroupResult send(GroupText msg) {
		String url = WxConstantData.sendGroupByTag + "?access_token=" + accessTokenUtil.getAccessToken();
		String json = FastJsonUtil.toJson(msg);
		return postJsonUtil.postJson(url, json, "根据标签进行群发", GroupResult.class);
	}

	/**
	 * 根据标签进行群发(语音)
	 * 
	 * @param msg
	 */
	public GroupResult send(GroupVoice msg) {
		String url = WxConstantData.sendGroupByTag + "?access_token=" + accessTokenUtil.getAccessToken();
		String json = FastJsonUtil.toJson(msg);
		return postJsonUtil.postJson(url, json, "根据标签进行群发", GroupResult.class);
	}

	/**
	 * 根据标签进行群发(图片)
	 * 
	 * @param msg
	 */
	public GroupResult send(GroupImage msg) {
		String url = WxConstantData.sendGroupByTag + "?access_token=" + accessTokenUtil.getAccessToken();
		String json = FastJsonUtil.toJson(msg);
		return postJsonUtil.postJson(url, json, "根据标签进行群发", GroupResult.class);
	}

	/**
	 * 根据标签进行群发(视频)
	 * 
	 * @param msg
	 */
	public GroupResult send(GroupVideo msg) {
		String url = WxConstantData.sendGroupByTag + "?access_token=" + accessTokenUtil.getAccessToken();
		String json = FastJsonUtil.toJson(msg);
		return postJsonUtil.postJson(url, json, "根据标签进行群发", GroupResult.class);
	}
}
