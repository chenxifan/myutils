(function($){
	
	var weekDay = ["星期一","星期二","星期三","星期四","星期五","星期六","星期天"];

	$.extend({
		
		//本周第一天
		showWeekFirstDay:function(Nowdate) {
			var WeekFirstDay = new Date(Nowdate - (Nowdate.getDay() - 1) * 86400000);
			return WeekFirstDay;
		},
		
		//本周最后一天
		showWeekLastDay:function(Nowdate) {
			var WeekFirstDay = new Date(Nowdate - (Nowdate.getDay() - 1) * 86400000);
			var WeekLastDay = new Date((WeekFirstDay / 1000 + 6 * 86400) * 1000);
			return WeekLastDay;
		},
		
		//上周7天
		//返回一个数组，数组里面是7天的Date对象
		showLastWeekDays:function(now) {
		    var currentWeek = now.getDay();
		    
		    if (currentWeek == 0) {
		    	currentWeek = 7;
		    }
		    
		    var monday  = now.getTime() - (currentWeek+6)*24*60*60*1000; //星期一
		    var tuesday  = now.getTime() - (currentWeek+5)*24*60*60*1000; //星期二
		    var wednesday = now.getTime() - (currentWeek+4)*24*60*60*1000; //星期三
		    var thursday = now.getTime() - (currentWeek+3)*24*60*60*1000; //星期四
		    var friday  = now.getTime() - (currentWeek+2)*24*60*60*1000; //星期五
		    var saturday = now.getTime() - (currentWeek+1)*24*60*60*1000; //星期六
		    var sunday  = now.getTime() - (currentWeek)*24*60*60*1000;   //星期日
		    
		    var week = new Array(new Date(monday), new Date(tuesday), 
		    					 new Date(wednesday), new Date(thursday),
		    					 new Date(friday), new Date(saturday), 
		    					 new Date(sunday));
			return week; 
		},
		
		//本周7天
		//返回一个数组，数组里面是7天的Date对象
		showThisWeekDays:function(now) {
		    var currentWeek = now.getDay();
		    
		    if (currentWeek == 0) {
		    	currentWeek = 7;
		    }
		    
		    var monday  = now.getTime() - (currentWeek-1)*24*60*60*1000; //星期一
		    var tuesday  = now.getTime() - (currentWeek-2)*24*60*60*1000; //星期二
		    var wednesday = now.getTime() - (currentWeek-3)*24*60*60*1000; //星期三
		    var thursday = now.getTime() - (currentWeek-4)*24*60*60*1000; //星期四
		    var friday  = now.getTime() - (currentWeek-5)*24*60*60*1000; //星期五
		    var saturday = now.getTime() - (currentWeek-6)*24*60*60*1000; //星期六
		    var sunday  = now.getTime() - (currentWeek-7)*24*60*60*1000;   //星期日
		    
		    var week = new Array(new Date(monday), new Date(tuesday), 
		    					 new Date(wednesday), new Date(thursday),
		    					 new Date(friday), new Date(saturday), 
		    					 new Date(sunday));
			return week; 
		},
		
		//下周7天
		//返回一个数组，数组里面是7天的Date对象
		showNextWeekDays:function(now) {
			 var currentWeek = now.getDay();
			 
			 if ( currentWeek == 0 ) {
			 	currentWeek = 7;
			 }
			 
			 var monday = now.getTime() - (currentWeek-8)*24*60*60*1000;  //星期一
			 var tuesday = now.getTime() - (currentWeek-9)*24*60*60*1000;  //星期二
			 var wednesday = now.getTime() - (currentWeek-10)*24*60*60*1000; //星期三
			 var thursday = now.getTime() - (currentWeek-11)*24*60*60*1000; //星期四
			 var friday= now.getTime() - (currentWeek-12)*24*60*60*1000; //星期五
			 var saturday = now.getTime() - (currentWeek-13)*24*60*60*1000; //星期六
			 var sunday = now.getTime() - (currentWeek-14)*24*60*60*1000; //星期日
			
		    var week = new Array(new Date(monday), new Date(tuesday), 
		    					 new Date(wednesday), new Date(thursday),
		    					 new Date(friday), new Date(saturday), 
		    					 new Date(sunday));
			return week; 
		},
		
		//本月第一天
		showMonthFirstDay : function(Nowdate) {
			var MonthFirstDay = new Date(Nowdate.getFullYear(), Nowdate.getMonth(), 1);
			return MonthFirstDay;
		},
		
		//本月最后一天
		showMonthLastDay : function(Nowdate) {
			var MonthNextFirstDay = new Date(Nowdate.getFullYear(), Nowdate.getMonth() + 1, 1);
			var MonthLastDay = new Date(MonthNextFirstDay - 86400000);
			return MonthLastDay;
		},
		
		//上月
		//返回一个数组，数组里面是上月第1天和最后1天的Date对象
		showLastMonthDays:function(Nowdate) {
			var MonthFirstDay = new Date(Nowdate.getFullYear(),Nowdate.getMonth()-1,1);
		   	var MonthNextFirstDay = new Date(Nowdate.getFullYear(),Nowdate.getMonth(),1);
		  	var MonthLastDay = new Date(MonthNextFirstDay-86400000);
		  	
		  	return new Array(MonthFirstDay, MonthLastDay);
		},
		
		//下月
		//返回一个数组，数组里面是下月第1天和最后1天的Date对象
		showNextMonthDays:function(Nowdate) {
			var MonthFirstDay = new Date(Nowdate.getFullYear(), Nowdate.getMonth()+1, 1);
		   	var MonthNextFirstDay = new Date(MonthFirstDay.getFullYear(), MonthFirstDay.getMonth() + 1, 1);
		  	var MonthLastDay = new Date(MonthNextFirstDay - 86400000);
		  	
		  	return new Array(MonthFirstDay, MonthLastDay);
		},
		
		//基于date 和 day（整数类型：允许正负整数）做天数的加减运算
		addMonthDay : function(date,day){
			if(!date){
				return;
			}
			var temp = new Date();
			temp.setTime(date.getTime() + day * 86400000);
			return temp;
		},
		
		//基于date 和 month （整数类型：允许正负整数）做月份的加减运算
		addMonth : function(date,month){
			if(!date){
				return;
			}
			var temp = new Date(date.getTime());
			
			var currMonth = temp.getMonth();
			
			temp.setMonth(10);
			var currYear  = temp.getFullYear();
			if(month > 0){
				for(var i = 0 ; i < month ; i++){
					if(currMonth == 11){
						currMonth = -1;
					}
				
					currMonth += 1;
					if(currMonth > 10){
						currYear += 1;
					}
				}
				temp.setMonth(currMonth);
			}else{
				month = 0 - month;
				for(var i = 0 ; i < month ; i++){
					if(currMonth == 0){
						currMonth = 12;
					}
				
					currMonth -= 1;
					if(currMonth < 1){
						currYear -= 1;
					}
				}
				temp.setMonth(currMonth);
			}
			temp.setYear(currYear);
			return temp;
		},
		
		//简单格式化时间 yyyy-MM-dd
		dateFormat : function(date) {
			var month = date.getMonth() + 1;
			var monthStr = month;
			var dayStr = date.getDate();
			
			if(month < 10){
				monthStr = "0"+month;
			}
			if (dayStr < 10) {
				dayStr = "0" + dayStr;
			}
			return date.getFullYear() + "-" + monthStr + "-" + dayStr;
		},
		
		//根据date获取星期的中文描述
		getWeekDay : function(date){
			var weekDayNum = date.getDay();
			return weekDay[weekDayNum];
		}
	});
})(jQuery);