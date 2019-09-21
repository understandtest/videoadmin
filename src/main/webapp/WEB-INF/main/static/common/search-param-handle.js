
/**
 * 说明: js 中 Map()对象使用set(k,v) 进行赋值 而非 push(),该方式是 Array()中的.
 * 		Map 集合是 ES6 版本新增的特性,所以 IE 10+版本才会支持,具体兼容参考 ES6 相关说明
 * 		另外:如果使用 var map = {} 该方式表示map 则需自行定义set函数,否则出现 not function 错误,
 * 		因其本身就是一个普通对象,需自行定义相关处理
 * Date	:	2018-07-03 
 * Author : Hou.peng
 */
//自定义参数处理(map 处理)
function getUserDefinedParam(map){
	var paramStr = "";
//	$.each(map,function(k,n){
//		if(paramStr == ''){
//			paramStr += "?";
//		}
//		else{
//			paramStr += "&";
//		}
//		 paramStr +=k+="="+n;
//	})
	map.forEach(function (item, key, mapObj){
		if("" != mapObj.get(key) && null != mapObj.get(key)){
			if(paramStr == ''){
				paramStr += "?";
			}else{
				paramStr += "&";
			}
		    paramStr += key+"="+mapObj.get(key);
		}
	});
	return paramStr;
}
//分页参数处理 --> turnPage() 函数的参数处理
function getTurnPageParam(map){
	var paramStr = "";
	//20180629 修改
//	$.each(map,function(k,n){
//		 paramStr +=k+="="+n+"&";
//	})
	//原版废弃--20180703 update by Houpeng. 启用
	map.forEach(function (item, key, mapObj){
		if("" != mapObj.get(key) && null != mapObj.get(key)){
		    paramStr += "&" + key+"="+mapObj.get(key);
		}
	});
	return paramStr;
}