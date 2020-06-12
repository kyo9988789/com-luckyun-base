package com.luckyun.base.oss.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luckyun.core.data.filter.AuthFilter;
import com.luckyun.core.data.filter.AuthInfo;
import com.luckyun.core.tool.OssPathHelperUtils;

@Controller
public class OssHelperController {
	
	@Autowired
	ObjectFactory<HttpSession> httpSessionFactory;
	
	@Autowired
	private OssPathHelperUtils ossPathHelperUtils;

	@RequestMapping("/show/img/read")
	public void showImg(HttpServletResponse response,@RequestParam("sprojectno") String sprojectno
			,@RequestParam("uuidkey") String uuidkey) throws IOException {
		response.sendRedirect(OssPathHelperUtils.ossPrefixUrl +"/oss/show/img?sprojectno="+sprojectno+"&uuidkey="+uuidkey);
	}
	
	@RequestMapping("/show/img/oss/read")
	public String showImgApi(HttpServletResponse response,@RequestParam("sprojectno") String sprojectno
			,@RequestParam("filepath") String filepath,@RequestParam(value="prefix",defaultValue = "/api") String prefix) 
					throws IOException {
		String redurl = prefix + OssPathHelperUtils.ossPrefixUrl 
				+"/oss/show/img?sprojectno="+sprojectno+"&filepath="+filepath;
		return "redirect:"+redurl;
	}
	
	@RequestMapping("/down/load/oss/file")
	public void downloadFile(HttpServletResponse response,@RequestParam("sprojectno") String sprojectno
			,@RequestParam("filepath") String filepath,@RequestParam(value="prefix",defaultValue = "/api") String prefix
			,@RequestParam(value = "filename") String filename) throws IOException {
		response.sendRedirect(prefix + OssPathHelperUtils.ossPrefixUrl 
				+"/oss/download/file?sprojectno="+sprojectno+"&filepath="+filepath+"&filename="+filename);
	}
	
	@RequestMapping("/test/generate/file")
	@ResponseBody
	public String testGenerateUrl(@RequestParam("filepath") String filepath,@RequestParam(required = false) String filename) {
		AuthInfo authInfo = getAuthInfo();
		String result = ossPathHelperUtils.generatePdfPreview(authInfo.getSloginid(), "njmis", filepath,filename);
		return result;
	}
	
	public AuthInfo getAuthInfo() {
		return (AuthInfo) httpSessionFactory.getObject().getAttribute(AuthFilter.AUTH_SESSION_KEY);
	}
	
}
