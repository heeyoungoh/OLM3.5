function ajaxChartLoad(chart, url, data, target, debug, noMsg, msg) {
	$.ajax({
		url: url,type: 'post',data: data
		,error: function(xhr, status, error) {('#loading').fadeOut(150);var errUrl = "errorPage.do";var errMsg = "status::"+status+"///error::"+error;var callback = "";var width = "300px";var height = "300px";var callBack = function(){};fnOpenLayerPopup(errUrl,errMsg,callBack,width,height);}
		,beforeSend: function(x) {$('#loading').fadeIn(150);if(x&&x.overrideMimeType) {x.overrideMimeType("application/html;charset=UTF-8");}	}
		,success: function(result){
			$('#loading').fadeOut(100);	if(debug){alert(result);}if(result == 'error' || result == ""){if(noMsg != 'Y'){alert(msg);}
			}else {
				result = eval('(' + result + ')');				
				$('#'+target).hide();
				$('#'+target).show();
				
				chart.refresh();				
				chart.parse(result,"json");
			}
		}
	});
}

function integrationChartLoad(dhtmlxChart, p_grid, target, config, debug, noMsg) {

	if(config.view == "PIE"){
		dhtmlxChart = new dhtmlXChart({
	    	 view: "pie",
	         container: target,
	         value: config.value,
	         legend: config.label,
	         pieInnerText: config.value,
	         shadow: 0
	    });
 	} else if(config.view == "donut"){
		dhtmlxChart = new dhtmlXChart({
	        view: "donut",
	        container: target,
	        value: config.value,
	        legend: {
	            width: 75,
	            align: "right",
	            valign: "middle",
	            template: config.label
	        },
	        gradient: 1,
	        shadow: false
	    });				    
 	} else if(config.view == "BAR"){
		dhtmlxChart =  new dhtmlXChart({
            view:"bar",
            container:target,
            value:config.value,
            radius:0,
            border:true,
            xAxis:{
                template:config.label
            },
            yAxis:{
                start:0,
                end:100,
                step:10,
                template:function(obj){

                    return (obj%20?"":obj);
                }
            }
        });
	}	
	dhtmlxChart.parse(p_grid, "dhtmlxgrid");
}

