var province = "";
var num = "";
function ShowMessage() 
{ 
   var thisURL = decodeURI(location.href);  
   var getval = thisURL.split('?')[1];  
   province = getval.split("=")[1].split("&")[0];  
   num = getval.split("=")[2];  
} 
window.onload=ShowMessage(); 

var data = {
	"city1": [{ "city":"安庆"},{ "city":"蚌埠" },{ "city":"亳州"},{ "city":"巢湖"},{ "city":"滁州"},{ "city":"池州"},{ "city":"阜阳"},{ "city":"淮北"},{ "city":"合肥"},{ "city":"淮南" },{ "city":"黄山"},{ "city":"六安"},{ "city":"马鞍山" },{ "city":"宿州"},{ "city":"铜陵"},{ "city":"芜湖" },{ "city":"宣州"}],
	"city2": [{ "city":"大堂区"},{ "city":"氹仔" },{ "city":"风顺堂区"},{ "city":"花地玛堂区"},{ "city":"路环岛" },{ "city":"圣安多尼堂区"},{ "city":"望德堂区"}],
	"city3": [{ "city":"昌平"},{ "city":"朝阳" },{ "city":"东城"},{ "city":"大兴"},{ "city":"房山" },{ "city":"丰台"},{ "city":"海淀"},{ "city":"怀柔" },{ "city":"门头沟"},{ "city":"密云"},{ "city":"平谷" },{ "city":"石景山"},{ "city":"顺义"},{ "city":"通州" },{ "city":"西城"},{ "city":"延庆"}],
	"city4": [{ "city":"北碚"},{ "city":"巴南" },{ "city":"璧山"},{ "city":"城口"},{ "city":"长寿" },{ "city":"大渡口"},{ "city":"垫江"},{ "city":"大足" },{ "city":"丰都"},{ "city":"奉节"},{ "city":"涪陵" },{ "city":"合川"},{ "city":"江北"},{ "city":"江津" },{ "city":"九龙坡"},{ "city":"开县"},{ "city":"两江新区" },{ "city":"梁平"},{ "city":"南岸"},{ "city":"南川"},{ "city":"彭水" },{ "city":"綦江"},{ "city":"黔江" },{ "city":"荣昌"},{ "city":"沙坪坝"},{ "city":"双桥"},{ "city":"石柱" },{ "city":"铜梁"},{ "city":"潼南"},{ "city":"武隆"},{ "city":"巫山"},{ "city":"万盛" },{ "city":"吴曦"},{ "city":"万州"},{ "city":"秀山"},{ "city":"渝北"},{ "city":"永川"},{ "city":"酉阳" },{ "city":"云阳"},{ "city":"渝中"},{"city":"忠县"}],
	"city5": [{ "city":"福州"},{ "city":"龙岩" },{ "city":"宁德"},{ "city":"南平"},{ "city":"莆田" },{ "city":"泉州"},{ "city":"厦门"},{ "city":"三明"},{ "city":"漳州"}],
	"city6": [{ "city":"潮州"},{ "city":"东莞" },{ "city":"佛山"},{ "city":"广州"},{ "city":"河源" },{ "city":"惠州"},{ "city":"江门"},{ "city":"揭阳"},{ "city":"茂名"},{ "city":"梅州"},{ "city":"清远" },{ "city":"韶关"},{ "city":"汕头"},{ "city":"汕尾" },{ "city":"深圳"},{ "city":"云浮"},{ "city":"阳江"},{ "city":"珠海"},{ "city":"湛江"},{ "city":"肇庆"},{ "city":"中山"}],
	"city7": [{ "city":"白银"},{ "city":"定西" },{ "city":"甘南"},{ "city":"金昌"},{ "city":"酒泉" },{ "city":"嘉峪关"},{ "city":"陇南"},{ "city":"临夏"},{ "city":"兰州"},{ "city":"平凉"},{ "city":"庆阳" },{ "city":"天水"},{ "city":"武威"},{ "city":"张掖" }],
	"city8": [{ "city":"北海"},{ "city":"百色" },{ "city":"崇左"},{ "city":"防城港"},{ "city":"贵港" },{ "city":"桂林"},{ "city":"河池"},{ "city":"贺州"},{ "city":"来宾"},{ "city":"柳州"},{ "city":"南宁" },{ "city":"钦州"},{ "city":"梧州"},{ "city":"玉林" }],
	"city9": [{ "city":"安顺"},{ "city":"毕节" },{ "city":"贵阳"},{ "city":"六盘水"},{ "city":"黔东南" },{ "city":"黔南"},{ "city":"黔西南"},{ "city":"铜仁"},{ "city":"遵义"}],
	"city10": [{ "city":"保定"},{ "city":"承德" },{ "city":"沧州"},{ "city":"邯郸"},{ "city":"廊坊" },{ "city":"秦皇岛"},{ "city":"石家庄"},{ "city":"唐山"},{ "city":"邢台"},{ "city":"张家口"}],
	"city11": [{ "city":"恩施"},{ "city":"鄂州" },{ "city":"黄冈"},{ "city":"黄石"},{ "city":"荆门" },{ "city":"荆州"},{ "city":"潜江"},{ "city":"神农架"},{ "city":"十堰"},{ "city":"随州"},{ "city":"天门" },{ "city":"武汉"},{ "city":"孝感"},{ "city":"咸宁" },{ "city":"仙桃"},{ "city":"襄阳"},{ "city":"宜昌" }],
	"city12": [{ "city":"大庆"},{ "city":"大兴安岭" },{ "city":"哈尔滨"},{ "city":"鹤岗"},{ "city":"佳木斯" },{ "city":"鸡西"},{ "city":"牡丹江"},{ "city":"齐齐哈尔"},{ "city":"七台河"},{ "city":"绥化"},{ "city":"双鸭江" },{ "city":"伊春"}],
	"city13": [{ "city":"白沙"},{ "city":"保亭" },{ "city":"昌江"},{ "city":"澄迈"},{ "city":"定安"},{ "city":"东方"},{ "city":"儋州"},{ "city":"海口"},{ "city":"乐东"},{ "city":"临高" },{ "city":"陵水"},{ "city":"琼海"},{ "city":"琼中" },{ "city":"三沙"},{ "city":"三亚"},{ "city":"屯昌" },{ "city":"文昌"},{"city":"万宁"},{"city":"五指山"}],
	"city14": [{ "city":"安阳"},{ "city":"鹤壁" },{ "city":"济源"},{ "city":"焦作"},{ "city":"开封"},{ "city":"漯河"},{ "city":"洛阳"},{ "city":"南阳"},{ "city":"平顶山"},{ "city":"濮阳" },{ "city":"三门峡"},{ "city":"商丘"},{ "city":"许昌" },{ "city":"新乡"},{ "city":"信阳"},{ "city":"周口" },{ "city":"驻马店"},{ "city":"郑州"}],
	"city15": [{ "city":"常德"},{ "city":"长沙" },{ "city":"郴州"},{ "city":"怀化"},{ "city":"衡阳"},{ "city":"娄底"},{ "city":"邵阳"},{ "city":"湘潭"},{ "city":"湘西"},{ "city":"益阳" },{ "city":"岳阳"},{ "city":"永州"},{ "city":"张家界" },{ "city":"株洲"}],
	"city16": [{ "city":"白城"},{ "city":"白山" },{ "city":"长春"},{ "city":"吉林"},{ "city":"辽源" },{ "city":"四平"},{ "city":"松原"},{ "city":"通化"},{ "city":"延边"}],
	"city17": [{ "city":"常州"},{ "city":"淮安" },{ "city":"连云港"},{ "city":"南京"},{ "city":"南通" },{ "city":"宿迁"},{ "city":"苏州"},{ "city":"泰州"},{ "city":"无锡"},{ "city":"徐州"},{ "city":"盐城"},{ "city":"扬州"},{ "city":"镇江"}],
	"city18": [{ "city":"梧州"},{ "city":"赣州" },{ "city":"吉安"},{ "city":"景德镇"},{ "city":"九江" },{ "city":"南昌"},{ "city":"萍乡"},{ "city":"上饶"},{ "city":"新余"},{ "city":"伊春"},{ "city":"鹰潭"}],
	"city19": [{ "city":"鞍山"},{ "city":"本溪" },{ "city":"朝阳"},{ "city":"丹东"},{ "city":"大连" },{ "city":"抚顺"},{ "city":"阜新"},{ "city":"葫芦岛" },{ "city":"锦州"},{ "city":"辽阳"},{ "city":"盘锦" },{ "city":"沈阳"},{ "city":"铁岭"},{ "city":"营口" }],
	"city20": [{ "city":"阿拉善"},{ "city":"包头" },{ "city":"巴彦淖尔"},{ "city":"赤峰"},{ "city":"鄂尔多斯" },{ "city":"呼和浩特"},{ "city":"呼伦贝尔"},{ "city":"通辽" },{ "city":"乌海"},{ "city":"乌兰察布"},{ "city":"兴安" },{ "city":"锡林郭勒"}],
	"city21": [{ "city":"固原"},{ "city":"石嘴山" },{ "city":"吴忠"},{ "city":"银川"},{ "city":"中卫" }],
	"city22": [{ "city":"果洛"},{ "city":"海北" },{ "city":"海东"},{ "city":"海南"},{ "city":"黄南" },{ "city":"海西"},{ "city":"西宁"},{ "city":"玉树"}],
	"city23": [{ "city":"阿坝"},{ "city":"巴中" },{ "city":"成都"},{ "city":"菏泽"},{ "city":"济南" },{ "city":"济宁"},{ "city":"聊城"},{ "city":"莱芜" },{ "city":"临沂"},{ "city":"青岛"},{ "city":"日照"},{ "city":"泰安"},{ "city":"潍坊" },{ "city":"威海"},{ "city":"烟台"},{ "city":"临淄"},{ "city":"枣庄" }],
	"city24": [{ "city":"滨州"},{ "city":"东营" },{ "city":"德州"},{ "city":"菏泽"},{ "city":"济南" },{ "city":"济宁"},{ "city":"聊城"},{ "city":"莱芜" },{ "city":"临沂"},{ "city":"青岛"},{ "city":"日照"},{ "city":"泰安"},{ "city":"潍坊" },{ "city":"威海"},{ "city":"烟台"},{ "city":"临淄"},{ "city":"枣庄" }],
	"city25": [{ "city":"宝山"},{ "city":"崇明" },{ "city":"长宁"},{ "city":"奉贤"},{ "city":"虹口" },{ "city":"黄浦"},{ "city":"静安"},{ "city":"嘉定" },{ "city":"金山"},{ "city":"卢湾"},{ "city":"闵行"},{ "city":"浦东新区"},{ "city":"普陀" },{ "city":"青浦"},{ "city":"松江"},{ "city":"徐汇"},{ "city":"杨浦" },{ "city":"闸北"}],
	"city26": [{ "city":"安康"},{ "city":"宝鸡" },{ "city":"汉中"},{ "city":"商洛"},{ "city":"铜川" },{ "city":"渭南"},{ "city":"西安"},{ "city":"咸阳" },{ "city":"延安"},{ "city":"榆林"}],
	"city27": [{ "city":"长治"},{ "city":"大同" },{ "city":"晋城"},{ "city":"晋中"},{ "city":"临汾" },{ "city":"吕梁"},{ "city":"朔州"},{ "city":"太原" },{ "city":"忻州"},{ "city":"运城"},{ "city":"阳泉" }],
	"city28": [{ "city":"北辰"},{ "city":"宝坻" },{ "city":"滨海新区"},{ "city":"东丽"},{ "city":"河北" },{ "city":"河东"},{ "city":"和平"},{ "city":"红桥" },{ "city":"河西"},{ "city":"静海"},{ "city":"津南" },{ "city":"蓟县"},{ "city":"宁河" },{ "city":"南开"},{ "city":"武清"},{ "city":"西青" }],
	"city29": [{ "city":"高雄市"},{ "city":"花莲县" },{ "city":"基隆市"},{ "city":"金门县"},{ "city":"嘉义市" },{ "city":"嘉义县"},{ "city":"连江县"},{ "city":"苗栗县" },{ "city":"南投县"},{ "city":"屏东县"},{ "city":"澎湖县" },{ "city":"台北市"},{ "city":"台东县" },{ "city":"台南市"},{ "city":"桃园县"},{ "city":"台中市" },{ "city":"新北市"},{ "city":"新竹市" },{ "city":"新竹县"},{ "city":"云林县" },{ "city":"宜兰县"},{ "city":"彰化县" }],
	"city30": [{ "city":"阿里"},{ "city":"昌都" },{ "city":"拉萨"},{ "city":"林芝"},{ "city":"那曲" },{ "city":"日喀则"},{ "city":"山南"}],
	"city31": [{ "city":"北区"},{ "city":"大埔区" },{ "city":"东区"},{ "city":"观塘区"},{ "city":"黄大仙区" },{ "city":"九龙城区"},{ "city":"葵青区"},{ "city":"离岛区" },{ "city":"南区"},{ "city":"葵湾区"},{ "city":"深水埗区" },{ "city":"沙田区"},{ "city":"屯门区" },{ "city":"湾仔区"},{ "city":"西贡区"},{ "city":"油尖旺区" },{ "city":"元朗区"},{ "city":"中西区" }],
	"city32": [{ "city":"阿克苏"},{ "city":"阿拉尔" },{ "city":"阿勒泰"},{ "city":"博尔塔拉"},{ "city":"巴音郭楞" },{ "city":"昌吉"},{ "city":"哈密"},{ "city":"和田" },{ "city":"克拉玛依"},{ "city":"喀什"},{ "city":"克孜勒苏" },{ "city":"石河子"},{ "city":"塔城" },{ "city":"吐鲁番"},{ "city":"图木苏克"},{ "city":"五家渠" },{ "city":"乌鲁木齐"},{ "city":"伊犁" }],
	"city33": [{ "city":"保山"},{ "city":"楚雄" },{ "city":"德宏"},{ "city":"大理"},{ "city":"迪庆" },{ "city":"红河"},{ "city":"昆明"},{ "city":"临沧" },{ "city":"丽江"},{ "city":"怒江"},{ "city":"普洱" },{ "city":"曲靖"},{ "city":"文山" },{ "city":"西双版纳"},{ "city":"玉溪"},{ "city":"昭通" }],
	"city34": [{ "city":"杭州"},{ "city":"湖州" },{ "city":"金华"},{ "city":"嘉兴"},{ "city":"丽水" },{ "city":"宁波"},{ "city":"衢州"},{ "city":"绍兴" },{ "city":"台州"},{ "city":"温州"},{ "city":"舟山" }]
}

$(function(){

	var html = "";
	
	var count = "city"+num;
	// var citylist = data.city1;
	var citylist = data[count];
	$.each(citylist,function(commentIndex,comment){
		html += "<li>"+comment.city+"</li>";
	});
	$(".container ul").html(html);
	$(".container li").click(function(){
        var newcity = $(this).html();//获取当前点击的city
        $.post("changeInfo.action",{"province":province,"city":newcity},function(obj){
        	if(obj.status=="0"){
        		window.location.href = "user.html?";
        	}
        	
		},'json');
    });
	
});

