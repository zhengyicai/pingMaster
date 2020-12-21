package com.qzi.cms.web.controller;

import com.qzi.cms.common.annotation.SystemControllerLog;
import com.qzi.cms.common.enums.RespCodeEnum;
import com.qzi.cms.common.po.SuyuanCardPo;
import com.qzi.cms.common.po.SuyuanUserPo;
import com.qzi.cms.common.resp.Paging;
import com.qzi.cms.common.resp.RespBody;
import com.qzi.cms.common.util.LogUtils;
import com.qzi.cms.server.mapper.SuyuanCardMapper;
import com.qzi.cms.server.mapper.SuyuanUserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文件管理
 * Created by Administrator on 2019/6/8.
 */


@RestController
@RequestMapping("/suyuanCard")
public class SuyuanCardController {

       @Resource
       private SuyuanCardMapper suyuanCardMapper;






    
       private String imagepath = "/data/page/uploadImages/";





        @GetMapping("/findAll")
    	public RespBody findAll(Paging paging, SuyuanCardPo suyuancardPo){
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

    		  List<SuyuanCardPo> list =  suyuanCardMapper.findAll(suyuancardPo,startRow,pageSize);

    			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", list);
    			//保存分页对象
    			paging.setTotalCount(suyuanCardMapper.findCount(suyuancardPo));
    			respBody.setPage(paging);
    		} catch (Exception ex) {
    			respBody.add(RespCodeEnum.ERROR.getCode(), "查找文件所有设备数据失败");
    			LogUtils.error("查找所有设备数据失败！",ex);
    		}
    		return respBody;
    	}


        @PostMapping("/update")
    	@SystemControllerLog(description="修改设备")
    	public RespBody update(@RequestBody SuyuanCardPo suyuanCardPo){
    		RespBody respBody = new RespBody();
    		try {
				//sysEquipmentFileMapper.deleteList(sysEquipmentPo.getId());
				suyuanCardMapper.update1(suyuanCardPo);
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
   	public RespBody delete(@RequestBody SuyuanCardPo suyuanCardPo){
   		RespBody respBody = new RespBody();
   		try {

			suyuanCardMapper.delete(suyuanCardPo);
   			respBody.add(RespCodeEnum.SUCCESS.getCode(), "操作成功");
   		} catch (Exception ex) {
   			respBody.add(RespCodeEnum.ERROR.getCode(), "操作失败");
   			LogUtils.error("操作失败！",ex);
   		}
   		return respBody;
   	}
   	

}
