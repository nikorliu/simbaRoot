package com.caozj.fastdfs;

import java.io.IOException;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerServer;

/**
 * 操作fastdfs的工具类
 * 
 * @author caozhejun
 *
 */
public class FastdfsUtil {

	private static final Log logger = LogFactory.getLog(FastdfsUtil.class);

	/** 连接池 */
	private ConnectionPool connectionPool = null;
	/** 连接池默认最小连接数 */
	private long minPoolSize = 10;
	/** 连接池默认最大连接数 */
	private long maxPoolSize = 30;
	/** 当前创建的连接数 */
	private volatile long nowPoolSize = 0;
	/** 默认等待时间（单位：秒） */
	private long waitTimes = 200;

	private FastdfsUtil() {
		init();
	}

	private void init() {
		String logId = UUID.randomUUID().toString();
		logger.info("[初始化线程池(Init)][" + logId + "][默认参数：minPoolSize=" + minPoolSize + ",maxPoolSize=" + maxPoolSize + ",waitTimes=" + waitTimes + "]");
		connectionPool = new ConnectionPool(minPoolSize, maxPoolSize, waitTimes);
	}

	private static final class FastdfsUtilHolder {
		private static FastdfsUtil instance = new FastdfsUtil();
	}

	public static FastdfsUtil getInstance() {
		return FastdfsUtilHolder.instance;
	}

	/**
	 * 上传到fastdfs
	 * 
	 * @param content
	 *            文件的字节数组
	 * @param fileName
	 *            文件名
	 * @return 可以访问的http地址
	 * @throws MyException
	 * @throws IOException
	 */
	public String upload(byte[] content, String fileName) throws IOException, MyException {
		String logId = UUID.randomUUID().toString();
		TrackerServer trackerServer = connectionPool.checkout(logId);
		StorageServer storageServer = null;
		StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
		String fileExtName = getFileExt(fileName);
		NameValuePair[] metaList = new NameValuePair[3];
		metaList[0] = new NameValuePair("fileName", fileName);
		metaList[1] = new NameValuePair("fileExtName", fileExtName);
		metaList[2] = new NameValuePair("fileLength", String.valueOf(content.length));
		String[] result = storageClient.upload_file(content, fileExtName, metaList);
		/** 上传完毕及时释放连接 */
		connectionPool.checkin(trackerServer, logId);
		String groupName = result[0];
		String remoteFile = result[1];
		String url = "http://" + trackerServer.getInetSocketAddress().getHostName() + ":" + ClientGlobal.getG_tracker_http_port() + "/" + groupName + "/" + remoteFile;
		return url;
	}

	/**
	 * 删除文件
	 * 
	 * @param fileUrl
	 *            文件地址
	 * @throws IOException
	 * @throws MyException
	 */
	public void delete(String fileUrl) throws IOException, MyException {
		String logId = UUID.randomUUID().toString();
		TrackerServer trackerServer = connectionPool.checkout(logId);
		StorageServer storageServer = null;
		StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
		int index = fileUrl.lastIndexOf("/");
		String remoteFileName = fileUrl.substring(index + 1);
		int mIndex = fileUrl.indexOf(":");
		int bIndex = fileUrl.indexOf("/", mIndex);
		String groupName = fileUrl.substring(bIndex + 1, index);
		storageClient.delete_file(groupName, remoteFileName);
		/** 上传完毕及时释放连接 */
		connectionPool.checkin(trackerServer, logId);
	}

	/**
	 * 获取文件的扩展名
	 * 
	 * @param fileName
	 *            文件名
	 * @return
	 */
	private String getFileExt(String fileName) {
		int index = fileName.lastIndexOf(".");
		return fileName.substring(index + 1);
	}

}
