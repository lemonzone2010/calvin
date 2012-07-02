package com.xia.search.solr.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;

/**
 * 
 * @author xiayong
 * 
 */
public class XmlUtil {
	public static Map<Object, Document> cache = new HashMap<Object, Document>();
	private Document document;

	public XmlUtil(String xmlString) {
		try {
			document=cache.get(xmlString);
			if (document == null) {
				document = DocumentHelper.parseText(xmlString);
				cache.put(xmlString, document);
			}
		} catch (DocumentException e) {
			throw new RuntimeException("xml解析出错:" + xmlString, e);
		}
	}

	public XmlUtil(File xmlFile) {
		try {
			document=cache.get(xmlFile);
			if (document == null) {
				SAXReader reader = new SAXReader();
				document = reader.read(xmlFile);
			}
		} catch (DocumentException e) {
			throw new RuntimeException("xml解析出错:" + xmlFile, e);
		}
	}

	/**
	 * 不存在返回""
	 * 
	 * @param xpath
	 *            如//icbc_corporbank_credit_pay/order/amount
	 * @return
	 */
	public String getString(String xpath) {
		if (null == document) {
			return "";
		}
		Node node = document.selectSingleNode(StringUtils.trim(xpath));
		if (null == node) {
			return "";
		}
		return StringUtils.trim(node.getStringValue());
	}

	@SuppressWarnings("unchecked")
	public List<Node> getNodes(String xpath) {
		return document.selectNodes(xpath);
	}
	
	@SuppressWarnings("unchecked")
	public boolean isExistsAttribute(String attrXpath,String value) {
		boolean ret=false;
		List<Attribute> attrs=document.selectNodes(attrXpath);
		//Attribute singleNode = (Attribute) getSingleNode(attrXpath);
		for (Attribute attribute : attrs) {
			ret =	StringUtils.equals(attribute.getValue(), value);
			if(ret) {
				return true;
			}
		}
		return false;
	}
	public Node getSingleNode(String xpath) {
		return document.selectSingleNode(xpath);
	}
	/*public void createNode(String parentPath,String nodeName,Map<String,String> attributes) {
		DefaultElement node = (DefaultElement) document.selectSingleNode(parentPath);
		node.
	}*/

	/**
	 * 不存在返回null
	 * 
	 * @param xpath
	 *            如//icbc_corporbank_credit_pay/order/amount
	 * @return
	 */
	public Integer getInt(String xpath) {
		String value = getString(xpath);
		if (StringUtils.isNotBlank(value)) {
			return Integer.valueOf(value);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<String> getAsXml(String xpath) {
		List<String> ret = new ArrayList<String>();
		List<Node> statementNodes = document.selectNodes(xpath);
		for (Node node : statementNodes) {
			ret.add(node.asXML());
		}
		return ret;
	}
	public void wirteToFile(String fileName, String encoding) {
		FileWriter writer = null;
		try {
			// 创建输出格式
			OutputFormat format = OutputFormat.createPrettyPrint();
			// 制定输出xml的编码类型
			format.setEncoding(encoding);

			writer = new FileWriter(new File(fileName));
			// 创建一个文件输出流
			XMLWriter xmlwriter = new XMLWriter(writer, format);
			// 将格式化后的xml串写入到文件
			xmlwriter.write(document);
		} catch (Exception e) {
		} finally {
			try {
				if (null != writer) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String formatXml(String xml, String encoding) {
		if (!xml.startsWith("<?")) {
			return xml;
		}
		String returnValue = "";
		SAXReader saxReader = new SAXReader();
		StringWriter writer = null;
		try {
			Document document = saxReader.read(new ByteArrayInputStream(xml.getBytes(encoding)));
			// 创建输出格式
			OutputFormat format = OutputFormat.createPrettyPrint();
			// 制定输出xml的编码类型
			format.setEncoding(encoding);

			writer = new StringWriter();
			// 创建一个文件输出流
			XMLWriter xmlwriter = new XMLWriter(writer, format);
			// 将格式化后的xml串写入到文件
			xmlwriter.write(document);
			returnValue = writer.toString();
		} catch (Exception e) {
			return xml;
		} finally {
			try {
				if (null != writer) {
					writer.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// 返回编译后的字符串格式
		return returnValue;
	}

	/**
	 * 格式化XML文件
	 * 
	 * @param xml
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static String formatXml(String xml) {
		return formatXml(xml, "GBK");
	}
}
