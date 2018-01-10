package com.bjpygh.gzh.dao;

import com.bjpygh.gzh.bean.Address;

import java.util.List;

public interface AddressMapper {

    void insertAddress(Address address);

    List<Address> selectAddresses(Long userId);

    Address selectAddress(Long addressId);

    void updateAddress(Address address);

    void deleteAddress(Long addressId);
}
