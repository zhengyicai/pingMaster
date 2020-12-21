/* 
 * 文件名：SysParameterMapper.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月15日
 * 版本号：v1.0
*/
package com.qzi.cms.server.mapper;

import com.qzi.cms.common.po.SuyuanCardPo;
import com.qzi.cms.common.po.SuyuanUserPo;
import com.qzi.cms.common.po.SysClassesPo;
import com.qzi.cms.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 设置file
 * @author qsy
 * @version v1.0
 * @date 2017年6月15日
 */
public interface SuyuanUserMapper extends BaseMapper<SuyuanUserPo>{


	public List<SuyuanUserPo> findAll(@Param("model") SuyuanUserPo suyuanUserPo, @Param("startRow") int startRow, @Param("pageSize") int pageSize);

	public long findCount(@Param("model") SuyuanUserPo suyuanUserPo);

	@Select("select * from suyuan_user where phone=#{phone} and state='10' limit 1")
	public SuyuanUserPo findPhone(@Param("phone") String phone);

	@Update("update suyuan_user set name=#{model.name},phone=#{model.phone},code=#{model.code},state=#{model.state},address=#{model.address} where id = #{model.id}")
	public void update1(@Param("model") SuyuanUserPo suyuanUserPo);



}
