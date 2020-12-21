package com.qzi.cms.web.controller;

import com.qzi.cms.common.annotation.SystemControllerLog;
import com.qzi.cms.common.enums.RespCodeEnum;
import com.qzi.cms.common.po.SysClassesPo;
import com.qzi.cms.common.po.SysEquipmentFilePo;
import com.qzi.cms.common.po.SysEquipmentPo;
import com.qzi.cms.common.po.SysFileUrlPo;
import com.qzi.cms.common.resp.Paging;
import com.qzi.cms.common.resp.RespBody;
import com.qzi.cms.common.util.LogUtils;
import com.qzi.cms.common.util.ToolUtils;
import com.qzi.cms.common.vo.SysEquipmentFileVo;
import com.qzi.cms.common.vo.SysEquipmentVo;
import com.qzi.cms.common.vo.SysFileUrlVo;
import com.qzi.cms.server.mapper.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.tools.Tool;
import java.util.Date;
import java.util.List;

/**
 * 文件管理
 * Created by Administrator on 2019/6/8.
 */


@RestController
@RequestMapping("/sysClasses")
public class SysClassesController {


	@Resource
	private SysClassesMapper sysClassesMapper;

	@GetMapping("/findAll")
	public RespBody findAll(Paging paging, SysClassesPo sysClassesPo){
		RespBody respBody = new RespBody();


		int startRow=0;int pageSize=0;
		if(null!=paging){
			startRow=(paging.getPageNumber()>0)?(paging.getPageNumber()-1)*paging.getPageSize():0;
			pageSize=paging.getPageSize();
		}else{
			pageSize=Integer.MAX_VALUE;
		}
		try {
			//保存返回数据

			List<SysClassesPo> list =  sysClassesMapper.findAll1(sysClassesPo,startRow,pageSize);

			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", list);
			//保存分页对象
			paging.setTotalCount(sysClassesMapper.findCount1(sysClassesPo));
			respBody.setPage(paging);
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "查找文件所有设备数据失败");
			LogUtils.error("查找所有设备数据失败！",ex);
		}
		return respBody;
	}



	@ResponseBody
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public RespBody addFile(@RequestBody SysClassesPo po){
		RespBody respBody = new RespBody();

		SysClassesPo sysClassesPo = new SysClassesPo();
		sysClassesPo.setId(ToolUtils.getUUID());
		sysClassesPo.setCreateTime(new Date());
		sysClassesPo.setState("10");
		sysClassesPo.setSort(po.getSort());
		sysClassesPo.setClasses(po.getClasses());
		sysClassesMapper.insert(sysClassesPo);
		return  respBody;
	}



	@GetMapping("/findAllSelect")
	public RespBody findAllSelect(SysEquipmentFilePo sysEquipmentFilePo){
		RespBody respBody = new RespBody();


		List<SysClassesPo> list =  sysClassesMapper.findList();
		respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功",list );

		return respBody;
	}







}
