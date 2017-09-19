package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.DsInformation;
import com.bjpygh.gzh.bean.DsInformationExample;
import com.bjpygh.gzh.bean.DsPackage;
import com.bjpygh.gzh.bean.DsPackageExample;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.dao.DsInformationMapper;
import com.bjpygh.gzh.dao.DsPackageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageService {

    @Autowired
    DsPackageMapper dsPackageMapper;

    @Autowired
    DsInformationMapper dsInformationMapper;

    //根据套餐id获取套餐信息
    public Status getPackageById(String packageid) {
        DsPackage dsPackage = dsPackageMapper.selectByPrimaryKey(Integer.parseInt(packageid));
        if(dsPackage!=null){
            return Status.success().add("dsPackage",dsPackage);
        }else{
            return Status.fail(-20,"没有数据");
        }
    }

    //根据驾校名称匹配相应的套餐列表，为空时返回驾校信息
    public Status getPackageList(String dsname) {
        DsPackageExample example = new DsPackageExample();
        DsPackageExample.Criteria criteria = example.createCriteria();
        criteria.andDsNameEqualTo(dsname);
        DsInformation dsInfo = new DsInformation();
        dsInfo = dsInformationMapper.selectByPrimaryKey(dsname);
        dsInfo.setDspList(dsPackageMapper.selectByExample(example));

        return Status.success().add("dsInfos",dsInfo);
    }

    public DsPackage getPackage(String packageid) {
        return dsPackageMapper.selectByPrimaryKey(Integer.parseInt(packageid));
    }
}
