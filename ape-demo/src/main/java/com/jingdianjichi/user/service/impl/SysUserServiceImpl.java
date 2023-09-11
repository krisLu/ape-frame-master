package com.jingdianjichi.user.service.impl;

import com.jingdianjichi.bean.PageResponse;
import com.jingdianjichi.user.convert.SysUserConverter;
import com.jingdianjichi.user.entity.po.SysUser;
import com.jingdianjichi.user.dao.SysUserDao;
import com.jingdianjichi.user.entity.req.SysUserReq;
import com.jingdianjichi.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * (SysUser)表服务实现类
 *
 * @author makejava
 * @since 2022-09-10 21:14:47
 */
@Service("sysUserService")
@Slf4j
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserDao sysUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    @Cacheable(cacheNames = "sysUser", key = "'querySysUserById'+#id")
    public SysUser queryById(Long id) {
        System.out.println("1");
        return this.sysUserDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @Override
    public PageResponse<SysUser> queryByPage(SysUserReq sysUserReq) {
        SysUser sysUser = SysUserConverter.INSTANCE.convertReqToSysUser(sysUserReq);
        PageResponse<SysUser> pageResponse = new PageResponse<>();
        pageResponse.setCurrent(sysUserReq.getPageNo());
        pageResponse.setPageSize(sysUserReq.getPageSize());
        Long pageStart = (sysUserReq.getPageNo() - 1) * sysUserReq.getPageSize();
        long total = this.sysUserDao.count(sysUser);
        List<SysUser> sysUserList = this.sysUserDao.queryAllByLimit(sysUser, pageStart, sysUserReq.getPageSize());
        pageResponse.setTotal(total);
        pageResponse.setRecords(sysUserList);
        return pageResponse;
    }

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @Override
    public SysUser insert(SysUser sysUser) {
        this.sysUserDao.insert(sysUser);
        return sysUser;
    }

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @Override
    public SysUser update(SysUser sysUser) {
        this.sysUserDao.update(sysUser);
        return this.queryById(sysUser.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.sysUserDao.deleteById(id) > 0;
    }

    @Override
    public List<SysUser> queryByExport(Map<String, Object> conditions) {
        SysUser sysUser = new SysUser();
        sysUser.setId(Long.valueOf(conditions.get("id").toString()));
        return sysUserDao.queryAll(sysUser);
    }

    @Override
    public Long queryCount(Map<String, Object> conditions) {
        SysUser sysUser = new SysUser();
        return sysUserDao.count(sysUser);
    }
}
