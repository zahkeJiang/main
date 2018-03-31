var currentMonth;
var currentDay;
//    日历开始
    /*
     * 用于记录日期，显示的时候，根据dateObj中的日期的年月显示
     */
    var dateObj = (function(){
        var _date = new Date();    // 默认为当前系统时间
        return {
            getDate : function(index){
                return _date;
            },
            setDate : function(date) {
                _date = date;
            }
        };
    })();
    /**
     * 渲染html结构
     */
    function renderHtml(index) {
        var calendar = document.getElementsByClassName("calendar")[index];
        var bodyBox = document.createElement("div");  // 表格区 显示数据
        // 设置表格区的html结构
        bodyBox.className = 'calendar-body-box';
        var _bodyHtml = "";
        // 一个月最多31天，所以一个月最多占6行表格
        for(var i = 0; i < 6; i++) {
            _bodyHtml += "<tr>" +
                "<td class='td"+index+"'></td>" +
                "<td class='td"+index+"'></td>" +
                "<td class='td"+index+"'></td>" +
                "<td class='td"+index+"'></td>" +
                "<td class='td"+index+"'></td>" +
                "<td class='td"+index+"'></td>" +
                "<td class='td"+index+"'></td>" +
                "</tr>";
        }
        bodyBox.innerHTML = "<table  class='calendarTable calendarTable"+index+" calendar-table'>" +
            _bodyHtml +
            "</table>";
        // 添加到calendar div中
        calendar.appendChild(bodyBox);
        
        $('.calendarTable td').height($('.calendarTable td').width());
    }
    /**
     * 表格中显示数据，并设置类名  第一个参数为0 ，第二个参数 0为本月 上一个月1，下一个月-1
     */
    function showCalendarData(index,newMonth) {
        var _oldyear = dateObj.getDate().getFullYear();
        var _oldmonth = dateObj.getDate().getMonth() - newMonth;
        var _oldday = dateObj.getDate().getDate();
        var newdate  =  new Date(_oldyear,_oldmonth,1);
        var _year = newdate.getFullYear();
        var _month = newdate.getMonth()+1;
        currentMonth = _month;
        var _day = newdate.getDate();
        currentDay = _day;
        var calendarTitle = document.getElementsByClassName("sit-date")[0];
        var titleStr =  _year +'年'+_month+ "月";
        //var titleStr = _month+ "月"+_day+"日";
        calendarTitle.innerText = titleStr;
        // 设置表格中的日期数据
        var _table = document.getElementsByClassName("calendarTable")[0];
        var _tds = _table.getElementsByTagName("td");
        var _firstDay = new Date(_year, _month - 1, 1);
        // 当前月第一天
        for(var i = 0; i < _tds.length; i++) {
            var _thisDay = new Date(_year, _month-1 , i + 1 - _firstDay.getDay());
            var _thisDayStr = getDateStr(_thisDay,0);
            _tds[i].innerHTML = "<span class='borderr' thisDayNumber='"+getYealMonthDate(_thisDay)+"'>"+_thisDay.getDate()+"</span>";
            //_tds[i].setAttribute('data', _thisDayStr);
            if(_thisDayStr.substr(0, 6) == getDateStr(_firstDay,index).substr(0, 6)){
                _tds[i].className = 'currentMonth currentMonth'+index+'';
                _tds[i].firstChild.className='borderr borderr'+index+'';
                // 当前月
            }else {    // 其他月
                _tds[i].className = 'otherMonth';
            }

            //当天变色
            var _currentYear = dateObj.getDate().getFullYear();
            var _currentMonth = dateObj.getDate().getMonth() + 1;
             if(_currentMonth < 10){
                _currentMonth =  "0" + _currentMonth;
             }else{
                _currentMonth =  _currentMonth;
             }
            var _currentDay = dateObj.getDate().getDate();
            
            //当天之前变灰
            if(_thisDay.getTime()<(dateObj.getDate().getTime()-24*60*60*1000)){
                _tds[i].innerHTML = "<span class='borderr' thisDayNumber='"+getYealMonthDate(_thisDay)+"'>"+_thisDay.getDate()+"</span>";
                $(_tds[i]).addClass('beforeDay');
            }else{
                _tds[i].innerHTML = "<span class='borderr' thisDayNumber='"+getYealMonthDate(_thisDay)+"'>"+_thisDay.getDate()+"</span>";
                $(_tds[i]).addClass('canSelectDay');
            }
            if(_thisDayStr.substr(0, 4) == _currentYear && _thisDayStr.substr(4, 2) == _currentMonth && _thisDayStr.substr(6, 2) == _currentDay ){
                _tds[i].innerHTML="<span class='borderr' thisDayNumber='"+getYealMonthDate(_thisDay)+"'>今天</span>";
                $(_tds[i]).addClass('currentDay');
            }

        }
            
    }
    //获取年月日的字符串形式
    function getDateStr(date) {
        var _year = date.getFullYear();
        var _month = date.getMonth() + 1;    // 月从0开始计数
        var _d = date.getDate();
        _month = (_month > 9) ? ("" + _month) : ("0" + _month);
        _d = (_d > 9) ? ("" + _d) : ("0" + _d);
        return _year + _month + _d;
    }

        //获取年月日带‘-’形式
    function getYealMonthDate(date) {
        var _year = date.getFullYear();
        var _month = date.getMonth() + 1;    // 月从0开始计数
        var _d = date.getDate();
        _month = (_month > 9) ? ("" + _month) : ("0" + _month);
        _d = (_d > 9) ? ("" + _d) : ("0" + _d);
        return _year +"-"+ _month +"-"+ _d;
    }

    //获取年月日
    function getYandM(num) {
        var _oldfyear = dateObj.getDate().getFullYear();
        var _oldfmonth = dateObj.getDate().getMonth() - num;
        var _oldfday = dateObj.getDate().getDate();
        var newfdate  =  new Date(_oldfyear,_oldfmonth,1);
        var _fyear = newfdate.getFullYear();
        var _fmonth = newfdate.getMonth()+1;
        var _fdate = newfdate.getDate();
        _fmonth = (_fmonth > 9) ? ("" + _fmonth) : ("0" + _fmonth);
        // var _fday = newfdate.getDate();
        var dataPara = _fyear+'-'+_fmonth+'-'+_fdate;
        return {dataPara:dataPara,_fmonth:_fmonth};
    }
    
    //别墅的日历获取价格
    function setPrice(dataPar){
        // 后台返回一个json数据
        //后台返回的数据是某个月对应的日期和价格，前端传参（某月）
        //  $.ajax({
        //         type: 'POST',
        //         url: 'getVillaPrice',
        //         data: {"data":dataPar},
        //         success: function(datas){
        //             console.log(datas);
        //             var priceList = datas.data.priceList;
        //             for (var i = 0; i < priceList.length; i++) {
        //                 $(".currentMonth0").eq(priceList[i].date-1).append("<p class='fixedhours'>"+dataList[i].quanAll+"</p><p class='fixedhours2'>"+dataList[i].volAll+"</p>")
        //             }
        //         }
        // });
        $.post("getVillaPrice",{"date":dataPar},function(datas){
            console.log(datas);
            var priceList = datas.data.priceList;
            for (var i = 0; i < priceList.length; i++) {
            //这个判断主要根据后台返回数据，返回数据不是这个格式 做其他判断
                if(priceList[i].price&&priceList[i].price!=null&&!priceList[i].num){
                    //有很多票，显示价格
                    $(".currentMonth0").eq(priceList[i].date-1).append("<p class='fixedhours'>¥"+priceList[i].price+"</p>");
                }else if(priceList[i].num&&priceList[i].num!=null&&priceList[i].num!=0&&!priceList[i].price){
                    //没有票或者票很少 不显示价格显示灰色
                    // $(".currentMonth0").eq(priceList[i].date-1).addClass("small-ticket");
                    $(".currentMonth0").eq(priceList[i].date-1).append("<p class='fixedhours'>余"+priceList[i].num+"</p>");
                }else if(priceList[i].num&&priceList[i].num==0&&!priceList[i].price){
                    //没有票或者票很少 不显示价格显示灰色
                    $(".currentMonth0").eq(priceList[i].date-1).addClass("no-ticket");
                    $(".currentMonth0").eq(priceList[i].date-1).append("<p class='fixedhours'>满</p>");
                }
            }
        },"json");
        //我模拟一个数据 实际需要后台接口
        // var priceList = [
        // {"date":"1","price":"100"},{"date":"2","price":"100"},{"date":"3","price":"100"},{"date":"4","price":"66"},{"date":"5","price":"66"},{"date":"6","price":"66"},{"date":"7","price":"66"},{"date":"8","price":"66"},{"date":"9","price":"100"},{"date":"10","price":"100"},
        // {"date":"11","num":"1"},{"date":"12","price":"66"},{"date":"13","price":"66"},{"date":"14","price":"66"},{"date":"15","price":"66"},{"date":"16","price":"100"},{"date":"17","price":"100"},{"date":"18","price":"66"},{"date":"19","price":"66"},{"date":"20","price":"66"},
        // {"date":"21","price":"66"},{"date":"22","num":"0"},{"date":"23","price":"100"},{"date":"24","price":"100"},{"date":"25","price":"66"},{"date":"26","price":"66"},{"date":"27","price":"66"},{"date":"28","num":"1"},{"date":"29","price":"66"},{"date":"30","num":"0"},{"date":"31","price":"80"}
        // ]


    // for (var i = 0; i < priceList.length; i++) {
    //     //这个判断主要根据后台返回数据，返回数据不是这个格式 做其他判断
    //      if(priceList[i].price&&priceList[i].price!=null&&!priceList[i].num){
    //         //有很多票，显示价格
    //         $(".currentMonth0").eq(priceList[i].date-1).append("<p class='fixedhours'>¥"+priceList[i].price+"</p>");
    //      }else if(priceList[i].num&&priceList[i].num!=null&&priceList[i].num!=0&&!priceList[i].price){
    //         //没有票或者票很少 不显示价格显示灰色
    //         // $(".currentMonth0").eq(priceList[i].date-1).addClass("small-ticket");
    //         $(".currentMonth0").eq(priceList[i].date-1).append("<p class='fixedhours'>余"+priceList[i].num+"</p>");
    //      }else if(priceList[i].num&&priceList[i].num==0&&!priceList[i].price){
    //         //没有票或者票很少 不显示价格显示灰色
    //         $(".currentMonth0").eq(priceList[i].date-1).addClass("no-ticket");
    //         $(".currentMonth0").eq(priceList[i].date-1).append("<p class='fixedhours'>满</p>");
    //      }
    // }

    };

