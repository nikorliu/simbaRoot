package com.caozj.util.send;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.caozj.framework.util.json.FastJsonUtil;
import com.caozj.model.wx.Url;
import com.caozj.model.wx.upload.NewsArticles;
import com.caozj.model.wx.upload.UploadResult;
import com.caozj.model.wx.upload.Video;
import com.caozj.util.common.AccessTokenUtil;
import com.caozj.util.common.FileUploadUtil;
import com.caozj.util.common.PostJsonUtil;
import com.caozj.util.common.WxConstantData;

/**
 * 上传微信服务器文件工具类
 * 
 * @author caozhejun
 *
 */
@Component
public class UplodWxUtil {

	@Autowired
	private AccessTokenUtil accessTokenUtil;

	@Autowired
	private PostJsonUtil postJsonUtil;

	@Autowired
	private FileUploadUtil fileUploadUtil;

	/**
	 * 上传图文消息内的图片获取URL
	 * 
	 * @param file
	 */
	public String uploadImage(String file) {
		String url = WxConstantData.uploadImageUrl + "?access_token=" + accessTokenUtil.getAccessToken();
		Url imageUrl = fileUploadUtil.upload(url, file, "上传图文消息内的图片", Url.class);
		return imageUrl.getUrl();
	}

	/**
	 * 上传图文消息素材
	 * 
	 * @param newsArticles
	 * @return
	 */
	public UploadResult uploadNewsArticles(NewsArticles newsArticles) {
		String url = WxConstantData.uploadNewsUrl + "?access_token=" + accessTokenUtil.getAccessToken();
		String json = FastJsonUtil.toJson(newsArticles);
		return postJsonUtil.postJson(url, json, "上传图文消息素材", UploadResult.class);
	}

	/**
	 * 上传视频
	 * 
	 * @return
	 */
	public UploadResult uploadVideo(Video video) {
		String url = WxConstantData.uploadVideoUrl + "?access_token=" + accessTokenUtil.getAccessToken();
		String json = FastJsonUtil.toJson(video);
		return postJsonUtil.postJson(url, json, "上传视频", UploadResult.class);
	}
}
