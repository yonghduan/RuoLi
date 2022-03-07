package com.ruoli.entity.datasource;

import java.sql.Time;

public class BaseEntity
{
    private String createdBy;
    private java.sql.Time createdTime;
    private String updatedBy;
    private java.sql.Time updatedTime;
    private String remark;

    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    public void setCreatedTime(Time createdTime)
    {
        this.createdTime = createdTime;
    }

    public void setUpdatedBy(String updatedBy)
    {
        this.updatedBy = updatedBy;
    }

    public void setUpdatedTime(Time updatedTime)
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

    public Time getCreatedTime()
    {
        return createdTime;
    }

    public String getUpdatedBy()
    {
        return updatedBy;
    }

    public Time getUpdatedTime()
    {
        return updatedTime;
    }

    public String getRemark()
    {
        return remark;
    }
}
