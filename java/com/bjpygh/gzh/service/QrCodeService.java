package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.QrCode;
import com.bjpygh.gzh.bean.QrCodeExample;
import com.bjpygh.gzh.dao.QrCodeMapper;
import com.bjpygh.gzh.utils.Http;
import com.bjpygh.gzh.utils.OrderPush;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QrCodeService {

    @Autowired
    QrCodeMapper qrCodeMapper;

    public String checkTicket(int userid) {
        QrCode qrCode = qrCodeMapper.selectByPrimaryKey((long) userid);
        if (qrCode != null){
            return qrCode.getTicket();
        }else {
            String access_token = OrderPush.getAccess_token();
            JSONObject scene_id = new JSONObject();
            scene_id.element("scene_id",userid);
            JSONObject scene = new JSONObject();
            scene.element("scene",scene_id);
            JSONObject obj = new JSONObject();
            obj.element("action_name","QR_LIMIT_SCENE");
            obj.element("action_info",scene);
            String result = Http.sendPost("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + access_token,
                    obj.toString());
            JSONObject jsonObject = JSONObject.fromObject(result);
            System.out.println(jsonObject);
//            if (jsonObject.getString("errcode") != null){
//                OrderPush.getAccesstoken();
//                result = Http.sendPost("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + access_token,
//                        obj.toString());
//                jsonObject = JSONObject.fromObject(result);
//            }
            String ticket = jsonObject.getString("ticket");
            QrCode code1 = new QrCode();
            code1.setUserId((long) userid);
            code1.setTicket(ticket);
            qrCodeMapper.insert(code1);
            return ticket;
        }
    }

    public void updateCode(Map<String, String> map) {
        QrCodeExample example = new QrCodeExample();
        QrCodeExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(Long.valueOf(map.get("EventKey")));
        QrCode code = qrCodeMapper.selectByPrimaryKey(Long.valueOf(map.get("EventKey")));
        code.setConcern(code.getConcern()+1);//净关注量+1
        code.setConcerned(code.getConcerned()+1);//总关注量+1
        qrCodeMapper.updateByPrimaryKey(code);
    }

    //取关
    public void changeCode(Map<String, String> map) {
        QrCodeExample example = new QrCodeExample();
        QrCodeExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(Long.valueOf(map.get("EventKey")));
        QrCode code = qrCodeMapper.selectByPrimaryKey(Long.valueOf(map.get("EventKey")));
        code.setConcern(code.getConcern()-1);//净关注量-1
        code.setUnconcern(code.getUnconcern()+1);
        qrCodeMapper.updateByPrimaryKey(code);
    }

    public QrCode getConcern(Long userid) {
        return qrCodeMapper.selectByPrimaryKey(userid);
    }

    public void updateOnconcern(QrCode concern) {
        qrCodeMapper.updateByPrimaryKey(concern);
    }
}
