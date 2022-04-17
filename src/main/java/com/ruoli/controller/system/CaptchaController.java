package com.ruoli.controller.system;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.ruoli.common.Constants;
import com.ruoli.common.core.AjaxResult;
import com.ruoli.common.redis.RedisCache;
import com.ruoli.config.RuoLiConfig;
import com.ruoli.enums.CaptchaType;
import com.ruoli.mapper.SysMenuMapper;
import com.ruoli.service.IConfigService;
import com.ruoli.service.impl.ConfigService;
import com.ruoli.utils.Base64;
import com.ruoli.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/captchaImage")
public class CaptchaController
{
    private IConfigService configService;

    private RedisCache redisCache;

    @Resource(name = "charCaptchaProducer")
    private DefaultKaptcha charCaptchaProducer;

    @Resource(name = "mathCaptchaProducer")
    private DefaultKaptcha mathCaptchaProducer;

    @Autowired
    public CaptchaController(IConfigService configService,RedisCache redisCache)
    {
        this.configService = configService;
        this.redisCache = redisCache;

    }

    @GetMapping
    public AjaxResult getCaptcha()
    {
        AjaxResult ajaxResult = new AjaxResult();
        boolean captchaButton = configService.getCaptchaButton();
        ajaxResult.put("captchaButton",captchaButton);
        if(captchaButton == false)
        {
            ajaxResult.setSuccess();
            return ajaxResult;
        }

        /**
        * produce captcha and save it into redis cache
        * */
        String uuid = IdUtils.getUUID();
        String captchaToken = Constants.CAPTCHA_CODE_KEY + uuid;

        String captchaText;
        BufferedImage captchaImage;
        String captchaResult;

        if(configService.getCaptchaType() == CaptchaType.Char)
        {
            captchaText = charCaptchaProducer.createText();
            captchaImage = charCaptchaProducer.createImage(captchaText);
            captchaResult = captchaText;
        }
        else
        {
            captchaText = mathCaptchaProducer.createText();
            String[] splitTextResult = captchaText.trim().split("@");
            captchaText = splitTextResult[0];
            captchaResult = splitTextResult[1];
            captchaImage = mathCaptchaProducer.createImage(captchaText);
        }
        FastByteArrayOutputStream fastByteArrayOutputStream = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(captchaImage,"jpg",fastByteArrayOutputStream);
        }
        catch (Exception e)
        {
            ajaxResult.setError("captcha produce error:" + e.getMessage());
            return ajaxResult;
        }
        redisCache.setCacheObject(captchaToken,captchaResult,Constants.CAPTCHA_EXPIRE_MINUTE, TimeUnit.MINUTES);
        ajaxResult.put("uuid",uuid);
        ajaxResult.put("captchaType",configService.getCaptchaType());
        ajaxResult.put("img", Base64.encode(fastByteArrayOutputStream.toByteArray()));
        ajaxResult.setSuccess();

        return ajaxResult;
    }
}
