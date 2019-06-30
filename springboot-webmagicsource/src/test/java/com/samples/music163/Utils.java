package com.samples.music163;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class Utils {
	//api
			public static void api(String a) throws Exception {
				
				String detail=a;
				a=a.split(":::")[0];
				CloseableHttpClient httpclient = HttpClients.createDefault();
				CloseableHttpResponse response = null;
				//json
				//String first_param = "{rid:\"\", offset:\"offset_param\", total:\"true\", limit:\"20\", csrf_token:\"\"}";
				String first_param = "{ids:\"["+a+"]\", br: \"320000\", csrf_token:\"\"}";
				//  first_param = first_param.replace("offset_param", offset + "");
				// 参数加密
				// 16位随机字符串，直接FFF
				// String secKey = new BigInteger(100, new SecureRandom()).toString(32).substring(0, 16);
				String secKey = "FFFFFFFFFFFFFFFF";
				// 两遍ASE加密
		            String encText = aesEncrypt(aesEncrypt(first_param, "0CoJUm6Qyw8W8jud"), secKey);
		            String encSecKey = rsaEncrypt();
		            HttpPost httpPost = new HttpPost("http://music.163.com/weapi/song/enhance/player/url?csrf_token=");
		            httpPost.addHeader("Referer","http://music.163.com/" );
		            httpPost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:58.0) Gecko/20100101 Firefox/58.0" );
					List<NameValuePair> ls = new ArrayList<NameValuePair>();
					ls.add(new BasicNameValuePair("params", encText));
					ls.add(new BasicNameValuePair("encSecKey", encSecKey));
					UrlEncodedFormEntity paramEntity = new UrlEncodedFormEntity(ls, "utf-8");
					httpPost.setEntity(paramEntity);
					response = httpclient.execute(httpPost);
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						String json=EntityUtils.toString(entity, "utf-8").toString();
						System.out.println(json);
						JSONObject jsStr = JSONObject.parseObject(json);
						String json1=jsStr.getString("data").replace("[", "").replace("]", "");
						JSONObject jsStr1 =JSONObject.parseObject(json1);
					//	System.out.println(jsStr1.getString("url"));
					//	list2.add(detail+":::"+jsStr1.getString("url"));
					}
						response.close();
						httpclient.close();
				}
				/**
				 * ASE-128-CBC加密模式可以需要16位
				 *
				 * @param src 加密内容
				 * @param key 密钥
				 * @return
				 */
		        public static String aesEncrypt(String src, String key) throws Exception {
		            String encodingFormat = "UTF-8";
		            String iv = "0102030405060708";
		            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		            byte[] raw = key.getBytes();
		            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
		            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
		         // 使用CBC模式，需要一个向量vi，增加加密算法强度
		            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
		            byte[] encrypted = cipher.doFinal(src.getBytes(encodingFormat));
		            Encoder encode = Base64.getEncoder();
		            return new String(encode.encode((encrypted)));
		        }
		        public static String rsaEncrypt() {
		            String secKey = "257348aecb5e556c066de214e531faadd1c55d814f9be95fd06d6bff9f4c7a41f831f6394d5a3fd2e3881736d94a02ca919d952872e7d0a50ebfa1769a7a62d512f5f1ca21aec60bc3819a9c3ffca5eca9a0dba6d6f7249b06f5965ecfff3695b54e1c28f3f624750ed39e7de08fc8493242e26dbc4484a01c76f739e135637c";
		            return secKey;
		        }
	
}

