/* 
 * 文件名：SysParameterMapper.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月15日
 * 版本号：v1.0
*/
package com.qzi.cms.server.mapper;

import com.qzi.cms.common.po.SuyuanClassesPo;
import com.qzi.cms.common.po.SysClassesPo;
import com.qzi.cms.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 设置file
 * @author qsy
 * @version v1.0
 * @date 2017年6月15日
 */
public interface SuyuanClassesMapper extends BaseMapper<SuyuanClassesPo>{







	public List<SuyuanClassesPo> findAll1(@Param("model") SuyuanClassesPo suyuanClassesPo, @Param("startRow") int startRow, @Param("pageSize") int pageSize);

	public long findCount1(@Param("model") SuyuanClassesPo suyuanClassesPo);


	/**
	 *
	 * @return
	 */

	@Select("select * from suyuan_classes where state='10'  order by  CAST(sort AS SIGNED)")
	public List<SuyuanClassesPo> findList();



}
