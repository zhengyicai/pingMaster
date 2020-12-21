/* 
 * 文件名：SysParameterMapper.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月15日
 * 版本号：v1.0
*/
package com.qzi.cms.server.mapper;

import com.qzi.cms.common.po.SysEquipmentPo;
import com.qzi.cms.common.po.SysFileUrlPo;
import com.qzi.cms.common.vo.SysEquipmentVo;
import com.qzi.cms.common.vo.SysFileUrlVo;
import com.qzi.cms.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 文件管理DAO
 * @author qsy
 * @version v1.0
 * @date 2017年6月15日
 */
public interface SysEquipmentMapper extends BaseMapper<SysEquipmentPo>{


	public List<SysEquipmentVo> findAll(@Param("model") SysEquipmentPo sysEquipmentPo, @Param("startRow") int startRow, @Param("pageSize") int pageSize);

	public long findCount(@Param("model") SysEquipmentPo sysEquipmentPo);

	
	@Update("update sys_equipment set status = #{status} where id= #{id}")
	public void updateOne(@Param("id") String id, @Param("status") String status);


	@Update("update sys_equipment set titleStatus = #{status} where id= #{id}")
	public void updateOneTitleStatus(@Param("id") String id, @Param("status") String status);



	@Update("update sys_equipment set titleStatus = #{status} where id= #{id}")
	public void updateTitleOne(@Param("id") String id, @Param("status") String status);

	@Update("update sys_equipment set status = #{status} where userId= #{userId}")
		public void updateAll(@Param("userId") String userId, @Param("status") String status);




	@Update("update sys_equipment set type = #{type} where userId= #{userId}")
	public void updateSelect(@Param("userId") String userId, @Param("type") String type);

	
	@Select("select * from sys_equipment where equipmentNo = #{id}")
	public SysEquipmentPo selectOne1(@Param("id") String id);


	@Select("select * from sys_equipment where userId = #{userId}")
	public List<SysEquipmentPo> listAll(@Param("userId") String userId);


	@Select("select * from sys_equipment where state='10'")
	public List<SysEquipmentPo> listAll1();


	@Select("select * from sys_equipment where equipmentNo=#{equipmentNo} limit 1")
	public  SysEquipmentPo findOne(@Param("equipmentNo") String equipmentNo);

	@Select("select * from sys_equipment where equipmentNo=#{equipmentNo} and state='10' limit 1")
	public  SysEquipmentPo findOneState(@Param("equipmentNo") String equipmentNo);


}
