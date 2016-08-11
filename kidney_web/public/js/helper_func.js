function createPopupWindow(options)
{
    var div = $("<div class='popup-window container-fluid'></div>");
    var row = $("<div class='row-fluid'></div>").appendTo(div);
    var content = $("<div class='popup-content'></div>").appendTo(row);
    content.addClass("col-xs-10");
    if(options.colsm)
        content.addClass("col-sm-"+options.colsm);

    if(options.scrollable)
      content.addClass("overflowAuto");
    else
      content.addClass("overflowHidden");

    if(options.padding)
      content.css("padding", options.padding);

    div.content = content;
    div.click(function(e){
      if(e.target !== this)
        return false;
          $(this).remove();
    });
    return div;
}

function createAuctionStatusIndicatior(status, elem)
{ 
    if(status == 0)
        elem.removeClass().addClass("label label-danger state").text("Not started").attr("data-status", status);
    else if(status == 1)
        elem.removeClass().addClass("label label-success state").text("Started").attr("data-status", status);
    else if(status == 2)
        elem.removeClass().addClass("label label-warning state").text("Waiting payment").attr("data-status", status);
}

function confirmDialog(message, callback)
{     
      bootbox.confirm(message, function(result){
          if(result)
          {
              if(typeof(callback) !== "undefined")
                  callback();
          }
      });
}

function alertBox(message, callback)
{
    bootbox.alert(message, function(){
          if(typeof(callback) !== "undefined")
              callback();
    });
}

function createTimerLabelByTime(time)
{
    var ob = null;
    var msh = 1000*60*60;
    var diff = new Date(time) - new Date();
    if(diff > msh * 24)
        ob = $("<span class='label label-success'></span>");
    else if(diff > msh*5)
        ob = $("<span class='label label-warning'></span>")
    else ob = $("<span class='label label-danger'></span>");
    return ob;
}

function initializeCounter(e, time_end)
{
    var time = new Date(time_end);
   
    time = new Date(time.getFullYear(), time.getMonth(), time.getDate()+1);
    e.countdown({until:time,
        layout: "{d<}{dn} {dl}{d>} {hn}{sep}{mn}{sep}{sn}",
        format: "dHMS"
    });
    //e.countdown("pause");
}

function appendTimerIconToParent(parent)
{
    var icon = $("<span></span>").addClass("glyphicon glyphicon-time").appendTo(parent);
    parent.append("&nbsp;");    
}

function acknowledgeMessages(to)
{
    $.post("../ajax/realtime/acknowledge_message.php", {to:to}, function(data){
        console.log(data);
    });
}

function updateMessageCounter(action)
{
    $.post("../ajax/realtime/check_new_messages.php", {action: action}, function(data){
        if(action == 'check')
        {
            if(parseInt(data) > 0)
                $("#messagesCounter").attr("data-count", data).text(data); 
            else 
                 $("#messagesCounter").attr("data-count", data).text(""); 
        }

        
         console.log(data);
   });
}

function addToScrollContainer(parent, data)
{
	parent.children(":first").children(":first").html(data);
}
function appendToScrollContainer(parent, data)
{
	parent.children(":first").children(":first").append(data);
}
function clearScrollContainer(cnt)
{
	cnt.children(":first").children(":first").html("");
}
function loadScroller(ob){
	ob.mCustomScrollbar({
        scrollButtons:{
          enable:true
        },
	    axis: 'y',
	    theme: "dark-thick",
	    scrollInertia: 300
  	}); 
}
function loadScrollerAutohide(ob){
    ob.mCustomScrollbar({
        scrollButtons:{
          enable:true
        },
        autoHideScrollbar: true,
        axis: 'y',
        theme: "dark-thick",
        scrollInertia: 300
    }); 
}

