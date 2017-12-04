package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.DsInformation;
import com.bjpygh.gzh.bean.DsInformationExample;
import com.bjpygh.gzh.dao.DsInformationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    public List<Map<String, String>> selectDsNames() {

        return dsInformationMapper.selectDsNames();
    }

    public DsInformation selectDsInfo(String dsName) {
        DsInformationExample example = new DsInformationExample();
        DsInformationExample.Criteria criteria = example.createCriteria();
        criteria.andDsNameEqualTo(dsName);
        return dsInformationMapper.selectByExample(example).get(0);
    }
}
