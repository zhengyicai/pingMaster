/* 
 * 文件名：UseRoomMapper.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年7月6日
 * 版本号：v1.0
*/
package com.qzi.cms.server.mapper;

import com.qzi.cms.common.po.UseEquipmentPo;
import com.qzi.cms.common.po.UseEquipmentPortPo;
import com.qzi.cms.common.po.UseEquipmentTitlePo;
import com.qzi.cms.common.vo.UseEquipmentTitleVo;
import com.qzi.cms.server.base.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 设备标签
 * @author qsy
 * @version v1.0
 * @date 2017年7月6日
 */
public interface UseEquipmentTitleMapper extends BaseMapper<UseEquipmentTitlePo>{

//	@Update("update use_equipment_title set ips=#{ips},port=#{port},createTime = now()  where equipmentNo=#{equipmentNo}")
//	public void update(@Param("ips") String ips, @Param("port") String port, @Param("equipmentNo") String equipmentNo);

	public List<UseEquipmentTitleVo> findAll(@Param("model") UseEquipmentPo useEquipmentPo);

	@Select("select * from use_equipment_title where equipmentId=#{equipmentId} and fileId = #{fileId}")
	public UseEquipmentTitlePo findOne(@Param("equipmentId") String  equipmentId,@Param("fileId") String  fileId);


	@Delete("DELETE FROM use_equipment_title WHERE equipmentId = #{equipmentId}")
	public void deleteEquipmentId(@Param("equipmentId") String  equipmentId);

	@Delete("DELETE FROM use_equipment_title WHERE equipmentId = #{equipmentId} and fileId = #{fileId}")
	public void deleteEquipmentFileId(@Param("equipmentId") String  equipmentId,@Param("fileId") String  fileId);


}