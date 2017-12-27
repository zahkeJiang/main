package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.*;
import com.bjpygh.gzh.entity.DsPackageInfo;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.dao.DsInformationMapper;
import com.bjpygh.gzh.dao.DsPackageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

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
        DsPackageExample example = new DsPackageExample();
        example.createCriteria();

        List<DsPackage> dsPackages = dsPackageMapper.selectByExample(example);
        List<DsPackageInfo> dsPackageInfos = new ArrayList<DsPackageInfo>();
        for (DsPackage dsPackage : dsPackages){
            if (dsPackage.getDsName().equals("东方时尚驾校")) {
                dsPackageInfos.add(new DsPackageInfo(dsPackage,
                        "http://120.24.184.86/glxt/dsimage/dfss_1.jpg"));
            } else if (dsPackage.getDsName().equals("京都府驾校")) {
                dsPackageInfos.add(new DsPackageInfo(dsPackage,
                        "http://120.24.184.86/glxt/dsimage/jdf_1.jpg"));
            } else if (dsPackage.getDsName().equals("公交驾校")) {
                dsPackageInfos.add(new DsPackageInfo(dsPackage,
                        "http://120.24.184.86/glxt/dsimage/gj_1.png"));
            } else if (dsPackage.getDsName().equals("北方驾校")) {
                dsPackageInfos.add(new DsPackageInfo(dsPackage,
                        "http://120.24.184.86/glxt/dsimage/bf_1.png"));
            } else if (dsPackage.getDsName().equals("海淀驾校")) {
                dsPackageInfos.add(new DsPackageInfo(dsPackage,
                        "http://120.24.184.86/glxt/dsimage/ds_bg1.jpg"));
            } else if (dsPackage.getDsName().equals("远大驾校")) {
                dsPackageInfos.add(new DsPackageInfo(dsPackage,
                        "http://120.24.184.86/glxt/dsimage/yd_1.jpg"));
            } else if (dsPackage.getDsName().equals("远航驾校")) {
                dsPackageInfos.add(new DsPackageInfo(dsPackage,
                        "http://120.24.184.86/glxt/dsimage/yh_1.png"));
            } else if (dsPackage.getDsName().equals("龙泉驾校")) {
                dsPackageInfos.add(new DsPackageInfo(dsPackage,
                        "http://120.24.184.86/glxt/dsimage/lq_1.jpg"));
            }
        }
        /**
         * 驾校区域判断
         */
        String[] dsNames = null;
        if (recommend.getScale().equals("海淀区")) {
//            System.out.println("Scale = 海淀区");
            dsNames = dsInformationMapper.selectDsNamesByAddress("海淀区");
        } else if (recommend.getScale().equals("昌平区")) {
//            System.out.println("Scale = 昌平区");
            dsNames = dsInformationMapper.selectDsNamesByAddress("昌平区");
        } else if (recommend.getScale().equals("大兴区")) {
//            System.out.println("Scale = 大兴区");
            dsNames = dsInformationMapper.selectDsNamesByAddress("大兴区");
        }else {
            List<Map<String, String>> maps = dsInformationMapper.selectDsNames();
            dsNames = new String[maps.size()];
            for (int i=0;i<maps.size();i++){
                dsNames[i] = maps.get(i).get("ds_name");
            }
        }

        /**
         * 根据区域返回名称判断
         */
        for (String s : dsNames){
            for (int i=0;i<dsPackageInfos.size();i++){
                if (s.equals(dsPackageInfos.get(i).getDsName())){
                    dsPackageInfos.get(i).setCount(dsPackageInfos.get(i).getCount()+1);
                }
            }
        }

        /**
         * 训练时间判断
         */
        if (recommend.getWorkDay().equals("愿意")) {
            for (int i=0;i<dsPackageInfos.size();i++){
                if (dsPackageInfos.get(i).getTrainTime().equals("工作日")){
                    System.out.println("+++++++++++++++++++++++++1++++++++++++");
                    dsPackageInfos.get(i).setCount(dsPackageInfos.get(i).getCount()+1);
                }
            }
//            map.put("trainTime", "工作日");
        } else {
            for (int i=0;i<dsPackageInfos.size();i++){
                if (!dsPackageInfos.get(i).getTrainTime().equals("工作日")){
                    System.out.println("+++++++++++++++++++++++++1++++++++++++");
                    dsPackageInfos.get(i).setCount(dsPackageInfos.get(i).getCount()+1);
                }
            }
//            map.put("trainTime", "周");
        }

        /**
         * 价格判断
         */
        if (recommend.getPrice().equals("低于4000元")) {
            for (int i=0;i<dsPackageInfos.size();i++){
                if (dsPackageInfos.get(i).getPrice() < 4000){
                    dsPackageInfos.get(i).setCount(dsPackageInfos.get(i).getCount()+2);
                }
            }
        } else if (recommend.getPrice().equals("4000元-6000元")) {
            for (int i=0;i<dsPackageInfos.size();i++){
                if (4000 < dsPackageInfos.get(i).getPrice() && dsPackageInfos.get(i).getPrice() < 6000){
                    dsPackageInfos.get(i).setCount(dsPackageInfos.get(i).getCount()+2);
                }
            }
        } else if (recommend.getPrice().equals("高于6000元")) {
            for (int i=0;i<dsPackageInfos.size();i++){
                if (dsPackageInfos.get(i).getPrice() > 6000){
                    dsPackageInfos.get(i).setCount(dsPackageInfos.get(i).getCount()+2);
                }
            }
        }
        /**
         * 班型判断
         */
        if (recommend.getShortTerm().equals("需要")) {
            for (int i=0;i<dsPackageInfos.size();i++){
                if (Pattern.matches(".*速*.",dsPackageInfos.get(i).getDsType())){
                    dsPackageInfos.get(i).setCount(dsPackageInfos.get(i).getCount()+1);
                }
            }
        } else {
            for (int i=0;i<dsPackageInfos.size();i++){
                dsPackageInfos.get(i).setCount(dsPackageInfos.get(i).getCount()+1);
            }
        }

        /**
         * 判断是否私人定制
         */
        if (recommend.getCustomize().equals("是")) {
            for (int i=0;i<dsPackageInfos.size();i++){
                if (dsPackageInfos.get(i).getReservation().equals("私人定制")){
                    dsPackageInfos.get(i).setCount(dsPackageInfos.get(i).getCount()+1);
                }
            }
        } else {
            for (int i=0;i<dsPackageInfos.size();i++){
                if (!dsPackageInfos.get(i).getReservation().equals("私人定制")){
                    dsPackageInfos.get(i).setCount(dsPackageInfos.get(i).getCount()+1);
                }
            }
        }

        /**
         * 判断是否自动挡
         */
        if (recommend.getModels().equals("C1 手动挡")){
            for (int i=0;i<dsPackageInfos.size();i++){
                if (dsPackageInfos.get(i).getModels().equals("C1 手动挡")){
                    dsPackageInfos.get(i).setCount(dsPackageInfos.get(i).getCount()+2);
                }
            }
        }else {
            for (int i=0;i<dsPackageInfos.size();i++){
                if (dsPackageInfos.get(i).getModels().equals("C2 自动挡")){
                    dsPackageInfos.get(i).setCount(dsPackageInfos.get(i).getCount()+2);
                }
            }
        }

        //排序
        Collections.sort(dsPackageInfos,new Comparator<DsPackageInfo>(){
            public int compare(DsPackageInfo arg1, DsPackageInfo arg0) {
                return new Double(arg0.getCount()).compareTo(new Double(arg1.getCount()));
            }
        });
        List<DsPackage> dsPackagesList = new ArrayList<DsPackage>();
        for (int i=0;i<5;i++){
            dsPackagesList.add(dsPackageInfos.get(i));
        }
        return dsPackagesList;
    }
}
