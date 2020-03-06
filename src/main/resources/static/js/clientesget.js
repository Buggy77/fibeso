$(document).ready(function() {
	var _ctx = $("meta[name='ctx']").attr("content");
	
	$('#cp').blur(function(e){
		cp = $('#cp').val().replace(/ /g,'');
		//alert('Valor: ' + cp)
		//obtenColoniasDir(cp);
		obtdireccion();
	});
	
	$("form").submit(function() { 
        var cp =  $('#cp').val();
        if (cp == '') {
        	$('#cp').val('0');
        }
        var dat1 = $('#idRegAsentaS').val();
        var dat2 = $('#descAsentaS').val();
        var dat3 = $('#idRegTipoS').val();
        console.log('Valor de dat1: ' + dat1);
        console.log('Valor de dat2: ' + dat2);
        console.log('Valor de dat3: ' + dat3);
        if(dat1 == '') {
	        var selected = $("#asentamientos option:selected");
			$('#idRegAsentaS').val(selected.val());
			$('#descAsentaS').val(selected.attr('descasenta'));
			$('#idRegTipoS').val(selected.attr('idtipo'));
        }
        
    });
	
	/*
	$("#cp").validate({
		  rules: {
		    amount: {
		      required: true,
		      digits: true
		    }
		  }
		});
	*/
	/*
	$('#nombre').blur(function(e){
		var global = [[${editMode}]];
		console.log('Dato de editMode: ' + dato);
	});
	*/
	
	$('#asentamientos').on('click', function(e){
		var selected = $("#asentamientos option:selected");
		//alert('Valor di: ' + selected.val());
		$('#idRegAsentaS').val(selected.val());
		$('#descAsentaS').val(selected.attr('descasenta'));
		$('#idRegTipoS').val(selected.attr('idtipo'));
		//var idRegTipoSL = $('#idRegTipoS').val();
		//alert('Valor de la variable: ' + idRegTipoSL);
		//cp = $('#cp').val().replace(/ /g,'');
		//alert('Valor: ' + cp)
		//obtenColoniasDir(cp);
		//obtdireccion();
	});
	
	$('#asentamientos').on('change', function(e){
		var selected = $("#asentamientos option:selected");
		$('#idRegAsentaS').val(selected.val());
		$('#descAsentaS').val(selected.attr('descasenta'));
		$('#idRegTipoS').val(selected.attr('idtipo'));
	});
	
	/*
	$('#cp').on('input',function(e){
		if($('#cp').val().replace(/ /g,'').length<=5){
			cp = $('#cp').val().replace(/ /g,'');
			cp = pad(cp, 5);
			alert('Cambio! '+ cp);
		}
		//LLAMADO A FUNCIÓN DE AJAX
		//obtenColoniasDir(cp);
	});
	 */
		
	
	function obtdireccion(){
		var cp = $('#cp').val()
		var f = 0;
		//var dataResp;
		$.ajax({
			url : urldirecciones = _ctx + "/obtDireccionesB",
			type : "get",
			data : { 'cp': cp },
			success: function(response){
				//$('#idRegAsentaS').empty();
				//$('#idRegAsentaS').append($('<option>', {
				$('#asentamientos').empty();
				$('#asentamientos').append($('<option>', {
					disabled: '',
					value: 'NINGUNO',
					text: '-----Seleccionar-----'
						,
					descasenta: '',
					idtipo: ''
				}));
				//console.log(response.length);
				
				if(response.length == 0)
					{
						$('#idRegMnpioS').val('');
						$('#descMnpio').val('');
						$('#idRegEstadoS').val('');
						$('#descEstado').val('');
					}
					
				for (fila in response){
					if(f == 0){
						$('#idRegMnpioS').val(response[fila].idRegMnpioS);
						$('#descMnpio').val(response[fila].descMnpio);
						$('#idRegEstadoS').val(response[fila].idRegEstadoS);
						$('#descEstado').val(response[fila].descEstado);
						f=1;
					}
					//$('#idRegAsentaS').append($('<option>', {
					if(f==1){
						$('#asentamientos').append($('<option>', {
							selected: 'selected',
							value: response[fila].idRegAsentaS,
							text: response[fila].descAsentaS + '/' + response[fila].descAsentamiento
							,
							descasenta: response[fila].descAsentaS,
							idtipo: response[fila].idRegTipoS
						}));
						f=2;
					} else {
						$('#asentamientos').append($('<option>', {
							value: response[fila].idRegAsentaS,
							text: response[fila].descAsentaS + '/' + response[fila].descAsentamiento
							,
							descasenta: response[fila].descAsentaS,
							idtipo: response[fila].idRegTipoS
						}));
					}
				}
			},
			error: function (e) {
				//alert('Error!!!')
			}
		
			
		});
	}
	
	function pad (str, max) {
	  str = str.toString();
	  return str.length < max ? pad("0" + str, max) : str;
	}
	
	function obtenColoniasDir(strcp){
		var urldirecciones = _ctx + "/obtDirecciones";
		alert('Valor de urldirecciones: ' + urldirecciones);
		//$("#colonia").load(urldirecciones,strcp);
		$('#idRegAsentaS').load(urldirecciones,$('#cp').serialize());
	}
		
	function obtenColonias(strcp){
		alert("Entra");
		//var cp=$(#cp).on('input',function(e)){
			
		//}
		$.ajax({
			url: getContextPath() + "/getAsentamientosxcp",
			type: "get",
			success: function(response) {
				$('#colonia').empty();
				$('#colonia').append($('<option>', {
					disabled: '',
				    value: 'NINGUNO',
				    text:  '-----Seleccionar-----'
				}));
				for (item in response) {
					$('#colonia').append($('<option>', {
					    value: response[item].indRegAsentaS,
					    text:  response[item].descAsentaS
					}));
				}
			},
			error: function(e){
				//TODO: colocar la etiqueta para mandar error
				alert('Error en la recuperación de los asentamientos');
			}
		});
	}
	
	//**************************************************************
	//VALIDACIONES FORM BENEF
	
	/*
	$('#validaform').on('click', function(e){
		validaForm();
	});
	
	$('#beneficiariosTitular2\\.registrar1').change(function(){
		var value = $(this).prop("checked") ? 'true' : 'false';                     
	    console.log(value);
	});
	
	function validaForm(){
		var cont = 0;
		$('#beneficiarioForm').find(':checkbox').each(function() {
			var elementoCheck = this;
			console.log('Valor de id check: ' + elementoCheck.id);
			console.log('Valor de nombre check: ' + elementoCheck.name);
			
			var element = elementoCheck.id;
			//element = "#"+element.replace(".","\\\\.");
			//element = element.replace(".","\\\\.");
			//element = element.replace(".","\\\.");
			element = element.replace(".","\\.");  //--> Funcional
			console.log('Valor sustituído: ' + element);
			//if($("#beneficiariosTitular2\\.registrar1").is(':checked')){
			//	console.log('SELECCIONADO 1');
			//}
			//if($("#"+element).prop("checked")){
			//if($(element).prop("checked")){
			console.log('Verificar que toma los cambios 1');
			if(element == "beneficiariosTitular2\\.registrar1"){
				console.log('Iguales: ' + elementoCheck.id);
			}
			if($("#"+element).prop("checked")){
				console.log('SELECCIONADO 5');
			}
			if($("#beneficiariosTitular2\\.registrar1").prop("checked")){  //--> Funcional
				console.log('SELECCIONADO 2');
			}
			if($("checkbox[id='beneficiariosTitular2.registrar1']").prop('checked')){
				console.log('SELECCIONADO');
			}
			if($("checkbox[name='beneficiariosTitular[2].registrar']").prop('checked')){
				console.log('SELECCIONADO 3');
			}
			if($("#beneficiariosTitular\\[2\\]\\.registrar").is(':checked')){
				console.log('SELECCIONADO 4');
			}
			//if(('#beneficiariosTitular2\\.registrar1').prop('checked')){
			//	console.log('SELECCIONADO');
			//}
			//if($('#'+elementoCheck.id).is(':checked')){
			//	console.log('El elemto: ' +elementoCheck.id+ ' con valor: ' + elementoCheck.value + ' esta seleccionado.');
			//}
		});
	}
	
	*/
	//**************************************************************
	
	//MANEJO FORM CONVENIOS
	$('#estados\\.idRegEstadoS').on('change', function(e){
		//console.log('Cambio');
		var selected = $("#estados\\.idRegEstadoS option:selected");

		//console.log('Valor select: ' + selected.val());
		valorsel = selected.val();
		
		$.ajax({
			url : urldirecciones = _ctx + "/obtmnpiosestado",
			type : "get",
			data : { 'cvmnpio': valorsel },
			success: function(response){

				$('#municipios\\.idRegMnpioS').empty();
				$('#municipios\\.idRegMnpioS').append($('<option>', {
					disabled: '',
					value: '',
					text: '-----Seleccionar-----'
				}));
				//console.log(response.length);
				
				//if(response.length == 0)
				//	{
				//	}
					
				for (fila in response){
					$('#municipios\\.idRegMnpioS').append($('<option>', {
						value: response[fila].idRegMnpioS,
						text: response[fila].descMnpio
					}));
				}
			},
			error: function (e) {
				//alert('Error!!!')
			}
		});
		//console.log('Texto select: ' + selected.text())
		//$('#idRegAsentaS').val(selected.val());
		//$('#descAsentaS').val(selected.attr('descasenta'));
		//$('#idRegTipoS').val(selected.attr('idtipo'));
		
		//var idRegTipoSL = $('#idRegTipoS').val();
		//alert('Valor de la variable: ' + idRegTipoSL);
		//cp = $('#cp').val().replace(/ /g,'');
		//alert('Valor: ' + cp)
		//obtenColoniasDir(cp);
		//obtdireccion();
	});
	
	//SE COMENTA FUNCIÓN DEBIDO A QUE LA FUNCIONALIDAD SE HACE POSTERIOR A LA CARGA DEL DATO
	/*
	$('#clientes\\.rfc').blur(function(e){
		//console.log('Entra en función');
		if($('#clientes\\.rfc').val().length==13){
			//console.log('Longitud 13');
			inputvalor = $('#clientes\\.rfc').val();
			
			$.ajax({
				url : urldirecciones = _ctx + "/obtbeneficiarioscosto",
				type : "get",
				data : { 'rfctitular': inputvalor },
				success: function(response){
					if(response.mensaje=='ClienteNoExiste'){
						$('#infoModal').modal('show');
					}else {
						
						 * PENDIENTE YA QUE SE PASO EN LA SELECCION DE PAQUETE
						//var total = 0;
						//var cospaq = $('#precioPaq').val();
						//if (cospaq==''){
						//	$('#precioPaq').val('0');
						//	cospaq = 0;
						//}
						
						//$('#subTotalConvenio').val(response.valor);
						//total = Number(cospaq) + Number(response.valor);
						//$('#totalConvenio').val(total);
						
					}
					
					console.log('Respuesta: ' + response);
					console.log('Respuesta: ' + response.mensaje);
					console.log('Respuesta: ' + response.valor);
				},
				error: function (e) {
					//alert('Error!!!')
				}
			});
		}
	});
	*/
	
	$('#paquetespfs\\.idPaqPF').on('change', function(e){
		console.log('Cambio paquete');
		var selected = $("#paquetespfs\\.idPaqPF option:selected");

		//console.log('Valor select: ' + selected.val());
		valorsel = selected.val();
		
		$.ajax({
			url : urldirecciones = _ctx + "/obtinfopaquete",
			type : "get",
			data : { 'cvpaq': valorsel },
			success: function(response){
				//console.log('Valor servicios' + response.serviciosPaq);
				//console.log('Valor precio' + response.precioPaq);
				$("#serviciosPaq").val(response.serviciosPaq);
				$("#precioPaq").val(response.precioPaq);
				var total = 0;
				var cospaq = $('#precioPaq').val();
				if (cospaq==''){
					$('#precioPaq').val('0');
					cospaq = 0;
				}
				var cosbenef = $('#subTotalConvenio').val();
				if (cosbenef==''){
					$('#subTotalConvenio').val('0');
					cosbenef = 0;
				}
				total = Number(cospaq) + Number(cosbenef);
				$('#totalConvenio').val(total);
				
				
				var esrenovacion =  $('#isrenova').val();
				if(esrenovacion=='true'){
					console.log('Invocar llamado de retroactivo');
					obtCostoRetroactivo();
				}
				//obtCostoAsegurados();
				
				//console.log('Regresa1');
				//for (fila in response){
				//	console.log('val1' + response[fila].serviciosPaq);
				//	console.log('val1' + response[fila].precioPaq);
					//$("#serviciosPaq").val(response[fila].serviciosPaq);
					//$("#precioPaq").val(response[fila].precioPaq);
				//}
			},
			error: function (e) {
				//alert('Error!!!')
			}
		});

	});
	
	/*
	function obtCostoAsegurados(){
		console.log('Segunda invocación');
		
		var selected = $("#paquetespfs\\.idPaqPF option:selected");
		valorsel = selected.val();
		if($('#clientes\\.rfc').val().length==13){
			var inputvalor = $('#clientes\\.rfc').val();
			
			$.ajax({
				url : urldirecciones = _ctx + "/obtCostoBeneficiario",
				type : "get",
				data : { 'rfctitular' : inputvalor, 'cvpaq': valorsel },
				success: function(response){
						
					var total = 0;
					var cospaq = $('#precioPaq').val();
					if (cospaq==''){
						$('#precioPaq').val('0');
						cospaq = 0;
					}
					
					//var costoben = $('#subTotalConvenio').val();
					//if(costoben==''){
					//	$('#subTotalConvenio').val('0');
					//	costoben = 0;
					//}
					
					$('#subTotalConvenio').val(response.valor);
					total = Number(cospaq) + Number(response.valor);
					$('#totalConvenio').val(total);
					
					//LLAMADO A FUNCIÓN RETROACTIVO
					var esrenovacion =  $('#isrenova').val();
					if(esrenovacion=='true'){
						console.log('Invocar llamado de retroactivo');
						obtCostoRetroactivo();
					}
					//LLAMADO A FUNCIÓN RETROACTIVO
					
				},
				error: function (e) {
					//alert('Error!!!')
				}
			});
		}
	}
	*/
	
	//COSTO RETROACTIVO
	function obtCostoRetroactivo(){
		console.log('Tercera invocación');
		
		var selected = $("#paquetespfs\\.idPaqPF option:selected");
		valorsel = selected.val();
		if($('#clientes\\.rfc').val().length==13){
			var inputvalor = $('#clientes\\.rfc').val();
			
			$.ajax({
				url : urldirecciones = _ctx + "/obtCostoRetroactivo",
				type : "get",
				data : { 'rfctitular' : inputvalor, 'cvpaq': valorsel },
				success: function(response){
						
					var total = 0;
					$('#costoRetroConvenio').val(response.valor);
					var cosretro = $('#costoRetroConvenio').val();
					if (cosretro==''){
						$('#costoRetroConvenio').val('0'); //--> AQUI 7ENE2020
						cospaq = 0;
					}
					
					//$('#costoRetroConvenio').val(response.valor);
					//var cosretro = $('#costoRetroConvenio').val();
					var cospaq = $('#precioPaq').val();
					var cossubto = $('#subTotalConvenio').val();
					var cossubtof = Number(cosretro) + Number(cossubto);
					
					$('#subTotalConvenio').val(cossubtof);
					
					total = Number(cospaq) + Number(cossubtof);
					$('#totalConvenio').val(total);
					
				},
				error: function (e) {
					//alert('Error!!!')
				}
			});
		}
	}
	//COSTO RETROACTIVO
	
	
})


