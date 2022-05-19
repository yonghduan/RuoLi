package com.ruoli.utils;

import com.ruoli.common.annotation.Excel;
import com.ruoli.common.annotation.Excels;
import net.sf.jsqlparser.expression.operators.relational.OldOracleJoinBinaryExpression;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExcelUtil<T>
{
    private String sheetName;

    private Excel.Type type;

    private List<T> list;

    private String title;

    private List<Object[]> fields;

    public Class<T> clazz;


    public ExcelUtil(Class<T> clazz)
    {
        this.clazz = clazz;
    }

    public void exportExcel(HttpServletResponse response, List<T> list,String sheetName)
    {
        exportExcel(response,list,sheetName,StringUtils.EMPTY);
    }

    public void exportExcel(HttpServletResponse response,List<T> list,String sheetName,String title)
    {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        this.init(list,sheetName,title, Excel.Type.EXPORT);
    }

    public void init(List<T> list, String sheetName, String title, Excel.Type type)
    {
        if(list == null)
        {
            list = new ArrayList<T>();
        }
        this.list = list;
        this.sheetName = sheetName;
        this.type = type;
        this.title = title;

        createExcelField();
        createWorkbook();
        createTitle();

    }

    private void createExcelField()
    {
        this.fields = getFields();
    }

    public List<Object[]> getFields()
    {
        List<Object[]> fields = new ArrayList<>();
        List<Field> tempFields = new ArrayList<>();
        tempFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        for(Field field : tempFields)
        {
            //单注解
            if(field.isAnnotationPresent(Excel.class))
            {
                Excel attr = field.getAnnotation(Excel.class);
                if(attr != null && (attr.type() == Excel.Type.ALL || attr.type() == type))
                {
                    field.setAccessible(true);
                    fields.add(new Object[]{field,attr});
                }
            }

            //多注解
            if(field.isAnnotationPresent(Excels.class))
            {
                Excels excels = field.getAnnotation(Excels.class);
                Excel[] attrs = excels.value();

                for(Excel attr : attrs)
                {
                    if(attr != null && (attr.type() == Excel.Type.ALL || attr.type() == type))
                    {
                        field.setAccessible(true);
                        fields.add(new Object[]{field,attr});
                    }
                }
            }
        }
        return fields;
    }

}
