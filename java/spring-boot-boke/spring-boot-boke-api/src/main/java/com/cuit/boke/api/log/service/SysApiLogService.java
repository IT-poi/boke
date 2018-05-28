package com.cuit.boke.api.log.service;

import com.cuit.boke.api.log.dto.ApiLogDTO;
import com.cuit.boke.beans.entry.SysApiLog;
import com.cuit.boke.dao.SysApiLogMapper;
import com.cuit.boke.utils.TransformUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yinjk.web.core.util.BeanMapUtil;
import com.yinjk.web.core.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class SysApiLogService {

    @Autowired
    private SysApiLogMapper sysApiLogMapper;


    /**
     * 分页查询用户的登陆记录
     *
     * @param apiLogDTO 查询参数
     * @param userId 用户Id
     * @return 用户的登陆记录
     */
    public PageVO<SysApiLog> list(ApiLogDTO apiLogDTO, Integer userId) {
        Map<String, Object> paramMap = BeanMapUtil.beanToMap(apiLogDTO);
        PageHelper.startPage(apiLogDTO.getPageNum(), apiLogDTO.getPageSize());
        PageHelper.orderBy("create_time DESC");
        Page<SysApiLog> pages = (Page<SysApiLog>) sysApiLogMapper.listBy(paramMap);
        return new PageVO<>(pages);
    }

    /**
     * 批量删除
     *
     * @param logIds 日志ids
     * @param userId 操作用户id
     * @return 成功
     */
    @Transactional(rollbackFor = Exception.class)
    public int delete(String logIds, Integer userId) {
        List<Integer> idList = TransformUtils.putIdsToList(logIds, Integer.class);
        for (Integer id : idList) {
            sysApiLogMapper.deleteByPrimaryKey(id);
        }
        return 1;
    }

}
