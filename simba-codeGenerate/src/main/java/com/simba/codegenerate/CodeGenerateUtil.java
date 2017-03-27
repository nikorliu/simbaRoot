package com.simba.codegenerate;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.simba.annotation.DescAnnotation;
import com.simba.common.CustomizedPropertyPlaceholderConfigurer;
import com.simba.framework.util.common.AnnotationUtil;
import com.simba.framework.util.common.ReflectUtil;
import com.simba.framework.util.common.StringUtil;
import com.simba.framework.util.freemarker.FreemarkerUtil;
import com.simba.model.constant.ConstantData;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

/**
 * 代码生成器工具类
 * 
 * @author caozj
 *
 */
public class CodeGenerateUtil {

	private static final Log logger = LogFactory.getLog(CodeGenerateUtil.class);

	private String packageName = CustomizedPropertyPlaceholderConfigurer.getContextProperty("code.generate.package");

	private String srcPath = "/src/main/java/";

	private String webappPath = "/src/main/webapp/";

	private String daoProjectSuffix = "-dao";

	private String serviceProjectSuffix = "-service";

	private static class CodeGenerateUtilHolder {
		private static CodeGenerateUtil instance = new CodeGenerateUtil();
	}

	private CodeGenerateUtil() {

	}

	public static CodeGenerateUtil getInstance() {
		return CodeGenerateUtilHolder.instance;
	}

	/**
	 * 生成页面文件
	 * 
	 * @param webPath
	 * @param param
	 * @throws IOException
	 * @throws TemplateException
	 */
	private void generatePageFile(String webPath, Map<String, Object> param) throws IOException, TemplateException {
		String firstLower = param.get("firstLower").toString();
		String jsDir = webPath + webappPath + "js/app";
		String jspDir = webPath + webappPath + "WEB-INF/jsp/" + firstLower;
		String jsFile = jsDir + "/" + firstLower + ".js";
		String addFile = jspDir + "/add.jsp";
		String updateFile = jspDir + "/update.jsp";
		String listFile = jspDir + "/list.jsp";
		String jsContent = getJs(param);
		String addContent = getAdd(param);
		String updateContent = getUpdate(param);
		String listContent = getList(param);
		FileUtils.writeStringToFile(new File(jsFile), jsContent, ConstantData.DEFAULT_CHARSET);
		logger.info("生成" + jsFile);
		FileUtils.writeStringToFile(new File(addFile), addContent, ConstantData.DEFAULT_CHARSET);
		logger.info("生成" + addFile);
		FileUtils.writeStringToFile(new File(updateFile), updateContent, ConstantData.DEFAULT_CHARSET);
		logger.info("生成" + updateFile);
		FileUtils.writeStringToFile(new File(listFile), listContent, ConstantData.DEFAULT_CHARSET);
		logger.info("生成" + listFile);
	}

