package com.jingdianjichi.user.convert;

import com.jingdianjichi.user.entity.po.SysUser;
import com.jingdianjichi.user.entity.req.SysUserReq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SysUserConverter {

    SysUserConverter INSTANCE = Mappers.getMapper(SysUserConverter.class);

    SysUser convertReqToSysUser(SysUserReq sysUserReq);

}
