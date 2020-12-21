package com.qzi.cms.web.controller;

import com.qzi.cms.common.enums.RespCodeEnum;
import com.qzi.cms.common.po.SuyuanClassesPo;
import com.qzi.cms.common.po.SysClassesPo;
import com.qzi.cms.common.po.SysEquipmentFilePo;
import com.qzi.cms.common.resp.Paging;
import com.qzi.cms.common.resp.RespBody;
import com.qzi.cms.common.util.LogUtils;
import com.qzi.cms.common.util.ToolUtils;
import com.qzi.cms.server.mapper.SuyuanClassesMapper;
import com.qzi.cms.server.mapper.SysClassesMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 文件管理
 * Created by Administrator on 2019/6/8.
 */


@RestController
@RequestMapping("/suyuanClasses")
public class SuyuanClassesController {


	@Resource
	private SuyuanClassesMapper suyuanClassesMapper;

	@GetMapping("/findAll")
	public RespBody findAll(Paging paging, SuyuanClassesPo suyuanClassesPo){
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

			List<SuyuanClassesPo> list =  suyuanClassesMapper.findAll1(suyuanClassesPo,startRow,pageSize);

			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", list);
			//保存分页对象
			paging.setTotalCount(suyuanClassesMapper.findCount1(suyuanClassesPo));
			respBody.setPage(paging);
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "查找文件所有设备数据失败");
			LogUtils.error("查找所有设备数据失败！",ex);
		}
		return respBody;
	}



	@ResponseBody
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public RespBody addFile(@RequestBody SuyuanClassesPo po){
		RespBody respBody = new RespBody();

		SuyuanClassesPo suyuanClassesPo = new SuyuanClassesPo();
		suyuanClassesPo.setId(ToolUtils.getUUID());
		suyuanClassesPo.setCreateTime(new Date());
		suyuanClassesPo.setState("10");
		suyuanClassesPo.setSort(po.getSort());
		suyuanClassesPo.setClasses(po.getClasses());
		suyuanClassesMapper.insert(suyuanClassesPo);
		return  respBody;
	}

	@ResponseBody
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public RespBody delete(@RequestBody SuyuanClassesPo po){
		RespBody respBody = new RespBody();

		suyuanClassesMapper.delete(po);
		return  respBody;
	}



	@GetMapping("/findAllSelect")
	public RespBody findAllSelect(SysEquipmentFilePo sysEquipmentFilePo){
		RespBody respBody = new RespBody();


		List<SuyuanClassesPo> list =  suyuanClassesMapper.findList();
		respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功",list );

		return respBody;
	}







}
