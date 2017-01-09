var wxConfigData = {};
$(document).ready(function() {
	$.ajax({
		url : contextPath + "/grant/get.do",
		dataType : "json",
		type : "get",
		async : false,
		data : {
			url : window.self.location.href
		},
		success : function(data) {
			wxConfigData = data;
		}
	});
});