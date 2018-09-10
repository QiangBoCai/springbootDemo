package com.lance.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cglib.beans.BeanMap;

import com.alibaba.fastjson.JSONObject;


public class PostFile
{
  public static String post(String actionUrl, Map<String, String> params, Map<String, File> files)
    throws IOException
  {
    DataOutputStream outStream = null;
    Map resultMap = new HashMap();
    try {
      String BOUNDARY = UUID.randomUUID().toString();
      String PREFIX = "--"; String LINEND = "\r\n";
      String MULTIPART_FROM_DATA = "multipart/form-data";
      String CHARSET = "UTF-8";

      URL uri = new URL(actionUrl);
      HttpURLConnection conn = (HttpURLConnection)uri.openConnection();

      conn.setReadTimeout(5000);
      conn.setDoInput(true);
      conn.setDoOutput(true);
      conn.setUseCaches(false);
      conn.setRequestMethod("POST");
      conn.setRequestProperty("connection", "keep-alive");
      conn.setRequestProperty("Charsert", "UTF-8");
      conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) abugong  manongdoudou AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.73 Safari/537.36");

      conn.setRequestProperty("Content-Type", new StringBuilder().append(MULTIPART_FROM_DATA).append(";boundary=").append(BOUNDARY).toString());

      StringBuilder sb = new StringBuilder();
      for (Map.Entry entry : params.entrySet()) {
        sb.append(PREFIX);
        sb.append(BOUNDARY);
        sb.append(LINEND);
        sb.append(new StringBuilder().append("Content-Disposition: form-data; name=\"").append((String)entry.getKey()).append("\"").append(LINEND).toString());
        sb.append(new StringBuilder().append("Content-Type: text/plain; charset=").append(CHARSET).append(LINEND).toString());
        sb.append(new StringBuilder().append("Content-Transfer-Encoding: 8bit").append(LINEND).toString());
        sb.append(LINEND);
        sb.append((String)entry.getValue());
        sb.append(LINEND);
      }

      outStream = new DataOutputStream(conn.getOutputStream());
      outStream.write(sb.toString().getBytes());

      if (files != null) {
        for (Map.Entry file : files.entrySet()) {
          StringBuilder sb1 = new StringBuilder();
          sb1.append(PREFIX);
          sb1.append(BOUNDARY);
          sb1.append(LINEND);
          sb1.append(new StringBuilder().append("Content-Disposition: form-data; name=\"file\"; filename=\"").append((String)file.getKey()).append("\"").append(LINEND).toString());

          sb1.append(new StringBuilder().append("Content-Type: application/octet-stream; charset=").append(CHARSET).append(LINEND).toString());
          sb1.append(LINEND);
          outStream.write(sb1.toString().getBytes());

          InputStream is = new FileInputStream((File)file.getValue());
          byte[] buffer = new byte[1024];
          int len = 0;
          while ((len = is.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
          }

          is.close();
          outStream.write(LINEND.getBytes());
        }
      }

      byte[] end_data = new StringBuilder().append(PREFIX).append(BOUNDARY).append(PREFIX).append(LINEND).toString().getBytes();
      outStream.write(end_data);
      outStream.flush();

      int res = conn.getResponseCode();
      InputStream in = conn.getInputStream();
      InputStreamReader isReader = new InputStreamReader(in);

      BufferedReader bufReader = new BufferedReader(isReader);

      String line = null;
      String data = "OK";
      while ((line = bufReader.readLine()) == null)
        data = new StringBuilder().append(data).append(line).toString();
      StringBuilder sb2 = new StringBuilder();
      int ch;
      if (res == 200) {
        ch = 0;
        while ((ch = in.read()) != -1) {
          sb2.append((char)ch);
        }
      }
      resultMap.put("data", data);
      resultMap.put("responsecode", Integer.valueOf(res));
      resultMap.put("data2", sb2.toString());
      conn.disconnect();
      return data;
    } finally {
      if (outStream != null)
        outStream.close();
    }
  }
  
  
  public static void main(String[] args){
	    Map inVaues = new HashMap();
	    inVaues.put("projectId", "0f2a31dc-29ef-496b-9ad2-1c74659f597b");
	    inVaues.put("orgNumber", "001");
	    inVaues.put("beginDT", "2018-07-06");
	    inVaues.put("endDT", "2018-07-06");
	    inVaues.put("workFlag", "在职");
	    inVaues.put("keyWords","");
	    inVaues.put("page", "1");
	    inVaues.put("rows", "50");
	    inVaues.put("personnelProjectId", "d92a53a4-a6a1-44b5-b60e-a8550bf5f7f5");
	    JSONObject json = new JSONObject();
	    json.put("projectId", "0f2a31dc-29ef-496b-9ad2-1c74659f597b");
	    json.put("orgNumber", "001");
	    json.put("beginDT", "2018-07-06");
	    json.put("endDT", "2018-07-06");
	    json.put("workFlag", "在职");
	    json.put("keyWords","");
	    json.put("page", "1");
	    json.put("rows", "50");
	    json.put("personnelProjectId", "d92a53a4-a6a1-44b5-b60e-a8550bf5f7f5");
	    
	    String postJsonStr="["+json.toString()+"]";
	    
	    Map<String, File> files = new HashMap<String,File>();
	    try {
	    //  String result=	HttpClientUtil.doPost(postJsonStr, "https://am.abugong.com/projectPersonnelAttendancLog/dataGrid");
	    	  JSONObject jsonResult1 =	HttpClientUtils.httpPost("http://47.104.170.240:20010/springboot-helloworld", json);
	    	// String result = post("https://am.abugong.com/projectPersonnelAttendancLog/dataGrid", inVaues, files);
	      //JSONObject jsonResult = JSONObject.parseObject(result);
	      System.out.println("1"+jsonResult1.toJSONString());
	    }
	    catch (Exception e) {
	    	System.out.println("2"+e.getLocalizedMessage());
	    }
  }
}