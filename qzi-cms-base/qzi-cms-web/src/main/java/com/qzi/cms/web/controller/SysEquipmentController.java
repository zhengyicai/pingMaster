package com.qzi.cms.web.controller;

import com.qzi.cms.common.annotation.SystemControllerLog;
import com.qzi.cms.common.enums.RespCodeEnum;
import com.qzi.cms.common.po.SysEquipmentFilePo;
import com.qzi.cms.common.po.SysEquipmentPo;
import com.qzi.cms.common.po.SysFileUrlPo;
import com.qzi.cms.common.resp.Paging;
import com.qzi.cms.common.resp.RespBody;
import com.qzi.cms.common.util.CardNoUtils;
import com.qzi.cms.common.util.LogUtils;
import com.qzi.cms.common.util.ToolUtils;
import com.qzi.cms.common.vo.SysEquipmentFileVo;
import com.qzi.cms.common.vo.SysEquipmentVo;
import com.qzi.cms.common.vo.SysFileUrlVo;
import com.qzi.cms.server.mapper.SysEquipmentFileMapper;
import com.qzi.cms.server.mapper.SysEquipmentMapper;
import com.qzi.cms.server.mapper.SysFileUrlMapper;
import com.qzi.cms.server.mapper.SysParameterMapper;
import org.springframework.jdbc.core.ParameterMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 文件管理
 * Created by Administrator on 2019/6/8.
 */


@RestController
@RequestMapping("/sysEquipment")
public class SysEquipmentController {

       @Resource
       private SysEquipmentMapper sysEquipmentMapper;


       @Resource
	   private SysEquipmentFileMapper sysEquipmentFileMapper;

	   @Resource
	   private SysFileUrlMapper sysFileUrlMapper;

	   @Resource
	   private SysParameterMapper parameterMapper;




    
       private String imagepath = "/data/page/uploadImages/";



       @ResponseBody
       @RequestMapping(value = "/addFile",method = RequestMethod.POST)
       public RespBody addFile(@RequestBody SysEquipmentPo po){
           RespBody respBody = new RespBody();

		   SysEquipmentPo po2 =  sysEquipmentMapper.selectOne1(po.getEquipmentNo());
		   if(po2!=null){
			   respBody.add(RespCodeEnum.ERROR.getCode(), "该设备号已经存在");
		   	 return  respBody;
		   }


           SysEquipmentPo sysEquipmentPo = new SysEquipmentPo();

		   sysEquipmentPo.setId(ToolUtils.getUUID());
		   sysEquipmentPo.setCreateTime(new Date());
		   sysEquipmentPo.setState(po.getState());
		   sysEquipmentPo.setType(po.getType());
		   sysEquipmentPo.setEquipmentName1(po.getEquipmentName1());
		   sysEquipmentPo.setEquipmentNo(po.getEquipmentNo());
		   sysEquipmentPo.setRemark(po.getRemark());
		   sysEquipmentPo.setUserId(po.getUserId());
		   sysEquipmentPo.setUserName(po.getUserName());
		   sysEquipmentPo.setStatus("20");

		   //每页显示的商品数
		   sysEquipmentPo.setShowCount("18");
		   sysEquipmentPo.setShowMin("5");
		   sysEquipmentPo.setImgUrl("");
		   //sysEquipmentPo.setTitleStatus("20");
		   //sysEquipmentPo.setTitleDetail("");
//		   sysEquipmentPo.setRunTime(po.getRunTime());
//		   sysEquipmentPo.setUpdateTimes(po.getUpdateTimes());
		   sysEquipmentMapper.insert(sysEquipmentPo);
            


           return  respBody;
       }





