/* 
 * 文件名：SysParameterMapper.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月15日
 * 版本号：v1.0
*/
package com.qzi.cms.server.mapper;

import com.qzi.cms.common.po.SysFileUrlPo;
import com.qzi.cms.common.po.UseEquipmentFilePo;
import com.qzi.cms.common.vo.SysFileUrlVo;
import com.qzi.cms.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 设备文件管理DAO
 * @author qsy
 * @version v1.0
 * @date 2017年6月15日
 */
public interface UseEquipmentFileMapper extends BaseMapper<UseEquipmentFilePo>{

	@Select("select * from use_equipment_file where equipmentId = #{equipmentId}")
	public List<SysFileUrlVo> findAll( @Param("equipmentId") String  equipmentId);




}
