/**
 * @author chenxf
 * @createdate 2013-11-25
 * @version 0.1
 * 自定义一个javascript logger类，实现javascript代码按级别输出调试信息，
 * 避免部署前遗忘注释调试性的alert方法，提高调试和部署效率。
 */

/** 定义调试级别：debug<info<warn<error */
var jsLoggerLevel = {debug:0, info:1, warn:2, error:3};

/** 定义日志对象 */
var jsLogger = {
		/** 是否启动调试，默认关闭 */
		isDebug:false, 
		/** 调试级别，默认为最高级别error */
		debugLevel:jsLoggerLevel.error
	};

/** 日志输出控制方法 */
jsLogger.log = function (message, level) {
	//alert("level=" + level + ", debugLevel=" + this.debugLevel);
	if (!this.isDebug) {
		return;
	}
	if (level >= this.debugLevel) {
		/*
		if(typeof message == "object"){
	        message = jsLogger.toString(message);
	    }*/
		alert(message);
	}
};
jsLogger.debug = function (message) {
    jsLogger.log(message, jsLoggerLevel.debug);
};
jsLogger.info = function (message) {
    jsLogger.log(message, jsLoggerLevel.info);
};
jsLogger.warn = function (message) {
    jsLogger.log(message, jsLoggerLevel.warn);
};
jsLogger.error = function (message) {
    jsLogger.log(message, jsLoggerLevel.error);
};

/** 将对象转换为json字符串 */
jsLogger.toString = function(obj){
    var values = [];
    for(var i in obj){
        if(typeof obj[i] == "object"){
            values.push(jsLogger.toString(obj[i]));
        }else if(typeof obj[i] == "function"){
            //
        }else {
            values.push(i + ":" + obj[i]);
        }
    }
    if(obj instanceof Array){
        return "["+values.join(",")+"]";
    }
    return "{"+values.join(",")+"}";
};