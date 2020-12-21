/* 
 * 文件名：SysParameterMapper.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月15日
 * 版本号：v1.0
*/
package com.qzi.cms.server.mapper;

import com.qzi.cms.common.po.SysFileUrlPo;
import com.qzi.cms.common.po.SysParameterPo;
import com.qzi.cms.common.vo.SysFileUrlVo;
import com.qzi.cms.common.vo.SysParameterVo;
import com.qzi.cms.server.base.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 文件管理DAO
 * @author qsy
 * @version v1.0
 * @date 2017年6月15日
 */
public interface SysFileUrlMapper extends BaseMapper<SysFileUrlPo>{


	public List<SysFileUrlVo> findAll(@Param("model") SysFileUrlPo sysFileUrlPo,@Param("startRow") int startRow, @Param("pageSize") int pageSize);

	public long findCount(@Param("model") SysFileUrlPo sysFileUrlPo);





	//查询该用户的所有资源数据
	@Select("select f.*  from sys_fileUrl f  where f.userId=#{userId} and f.state = '10'")
	public List<SysFileUrlVo> findAllList(@Param("userId") String userId);


	//查询该用户的所有资源数据
	@Select("select f.*  from sys_fileUrl f  where  (f.userId=#{userId} or f.userName='adminA') and f.state = '10'")
	public List<SysFileUrlVo> findAllList1(@Param("userId") String userId);


	@Select("select count(1) from sys_fileUrl where userId=#{userId}  or f.userName='adminA'")
	public Integer findAllCount1(@Param("userId") String userId);


	@Select("select count(1) from sys_fileUrl where userId=#{userId}")
	public Integer findCount1(@Param("userId") String userId);


	@Delete("delete from sys_fileUrl where id=#{id}")
	public void delete1(@Param("id") String id);


	@Select("select count(1) from sys_fileUrl  where state='10' and  remark=#{remark} and (userName=#{userName} or userName='adminA')")
	public Integer findAllChoCount(@Param("userName") String userName,@Param("remark") String remark);














}
