package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.DsInformation;
import com.bjpygh.gzh.bean.DsInformationExample;
import com.bjpygh.gzh.dao.DsInformationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DsInfoService {

    @Autowired
    DsInformationMapper dsInformationMapper;

    public DsInformation getDsInfoByName(String dsName) {
        return dsInformationMapper.selectByPrimaryKey(dsName);
    }

    public List<DsInformation> selectDsInfoList() {
        DsInformationExample example = new DsInformationExample();
        DsInformationExample.Criteria criteria = example.createCriteria();
        return dsInformationMapper.selectByExample(example);
    }
}
