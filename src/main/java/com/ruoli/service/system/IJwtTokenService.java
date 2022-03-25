package com.ruoli.service.system;

import com.ruoli.entity.common.SuccessfullyLoginUser;

import javax.servlet.http.HttpServletRequest;

public interface IJwtTokenService
{
    String generateToken(SuccessfullyLoginUser successfullyLoginUser);

    void generateExpireTime(SuccessfullyLoginUser successfullyLoginUser);

    void refreshExpireTime(SuccessfullyLoginUser successfullyLoginUser);

    boolean authenticationByToken(String token);

    SuccessfullyLoginUser getSuccessfullyLoginUser(HttpServletRequest request);
}
