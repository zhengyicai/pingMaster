/* 
 * 文件名：LoginController.java  
 * 版权：Copyright 2016-2016 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2016年11月27日
 * 版本号：v1.0
*/
package com.qzi.cms.web.controller;

import javax.annotation.Resource;

import com.qzi.cms.common.exception.CommException;
import com.qzi.cms.common.po.SuyuanCardPo;
import com.qzi.cms.common.po.SuyuanUserPo;
import com.qzi.cms.common.service.RedisService;
import com.qzi.cms.common.util.ToolUtils;
import com.qzi.cms.common.vo.SuyuanCardVo;
import com.qzi.cms.common.vo.SuyuanUserVo;
import com.qzi.cms.common.vo.UseResidentVo;
import com.qzi.cms.server.mapper.SuyuanCardMapper;
import com.qzi.cms.server.mapper.SuyuanUserMapper;
import com.qzi.cms.server.service.common.CommonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qzi.cms.common.annotation.SystemControllerLog;
import com.qzi.cms.common.enums.RespCodeEnum;
import com.qzi.cms.common.resp.RespBody;
import com.qzi.cms.common.util.LogUtils;
import com.qzi.cms.common.vo.LoginVo;
import com.qzi.cms.server.service.common.KaptchaService;
import com.qzi.cms.server.service.web.LoginService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户登陆控制器
 * 
 * @author qsy
 * @version v1.0
 * @date 2016年11月27日
 */
@RestController
@RequestMapping(value = "/login")
public class LoginController {
	@Resource(name="webLogin")
	private LoginService loginService;
	@Resource
	private KaptchaService kaptchaService;

	@Resource
	private CommonService commonServcie;

	@Resource
	private RedisService redisService;

	@Resource
	private SuyuanUserMapper suyuanUserMapper;


