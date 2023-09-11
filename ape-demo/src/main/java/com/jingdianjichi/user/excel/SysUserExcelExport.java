package com.jingdianjichi.user.excel;

import com.jingdianjichi.bean.PageResponse;
import com.jingdianjichi.tool.excel.BaseEasyExcelExport;
import com.jingdianjichi.user.entity.po.SysUser;
import com.jingdianjichi.user.entity.req.SysUserReq;
import com.jingdianjichi.user.service.SysUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 商品数据导出
 *
 * @author: ChickenWing
 * @date: 2023/3/5
 */
@Component
public class SysUserExcelExport extends BaseEasyExcelExport<SysUser> {

    @Resource
    private SysUserService sysUserService;

    /**
     * 数据导出
     */
    public void exportWithBigData(String fileName, Map<String, Object> conditions) {
        this.exportExcel(fileName, conditions);
    }

    @Override
    protected List<List<String>> getExcelHead() {
        List<List<String>> head = new ArrayList<>();
        head.add(Collections.singletonList("用户编号"));
        head.add(Collections.singletonList("用户姓名"));
        return head;
    }

    @Override
    protected Long dataTotalCount(Map<String, Object> conditions) {
        return sysUserService.queryCount(conditions);
    }

    @Override
    protected Long eachSheetTotalCount() {
        return 5000L;
    }

    @Override
    protected Long eachTimesWriteSheetTotalCount() {
        return 2000L;
    }

    @Override
    protected void buildDataList(List<List<String>> resultList, Map<String, Object> condition,
                                 Long pageNo, Long pageSize) {
        SysUserReq sysUserReq = new SysUserReq();
        //可以根据condition设置条件
        sysUserReq.setPageNo(pageNo);
        sysUserReq.setPageSize(pageSize);
        PageResponse<SysUser> pageResponse = sysUserService.queryByPage(sysUserReq);
        List<SysUser> sysUserList = pageResponse.getResult();
        if (CollectionUtils.isNotEmpty(sysUserList)) {
            sysUserList.forEach(sysUser -> {
                resultList.add(Arrays.asList(sysUser.getId().toString(), sysUser.getName()));
            });
        }
    }

}