//军旅的日历获取价格
    function setArmyPrice(dataPar){
        $.post("getArmyPrice",{"date":dataPar},function(datas){
            console.log(datas);
            var priceList = datas.data.priceList;
            for (var i = 0; i < priceList.length; i++) {
            //这个判断主要根据后台返回数据，返回数据不是这个格式 做其他判断
                if(priceList[i].price&&priceList[i].price!=null&&!priceList[i].num){
                    //有很多票，显示价格
                    $(".currentMonth0").eq(priceList[i].date-1).append("<p class='fixedhours'>¥"+priceList[i].price+"</p>");
                }else if(priceList[i].num&&priceList[i].num!=null&&priceList[i].num!=0&&!priceList[i].price){
                    //没有票或者票很少 不显示价格显示灰色
                    // $(".currentMonth0").eq(priceList[i].date-1).addClass("small-ticket");
                    $(".currentMonth0").eq(priceList[i].date-1).append("<p class='fixedhours'>余"+priceList[i].num+"</p>");
                }else if(priceList[i].num&&priceList[i].num==0&&!priceList[i].price){
                    //没有票或者票很少 不显示价格显示灰色
                    $(".currentMonth0").eq(priceList[i].date-1).addClass("no-ticket");
                    $(".currentMonth0").eq(priceList[i].date-1).append("<p class='fixedhours'>满</p>");
                }
            }
        },"json");
    };

