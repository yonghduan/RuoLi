package com.ruoli.service.system;

import java.util.Collection;

public interface UserAuthoritiesService
{
    public Collection<?> generateAuthoritiesForUser();

    public Collection<?> searchUserAuthorities();
}
