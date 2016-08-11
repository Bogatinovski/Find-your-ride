$(document).ready(function(){
	var content = $("#content");

	$("#findTrips").click(function(){
		content.html("");
		createTripsTab();
	});

	$("#offerTrip").click(function(){
		var jqXHR = $.get("scripts/get_login_status.php", function(data){
			showNewTripScreen();
		});
		jqXHR.error(function(){
			showLoginScreen();
		});
	});

	function showNewTripScreen()
	{
		content.html("");
		var panel =$("<div class='panel panel-active'></div>").appendTo(content);
		var head = $("<div class='panel-heading'></div>").appendTo(panel);
		$("<h3 class='panel-title'></h3>").html("Закажани патувања").appendTo(head);
		var body = $("<div class='panel-body'></div>").appendTo(panel);
		var btn = $("<button class='btn btn-warning btn-large' id='addDestination'>Нова дестинација</button>").appendTo(body);
		btn.click(showNewTripForm);

		$.getJSON("scripts/get_trips_user.php", function(data){
			
			var trips = $("<ul class='list-group text-left' id='trips'></ul>").appendTo(body);
			for(var i=0; i<data.length; i++)
			{
				createAdminTripHTML(data[i]).click(viewAdminTrip).appendTo(trips);
			}
		});

		var footer = $("<div class='panel-footer'></div>").appendTo(panel);
		$("<a href='#' id='logout'>Одјави се</a>").click(logout).appendTo(footer);
	}

	function logout()
	{
		$.post("scripts/logout.php", function(data){
			console.log(data);
			content.html("");
		});
	}

	function showNewTripForm()
	{
		content.html("");
		$.getJSON("scripts/get_all_destinations.php", function(data){
			 var row = $("<div class='row'></div>").appendTo(content);
			 var col1 = $("<div class='col-xs-6'></div>").appendTo(row);
			 var col2 = $("<div class='col-xs-6'></div>").appendTo(row);
			 var select = $("<select id='from'></select>").appendTo(col1);
		
			 for(var i=0; i<data.length; i++)
			 {
			 	var e = data[i];
			 	$("<option></option>").html(e.destination).val(e.destination_id).appendTo(select);
			 }

			 var select2 = select.clone().attr("id", "to").appendTo(col2);
			 select.select2();
			 select2.select2();
			 //select.change(findTrips);
			 //select2.change(findTrips);

			 row = $("<div class='row'></div>").appendTo(content);
		 	 col1 = $("<div class='col-xs-12'></div>").appendTo(row);
		 	 var ul = $("<ul class='list-group text-left' id='trips'></ul>").appendTo(col1);

			var li = $("<li class='list-group-item'></li>").appendTo(ul);
			var row = $("<div class='row'></div>").appendTo(li);
			$("<div class='col-xs-4'></div>").html("Датум: ").appendTo(row);
			var div = $("<div class='col-xs-8'></div>").appendTo(row);
			var input = $("<input type='date' id='datepicker' required>").appendTo(div);
			//input.datepicker();

			li = $("<li class='list-group-item'></li>").appendTo(ul);
			row = $("<div class='row'></div>").appendTo(li);
			$("<div class='col-xs-4'></div>").html("Време: ").appendTo(row);
			var div = $("<div class='col-xs-8'></div>").appendTo(row);
			var input = $("<input type='time' id='timepicker' required>").appendTo(div);

			li = $("<li class='list-group-item'></li>").appendTo(ul);
			row = $("<div class='row'></div>").appendTo(li);
			$("<div class='col-xs-4'></div>").html("Слободни седишта: ").appendTo(row);
			div = $("<div class='col-xs-8'></div>").appendTo(row);
			$("<input type='number' id='sits' min='1' required>").appendTo(div);

			li = $("<li class='list-group-item'></li>").appendTo(ul);
			row = $("<div class='row'></div>").appendTo(li);
			$("<div class='col-xs-4'></div>").html("Телефон: ").appendTo(row);
			div = $("<div class='col-xs-8'></div>").appendTo(row);
			$("<input type='tel' id='telefon' required>").appendTo(div);

			li = $("<li class='list-group-item'></li>").appendTo(ul);
			row = $("<div class='row'></div>").appendTo(li);
			$("<div class='col-xs-4'></div>").html("Цена: ").appendTo(row);
			div = $("<div class='col-xs-8'></div>").appendTo(row);
			$("<input type='price' id='cena'>").appendTo(div);

			li = $("<li class='list-group-item'></li>").appendTo(ul);
			row = $("<div class='row'></div>").appendTo(li);
			$("<div class='col-xs-4'></div>").html("Опис: ").appendTo(row);
			div = $("<div class='col-xs-8'></div>").appendTo(row);
			$("<textarea type='text' id='opis'></textarea>").appendTo(div);
			
			li = $("<li class='list-group-item'></li>").appendTo(ul);
			row = $("<div class='row'></div>").appendTo(li);
			var col1 = $("<div class='col-xs-4'></div>").appendTo(row);

			$("<button class='btn btn-danger' id='addTrip'>Додади</button>").click(addTrip).appendTo(col1);
		});
	}

	function addTrip()
	{
		var ob = {};
		ob.datum = $("#datepicker").val();
		ob.sits = $("#sits").val();
		ob.start = $("#from").val();
		ob.end = $("#to").val();
		ob.price = $("#cena").val();
		ob.opis = $("#opis").val();
		ob.telefon = $("#telefon").val();
		ob.vreme = $("#timepicker").val();

		if(!validateTrip(ob))
			return false;
		console.log(ob);

		var jqXHR = $.post("scripts/add_trip_web.php", ob, function(data){
			console.log(data);
			showNewTripScreen();
		});
		jqXHR.error(globalAjaxErrors);
	}

	function validateTrip(ob)
	{
		if(ob.datum.length == 0)
		{
			message("Внесете валиден датум");
			return false;
		}
		if(ob.vreme.length == 0)
		{
			message("Внесете валидно време");
			return false;
		}
		if(isNaN(ob.sits) || ob.sits.length == 0)
		{
			message("Внесете валиден број на седишта");
			return false;
		}
		if(ob.telefon.length == 0)
		{
			message("Внесете валиден телефонски број");
			return false;
		}

		return true;
	}

	function showLoginScreen()
	{
		var wall = $("<div class='account-wall'></div>").appendTo(content);
		$("<h1 class='text-center login-title'>Најавете се за да организирате патување</h1>").appendTo(wall);
		var form = $("<form class='form-signin' id='registerForm'></form>").appendTo(wall);
		form.submit(onLogin);
		$("<input type='text' id='email' class='form-control' placeholder='Email' required autofocus>").appendTo(form);
		$("<input type='password' id='password' class='form-control' placeholder='Password' required>").appendTo(form);
		$("<button class='btn btn-lg btn-primary btn-block' type='submit'>Sign in</button>").appendTo(form);
		var a =$(" <a href='#'' class='text-center new-account' id='register'>Create an account </a>").appendTo(wall);
		a.click(registerProfile);
	}

	function onLogin(e)
	{
		e.preventDefault();
		var email = $("#email").val();
		var password = $("#password").val();

		var jqXHR = $.post("scripts/login.php", {username:email, password:password}, function(data){
			showNewTripScreen();
		});
		jqXHR.error(globalAjaxErrors);

		return false;
	}



	function registerProfile()
	{
		alert();
	}

	function createTripsTab()
	{
		$.getJSON("scripts/get_all_destinations.php", function(data){
			 var row = $("<div class='row'></div>").appendTo(content);
			 var col1 = $("<div class='col-xs-6'></div>").appendTo(row);
			 var col2 = $("<div class='col-xs-6'></div>").appendTo(row);
			// var col3 = $("<div class='col-xs-2'></div>").appendTo(row);
			 var select = $("<select id='from'></select>").appendTo(col1);
			 //var button = $("<button class='btn btn-warning'>Search</button>").appendTo(col3);
			 //button.click(findTrips);
			 for(var i=0; i<data.length; i++)
			 {
			 	var e = data[i];
			 	$("<option></option>").html(e.destination).val(e.destination_id).appendTo(select);
			 }

			 var select2 = select.clone().attr("id", "to").appendTo(col2);
			 select.select2();
			 select2.select2();
			 select.change(findTrips);
			 select2.change(findTrips);


			 row = $("<div class='row'></div>").appendTo(content);
		 	 col1 = $("<div class='col-xs-12'></div>").appendTo(row);
		 	 var list = $("<ul class='list-group text-left' id='trips'></ul>").appendTo(col1);
		 	 select.change();
		});
	}

	function findTrips()
	{
		var trips = $("#trips").html("");
		var from = $("#from").val();
		var to = $("#to").val();

		$.getJSON("scripts/get_trip.php", {from:from, to:to}, function(data){
			
			for(var i=0; i<data.length; i++)
			{
				createTripHTML(data[i]).click(viewTrip).appendTo(trips);
			}

		});
	}



	function createTripHTML(e)
	{
		console.log(e);
		var li = $("<li class='list-group-item row'></li>").attr("data-id", e.trip_id).appendTo(trips);
		var col1 = $("<div class='col-xs-4'></div>").html(e.first + " " +e.last ).appendTo(li);
		var col2 = $("<div class='col-xs-4'></div>").html("<b>"+e.telefon+"</b>").appendTo(li);
		var col3 = $("<div class='col-xs-4'></div>").html(e.datetime).appendTo(li);
		
		return li;
	}	

	function createAdminTripHTML(e)
	{
		var li = $("<li class='list-group-item row'></li>").attr("data-id", e.trip_id).appendTo(trips);
		var col1 = $("<div class='col-xs-4'></div>").html(e.od+ " - " +e.do).appendTo(li);
		var col2 = $("<div class='col-xs-4'></div>").html("<b>"+e.datetime+"</b>").appendTo(li);
		var col3 = $("<div class='col-xs-4'></div>").html(e.sits + " места").appendTo(li);
		
		return li;
	}

	function viewAdminTrip()
	{
		var trip_id = $(this).attr("data-id");
		loadTripAdmin(trip_id);
	}

	function loadTripAdmin(trip_id)
	{
		$.getJSON('scripts/get_single_trip.php', {trip_id:trip_id}, function(e){
			content.html("");
			console.log(e);
			
			var ul = $("<ul class='list-group text-left' id='trips'></ul>").appendTo(content);
			var li = $("<li class='list-group-item'></li>").appendTo(ul);
			var row = $("<div class='row'></div>").appendTo(li);
			$("<div class='col-xs-4'></div>").html("Организатор: ").appendTo(row);
			$("<div class='col-xs-8'></div>").html(e.first + " " + e.last).appendTo(row);

			li = $("<li class='list-group-item'></li>").appendTo(ul);
			row = $("<div class='row'></div>").appendTo(li);
			$("<div class='col-xs-4'></div>").html("Датум: ").appendTo(row);
			$("<div class='col-xs-8'></div>").html(e.datetime).appendTo(row);

			li = $("<li class='list-group-item'></li>").appendTo(ul);
			row = $("<div class='row'></div>").appendTo(li);
			$("<div class='col-xs-4'></div>").html("Слободни седишта: ").appendTo(row);
			$("<div class='col-xs-8'></div>").html(e.sits).appendTo(row);

			li = $("<li class='list-group-item'></li>").appendTo(ul);
			row = $("<div class='row'></div>").appendTo(li);
			$("<div class='col-xs-4'></div>").html("Телефон: ").appendTo(row);
			$("<div class='col-xs-8'></div>").html(e.telefon).appendTo(row);

			li = $("<li class='list-group-item'></li>").appendTo(ul);
			row = $("<div class='row'></div>").appendTo(li);
			$("<div class='col-xs-4'></div>").html("Цена: ").appendTo(row);
			$("<div class='col-xs-8'></div>").html(e.cena + " ден.").appendTo(row);

			li = $("<li class='list-group-item'></li>").appendTo(ul);
			row = $("<div class='row'></div>").appendTo(li);
			$("<div class='col-xs-4'></div>").html("Опис: ").appendTo(row);
			$("<div class='col-xs-8'></div>").html(e.opis).appendTo(row);
			
			li = $("<li class='list-group-item'></li>").appendTo(ul);
			row = $("<div class='row'></div>").appendTo(li);
			var col1 = $("<div class='col-xs-4'></div>").appendTo(row);
			var col2 =$("<div class='col-xs-4'></div>").appendTo(row);
			var col3 =$("<div class='col-xs-4'></div>").appendTo(row);

			$("<button class='btn btn-danger' id='removeTrip'>Избриши</button>").attr("data-id", trip_id).click(removeTrip).appendTo(col1);
			$("<button class='btn btn-success' id='addPassinger'>Додади Патник</button>").attr("data-id", trip_id).click(addPassinger).appendTo(col2);
			$("<button class='btn btn-warning' id='removePassinger'>Отстрани Патник</button>").attr("data-id", trip_id).click(removePassinger).appendTo(col3);
		
		});
	}

	function removeTrip()
	{
		var id = $(this).attr("data-id");
		var jqXHR = $.post("scripts/remove_trip.php", {trip_id:id}, function(data){
			console.log(data);
			showNewTripScreen();
		});
		jqXHR.error(globalAjaxErrors);	
	}

	function addPassinger()
	{
		var id = $(this).attr("data-id");

		var jqXHR = $.post("scripts/manage_trip.php", {trip_id:id, action:"add"}, function(data){
			console.log(data);
			loadTripAdmin(id);
		});
		jqXHR.error(globalAjaxErrors);
	}

	function removePassinger()
	{
		var id = $(this).attr("data-id");

		var jqXHR = $.post("scripts/manage_trip.php", {trip_id:id, action:"remove"}, function(data){
			console.log(data);
			loadTripAdmin(id);
		});
		jqXHR.error(globalAjaxErrors);
		
	}

	function viewTrip()
	{
		var trip_id = $(this).attr("data-id");
		$.getJSON('scripts/get_single_trip.php', {trip_id:trip_id}, function(e){
			content.html("");
			console.log(e);
			
			var ul = $("<ul class='list-group text-left' id='trips'></ul>").appendTo(content);
			var li = $("<li class='list-group-item'></li>").appendTo(ul);
			var row = $("<div class='row'></div>").appendTo(li);
			$("<div class='col-xs-4'></div>").html("Организатор: ").appendTo(row);
			$("<div class='col-xs-8'></div>").html(e.first + " " + e.last).appendTo(row);

			li = $("<li class='list-group-item'></li>").appendTo(ul);
			row = $("<div class='row'></div>").appendTo(li);
			$("<div class='col-xs-4'></div>").html("Датум: ").appendTo(row);
			$("<div class='col-xs-8'></div>").html(e.datetime).appendTo(row);

			li = $("<li class='list-group-item'></li>").appendTo(ul);
			row = $("<div class='row'></div>").appendTo(li);
			$("<div class='col-xs-4'></div>").html("Слободни седишта: ").appendTo(row);
			$("<div class='col-xs-8'></div>").html(e.sits).appendTo(row);

			li = $("<li class='list-group-item'></li>").appendTo(ul);
			row = $("<div class='row'></div>").appendTo(li);
			$("<div class='col-xs-4'></div>").html("Телефон: ").appendTo(row);
			$("<div class='col-xs-8'></div>").html(e.telefon).appendTo(row);

			li = $("<li class='list-group-item'></li>").appendTo(ul);
			row = $("<div class='row'></div>").appendTo(li);
			$("<div class='col-xs-4'></div>").html("Цена: ").appendTo(row);
			$("<div class='col-xs-8'></div>").html(e.cena + " ден.").appendTo(row);

			li = $("<li class='list-group-item'></li>").appendTo(ul);
			row = $("<div class='row'></div>").appendTo(li);
			$("<div class='col-xs-4'></div>").html("Опис: ").appendTo(row);
			$("<div class='col-xs-8'></div>").html(e.opis).appendTo(row);
			
		});
	}

	function message(data){
		console.log(data);
		alertBox(data);
	}

	function alertBox(message, callback)
	{
	    bootbox.alert(message, function(){
	          if(typeof(callback) !== "undefined")
	              callback();
	    });
	}
});