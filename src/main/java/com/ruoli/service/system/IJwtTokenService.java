package com.ruoli.service.system;

import com.ruoli.entity.common.SuccessfullyLoginUser;

public interface IJwtTokenService
{
    String generateToken(SuccessfullyLoginUser successfullyLoginUser);

    void generateExpireTime(SuccessfullyLoginUser successfullyLoginUser);

    void refreshExpireTime();

    boolean authenticationByToken(String token);
}
