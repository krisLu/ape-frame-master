package com.jingdianjichi.user.controller;

import com.jingdianjichi.annoation.ResultEncrypt;
import com.jingdianjichi.bean.PageResponse;
import com.jingdianjichi.bean.Result;
import com.jingdianjichi.user.entity.po.SysUser;
import com.jingdianjichi.user.entity.req.SysUserReq;
import com.jingdianjichi.user.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (SysUser)表控制层
 *
 * @author makejava
 * @since 2022-09-10 21:14:34
 */
@RestController
@RequestMapping("sysUser")
@Api(tags = "用户controller")
public class SysUserController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @GetMapping
    public Result<PageResponse<SysUser>> queryByPage(SysUserReq sysUserReq) {
        return Result.ok(this.sysUserService.queryByPage(sysUserReq));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @ResultEncrypt("$.name")
    public Result<SysUser> queryById(@PathVariable("id") Long id) {
        return Result.ok(this.sysUserService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param sysUser 实体
     * @return 新增结果
     */
    @PostMapping
    @ApiOperation(value = "新增用户",notes = "用户数据")
    public Result<SysUser> add(@RequestBody SysUser sysUser) {
        return Result.ok(this.sysUserService.insert(sysUser));
    }

    /**
     * 编辑数据
     *
     * @param sysUser 实体
     * @return 编辑结果
     */
    @PutMapping
    public Result<SysUser> edit(@RequestBody SysUser sysUser) {
        return Result.ok(this.sysUserService.update(sysUser));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public Result<Boolean> deleteById(Long id) {
        return Result.ok(this.sysUserService.deleteById(id));
    }

}

