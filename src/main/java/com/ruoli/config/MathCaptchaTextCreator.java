package com.ruoli.config;

import com.google.code.kaptcha.text.impl.DefaultTextCreator;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

public class MathCaptchaTextCreator extends DefaultTextCreator
{
    /**
     * 随机生成0~10范围内的两个随机数
     * 随机生成加减乘除四个符号中的任意一个符号
     * 组合成一个字符串*/
    private String[] numberStr = "0,1,2,3,4,5,6,7,8,9,10".split(",");
    private String[] operatorStr = "+,-,*".split(",");

    private int computeMathResult(int numFirst,int numSecond,int numOperator)
    {
            int result;
            switch (numOperator)
            {
                case 0:
                    result = numFirst + numSecond;
                    break;
                case 1:
                    result = numFirst - numSecond;
                    break;
                case 2:
                    result = numFirst * numSecond;
                    break;
                default:
                    result = 0;
                    break;
            }
            return result;
    }

    @Override
    public String getText()
    {
        Random random = new Random();
        int numFirst = random.nextInt(11);
        int numSecond = random.nextInt(11);
        int numOperator = random.nextInt(3);

        int mathResult = computeMathResult(numFirst,numSecond,numOperator);

        String result = numberStr[numFirst] + operatorStr[numOperator] + numberStr[numSecond]
                + "@" + mathResult;
        return result;
    }

}
