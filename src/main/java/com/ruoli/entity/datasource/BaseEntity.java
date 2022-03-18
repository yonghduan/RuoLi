package com.ruoli.entity.datasource;

import java.sql.Time;

public class BaseEntity
{
    private String createdBy;
    private String createdTime;
    private String updatedBy;
    private String updatedTime;
    private String remark;

    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    public void setCreatedTime(String createdTime)
    {
        this.createdTime = createdTime;
    }

    public void setUpdatedBy(String updatedBy)
    {
        this.updatedBy = updatedBy;
    }

    public void setUpdatedTime(String updatedTime)
    {
        this.updatedTime = updatedTime;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }

    public String getCreatedTime()
    {
        return createdTime;
    }

    public String getUpdatedBy()
    {
        return updatedBy;
    }

    public String getUpdatedTime()
    {
        return updatedTime;
    }

    public String getRemark()
    {
        return remark;
    }
}