function loadBothScroller(ob){
    ob.mCustomScrollbar({
        scrollButtons:{
          enable:true
        },
        
        advanced:{ autoExpandHorizontalScroll: true },
        axis: 'yx',
        theme: "dark-thick",
        scrollInertia: 300
    }); 
}
function log(message)
{
  console.log(message);
}
function message(message)
{
	console.log(message);
  if(message.length == 0)
    return;
  alertBox(message);
}
function globalAjaxSuccess(data){
    message(data);
}
function globalAjaxErrors(data, statusText, jqXHR)
{	
   console.log("From global ajax errors: ", data.responseText);
	 message(data.responseText);
}
function removeGracefully(elem, time, callback)
{
	time = typeof time !== 'undefined' ? time : 300;
	$(elem).fadeTo(300, 0,  function(){
		$(this).remove();
		if(typeof callback != "undefined")
			callback();
	});
}
function highlightElement(elem)
{	
	$(".blinkingContainer").remove();
	$(".activeContainer").removeClass("activeContainer");
	elem.css("position", "relative").append($("<div class='blinkingContainer'></div>"));
	elem.addClass("activeContainer");
}
function removeHighlight(elem)
{
	elem.find(".blinkingContainer").remove();
	elem.removeClass("activeContainer");
}

function removeCommas(nStr)
{
    var x = nStr.split(",");
    return x.join('');
}
//function to format bites bit.ly/19yoIPO
function bytesToSize(bytes) {
   var sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
   if (bytes == 0) return '0 Bytes';
   var i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));
   return Math.round(bytes / Math.pow(1024, i), 2) + ' ' + sizes[i];
}

function addCommas(nStr)
{
  nStr += '';
  var x = nStr.split('.');
  var x1 = x[0];
  var x2 = x.length > 1 ? '.' + x[1] : '';
  var rgx = /(\d+)(\d{3})/;
  while (rgx.test(x1)) {
    x1 = x1.replace(rgx, '$1' + ',' + '$2');
  }
  return x1 + x2;
}

function formatNumber(number)
{
    console.log(number);
    if(isNaN(number) || number == null || number == undefined)
      return number;
    var number = number.toFixed(2) + '';
    var x = number.split('.');
    var x1 = x[0];
    var x2 = x.length > 1 ? '.' + x[1] : '';
    var rgx = /(\d+)(\d{3})/;
    while (rgx.test(x1)) {
        x1 = x1.replace(rgx, '$1' + ',' + '$2');
    }
    return x1 + x2;
}

function number_format (number, decimals, dec_point, thousands_sep) {
    var n = number, prec = decimals;

    var toFixedFix = function (n,prec) {
        var k = Math.pow(10,prec);
        return (Math.round(n*k)/k).toString();
    };

    n = !isFinite(+n) ? 0 : +n;
    prec = !isFinite(+prec) ? 0 : Math.abs(prec);
    var sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep;
    var dec = (typeof dec_point === 'undefined') ? '.' : dec_point;

    var s = (prec > 0) ? toFixedFix(n, prec) : toFixedFix(Math.round(n), prec); 
    //fix for IE parseFloat(0.55).toFixed(0) = 0;

    var abs = toFixedFix(Math.abs(n), prec);
    var _, i;

    if (abs >= 1000) {
        _ = abs.split(/\D/);
        i = _[0].length % 3 || 3;

        _[0] = s.slice(0,i + (n < 0)) +
               _[0].slice(i).replace(/(\d{3})/g, sep+'$1');
        s = _.join(dec);
    } else {
        s = s.replace('.', dec);
    }

    var decPos = s.indexOf(dec);
    if (prec >= 1 && decPos !== -1 && (s.length-decPos-1) < prec) {
        s += new Array(prec-(s.length-decPos-1)).join(0)+'0';
    }
    else if (prec >= 1 && decPos === -1) {
        s += dec+new Array(prec).join(0)+'0';
    }
    return s; 
}
function InitializeDatePicker(elem)
{
    var today = new Date();
    var today_formated = (today.getDate()+1) + "."+(today.getMonth() + 1) + "." + today.getFullYear();
    $(elem).datepicker("setDate", today_formated);
}
function enableDatepicker(ob)
{
    ob.datepicker("option", "disabled", false);
    ob.attr("disabled", false);
}

function disableDatepicker(ob)
{
    ob.datepicker("option", "disabled", true);
    ob.attr("disabled", true);
}

function handleDatepicker(ob, status)
{
    if(status == 1 || status == 2)
        disableDatepicker(ob)
    else if(status==0) 
        enableDatepicker(ob);
}