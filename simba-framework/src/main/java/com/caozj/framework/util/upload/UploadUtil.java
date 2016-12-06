package com.caozj.framework.util.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.csource.common.MyException;

import com.caozj.common.CustomizedPropertyPlaceholderConfigurer;
import com.caozj.fastdfs.FastdfsUtil;
import com.caozj.framework.util.common.SystemUtil;

/**
 * 文件上传工具类
 * 
 * @author caozj
 *
 */
public class UploadUtil {

	private static String storage;

	private static String localDir;

	private static final SimpleDateFormat format = new SimpleDateFormat("/yyyy/MM/dd");

	public static void init() {
		storage = CustomizedPropertyPlaceholderConfigurer.getContextProperty("files.storage");
		localDir = CustomizedPropertyPlaceholderConfigurer.getContextProperty("files.dir");
	}

	/**
	 * 获取唯一的文件名
	 * 
	 * @param fileName
	 *            原文件名
	 * @return
	 */
	public static String getUniqueFileName(String fileName) {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		int beginIndex = fileName.lastIndexOf(".");
		if (beginIndex == -1) {
			return uuid;
		}
		String ext = fileName.substring(beginIndex);
		return uuid + ext;
	}

	/**
	 * 上传文件，返回文件的路径，可以直接使用http访问
	 * 
	 * @param content
	 *            文件内容
	 * @param fileName
	 *            文件名称
	 * @param type
	 *            用于本地上传管理类型
	 * @return
	 * @throws IOException
	 * @throws MyException
	 */
	public static String upload(byte[] content, String fileName, String type) throws IOException, MyException {
		String absPath = null;
		if ("local".equals(storage)) {
			absPath = "/" + type + getPath() + getUniqueFileName(fileName);
			String fullPath = localDir + absPath;
			FileUtils.writeByteArrayToFile(new File(fullPath), content);
		} else if ("fastdfs".equals(storage)) {
			absPath = FastdfsUtil.getInstance().upload(content, fileName);
		}
		return absPath;
	}

	/**
	 * 上传文件
	 * 
	 * @param content
	 *            文件内容
	 * @param fileName
	 *            文件名称
	 * @return
	 * @throws IOException
	 * @throws MyException
	 */
	public static String upload(byte[] content, String fileName) throws IOException, MyException {
		return upload(content, fileName, StringUtils.EMPTY);
	}

	/**
	 * 将文件下载
	 * 
	 * @param fileName
	 *            文件名称
	 * @return
	 * @throws IOException
	 * @throws MyException
	 */
	public static InputStream download(String fileName) throws IOException, MyException {
		InputStream in = null;
		if ("local".equals(storage)) {
			in = new FileInputStream(new File(localDir + fileName));
		} else if ("fastdfs".equals(storage)) {
			String[] fastdfsFlag = FastdfsUtil.getInstance().parseUrl(fileName);
			String localPath = SystemUtil.getTempDir() + "/" + fastdfsFlag[1];
			FastdfsUtil.getInstance().download(fastdfsFlag[0], fastdfsFlag[1], localPath);
			in = new FileInputStream(new File(localPath));
		}
		return in;
	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 *            文件名称
	 * @throws MyException
	 * @throws IOException
	 */
	public static void delete(String fileName) throws IOException, MyException {
		if ("local".equals(storage)) {
			FileUtils.deleteQuietly(new File(localDir + fileName));
		} else if ("fastdfs".equals(storage)) {
			String[] fastdfsFlag = FastdfsUtil.getInstance().parseUrl(fileName);
			FastdfsUtil.getInstance().delete(fastdfsFlag[0], fastdfsFlag[1]);
		}
	}

	/**
	 * 获取上传文件的路径
	 * 
	 * @return
	 */
	public static String getPath() {
		return format.format(new Date()) + "/";
	}

}
