package com.qzi.cms.web.controller;

import com.qzi.cms.common.annotation.SystemControllerLog;
import com.qzi.cms.common.enums.RespCodeEnum;
import com.qzi.cms.common.po.SysEquipmentFilePo;
import com.qzi.cms.common.po.SysEquipmentPo;
import com.qzi.cms.common.po.SysFileUrlPo;
import com.qzi.cms.common.po.UseUnlockEquRecordPo;
import com.qzi.cms.common.resp.Paging;
import com.qzi.cms.common.resp.RespBody;
import com.qzi.cms.common.util.CardNoUtils;
import com.qzi.cms.common.util.LogUtils;
import com.qzi.cms.common.util.ToolUtils;
import com.qzi.cms.common.vo.SysFileUrlVo;
import com.qzi.cms.common.vo.UseEquipmentVo;
import com.qzi.cms.server.mapper.SysEquipmentFileMapper;
import com.qzi.cms.server.mapper.SysEquipmentMapper;
import com.qzi.cms.server.mapper.SysFileUrlMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.tools.Tool;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 文件管理
 * Created by Administrator on 2019/6/8.
 */


@RestController
@RequestMapping("/file")
public class FileController {

       @Resource
       private SysFileUrlMapper sysFileUrlMapper;


       @Resource
       private SysEquipmentMapper sysEquipemntMapper;

       @Resource
       private SysEquipmentFileMapper sysEquipmentFileMapper;


    private String imagepath = "/data/page/uploadImages/";



       @ResponseBody
       @RequestMapping(value = "/addFile",method = RequestMethod.POST)
       public RespBody addFile(@RequestBody SysFileUrlPo po){
           RespBody respBody = new RespBody();

           if(sysFileUrlMapper.findAllChoCount(po.getUserName(),po.getRemark())>0){
               respBody.add(RespCodeEnum.ERROR.getCode(), "存在相同的名称");
               return respBody;
           }


          String[] paths =  po.getFileUrl().split(",");

          String path1 =   paths[paths.length-1];


          paths = path1.split(",");

          for(int i=0 ;i<paths.length;i++){
            SysFileUrlPo sysFileUrlPo = new SysFileUrlPo();
            sysFileUrlPo.setId(ToolUtils.getUUID());
            sysFileUrlPo.setUserName(po.getUserName());
            sysFileUrlPo.setFileUrl(po.getUserName()+"/"+paths[i]);
            sysFileUrlPo.setStandards(po.getStandards());
            sysFileUrlPo.setUserId(po.getUserId());
            sysFileUrlPo.setType(po.getType());
            sysFileUrlPo.setState("10");
            sysFileUrlPo.setCreateTime(new Date());
            sysFileUrlPo.setRemark(po.getRemark());
            sysFileUrlPo.setClasses(po.getClasses());
            sysFileUrlMapper.insert(sysFileUrlPo);


           // SysEquipmentPo sysEquipemntMapper.listAll()

            List<SysEquipmentPo> sysEquipmentPoList =null;


            if("adminA".equals(po.getUserName())){
                sysEquipmentPoList = sysEquipemntMapper.listAll1();
            }else{
                sysEquipmentPoList = sysEquipemntMapper.listAll(po.getUserId());
            }


            Integer count1 = sysFileUrlMapper.findCount1(po.getUserId());


            if(sysEquipmentPoList.size()>0){

                for(int b=0;b<sysEquipmentPoList.size();b++){

                SysEquipmentFilePo sysEquipmentFilePo = new SysEquipmentFilePo();
                sysEquipmentFilePo.setId(ToolUtils.getUUID());
                sysEquipmentFilePo.setCreateTime(new Date());
                sysEquipmentFilePo.setState("20");
                sysEquipmentFilePo.setType(po.getType());
                sysEquipmentFilePo.setAlign(po.getStandards());
                sysEquipmentFilePo.setEquipmentId(sysEquipmentPoList.get(b).getId());
                sysEquipmentFilePo.setEquipmentNo(sysEquipmentPoList.get(b).getEquipmentNo());
                sysEquipmentFilePo.setSort(""+count1);
                sysEquipmentFilePo.setFileId(po.getUserName()+"/"+paths[i]);
                sysEquipmentFilePo.setTitleDetail("");
                sysEquipmentFilePo.setPrice("");
                sysEquipmentFilePo.setPrice1("");
                sysEquipmentFilePo.setName(po.getRemark());
                sysEquipmentFilePo.setRemark(po.getRemark());
                sysEquipmentFilePo.setUserId(po.getUserId());
                sysEquipmentFilePo.setUpdateState("10");
                sysEquipmentFilePo.setUnit("1斤");

                sysEquipmentFileMapper.insert(sysEquipmentFilePo);
                }

            }










          }
           
           return  respBody;
       }



