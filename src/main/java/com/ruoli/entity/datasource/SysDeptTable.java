package com.ruoli.entity.datasource;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_dept")
public class SysDeptTable extends BaseEntity
{
    @TableId(type = IdType.AUTO)
    private Long deptId;

    private Long parentId;

    private String ancestors;

    private String deptName;

    private Integer orderNum;

    private String leader;

    private String phone;

    private String email;

    private Character status;

    private Character delFlag;
}
