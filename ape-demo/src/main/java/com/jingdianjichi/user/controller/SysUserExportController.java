package com.jingdianjichi.user.controller;

import com.jingdianjichi.user.excel.SysUserExcelExport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户数据导出controller
 *
 * @author: ChickenWing
 * @date: 2023/3/5
 */
@Api(tags = "用户数据导出controller")
@RestController
@RequestMapping(value = "sysUserExport")
@Slf4j
public class SysUserExportController {

    @Resource
    private SysUserExcelExport sysUserExcelExport;

    @ApiOperation(value = "导出excel", notes = "导出excel")
    @GetMapping("exportData")
    public void exportData() {
        //指定数据条件
        Map<String, Object> map = new HashMap<>();
        sysUserExcelExport.exportWithBigData("用户列表", map);
    }

}