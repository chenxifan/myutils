/**
 * @author chenxf
 * @createdate 2013-11-25
 * @version 0.1
 * �Զ���һ��javascript logger�࣬ʵ��javascript���밴�������������Ϣ��
 * ���ⲿ��ǰ����ע�͵����Ե�alert��������ߵ��ԺͲ���Ч�ʡ�
 */

/** ������Լ���debug<info<warn<error */
var jsLoggerLevel = {debug:0, info:1, warn:2, error:3};

/** ������־���� */
var jsLogger = {
		/** �Ƿ��������ԣ�Ĭ�Ϲر� */
		isDebug:false, 
		/** ���Լ���Ĭ��Ϊ��߼���error */
		debugLevel:jsLoggerLevel.error
	};

/** ��־������Ʒ��� */
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

/** ������ת��Ϊjson�ַ��� */
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