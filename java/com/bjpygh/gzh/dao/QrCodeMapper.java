package com.bjpygh.gzh.dao;

import com.bjpygh.gzh.bean.QrCode;
import com.bjpygh.gzh.bean.QrCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QrCodeMapper {
    long countByExample(QrCodeExample example);

    int deleteByExample(QrCodeExample example);

    int deleteByPrimaryKey(Long userId);

    int insert(QrCode record);

    int insertSelective(QrCode record);

    List<QrCode> selectByExample(QrCodeExample example);

    QrCode selectByPrimaryKey(Long userId);

    int updateByExampleSelective(@Param("record") QrCode record, @Param("example") QrCodeExample example);

    int updateByExample(@Param("record") QrCode record, @Param("example") QrCodeExample example);

    int updateByPrimaryKeySelective(QrCode record);

    int updateByPrimaryKey(QrCode record);
}