	@Resource
	private SuyuanCardMapper  suyuanCardMapper;
	@PostMapping("/loginIn")
	@SystemControllerLog(description="用户登录")
	public RespBody longIn(@RequestBody LoginVo loginVo) {
		// 创建返回对象
		RespBody respBody = new RespBody();
		try {
			//验证FormBean
			if(hasErrors(loginVo,respBody)){
				// 验证通过，调用业务层，实现登录验证处理
				respBody = loginService.LoginIn(loginVo);
			}
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "用户登录失败");
			LogUtils.error("用户登录失败！",ex);
		}
		return respBody;
	}

	@PostMapping("/loginInApp")
	@SystemControllerLog(description="用户登录")
	public RespBody longInApp(@RequestBody LoginVo loginVo) {
		// 创建返回对象
		RespBody respBody = new RespBody();
		try {
			//验证FormBean
			//if(hasErrors(loginVo,respBody)){
				// 验证通过，调用业务层，实现登录验证处理
				respBody = loginService.LoginInApp(loginVo);
			//}
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "用户登录失败");
			LogUtils.error("用户登录失败！",ex);
		}
		return respBody;
	}
	
	/**
	 * 加载图片验证码
	 * @return
	 */
	@GetMapping("/loadImgCode")
	public RespBody loadImgCode() {
		// 创建返回对象
		RespBody respBody = new RespBody();
		try {
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "获取图片验证码成功", kaptchaService.createCodeImg());
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "获取图片验证码失败");
			LogUtils.error("用户登录失败！",ex);
		}
		return respBody;
	}
	
	
	
	/**
	 * 验证formBean
	 * @param loginVo
	 * @param respBody
	 * @return
	 */
	private boolean hasErrors(LoginVo loginVo,RespBody respBody){
		if(StringUtils.isEmpty(loginVo.getLoginName())){
			respBody.add(RespCodeEnum.ERROR.getCode(), "用户名不能为空！");
			return false;
		}
		if(StringUtils.isEmpty(loginVo.getPassword())){
			respBody.add(RespCodeEnum.ERROR.getCode(), "密码不能为空！");
			return false;
		}
		if(StringUtils.isEmpty(loginVo.getPicCode())){
			respBody.add(RespCodeEnum.ERROR.getCode(), "验证码不能为空！");
			return false;
		}
		return true;
	}




	@GetMapping("/sms")
	@SystemControllerLog(description="获取手机短信验证码")
	public RespBody sendSms(String mobile) {
		// 创建返回对象
		RespBody respBody = new RespBody();
		try {
			commonServcie.sendSms(mobile);
			respBody.add(RespCodeEnum.SUCCESS.getCode(),"获取手机短信验证码成功");
		} catch (CommException ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
			LogUtils.error("获取手机短信验证码失败！",ex);
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "获取手机短信验证码失败");
			LogUtils.error("获取手机短信验证码失败！",ex);
		}
		return respBody;
	}



	@GetMapping("/phone")
	@SystemControllerLog(description="获取手机号数据")
	public RespBody phone(String mobile) {
		// 创建返回对象
		RespBody respBody = new RespBody();


		SuyuanUserPo suyuanUserPo =   suyuanUserMapper.findPhone(mobile);

		if(suyuanUserPo!=null){
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "获取图片验证码成功", suyuanUserPo);
		}else{
			respBody.add(RespCodeEnum.ERROR.getCode(), "该手机号没有注册");
		}


		return respBody;
	}


	@GetMapping("/cardNo")
	@SystemControllerLog(description="获取二维码信息")
	public RespBody cardNo(String cardNo) {
		// 创建返回对象
		RespBody respBody = new RespBody();

		respBody.add(RespCodeEnum.SUCCESS.getCode(), "获取图片验证码成功",suyuanCardMapper.findCardNo(cardNo));



		return respBody;
	}









	@PostMapping("/add")
	@SystemControllerLog(description="注册")
	public RespBody add(@RequestBody SuyuanCardVo suyuanCardVo) throws  Exception {

		RespBody respBody = new RespBody();

		List<SuyuanCardPo> list  =   suyuanCardVo.getList();


		if(list!=null){


			for(int i = 0;i<list.size();i++){
				SuyuanCardPo suyuanCardPo = new SuyuanCardPo();

				suyuanCardPo.setId(ToolUtils.getUUID());
				suyuanCardPo.setState("10");
				suyuanCardPo.setCreateTime(new Date());
				suyuanCardPo.setRemark("");
				suyuanCardPo.setAddress(suyuanCardVo.getAddress());
				suyuanCardPo.setPhone(suyuanCardVo.getPhone());
				suyuanCardPo.setName(suyuanCardVo.getName());
				suyuanCardPo.setCode(suyuanCardVo.getCode());
				suyuanCardPo.setCardNo(list.get(i).getCardNo());
				suyuanCardPo.setType(list.get(i).getType());
				suyuanCardPo.setWeight(list.get(i).getWeight());

				suyuanCardMapper.insert(suyuanCardPo);

			}

		}

		return  respBody;

	}



	@PostMapping("/register")
	@SystemControllerLog(description="注册")
	public RespBody register(@RequestBody SuyuanUserVo suyuanUserVo) throws  Exception {
		// 创建返回对象
		RespBody respBody = new RespBody();
		String smsCode ="";
		Object obj = redisService.getObj(suyuanUserVo.getPhone());
		if(obj != null && obj instanceof Map){
			Map<String, String> data = (Map<String, String>) obj;
			smsCode = data.get("smsCode");
		}
		if(!smsCode.equals(suyuanUserVo.getSmsCode())){
			respBody.add(RespCodeEnum.ERROR.getCode(), "手机验证码输入有误");
			return  respBody;
			//throw new CommException("手机验证码输入有误");
		}


		SuyuanUserPo suyuanUserPo1 =   suyuanUserMapper.findPhone(suyuanUserVo.getPhone());
		if(suyuanUserPo1!=null){
			respBody.add(RespCodeEnum.ERROR.getCode(), "该手机号已存在");
			return  respBody;

		}


		SuyuanUserPo suyuanUserPo = new SuyuanUserPo();
		suyuanUserPo.setId(ToolUtils.getUUID());
		suyuanUserPo.setState("10");
		suyuanUserPo.setCreateTime(new Date());
		suyuanUserPo.setRemark("");


		suyuanUserPo.setAddress(suyuanUserVo.getAddress());
		suyuanUserPo.setCode(suyuanUserVo.getCode());
		suyuanUserPo.setName(suyuanUserVo.getName());
		suyuanUserPo.setPhone(suyuanUserVo.getPhone());


		suyuanUserMapper.insert(suyuanUserPo);




		return respBody;
	}





}
