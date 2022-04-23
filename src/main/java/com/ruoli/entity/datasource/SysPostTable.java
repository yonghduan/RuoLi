package com.ruoli.entity.datasource;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("sys_post")
@Data
public class SysPostTable extends BaseEntity
{
    @TableId(type = IdType.AUTO)
    private Long postId;

    private String postCode;

    private String postName;

    private Character postSort;

    private Character status;
}
