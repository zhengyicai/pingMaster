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
import com.qzi.cms.common.po.SysEquipmentPo;
import com.qzi.cms.common.vo.SysEquipmentVo;
import com.qzi.cms.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 设置file
 * @author qsy
 * @version v1.0
 * @date 2017年6月15日
 */
public interface SuyuanCardMapper extends BaseMapper<SuyuanCardPo>{


    public List<SuyuanCardPo> findAll(@Param("model") SuyuanCardPo suyuanCardPo, @Param("startRow") int startRow, @Param("pageSize") int pageSize);

    public long findCount(@Param("model") SuyuanCardPo suyuanCardPo);

    @Select("select * from suyuan_card where cardNo=#{cardNo} and state='10' order by createTime asc limit 1 ")
    public SuyuanCardPo findCardNo(@Param("cardNo") String cardNo);


    @Update("update suyuan_card set name=#{model.name},phone=#{model.phone},code=#{model.code},state=#{model.state},address=#{model.address},cardNo=#{model.cardNo},type=#{model.type},weight=#{model.weight} where id = #{model.id}")
    public void update1(@Param("model") SuyuanCardPo suyuanCardPo);



}
