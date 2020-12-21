package com.qzi.cms.web.controller;

import com.qzi.cms.common.enums.RespCodeEnum;
import com.qzi.cms.common.po.SuyuanWeightPo;
import com.qzi.cms.common.po.SysEquipmentFilePo;
import com.qzi.cms.common.resp.Paging;
import com.qzi.cms.common.resp.RespBody;
import com.qzi.cms.common.util.LogUtils;
import com.qzi.cms.common.util.ToolUtils;
import com.qzi.cms.server.mapper.SuyuanWeightMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 文件管理
 * Created by Administrator on 2019/6/8.
 */


@RestController
@RequestMapping("/suyuanWeight")
public class SuyuanWeightController {


	@Resource
	private SuyuanWeightMapper suyuanWeightMapper;

	@GetMapping("/findAll")
	public RespBody findAll(Paging paging, SuyuanWeightPo suyuanWeightPo){
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

			List<SuyuanWeightPo> list =  suyuanWeightMapper.findAll1(suyuanWeightPo,startRow,pageSize);

			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", list);
			//保存分页对象
			paging.setTotalCount(suyuanWeightMapper.findCount1(suyuanWeightPo));
			respBody.setPage(paging);
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "查找文件所有设备数据失败");
			LogUtils.error("查找所有设备数据失败！",ex);
		}
		return respBody;
	}



	@ResponseBody
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public RespBody addFile(@RequestBody SuyuanWeightPo po){
		RespBody respBody = new RespBody();

		SuyuanWeightPo suyuanWeightPo = new SuyuanWeightPo();
		suyuanWeightPo.setId(ToolUtils.getUUID());
		suyuanWeightPo.setCreateTime(new Date());
		suyuanWeightPo.setState("10");
		suyuanWeightPo.setSort(po.getSort());
		suyuanWeightPo.setClasses(po.getClasses());
		suyuanWeightMapper.insert(suyuanWeightPo);
		return  respBody;
	}

	@ResponseBody
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public RespBody delete(@RequestBody SuyuanWeightPo po){
		RespBody respBody = new RespBody();

		suyuanWeightMapper.delete(po);
		return  respBody;
	}



	@GetMapping("/findAllSelect")
	public RespBody findAllSelect(SysEquipmentFilePo sysEquipmentFilePo){
		RespBody respBody = new RespBody();


		List<SuyuanWeightPo> list =  suyuanWeightMapper.findList();
		respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功",list );

		return respBody;
	}







}
