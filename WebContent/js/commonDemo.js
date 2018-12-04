function disableButton(name) {
		if($('#'+name).is(':disabled')){
			document.getElementById(name).disabled = false;
		}else{
			document.getElementById(name).disabled = true;
		}
	}

function getListParams(form){
	var fields = $("#"+form+" :input" ).not( ":button" );
	var urlParams = "";
	fields.each(function(index, element) {
	    var isDisabled = $(element).is(':disabled');
	    if (!isDisabled) {
	        urlParams +=  $(element).attr("id")+"="+$(element).val()+"&";
	    }
	});
	urlParams = urlParams.slice(0,-1);
	return urlParams;
}

function getCurrentDate(){
	var date = new Date();
	var seconds = date.getSeconds();
	var minutes = date.getMinutes();
	var hour = date.getHours();

	var year = date.getFullYear();
	var month = date.getMonth()+1;
	var day = date.getDate();
	var formatedDate = pad(day,2)+"/"+ pad(month,2)+"/"+year+" "+ pad(hour,2)+":"+pad(minutes,2)+":"+pad(seconds,2);
	return formatedDate;
}

function pad (str, max) {
	  str = str.toString();
	  return str.length < max ? pad("0" + str, max) : str;
}