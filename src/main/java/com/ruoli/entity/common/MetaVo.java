package com.ruoli.entity.common;

import lombok.Data;

@Data
public class MetaVo
{
    private String title;

    private String icon;

    private boolean noCache;

    private String link;

    public MetaVo(String icon,boolean noCache)
    {
        this.icon = icon;
        this.noCache = noCache;
    }

    public MetaVo(String icon,Integer noCache)
    {
        this.icon = icon;
        if(noCache.intValue() == 0)
            this.noCache = true;
        else
            this.noCache = false;
    }
}
