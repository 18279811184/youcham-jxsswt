package io.youcham.modules.sys.entity;


import com.baomidou.mybatisplus.annotations.*;
import com.fasterxml.jackson.annotation.JsonFormat;


import java.util.Date;

@TableName("sys_declaration_date")
public class SysDeclarationDateEntity {

    /**
     * id
     */
    @TableId
    private String id;

    /**
     * 创建人
     */
    private String createId;

    /**
     * 创建时间
     */
    private Date   createTime;

    /**
     * 修改人
     */
    private String updateId;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 版本号
     */
    @Version
    private Integer version;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer delFlag;

    /**
     * 月份
     */
    private Integer month;

    /**
     * 截止日期
     */
    @JsonFormat (pattern = "yyyy-MM-dd",timezone = "GMT+8")//格式化时间，让前台能够正确的显示出来
    private Date theDate;

    @TableField (exist =  false)
    private String caoZuo;


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Date getTheDate() {
        return theDate;
    }

    public void setTheDate(Date theDate) {
        this.theDate = theDate;
    }

    public String getCaoZuo() {
        return caoZuo;
    }

    public void setCaoZuo(String caoZuo) {
        this.caoZuo = caoZuo;
    }
}
