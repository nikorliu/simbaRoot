package com.simba.framework.util.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.csource.common.MyException;

import com.simba.fastdfs.FastdfsUtil;
import com.simba.framework.util.common.SystemUtil;

/**
 * fastdfs分布式文件上传管理
 * 
 * @author caozhejun
 *
 */
public class FastdfsUpload implements UploadInterface {

	private FastdfsUpload() {

	}

	private static final class FastdfsUploadHolder {
		private static final FastdfsUpload instance = new FastdfsUpload();
	}

	public static FastdfsUpload getInstance() {
		return FastdfsUploadHolder.instance;
	}

	@Override
	public String upload(byte[] content, String fileName, String type) throws IOException, MyException {
		return FastdfsUtil.getInstance().upload(content, fileName);
	}

	@Override
	public String upload(byte[] content, String fileName) throws IOException, MyException {
		return upload(content, fileName, StringUtils.EMPTY);
	}

	@Override
	public InputStream download(String fileName) throws IOException, MyException {
		String[] fastdfsFlag = FastdfsUtil.getInstance().parseUrl(fileName);
		String localPath = SystemUtil.getTempDir() + "/" + fastdfsFlag[1];
		FastdfsUtil.getInstance().download(fastdfsFlag[0], fastdfsFlag[1], localPath);
		return new FileInputStream(new File(localPath));
	}

	@Override
	public void delete(String fileName) throws IOException, MyException {
		String[] fastdfsFlag = FastdfsUtil.getInstance().parseUrl(fileName);
		FastdfsUtil.getInstance().delete(fastdfsFlag[0], fastdfsFlag[1]);
	}

	@Override
	public long size(String fileName) throws IOException, MyException {
		String[] fastdfsFlag = FastdfsUtil.getInstance().parseUrl(fileName);
		return FastdfsUtil.getInstance().size(fastdfsFlag[0], fastdfsFlag[1]);
	}

}
