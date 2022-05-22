package com.ruoli.common.annotation;

import lombok.val;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel
{
    public String name() default "";

    public double width() default 16;

    public String prompt() default "";

    public String[] combo() default {};

    public boolean isExport() default true;

    Type type() default Type.ALL;

    public enum Type
    {
        ALL(0),EXPORT(1),IMPORT(2);
        private final int value;

        Type(int value){
            this.value = value;
        }

        public int value(){return this.value;}
    }

    public Align align() default Align.AUTO;

    public enum Align
    {
        AUTO(0),LEFT(1),CENTER(2),RIGHT(3);
        private final int value;

        Align(int value){this.value = value;}

        public int value(){return this.value;}
    }

    public int sort() default Integer.MAX_VALUE;

    public double height() default 14;

    public String targetAttr() default "";

    public String dateFormat() default "";

    public String dictType() default "";

    /**
     * 读取内容转表达式 (如: 0=男,1=女,2=未知)
     */
    public String readConverterExp() default "";

    /**
     * 分隔符，读取字符串组内容
     */
    public String separator() default ",";

    /**
     * BigDecimal 精度 默认:-1(默认不开启BigDecimal格式化)
     */
    public int scale() default -1;

    /**
     * BigDecimal 舍入规则 默认:BigDecimal.ROUND_HALF_EVEN
     */
    public int roundingMode() default BigDecimal.ROUND_HALF_EVEN;

    /**
     * 导出类型（0数字 1字符串）
     */
    public ColumnType cellType() default ColumnType.STRING;

    public enum ColumnType
    {
        NUMERIC(0), STRING(1), IMAGE(2);
        private final int value;

        ColumnType(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }
}
