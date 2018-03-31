package com.bjpygh.gzh.entity;

import com.bjpygh.gzh.bean.DsPackage;

public class DsPackageInfo extends DsPackage{

    private int count;

    private String imageUrl;

    public DsPackageInfo(){

    }

    public DsPackageInfo(DsPackage dsPackage,String imageUrl){
        this.setTrainTime(dsPackage.getTrainTime());
        this.setReservation(dsPackage.getReservation());
        this.setPrice(dsPackage.getPrice());
        this.setPackageid(dsPackage.getPackageid());
        this.setMustProtection(dsPackage.getMustProtection());
        this.setModels(dsPackage.getModels());
        this.setDsType(dsPackage.getDsType());
        this.setDsName(dsPackage.getDsName());
        this.setDescription(dsPackage.getDescription());
        this.setBrand(dsPackage.getBrand());
        this.setCount(0);
        this.setImageUrl(imageUrl);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
