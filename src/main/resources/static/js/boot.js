var bootPATH = __CreateJSPath("boot.js");

var rootPath = bootPATH.substring(0,bootPATH.indexOf("/js/"));

mini_debugger = false;                                           //

var skin = getCookie("miniuiSkin") || 'cupertino';             //skin cookie   cupertino
var mode = getCookie("miniuiMode") || 'medium';                 //mode cookie     medium

// document.write('<script src="' + bootPATH + 'miniui/miniui.js" type="text/javascript" ></sc' + 'ript>');
function getCookie(sName) {
    var aCookie = document.cookie.split("; ");
    var lastMatch = null;
    for (var i = 0; i < aCookie.length; i++) {
        var aCrumb = aCookie[i].split("=");
        if (sName == aCrumb[0]) {
            lastMatch = aCrumb;
        }
    }
    if (lastMatch) {
        var v = lastMatch[1];
        if (v === undefined) return v;
        return unescape(v);
    }
    return null;
}

function __CreateJSPath(js) {
    var scripts = document.getElementsByTagName("script");
    var path = "";
    for (var i = 0, l = scripts.length; i < l; i++) {
        var src = scripts[i].src;
        if (src.indexOf(js) != -1) {
            var ss = src.split(js);
            path = ss[0];
            break;
        }
    }
    var href = location.href;
    href = href.split("#")[0];
    href = href.split("?")[0];
    var ss = href.split("/");
    ss.length = ss.length - 1;
    href = ss.join("/");
    if (path.indexOf("https:") == -1 && path.indexOf("http:") == -1 && path.indexOf("file:") == -1 && path.indexOf("\/") != 0) {
        path = href + "/" + path;
    }
    return path;
}
// 关闭
function onCancel() {
    CloseWindow("cancel");
}
function CloseWindow(action) {
    if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
    else window.close();
}

// 高级、普通查询

function checkIsUrl(url){
	//url= 协议://(ftp的登录信息)[IP|域名](:端口号)(/或?请求参数)
	var strRegex = '^((https|http|ftp)://)?'//(https或http或ftp):// 可有可无
		+ '(([\\w_!~*\'()\\.&=+$%-]+: )?[\\w_!~*\'()\\.&=+$%-]+@)?' //ftp的user@  可有可无
		+ '(([0-9]{1,3}\\.){3}[0-9]{1,3}' // IP形式的URL- 3位数字.3位数字.3位数字.3位数字
		+ '|' // 允许IP和DOMAIN（域名）
		+ '(localhost)|'	//匹配localhost
		+ '([\\w_!~*\'()-]+\\.)*' // 域名- 至少一个[英文或数字_!~*\'()-]加上.
		+ '\\w+\\.' // 一级域名 -英文或数字  加上.
		+ '[a-zA-Z]{1,6})' // 顶级域名- 1-6位英文
		+ '(:[0-9]{1,5})?' // 端口- :80 ,1-5位数字
		+ '((/?)|' // url无参数结尾 - 斜杆或这没有
		+ '(/[\\w_!~*\'()\\.;?:@&=+$,%#-]+)+/?)$';//请求参数结尾- 英文或数字和[]内的各种字符

    var strRegex1 = '^(?=^.{3,255}$)((http|https|ftp)?:\/\/)?(www\.)?[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+(:\d+)*(\/)?(?:\/(.+)\/?$)?(\/\w+\.\w+)*([\?&]\w+=\w*|[\u4e00-\u9fa5]+)*$';
	var re=new RegExp(strRegex,'i');//i不区分大小写
	;
	//将url做uri转码后再匹配，解除请求参数中的中文和空字符影响
	if (re.test(encodeURI(url))) {
		return (true);
	} else {
		return (false);
	}
}

window.onload = function() {
  $('body').on('dblclick', '.cell', function() {
    var a = $(this).parent().parent().find('.el-button')
    
    $(a).each(function() {
      if ($(this).find('span').text() === "查看") {
        $(this)[0].click()
      }
    })
  })
}
