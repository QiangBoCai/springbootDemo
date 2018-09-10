package com.lance.client;


import com.lance.utils.HttpClientSoapUtil;


public class HttpClientTest {
	public static void main(String[] args){
		String wsdl = "http://localhost:8088/CommonService?wsdl";
		String SOAPAction = "urn:SimpleInOutMessageReceiver";

		//构造请求报文
		StringBuilder soapReq = new StringBuilder();
		soapReq.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		soapReq.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		soapReq.append("<soapenv:Body>");
		soapReq.append("<UNI_BSS>");
		soapReq.append("<UNI_HEAD>");
		soapReq.append("<TRAN_ID>1111111111111</TRAN_ID>");
		soapReq.append("<ORIG_DOMAIN>263</ORIG_DOMAIN>");
		soapReq.append("<SERVICE_NAME>263_PayFee</SERVICE_NAME>");
		soapReq.append("<ACTION_CODE>0</ACTION_CODE>");
		soapReq.append("</UNI_HEAD>");
		soapReq.append("<UNI_BODY>");
		soapReq.append("<transactionId></transactionId>");
		soapReq.append("<acc_nbr></acc_nbr>");
		soapReq.append("<charge_code></charge_code>");
		soapReq.append("<fee></fee>");
		soapReq.append("<expireDate></expireDate>");
		soapReq.append("<iKey></iKey>");
		soapReq.append("<token></token>");
		soapReq.append("</UNI_BODY>");
		soapReq.append("</UNI_BSS>");
		soapReq.append("</soapenv:Body>");
		soapReq.append("</soapenv:Envelope>");
		
		//httpclient发送soap请求
		System.out.println("HttpClient 发送 SOAP请求");
		HttpClientSoapUtil.doPostSoap1_1(wsdl, soapReq.toString(), SOAPAction);
	}
}
