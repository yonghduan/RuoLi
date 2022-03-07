package com.ruoli.service.factory;

import com.ruoli.common.Constants;
import com.ruoli.entity.datasource.RecordLoginInfoTable;
import com.ruoli.enums.LoginFlag;
import com.ruoli.mapper.RecordLoginInfoMapper;
import com.ruoli.utils.TimeUtils;
import com.ruoli.utils.web.AddressUtils;
import com.ruoli.utils.web.IPUtils;
import com.ruoli.utils.web.ServletUtils;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.util.Date;
import java.util.TimerTask;

@Service
public class AsyncTaskFactory
{
    @Resource
    private RecordLoginInfoMapper recordLoginInfoMapper;

    public TimerTask recordLoginInfo(final String username, final LoginFlag loginFlag,final String message)
    {
        HttpServletRequest httpServletRequest = ServletUtils.getRequest();
        final UserAgent userAgent = UserAgent.parseUserAgentString(httpServletRequest.getHeader("User-Agent"));
        String ip = IPUtils.getIpAddress(httpServletRequest);
        return new TimerTask()
        {
            @Override
            public void run()
            {
                final RecordLoginInfoTable recordLoginInfoTable = new RecordLoginInfoTable();
                recordLoginInfoTable.setUsername(username);
                recordLoginInfoTable.setIpAddress(ip);
                recordLoginInfoTable.setBrowser(userAgent.getBrowser().getName());
                recordLoginInfoTable.setOperateSystem(userAgent.getOperatingSystem().getName());

                String address = AddressUtils.getRealAddressByIP(ip);
                recordLoginInfoTable.setIpLocation(address);
                /**
                 * 1.id值的设置，自动增加？还是手动设置（id值自动增加，设置首值为100）
                 * 2.时间如何设置？sql时间格式，数据库如何创建时间(其为sql语句中获取时间）？
                 * 3.status的类型应该为如何？只需要两个值还是多个值？数据库类设置为char（1）*/
                if(loginFlag == LoginFlag.CAPTCHA_ERROR || loginFlag == LoginFlag.CAPTCHA_EXPIRE)
                {
                    recordLoginInfoTable.setStatus(Constants.LOGIN_FAIL_STATUS);
                }
                else
                {
                    recordLoginInfoTable.setStatus(Constants.LOGIN_SUCCESS_STATUS);
                }
                recordLoginInfoTable.setLoginTime(TimeUtils.getSqlTime());
                recordLoginInfoTable.setMsg(message);
                recordLoginInfoMapper.insert(recordLoginInfoTable);
            }
        };
    }
}
