package com.ruoli.common.exceptions;

import com.ruoli.enums.ServiceExceptionType;

public class ServiceException extends RuntimeException
{
    private ServiceExceptionType serviceExceptionType;

    public ServiceException(){}

    public ServiceException(ServiceExceptionType serviceExceptionType)
    {
        this.serviceExceptionType = serviceExceptionType;
    }

    public ServiceExceptionType getServiceExceptionType(){return serviceExceptionType;}
}
