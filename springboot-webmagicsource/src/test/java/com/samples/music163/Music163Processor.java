package com.samples.music163;

import com.lance.selenium.mashi.MaShiPageProcessor;
import com.lance.selenium.mashi.MaShiPipeline;
import com.lance.webmagic.downloader.selenium.SeleniumDownloader;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

@Slf4j
public class Music163Processor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);
	@Override
	public void process(Page page) {
		
		
		//1.API
		//http://music.163.com/#/song?id=484730184 
		
		//2.Network
		//https://music.163.com/weapi/feedback/weblog?csrf_token=
		//Content-Type:application/x-www-form-urlencoded
		/*
		 * 3.Network&Formdata
		params:mPYFYh8+EPMY8/Dxk951xhHnQTSauW51orev4IA9zSRyAXrvL/1UDdT4F0vDvw+cazCvI+/P9FGvoKSV6Clj43DPlenDtDxDJIxUkkPn9xD22+cOiH3Px9Fm8sXLEkz63QvcSRP2XNZEM46l8olEZdDkBjOhc0/z3w6VG6ZNITcjfxJACm7RJ5tsHQWQVPMlqQlr1pUCN+iUCh8rub8eHdarxi2tQeGDprxYIkzE4sesXrIR8oyYpVH+xN2OSK2Mk0Cd+RzIlf4qrt4rDSnMQrFb0GMt03Sh0eIcKvs/ec3dh1Wa7JTC1JITDgSphJ5r9DDwnmnxh0F8c9mW2BhsHaLbgyYtpnGQ8fsC9sHsxtR2PcpW0bowcTm8Ab/rlcxRfTgu+wOa7+wsIxOC9rXUmqiXpFNm48YVYlMrJDeLPY2cSy9LAbQuwu+0ydr7QRHEo5vfQ2/TAV2PAKib0BGuY1zdwoXUacJGZidLd3fB3Pvzjo1DlcY0dBnw/KIcvrtERhB3002s/rORGjxnU9x4/qYgXUFwZQWLTvLHZHv1R7vOEKTtB1q3cQKp7l/cpzEfHmnb2m0ad4iFc1v5SW85o9XWApInNpEVIFtFqjiBf87J/It7l9omXj9eGzFzZqdWcWMpKuybyMVCrdqIEXO6jw==
		encSecKey:7455e79e445e6d96b6742aa33a929cfde6f55a05cd0385521998abdcd358f1f32b8e718a6ccb13a099e1c4d1bef0c93b8b2fea8c52616e8cb0a4deb1552640238d9179e02acf7378900280d11c150c23bd965afea56af74d4434ea657e3abe3844c2ebb33e3cc0700208e5d745a9898422c37140708ac1421e61685f996cdb8b
		 */
		
		//4.Source find 参数
		//js加密你只需要认识 rsa aes md5 bas64
		//<script src="//s3.music.126.net/web/s/core_0f23ce45bdbb770e926f565f09f3128b.js?0f23ce45bdbb770e926f565f09f3128b" type="text/javascript"></script><script src="//s3.music.126.net/web/s/pt_frame_index_f069c1c332eebe4444ee0e8cd75049b1.js?f069c1c332eebe4444ee0e8cd75049b1" type="text/javascript"></script>

		/*
		   e4i.data = k4o.cB5G({
                params: bVq1x.encText,
                encSecKey: bVq1x.encSecKey
            })
		 * */
		//5.Control 断点打印解析参数和方法，环境加密算法，对应编写解密算法
		//var bVq1x = window.asrsea(JSON.stringify(i4m), bry4C(["流泪", "强"]), bry4C(RO6I.md), bry4C(["爱心", "女孩", "惊恐", "大笑"]));
		/*
		 * (参数1:包含了歌曲id 484730184)JSON.stringify(i4m)>>>>>"{"logs":"[{\"action\":\"sysaction\",\"json\":{\"dataType\":\"cdnCompare\",\"cdnType\":\"xy\",\"loadeddataTime\":312,\"resourceType\":\"audio\",\"resourceId\":484730184,\"resourceUrl\":\"https://m10.music.126.net/20181203162150/9eb6691f2c962bdb0f1698cbbf115eaa/ymusic/282c/9830/fddb/4aef2045c2458281e29d8664e3654b0f.mp3\",\"xySupport\":true,\"error\":false,\"errorType\":\"\"}}]","csrf_token":""}"
		 	{"id":"1306400549","c":"[{\"id\":\"1306400549\"}]","csrf_token":""}"
		 * bry4C(["流泪", "强"])>>>>>"010001"
		 * bry4C(RO6I.md)>>>>>"00e0b509f6259df8642dbc35662901477df22677ec152b5ff68ace615bb7b725152b3ab17a876aea8a5aa76d2e417629ec4ee341f56135fccf695280104e0312ecbda92557c93870114af6c9d05c4f7f0c3685b7a46bee255932575cce10b424d813cfe4875d3e82047b97ddef52741d546b8e289dc6935b3ece0462db0a22b8e7"
		 * bry4C(["爱心", "女孩", "惊恐", "大笑"])>>>>>"0CoJUm6Qyw8W8jud"
			function d(d, e, f, g) {// window.asrsea 方法：参数params是经过两次aes加密得到的，而encSecKey是RSA加密所得。并且都使用到了a方法产生的随机数
			    var h = {}
			      , i = a(16);//①随机数>>可以设置为固定
			    h.encText = b(d, g),//②固定id+固定的0CoJUm6Qyw8W8jud> 第一次加密固定h.encText结果
			    h.encText = b(h.encText, i),第一次加密固定h.encText结果
			    h.encSecKey = c(i, e, f),
			    return h;
			}
			function a(a) {//产生16位随机数
			        var d, e, b = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", c = "";
			    for (d = 0; a > d; d += 1)
			        e = Math.random() * b.length,
			        e = Math.floor(e),
			        c += b.charAt(e);
			    return c
			}
			function b(a, b) {//AES加密
			    var c = CryptoJS.enc.Utf8.parse(b)//歌曲id
			      , d = CryptoJS.enc.Utf8.parse("0102030405060708")
			      , e = CryptoJS.enc.Utf8.parse(a)
			      , f = CryptoJS.AES.encrypt(e, c, {
			        iv: d,
			        mode: CryptoJS.mode.CBC
			    });
			    return f.toString()
			}
			function c(a, b, c) {//RSA加密
			    var d, e;
			    return setMaxDigits(131),
			    d = new RSAKeyPair(b,"",c),
			    e = encryptedString(d, a)
			}
 
		 * */
		
		
	}

	@Override
	public Site getSite() {
		return site;
	}

	
	
	public static void main(String[] args) 
	{			//创建爬虫
		Spider.create(new Music163Processor())
		 	   //从这里开始抓取
		 	   .addUrl("https://mart.coding.net/projects")
		 	   //保存结果
		 	   .setDownloader(new SeleniumDownloader("D:/tools/chromedriver.exe"))
		 	   .addPipeline(new MaShiPipeline())
		 	   //开启5个线程抓取
		 	   .thread(2)
		 	   //启动爬虫
		 	   .run();
		
	}
}
