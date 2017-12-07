package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.*;
import com.bjpygh.gzh.entity.DsPackageInfo;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.dao.DsInformationMapper;
import com.bjpygh.gzh.dao.DsPackageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public Status getPackageList(DsPackage dsPackage) {
        DsPackageExample example = new DsPackageExample();
        DsPackageExample.Criteria criteria = example.createCriteria();
        criteria.andDsNameEqualTo(dsPackage.getDsName());
        String models = dsPackage.getModels();
        if (models!=null&&models!="")
            criteria.andModelsEqualTo(models);
        String reservation = dsPackage.getReservation();
        if (reservation!=null&&reservation!="")
            criteria.andReservationEqualTo(reservation);
        String trainTime = dsPackage.getTrainTime();
        if (trainTime!=null&&trainTime!="")
            criteria.andTrainTimeEqualTo(trainTime);
        DsInformation dsInfo = dsInformationMapper.selectByPrimaryKey(dsPackage.getDsName());
        DsPackageExample example1 = new DsPackageExample();
        DsPackageExample.Criteria criteria1 = example1.createCriteria();
        criteria1.andDsNameEqualTo(dsPackage.getDsName());
        criteria1.andReservationEqualTo("自选练车方式");
        List<DsPackage> dsPackages = dsPackageMapper.selectByExample(example);
        List<DsPackage> dsPackages1 = dsPackageMapper.selectByExample(example1);
        for (DsPackage d : dsPackages1){
            dsPackages.add(d);
        }
        dsInfo.setDspList(dsPackages);

        return Status.success().add("dsInfos",dsInfo);
    }

    public DsPackage getPackage(String packageid) {
        return dsPackageMapper.selectByPrimaryKey(Integer.parseInt(packageid));
    }

    public List<DsPackage> getPackageByRecommend(Recommend recommend) {

        Map<String, Object> map = new HashMap<String, Object>();
        if (recommend.getShortTerm().equals("需要")) {
            map.put("dsType", "速");
        } else {
            map.put("dsType", "");
            if (recommend.getCustomize().equals("是")) {
                map.put("reservation", "私人定制");
            }
        }

        if (recommend.getWorkDay().equals("愿意")) {
            map.put("trainTime", "工作日");
        } else {
            map.put("trainTime", "周");
        }


        String[] dsNames = null;
        if (recommend.getScale().equals("海淀区")) {
            System.out.println("Scale = 海淀区");
            dsNames = dsInformationMapper.selectDsNamesByAddress("海淀区");
        } else if (recommend.getScale().equals("昌平区")) {
            System.out.println("Scale = 昌平区");
            dsNames = dsInformationMapper.selectDsNamesByAddress("昌平区");
        } else if (recommend.getScale().equals("大兴区")) {
            System.out.println("Scale = 大兴区");
            dsNames = dsInformationMapper.selectDsNamesByAddress("大兴区");
        }

        System.out.println("dsNames="+dsNames);
        map.put("dsName",dsNames);

        if (recommend.getPrice().equals("低于4000元")) {
            map.put("highPrice", 4000);
            map.put("lowPrice", 0);
        } else if (recommend.getPrice().equals("4000元-6000元")) {
            map.put("highPrice", 6000);
            map.put("lowPrice", 4000);
        } else if (recommend.getPrice().equals("高于6000元")) {
            map.put("price", 6000);
        }

        List<DsPackage> dsPackages = dsPackageMapper.selectByRecommend(map);
        if (dsPackages.size()>0){
            return dsPackages;
        }else {
            map.remove("reservation");
            List<DsPackage> dsPackages1 = dsPackageMapper.selectByRecommend(map);
            if (dsPackages1.size()>0){
                return dsPackages1;
            }else {
                map.remove("dsName");
                return dsPackageMapper.selectByRecommend(map);
            }
        }

    }
}