	private String getList(Map<String, Object> param) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		return FreemarkerUtil.parseFile("codegenerate/jsp/list.ftl", param);
	}

	private String getUpdate(Map<String, Object> param) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		return FreemarkerUtil.parseFile("codegenerate/jsp/update.ftl", param);
	}

	private String getAdd(Map<String, Object> param) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		return FreemarkerUtil.parseFile("codegenerate/jsp/add.ftl", param);
	}

	private String getJs(Map<String, Object> param) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		return FreemarkerUtil.parseFile("codegenerate/js/js.ftl", param);
	}

	/**
	 * 构造参数
	 * 
	 * @param c
	 * @return
	 */
	private Map<String, Object> buildParam(Class<?> c, PAGETYPE pageType) {
		Map<String, Object> param = new HashMap<String, Object>();
		String className = c.getSimpleName();
		String firstLower = StringUtil.getFirstLower(className);
		DescAnnotation descAnnotation = AnnotationUtil.getClassAnnotation(c, DescAnnotation.class);
		if (descAnnotation == null) {
			param.put("classDesc", "");
		} else {
			param.put("classDesc", descAnnotation.desc());
		}
		param.put("className", className);
		param.put("firstLower", firstLower);
		param.put("packageName", packageName);
		param.put("pageType", pageType.getName());
		List<String> fields = ReflectUtil.getAllPropertiesName(c);
		List<Map<String, String>> filedsWithPage = new ArrayList<>();
		String updateProperties = "";
		String insertProperties = "";
		String params = "";
		String propertiesCount = "";
		for (String field : fields) {
			if ("id".equals(field) || "serialVersionUID".equals(field)) {
				continue;
			}
			if (pageType == PAGETYPE.TREETABLE && "state".equals(field)) {
				continue;
			}
			if (!"parentID".equals(field)) {
				Map<String, String> fDesc = new HashMap<>(2);
				fDesc.put("key", field);
				DescAnnotation da = AnnotationUtil.getFiledAnnotion(c, field, DescAnnotation.class);
				if (da == null) {
					fDesc.put("desc", field);
				} else {
					fDesc.put("desc", da.desc());
				}
				filedsWithPage.add(fDesc);
			}
			updateProperties += " " + field + " = ? ,";
			insertProperties += " " + field + ",";
			propertiesCount += "?,";
			params += firstLower + ".get" + StringUtil.getFirstUpper(field) + "(),";
		}
		updateProperties = updateProperties.substring(0, updateProperties.length() - 1);
		insertProperties = insertProperties.substring(0, insertProperties.length() - 1);
		propertiesCount = propertiesCount.substring(0, propertiesCount.length() - 1);
		params = params.substring(0, params.length() - 1);
		param.put("updateProperties", updateProperties);
		param.put("insertProperties", insertProperties);
		param.put("params", params);
		param.put("propertiesCount", propertiesCount);
		param.put("filedsWithPage", filedsWithPage);
		String idType = "Integer";
		Field[] fs = c.getDeclaredFields();
		Map<String, String> typeMapping = new HashMap<>();
		typeMapping.put("int", "Integer");
		typeMapping.put("long", "Long");
		typeMapping.put("String", "String");
		typeMapping.put("Integer", "Integer");
		typeMapping.put("Long", "Long");
		for (Field f : fs) {
			if ("id".equals(f.getName())) {
				String type = f.getType().getSimpleName();
				idType = typeMapping.get(type);
				logger.info("id的类型为"+idType);
			}
		}
		param.put("idType", idType);
		return param;
	}

	/**
	 * 生成类文件
	 * 
	 * @param projectName
	 * @param webPath
	 * @param param
	 * @param type
	 * @throws TemplateNotFoundException
	 * @throws MalformedTemplateNameException
	 * @throws ParseException
	 * @throws IOException
	 * @throws TemplateException
	 */
	private void generateClassFile(String projectName, String webPath, Map<String, Object> param, CODETYPE type)
			throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		String className = param.get("className").toString();
		String controllerContent = getController(param, type);
		String serviceContent = getService(param, type);
		String serviceImplContent = getServiceImpl(param, type);
		String daoContent = getDao(param, type);
		String daoImplContent = getDaoImpl(param, type);
		String daoDir = getDaolDir(projectName, webPath);
		String serviceDir = getServiceDir(projectName, webPath);
		String packagePath = getPackagePath();
		String controllerFile = webPath + srcPath + packagePath + "/controller/" + className + "Controller.java";
		String serviceFile = serviceDir + srcPath + packagePath + "/service/" + className + "Service.java";
		String serviceImplFile = serviceDir + srcPath + packagePath + "/service/impl/" + className + "ServiceImpl.java";
		String daoFile = daoDir + srcPath + packagePath + "/dao/" + className + "Dao.java";
		String daoImplFile = daoDir + srcPath + packagePath + "/dao/impl/" + className + "DaoImpl.java";
		FileUtils.writeStringToFile(new File(controllerFile), controllerContent, ConstantData.DEFAULT_CHARSET);
		logger.info("生成" + controllerFile);
		FileUtils.writeStringToFile(new File(serviceFile), serviceContent, ConstantData.DEFAULT_CHARSET);
		logger.info("生成" + serviceFile);
		FileUtils.writeStringToFile(new File(serviceImplFile), serviceImplContent, ConstantData.DEFAULT_CHARSET);
		logger.info("生成" + serviceImplFile);
		FileUtils.writeStringToFile(new File(daoFile), daoContent, ConstantData.DEFAULT_CHARSET);
		logger.info("生成" + daoFile);
		FileUtils.writeStringToFile(new File(daoImplFile), daoImplContent, ConstantData.DEFAULT_CHARSET);
		logger.info("生成" + daoImplFile);
	}

	private String getController(Map<String, Object> param, CODETYPE type) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		return FreemarkerUtil.parseFile("codegenerate/" + type.getFolderName() + "/controller.ftl", param);
	}

	private String getService(Map<String, Object> param, CODETYPE type) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		return FreemarkerUtil.parseFile("codegenerate/" + type.getFolderName() + "/service.ftl", param);
	}

	private String getServiceImpl(Map<String, Object> param, CODETYPE type) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		return FreemarkerUtil.parseFile("codegenerate/" + type.getFolderName() + "/serviceImpl.ftl", param);
	}

	private String getDao(Map<String, Object> param, CODETYPE type) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		return FreemarkerUtil.parseFile("codegenerate/" + type.getFolderName() + "/dao.ftl", param);
	}

	private String getDaoImpl(Map<String, Object> param, CODETYPE type) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		return FreemarkerUtil.parseFile("codegenerate/" + type.getFolderName() + "/daoImpl4Mysql.ftl", param);
	}

	/**
	 * 创建好所有需要的路径
	 * 
	 * @param projectName
	 * @param webPath
	 * @param type
	 * @param pageType
	 * @param c
	 */
	private void initPath(String projectName, String webPath, CODETYPE type, PAGETYPE pageType, Class<?> c) {
		String daoDir = getDaolDir(projectName, webPath);
		String serviceDir = getServiceDir(projectName, webPath);
		String packagePath = getPackagePath();
		String daoImplPath = daoDir + srcPath + packagePath + "/dao/impl";
		String serviceImplPath = serviceDir + srcPath + packagePath + "/service/impl";
		String controllerPath = webPath + srcPath + packagePath + "/controller";
		new File(daoImplPath).mkdirs();
		new File(serviceImplPath).mkdirs();
		new File(controllerPath).mkdirs();
		if (pageType != PAGETYPE.NONE) {
			String jsDir = webPath + webappPath + "/js/app";
			String jspDir = webPath + webappPath + "/WEB-INF/jsp/" + StringUtil.getFirstLower(c.getSimpleName());
			new File(jsDir).mkdirs();
			new File(jspDir).mkdirs();
		}
	}

	private String getPackagePath() {
		return packageName.replaceAll("\\.", "/");
	}

	/**
	 * 生成代码
	 * 
	 * @param classes
	 *            要生成代码的Class对象数组
	 * @param codeType
	 *            代码类型
	 * @param pageType
	 *            页面类型
	 * @param projectName
	 *            项目名称
	 * @param webPath
	 *            web项目路径
	 * @throws TemplateException
	 * @throws IOException
	 * @throws ParseException
	 * @throws MalformedTemplateNameException
	 * @throws TemplateNotFoundException
	 */
	public void codeGenerate(Class<?>[] classes, CODETYPE codeType, PAGETYPE pageType, String projectName, String webPath)
			throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		for (Class<?> c : classes) {
			codeGenerate(c, codeType, pageType, projectName, webPath);
		}
	}

	/**
	 * 生成代码
	 * 
	 * @param c
	 *            要生成代码的Class对象
	 * @param codeType
	 *            代码类型
	 * @param pageType
	 *            页面类型
	 * @param projectName
	 *            项目名称
	 * @param webPath
	 *            web项目路径
	 * @throws TemplateException
	 * @throws IOException
	 * @throws ParseException
	 * @throws MalformedTemplateNameException
	 * @throws TemplateNotFoundException
	 */
	private void codeGenerate(Class<?> c, CODETYPE codeType, PAGETYPE pageType, String projectName, String webPath)
			throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		logger.info("生成代码" + c.getName() + "开始,代码类型为" + codeType.getFolderName() + ",页面类型为" + pageType.getName() + ",项目名称:" + projectName + ",web项目目录:" + webPath);
		initPath(projectName, webPath, codeType, pageType, c);
		Map<String, Object> param = buildParam(c, pageType);
		generateClassFile(projectName, webPath, param, codeType);
		if (pageType != PAGETYPE.NONE) {
			generatePageFile(webPath, param);
		}
		logger.info("生成代码" + c.getName() + "结束,代码类型为" + codeType.getFolderName() + ",页面类型为" + pageType.getName() + ",项目名称:" + projectName + ",web项目目录:" + webPath);
	}

	/**
	 * 获取dao项目的路径
	 * 
	 * @param projectName
	 * @param webPath
	 * @return
	 */
	private String getDaolDir(String projectName, String webPath) {
		return new File(webPath).getParentFile().getAbsolutePath() + "/" + projectName + daoProjectSuffix;
	}

	/**
	 * 获取service项目的路径
	 * 
	 * @param projectName
	 * @param webPath
	 * @return
	 */
	private String getServiceDir(String projectName, String webPath) {
		return new File(webPath).getParentFile().getAbsolutePath() + "/" + projectName + serviceProjectSuffix;
	}

}
