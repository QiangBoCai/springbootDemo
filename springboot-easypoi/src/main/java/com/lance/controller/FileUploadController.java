package com.lance.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lance.db.utils.EasyPOIUtils;
import com.lance.db.vo.ResImsiVO;

@Controller

public class FileUploadController {

	   private Logger logger = LoggerFactory.getLogger(getClass());

	   @Value("${files.path}")
	   private String filePath;
	   
       @RequestMapping("/")
       public String index(){
            return"/index";

       }
       
       
       @RequestMapping("file")
       public String file(){
            return"/file";

       }
       /**
        * 实现文件上传
        * */
       @RequestMapping("fileUpload")
       public String fileUpload(@RequestParam("fileName") MultipartFile file ,ModelMap modelMap){
           if(file.isEmpty()){
        	   modelMap.put("result", "未选择任何文件");
               return "file";
           }
           
           
           String fileName = file.getOriginalFilename();
           int size = (int) file.getSize();
           logger.debug("filePath:"+filePath);
           logger.debug(fileName + "-->" + size);
           
           File dest = new File(filePath + "/" + fileName);
           if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
               dest.getParentFile().mkdir();
           }
           try {
               file.transferTo(dest); //保存文件
               modelMap.put("result", "上传成功");
                try {
				Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
               List<ResImsiVO> resImsiList = EasyPOIUtils.importExcel(dest.getPath(),0,0,ResImsiVO.class);
               logger.debug("dest.getpath:"+dest.getPath());
               for(ResImsiVO resImsiVO: resImsiList)
               {
                   logger.debug(resImsiVO.getImsi()+"|"
                		   +resImsiVO.getKi()+"|"
                		   +resImsiVO.getOpc()+"|"
                		   +resImsiVO.getMsisdn()+"|"
                		   +resImsiVO.getMlist()+"|");
               }

               return "file";
           } catch (IllegalStateException e) {
        	   modelMap.put("result", "上传失败");
        	   e.printStackTrace();
               return "file";
           } catch (IOException e) {
        	   modelMap.put("result", "上传失败");
        	   e.printStackTrace();
               return "file";
           }
       }

      

       

}    