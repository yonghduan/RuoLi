package com.ruoli.common.annotation;

import lombok.val;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel
{

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
}
