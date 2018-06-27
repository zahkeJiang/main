package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.Concern;
import com.bjpygh.gzh.bean.ConcernExample;
import com.bjpygh.gzh.bean.QrCode;
import com.bjpygh.gzh.bean.QrCodeExample;
import com.bjpygh.gzh.dao.ConcernMapper;
import com.bjpygh.gzh.dao.QrCodeMapper;
import com.bjpygh.gzh.utils.Http;
import com.bjpygh.gzh.utils.OrderPush;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QrCodeService {

    @Autowired
    QrCodeMapper qrCodeMapper;

    @Autowired
    ConcernMapper concernMapper;

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
            String ticket = jsonObject.getString("ticket");
            QrCode code1 = new QrCode();
            code1.setUserId((long) userid);
            code1.setTicket(ticket);
            code1.setOnconcern(0);
            code1.setUnconcern(0);
            code1.setConcerned(0);
            code1.setConcern(0);
            qrCodeMapper.insert(code1);
            return ticket;
        }
    }

    public void updateNewConcern(Map<String, String> map) {
        QrCodeExample example = new QrCodeExample();
        QrCodeExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(Long.valueOf(map.get("EventKey").split("_")[1]));
        QrCode code = qrCodeMapper.selectByPrimaryKey(Long.valueOf(map.get("EventKey").split("_")[1]));
        code.setConcern(code.getConcern()+1);//净关注量+1
        code.setConcerned(code.getConcerned()+1);//总关注量+1
        qrCodeMapper.updateByPrimaryKey(code);
    }

    //取关
    public void changeCode(Map<String, String> map) {
//        QrCodeExample example = new QrCodeExample();
//        QrCodeExample.Criteria criteria = example.createCriteria();
//        criteria.andUserIdEqualTo(Long.valueOf(map.get("EventKey").split("_")[1]));
        ConcernExample example = new ConcernExample();
        ConcernExample.Criteria criteria = example.createCriteria();
        criteria.andOpenidEqualTo(map.get("FromUserName"));
        Concern concern = concernMapper.selectByExample(example).get(0);
        QrCode code = qrCodeMapper.selectByPrimaryKey(concern.getUserId());
        code.setConcern(code.getConcern()-1);//净关注量-1
        code.setUnconcern(code.getUnconcern()+1);//取关量+1
        qrCodeMapper.updateByPrimaryKey(code);
    }

    public QrCode getConcern(Long userid) {
        return qrCodeMapper.selectByPrimaryKey(userid);
    }

    public void updateOnconcern(QrCode concern) {
        qrCodeMapper.updateByPrimaryKey(concern);
    }

    public void updatePerUser(Long userId) {
        QrCode code = qrCodeMapper.selectByPrimaryKey(userId);
        code.setConcerned(code.getConcerned()-1);
        qrCodeMapper.updateByPrimaryKey(code);
    }

    public void updateQrConcern(Long userId) {
        QrCodeExample example = new QrCodeExample();
        QrCodeExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        QrCode code = qrCodeMapper.selectByPrimaryKey(userId);
        code.setConcern(code.getConcern()+1);//净关注量+1
        code.setUnconcern(code.getUnconcern()-1);//取关量-1
        qrCodeMapper.updateByPrimaryKey(code);
    }

    public void updateNewQrCode(String userId) {
        QrCode qrCode = qrCodeMapper.selectByPrimaryKey(Long.valueOf(userId));
        qrCode.setConcerned(qrCode.getConcerned() + 1);  //总关注量+1
        qrCode.setConcern(qrCode.getConcern() + 1);  //净关注量+1

        qrCodeMapper.updateByPrimaryKey(qrCode);
    }
}