       /**
         * 添加人脸开锁记录（带开锁图片）
         */
        @ResponseBody
        @RequestMapping(value = "/upload",method = RequestMethod.POST)
        public RespBody addEquRecord(@RequestPart("file") MultipartFile file, SysFileUrlPo po, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IllegalStateException, IOException {
            RespBody respBody=new RespBody();


            String imagepath = po.getUserName();
            if (file!=null) {// 判断上传的文件是否为空
                String path=null;// 文件路径
                String type=null;// 文件类型
                String fileName=file.getOriginalFilename();// 文件原名称
                System.out.println("上传的文件原名称:"+fileName);
                // 判断文件类型
                type=fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()):null;
                if (type!=null) {// 判断文件类型是否为空
                    if ("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase()) ||"mp4".equals(type.toUpperCase())  ||"MP4".equals(type.toUpperCase()) ) {




                        // 项目在容器中实际发布运行的根路径
                        String realPath=request.getSession().getServletContext().getRealPath("/");
                        // 自定义的文件名称
                        String trueFileName=String.valueOf(System.currentTimeMillis())+"."+type.toUpperCase();
                        // 设置存放图片文件的路径


                        //path = "/data/page/uploadImages/"+/*System.getProperty("file.separator")+*/trueFileName;
                        path = realPath+"\\"+/*System.getProperty("file.separator")+*/trueFileName;

                        System.out.println("存放图片文件的路径:"+realPath+fileName);
                        // 转存文件到指定的路径
                        file.transferTo(new File(path));
                       // Runtime.getRuntime().exec("chmod 777 -R " +"/data/page/uploadImages/"+trueFileName);

                        String str =   CardNoUtils.getCardNo(path);
                        respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", str);

                       // respBody.add(RespCodeEnum.SUCCESS.getCode(), "添加成功",path);
                    }else {
                        System.out.println("不是我们想要的文件类型,请按要求重新上传");
                        respBody.add(RespCodeEnum.ERROR.getCode(), "文件类型不对，请重新上传");
                        return respBody;
                    }
                }else {
                    System.out.println("文件类型为空");
                    respBody.add(RespCodeEnum.ERROR.getCode(), "文件类型为空");
                    return respBody;
                }
            }else {
                System.out.println("没有找到相对应的文件");
                respBody.add(RespCodeEnum.ERROR.getCode(), "没有找到相对应的文件");
                return respBody;
            }
            return respBody;
        }




