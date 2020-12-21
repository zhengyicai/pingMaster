package com.qzi.cms.web.controller;

import com.qzi.cms.common.annotation.SystemControllerLog;
import com.qzi.cms.common.enums.RespCodeEnum;
import com.qzi.cms.common.po.SysEquipmentFilePo;
import com.qzi.cms.common.po.SysEquipmentPo;
import com.qzi.cms.common.resp.Paging;
import com.qzi.cms.common.resp.RespBody;
import com.qzi.cms.common.util.LogUtils;
import com.qzi.cms.common.util.ToolUtils;
import com.qzi.cms.common.vo.SysEquipmentFileVo;
import com.qzi.cms.common.vo.SysEquipmentVo;
import com.qzi.cms.server.mapper.SysEquipmentFileMapper;
import com.qzi.cms.server.mapper.SysEquipmentMapper;
import com.qzi.cms.server.mapper.SysFileUrlMapper;
import com.qzi.cms.server.mapper.SysParameterMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * 文件管理
 * Created by Administrator on 2019/6/8.
 */


@RestController
@RequestMapping("/appSysEquipment")
public class AppSysEquipmentController {

       @Resource
       private SysEquipmentMapper sysEquipmentMapper;

       @Resource
	   private SysEquipmentFileMapper sysEquipmentFileMapper;

	   @Resource
	   private SysFileUrlMapper sysFileUrlMapper;


	   @Resource
	   private  SysParameterMapper sysParameterMapper;

	    @GetMapping("/equipmentFile/findAll")
    	public RespBody equipmentFileAll(SysEquipmentFilePo sysEquipmentFilePo){
    		RespBody respBody = new RespBody();






			respBody.add(RespCodeEnum.SUCCESS.getCode(), "数据查找成功", sysEquipmentFileMapper.findAll(sysEquipmentFilePo.getEquipmentId()));

    		return respBody;
    	}


