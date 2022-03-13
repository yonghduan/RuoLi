package com.ruoli.entity.datasource;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("record_login_info")
public class RecordLoginInfoTable
{

    private Long id;
    private String username;
    /**
     * can require form http request*/
    private String ipAddress;
    private String ipLocation;
    private String operateSystem;
    private String browser;
    private String status;
    private String msg;
    private String loginTime;

}
