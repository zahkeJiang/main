package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.QrCode;
import com.bjpygh.gzh.bean.QrCodeExample;
import com.bjpygh.gzh.dao.QrCodeMapper;
import com.bjpygh.gzh.utils.Http;
import com.bjpygh.gzh.utils.OrderPush;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QrCodeService {

    @Autowired
    QrCodeMapper qrCodeMapper;

    public String getTicket(int userid) {
        QrCode qrCode = qrCodeMapper.selectByPrimaryKey((long) userid);
        if (qrCode.getTicket() != null){
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
            String ticket = jsonObject.getString("ticket");
            QrCode code1 = new QrCode();
            code1.setUserId((long) userid);
            code1.setTicket(ticket);
            qrCodeMapper.insert(code1);
            return ticket;
        }
    }
}