        @GetMapping("/findAll")
    	public RespBody findAll(Paging paging,SysEquipmentPo sysEquipmentPo){
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

    		  List<SysEquipmentVo> list =  sysEquipmentMapper.findAll(sysEquipmentPo,startRow,pageSize);

    			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", list);
    			//保存分页对象
    			paging.setTotalCount(sysEquipmentMapper.findCount(sysEquipmentPo));
    			respBody.setPage(paging);
    		} catch (Exception ex) {
    			respBody.add(RespCodeEnum.ERROR.getCode(), "查找文件所有设备数据失败");
    			LogUtils.error("查找所有设备数据失败！",ex);
    		}
    		return respBody;
    	}


        @PostMapping("/update")
    	@SystemControllerLog(description="修改设备")
    	public RespBody update(@RequestBody SysEquipmentPo sysEquipmentPo){
    		RespBody respBody = new RespBody();
    		try {
				//sysEquipmentFileMapper.deleteList(sysEquipmentPo.getId());
                sysEquipmentMapper.updateByPrimaryKey(sysEquipmentPo);
                sysEquipmentFileMapper.updateEquipmentState("10",sysEquipmentPo.getEquipmentNo());
    			respBody.add(RespCodeEnum.SUCCESS.getCode(), "操作成功");
    		} catch (Exception ex) {
    			respBody.add(RespCodeEnum.ERROR.getCode(), "操作失败");
    			LogUtils.error("操作失败！",ex);
    		}
    		return respBody;
    	}



    @PostMapping("/updateList")
    @SystemControllerLog(description="修改设备")
    public RespBody updateList(@RequestBody SysEquipmentPo sysEquipmentPo){
        RespBody respBody = new RespBody();
        try {
            //sysEquipmentFileMapper.deleteList(sysEquipmentPo.getId());
            sysEquipmentMapper.updateByPrimaryKey(sysEquipmentPo);


            SysEquipmentPo sysEquipmentPo1 = sysEquipmentMapper.findOne(sysEquipmentPo.getEquipmentNo());

            List<SysFileUrlVo> fileList =   sysFileUrlMapper.findAllList1(sysEquipmentPo1.getUserId());



            Integer count1 = sysFileUrlMapper.findCount1(sysEquipmentPo1.getUserId());


            if(fileList.size()>0){


            	sysEquipmentFileMapper.deleteListAll(sysEquipmentPo1.getId());

                for(int b=0;b<fileList.size();b++){

                    SysEquipmentFilePo sysEquipmentFilePo = new SysEquipmentFilePo();
                    sysEquipmentFilePo.setId(ToolUtils.getUUID());
                    sysEquipmentFilePo.setCreateTime(new Date());
                    sysEquipmentFilePo.setState("20");
                    sysEquipmentFilePo.setType(sysEquipmentPo1.getType());
                    sysEquipmentFilePo.setAlign("10");
                    sysEquipmentFilePo.setEquipmentId(sysEquipmentPo1.getId());
                    sysEquipmentFilePo.setEquipmentNo(sysEquipmentPo1.getEquipmentNo());
                    sysEquipmentFilePo.setSort(""+count1);
                    sysEquipmentFilePo.setFileId(fileList.get(b).getFileUrl());
                    sysEquipmentFilePo.setTitleDetail("");
                    sysEquipmentFilePo.setPrice("");
                    sysEquipmentFilePo.setPrice1("");
                    sysEquipmentFilePo.setName(fileList.get(b).getRemark());
                    sysEquipmentFilePo.setRemark(fileList.get(b).getRemark());
                    sysEquipmentFilePo.setUserId(fileList.get(b).getUserId());
                    sysEquipmentFilePo.setUpdateState("10");
                    sysEquipmentFilePo.setUnit("1斤");
                    count1++;

                    sysEquipmentFileMapper.insert(sysEquipmentFilePo);
                }

            }







            respBody.add(RespCodeEnum.SUCCESS.getCode(), "操作成功");
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "操作失败");
            LogUtils.error("操作失败！",ex);
        }
        return respBody;
    }


	@PostMapping("/updateSelect")
	@SystemControllerLog(description="修改选中设备")
	public RespBody updateSelect(@RequestBody SysEquipmentVo sysEquipmentVo){
		RespBody respBody = new RespBody();
		try {



			for(int i = 0 ;i<sysEquipmentVo.getEquipmentIds().length;i++){

				sysEquipmentFileMapper.deleteList(sysEquipmentVo.getId());
				sysEquipmentMapper.updateOne(sysEquipmentVo.getEquipmentIds()[i],sysEquipmentVo.getType());
			}
//			sysEquipmentFileMapper.deleteListSelect(sysEquipmentVo.getUserId());
//			sysEquipmentMapper.updateSelect(sysEquipmentVo.getUserId(),sysEquipmentVo.getType());
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "操作成功");
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "操作失败");
			LogUtils.error("操作失败！",ex);
		}
		return respBody;
	}



	@PostMapping("/updateSelectAll")
		@SystemControllerLog(description="修改选中设备")
		public RespBody updateSelectAll(@RequestBody SysEquipmentVo sysEquipmentVo){
			RespBody respBody = new RespBody();
			try {
				sysEquipmentFileMapper.deleteListSelect(sysEquipmentVo.getUserId());
				sysEquipmentMapper.updateSelect(sysEquipmentVo.getUserId(),sysEquipmentVo.getType());
				respBody.add(RespCodeEnum.SUCCESS.getCode(), "操作成功");
			} catch (Exception ex) {
				respBody.add(RespCodeEnum.ERROR.getCode(), "操作失败");
				LogUtils.error("操作失败！",ex);
			}
			return respBody;
		}

	


	@PostMapping("/delete")
   	@SystemControllerLog(description="删除设备")
   	public RespBody delete(@RequestBody SysEquipmentPo sysEquipmentPo){
   		RespBody respBody = new RespBody();
   		try {
			sysEquipmentFileMapper.deleteList(sysEquipmentPo.getId());
               sysEquipmentMapper.delete(sysEquipmentPo);
   			respBody.add(RespCodeEnum.SUCCESS.getCode(), "操作成功");
   		} catch (Exception ex) {
   			respBody.add(RespCodeEnum.ERROR.getCode(), "操作失败");
   			LogUtils.error("操作失败！",ex);
   		}
   		return respBody;
   	}



	    @GetMapping("/equipmentFile/findAll")
    	public RespBody equipmentFileAll(SysEquipmentFilePo sysEquipmentFilePo){
    		RespBody respBody = new RespBody();

			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", sysEquipmentFileMapper.findAll(sysEquipmentFilePo.getEquipmentId()));

    		return respBody;
    	}

	    @GetMapping("/equipmentFile/findOne")
    	public RespBody equipmentFilefindOne(SysEquipmentFilePo sysEquipmentFilePo){
    		RespBody respBody = new RespBody();

			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", sysEquipmentFileMapper.findOne(sysEquipmentFilePo.getEquipmentId(),sysEquipmentFilePo.getFileId()));

    		return respBody;
    	}


	/**
	 * 获取设备列表
	 * @param sysEquipmentPo
	 * @return
	 */

	@GetMapping("/equipmentFile/findList")
	public RespBody equipmentFilefindList(SysEquipmentPo sysEquipmentPo){
		RespBody respBody = new RespBody();

		respBody.add(RespCodeEnum.SUCCESS.getCode(), "获取数据成功",sysEquipmentMapper.listAll(sysEquipmentPo.getUserId()));

		return respBody;
	}


	/***
	 * 获取绑定的图片
	 * @param sysEquipmentPo
	 * @return
	 */

	@GetMapping("/equipmentFile/selectFindList")
	public RespBody equipmentFileselectFindList(SysEquipmentPo sysEquipmentPo){
		RespBody respBody = new RespBody();

		respBody.add(RespCodeEnum.SUCCESS.getCode(), "获取数据成功",sysEquipmentFileMapper.findAll(sysEquipmentPo.getId()));

		return respBody;
	}


	/**
	 * 获取所有的图片
	 * @param sysEquipmentPo
	 * @return
	 */
	@GetMapping("/equipmentFile/findImage")
	public RespBody equipmentFilefindImage(SysEquipmentPo sysEquipmentPo){
		RespBody respBody = new RespBody();


		respBody.add(RespCodeEnum.SUCCESS.getCode(), "获取数据成功",sysFileUrlMapper.findAllList1(sysEquipmentPo.getUserId()));

		return respBody;
	}












	@PostMapping("/equipmentFile/updateTitle")
	@SystemControllerLog(description="修改参数")
	public RespBody updateTitle(@RequestBody SysEquipmentFileVo sysEquipmentFileVo){
		RespBody respBody = new RespBody();
		try {
			sysEquipmentFileMapper.updateTitle(sysEquipmentFileVo.getEquipmentId(),sysEquipmentFileVo.getFileId(),sysEquipmentFileVo.getTitleDetail());
			sysEquipmentMapper.updateTitleOne(sysEquipmentFileVo.getEquipmentId(),"10");
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "操作成功");
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "操作失败");
			LogUtils.error("操作失败！",ex);
		}
		return respBody;
	}


	   @ResponseBody
	   @RequestMapping(value = "/equipmentFile/addFile",method = RequestMethod.POST)
	   public RespBody equipmentFileAdd(@RequestBody SysEquipmentFileVo  vo){
		   RespBody respBody = new RespBody();


		   if(vo.getImageList().length>0){
		   		for(int i= 0 ;i<vo.getImageList().length;i++){
		   			SysEquipmentFilePo po = new SysEquipmentFilePo();
		   			po.setId(ToolUtils.getUUID());
		   			po.setCreateTime(new Date());
		   			po.setState("10");
		   			po.setFileId(vo.getImageList()[i]);
		   			po.setType("10");
		   			po.setUserId(vo.getUserId());
					po.setEquipmentId(vo.getEquipmentId());
					//po.setTitleDetail("");
					sysEquipmentFileMapper.insert(po);
				}

		   }

			if(vo.getVideoList().length>0){
				for(int i= 0 ;i<vo.getVideoList().length;i++){
					SysEquipmentFilePo po = new SysEquipmentFilePo();
					po.setId(ToolUtils.getUUID());
					po.setCreateTime(new Date());
					po.setState("10");
					po.setFileId(vo.getVideoList()[i]);
					po.setType("20");
					po.setUserId(vo.getUserId());
					po.setEquipmentId(vo.getEquipmentId());
					po.setTitleDetail("");
					sysEquipmentFileMapper.insert(po);
				}

		   }

		   return  respBody;
	   }


	   @ResponseBody
	   @RequestMapping(value = "/equipmentFile/updateFile",method = RequestMethod.POST)
	   public RespBody equipmentFileUpdate(@RequestBody SysEquipmentFileVo  vo){
		   RespBody respBody = new RespBody();

			//删除
		   sysEquipmentFileMapper.deleteList(vo.getEquipmentId());

		   if(vo.getImageList().length>0){
				for(int i= 0 ;i<vo.getImageList().length;i++){
					SysEquipmentFilePo po = new SysEquipmentFilePo();
					po.setId(ToolUtils.getUUID());
					po.setCreateTime(new Date());
					po.setState("10");
					po.setFileId(vo.getImageList()[i]);
					po.setType("10");
					po.setUserId(vo.getUserId());
					po.setEquipmentId(vo.getEquipmentId());
					po.setAlign("10");
					sysEquipmentFileMapper.insert(po);
				}

		   }

			if(vo.getVideoList().length>0){
				for(int i= 0 ;i<vo.getVideoList().length;i++){
					SysEquipmentFilePo po = new SysEquipmentFilePo();
					po.setId(ToolUtils.getUUID());
					po.setCreateTime(new Date());
					po.setState("10");
					po.setFileId(vo.getVideoList()[i]);

					if("".equals(vo.getAlign())){
						po.setType("20");
						po.setAlign("10");
					}else{
						po.setType("10");
						po.setAlign("20");
					}

					po.setUserId(vo.getUserId());
					po.setEquipmentId(vo.getEquipmentId());
					po.setTitleDetail("null,null,null,null,null");

					sysEquipmentFileMapper.insert(po);
				}

		   }


		  sysEquipmentMapper.updateOne(vo.getEquipmentId(),"10");

		   

		   return  respBody;
	   }





	//多台设备修改素材
	@ResponseBody
	@RequestMapping(value = "/equipmentFile/updateSelectFile",method = RequestMethod.POST)
   public RespBody updateSelectFile(@RequestBody SysEquipmentFileVo  vo){
	   RespBody respBody = new RespBody();


		sysEquipmentFileMapper.deleteListSelect(vo.getUserId());


		List<SysEquipmentPo> listAll = sysEquipmentMapper.listAll(vo.getUserId());

	   for(int aa =0;aa<listAll.size();aa++){

		   	   if(vo.getImageList().length>0){
		   			for(int i= 0 ;i<vo.getImageList().length;i++){
		   				SysEquipmentFilePo po = new SysEquipmentFilePo();
		   				po.setId(ToolUtils.getUUID());
		   				po.setCreateTime(new Date());
		   				po.setState("10");
		   				po.setFileId(vo.getImageList()[i]);
		   				po.setType("10");
		   				po.setUserId(vo.getUserId());
		   				po.setEquipmentId(listAll.get(aa).getId());
		   				po.setAlign("10");
						po.setTitleDetail("null,null,null,null,null");
		   				sysEquipmentFileMapper.insert(po);
		   			}

		   	   }

		   		if(vo.getVideoList().length>0){
		   			for(int i= 0 ;i<vo.getVideoList().length;i++){
		   				SysEquipmentFilePo po = new SysEquipmentFilePo();
		   				po.setId(ToolUtils.getUUID());
		   				po.setCreateTime(new Date());
		   				po.setState("10");
		   				po.setFileId(vo.getVideoList()[i]);

		   				if("".equals(vo.getAlign())){
		   					po.setType("20");
		   					po.setAlign("10");
		   				}else{
		   					po.setType("10");
		   					po.setAlign("20");
		   				}

		   				po.setUserId(vo.getUserId());
		   				po.setEquipmentId(listAll.get(aa).getId());
						po.setTitleDetail("null,null,null,null,null");

		   				sysEquipmentFileMapper.insert(po);
		   			}

		   	   }






	   }

		//修改全部
		sysEquipmentMapper.updateAll(vo.getUserId(),"10");





	   return  respBody;
	}



	//多台设备修改素材
		@ResponseBody
		@RequestMapping(value = "/equipmentFile/updateSelectAllFile",method = RequestMethod.POST)
	   public RespBody updateSelectAllFile(@RequestBody SysEquipmentFileVo  vo){
		   RespBody respBody = new RespBody();



		   for(int aa =0;aa<vo.getEquipmentIds().length;aa++){

			   //删除
			   	   sysEquipmentFileMapper.deleteList(vo.getEquipmentId());

			   	   if(vo.getImageList().length>0){
			   			for(int i= 0 ;i<vo.getImageList().length;i++){
			   				SysEquipmentFilePo po = new SysEquipmentFilePo();
			   				po.setId(ToolUtils.getUUID());
			   				po.setCreateTime(new Date());
			   				po.setState("10");
			   				po.setFileId(vo.getImageList()[i]);
			   				po.setType("10");
			   				po.setUserId(vo.getUserId());
			   				po.setEquipmentId(vo.getEquipmentIds()[aa]);
			   				po.setAlign("10");
			   				po.setTitleDetail("null,null,null,null,null");
			   				sysEquipmentFileMapper.insert(po);
			   			}

			   	   }

			   		if(vo.getVideoList().length>0){
			   			for(int i= 0 ;i<vo.getVideoList().length;i++){
			   				SysEquipmentFilePo po = new SysEquipmentFilePo();
			   				po.setId(ToolUtils.getUUID());
			   				po.setCreateTime(new Date());
			   				po.setState("10");
			   				po.setFileId(vo.getVideoList()[i]);

			   				if("".equals(vo.getAlign())){
			   					po.setType("20");
			   					po.setAlign("10");
			   				}else{
			   					po.setType("10");
			   					po.setAlign("20");
			   				}

			   				po.setUserId(vo.getUserId());
			   				po.setEquipmentId(vo.getEquipmentIds()[aa]);

			   				sysEquipmentFileMapper.insert(po);
			   			}

			   	   }


			   	  sysEquipmentMapper.updateOne(vo.getEquipmentId(),"10");



		   }





		   return  respBody;
		}





	@GetMapping("/equipmentFile/findAllType")
	public RespBody equipmentFileAllType(String userId){
		RespBody respBody = new RespBody();
		respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", sysFileUrlMapper.findAllList(userId));

		return respBody;
	}


	@GetMapping("/sysEquipmentFile/findAllType1")
	public RespBody sysEquipmentFileAllType1(String id){
		RespBody respBody = new RespBody();
		respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", sysEquipmentFileMapper.findEquipemntIdList(id));
		return respBody;
	}


	/**
	 * 获取图片关联
	 * @param id
	 * @return 2020-08-09
	 */

	@GetMapping("/sysEquipmentFile/findAllType")
	public RespBody sysEquipmentFileAllType(String id,String classes,String name,String type){
		RespBody respBody = new RespBody();

		if("20".equals(type)){
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", sysEquipmentFileMapper.findEquipemntIdList1(id));


		}else if("30".equals(type)){
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", sysEquipmentFileMapper.findEquipemntIdList2(id));

		}else{

			if("".equals(classes)){
				respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", sysEquipmentFileMapper.findEquipemntIdList(id));

			}else{
				respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", sysEquipmentFileMapper.findEquipemntIdListClass(id,classes));
			}

			if(!"".equals(classes) && !"".equals(name)){
				respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", sysEquipmentFileMapper.findEquipemntIdListName(id,classes,name));
			}else if(!"".equals(name)){
				respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", sysEquipmentFileMapper.findEquipemntIdListName1(id,name));

			}
		}



			return respBody;
	}



	@PostMapping("/sysEquipmentFile/update")
	public RespBody sysEquipmentFileUpdate(@RequestBody SysEquipmentFileVo  vo){
		RespBody respBody = new RespBody();
		//respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", sysEquipmentFileMapper.findEquipemntIdList(id));

		SysEquipmentFilePo sysEquipmentFilePo = sysEquipmentFileMapper.findEquipemntId(vo.getId());


		if(sysEquipmentFilePo!=null){

			sysEquipmentFilePo.setPrice(vo.getPrice());
            sysEquipmentFilePo.setPrice1(vo.getPrice1());
			sysEquipmentFilePo.setName(vo.getName());
			sysEquipmentFilePo.setSort(vo.getSort());
			sysEquipmentFilePo.setState(vo.getState());
			sysEquipmentFilePo.setUnit(vo.getUnit());
			sysEquipmentFilePo.setUpdateState("10");

			sysEquipmentFileMapper.updateEquipmentAllUpdateState(sysEquipmentFilePo.getEquipmentNo());
			sysEquipmentFileMapper.updateByPrimaryKey(sysEquipmentFilePo);
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "数据修改成功");
		}else{
			respBody.add(RespCodeEnum.ERROR.getCode(), "数据修改失败");

		}


		return respBody;
	}

	//获取选中值
	@GetMapping("/equipmentFile/findSelectType")
	public RespBody equipmentFileSelectType(String equipmentId){
			RespBody respBody = new RespBody();

			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", sysEquipmentFileMapper.findAll(equipmentId));

			return respBody;
	}


	//商品全部下架
	@GetMapping("/deleteAll")
	public RespBody deleteAll(String equipmentId){
		RespBody respBody = new RespBody();


		sysEquipmentFileMapper.updateEquipmentAllState(equipmentId);

		respBody.add(RespCodeEnum.SUCCESS.getCode(), "一键下架已完成");

		return respBody;
	}

    //商品全部下架
    @GetMapping("/deleteAllIds")
    public RespBody deleteAllIds(String equipmentId){
        RespBody respBody = new RespBody();


        sysEquipmentFileMapper.updateEquipmentAllStateId(equipmentId);

        respBody.add(RespCodeEnum.SUCCESS.getCode(), "一键下架已完成");

        return respBody;
    }


	//图片删除下架
	@GetMapping("/imageDeleteAll")
	public RespBody imageDeleteAll(String fileId){

		RespBody respBody = new RespBody();
		sysEquipmentFileMapper.updateImageAllState(fileId);
		respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功");

		return respBody;
	}






	@GetMapping("/equipment/findOne")
	public RespBody findOne(String equipmentId){
			RespBody respBody = new RespBody();
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", sysEquipmentMapper.selectOne1(equipmentId));

			return respBody;
	}


	@GetMapping("/equipment/updateStatus")
	public RespBody updateStatus(String equipmentId){
			RespBody respBody = new RespBody();
			sysEquipmentMapper.updateOne(equipmentId,"20");
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", "ok");

			return respBody;
	}

	@GetMapping("/equipment/updateOneTitleStatus")
	public RespBody updateOneTitleStatus(String equipmentId){
		RespBody respBody = new RespBody();
		sysEquipmentMapper.updateOneTitleStatus(equipmentId,"20");
		respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", "ok");

		return respBody;
	}







	@GetMapping("/equipment/findParam")
		public RespBody findParam(String paramName){
				RespBody respBody = new RespBody();

				respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", parameterMapper.findParam(paramName));

				return respBody;
		}



	@GetMapping("/getCard")
	public RespBody getCard(String id){
		RespBody respBody = new RespBody();

		//CardNoUtils.getCardNo("http://device.kangxiaoshuai.com:8000/uploadImages/122/test11.jpg");
        String str =   CardNoUtils.getCardNo("C:\\Users\\Administrator\\Desktop\\test11.jpg");
        respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", str);
		return respBody;
	}








}
