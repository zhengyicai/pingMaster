/* 
 * 文件名：SysParameterMapper.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月15日
 * 版本号：v1.0
*/
package com.qzi.cms.server.mapper;

import com.qzi.cms.common.po.SysEquipmentFilePo;
import com.qzi.cms.common.po.SysEquipmentPo;
import com.qzi.cms.common.po.SysParameterPo;
import com.qzi.cms.common.vo.SysEquipmentFileVo;
import com.qzi.cms.common.vo.SysParameterVo;
import com.qzi.cms.server.base.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import java.security.PublicKey;
import java.util.List;

/**
 * 设置file
 * @author qsy
 * @version v1.0
 * @date 2017年6月15日
 */
public interface SysEquipmentFileMapper extends BaseMapper<SysEquipmentFilePo>{

	/**
	 * @param equipmentId
	 * @return
	 */
	@Select("select ef.*, f.fileUrl,f.standards from sys_equipment_file ef  left join  sys_fileUrl f  on ef.fileId = f.id where ef.equipmentId = #{equipmentId}  order by ef.createTime desc")
	public List<SysEquipmentFileVo> findAll(@Param("equipmentId") String equipmentId);





	public List<SysEquipmentFileVo> findAll1(@Param("model") SysEquipmentFilePo sysEquipmentPo, @Param("startRow") int startRow, @Param("pageSize") int pageSize);

	public long findCount1(@Param("model") SysEquipmentFilePo sysEquipmentPo);


	@Delete("delete from  sys_equipment_file where equipmentId = #{equipmentId}")
	public void deleteList(@Param("equipmentId") String equipmentId);

	@Delete("delete from  sys_equipment_file where userId = #{userId}")
	public void deleteListSelect(@Param("userId") String userId);


	@Update("update sys_equipment_file set titleDetail = #{detail} where  equipmentId=#{equipmentId} and fileId =#{fileId} ")
	public  void updateTitle(@Param("equipmentId") String equipmentId,@Param("fileId") String fileId,@Param("detail") String detail);
	

    @Select("select * from sys_equipment_file where equipmentId=#{equipmentId} and fileId =#{fileId} limit 1 ")
	public SysEquipmentFileVo findOne(@Param("equipmentId") String equipmentId,@Param("fileId") String fileId);


	@Select("select * from sys_equipment_file where userId=#{userId}  order by sort desc limit 1 ")
	public SysEquipmentFileVo findMax(@Param("userId") String userId);



	@Select("select * from  sys_equipment_file where align='10' and   equipmentId=#{equipmentId} order by state asc,CAST(sort AS SIGNED)")
	public List<SysEquipmentFileVo> findEquipemntIdList(@Param("equipmentId") String equipmentId);



	@Select("select * from  sys_equipment_file where align='20' and   equipmentId=#{equipmentId} order by state asc,CAST(sort AS SIGNED)")
	public List<SysEquipmentFileVo> findEquipemntIdList1(@Param("equipmentId") String equipmentId);

	@Select("select * from  sys_equipment_file where align='30' and   equipmentId=#{equipmentId} order by state asc,CAST(sort AS SIGNED)")
	public List<SysEquipmentFileVo> findEquipemntIdList2(@Param("equipmentId") String equipmentId);



	@Select("select * from  sys_equipment_file where align='20' and state='10' and   equipmentNo=#{equipmentId} order by state asc,CAST(sort AS SIGNED)")
	public List<SysEquipmentFileVo> findEquipemntIdList1State(@Param("equipmentId") String equipmentId);


	@Select("select * from  sys_equipment_file where align='30' and state='10' and   equipmentNo=#{equipmentId} order by state asc,CAST(sort AS SIGNED)")
	public List<SysEquipmentFileVo> findEquipemntIdList2State(@Param("equipmentId") String equipmentId);


	@Select("select e.*,f.classes from sys_equipment_file e left join sys_fileUrl f  on e.fileId = f.fileUrl where  e.align='10' and e.equipmentId=#{equipmentId} and f.classes=#{classes} order by e.state asc,CAST(sort AS SIGNED)")
	public List<SysEquipmentFileVo> findEquipemntIdListClass(@Param("equipmentId") String equipmentId,@Param("classes") String classes);


	@Select("select e.*,f.classes from sys_equipment_file e left join sys_fileUrl f  on e.fileId = f.fileUrl where  e.align='10' and e.equipmentId=#{equipmentId} and f.classes=#{classes} and e.name=#{name} order by e.state asc,CAST(sort AS SIGNED)")
	public List<SysEquipmentFileVo> findEquipemntIdListName(@Param("equipmentId") String equipmentId,@Param("classes") String classes,@Param("name") String name);




	@Select("select e.*,f.classes from sys_equipment_file e left join sys_fileUrl f  on e.fileId = f.fileUrl where  e.align='10' and e.equipmentId=#{equipmentId} and e.name=#{name} order by e.state asc,CAST(sort AS SIGNED)")
	public List<SysEquipmentFileVo> findEquipemntIdListName1(@Param("equipmentId") String equipmentId,@Param("name") String name);

	@Select("select * from  sys_equipment_file where  id=#{id} limit 1")
	public SysEquipmentFilePo findEquipemntId(@Param("id") String id);


	@Update("update sys_equipment_file set updateState=#{updateState} where  id=#{id}")
	public void updateState(@Param("updateState") String updateState,@Param("id") String id);


	@Update("update sys_equipment_file set updateState=#{updateState} where  equipmentNo=#{id}")
	public void updateEquipmentState(@Param("updateState") String updateState,@Param("id") String id);




	@Update("update sys_equipment_file set updateState='10',state='20' where  equipmentNo=#{id}")
	public void updateEquipmentAllState(@Param("id") String id);


	@Update("update sys_equipment_file set updateState='10',state='20' where  equipmentId=#{id}")
	public void updateEquipmentAllStateId(@Param("id") String id);


	@Update("update sys_equipment_file set updateState='10' where  equipmentNo=#{id}")
	public void updateEquipmentAllUpdateState(@Param("id") String id);



	@Update("update sys_equipment_file set updateState='10',state='20' where  fileId=#{fileId}")
	public void updateImageAllState(@Param("fileId") String fileId);



	@Update("update sys_equipment_file set updateState='10' and equipmentNo=#{equipmentNo}")
	public void updateAllState(@Param("equipmentNo") String equipmentNo);



	@Delete("delete from  sys_equipment_file where fileId = #{fileId}")
	public void deleteListAll(@Param("fileId") String fileId);



	@Update("update sys_equipment_file set updateState='10'")
	public void updateListAll();

	@Update("update sys_equipment_file  set updateState='10' where equipmentNo in (select equipmentNo from sys_equipment where userName=#{userName}) ")
	public void updateListAllUser(@Param("userName") String userName);








}
