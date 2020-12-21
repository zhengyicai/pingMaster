package com.qzi.cms.common.vo;

import java.util.Date;

/**
 * Created by Administrator on 2019/6/9.
 */
public class SysEquipmentVo {
        private String id;

    	private String userName;

        private String equipmentNo;

        private String equipmentName1;


    	private String userId;

    	/**
    	 * 文件类型
    	 * 10图片 20视频
    	 */
    	private String type;

    	private String state;

    	private Date createTime;

        private Date updateTime;

    	private String  remark;

    	private String status;


    	private String[] equipmentIds;

    private String  agentUserId;
    private String  agentUserName;
    private String titleStatus;


    private String titleDetail;

    private String runTime;

    private String updateTimes;

    private String showCount;

    private String imgUrl;

    private String showMin;


    public String getShowMin() {
        return showMin;
    }

    public void setShowMin(String showMin) {
        this.showMin = showMin;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getShowCount() {
        return showCount;
    }

    public void setShowCount(String showCount) {
        this.showCount = showCount;
    }

    public String getUpdateTimes() {
        return updateTimes;
    }

    public void setUpdateTimes(String updateTimes) {
        this.updateTimes = updateTimes;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public String getTitleStatus() {
        return titleStatus;
    }

    public void setTitleStatus(String titleStatus) {
        this.titleStatus = titleStatus;
    }

    public String getTitleDetail() {
        return titleDetail;
    }

    public void setTitleDetail(String titleDetail) {
        this.titleDetail = titleDetail;
    }

    public String getAgentUserId() {
        return agentUserId;
    }

    public void setAgentUserId(String agentUserId) {
        this.agentUserId = agentUserId;
    }

    public String getAgentUserName() {
        return agentUserName;
    }

    public void setAgentUserName(String agentUserName) {
        this.agentUserName = agentUserName;
    }

    public String[] getEquipmentIds() {
        return equipmentIds;
    }

    public void setEquipmentIds(String[] equipmentIds) {
        this.equipmentIds = equipmentIds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getEquipmentName1() {
        return equipmentName1;
    }

    public void setEquipmentName1(String equipmentName1) {
        this.equipmentName1 = equipmentName1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEquipmentNo() {
        return equipmentNo;
    }

    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
