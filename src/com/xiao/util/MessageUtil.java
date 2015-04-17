package com.xiao.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.xiao.entity.ExcelEntity;
import com.xiao.po.TextMessage;


public class MessageUtil {

	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVENT = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";

	/**
	 * xml转为map集合
	 * @author Longwu Xiao
	 * @param request
	 * @return map
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
		Map<String,String> map = new HashMap<String,String>();
		SAXReader reader = new SAXReader();

		InputStream ins = request.getInputStream();
		Document doc = reader.read(ins);

		Element root = doc.getRootElement();

		List<Element> list = root.elements();

		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}

	/**
	 * 将文本信息转换成xml
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return  xstream.toXML(textMessage);
	}

	public static String initText(String toUserName,String fromUserName,String content){
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MessageUtil.MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		return MessageUtil.textMessageToXml(text);
	}

	/**
	 * 主菜单
	 * @return
	 */
	public static String subscribeText(){
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您关注信计一班公众号，您可以输入姓名或电话来查找该同学信息，比如：\n");
		sb.append("输入广东，会显示所有在广东的同学；\n");
		sb.append("输入肖龙雾，会显示肖龙雾的个人信息。\n");
		return sb.toString();
	}


	public static String firstMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("本套课程介绍微信公众号开发，主要涉及公众号介绍、编程模式介绍、开发模式介绍等");
		return sb.toString();
	}

	public static String secondMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("慕课网是垂直的互联网IT技能免费学习网站。以独家视频教程、在线编程工具、学习计划、问答社区为核心特色。在这里，你可以找到最好的互联网技术牛人，也可以通过免费的在线公开视频课程学习国内领先的互联网IT技术。" +
"慕课网课程涵盖前端开发、PHP、Html5、Android、iOS、Swift等IT前沿技术语言，包括基础课程、实用案例、高级分享三大类型，适合不同阶段的学习人群。以纯干货、短视频的形式为平台特点，为在校学生、职场白领提供了一个迅速提升技能、共同分享进步的学习平台。[1-2]" +
"4月2日，国内首个IT技能学习类应用——慕课网3.1.0版本在应用宝首发。据了解，在此次上线的版本中，慕课网新增了课程历史记录、相关课程推荐等四大功能，为用户营造更加丰富的移动端IT学习体验。");
		return sb.toString();
	}

	public static String showResult(ExcelEntity excelEntity) {
		StringBuffer sb = new StringBuffer();
		sb.append("Hi，大家好，我是：" + excelEntity.getName());
		sb.append("，我的电话号码是：" + excelEntity.getTel());
		sb.append("，我的qq是：" + excelEntity.getQq());
		sb.append("，我的微信是：" + excelEntity.getWeixin());
		String[] address = excelEntity.getAddress().split("-");
		sb.append("，我现在在：" + address[0] + "，" + address[1] + "，"
				+ address[2] + "工作，欢迎大家来电，多多联系！");
		return sb.toString();
	}
}
