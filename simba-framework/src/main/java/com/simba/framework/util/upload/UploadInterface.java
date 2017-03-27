package com.simba.framework.util.upload;

import java.io.IOException;
import java.io.InputStream;

import org.csource.common.MyException;

/**
 * 文件上传接口
 * 
 * @author caozhejun
 *
 */
public interface UploadInterface {

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
	String upload(byte[] content, String fileName, String type) throws IOException, MyException;

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
	String upload(byte[] content, String fileName) throws IOException, MyException;

	/**
	 * 将文件下载
	 * 
	 * @param fileName
	 *            文件名称
	 * @return
	 * @throws IOException
	 * @throws MyException
	 */
	InputStream download(String fileName) throws IOException, MyException;

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 *            文件名称
	 * @throws MyException
	 * @throws IOException
	 */
	void delete(String fileName) throws IOException, MyException;

	/**
	 * 获取文件长度
	 * 
	 * @param fileName
	 * @throws IOException
	 * @throws MyException
	 */
	long size(String fileName) throws IOException, MyException;
}
