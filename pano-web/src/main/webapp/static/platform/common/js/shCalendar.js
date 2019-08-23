
try{
    var calendarV2 = function(e, options) {
        options = options || {};

        this.classWeek   = options.classWeek || 'calendar-week';
        this.classTitle   = options.classTitle || 'calendar_tit';
        this.classMonth   = options.classMonth || 'calendar_l';
        this.classDay   = options.classDay || 'calendar-day';
        this.classDayBase   = options.classDayBase || 'col_base';
        this.classDayShow   = options.classDayShow || 'show-date';
        this.classDayHasRented = options.classDayHasRented || 'yz';
        this.classDaySelect = options.classDaySelect || 'z';
        this.classDayPass = options.classDayPass || 'col_gray';
        this.classDayUnSelectAble = options.classDayUnSelectAble || 'yz';
        this.classDaySpecial = options.classDaySpecial || 'cur';
        this.classPink = options.classPink || 'col_pink';
        this.classTable = options.classTable || 'calendar_table';
        this.prependHtml   = options.prependHtml || '';
        this.today   = options.today || new Date();

        this.fillDayInfo = options.fillDayInfo || null;
        this.getDayInfo  = options.getDayInfo || null;

        this.checkState = 0;
        this.doColor = false;

        this.checkIn      = options.checkIn || null;
        this.checkOut     = options.checkOut || null;
        this.checkDayChange = options.checkDayChange || function(){};
        this.setDay = new Date();
        this.minDay = $('#minDay').val();
        this.maxDay = $('#maxDay').val();

        this.dateArray      = [];
        this.monthArray     = [];
        this.calenderHTML   = '';
        this.clearDateHtml   = '';
        this.preMonthHtml   = '';
        this.nextMonthHtml   = '';
        this.totalMohth     = 2;
        this.firstGray = false;
        this.isAbroad = options.isAbroad || false;

        this.e = typeof(e) == 'object' ? e : $(e);

        this.init();

        return(this);
    };
    calendarV2.prototype.init = function(initAtWhen) {
        this.dateArray      = [];
        this.monthArray     = [];
        this.calenderHTML   = '';
        this.checkState = 0;
        this.gengerateCalArray(true,initAtWhen).gengerateCalHtml().fillCalender();
        this.bindEvent();
        return(this);
    };
    calendarV2.prototype.preMonth = function() {
        if((this.today.getMonth() == this.firstDate.m) && (this.today.getFullYear() == this.firstDate.y) ){
            return false;
        }
        else {
            this.setDay.setDate(1);
            (this.firstDate.m == 0) ? (this.setDay.setMonth(11)) : this.setDay.setMonth(this.firstDate.m-1);
            (this.firstDate.m == 0) ? (this.setDay.setFullYear(this.firstDate.y-1)) : this.setDay.setFullYear(this.firstDate.y);
            this.dateArray      = [];
            this.monthArray     = [];
            this.calenderHTML   = '';
            this.checkState = 0;
            this.gengerateCalArray(false).gengerateCalHtml().fillCalender();
            this.bindEvent();
            return(this);
        }
    };
    calendarV2.prototype.nextMonth = function() {
        this.setDay.setDate(1);
        (this.lastDate.m == 0) ? this.setDay.setMonth(11) : (this.setDay.setMonth(this.lastDate.m-1));
        (this.lastDate.m == 0) ? this.setDay.setFullYear(this.lastDate.y-1) :this.setDay.setFullYear(this.lastDate.y);
        this.dateArray      = [];
        this.monthArray     = [];
        this.calenderHTML   = '';
        this.checkState = 0;
        this.gengerateCalArray(false).gengerateCalHtml().fillCalender();
        this.bindEvent();
        return(this);
    };
    calendarV2.prototype.setYmd = function(y,m,d, isDisplay) {
        var cdate = new Date();
        this.setDay = new Date();
        m = isDisplay ? m - 1 : m;
        cdate.setDate(1);
        cdate.setMonth(m);
        cdate.setFullYear(y);
        cdate.setDate(d);
        return cdate;
    };
    calendarV2.prototype.getYmd = function(s) {
        // return s.getFullYear() + '-' + (s.getMonth() + 1) + '-' +
        // s.getDate();// + ' ' + s.getDay();
        return s.getFullYear() + '-' + numToStr(2,s.getMonth() + 1) + '-' + numToStr(2,s.getDate());// + '
                                                                                                    // ' +
                                                                                                    // s.getDay();
    };

    calendarV2.prototype.gengerateCalArray = function(turnToCheckInDay,initAtWhen) {
        // this.dateArray = [];
        if(turnToCheckInDay && this.checkIn && this.checkOut) {
            this.setDay = new Date(this.checkIn); 
        }
        if(initAtWhen) {
            this.setDay = new Date(initAtWhen); 
        }
        for (var i = 0; i <= this.totalMohth; i++) {
            var toMonth = this.setDay.getMonth();
            for (var j = 1; j <= 37; j++) {
                this.setDay.setDate(j);
                if (this.setDay.getMonth() != toMonth){
                    break;
                }
                this.dateArray.push({y:this.setDay.getFullYear(),m:this.setDay.getMonth(),d:this.setDay.getDate(),w:this.setDay.getDay()});
            };
        };
        this.firstDate = this.dateArray[0];
        this.lastDate  = this.dateArray[this.dateArray.length -1];
        return(this);
    };

    calendarV2.prototype.gengerateCalHtml = function() {
        this.calenderHTML = '';
        this.monthArray = [];
        var m = -1;
        var len = this.dateArray.length - 1;
        for (var i = 0; i <= len; i++) {
            var day = this.dateArray[i];
            var cc = this.setYmd(day.y,day.m,day.d,false);
            if (m == -1) m = day.m;
            if (m != day.m) {
                this.calenderHTML += this.genMonth();
                this.monthArray = [];
                m = day.m;
            }
            this.monthArray.push(day);
        };
        return(this);
    };

    calendarV2.prototype.genDate = function(day,i,j) {
        var writeDay = this.setYmd(day.y,day.m,day.d,false);
        var old = '';
        var unselectable = '';
        var checkDay = '';
        var isToday = this.getYmd(writeDay) == this.getYmd(this.today) ? 1 : 0;
        var attributeStr = '';
        if(writeDay < this.today) {
                var dayText = isToday && !this.isAbroad ? '今天' : day.d;
        } else {
            if(i.state == 'available') {
                var dayText = isToday && !this.isAbroad ? '今天<br/><span class="col_gray">&yen;'+i.houseprice+'</span>' : day.d+'<br/><span class="col_gray">&yen;'+i.houseprice+'</span>';
            } else {
                var dayText = isToday && !this.isAbroad ? '今天<br/><span class="col_gray">已租</span>' : day.d+'<br/><span class="col_gray">已租</span>';
            }
        }
        if (writeDay < this.today) old = this.classDayPass;
        if (writeDay>=this.today && writeDay < this.checkIn && this.checkOut == null) unselectable = this.classDayUnSelectAble;
        if ((this.checkIn && writeDay <= this.checkOut && writeDay > this.checkIn) || 
                (this.checkOut && this.getYmd(writeDay) == this.getYmd(this.checkOut)) ||  
                (this.checkIn && this.getYmd(writeDay) == this.getYmd(this.checkIn))
           ){
               checkDay = this.classDaySelect;
               dayText = dayText.replace(/gray/,'');
        } 
        if (this.checkIn && this.getYmd(writeDay) == this.getYmd(this.checkIn)){
            if(i.state == 'available'){
               dayText = '入住<br><span>&yen;'+i.houseprice+'</span>';
            } else {
               dayText = '入住<br><span>已租</span>';
            }
        }
        if(this.checkOut && this.getYmd(writeDay) == this.getYmd(this.checkOut)){
            if(i.state == 'available'){
                dayText = '离开<br><span>&yen;'+i.houseprice+'</span>';
            } else {
                dayText = '离开<br><span>已租</span>';
            }
        }
        if(old) {
            var li_classes = [this.classDayBase, old, checkDay];
        }
        else if(unselectable) {
            var li_classes = [this.classDayBase, unselectable];
        }else if(writeDay >= this.checkIn && writeDay <= this.checkOut){
            var li_classes = [this.classDayBase, this.classDayShow, checkDay];
        }
        else if(i.state != 'available') {
            if(this.checkOut && this.getYmd(writeDay) == this.getYmd(this.checkOut)) {
                var li_classes = [this.classDayBase, checkDay];
            } else {
                var li_classes = [this.classDayBase, this.classDayHasRented];
            }
        }
        else if(i.pricetype != 'normal' && checkDay != this.classDaySelect) {
            var li_classes = [this.classDayBase, this.classDayShow, checkDay, this.classDaySpecial];

        }
        else {
            var li_classes = [this.classDayBase, this.classDayShow, checkDay];
        }

        if(this.checkIn && !this.checkOut) {
            if(this.getYmd(writeDay) > this.getYmd(this.checkIn) && !this.firstGray) {
                if(i.state != 'available') {
                    localStorage.setItem('firstRentedDay',this.getYmd(writeDay));
                    this.firstGray = true;
                } else if(localStorage.getItem('firstHasRentedDay')) {
                    localStorage.setItem('firstRentedDay',localStorage.getItem('firstHasRentedDay'));
                    this.firstGray = true;
                }
            }
            var firstRentedDay = localStorage.getItem('firstRentedDay');
            if(this.firstGray) {
                if(firstRentedDay && this.getYmd(writeDay) > firstRentedDay) {
                    var li_classes = [this.classDayBase, this.classDayHasRented];
                }
                if(firstRentedDay && this.getYmd(writeDay) == firstRentedDay) {
                    var li_classes = [this.classDayBase, this.classDayShow];
                    dayText = day.d+'<br/><span class="col_gray">可退</span>';
                    attributeStr += ' checkoutable="1" ';
                }
            }
        }

        if(i.pricetype != 'normal'){
            attributeStr += ' isspecial="1" ';
        }

        if(j == 6) {
            if(i.state == 'available') {
                var li_html =  '<td class="'+ li_classes.join(' ') +'" d="'+day.d+'" m="'+day.m+'" y="'+day.y+'" w="'+day.w+'" today="'+isToday+'" ymd="'+this.getYmd(writeDay)+'" price="'+i.houseprice+'" available="1" '+attributeStr+'><span>'+ dayText +'</span></td></tr>';
            } else {
                var li_html =  '<td onclick="return false;" class="'+ li_classes.join(' ') +'" d="'+day.d+'" m="'+day.m+'" y="'+day.y+'" w="'+day.w+'" today="'+isToday+'" ymd="'+this.getYmd(writeDay)+'" price="'+i.houseprice+'"'+attributeStr+'><span>'+ dayText +'</span></td></tr>';
            }
        } else if(j == 0) {
            if(i.state == 'available') {
                var li_html =  '<tr><td class="'+ li_classes.join(' ') +'" d="'+day.d+'" m="'+day.m+'" y="'+day.y+'" w="'+day.w+'" today="'+isToday+'" ymd="'+this.getYmd(writeDay)+'" price="'+i.houseprice+'" available="1" '+attributeStr+'><span>'+ dayText +'</span></td>';
            } else {
                var li_html =  '<tr><td onclick="return false;" class="'+ li_classes.join(' ') +'" d="'+day.d+'" m="'+day.m+'" y="'+day.y+'" w="'+day.w+'" today="'+isToday+'" ymd="'+this.getYmd(writeDay)+'" price="'+i.houseprice+'"'+attributeStr+'><span>'+ dayText +'</span></td>';
            }
        } else {
            if(i.state == 'available') {
                var li_html =  '<td class="'+ li_classes.join(' ') +'" d="'+day.d+'" m="'+day.m+'" y="'+day.y+'" w="'+day.w+'" today="'+isToday+'" ymd="'+this.getYmd(writeDay)+'" price="'+i.houseprice+'"available="1" '+attributeStr+'><span>'+ dayText +'</span></td>';
            } else {
                var li_html =  '<td  class="'+ li_classes.join(' ') +'" d="'+day.d+'" m="'+day.m+'" y="'+day.y+'" w="'+day.w+'" today="'+isToday+'" ymd="'+this.getYmd(writeDay)+'" price="'+i.houseprice+'"'+attributeStr+'><span>'+ dayText +'</span></td>';
            }
        }

        var new_html = '';
        if ( this.fillDayInfo) new_html = this.fillDayInfo(li_html);
        return new_html == '' ? li_html : new_html;
    };

    calendarV2.prototype.genMonth = function() {
        var dayHtml = '';
        var len = this.monthArray.length - 1;
        var calendarDay = this.monthArray[len];
        var luid = $('#lodgeUnitId').val();
        if(calendarDay.m <= 7) {
            var startDate = calendarDay.y + '-0' + (calendarDay.m + 1) + '-01';
            var endDate = calendarDay.y + '-0' + (calendarDay.m + 2) + '-01';
        }else if(calendarDay.m == 8){
            var startDate = calendarDay.y + '-0' + (calendarDay.m + 1) + '-01';
            var endDate = calendarDay.y + '-' + (calendarDay.m + 2) + '-01';
        } else if(calendarDay.m == 11){
            var startDate = calendarDay.y + '-' + (calendarDay.m + 1) + '-01';
            var endDate = (calendarDay.y+1) + '-01-01';
        } else {
            var startDate = calendarDay.y + '-' + (calendarDay.m + 1) + '-01';
            var endDate = calendarDay.y + '-' + (calendarDay.m + 2) + '-01';
        }

        var storageData = localStorage.getItem("dayData"+this.monthArray[0].y+this.monthArray[0].w+this.monthArray[0].m+this.monthArray[0].d);
        if(storageData) {
            var data = storageData;
        } else {
            var editable = true;
            var calendarCode = LodgeUnitCalendarImageCodeValidator.getCalendarCode();
            if(!calendarCode){
                return false;
            }
            var url = XZWebUrlWriter.getAJAX_GetLodgeUnitCalendar(luid,startDate,endDate,editable,calendarCode);
            var data = XZWebUrlWriter.getRequest(url,'json');
            if('status' in data){
                if(data.status == 'error_calendarCode_wrong'){
                    LodgeUnitCalendarImageCodeValidator.removeCalendarCode();
                    return false;
                } else {
                    alert(data.statusInfo);
                    return false;
                }
            } else {}
            // localStorage.setItem("dayData"+this.monthArray[0].y+this.monthArray[0].w+this.monthArray[0].m+this.monthArray[0].d,data);
        }

        for (var i = 0; i <= len ; i++) {
            var day = this.monthArray[i];
            if (i==0) {
                dayHtml+=this.genEmptyDay(day.w);
            }
            var dayHtml = dayHtml + this.genDate(day,data[i],day.w);
        };
        dayHtml+=this.genEmptyDay(6 - day.w);

        var preHtml =
            '<div class="'+this.classMonth+'">'+
            '<div class="'+this.classTitle+'">'+
            '<span cury="'+day.y+'" curm="'+(day.m + 1)+'">'+day.y+'-'+(day.m + 1)+'</span>'+
            '</div>'+
            '<div class="pl5">'+
            '<table  border="0" cellspacing="2" cellpadding="0" class="'+this.classTable+'">'+
            '<thead>'+
            '<tr>'+
            '<th class="pink">日</th>' +
            '<th>一</th>' +
            '<th>二</th>' +
            '<th>三</th>' +
            '<th>四</th>' +
            '<th>五</th>' +
            '<th class="pink">六</th>' +
            '</tr>'+
            '</thead>'+
            '<thead>';
        var afterHtml = '</thead></table></div></div>';
        // return preHtml + afterHtml;
        return preHtml + '<tr class="'+this.classDay+'">'+dayHtml + afterHtml;
    };

    calendarV2.prototype.genEmptyDay = function(num) {
        var emptyday = '';
        for (var i = 1; i<= num; i++) {
            emptyday+='<td class="'+this.classDayPass+' '+this.classDayBase+'"></td>';
        }
        return emptyday;
    };

    calendarV2.prototype.fillCalender = function() {
        // this.e.html(this.prependHtml + this.calenderHTML +
        // this.clearDateHTML() + this.preMonthHTML() + this.nextMonthHTML());
        var nationHtml = '';
        if(this.isAbroad){
            var nationHtml = '<div class="calendar_time">以'+$('#cityName').val()+'时间为准</div>';
        }
        this.e.html(this.prependHtml + this.calenderHTML +'<div class="day">特价日</div>'+ this.preMonthHTML() + this.nextMonthHTML() + nationHtml);
        // this.e.find('.' + this.classMonth).first().addClass('paddingR10');
        this.e.find('.' + this.classMonth).first().next().removeClass('calendar_l').addClass('calendar_r');
        // this.e.find('.' + this.classMonth).first().children('.' +
        // this.classTitle).addClass('width_274');
        return(this);
    };
    calendarV2.prototype.clearSelect = function() {
        var _this = this;
        this.e.find('.checkedday,' + '.' + this.classDaySelect).each(function(){
            var price = $(this).attr('price');
            if ($(this).attr('today') == '1') {
                if($(this).attr('available') == '1'){
                    $(this).find('span').html('今天<br/><span class="col_gray">&yen;'+price+'<span>');
                } else {
                    $(this).find('span').html('今天<br/><span class="col_gray">已租<span>').end().addClass(_this.classDayHasRented);
                }
            } else {
                if($(this).attr('available') == '1'){
                    $(this).find('span').html($(this).attr('d')+'<br/><span class="col_gray">&yen;'+price+'</span>');
                } else {
                    $(this).find('span').html($(this).attr('d')+'<br/><span class="col_gray">已租</span>').end().addClass(_this.classDayHasRented);
                }
            }
            if($(this).attr('isspecial') == '1'){
                $(this).addClass(_this.classDaySpecial);
            }

            if (_this.fillDayInfo) _this.fillDayInfo($(this));
        })
        .removeClass('checkedday');
        this.e.find('.' + this.classDaySelect).removeClass(this.classDaySelect);
        // this.e.find('.' +
        // this.classDayUnSelectAble+':first').addClass(this.classDayShow).removeClass(this.classDayUnSelectAble);
        $("#clearSelect").removeClass(_this.classPink);
        if(this.checkIn && this.checkOut) {
            this.e.hide();
        }
        this.checkOut = null;
        this.checkIn  = null;
        // this.firstGray = false;
        return(this);
    };

    calendarV2.prototype.bindEvent = function() {
        var _this = this;

        this.e.find('.' + this.classDayBase).on('click', function(){
            // $(this).parent().prevAll('tr').each(function(){
            // $(this).children('td').addClass(_this.classDayHasRented);
            // });
            this.firstGray = false;
            localStorage.setItem('firstHasRentedDay','');
            if ($(this).hasClass(_this.classDayPass) || $(this).hasClass(_this.classDayUnSelectAble) || ($(this).attr('available') != '1' && $(this).attr('checkoutable') != '1')) return false;
            if ($(this).hasClass('cal_noRoom')) return false;
            if ($(this).hasClass(_this.classDaySelect) && $('.checkedday').index(this) == 0) {
                var curClickTd = $(this);
                if(curClickTd.closest('.calendar_r').length){
                    if(curClickTd.attr('m') == '0'){
                        var initAtWhen =  _this.setYmd(curClickTd.attr('y')-1,11,1);
                    } else {
                        var initAtWhen =  _this.setYmd(curClickTd.attr('y'),curClickTd.attr('m')-1,1);
                    }
                } else {
                    var initAtWhen =  _this.setYmd(curClickTd.attr('y'),curClickTd.attr('m'),1);
                }
                _this.checkIn = null;
                _this.checkOut = null;
                $('#startdate').val('');
                $('#enddate').val('');
                _this.init(initAtWhen);
                $('#startenddate').val('选择入住日期');
                $('#startenddate').click();
                return false;
            }
            if ($(this).hasClass(_this.classDayHasRented)) return false;
            // if ($(this).hasClass(_this.classDaySpecial))
            // $(this).removeClass(_this.classDaySpecial);
            var thisday = $(this).find('span');
            if (!_this.checkIn && !_this.checkOut) {
                $('.checkedday').removeClass('checkedday');
                $(this).addClass('checkedday').removeClass(_this.classDaySpecial);
            } else if (_this.checkIn && !_this.checkOut) {
                $(this).addClass('checkedday');
            } else if (_this.checkIn && _this.checkOut) {
                _this.clearSelect();
                _this.e.show();
                $(this).addClass('checkedday').removeClass(_this.classDaySpecial);
            } else if (!_this.checkIn && _this.checkOut) {
                _this.clearSelect();
            }
            $(this).toggleClass(_this.classDaySelect);
            _this.refreshCheckState();
            /*
             * if
             * ($(this).parent().parent().parent().parent().parent().hasClass('calendar_r')) {
             * _this.nextMonth(); }
             */
        })
// this.e.find('.' + this.classDayBase).hover(function() {
// var ymd = $(this).attr('ymd');
// if(_this.checkIn && _this.checkOut && ymd >= _this.getYmd(_this.checkIn) &&
// ymd <= _this.getYmd(_this.checkOut)) {
// } else {
// if(!$(this).hasClass(_this.classDayHasRented)) {
// $(this).addClass(_this.classDaySelect);
// }
// }
// if(!$(this).hasClass(_this.classDayHasRented)) {
// $(this).addClass('hz');
// }
// },function() {
// var ymd = $(this).attr('ymd');
// if(_this.checkIn && _this.checkOut && ymd >= _this.getYmd(_this.checkIn) &&
// ymd <= _this.getYmd(_this.checkOut)) {
// } else {
// if(!$(this).hasClass('checkedday')) {
// $(this).removeClass(_this.classDaySelect);
// }
// }
// $(this).removeClass('hz');
// })
        return(this);
    };
    calendarV2.prototype.refreshCheckState = function() {
        _this = this;
        var checkedday = this.e.find('.checkedday');
        var checkLast = checkedday.last();
        checkLast.find('span').html('离开');

        if(!_this.checkIn) {
            var checkFirst = checkedday.first();
            var price = checkFirst.attr('price');
            checkFirst.find('span').html('入住<br/><span>&yen;'+price+'</span>');
            checkFirst.prevAll('td').removeClass(_this.classDaySpecial).addClass(_this.classDayHasRented);
            if(checkFirst.closest('.calendar_r').length){
                this.e.find('.calendar_l td.show-date').removeClass(this.classDaySpecial).addClass(this.classDayHasRented);
            }
            checkFirst.parent().prevAll('tr').each(function() {
                $(this).children('td').each(function() {
                    if($(this).children().length) {
                        $(this).removeClass(_this.classDaySpecial).addClass(_this.classDayHasRented);
                    }
                })
            })
            var enterday = checkFirst.attr('ymd');
            var firstHasRented = false;
            var firstHasRentedDay = '';
            _this.e.find('.' + _this.classDayBase).each(function() {
                if($(this).attr('ymd') && $(this).attr('ymd') > enterday && ($(this).hasClass(_this.classDayHasRented) || $(this).attr('ymd') == $('#detailBookArea').attr('firstHasRentedDay'))) {
                    $(this).removeClass(_this.classDayHasRented).addClass(_this.classDayShow).attr('checkoutable',1).find('span span').text('可退');
                    firstHasRentedDay = $(this).attr('ymd');
                    localStorage.setItem('firstHasRentedDay',firstHasRentedDay);
                    $('#detailBookArea').attr('firstHasRentedDay',firstHasRentedDay);
                    firstHasRented = true;
                    return false;
                }
            })
            if(firstHasRented) {
                _this.e.find('.' + _this.classDayBase).each(function() {
                    if($(this).attr('ymd') && $(this).attr('ymd') > firstHasRentedDay) {
                        $(this).addClass(_this.classDayHasRented).removeClass(_this.classDaySpecial);
                    }
                })
            }
        }
        if (_this.fillDayInfo) {
            this.fillDayInfo(checkLast);
            if(!_this.checkIn) {
                this.fillDayInfo(checkFirst);
            }
        }
        var leaveday = checkLast.attr('ymd');
        this.doColor = leaveday == enterday ? false : true;

        doColorState = false;

        if (!_this.checkIn && checkFirst.length) {
            this.checkIn = this.setYmd(checkFirst.attr('y'),checkFirst.attr('m'),checkFirst.attr('d'));
        }
        if(leaveday != enterday) {
            this.checkOut = this.setYmd(checkLast.attr('y'),checkLast.attr('m'),checkLast.attr('d'));
        }
        if(this.checkIn && this.checkOut && (this.getYmd(this.checkIn) == this.getYmd(this.checkOut))) {
            var initAtWhen = _this.checkIn;
            _this.checkIn = null;
            _this.checkOut = null;
            $('#startdate').val('');
            $('#enddate').val('');
            _this.init(initAtWhen);
            $('#startenddate').val('选择入住日期');
            setTimeout(function() {
                $('#startenddate').click();
            },100);
            return false;
        }
        /*
         * this.e.find('.' +
         * this.classDayBase).not('.old').not('span').each(function(){ var liYmd =
         * $(this).attr('ymd'); liYmd = new Date(liYmd.replace(/-/g, "/")); if
         * (_this.doColor) { if(!_this.checkIn) { if
         * ($(this).hasClass(_this.classDaySelect)) doColorState =
         * !doColorState; if (doColorState){
         * $(this).addClass(_this.classDaySelect);
         * $(this).addClass('checkedday'); if (_this.getDayInfo)
         * _this.getDayInfo($(this)); } } else { if( (_this.getYmd(liYmd) >=
         * _this.getYmd(_this.checkIn)) && ( _this.getYmd(liYmd) <=
         * _this.getYmd(_this.checkOut)) ) {
         * $(this).addClass(_this.classDaySelect);
         * $(this).addClass('checkedday'); if (_this.getDayInfo)
         * _this.getDayInfo($(this)); } } } if(_this.getYmd(liYmd) <
         * _this.getYmd(_this.checkIn) ) {
         * $(this).addClass(_this.classDayUnSelectAble);
         * $(this).removeClass(_this.classDayShow); } if (_this.fillDayInfo)
         * _this.fillDayInfo($(this)); })
         */
        this.checkDayChange();
        return(this);
    };
    calendarV2.prototype.resetBeforeCheckInState = function() {
        // this.e.find('.' +
        // this.classDayUnSelectAble).addClass(this.classDayShow).removeClass(this.classDayUnSelectAble);
    };
    calendarV2.prototype.clearDateHTML = function() {

        if(this.checkIn){
            var clearHtml = '<span class="empty ' + this.classPink + '" id="clearSelect">清空日期 </span>';
        }
        else {
            var clearHtml = '<div class="empty" id="clearSelect">清空日期 </div>';
        }
        return clearHtml;
    };
    calendarV2.prototype.preMonthHTML = function() {
        if((this.today.getMonth() == this.firstDate.m) && (this.today.getFullYear() == this.firstDate.y)) {
            return '';
        }
        else {
            var preMonth = '<span class="cal_pre" id="preMonth"></span>';
            return preMonth;
        }
    };
    calendarV2.prototype.nextMonthHTML = function() {
        var nextMonth = '<span class="cal_next" id="nextMonth"></span>';
        return nextMonth;
    }
    calendarV2.prototype.renderRefundRule = function() {
        var lodgeUnitCreateType = $("#lodgeUnitCreateType").val();
        if(lodgeUnitCreateType == 'spider'){
            this.renderSpiderLodgeUnitRefundRule();
        } else {
            this.renderPublishLodgeUnitRefundRule();
        }
        return (this);
    };
    calendarV2.prototype.renderPublishLodgeUnitRefundRule = function(){
        var rule_a = $('#rule_A').val() || 0;
        var rule_n = $('#rule_N').val() || 0;
        var getIfOversea = $('#getIfOversea').val();
        var city_name = getIfOversea == '1' ?  $('#cityName').val()+'时间':'';
        var timeZone = 0;
        if(this.checkIn && this.checkOut){
            var checkInTime  = (this.checkIn.getMonth()+1)+'.'+this.checkIn.getDate();
            var checkOutTime = (this.checkOut.getMonth()+1)+'.'+this.checkOut.getDate();
            var now          = new Date();
            var cancelAllDayTime =  this.checkIn.valueOf() - rule_n * 24 * 60 * 60 * 1000;
            var cancelAllDay = new Date(cancelAllDayTime);
            var cancelAllDayDate = (cancelAllDay.getMonth()+1)+'.'+cancelAllDay.getDate();
            var newCheckInDate  = (cancelAllDay.getMonth()+1)+'月'+cancelAllDay.getDate()+'日';
            $('#pos_n_1').text((now.getMonth()+1)+'.'+now.getDate());
            $('#pos_n_2').text(cancelAllDayDate+' 14:00前');
            $('#pos_n_3').text((this.checkIn.getMonth()+1)+'.'+this.checkIn.getDate()+' 14:00前');
            $('#pos_n_4').text((this.checkOut.getMonth()+1)+'.'+this.checkOut.getDate()+' 12:00前');
            if(cancelAllDayTime <= now.valueOf()){
                $('#pos_n_1').text('预订成功');
            }
            iDays  =  parseInt(Math.abs(this.checkIn  -  this.checkOut)  /  1000  /  60  /  60  /24);    // 把相差的毫秒数转换为天数
            nDays  =  parseInt(Math.abs(this.checkIn  -  cancelAllDay)  /  1000  /  60  /  60  /24);    // 把相差的毫秒数转换为天数
            if(rule_a<iDays){
                var  newCheckOut = this.checkIn.valueOf();
                newCheckOut = newCheckOut + rule_a * 24 * 60 * 60 * 1000;
                newCheckOut = new Date(newCheckOut);
                checkOutTime = (newCheckOut.getMonth()+1)+'.'+newCheckOut.getDate();
            }
            var current_rule_a = rule_a > iDays ? iDays: rule_a;
            if(cancelAllDay > now.valueOf()){
                $('.new_pos_notice .new_pos_n2').html(city_name+newCheckInDate);
                $('.new_pos_notice .new_pos_n3').html(current_rule_a);
                $('.new_pos_notice').text('根据房东设置的交易规则未早于'+city_name+newCheckInDate+ '退订视为有责取消，应扣除取消订单时间点后'+current_rule_a+'天订金作为违约金，剩余钱款将被原路退还');
            }else {
                $('.new_pos_notice').text('根据房东设置的交易规则您将不能享受无责取消权益。若退订将被扣除取消订单时间点后'+current_rule_a+'天订金作为违约金，剩余钱款将被原路退还');
            }
            $('.pos_3').text('如提前退房，扣除之后'+current_rule_a+'天的订金。');
            $('.pos_2').text('如取消订单，扣除'+checkInTime+'-'+checkOutTime+'的订金');
        }else if(!this.checkIn && !this.checkOut){
            $('.pos_2').text('如取消订单，扣除前'+rule_a+'天的订金');
            $('#pos_n_1').text('预订成功');
            $('#pos_n_2').text('入住前'+rule_n+'天14:00前');
            $('#pos_n_3').text('入住当天14:00前');
            $('#pos_n_4').text('退房当天12:00前');
        }else{
        }

    }
    calendarV2.prototype.renderSpiderLodgeUnitRefundRule = function(){
        var rule_n = parseInt($('#rule_N').val()) || 0;
        var nativeCity = $("#nativeCity").val();
        var timeZone = 0;
        var spiderLodgeUnitCancelRule = '';
        if(this.checkIn && this.checkOut){
            var checkInTime  = this.checkIn.getFullYear()+'.'+(this.checkIn.getMonth()+1)+'.'+this.checkIn.getDate();
            var cancelLimitTime =  this.checkIn.valueOf() - rule_n * 24 * 60 * 60 * 1000;
            var cancelLimitDay = new Date(cancelLimitTime);
            var cancelLimitDayDate = cancelLimitDay.getFullYear()+'.'+(cancelLimitDay.getMonth()+1)+'.'+cancelLimitDay.getDate();
            switch(rule_n){
               case 1:
                   spiderLodgeUnitCancelRule = "入住前1天取消预订可获得全额退款</br>";
                   spiderLodgeUnitCancelRule += "要获得全额住宿费用退款，房客必须在"+cancelLimitDayDate+" 15：00（"+nativeCity+"当地时间）之前取消预订。</br>";
                   spiderLodgeUnitCancelRule += "如果房客在"+cancelLimitDayDate+" 15：00 - "+checkInTime+" 15：00 取消预订，首晚房费将不可退还。</br>";
                   spiderLodgeUnitCancelRule += "如果房客已入住但决定提前退房，那么正式取消预订24小时后的未住宿天数的住宿费用将全额退款。";
                   break;
                case 5:
                    spiderLodgeUnitCancelRule = "入住前5天取消预订可获得全额退款</br>";
                    spiderLodgeUnitCancelRule += "要获得住宿费用的全额退款，房客必须在"+cancelLimitDayDate+" 15：00（"+nativeCity+"当地时间）之前取消预订。</br>";
                    spiderLodgeUnitCancelRule += "如果房客"+cancelLimitDayDate+" 15：00 - "+checkInTime+" 15：00 退订，那么首晚房费将不可退还，但剩余的天数将退还50%的房费。</br>";
                    spiderLodgeUnitCancelRule += "如果房客已入住但决定提前退房，那么退订发生24小时后未住宿的天数将退还50%的房费。";
                    break;
                case 7:
                    spiderLodgeUnitCancelRule = "入住前1周取消预订可获得50%退款（服务费除外)</br>";
                    spiderLodgeUnitCancelRule += "要获得50%的住宿费用退款，房客必须在"+cancelLimitDayDate+" 15：00（"+nativeCity+"当地时间）之前取消预订，否则不予退款。</br>";
                    spiderLodgeUnitCancelRule += "如果房客未能在"+cancelLimitDayDate+" 15：00前取消预订，未住宿天数的房费将不予退还。</br>";
                    spiderLodgeUnitCancelRule += "如果房客已入住但决定提前退房，剩余天数的房费将不予退还。";
                    break;
                case 30:
                    spiderLodgeUnitCancelRule = "入住前30天取消预订可获得50%退款</br>";
                    spiderLodgeUnitCancelRule += "要获得50%的住宿费用退款，房客必须在"+cancelLimitDayDate+" 15：00（"+nativeCity+"当地时间）之前取消预订。</br>";
                    spiderLodgeUnitCancelRule += "如果房客未能在"+cancelLimitDayDate+" 15：00前取消预订，未住宿天数的房费将不予退还。</br>";
                    spiderLodgeUnitCancelRule += "如果房客已入住但决定提前离开，剩余天数不予退款。"
                    break;
                default:
                    break;
            }
            $('#spiderLodgeUnitCancelRule').html(spiderLodgeUnitCancelRule);
        }else if(!this.checkIn && !this.checkOut){
            switch(rule_n){
               case 1:
                   spiderLodgeUnitCancelRule = "入住前1天取消预订可获得全额退款</br>";
                   spiderLodgeUnitCancelRule += "要获得全额住宿费用退款，房客必须在入住日期当天的入住时间（"+nativeCity+"当地时间；若未特别注明，则以下午3点为准）前24小时取消预订。</br>";
                   spiderLodgeUnitCancelRule += "如果房客在入住前不到24小时取消预订，首晚房费将不可退还。</br>";
                   spiderLodgeUnitCancelRule += "如果房客已入住但决定提前退房，那么正式取消预订24小时后的未住宿天数的住宿费用将全额退款。";
                   break;
                case 5:
                    spiderLodgeUnitCancelRule = "入住前5天取消预订可获得全额退款</br>";
                    spiderLodgeUnitCancelRule += "要获得住宿费用的全额退款，房客必须在入住日期（"+nativeCity+"当地时间；若未特别注明，则以下午3点为准）前5天整取消预订。</br>";
                    spiderLodgeUnitCancelRule += "如果房客提前不到5天退订，那么首晚房费将不可退还，但剩余的天数将退还50%的房费。</br>";
                    spiderLodgeUnitCancelRule += "如果房客已入住但决定提前退房，那么退订发生24小时后未住宿的天数将退还50%的房费。";
                    break;
                case 7:
                    spiderLodgeUnitCancelRule = "入住前1周取消预订可获得50%退款（服务费除外)</br>";
                    spiderLodgeUnitCancelRule += "要获得50%的住宿费用退款，房客必须在入住日期（"+nativeCity+"当地时间；若未特别注明，则以下午3点为准）前7天整取消预订，否则不予退款。</br>";
                    spiderLodgeUnitCancelRule += "如果房客未能在7天前取消预订，未住宿天数的房费将不予退还。</br>";
                    spiderLodgeUnitCancelRule += "如果房客已入住但决定提前退房，剩余天数的房费将不予退还。";
                    break;
                case 30:
                    spiderLodgeUnitCancelRule = "入住前30天取消预订可获得50%退款</br>";
                    spiderLodgeUnitCancelRule += "要获得50%的住宿费用退款，房客必须在入住日期（"+nativeCity+"当地时间；若未特别注明，则以下午3点为准）前30天整取消预订。</br>";
                    spiderLodgeUnitCancelRule += "如果房客未能在30天前取消预订，未住宿天数的房费将不予退还。</br>";
                    spiderLodgeUnitCancelRule += "如果房客已入住但决定提前离开，剩余天数不予退款。";
                    break;
                default:
                    break;
            }
            $('#spiderLodgeUnitCancelRule').html(spiderLodgeUnitCancelRule);
        }else{
        }
    }
}
catch(e){console.log(e);}


