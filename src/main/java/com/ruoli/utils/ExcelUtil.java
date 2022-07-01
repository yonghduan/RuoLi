package com.ruoli.utils;

import com.ruoli.common.annotation.Excel;
import com.ruoli.common.annotation.Excels;
import com.ruoli.common.core.text.Convert;
import net.sf.jsqlparser.expression.operators.relational.OldOracleJoinBinaryExpression;
import org.apache.commons.lang3.RegExUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelUtil<T>
{

    public static final int sheetSize = 65536;

    private String sheetName;

    private Excel.Type type;

    private List<T> list;

    private String title;

    private List<Object[]> fields;

    public Class<T> clazz;

    private short maxHeight;

    public Workbook workbook;

    private Sheet sheet;

    private int rownum;

    private Map<String, CellStyle> styles;

    public static final String[] FORMULA_STR = {"=","-","+","@"};

    public static final String FORMULA_REGEX_STR = "|-|\\+|@";


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
        exportExcel(response);
    }

    public void exportExcel(HttpServletResponse response)
    {

    }

    public void writeSheet()
    {
        int sheetNo = Math.max(1,(int)Math.ceil(list.size() * 1.0 / sheetSize));
        for(int index = 0;index < sheetNo;index ++)
        {
            createSheet(sheetNo,index);

            Row row = sheet.createRow(rownum);
            int column = 0;

            for(Object[] objects : fields)
            {
                Excel excel = (Excel) objects[1];
                this.createCell(excel,row,column ++);
            }

            if(Excel.Type.EXPORT.equals(type))
            {
                fillExcelData(index,row);
            }
        }
    }

    public void fillExcelData(int index,Row row)
    {
        int startNo = index * sheetSize;
        int endNo = Math.min(startNo + sheetSize,list.size());
        for(int i = startNo;i < endNo;i ++)
        {
            row = sheet.createRow(i + 1 + rownum - startNo);
            T vo = (T) list.get(i);
            int column = 0;
            for(Object[] os : fields)
            {
                Field field = (Field) os[0];
                Excel excel = (Excel) os[1];

            }
        }
    }

    public Cell addCell(Excel attr,Row row,T vo,Field field,int column) throws Exception
    {
        Cell cell = null;


        return null;
    }

    public void setCellVo(Object value,Excel attr,Cell cell)
    {
        if(Excel.ColumnType.STRING == attr.cellType())
        {
            String cellValue = Convert.toStr(value);
            if(org.apache.commons.lang3.StringUtils.startsWithAny(cellValue,FORMULA_STR))
            {
                cellValue = RegExUtils.replaceFirst(cellValue,FORMULA_REGEX_STR,"\t$0");
            }
        }
    }

    public static String convertByExp(String propertyValue, String converterExp, String separator)
    {
        StringBuilder propertyString = new StringBuilder();
        String[] convertSource = converterExp.split(",");
        for (String item : convertSource)
        {
            String[] itemArray = item.split("=");
            if (org.apache.commons.lang3.StringUtils.containsAny(separator, propertyValue))
            {
                for (String value : propertyValue.split(separator))
                {
                    if (itemArray[0].equals(value))
                    {
                        propertyString.append(itemArray[1] + separator);
                        break;
                    }
                }
            }
            else
            {
                if (itemArray[0].equals(propertyValue))
                {
                    return itemArray[1];
                }
            }
        }
        return org.apache.commons.lang3.StringUtils.stripEnd(propertyString.toString(), separator);
    }

    public String parseDateToStr(String dateFormat, Object val)
    {
        if (val == null)
        {
            return "";
        }
        String str;
        if (val instanceof Date)
        {
            str = DateUtils.parseDateToStr(dateFormat, (Date) val);
        }
        else if (val instanceof LocalDateTime)
        {
            str = DateUtils.parseDateToStr(dateFormat, DateUtils.toDate((LocalDateTime) val));
        }
        else if (val instanceof LocalDate)
        {
            str = DateUtils.parseDateToStr(dateFormat, DateUtils.toDate((LocalDate) val));
        }
        else
        {
            str = val.toString();
        }
        return str;
    }

    private Object getTargetValue(T vo, Field field, Excel excel) throws Exception
    {
        Object o = field.get(vo);
        if (StringUtils.isNotEmpty(excel.targetAttr()))
        {
            String target = excel.targetAttr();
            if (target.contains("."))
            {
                String[] targets = target.split("[.]");
                for (String name : targets)
                {
                    o = getValue(o, name);
                }
            }
            else
            {
                o = getValue(o, target);
            }
        }
        return o;
    }

    private Object getValue(Object o,String name) throws Exception
    {
        if(StringUtils.isNotNull(o) && StringUtils.isNotEmpty(name))
        {
            Class<?> clazz = o.getClass();
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            o = field.get(o);
        }
        return o;
    }

    public Cell createCell(Excel attr,Row row,int column)
    {
        Cell cell = row.createCell(column);
        cell.setCellValue(attr.name());
        setDataValidation(attr,row,column);
        cell.setCellStyle(styles.get("header"));
        return cell;
    }

    public void setDataValidation(Excel attr,Row row,int column)
    {
        if(attr.name().indexOf("注： ") >= 0)
        {
            sheet.setColumnWidth(column,6000);
        }
        else
        {
            sheet.setColumnWidth(column,(int)((attr.width() + 0.72) * 256));
        }

        if(StringUtils.isNotEmpty(attr.prompt()) || attr.combo().length > 0)
        {
            setPromptOrValidation(sheet, attr.combo(), attr.prompt(), 1, 100, column, column);
        }
    }

    public void setPromptOrValidation(Sheet sheet, String[] textlist, String promptContent, int firstRow, int endRow,
                                      int firstCol, int endCol)
    {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = textlist.length > 0 ? helper.createExplicitListConstraint(textlist) : helper.createCustomConstraint("DD1");
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        if (StringUtils.isNotEmpty(promptContent))
        {
            // 如果设置了提示信息则鼠标放上去提示
            dataValidation.createPromptBox("", promptContent);
            dataValidation.setShowPromptBox(true);
        }
        // 处理Excel兼容性问题
        if (dataValidation instanceof XSSFDataValidation)
        {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        }
        else
        {
            dataValidation.setSuppressDropDownArrow(false);
        }
        sheet.addValidationData(dataValidation);
    }

    public void createSheet(int sheetNo,int index)
    {
        if(sheetNo > 1 && index > 0)
        {
            this.sheet = workbook.createSheet();
            this.createTitle();
            workbook.setSheetName(index,sheetName + index);
        }
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

    public void createTitle()
    {
        if (StringUtils.isNotEmpty(title))
        {
            Row titleRow = sheet.createRow(rownum == 0 ? rownum++ : 0);
            titleRow.setHeightInPoints(30);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(styles.get("title"));
            titleCell.setCellValue(title);
            sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), titleRow.getRowNum(),
                    this.fields.size() - 1));
        }
    }

    public void createWorkbook()
    {
        this.workbook = new SXSSFWorkbook(500);
        this.sheet = workbook.createSheet();
        workbook.setSheetName(0,sheetName);
        this.styles = createStyles(workbook);
    }

    private Map<String,CellStyle> createStyles(Workbook workbook)
    {
        Map<String, CellStyle> styles = new HashMap<>();
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font titleFont = workbook.createFont();
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        style.setFont(titleFont);
        styles.put("title", style);

        style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = workbook.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        style.setFont(dataFont);
        styles.put("data", style);

        style = workbook.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = workbook.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
        styles.put("header", style);

        style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font totalFont = workbook.createFont();
        totalFont.setFontName("Arial");
        totalFont.setFontHeightInPoints((short) 10);
        style.setFont(totalFont);
        styles.put("total", style);

        style = workbook.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.LEFT);
        styles.put("data1", style);

        style = workbook.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        styles.put("data2", style);

        style = workbook.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        styles.put("data3", style);

        return styles;
    }

    private void createExcelField()
    {
        this.fields = getFields();
        this.fields = this.fields.stream().sorted(Comparator.comparing(objects -> ((Excel)objects[1]).sort())).collect(Collectors.toList());
        this.maxHeight = getRowHeight();
    }

    public short getRowHeight()
    {
        double maxHeight = 0L;
        for(Object[] objects : this.fields)
        {
            Excel excel = (Excel) objects[1];
            maxHeight = Math.max(maxHeight,excel.height());
        }
        return (short)(maxHeight * 20);
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
