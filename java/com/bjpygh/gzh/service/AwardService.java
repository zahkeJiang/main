package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.Address;
import com.bjpygh.gzh.bean.Award;
import com.bjpygh.gzh.dao.AddressMapper;
import com.bjpygh.gzh.dao.AwardMapper;
import com.bjpygh.gzh.entity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AwardService {

    @Autowired
    AwardMapper awardMapper;

    @Autowired
    AddressMapper addressMapper;


    public List<Award> getAwards(String userid, String filter) {
        if (filter.equals("1")){

        }
        return awardMapper.getAwards();
    }

    public Award getAward(String userid, String awardId) {
        return awardMapper.getAward(Long.valueOf(awardId));
    }


    public Status insertAddress(String userid, Address address) {
        address.setUserId(Long.valueOf(userid));
        addressMapper.insertAddress(address);
        return Status.success();
    }

    public Status selectAddresses(String userid) {
        List<Address> addresses = addressMapper.selectAddresses(Long.valueOf(userid));
        Long addressId = addressMapper.selectDefaultAddressId(Long.valueOf(userid));
        List<Address> addresses1 = new ArrayList<Address>();
        for (Address a : addresses){
            if (addressId == a.getAddressId()){
                a.setDefaultId(1);
            }else {
                a.setDefaultId(0);
            }
            addresses1.add(a);
        }
        return Status.success().add("addresses",addresses1);
    }

    public Address selectAddress(String addressId) {
        return addressMapper.selectAddress(Long.valueOf(addressId));
    }

    public Status updateAddress(Address address) {
        addressMapper.updateAddress(address);
        return Status.success();
    }

    public Status deleteAddress(String addressId) {
        addressMapper.deleteAddress(Long.valueOf(addressId));
        return Status.success();
    }

}
