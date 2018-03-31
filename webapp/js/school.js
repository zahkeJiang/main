var datas = {
	"list": [{ "school":"北方工业大学"},{ "school":"北京北大方正软件职业技术学院"},{ "school":"北京财贸职业学院"},{ "school":"北京城市学院"},{ "school":"北京大学"},{ "school":"北京电影学院"},{ "school":"北京电子科技学院"},{ "school":"北京电子科技职业学院"},{ "school":"北京第二外国语学院"},{ "school":"北京第二外国语学院中瑞酒店管理学院"},{ "school":"北京服装学院"},{ "school":"北京工商大学"},{ "school":"北京工商大学嘉华学院"},{ "school":"北京工业大学"},{ "school":"北京工业大学耿丹学院"},{ "school":"北京工业大学通州分校"},{ "school":"北京工业职业技术学院"},{ "school":"北京国家会计学院"},{ "school":"北京航天航空大学"},{ "school":"北京化工大学"},{ "school":"北京汇佳职业学院"},{ "school":"北京建筑大学"},{ "school":"北京交通大学"},{ "school":"北京交通运输职业学院"},{ "school":"北京教育学院"},{ "school":"北京吉利学院"},{ "school":"北京京北职业技术学院"},{ "school":"北京警察学院"},{ "school":"北京经济管理职业学院"},{ "school":"北京经济技术职业学院"},{ "school":"北京经贸职业学院"},{ "school":"北京开放大学"},{ "school":"北京科技大学"},{ "school":"北京科技大学延庆分校"},{ "school":"北京科技经营管理学院"},{ "school":"北京科技职业学院"},{ "school":"北京劳动保障职业学院"},{ "school":"北京联合大学"},{ "school":"北京理工大学"},{ "school":"北京林业大学"},{ "school":"北京农学院"},{ "school":"北京农业职业学院"},{ "school":"北京培黎职业学院"},{ "school":"中国青年政治学院"},{ "school":"北京社会管理职业学院"},{ "school":"北京市东城区职工大学"},{ "school":"北京市东城区职工业余大学"},{ "school":"北京师范大学"},{ "school":"北京市丰台区职工大学"},{ "school":"北京市海淀区职工大学"},{ "school":"北京市建设职工大学"},{ "school":"北京市石景山区业余大学"},{ "school":"北京市西城经济科学大学"},{ "school":"北京石油化工学院"},{ "school":"北京市朝阳区职工大学"},{ "school":"北京市职工体育运动技术学院"},{ "school":"北京市总工会职工大学"},{ "school":"北京体育大学"},{ "school":"北京体育职业学院"},{ "school":"北京外国语大学"},{ "school":"北京卫生职业学院"},{ "school":"北京舞蹈学院"},{ "school":"北京物资学院"},{ "school":"北京现代职业技术学院"},{ "school":"北京协和医学院(清华大学医学部)"},{ "school":"北京信息科技大学"},{ "school":"北京信息职业技术学院"},{ "school":"北京戏曲艺术职业学院"},{ "school":"北京宣武红旗业余大学"},{ "school":"北京印刷学院"},{ "school":"北京艺术传媒职业学院"},{ "school":"北京医药集团职业大学"},{ "school":"北京邮电大学"},{ "school":"北京邮电大学世纪学院"},{ "school":"北京有色金属研究总院"},{ "school":"北京语言大学"},{ "school":"北京政法职业学院"},{ "school":"北京中医药大学"},{ "school":"电信科学技术研究院"},{ "school":"对外经济贸易大学"},{ "school":"钢铁研究总院"},{ "school":"公安部管理干部学院"},{ "school":"国家法官学院"},{ "school":"国家行政学院"},{ "school":"国家检察官学院"},{ "school":"国家开放大学"},{ "school":"国际关系学院"},{ "school":"华北电力大学"},{ "school":"华北计算技术研究所"},{ "school":"机械科学研究总院"},{ "school":"清华大学"},{ "school":"石油化工科学研究院"},{ "school":"首都经济贸易大学"},{ "school":"首都经济贸易大学密云分校"},{ "school":"首都联合职工大学"},{ "school":"首都师范大学"},{ "school":"首都师范大学科德学院"},{ "school":"首都体育学院"},{ "school":"首都医科大学"},{ "school":"首钢工学院"},{ "school":"外交学院"},{ "school":"中共中央党校"},{ "school":"中国财经科学研究院"},{ "school":"中国传媒大学"},{ "school":"中国电力科学研究院"},{ "school":"中国地震局地球物理研究所"},{ "school":"中国地震局地质研究所"},{ "school":"中国地质大学(北京)"},{ "school":"中国地质科学院"},{ "school":"中国工程物理研究院"},{ "school":"中国航天科工集团第二研究院"},{ "school":"中国航天科工集团第三研究院"},{ "school":"中国航天科技集团公司第一研究院"},{ "school":"中国疾病预防控制中心"},{ "school":"中国科学院大学"},{ "school":"中国空间技术研究院"},{ "school":"中国矿业大学(北京)"},{ "school":"中国劳动关系学院"},{ "school":"中国林业科学研究所"},{ "school":"中国民航管理干部学院"},{ "school":"中国农业大学"},{ "school":"中国农业科学院研究生院"},{ "school":"中国青年政治学院"},{ "school":"中国气象科学研究院"},{ "school":"中国人名大学"},{ "school":"中国人民公安大学"},{ "school":"中国社会科学院研究生院"},{ "school":"中国石油大学(北京)"},{ "school":"中国石油勘探开发研究院"},{ "school":"中国水利水电科学研究院"},{ "school":"中国铁道科学研究院"},{ "school":"中国戏曲学院"},{ "school":"中国音乐学院"},{ "school":"中国艺术研究院"},{ "school":"中国政法大学"},{ "school":"中国中医科学院"},{ "school":"中华女子学院"},{ "school":"中央财经大学"},{ "school":"中央美术学院"},{ "school":"中央民族大学"},{ "school":"中央戏剧学院"},{ "school":"中央音乐学院"}]
	}

$(function(){
	var schoolList = datas.list;
	var html = "";
	$.each(schoolList,function(commentIndex,comment){
		html += "<li>"+comment.school+"</li>";
	});
	$(".schoolList ul").html(html);
	$(".schoolList li").click(function(){
        var schoolname = $(this).html();//获取当前点击的city
        $.post("changeInfo.action",{"school":schoolname},function(obj){
		},'json');
		window.location.href = "user.html?";
    });
});