	public static String saveUrlAs(String url, String filePath, String userName,String method){
		//System.out.println("fileName---->"+filePath);
		//创建不同的文件夹目录
		File file=new File(filePath);
		String str = new Date().getTime()+".mp3";





		//String str = new Date().getTime()+".mp3";



		//判断文件夹是否存在
		if (!file.exists())
		{
			//如果文件夹不存在，则创建新的的文件夹
			file.mkdirs();
		}
		FileOutputStream fileOut = null;
		HttpURLConnection conn = null;
		InputStream inputStream = null;

		try
		{
			// 建立链接
			URL httpUrl=new URL(url);
			conn=(HttpURLConnection) httpUrl.openConnection();
			//以Post方式提交表单，默认get方式
			conn.setRequestMethod(method);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Charset", "UTF-8");
			// post方式不能使用缓存
			conn.setUseCaches(false);
			//连接指定的资源
			conn.connect();
			//获取网络输入流
			inputStream=conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(inputStream);
			//判断文件的保存路径后面是否以/结尾
			if (!filePath.endsWith("/")) {

				filePath += "/";

			}


			//写入到文件（注意文件保存路径的后面一定要加上文件的名称）
			fileOut = new FileOutputStream(filePath+str);
			BufferedOutputStream bos = new BufferedOutputStream(fileOut);

			byte[] buf = new byte[4096];
			int length = bis.read(buf);
			//保存文件
			while(length != -1)
			{
				bos.write(buf, 0, length);
				length = bis.read(buf);
			}
			bos.close();
			bis.close();
			conn.disconnect();
		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("抛出异常！！");
		}

		try {
			Runtime.getRuntime().exec("chmod 777 -R " +filePath+str);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return userName+"/"+str;
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






	@GetMapping("/file/findone")
	public RespBody findone(String equipmentNo){
		RespBody respBody = new RespBody();

		respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有数据成功",sysEquipmentMapper.findOne(equipmentNo));

		return respBody;
	}


	/**
	 * 获取参数版本
	 * @param paramName
	 * @return
	 */

	@GetMapping("/param/findone")
	public RespBody paramFindone(String paramName){
		RespBody respBody = new RespBody();
		respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有数据成功",sysParameterMapper.findParam(paramName));
		return respBody;
	}


	@GetMapping("/file/findAllImg")
	public RespBody findAllImg(SysEquipmentPo sysEquipmentPo1){
		RespBody respBody = new RespBody();

		respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有数据成功",sysEquipmentFileMapper.findEquipemntIdList1State(sysEquipmentPo1.getEquipmentNo()));
		return respBody;
	}

	@GetMapping("/file/findAllVideo")
	public RespBody findAllVideo(SysEquipmentPo sysEquipmentPo1){
		RespBody respBody = new RespBody();

		respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有数据成功",sysEquipmentFileMapper.findEquipemntIdList2State(sysEquipmentPo1.getEquipmentNo()));
		return respBody;
	}



	@GetMapping("/file/findAll")
	public RespBody fileFindAll(Paging paging,SysEquipmentPo sysEquipmentPo1, HttpServletRequest request){
		RespBody respBody = new RespBody();

		//Paging paging = new Paging();

		SysEquipmentPo sysEquipmentPo =   sysEquipmentMapper.findOneState(sysEquipmentPo1.getEquipmentNo());


		if(sysEquipmentPo!=null){

			int startRow=0;int pageSize=0;
			if(null!=paging){
				startRow=(paging.getPageNumber()>0)?(paging.getPageNumber()-1)*paging.getPageSize():0;
				pageSize=paging.getPageSize();
			}else{
				pageSize=Integer.MAX_VALUE;
			}
			//paging.setPageSize(pageSize);

			try {
				//保存返回数据

				SysEquipmentFilePo sysEquipmentFilePo = new SysEquipmentFilePo();
				sysEquipmentFilePo.setEquipmentNo(sysEquipmentPo.getEquipmentNo());
				sysEquipmentFilePo.setState("10");
				List<SysEquipmentFileVo> list =  sysEquipmentFileMapper.findAll1(sysEquipmentFilePo,startRow,pageSize);
				//sysEquipmentFilePo.setState("10");
				//List<SysEquipmentFileVo> list11 =  sysEquipmentFileMapper.findAll1(sysEquipmentFilePo,startRow,pageSize);


				//语音文件
				String  spc= "";



				String updateState="20";



				for(SysEquipmentFileVo vo:list){

					if("10".equals(vo.getState())) {

						if ("".equals(vo.getPrice1()) || vo.getPrice1() == null) {
							spc += vo.getName() + "价格" + vo.getPrice() + "元" + vo.getUnit() + ",";
						} else {
							spc += vo.getName() + "特价" + vo.getPrice1() + "元" + vo.getUnit() + ",";
						}
					}

					//发现是否有数据更新
					if("10".equals(vo.getUpdateState())){
						updateState="10";
						sysEquipmentFileMapper.updateState("20",vo.getId());
					}

				}




				//spc="苹果5元/斤,香蕉6元/斤,哈密瓜8元/斤";


				String encodeStr = URLEncoder.encode(spc, "utf-8");
				String urls = "http://tts.baidu.com/text2audio?cuid=baiduid&lan=zh&ctp=1&spd=3&pdt=311&tex="+encodeStr;


				// 项目在容器中实际发布运行的根路径
				//String realPath=request.getSession().getServletContext().getRealPath("/");

				String realPath = "/data/page/uploadImages/"+sysEquipmentPo.getUserName()+"/";



				if("10".equals(updateState)){
					String string1 =  saveUrlAs(urls,realPath,sysEquipmentPo.getUserName(),"POST");
					respBody.add(RespCodeEnum.SUCCESS.getCode(), string1, list);
				}else{
					respBody.add(RespCodeEnum.SUCCESS.getCode(), "", list);
				}


				//保存分页对象
				paging.setTotalCount(sysEquipmentFileMapper.findCount1(sysEquipmentFilePo));
				respBody.setPage(paging);
			} catch (Exception ex) {
				respBody.add(RespCodeEnum.ERROR.getCode(), "查找文件所有设备数据失败");
				LogUtils.error("查找所有设备数据失败！",ex);
			}
			return respBody;
		}

		//respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", sysEquipmentFileMapper.findAll(sysEquipmentPo1.getEquipmentNo()));

		return respBody;
	}









}
