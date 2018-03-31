package com.bjpygh.gzh.bean;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

public class DsImage implements Serializable {
    private static final long serialVersionUID = 78L;
    private List<MultipartFile> images;

    public List<MultipartFile> getImages() {
        return images;
    }
    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }
}