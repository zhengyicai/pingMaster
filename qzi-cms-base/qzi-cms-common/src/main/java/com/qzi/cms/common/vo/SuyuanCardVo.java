package com.qzi.cms.common.vo;

import com.qzi.cms.common.po.SuyuanCardPo;

import javax.persistence.Table;
import java.util.Date;
import java.util.List;


public class SuyuanCardVo {

    private String id;
    private String name;
    private String phone;
    private String code;
    private String state;
    private String remark;
    private Date createTime;

    private String cardNo;
    private String type;

    private String address;




    private List<SuyuanCardPo> list;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<SuyuanCardPo> getList() {
        return list;
    }

    public void setList(List<SuyuanCardPo> list) {
        this.list = list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
