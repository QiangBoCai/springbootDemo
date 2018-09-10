package com.lance.db.vo;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResImsiVO implements Serializable {
	private static final long serialVersionUID = -2314142742688138932L;


	@Excel(name = "imsi", orderNum = "1")
	@NotBlank(message = "该字段不能为空")
	private String imsi;
	
	@Excel(name = "msisdn", orderNum = "2")
	@NotBlank(message = "该字段不能为空")
	private String msisdn;
	
	@Excel(name = "ki", orderNum = "3")
	@NotBlank(message = "该字段不能为空")
	private String ki;
	
	@Excel(name = "opc", orderNum = "4")
	@NotBlank(message = "该字段不能为空")
	private String opc;
	
	@Excel(name = "mlist", orderNum = "5")
	@NotBlank(message = "该字段不能为空")
	private String mlist;
	
	
	
	
	
}
