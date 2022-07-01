package com.ruoli.controller.system;

import com.ruoli.common.core.AjaxResult;
import com.ruoli.entity.datasource.SystemUserTable;
import com.ruoli.service.system.IUserService;
import com.ruoli.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/system/user")
public class SysUserController
{
    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    @PreAuthorize("@ss.hasPerm('system:user:list')")
    public AjaxResult list()
    {
        return AjaxResult.success();
    }

    @GetMapping("/export")
    @PreAuthorize("@ss.hasPerm('system:user:export')")
    public void export(HttpServletResponse response) throws IOException
    {
        List<SystemUserTable> userList = userService.selectUserList();
        ExcelUtil<SystemUserTable> util = new ExcelUtil<>(SystemUserTable.class);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheet01");

        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("编号");
        row.createCell(1).setCellValue("名称");
        row.createCell(2).setCellValue("年龄");

        XSSFRow row1 = sheet.createRow(1);
        row.createCell(0).setCellValue("1");
        row.createCell(1).setCellValue("小明");
        row.createCell(2).setCellValue("10");

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");

        try
        {
            workbook.write(response.getOutputStream());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            workbook.close();
        }

    }


}
