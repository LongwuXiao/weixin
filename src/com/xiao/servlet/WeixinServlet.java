package com.xiao.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.xiao.entity.ExcelEntity;
import com.xiao.po.TextMessage;
import com.xiao.util.CheckUtil;
import com.xiao.util.ExcelOperateUtil;
import com.xiao.util.MatchUtil;
import com.xiao.util.MessageUtil;

/**
 * Servlet implementation class WeixinServlet
 */
@WebServlet("/WeixinServlet")
public class WeixinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static List<ExcelEntity> excelResult = null;

	@Override
	public void init() throws ServletException {

		String path = getServletContext().getRealPath("/");
		File file = new File(path + "WEB-INF/classes/信计一班.xls");
		try {
			excelResult = ExcelOperateUtil.getData(file, 1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.init();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();
		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			Map<String, String> map = MessageUtil.xmlToMap(request);
			String toUserName = map.get("ToUserName");
			String fromUserName = map.get("FromUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");

			String message = null;
			// 如果是文本
			if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
				StringBuffer info = new StringBuffer();
				// 如果输入名字，则返回该同学的详细信息
				for (ExcelEntity excelEntity : excelResult) {
					if (MatchUtil.matchs(excelEntity.getString(), content)) {
						info.append(MessageUtil.showResult(excelEntity)
								+ "\n\n");
					}

					/*
					 * if (excelEntity.getName().equals(content)) { message =
					 * MessageUtil.initText(toUserName, fromUserName,
					 * MessageUtil.showResult(excelEntity)); // message =
					 * MessageUtil.showResult(excelEntity); break; }
					 */
				}
				if (info.length() == 0) {
					info.append("您输入的内容是：" + content + "\n暂找不到该同学信息,请联系管理员。");
				}

				message = MessageUtil.initText(toUserName, fromUserName,
						info.toString());
			}
			// 如果是事件驱动
			else if (MessageUtil.MESSAGE_EVENT.equals(msgType)) {
				String eventType = map.get("Event");
				// 如果是订阅event
				if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.subscribeText());
				}
			}
			out.print(message);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

}
