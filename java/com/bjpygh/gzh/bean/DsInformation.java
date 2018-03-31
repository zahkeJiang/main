package com.bjpygh.gzh.bean;

import java.util.List;

public class DsInformation {
    private String dsName;

    private Integer sort;

    private String dsImage;

    private String dsIntro;

    private String address;

    private String description;

    private List<DsPackage> dspList;

    public List<DsPackage> getDspList() {
        return dspList;
    }

    public void setDspList(List<DsPackage> dspList) {
        this.dspList = dspList;
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName == null ? null : dsName.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getDsImage() {
        return dsImage;
    }

    public void setDsImage(String dsImage) {
        this.dsImage = dsImage == null ? null : dsImage.trim();
    }

    public String getDsIntro() {
        return dsIntro;
    }

    public void setDsIntro(String dsIntro) {
        this.dsIntro = dsIntro == null ? null : dsIntro.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}