package com.qzi.cms.web.controller;

import com.qzi.cms.common.annotation.SystemControllerLog;
import com.qzi.cms.common.enums.RespCodeEnum;
import com.qzi.cms.common.po.SuyuanUserPo;
import com.qzi.cms.common.po.SysEquipmentFilePo;
import com.qzi.cms.common.po.SysEquipmentPo;
import com.qzi.cms.common.resp.Paging;
import com.qzi.cms.common.resp.RespBody;
import com.qzi.cms.common.util.CardNoUtils;
import com.qzi.cms.common.util.LogUtils;
import com.qzi.cms.common.util.ToolUtils;
import com.qzi.cms.common.vo.SysEquipmentFileVo;
import com.qzi.cms.common.vo.SysEquipmentVo;
import com.qzi.cms.common.vo.SysFileUrlVo;
import com.qzi.cms.server.mapper.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 文件管理
 * Created by Administrator on 2019/6/8.
 */


@RestController
@RequestMapping("/suyuanUser")
public class SuyuanUserController {

       @Resource
       private SuyuanUserMapper suyuanUserMapper;






    
       private String imagepath = "/data/page/uploadImages/";





        @GetMapping("/findAll")
    	public RespBody findAll(Paging paging, SuyuanUserPo suyuanUserPo){
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

    		  List<SuyuanUserPo> list =  suyuanUserMapper.findAll(suyuanUserPo,startRow,pageSize);

    			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", list);
    			//保存分页对象
    			paging.setTotalCount(suyuanUserMapper.findCount(suyuanUserPo));
    			respBody.setPage(paging);
    		} catch (Exception ex) {
    			respBody.add(RespCodeEnum.ERROR.getCode(), "查找文件所有设备数据失败");
    			LogUtils.error("查找所有设备数据失败！",ex);
    		}
    		return respBody;
    	}


        @PostMapping("/update")
    	@SystemControllerLog(description="修改设备")
    	public RespBody update(@RequestBody SuyuanUserPo suyuanUserPo){
    		RespBody respBody = new RespBody();
    		try {
				//sysEquipmentFileMapper.deleteList(sysEquipmentPo.getId());
				suyuanUserMapper.update1(suyuanUserPo);

                //sysEquipmentFileMapper.updateEquipmentState("10",sysEquipmentPo.getEquipmentNo());
    			respBody.add(RespCodeEnum.SUCCESS.getCode(), "操作成功");
    		} catch (Exception ex) {
    			respBody.add(RespCodeEnum.ERROR.getCode(), "操作失败");
    			LogUtils.error("操作失败！",ex);
    		}
    		return respBody;
    	}







	


	@PostMapping("/delete")
   	@SystemControllerLog(description="删除设备")
   	public RespBody delete(@RequestBody SuyuanUserPo sysEquipmentPo){
   		RespBody respBody = new RespBody();
   		try {

               suyuanUserMapper.delete(sysEquipmentPo);
   			respBody.add(RespCodeEnum.SUCCESS.getCode(), "操作成功");
   		} catch (Exception ex) {
   			respBody.add(RespCodeEnum.ERROR.getCode(), "操作失败");
   			LogUtils.error("操作失败！",ex);
   		}
   		return respBody;
   	}


}
