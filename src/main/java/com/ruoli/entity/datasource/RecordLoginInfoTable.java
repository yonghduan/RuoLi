package com.ruoli.entity.datasource;

import lombok.Data;

@Data
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
    private java.sql.Time loginTime;

}
