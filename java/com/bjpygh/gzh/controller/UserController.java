package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.bean.YouCard;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.bean.User;
import com.bjpygh.gzh.service.UserService;
import com.bjpygh.gzh.service.YouCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    YouCardService youCardService;

    //更改用户信息接口
    @ResponseBody
    @RequestMapping(value = "/changeInfo.action", method = RequestMethod.POST)
    public Status ChangeUserInfo(HttpServletRequest request, User user){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        user.setUserId(Long.parseLong(userMap.get("id")));
        userService.updateUser(user);
        return Status.success();
    }

    //获取用户id接口
    @ResponseBody
    @RequestMapping(value = "/getid.action", method = RequestMethod.POST)
    public Status getUserId(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        return Status.success().add("userid",userid);
    }

    //获取用户信息接口
    @ResponseBody
    @RequestMapping(value = "/personal.action", method = RequestMethod.POST)
    public Status getUser(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        return Status.success().add("userInfo",userService.getUserById(userid));
    }

    @RequestMapping(value = "/personal.action", method = RequestMethod.GET)
    public String toPersonal(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return "error";
        }
        return "user";
    }

    //绑定接口
    @ResponseBody
    @RequestMapping(value = "/bond.action", method = RequestMethod.POST)
    public Status BondUser(HttpServletRequest request,String phonenumber){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");

        if (userService.checkBond(phonenumber)){
            User user = new User();
            user.setUserId(Long.parseLong(userid));
            user.setPhoneNumber(phonenumber);
            userService.updateUser(user);
            return Status.success();
        }else{
            return Status.fail(-20,"手机号已绑定");
        }

    }

    //绑定接口
    @ResponseBody
    @RequestMapping(value = "/isBond.action", method = RequestMethod.POST)
    public Status isBond(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        if (userService.isBondUser(userid)){
            return Status.success();
        }else {
            return Status.fail(-20,"没有绑定");
        }
    }

    //绑定接口
    @ResponseBody
    @RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
    public Status checkLogin(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }else {
            return Status.success();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/applyCard",method = RequestMethod.POST)
    public Status applyCard(HttpServletRequest request, YouCard youCard){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        return youCardService.applyCard(userid,youCard);
    }

    @ResponseBody
    @RequestMapping(value = "/isApply",method = RequestMethod.POST)
    public Status isApply(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        return youCardService.isApply(userid);
    }

    @RequestMapping(value = "/applyCard", method = RequestMethod.GET)
    public String applyCard(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return "error";
        }
        return "excellentCard";
    }

    @RequestMapping(value = "/myShare", method = RequestMethod.GET)
    public String myShare(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return "error";
        }
        return "spreadIndex";
    }

//    /**
//     * 获取积分
//     * @param request
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/getUserCoin",method = RequestMethod.POST)
//    public Status getUserCoin(HttpServletRequest request){
//        Map<String, String> userMap = checkWxUser(request);
//        if(userMap == null){
//            return Status.notInWx();
//        }
//        String userid = userMap.get("id");
//        return userService.getUserCoin(userid);
//    }
//
//    /**
//     * 签到
//     * @param request
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/updateUserSign",method = RequestMethod.POST)
//    public Status updateUserSign(HttpServletRequest request){
//        Map<String, String> userMap = checkWxUser(request);
//        if(userMap == null){
//            return Status.notInWx();
//        }
//        String userid = userMap.get("id");
//        return userService.updateUserSign(userid);
//    }

    /**
     * 设置默认地址
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateDefaultAddress",method = RequestMethod.POST)
    public Status updateDefaultAddress(String addressId, HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        return userService.setDefaultAddress(userid, addressId);
    }

    /**
     * 获取默认地址
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectDefaultAddress",method = RequestMethod.POST)
    public Status selectDefaultAddress(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        return userService.selectDefaultAddress(userid);
    }
}