    /**
     * 添加人脸开锁记录（带开锁图片）
     */
    @ResponseBody
    @RequestMapping(value = "/upload11",method = RequestMethod.POST)
    public RespBody addEquRecord11(@RequestPart("file") MultipartFile file, SysFileUrlPo po, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IllegalStateException, IOException {
        RespBody respBody=new RespBody();


        String imagepath = po.getUserName();
        if (file!=null) {// 判断上传的文件是否为空
            String path=null;// 文件路径
            String type=null;// 文件类型
            String fileName=file.getOriginalFilename();// 文件原名称
            System.out.println("上传的文件原名称:"+fileName);
            // 判断文件类型
            type=fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()):null;
            if (type!=null) {// 判断文件类型是否为空
                if ("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase()) ||"mp4".equals(type.toUpperCase())  ||"MP4".equals(type.toUpperCase()) ) {




                    // 项目在容器中实际发布运行的根路径
                    String realPath=request.getSession().getServletContext().getRealPath("/");
                    // 自定义的文件名称
                    String trueFileName=String.valueOf(System.currentTimeMillis())+"."+type.toUpperCase();
                    // 设置存放图片文件的路径


                    path = "/data/page/uploadImages/"+po.getUserName()+"/"+/*System.getProperty("file.separator")+*/trueFileName;
                    //path = realPath+"\\"+/*System.getProperty("file.separator")+*/trueFileName;

                    System.out.println("存放图片文件的路径:"+realPath+fileName);
                    // 转存文件到指定的路径
                    file.transferTo(new File(path));
                     Runtime.getRuntime().exec("chmod 777 -R " +"/data/page/uploadImages/"+po.getUserName()+"/"+trueFileName);

                  //  String str =   po.getUserName()+"/"+trueFileName;
                    respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", trueFileName);

                    // respBody.add(RespCodeEnum.SUCCESS.getCode(), "添加成功",path);
                }else {
                    System.out.println("不是我们想要的文件类型,请按要求重新上传");
                    respBody.add(RespCodeEnum.ERROR.getCode(), "文件类型不对，请重新上传");
                    return respBody;
                }
            }else {
                System.out.println("文件类型为空");
                respBody.add(RespCodeEnum.ERROR.getCode(), "文件类型为空");
                return respBody;
            }
        }else {
            System.out.println("没有找到相对应的文件");
            respBody.add(RespCodeEnum.ERROR.getCode(), "没有找到相对应的文件");
            return respBody;
        }
        return respBody;
    }


        @GetMapping("/findAll")
    	public RespBody findAll(Paging paging,SysFileUrlPo sysFileUrlPo){
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

    		  List<SysFileUrlVo> list =  sysFileUrlMapper.findAll(sysFileUrlPo,startRow,pageSize);
//    		  for(SysFileUrlVo vo:list){
//
//
//
//    		  }
    			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", list);
    			//保存分页对象
    			paging.setTotalCount(sysFileUrlMapper.findCount(sysFileUrlPo));
    			respBody.setPage(paging);
    		} catch (Exception ex) {
    			respBody.add(RespCodeEnum.ERROR.getCode(), "查找文件所有设备数据失败");
    			LogUtils.error("查找所有设备数据失败！",ex);
    		}
    		return respBody;
    	}


    @PostMapping("/update")
    @SystemControllerLog(description="修改设备")
    public RespBody update(@RequestBody SysFileUrlPo sysFileUrlPo){
        RespBody respBody = new RespBody();
        try {
            sysFileUrlMapper.updateByPrimaryKey(sysFileUrlPo);

            sysEquipmentFileMapper.updateImageAllState(sysFileUrlPo.getFileUrl());
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "操作成功");
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "操作失败");
            LogUtils.error("操作失败！",ex);
        }
        return respBody;
    }

    //adminA  删除图片
    @PostMapping("/deleteAll")
    @SystemControllerLog(description="删除设备")
    public RespBody deleteAll(@RequestBody SysFileUrlPo sysFileUrlPo){
        RespBody respBody = new RespBody();
        try {


            sysEquipmentFileMapper.deleteListAll(sysFileUrlPo.getFileUrl());

            if("adminA".equals(sysFileUrlPo.getUserName())){
                sysEquipmentFileMapper.updateListAll();
            }else{
                sysEquipmentFileMapper.updateListAllUser(sysFileUrlPo.getUserName());
            }
            sysFileUrlMapper.updateByPrimaryKey(sysFileUrlPo);
            //sysEquipmentFileMapper.updateListAll();

            //sysEquipmentFileMapper.updateImageAllState(sysFileUrlPo.getFileUrl());
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "操作成功");
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "操作失败");
            LogUtils.error("操作失败！",ex);
        }
        return respBody;
    }





        @PostMapping("/delete")
       	@SystemControllerLog(description="删除设备")
       	public RespBody delete(@RequestBody SysFileUrlPo sysFileUrlPo){
       		RespBody respBody = new RespBody();
       		try {
       			sysFileUrlMapper.delete(sysFileUrlPo);
       			respBody.add(RespCodeEnum.SUCCESS.getCode(), "操作成功");
       		} catch (Exception ex) {
       			respBody.add(RespCodeEnum.ERROR.getCode(), "操作失败");
       			LogUtils.error("操作失败！",ex);
       		}
       		return respBody;
       	}





}