var defaultText = '入住离开日期';
var defaultStartText = '选择入住日期';
var defaultEndText = '选择离开日期';
try {
    var execCalendar = function(input, option) {
        var inputObj = $(input);
        var autoSearch = option.autoSearch || null;
        var showPrice = option.showPrice || null;
        var calendarBox = option.calendar || '#calendarBox';
        var classTitle = option.classTitle || null;
        var classTable = option.classTable || null;
        var calendar = new calendarV2(calendarBox, {
            classTitle : classTitle,
            classTable : classTable,
            checkDayChange : function() {
                if (this.checkIn) {
                    var startMonth = (this.checkIn.getMonth() < 9) ? '0' + (this.checkIn.getMonth() + 1)
                            : (this.checkIn.getMonth() + 1);
                    var startDate = (this.checkIn.getDate() < 10) ? '0' + (this.checkIn.getDate()) : this.checkIn
                            .getDate();
                    $('#startdate').val(this.checkIn.getFullYear() + '-' + startMonth + '-' + startDate);
                    $('#enddate').val('');
                    inputObj.val(defaultEndText);
                }
                if (this.checkOut) {
                    var dateNum = dateDiff('d', this.checkIn, this.checkOut);
                    var _this = this;
                    if (dateNum < this.minDay || (this.maxDay && dateNum > this.maxDay)) {
                        if (dateNum < this.minDay) {
                            var tipText = '最少入住天数为' + this.minDay + '天';
                        } else {
                            var tipText = '最多入住天数为' + this.maxDay + '天';
                        }
                        $('#startenddate').val(tipText);
                        this.checkOut = null;
                        var checkOutTd = this.e.find('td.checkedday').last();
                        if (checkOutTd.attr('available') == '1') {
                            checkOutTd.find('span').html(
                                checkOutTd.attr('d') + '<br/><span class="col_gray">&yen;' + checkOutTd.attr('price')
                                        + '</span>').removeClass(_this.classDaySelect);
                        } else {
                            if (checkOutTd.attr('checkoutable') == '1') {
                                checkOutTd.find('span').html(
                                    checkOutTd.attr('d') + '<br/><span class="col_gray">可退</span>');
                            } else {
                                checkOutTd.find('span').html(
                                    checkOutTd.attr('d') + '<br/><span class="col_gray">已租</span>').end().addClass(
                                    _this.classDayHasRented);
                            }

                        }
                        checkOutTd.removeClass(_this.classDaySelect + ' checkedday');
                        $('#enddate').val('');
                        this.renderRefundRule();
                        setTimeout(function() {
                            $('#startenddate').click();
                            $('#startenddate').val(tipText);
                            $("#startenddate").fadeOut(200).fadeIn(200);
                            $("#startenddate").fadeOut(200).fadeIn(200);
                            $("#startenddate").fadeOut(200).fadeIn(200);
                        }, 50);
                        return false;
                    }
                    var endMonth = (this.checkOut.getMonth() < 9) ? '0' + (this.checkOut.getMonth() + 1)
                            : (this.checkOut.getMonth() + 1);
                    var endDate = (this.checkOut.getDate() < 10) ? '0' + (this.checkOut.getDate()) : this.checkOut
                            .getDate();
                    $('#enddate').val(this.checkOut.getFullYear() + '-' + endMonth + '-' + endDate).trigger('change');
                    inputObj.val(this.checkIn.getFullYear() + '-' + (this.checkIn.getMonth() + 1) + '-'
                            + this.checkIn.getDate() + '至' + this.checkOut.getFullYear() + '-'
                            + (this.checkOut.getMonth() + 1) + '-' + this.checkOut.getDate());
                    this.e.hide();
                    $('.icon_removetime').show();
                    if (autoSearch) {
                        calTotalPrice();
                    }
                }
                scrollPage($(calendarBox));
                this.renderRefundRule();
            },
            checkIn : $('#startdate').val() ? new Date($('#startdate').val().replace(/-/g, "/")) : null,
            checkOut : $('#enddate').val() ? new Date($('#enddate').val().replace(/-/g, "/")) : null,
            isAbroad : $('#isAbroad').val() == 1 ? true : false
        })
        $(calendarBox).unbind('click');
        $(calendarBox).on('click', '#clearSelect', function() { // calendar.clearSelect();
            $('#startdate').val('');
            $('#enddate').val('');
            $('#startenddate').val('入住离开时间');
            if (autoSearch) {
                deleteCookie('startDate', '/', '.' + topLevelDomain);
                deleteCookie('endDate', '/', '.' + topLevelDomain);
                calTotalPrice();
                return false;
            }
            if ($(calendarBox).is(':visible')) {
                inputObj.val(defaultStartText);
            } else {
                inputObj.val(defaultText);
            }
        })
        $(calendarBox).on('click', '#preMonth', function() {
            calendar.preMonth();
            scrollPage($(calendarBox));
        })
        $(calendarBox).on('click', '#nextMonth', function() {
            calendar.nextMonth();
            scrollPage($(calendarBox));
        })
        inputObj.bind('click focus', function() {
            if ($('#startdate').val() == '' && $('#enddate').val() == '') {
                inputObj.val(defaultStartText);
            }
            if ($('#startdate').val() != '' && $('#enddate').val() != '') {
                calendar.resetBeforeCheckInState();
            }
            calendar.e.show();
            scrollPage($(calendarBox));
        })
        inputObj.bind('blur', function() {
            // calendar.e.hide();
        })
    }
    $('#calendar_btn_s').click(function() {
        $("#startenddate").click();
    })
} catch (e) {
}
