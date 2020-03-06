package com.fibeso.captacion.pfuneraria.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fibeso.captacion.pfuneraria.dto.BeneficiariosDto;
import com.fibeso.captacion.pfuneraria.dto.BeneficiariosTDto;
import com.fibeso.captacion.pfuneraria.dto.DireccionesDto;
import com.fibeso.captacion.pfuneraria.dto.VariablesDto;
import com.fibeso.captacion.pfuneraria.entity.Asentamientos;
import com.fibeso.captacion.pfuneraria.entity.Beneficiarios;
import com.fibeso.captacion.pfuneraria.entity.Cat_asentamientos;
import com.fibeso.captacion.pfuneraria.entity.Cat_cps;
import com.fibeso.captacion.pfuneraria.entity.Cat_estados;
import com.fibeso.captacion.pfuneraria.entity.Cat_mnpios;
import com.fibeso.captacion.pfuneraria.entity.Cat_servicios;
import com.fibeso.captacion.pfuneraria.entity.Clientes;
import com.fibeso.captacion.pfuneraria.entity.Convenios;
import com.fibeso.captacion.pfuneraria.entity.Direcciones;
import com.fibeso.captacion.pfuneraria.entity.Paquetespfs;
import com.fibeso.captacion.pfuneraria.entity.Parentescos;
import com.fibeso.captacion.pfuneraria.entity.Pfdetalle;
import com.fibeso.captacion.pfuneraria.entity.UsuariosOperador;
import com.fibeso.captacion.pfuneraria.entity.Velatorios;
import com.fibeso.captacion.pfuneraria.exception.ClienteIdNoExiste;
import com.fibeso.captacion.pfuneraria.repository.DireccionesRepository;
import com.fibeso.captacion.pfuneraria.service.BeneficiariosService;
import com.fibeso.captacion.pfuneraria.service.Cat_estadosService;
import com.fibeso.captacion.pfuneraria.service.Cat_mnpiosService;
import com.fibeso.captacion.pfuneraria.service.Cat_serviciosService;
import com.fibeso.captacion.pfuneraria.service.ClientesService;
import com.fibeso.captacion.pfuneraria.service.ConveniosService;
import com.fibeso.captacion.pfuneraria.service.ConveniosServiceImpl;
import com.fibeso.captacion.pfuneraria.service.PaquetespfsService;
import com.fibeso.captacion.pfuneraria.service.ParentescosService;
import com.fibeso.captacion.pfuneraria.service.PfdetalleService;
import com.fibeso.captacion.pfuneraria.service.UsuariosOperadorService;
import com.fibeso.captacion.pfuneraria.service.VelatoriosService;
import com.fibeso.captacion.pfuneraria.util.AjaxResponseBody;
import com.fibeso.captacion.pfuneraria.util.Generos;
import com.fibeso.captacion.pfuneraria.util.Sino;

import antlr.StringUtils;

@SessionAttributes({"servicioses","paqueteses"})
@Controller
public class ClientesController {

	@Autowired
	ClientesService clientesService;
	
	@Autowired
	ParentescosService parentescoService;
	
	@Autowired
	DireccionesRepository direccionesrepo;
	
	@Autowired
	PfdetalleService pfdetalleService;
	
	@Autowired
	PaquetespfsService paquetesService;
	
	@Autowired
	BeneficiariosService benefService;
	
	@Autowired
	ConveniosService conveniosService;
	
	@Autowired
	Cat_estadosService cat_estadosService;
	
	@Autowired
	Cat_mnpiosService cat_mnpiosService;
	
	@Autowired
	UsuariosOperadorService usroperadorService;
	
	@Autowired
	Cat_serviciosService cat_servicioService;
	
	@Autowired
	VelatoriosService velatorioService;
	
	@Autowired
	ConveniosServiceImpl convenioServiceImpl;
	
	//AuditorAwareImpl authentication;
	
	@PostMapping("/actualizarSession")
	public String actualizarSession(Model model, @ModelAttribute("sesiondto") VariablesDto sesiondtoL, 
			@SessionAttribute("servicioses") String servicioses, @SessionAttribute("paqueteses") String paqueteses) {
		System.out.println("Valor de request param servicioses: " + servicioses);
		System.out.println("Valor de request param paqueteses " + paqueteses);
		model.addAttribute("servicioses", sesiondtoL.getServicio());
		model.addAttribute("paqueteses", sesiondtoL.getPaquete());
		model.addAttribute("sesiondto", sesiondtoL);
		System.out.println("Valor de requetparam: " + sesiondtoL.getPaquete());
		
		return "redirect:/clienteForm";
		//return "user_form/cliente-view";
	}
	
	@GetMapping("/clienteForm")
	//public String userForm(Model model) {
	public String userForm(Model model, @SessionAttribute("servicioses") String servicioses, @SessionAttribute("paqueteses") String paqueteses) {
	//public String userForm(Model model, HttpSession session) {
		//System.out.println("El valor de session es: " + servicioses);
		//servicioses = "2";
		//System.out.println("El valor de session es2: " + servicioses);
		//session.setAttribute("servicio", "Test");
		System.out.println("Valor session: " + servicioses);
		model.addAttribute("disableImp", "true");
		model.addAttribute("clienteForm", new Clientes());
		model.addAttribute("direccionesDto", new DireccionesDto());
		//12Ene2020
		model.addAttribute("cpC", new Cat_cps());
		model.addAttribute("asentamientoC", new Asentamientos());
		model.addAttribute("estadoC", new Cat_estados());
		model.addAttribute("municipioC", new Cat_mnpios());
		//12Ene2020
		//model.addAttribute("listasentamientos", new Asentamientos());
		model.addAttribute("clienteList", clientesService.getAllClientes());
		//Map<String,String> generosClie = new LinkedHashMap<String, String>();
		//generosClie.put("F", "Femenino");
		//generosClie.put("M", "Másculino");
		//model.addAttribute("generos",generosClie);
		model.addAttribute("generos", Generos.values());
		model.addAttribute("listTab","active");
		//return "user_form/cliente-list";
		
		//15Ene2020
		BeneficiariosTDto beneficiariosDto = new BeneficiariosTDto();
		for(int i = 1; i <= 3; i++) {
			beneficiariosDto.addBeneficiariosTitular(new Beneficiarios());
		}
		
		/*
		Set<Beneficiarios> b = beneficiariosDto.getBeneficiariosTitular();
		//b.forEach(action);
		Iterator<Beneficiarios> itr = b.iterator();

		Iterator<Beneficiarios> c = beneficiariosDto.iterator();
		while(itr.hasNext()) {
			Beneficiarios d = (Beneficiarios) itr.next();
			//d.getN
			System.out.println("Iterator");
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
        */

		
		model.addAttribute("beneficiarioForm", beneficiariosDto);
		model.addAttribute("listaParentescos", parentescoService.getAllParentescos());
		model.addAttribute("listaAsegurados", Sino.values());
		
		//CONVENIOS
		//19Ene2020 - MAGG
		
		LocalDateTime fchHoy = LocalDateTime.now();
		LocalDateTime fchAnioAnt = fchHoy.minusYears(1);
		LocalDateTime fchHoy20 = fchHoy.plusDays(20);
		
		System.out.println("La fecha de hoy es: " + fchHoy);
		
		//DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyyMMdd");
		//LocalDateTime fechaPreVig = LocalDateTime.parse(fchHoy.toString(), formateador);
		//System.out.println("El valor formateado: " + fechaPreVig);
		//System.out.println(StringUtils.leftpad(Integer.toString(fchHoy.getMonthValue()),2,"0"));
		
		System.out.println("La fecha con un año posterior: " + fchAnioAnt);
		System.out.println("La fecha con 20 días de más: " + fchHoy20);
		//La fecha de hoy es: 2020-01-22T11:27:53.667
		//Integer anioInt = fchHoy.getYear();
		//String anio = Integer.toString(anioInt);
		//String fchVigPre = anio+"-12-31 00:00:00";
		//DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		//LocalDateTime fechaPreVig = LocalDateTime.parse(fchVigPre, formateador);
		
		String usuariolog="";
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			usuariolog = authentication.getName();
		}
		try {
		//model.addAttribute("convenioList", conveniosService.getAllConveniosActivosporOperador(usuariolog));
		model.addAttribute("convenioList", conveniosService.getAllConveniosporOperador(usuariolog));
		//model.addAttribute("convenioList", conveniosService.getAllConveniosxOperador(usuariolog));
		}catch (Exception e) {
			e.printStackTrace();
		}
		UsuariosOperador operador = new UsuariosOperador();
		Iterable<Cat_servicios> cat_servicios = null;
		//@SuppressWarnings("unchecked")
		//Iterable<Paquetespfs> paquetesI = (Iterable<Paquetespfs>) new Paquetespfs();
		Iterable<Paquetespfs> paquetesI = null;
		try {
			operador = usroperadorService.getUsuarioOperadorById(usuariolog);
			paquetesI = paquetesService.getAllPaquetespfsxVelatorio(operador.getIdVelatorio());
			cat_servicios = cat_servicioService.getAllCat_serviciosActivos();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("convenioForm", new Convenios());
		//model.addAttribute("listaPaquetes", paquetesService.getAllPaquetespfs());
		model.addAttribute("listaPaquetes", paquetesI);
		model.addAttribute("listaEstados", cat_estadosService.getAllCat_estados());
		model.addAttribute("litadoServicios", cat_servicios);
		
		VariablesDto variables = new VariablesDto();
		variables.setPaquete(paqueteses);
		variables.setServicio(servicioses);
		model.addAttribute("sesiondto", variables);
		
		//model.addAttribute("paqV", servicioses);
		//model.addAttribute("listaMnpios", attributeValue);
		//19Ene2020 - MAGG
		
		//15Ene2020
		//return "user_form/cliente-form";
		//13Ene2020 -  MAGG
		return "user_form/cliente-view";
		//13Ene2020 -  MAGG
	}
	
	@PostMapping("/clienteForm")
	public String crearCliente(@Valid @ModelAttribute("clienteForm") Clientes cliente, 
			//BindingResult resultado, @RequestParam("cp") String cp, ModelMap model) { //Funcional
			BindingResult resultado, @ModelAttribute("cpC") Cat_cps objCp, @ModelAttribute("direccionesDto") DireccionesDto listadto, 
			@ModelAttribute("municipioC") Cat_mnpios objMunicipio, @ModelAttribute("estadoC") Cat_estados objEstado,
			@ModelAttribute("asentamientoC") Asentamientos objAsentamiento, 
			@RequestParam("cp") String cp, @SessionAttribute("servicioses") String servicioses, 
			@SessionAttribute("paqueteses") String paqueteses, ModelMap model) {
		
			
			//BindingResult resultado, @RequestParam("asentamientos") List<String> listadto, ModelMap model) {
			//BindingResult resultado, @ModelAttribute("direccionesDto") DireccionesDto listadto, ModelMap model) {
		//String lcp = "";
		if (cp == null) { 
			cp = "0"; 
		} else if (cp=="") {
			cp = "0"; 
		}
		if(resultado.hasErrors()) {
			//System.out.println("Valor devuelto: " + listadto);
			model.addAttribute("clienteForm", cliente);
			//model.addAttribute("listasentamientos", cliente.getAsentamientos());
			model.addAttribute("direccionesDto", llenardireccionxcp(cp));
			
			//12Ene2020
			model.addAttribute("municipioC", objMunicipio);
			model.addAttribute("estadoC", objEstado);
			model.addAttribute("cpC", objCp);
			model.addAttribute("asentamientoC", objAsentamiento);
			model.addAttribute("formTab","active");
			//12Ene2020
			//VariablesDto variables = new VariablesDto();
			//variables.setPaquete(paqueteses);
			//variables.setServicio(servicioses);
			//model.addAttribute("sesiondto", variables);
			
			//*****
			/*
			BeneficiariosTDto beneficiariosDto = new BeneficiariosTDto();
			for(int i = 1; i <= 3; i++) {
				beneficiariosDto.addBeneficiariosTitular(new Beneficiarios());
			}
			model.addAttribute("beneficiarioForm", beneficiariosDto);
			model.addAttribute("listaParentescos", parentescoService.getAllParentescos());
			model.addAttribute("listaAsegurados", Sino.values());
			
			String usuariolog="";
			final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if(!(authentication instanceof AnonymousAuthenticationToken)) {
				usuariolog = authentication.getName();
			}
			model.addAttribute("convenioList", conveniosService.getAllConveniosActivosporOperador(usuariolog));
			UsuariosOperador operador = new UsuariosOperador();
			Iterable<Cat_servicios> cat_servicios = null;
			Iterable<Paquetespfs> paquetesI = null;
			try {
				operador = usroperadorService.getUsuarioOperadorById(usuariolog);
				paquetesI = paquetesService.getAllPaquetespfsxVelatorio(operador.getIdVelatorio());
				cat_servicios = cat_servicioService.getAllCat_serviciosActivos();
			}catch(Exception e) {
				e.printStackTrace();
			}
			//model.addAttribute("convenioForm", new Convenios());
			model.addAttribute("listaPaquetes", paquetesI);
			model.addAttribute("listaEstados", cat_estadosService.getAllCat_estados());
			model.addAttribute("litadoServicios", cat_servicios);
			
			
			VariablesDto variables = new VariablesDto();
			variables.setPaquete(paqueteses);
			variables.setServicio(servicioses);
			model.addAttribute("sesiondto", variables);
			*/
			//*****
			//model.addAttribute("formTab", "active");
		} else {
			try {
				//12Ene2020
				cliente.setMunicipio(objMunicipio);
				//objMunicipio.getClientes().add(cliente);
				cliente.setEstado(objEstado);
				//objEstado.getClientes().add(cliente);
				cliente.setCat_cps(objCp);
				//objCp.getClientes().add(cliente);
				cliente.setAsentamientos(objAsentamiento);
				//objAsentamiento.getClientes().add(cliente);
				//12Ene2020
				//Clientes cli = new Clientes()
				clientesService.crearCliente(cliente);
				
				//13Ene2020 - MAGG
				//model.addAttribute("convenioForm", new Convenios());
				model.addAttribute("clienteForm", new Clientes());
				
				model.addAttribute("direccionesDto", new DireccionesDto());
				model.addAttribute("cpC", new Cat_cps());
				model.addAttribute("asentamientoC", new Asentamientos());
				model.addAttribute("estadoC", new Cat_estados());
				model.addAttribute("municipioC", new Cat_mnpios());
				
				model.addAttribute("listTab","active");
				//13Ene2020 - MAGG
				
				
				
			}catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("formErrorMessage", e.getMessage());
				model.addAttribute("clienteForm", cliente);
				model.addAttribute("direccionesDto", llenardireccionxcp(cp));
				
				//12Ene2020 -  MAGG
				model.addAttribute("municipioC", objMunicipio);
				model.addAttribute("estadoC", objEstado);
				model.addAttribute("cpC", objCp);
				model.addAttribute("asentamientoC", objAsentamiento);
				//12Ene2020 - MAGG
				model.addAttribute("formTab", "active");
			}
		}
		
		//model.addAttribute("clienteForm", cliente);
		model.addAttribute("convenioForm", new Convenios());
		model.addAttribute("clienteList", clientesService.getAllClientes());
		model.addAttribute("generos", Generos.values());
		//model.addAttribute("formTab","active");
		//*****
		BeneficiariosTDto beneficiariosDto = new BeneficiariosTDto();
		for(int i = 1; i <= 3; i++) {
			beneficiariosDto.addBeneficiariosTitular(new Beneficiarios());
		}
		model.addAttribute("beneficiarioForm", beneficiariosDto);
		model.addAttribute("listaParentescos", parentescoService.getAllParentescos());
		model.addAttribute("listaAsegurados", Sino.values());
		
		String usuariolog="";
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			usuariolog = authentication.getName();
		}
		//model.addAttribute("convenioList", conveniosService.getAllConveniosActivosporOperador(usuariolog));
		model.addAttribute("convenioList", conveniosService.getAllConveniosporOperador(usuariolog));
		
		UsuariosOperador operador = new UsuariosOperador();
		Iterable<Cat_servicios> cat_servicios = null;
		Iterable<Paquetespfs> paquetesI = null;
		try {
			operador = usroperadorService.getUsuarioOperadorById(usuariolog);
			paquetesI = paquetesService.getAllPaquetespfsxVelatorio(operador.getIdVelatorio());
			cat_servicios = cat_servicioService.getAllCat_serviciosActivos();
		}catch(Exception e) {
			e.printStackTrace();
		}
		//model.addAttribute("convenioForm", new Convenios());
		model.addAttribute("listaPaquetes", paquetesI);
		model.addAttribute("listaEstados", cat_estadosService.getAllCat_estados());
		model.addAttribute("litadoServicios", cat_servicios);
		
		
		VariablesDto variables = new VariablesDto();
		variables.setPaquete(paqueteses);
		variables.setServicio(servicioses);
		model.addAttribute("sesiondto", variables);
		//*****
		
		//13Ene2020 -  MAGG
		//return "user_form/cliente-form";
		return "user_form/cliente-view";
		//return "redirect:/clienteForm";
		//13Ene2020 -  MAGG
			
		//return "";
	}
	
	@PostMapping("/modificarCliente")
	public String portModificarClienteForm(@Valid @ModelAttribute("clienteForm") Clientes cliente,
			BindingResult resultado, @ModelAttribute("direccionesDto") DireccionesDto listadto, 
			@ModelAttribute("municipioC") Cat_mnpios objMunicipio, @ModelAttribute("estadoC") Cat_estados objEstado,
			@ModelAttribute("cpC") Cat_cps objCp, @ModelAttribute("asentamientoC") Asentamientos objAsentamiento, 
			//@RequestParam("cp") String cp, @SessionAttribute("servicioses") String servicioses, 
			@SessionAttribute("servicioses") String servicioses,
			@SessionAttribute("paqueteses") String paqueteses, ModelMap model) {
		String cp ="";
		if (Objects.isNull(objCp.getCp())) { 
			cp = "0"; 
		} else if (Long.toString(objCp.getCp()).equals("")) {
			cp = "0"; 
		}
		/*
		if (cp == null) { 
			cp = "0"; 
		} else if (cp=="") {
			cp = "0"; 
		}
		*/
		if(resultado.hasErrors()) {
			model.addAttribute("clienteForm", cliente);
			model.addAttribute("direccionesDto", llenardireccionxcp(cp));
			model.addAttribute("municipioC", objMunicipio);
			model.addAttribute("estadoC", objEstado);
			model.addAttribute("cpC", objCp);
			model.addAttribute("asentamientoC", objAsentamiento);
			model.addAttribute("formTab","active");
			model.addAttribute("editMode","true");
		}else{
			try {
				cliente.setMunicipio(objMunicipio);
				cliente.setEstado(objEstado);
				cliente.setCat_cps(objCp);
				cliente.setAsentamientos(objAsentamiento);
				clientesService.actualizarCliente(cliente);
				model.addAttribute("clienteForm", new Clientes());
				model.addAttribute("listTab","active");
			} catch(Exception e) {
				System.out.println(e);
				e.printStackTrace();
				model.addAttribute("formErrorMessage", e.getMessage());
				model.addAttribute("clienteForm", cliente);
				model.addAttribute("direccionesDto", llenardireccionxcp(cp));
				model.addAttribute("municipioC", objMunicipio);
				model.addAttribute("estadoC", objEstado);
				model.addAttribute("cpC", objCp);
				model.addAttribute("asentamientoC", objAsentamiento);
				model.addAttribute("formTab", "active");
				model.addAttribute("editMode","true");
			}
		}
		model.addAttribute("clienteList", clientesService.getAllClientes());
		model.addAttribute("generos", Generos.values());
		
		//*****
		model.addAttribute("convenioForm", new Convenios());
		BeneficiariosTDto beneficiariosDto = new BeneficiariosTDto();
		for(int i = 1; i <= 3; i++) {
			beneficiariosDto.addBeneficiariosTitular(new Beneficiarios());
		}
		model.addAttribute("beneficiarioForm", beneficiariosDto);
		model.addAttribute("listaParentescos", parentescoService.getAllParentescos());
		model.addAttribute("listaAsegurados", Sino.values());
		
		String usuariolog="";
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			usuariolog = authentication.getName();
		}
		//model.addAttribute("convenioList", conveniosService.getAllConveniosActivosporOperador(usuariolog));
		model.addAttribute("convenioList", conveniosService.getAllConveniosporOperador(usuariolog));
		UsuariosOperador operador = new UsuariosOperador();
		Iterable<Cat_servicios> cat_servicios = null;
		Iterable<Paquetespfs> paquetesI = null;
		try {
			operador = usroperadorService.getUsuarioOperadorById(usuariolog);
			paquetesI = paquetesService.getAllPaquetespfsxVelatorio(operador.getIdVelatorio());
			cat_servicios = cat_servicioService.getAllCat_serviciosActivos();
		}catch(Exception e) {
			e.printStackTrace();
		}
		//model.addAttribute("convenioForm", new Convenios());
		model.addAttribute("listaPaquetes", paquetesI);
		model.addAttribute("listaEstados", cat_estadosService.getAllCat_estados());
		model.addAttribute("litadoServicios", cat_servicios);
		
		
		VariablesDto variables = new VariablesDto();
		variables.setPaquete(paqueteses);
		variables.setServicio(servicioses);
		model.addAttribute("sesiondto", variables);
		//*****
				
		return "user_form/cliente-view";
	}
	
	@PostMapping("/registrarConvenio")
	public String registrarConvenios(@Valid @ModelAttribute("convenioForm") Convenios conveniog, 
			BindingResult resultado, ModelMap model, @SessionAttribute("servicioses") String servicioses, 
			@SessionAttribute("paqueteses") String paqueteses) {
		
		if(resultado.hasErrors()) {
			model.addAttribute("convenioForm", conveniog);
			model.addAttribute("listaMnpios", cat_mnpiosService.getAllCat_mnpiosxEstado(conveniog.getEstados().getIdRegEstadoS()));
			model.addAttribute("formcTab", "active");
		} else {
			try {
				
				//10Feb2020
				if (servicioses.equals("1")) {
				//10Feb2020
				
				Long cantConv = conveniosService.getCantConveniosByRfc(conveniog.getClientes().getRfc());
				if(cantConv < 1) {
					//OBTENER EL TIPO CONTRATACIÓN
					Cat_servicios catservicio = cat_servicioService.getByIdTipoContratacion(servicioses);
					conveniog.setCatservicios(catservicio);
					//OBTENER EL OPERADOR
					String usuariolog="";
					final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
					if(!(authentication instanceof AnonymousAuthenticationToken)) {
						usuariolog = authentication.getName();
					}
					UsuariosOperador operador = new UsuariosOperador();
					operador = usroperadorService.getUsuarioOperadorById(usuariolog);
					conveniog.setUsuariosOperador(operador);
					//OBTENER VELATORIO
					Velatorios velatorio = velatorioService.getVelatorioByIdVelatorio(operador.getIdVelatorio());
					conveniog.setVelatorios(velatorio);
					//OBTENER AFILIADO(CLIENTE)
					Clientes cliente = clientesService.getClienteByRfc(conveniog.getClientes().getRfc());
					conveniog.setClientes(cliente);
					
					//GENERAR FOLIO
					String folioreg="";
					//--Siglas de Velatorio
					String siglasvelatorio = velatorio.getIdVelatorio() +"-";
					//--Siglas de paquete
					String[] partes = conveniog.getPaquetespfs().getIdPaqPF().split(" ");
					String siglaspaquete = partes[1] + "-";
					//--Fecha contratación
					LocalDateTime fchHoy = LocalDateTime.now();
					String fechafol = Integer.toString(fchHoy.getYear()) + 
							(Integer.toString(fchHoy.getMonthValue()).length()<2?"0"+Integer.toString(fchHoy.getMonthValue()):Integer.toString(fchHoy.getMonthValue())) + 
							//Integer.toString(fchHoy.getDayOfMonth()) + "-";
							(Integer.toString(fchHoy.getDayOfMonth()).length()<2?"0"+Integer.toString(fchHoy.getDayOfMonth()):Integer.toString(fchHoy.getDayOfMonth())) + "-";
					//--Número de adenda consecutivo
					String folAdenda = "";
					if (servicioses.equals("1")) {
						folAdenda = "00000-";
					}else {
						folAdenda = convenioServiceImpl.obtenerFolioAden(conveniog.getClientes().getRfc())+"-";
					}
					//--Número de convenio consecutivo
					String folConvenio = convenioServiceImpl.obtenerFolio();
					//if (folConvenio.equals(null)) {
					//	folConvenio = "00001";
					//}
					folioreg = siglasvelatorio+siglaspaquete+fechafol+folAdenda+folConvenio;
					System.out.println("El folio a registrar:" + folioreg);
					conveniog.setFolioConvenio(folioreg);
					//System.out.println("La fecha para folio: " + fechafol);
					//String folioConsig = convenioServiceImpl.obtenerFolio();
					//System.out.println("El folio convenio recuperador es: " + folioConsig);
					//String folioAdesig = convenioServiceImpl.obtenerFolioAden(conveniog.getClientes().getRfc());
					//System.out.println("El folio adenda recuperador es: " + folioAdesig);
					//FECHA CONTRATACIÓN
					conveniog.setFchContConve(fchHoy);
					//OBTENER PAQUETES PF Y PAQUETES PF DETALLE
					Paquetespfs paquete = paquetesService.getPaqueteByIdPaqPF(conveniog.getPaquetespfs().getIdPaqPF());
					conveniog.setPaquetespfs(paquete);
					//Integer anioInt = fchHoy.getYear();
					String anioStr = Integer.toString(fchHoy.getYear());
					//String fechaVigPre = anioStr+"-12-31 00:00:00";
					String fechaVigPre = anioStr+"-12-31";
					//DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					//LocalDateTime fechaPreVig = LocalDateTime.parse(fechaVigPre, formateador);
					//LocalDate fechaPreVig = LocalDate.parse(fechaVigPre, formateador);
					Date fechaPreVig = new SimpleDateFormat("yyyy-MM-dd").parse(fechaVigPre); 
					Pfdetalle paqueteDet = pfdetalleService.getPFxIdAndFchVigPrec(paquete.getIdPaqPF(), fechaPreVig, anioStr);
					conveniog.setPrecioPaq(paqueteDet.getPrecioPaq());
					conveniog.setServiciosPaq(paqueteDet.getServiciosPaq());
					conveniog.setCostoAfiliacion(paqueteDet.getPrecioPaq());
					
					//BLOQ
					conveniog.setSubTotalConvenio(new Double(0));
					conveniog.setTotalConvenio(paqueteDet.getPrecioPaq());
					//BLOQ
					//OBTENER CIUDAD DE CONTRATO
					Cat_mnpios ciudad = cat_mnpiosService.getCat_mnpioById(conveniog.getMunicipios().getIdRegMnpioS());
					conveniog.setMunicipios(ciudad);
					//OBTENER ESTADO DE CONTRATO
					Cat_estados estado = cat_estadosService.getCat_estadoById(conveniog.getEstados().getIdRegEstadoS());
					conveniog.setEstados(estado);
					conveniog.setActivo(true);
					conveniog = conveniosService.crearConvenio(conveniog);
					
					//ACTUALIZAR BENEFICIARIOS PRECIO Y FOLIO
					benefService.actualizarFolioCostoBeneficiario(conveniog.getClientes().getRfc(), anioStr, conveniog.getFolioConvenio(), paqueteDet.getPrecioAse());
					model.addAttribute("disableImp", "false");
					model.addAttribute("convenioForm", conveniog);
					model.addAttribute("listaMnpios", cat_mnpiosService.getAllCat_mnpiosxEstado(conveniog.getEstados().getIdRegEstadoS()));
					model.addAttribute("formcTab", "active");
					model.addAttribute("disableFields", "true");
					//model.addAttribute("enableImprimir", "true");
					
					//model.addAttribute("convenioForm", new Convenios());
					//model.addAttribute("listConvTab","active");
					
				}else {
					model.addAttribute("formErrorMessage", "El Afiliado ya cuenta con un convenio.");
					model.addAttribute("convenioForm", conveniog);
					model.addAttribute("listaMnpios", cat_mnpiosService.getAllCat_mnpiosxEstado(conveniog.getEstados().getIdRegEstadoS()));
					model.addAttribute("formcTab", "active");
				}
				
				//10Feb2020
				} else if (servicioses.equals("2")) {
					Long cantConv = conveniosService.getCantConveniosByRfc(conveniog.getClientes().getRfc());
					if(cantConv >= 1) {
						
						cantConv = conveniosService.getConvenioPosteriorExistente(conveniog.getClientes().getRfc());
						if (cantConv==0) {
							//OBTENER EL TIPO CONTRATACIÓN
							Cat_servicios catservicio = cat_servicioService.getByIdTipoContratacion(servicioses);
							conveniog.setCatservicios(catservicio);
							//OBTENER EL OPERADOR
							String usuariolog="";
							final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
							if(!(authentication instanceof AnonymousAuthenticationToken)) {
								usuariolog = authentication.getName();
							}
							UsuariosOperador operador = new UsuariosOperador();
							operador = usroperadorService.getUsuarioOperadorById(usuariolog);
							conveniog.setUsuariosOperador(operador);
							
							//OBTENER VELATORIO
							Velatorios velatorio = velatorioService.getVelatorioByIdVelatorio(operador.getIdVelatorio());
							conveniog.setVelatorios(velatorio);
							//OBTENER AFILIADO(CLIENTE)
							Clientes cliente = clientesService.getClienteByRfc(conveniog.getClientes().getRfc());
							conveniog.setClientes(cliente);
							
							//GENERAR FOLIO
							String folioreg="";
							//--Siglas de Velatorio
							String siglasvelatorio = velatorio.getIdVelatorio() +"-";
							//--Siglas de paquete
							String[] partes = conveniog.getPaquetespfs().getIdPaqPF().split(" ");
							String siglaspaquete = partes[1] + "-";
							//--Fecha contratación
							LocalDateTime fchHoy = LocalDateTime.now();
							String fechafol = Integer.toString(fchHoy.getYear()) + 
									(Integer.toString(fchHoy.getMonthValue()).length()<2?"0"+Integer.toString(fchHoy.getMonthValue()):Integer.toString(fchHoy.getMonthValue())) + 
									//Integer.toString(fchHoy.getDayOfMonth()) + "-";
									(Integer.toString(fchHoy.getDayOfMonth()).length()<2?"0"+Integer.toString(fchHoy.getDayOfMonth()):Integer.toString(fchHoy.getDayOfMonth())) + "-";
							//--Número de adenda consecutivo
							String folAdenda = "";
							folAdenda = convenioServiceImpl.obtenerFolioAden(conveniog.getClientes().getRfc())+"-";
							String folConvenio = conveniog.getFolioConvenio().split("-")[4];
							folioreg = siglasvelatorio+siglaspaquete+fechafol+folAdenda+folConvenio;
							String foltmp = conveniog.getFolioConvenio();
							conveniog.setFolioConvenioPrev(conveniog.getFolioConvenio());
							conveniog.setFolioConvenio(folioreg);
							
							//FECHA CONTRATACIÓN
							conveniog.setFchContConve(fchHoy);
							//OBTENER PAQUETES PF Y PAQUETES PF DETALLE
							Paquetespfs paquete = paquetesService.getPaqueteByIdPaqPF(conveniog.getPaquetespfs().getIdPaqPF());
							conveniog.setPaquetespfs(paquete);
							
							String anioStr = Integer.toString(fchHoy.getYear());
							String fechaVigPre = anioStr+"-12-31";
							DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
							Date fechaPreVig = new SimpleDateFormat("yyyy-MM-dd").parse(fechaVigPre); 
							Pfdetalle paqueteDet = pfdetalleService.getPFxIdAndFchVigPrec(paquete.getIdPaqPF(), fechaPreVig, anioStr);
							conveniog.setPrecioPaq(paqueteDet.getPrecioPaq());
							conveniog.setServiciosPaq(paqueteDet.getServiciosPaq());
							conveniog.setCostoAfiliacion(paqueteDet.getPrecioPaq());
							
							Integer cantidadBenf = 0;
							cantidadBenf = benefService.getCantidadAsegurados(conveniog.getClientes().getRfc(), anioStr);
							//String fechaVigPre = anioStr+"-12-31";
							//BLOQ
							//Long costoTAsegurados = (long) 0;
							//BLOQ
							//Date fechaPreVig = new SimpleDateFormat("yyyy-MM-dd").parse(fechaVigPre);
							//BLOQ
							//costoTAsegurados = pfdetalleService.getCostoTotalAsegurados(conveniog.getPaquetespfs().getIdPaqPF(), fechaPreVig, anioStr, (double)cantidadBenf);
							//BLOQ
							//Double costoretro = new Double(0);
							//costoretro = convenioServiceImpl.obtenerCostoRetro(conveniog.getClientes().getRfc(), conveniog.getPaquetespfs().getIdPaqPF());
							//conveniog.setCostoRetroConvenio(costoretro);
							conveniog.setSubTotalConvenio((new Double(0) + conveniog.getCostoRetroConvenio()));
							conveniog.setTotalConvenio((new Double(0) + conveniog.getCostoRetroConvenio() + conveniog.getCostoAfiliacion()));
							conveniog.setActivo(true);
							
							//CREAR RENCONVENIO
							conveniog = conveniosService.crearConvenioActualizado(conveniog);
							
							//ACTUALIZAR BENEFICIARIOS PRECIO Y FOLIO
							benefService.actualizarFolioCostoBeneficiario(conveniog.getClientes().getRfc(), anioStr, conveniog.getFolioConvenio(), paqueteDet.getPrecioAse());
							model.addAttribute("disableImp", "false");
							model.addAttribute("convenioForm", conveniog);
							model.addAttribute("listaMnpios", cat_mnpiosService.getAllCat_mnpiosxEstado(conveniog.getEstados().getIdRegEstadoS()));
							model.addAttribute("formcTab", "active");
							model.addAttribute("disableFields", "true");
							model.addAttribute("editMode", "true");
						}else {
							//11Feb2020
							model.addAttribute("disableFields", "true");
							model.addAttribute("disableImp", "true");
							//model.addAttribute("editModeF","true");
							//11Feb2020
							
							model.addAttribute("formErrorMessage", "El Afiliado ya cuenta con un renovación de convenio.");
							model.addAttribute("convenioForm", conveniog);
							model.addAttribute("listaMnpios", cat_mnpiosService.getAllCat_mnpiosxEstado(conveniog.getEstados().getIdRegEstadoS()));
							model.addAttribute("formcTab", "active");
						}

						
					}else {
						//11Feb2020
						model.addAttribute("disableFields", "true");
						model.addAttribute("disableImp", "true");
						//11Feb2020
						
						model.addAttribute("formErrorMessage", "El Afiliado no cuenta con un convenio inicial.");
						model.addAttribute("convenioForm", conveniog);
						model.addAttribute("listaMnpios", cat_mnpiosService.getAllCat_mnpiosxEstado(conveniog.getEstados().getIdRegEstadoS()));
						model.addAttribute("formcTab", "active");
					}
					
				}
				//10Feb2020
				
			}catch(Exception e) {
				e.printStackTrace();
				//11Feb2020
				model.addAttribute("disableImp", "true");
				model.addAttribute("readOnly","readonly");
				model.addAttribute("editMode","true");
				model.addAttribute("editModeF","true");
				//11Feb2020
				model.addAttribute("formErrorMessage", e.getMessage());
				model.addAttribute("convenioForm", conveniog);
				model.addAttribute("listaMnpios", cat_mnpiosService.getAllCat_mnpiosxEstado(conveniog.getEstados().getIdRegEstadoS()));
				model.addAttribute("formcTab", "active");
			}
		}
		
		model.addAttribute("clienteForm", new Clientes());
		model.addAttribute("direccionesDto", new DireccionesDto());
		model.addAttribute("cpC", new Cat_cps());
		model.addAttribute("asentamientoC", new Asentamientos());
		model.addAttribute("estadoC", new Cat_estados());
		model.addAttribute("municipioC", new Cat_mnpios());
		model.addAttribute("clienteList", clientesService.getAllClientes());
		model.addAttribute("generos", Generos.values());
		
		BeneficiariosTDto beneficiariosDto = new BeneficiariosTDto();
		for(int i = 1; i <= 3; i++) {
			beneficiariosDto.addBeneficiariosTitular(new Beneficiarios());
		}
		model.addAttribute("beneficiarioForm", beneficiariosDto);
		model.addAttribute("listaParentescos", parentescoService.getAllParentescos());
		model.addAttribute("listaAsegurados", Sino.values());
		
		String usuariolog="";
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			usuariolog = authentication.getName();
		}
		//model.addAttribute("convenioList", conveniosService.getAllConveniosActivosporOperador(usuariolog));
		model.addAttribute("convenioList", conveniosService.getAllConveniosporOperador(usuariolog));
		UsuariosOperador operador = new UsuariosOperador();
		Iterable<Cat_servicios> cat_servicios = null;
		Iterable<Paquetespfs> paquetesI = null;
		try {
			operador = usroperadorService.getUsuarioOperadorById(usuariolog);
			paquetesI = paquetesService.getAllPaquetespfsxVelatorio(operador.getIdVelatorio());
			cat_servicios = cat_servicioService.getAllCat_serviciosActivos();
		}catch(Exception e) {
			e.printStackTrace();
		}
		//model.addAttribute("convenioForm", new Convenios());
		model.addAttribute("listaPaquetes", paquetesI);
		model.addAttribute("listaEstados", cat_estadosService.getAllCat_estados());
		model.addAttribute("litadoServicios", cat_servicios);
		
		VariablesDto variables = new VariablesDto();
		variables.setPaquete(paqueteses);
		variables.setServicio(servicioses);
		model.addAttribute("sesiondto", variables);
		
		//return "redirect:/clienteForm";
		return "user_form/cliente-view";
	}
	
	
	@PostMapping("/beneficiarioCliente")
	public String guardarBeneficiarios(@ModelAttribute BeneficiariosTDto form, ModelMap model, 
			@RequestParam("rfcpassdto") String rfcpass, @SessionAttribute("servicioses") String servicioses, 
			@SessionAttribute("paqueteses") String paqueteses) {
		
		List<Beneficiarios> lbenef = form.getBeneficiariosTitular();
		
		//String idPaqPF_e = "PAQ ECO";
		//String idPaqPF_b = "PAQ BAS";
		//String idPaqPF_c = "PAQ CREM";
		LocalDateTime fechaHoy = LocalDateTime.now();
		Integer annioInt = fechaHoy.getYear();
		String annio = Integer.toString(annioInt);
		String fechaVigPre = annio+"-12-31 00:00:00";
		DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime fechaPreVig = LocalDateTime.parse(fechaVigPre, formateador);
		String mensaje = "";
		Integer contaAct = 0;
		Integer contaAgre = 0;
		Integer contaElimi = 0;
		for(Beneficiarios beneficiario : lbenef) {
			
			if(beneficiario.isRegistrar()) {
				//PREGUNTAR SI EXISTE REGISTRO EN AÑO ACTUAL EL ID SERIAL
				//if(!Objects.isNull(beneficiario.getIdBeneficiario())) {
				try {
					//Pfdetalle paquete = pfdetalleService.getPFxIdAndFchVigPrec(idPaqPF_e, fechaPreVig, annio);
					
					if(!Objects.isNull(beneficiario.getIdBeneficiario())) {
						boolean bexiste = benefService.verificarExisteBeneficiario(beneficiario);
						//SI EXISTE ACTUALIZO
						if(bexiste) {
							//SETEAR DATOS FALTANTES
							beneficiario.setIdParentesco(beneficiario.getParentesco().getIdParentesco());
							beneficiario.setRfcTitular(beneficiario.getClientes().getRfc());
							Parentescos parentescotmp = parentescoService.getParentescoByIdParentesco(beneficiario.getParentesco().getIdParentesco());
							beneficiario.setParentesco(parentescotmp);
							Clientes clientetmp = clientesService.getClienteByRfc(beneficiario.getClientes().getRfc());
							beneficiario.setClientes(clientetmp);
							
							//SÓLO VALIDA QUE SE LLENE AL FINALIZAR LA CONTRATATACIÓN
							//beneficiario.setCostoAsegurado((beneficiario.getAsegurado())?(Objects.isNull(paquete)?0:paquete.getPrecioAse()):null);
							benefService.actualizarBeneficiario(beneficiario);
							contaAct++;
						}
					}else {
						//SI NO EXISTE REGISTRO
						//SETEAR DATOS FALTANTES
						//BLOQ
						beneficiario.setAsegurado(false);
						//BLOQ
						beneficiario.setIdParentesco(beneficiario.getParentesco().getIdParentesco());
						beneficiario.setRfcTitular(beneficiario.getClientes().getRfc());
						Parentescos parentescotmp = parentescoService.getParentescoByIdParentesco(beneficiario.getParentesco().getIdParentesco());
						beneficiario.setParentesco(parentescotmp);
						Clientes clientetmp = clientesService.getClienteByRfc(beneficiario.getClientes().getRfc());
						beneficiario.setClientes(clientetmp);
						//SÓLO VALIDA QUE SE LLENE AL FINALIZAR LA CONTRATATACIÓN
						//beneficiario.setCostoAsegurado((beneficiario.getAsegurado())?(Objects.isNull(paquete)?0:paquete.getPrecioAse()):null);
						
						benefService.crearBeneficiario(beneficiario);
						contaAgre++;
					}
				}catch(Exception e) {
					e.printStackTrace();
					
					//24Ene2020
					model.addAttribute("formErrorMessage", e.getMessage());
					model.addAttribute("beneficiarioForm", form);
					model.addAttribute("listaParentescos", parentescoService.getAllParentescos());
					model.addAttribute("listaAsegurados", Sino.values());
					model.addAttribute("formbTab", "active");
					//24Ene2020
				}
			}else {
				//CASUISTICA PARA CUANDO LOS BENEFICIARIOS YA FUERON REGISTRADOS (EXISTEN PERO YA NO SE DESEAN 
				//REGISTRAR AL MOMENTO DE UNA RENOVACIÓN-UPGRADES)
				try {
					if(!Objects.isNull(beneficiario.getIdBeneficiario())) {
						boolean bexiste = benefService.verificarExisteBeneficiario(beneficiario);
						//SI EXISTE ELIMINO(FISICAMENTE)
						if(bexiste) {
							benefService.eliminarBeneficiarioFisica(beneficiario.getIdBeneficiario());
							contaElimi++;
						}
					}
				}catch(Exception e) {
					e.printStackTrace();
					
					//24Ene2020
					model.addAttribute("formErrorMessage", e.getMessage());
					model.addAttribute("beneficiarioForm", form);
					model.addAttribute("listaParentescos", parentescoService.getAllParentescos());
					model.addAttribute("listaAsegurados", Sino.values());
					model.addAttribute("formbTab", "active");
					//24Ene2020
				}
			}
			
		}
		
		//SETEAR VALORES PARA MOSTRAR FORMULARIOS
		//24Ene2020
		//OBJETOS PARA EL FORMULARIO DE CIENTES Y LISTADO DE CLIENTES
		if(contaAgre>0) {
			mensaje = Integer.toString(contaAgre)+" registro creado"+(contaAgre>2?"s":"");
		}
		if(contaElimi>0) {
			mensaje = (mensaje.length()>0?mensaje+", ":"")+ Integer.toString(contaElimi)+" registro eliminado"+(contaElimi>2?"s":"");
		}
		if(contaAct>0) {
			mensaje = (mensaje.length()>0?mensaje+", ":"")+ Integer.toString(contaAct)+" registros actualizado"+(contaAct>2?"s":"");
		}
		if(mensaje.length()>0) {
			model.addAttribute("formErrorBenefMessage", "Se han realizado los siguientes cambios: " + mensaje);
		}
		
		
		//System.out.println("El valor de rfcpass: " + form.rfc)
		model.addAttribute("rfcpass", rfcpass);
		model.addAttribute("beneficiarioForm", form);
		model.addAttribute("formbTab", "active");
		
		model.addAttribute("clienteForm", new Clientes());
		model.addAttribute("direccionesDto", new DireccionesDto());
		model.addAttribute("cpC", new Cat_cps());
		model.addAttribute("asentamientoC", new Asentamientos());
		model.addAttribute("estadoC", new Cat_estados());
		model.addAttribute("municipioC", new Cat_mnpios());
		model.addAttribute("clienteList", clientesService.getAllClientes());
		model.addAttribute("generos", Generos.values());
		//model.addAttribute("listTab","active");
		//OBJETOS PARA EL FORMULARIO DE CIENTES Y LISTADO DE CLIENTES
		
		//OBJETOS PARA EL FORMULARIO DE CONVENIOS Y LISTADO DE CONVENIOS
		String usuariolog="";
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			usuariolog = authentication.getName();
		}
		//model.addAttribute("convenioList", conveniosService.getAllConveniosActivosporOperador(usuariolog));
		model.addAttribute("convenioList", conveniosService.getAllConveniosporOperador(usuariolog));
		UsuariosOperador operador = new UsuariosOperador();
		Iterable<Cat_servicios> cat_servicios = null;
		//@SuppressWarnings("unchecked")
		//Iterable<Paquetespfs> paquetesI = (Iterable<Paquetespfs>) new Paquetespfs();
		Iterable<Paquetespfs> paquetesI = null;
		try {
			operador = usroperadorService.getUsuarioOperadorById(usuariolog);
			paquetesI = paquetesService.getAllPaquetespfsxVelatorio(operador.getIdVelatorio());
			cat_servicios = cat_servicioService.getAllCat_serviciosActivos();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		model.addAttribute("listaParentescos", parentescoService.getAllParentescos());
		model.addAttribute("listaAsegurados", Sino.values());
		
		
		model.addAttribute("convenioForm", new Convenios());
		//model.addAttribute("listaPaquetes", paquetesService.getAllPaquetespfs());
		model.addAttribute("listaPaquetes", paquetesI);
		model.addAttribute("listaEstados", cat_estadosService.getAllCat_estados());
		model.addAttribute("litadoServicios", cat_servicios);
		VariablesDto variables = new VariablesDto();
		variables.setPaquete(paqueteses);
		variables.setServicio(servicioses);
		model.addAttribute("sesiondto", variables);
		//24Ene2020
		
		//model.addAttribute("clienteForm", new Clientes());
		//model.addAttribute("listTab","active");
		//return "redirect:/clienteForm";
		return "user_form/cliente-view";
	}
	
	@GetMapping("/conveniosCliente/{rfct}")
	public String obtConveniosForm(Model model, @PathVariable(name = "rfct")String rfct,
			@SessionAttribute("servicioses") String servicioses, @SessionAttribute("paqueteses") String paqueteses)throws Exception{
		return "user_form/cliente-view";
		
		//Convenios convenioL =  new Convenios();
	}
	
	@GetMapping("/beneficiarioCliente/{rfct}")
	//public String obtBeneficiariosForm(Model model, @PathVariable(name = "rfct")String rfct)throws Exception{
	public String obtBeneficiariosForm(Model model, @PathVariable(name = "rfct")String rfct, 
			@SessionAttribute("servicioses") String servicioses, @SessionAttribute("paqueteses") String paqueteses)throws Exception{
		
		
		//TODO: Obtener variable de session para tipo de servicio, folio y tipo de paquete.
		LocalDateTime fechaHoy = LocalDateTime.now();
		//Date fechaHoy = new Date();
		Integer SERVICIO_OBTENIDO_SESSION = 1;
		Integer tiposervicio = 0;
		//TODO
		//tiposervicio = SERVICIO_OBTENIDO_SESSION;
		tiposervicio = Integer.parseInt(servicioses);
		Integer annioInt = fechaHoy.getYear();
		String annio = Integer.toString(annioInt);
		String annioPrev = Integer.toString(tiposervicio==1?annioInt:annioInt-1);
		System.out.println("El año actual: " + annio);
		System.out.println("El año previo: " + annioPrev);
		//String annio = Integer.toString(fechaHoy.getYear());
		String folioConvenio = "";
		String tipoServicio = "";
		//String idPaqPF_e = "PAQ ECO";
		//String idPaqPF_b = "PAQ BAS";
		//String idPaqPF_c = "PAQ CREM";
		//String fechaVigPre = annio+"-31-12";
		//String fechaVigPre = annio+"-31-12 00:00:00";
		
		boolean rprev = false;
		
		String fechaVigPre = annio+"-12-31 00:00:00";
		//String fechaVigPre = "2018-12-31 00:00:00";
		
		//String fechaVigPre = "12-31-"+annio;
		BeneficiariosTDto beneficiariosDto = new BeneficiariosTDto();
		DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime fechaPreVig = LocalDateTime.parse(fechaVigPre, formateador);
		Long existeconveniopos = new Long(0);
		try {
			//Paquetespfs paquete = paquetesService.getPFxIdAndFchVigPrec(idPaqPF_e, fechaVigPre);
			//VALIDAR EL PAQUETE DESDE EL INICIO PARA BLOQUEAR TODO EL SISTEMA, DEBIDO A QUE PUEDE CAUSAR
			//INCOSISTENCIAS AL DEJAR PASAR EL FLUJO DEL PROCESO
			//SE COMENTA PORQUE NO ES NECESARIO PARA TENER EL PRECIO EN EL PROCESO
			//Pfdetalle paquete = pfdetalleService.getPFxIdAndFchVigPrec(idPaqPF_e, fechaPreVig, annio);
			//Paquetespfs paquete = paquetesService.getPFxIdAndFchVigPrec(idPaqPF_e, fechaPreVig);
			
			//CASO DE RENOVACIÓN O ACTUALIZACIÓN, PREGUNTAR PRIMERO SI EXISTE REGISTROS EN EL AÑO ACTUAL
			//SI EXITE POR LO MENOS UNO, YA NO RECUPERO LOS PREVIOS
			//SI NO EXISTE NINGUNO RECUPERO LOS PREVIOS
			
			Iterable<Beneficiarios> beneficiarioT = benefService.getBenefXRfcAndAnioAsegurado(rfct, (annio));
			
			//Set<Beneficiarios> beneficiarioS = benefService.getBenefXRfcAndAnioAsegurado(rfct, annio);
			
			//Iterator<Beneficiarios> beneficiarioT = beneficiarioS.iterator();
			//beneficiarioT.
			long numbenef = StreamSupport.stream(beneficiarioT.spliterator(), false).count();
			//int numbenef = Objects.isNull(beneficiarioS)?0:beneficiarioS.size();
			System.out.println("Cantidad recuperada: " + Long.toString(numbenef));
			int existentes = (int)numbenef;
			System.out.println("Cantidad recuperada seteada: " + existentes);
			
			if (tiposervicio > 1 && existentes == 0) {
				existeconveniopos = conveniosService.getConvenioPosteriorExistente(rfct);
				if(existeconveniopos==0) {
					beneficiarioT = benefService.getBenefXRfcAndAnioAsegurado(rfct, (annioPrev));
					numbenef = StreamSupport.stream(beneficiarioT.spliterator(), false).count();
					existentes = (int)numbenef;
					rprev = true;
				}
			}
			//int existentes = numbenef;
			//if (numbenef >= 1) {
			int restantes = 3;
			restantes = restantes - existentes;
			//}
			//CICLO PARA POBLAR LOS EXISTENTES
			Iterator<Beneficiarios> beneficiariosItera = beneficiarioT.iterator();
			//Iterator<Beneficiarios> beneficiariosItera = beneficiarioS.iterator();
			while(beneficiariosItera.hasNext()) {
				Beneficiarios e = (Beneficiarios) beneficiariosItera.next();
				if(tiposervicio == 1) {
					e.setRegistrar(true);
					beneficiariosDto.addBeneficiariosTitular(e);
				}else if(tiposervicio == 2 || tiposervicio == 3) {
					e.setRegistrar(true);
					if(rprev) {
						e.setIdBeneficiario(null);
						e.setFolioConvenio(null);
						//e.setCostoAsegurado((e.getAsegurado())?(Objects.isNull(paquete)?0:paquete.getPrecioAse()):0);
						e.setConvenio(null);
						e.setAnioAsegurado(annio);
						e.setUsrCreReg(null);
						e.setFchCreReg(null);
						e.setUsrModReg(null);
						e.setFchModReg(null);
					}
					beneficiariosDto.addBeneficiariosTitular(e);
					System.out.println("El valor de ID Beneficiario:" + e.getIdBeneficiario());
					System.out.println("El costo asegurado: " + e.getCostoAsegurado());
					System.out.println("El rfcTitular:" + e.getRfcTitular());
					System.out.println("El rfcTitularB:" + e.getClientes().getRfc());
					System.out.println("El año asegurado: " + e.getAnioAsegurado());
				}
			}
			
			//CICLO PARA POBLAR LOS FALTANTES
			for(int i = 1; i <= restantes; i++) {
				Beneficiarios benefComp = new Beneficiarios();
				
				//if(tiposervicio == 1) {
					benefComp.setClientes(new Clientes());
					benefComp.getClientes().setRfc(rfct);
					//benefComp.setRfcTitular(rfct);
					//benefComp.setCostoAsegurado(Objects.isNull(paquete)?0:paquete.getPrecioAse());
					benefComp.setAnioAsegurado(annio);
					benefComp.setActivo(true);
				//} if(tiposervicio == 2) {
				//	benefComp.setRfcTitular(rfct);
				//	benefComp.setCostoAsegurado(Objects.isNull(paquete)?0:paquete.getPrecioAse());
				//	benefComp.setAnioAsegurado(annio);
				//}
				
				beneficiariosDto.addBeneficiariosTitular(benefComp);
			}
		
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		//OBJETOS PARA EL FORMULARIO DE CIENTES Y LISTADO DE CLIENTES
		model.addAttribute("clienteForm", new Clientes());
		model.addAttribute("direccionesDto", new DireccionesDto());
		model.addAttribute("cpC", new Cat_cps());
		model.addAttribute("asentamientoC", new Asentamientos());
		model.addAttribute("estadoC", new Cat_estados());
		model.addAttribute("municipioC", new Cat_mnpios());
		model.addAttribute("clienteList", clientesService.getAllClientes());
		model.addAttribute("generos", Generos.values());
		//model.addAttribute("listTab","active");
		//OBJETOS PARA EL FORMULARIO DE CIENTES Y LISTADO DE CLIENTES
		
		//OBJETOS PARA EL FORMULARIO DE CONVENIOS Y LISTADO DE CONVENIOS
		String usuariolog="";
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			usuariolog = authentication.getName();
		}
		//model.addAttribute("convenioList", conveniosService.getAllConveniosActivosporOperador(usuariolog));
		model.addAttribute("convenioList", conveniosService.getAllConveniosporOperador(usuariolog));
		UsuariosOperador operador = new UsuariosOperador();
		Iterable<Cat_servicios> cat_servicios = null;
		//@SuppressWarnings("unchecked")
		//Iterable<Paquetespfs> paquetesI = (Iterable<Paquetespfs>) new Paquetespfs();
		Iterable<Paquetespfs> paquetesI = null;
		try {
			operador = usroperadorService.getUsuarioOperadorById(usuariolog);
			paquetesI = paquetesService.getAllPaquetespfsxVelatorio(operador.getIdVelatorio());
			cat_servicios = cat_servicioService.getAllCat_serviciosActivos();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("convenioForm", new Convenios());
		//model.addAttribute("listaPaquetes", paquetesService.getAllPaquetespfs());
		model.addAttribute("listaPaquetes", paquetesI);
		model.addAttribute("listaEstados", cat_estadosService.getAllCat_estados());
		model.addAttribute("litadoServicios", cat_servicios);
		VariablesDto variables = new VariablesDto();
		variables.setPaquete(paqueteses);
		variables.setServicio(servicioses);
		model.addAttribute("sesiondto", variables);
		//OBJETOS PARA EL FORMULARIO DE CONVENIOS Y LISTADO DE CONVENIOS
		
		//beneficiariosDto.setRfcpassdto(rfct);
		model.addAttribute("rfcpass", rfct);
		model.addAttribute("beneficiarioForm", beneficiariosDto);
		model.addAttribute("listaParentescos", parentescoService.getAllParentescos());
		model.addAttribute("listaAsegurados", Sino.values());
		
		//2020-FEB-04
		if(servicioses.equals("1")) {
			Long existeconvenio = conveniosService.getConvenioInicialExistente(rfct);
			if(existeconvenio==1) {
				model.addAttribute("formErrorBenefMessage", "No se pueden realizar modificaciones debido a que se ha registrado un convenio.");
				model.addAttribute("disableFields","true");
			}
		} else if(servicioses.equals("2")) {
			//Long existeconveniopos = conveniosService.getConvenioPosteriorExistente(rfct);
			if(existeconveniopos==1) {
				model.addAttribute("formErrorBenefMessage", "No se pueden realizar modificaciones debido a que se ha registrado un convenio.");
				model.addAttribute("disableFields","true");
				//model.addAttribute("disableRenew", "true");
				System.out.println("Entra con existentes");
			} else {
				Convenios convenio = conveniosService.getConvenioPrevioAct(rfct);
				if(!convenio.permitirRenovacion) {
					model.addAttribute("formErrorBenefMessage", "No se pueden realizar modificaciones debido a que no se encuentra en periodo de renovación.");
					model.addAttribute("disableFields","true");
				}
				//PREGUNTAR SI ES TIEMPO DE RENOVACIÓN
				//SI ES TRUE SE PERMITE LA MODIFICACIÓN DEL NOMBRE SOLAMENTE
				//model.addAttribute("disableFields","true");
				//model.addAttribute("readOnly","readonly");
				//th:readonly="${readOnly}"
				//model.addAttribute("disableRenew", "false");
			}
		}
		//2020-FEB-04
		
		model.addAttribute("formbTab", "active");
		return "user_form/cliente-view";
	}
	
	@GetMapping("/modificarConvenio/{rfct}")
	public String obtModificarConvenioForm(Model model, @PathVariable(name = "rfct")String rfct,
			@SessionAttribute("servicioses") String servicioses, @SessionAttribute("paqueteses") String paqueteses)throws Exception {
		if(servicioses.equals("2")) {
			//VERIFICO SI EXISTE YA UN CONVENIO CREADO PARA EL AÑO ACTUAL
			Long cantconvenio = conveniosService.getConvenioPosteriorExistente(rfct);
			if (cantconvenio == 1) {
				model.addAttribute("disableFields","true");
				model.addAttribute("formErrorMessage", "El Afiliado ya cuenta con un convenio para el año actual.");
				model.addAttribute("convenioForm", new Convenios());
			} else if(cantconvenio == 0) {
				Convenios convform = conveniosService.getConvenioPrevio(rfct);
				if (Objects.isNull(convform)) {
					model.addAttribute("disableFields","true");
					model.addAttribute("formErrorMessage", "El Afiliado no cuenta con un convenio previo para renovación.");
					model.addAttribute("convenioForm", new Convenios());
				} else {
					//OBTENER COSTO DE LOS BENEFICIARIOS ACTUALES CON RESPECTO A LOS PREVIOS
					LocalDateTime fechaHoy = LocalDateTime.now();
					Integer annioInt = fechaHoy.getYear();
					String annio = Integer.toString(annioInt);
					Integer cantidadBenf = 0;
					cantidadBenf = benefService.getCantidadAsegurados(rfct, annio);
					String fechaVigPre = annio+"-12-31";
					Long costoTAsegurados = (long) 0;
					Date fechaPreVig = new SimpleDateFormat("yyyy-MM-dd").parse(fechaVigPre);
					costoTAsegurados = pfdetalleService.getCostoTotalAsegurados(convform.getPaquetespfs().getIdPaqPF(), fechaPreVig, annio, (double)cantidadBenf);
					//OBTENER COSTO RETROACTIVO EN EL CASO DE HABER
					Double costoretro = new Double(0);
					//costoretro = convenioServiceImpl.obtenerCostoRetro(rfct, convform.getPaquetespfs().getIdPaqPF());
					
					model.addAttribute("listaMnpios", cat_mnpiosService.getAllCat_mnpiosxEstado(Objects.isNull(convform.getEstados().getIdRegEstadoS())?"0":convform.getEstados().getIdRegEstadoS()));
					convform.setIdReg(null);
					convform.setCostoRetroConvenio(costoretro);
					convform.setSubTotalConvenio((new Double(costoTAsegurados) + costoretro));
					convform.setTotalConvenio((new Double(costoTAsegurados) + costoretro + convform.getCostoAfiliacion()));
					
					//SETEAR DATOS Y FECHA DE PAGO
					convform.setDatosPago("");
					convform.setFchPago(new Date());
					
					model.addAttribute("convenioForm", convform);
					
					model.addAttribute("readOnly","readonly");
					model.addAttribute("editMode","true");
					model.addAttribute("editModeF","true");
				}
			}
		}else {
			//ENVIAR MENSAJE DE QUE DEBE SELECCIONAR EL TIPO DE SERVICIO 2
			model.addAttribute("disableFields","true");
			model.addAttribute("formErrorMessage", "Para realizar renovación de un convenio debe seleccionar el tipo de servicio 'Renovación'");
			model.addAttribute("convenioForm", new Convenios());
			//System.out.println("Enviar mensaje que debe seleccionar el tipo de servicio 2 para realizar una renovación");
		}
		
		//OBJETOS PARA EL FORMULARIO DE CIENTES Y LISTADO DE CLIENTES
		model.addAttribute("clienteForm", new Clientes());
		model.addAttribute("direccionesDto", new DireccionesDto());
		model.addAttribute("cpC", new Cat_cps());
		model.addAttribute("asentamientoC", new Asentamientos());
		model.addAttribute("estadoC", new Cat_estados());
		model.addAttribute("municipioC", new Cat_mnpios());
		model.addAttribute("clienteList", clientesService.getAllClientes());
		model.addAttribute("generos", Generos.values());
		
		String usuariolog="";
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			usuariolog = authentication.getName();
		}
		//model.addAttribute("convenioList", conveniosService.getAllConveniosActivosporOperador(usuariolog));
		model.addAttribute("convenioList", conveniosService.getAllConveniosporOperador(usuariolog));
		UsuariosOperador operador = new UsuariosOperador();
		Iterable<Cat_servicios> cat_servicios = null;
		
		Iterable<Paquetespfs> paquetesI = null;
		try {
			operador = usroperadorService.getUsuarioOperadorById(usuariolog);
			paquetesI = paquetesService.getAllPaquetespfsxVelatorio(operador.getIdVelatorio());
			cat_servicios = cat_servicioService.getAllCat_serviciosActivos();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//model.addAttribute("convenioForm", new Convenios());
		model.addAttribute("listaPaquetes", paquetesI);
		model.addAttribute("listaEstados", cat_estadosService.getAllCat_estados());
		model.addAttribute("litadoServicios", cat_servicios);
		VariablesDto variables = new VariablesDto();
		variables.setPaquete(paqueteses);
		variables.setServicio(servicioses);
		model.addAttribute("sesiondto", variables);
		//OBJETOS PARA EL FORMULARIO DE BENEFICIARIOS Y LISTADO DE CONVENIOS
		
		BeneficiariosTDto beneficiariosDto = new BeneficiariosTDto();
		for(int i = 1; i <= 3; i++) {
			beneficiariosDto.addBeneficiariosTitular(new Beneficiarios());
		}
		model.addAttribute("beneficiarioForm", beneficiariosDto);
		//model.addAttribute("beneficiarioForm", beneficiariosDto);
		model.addAttribute("listaParentescos", parentescoService.getAllParentescos());
		model.addAttribute("listaAsegurados", Sino.values());
		//OBJETOS PARA EL FORMULARIO DE BENEFICIARIOS Y LISTADO DE CONVENIOS
		
		model.addAttribute("disableImp", "true");
		model.addAttribute("formcTab", "active");
		
		return "user_form/cliente-view";
	}
	
	@GetMapping("/modificarCliente/{id}")
	public String obtModificarClienteForm(Model model, @PathVariable(name = "id")Integer id, @SessionAttribute("servicioses") String servicioses, 
			@SessionAttribute("paqueteses") String paqueteses)throws Exception{
		Clientes clienteEditar = clientesService.getClienteById(id);
		model.addAttribute("clienteForm", clienteEditar);
		String cp = Long.toString((Objects.isNull(clienteEditar.getCat_cps()))?0:clienteEditar.getCat_cps().getCp());
		Set<DireccionesDto> direccionesDtoL = new HashSet<>();
		direccionesDtoL = llenardireccionxcp(cp);
		model.addAttribute("direccionesDto", direccionesDtoL);
		model.addAttribute("municipioC", Objects.isNull(clienteEditar.getMunicipio())?new Cat_mnpios():clienteEditar.getMunicipio());
		model.addAttribute("estadoC", Objects.isNull(clienteEditar.getEstado())?new Cat_estados():clienteEditar.getEstado());
		model.addAttribute("cpC", Objects.isNull(clienteEditar.getCat_cps())?new Cat_cps():clienteEditar.getCat_cps());
		model.addAttribute("asentamientoC", Objects.isNull(clienteEditar.getAsentamientos())?new Asentamientos():clienteEditar.getAsentamientos());
		model.addAttribute("generos", Generos.values());
		
		//22Ene2020
		model.addAttribute("clienteList", clientesService.getAllClientes());
		BeneficiariosTDto beneficiariosDto = new BeneficiariosTDto();
		for(int i = 1; i <= 3; i++) {
			beneficiariosDto.addBeneficiariosTitular(new Beneficiarios());
		}
		model.addAttribute("beneficiarioForm", beneficiariosDto);
		model.addAttribute("listaParentescos", parentescoService.getAllParentescos());
		model.addAttribute("listaAsegurados", Sino.values());
		
		String usuariolog="";
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			usuariolog = authentication.getName();
		}
		//model.addAttribute("convenioList", conveniosService.getAllConveniosActivosporOperador(usuariolog));
		model.addAttribute("convenioList", conveniosService.getAllConveniosporOperador(usuariolog));
		UsuariosOperador operador = new UsuariosOperador();
		Iterable<Cat_servicios> cat_servicios = null;
		Iterable<Paquetespfs> paquetesI = null;
		try {
			operador = usroperadorService.getUsuarioOperadorById(usuariolog);
			paquetesI = paquetesService.getAllPaquetespfsxVelatorio(operador.getIdVelatorio());
			cat_servicios = cat_servicioService.getAllCat_serviciosActivos();
		}catch(Exception e) {
			e.printStackTrace();
		}
		//model.addAttribute("convenioForm", new Convenios());
		model.addAttribute("listaPaquetes", paquetesI);
		model.addAttribute("listaEstados", cat_estadosService.getAllCat_estados());
		model.addAttribute("litadoServicios", cat_servicios);
		
		
		VariablesDto variables = new VariablesDto();
		variables.setPaquete(paqueteses);
		variables.setServicio(servicioses);
		model.addAttribute("sesiondto", variables);
		//22Ene2020
		
		model.addAttribute("formTab","active");
		model.addAttribute("editMode","true");
		
		//2020-FEB-04
		model.addAttribute("convenioForm", new Convenios());
		if(servicioses.equals("1")) {
			Long existeconvenio = conveniosService.getConvenioInicialExistente(clienteEditar.getRfc());
			if(existeconvenio==1) {
				model.addAttribute("formErrorBenefMessage", "No se pueden realizar modificaciones debido a que se ha registrado un convenio.");
				model.addAttribute("disableFields","true");
				model.addAttribute("disableRenew", "true");
			}
		}else if(servicioses.equals("2")) {
			Long existeconveniopos = conveniosService.getConvenioPosteriorExistente(clienteEditar.getRfc());
			if(existeconveniopos==1) {
				model.addAttribute("formErrorBenefMessage", "No se pueden realizar modificaciones debido a que se ha registrado un convenio.");
				model.addAttribute("disableFields","true");
				model.addAttribute("disableRenew", "true");
			} else {
				Convenios convenio = conveniosService.getConvenioPrevioAct(clienteEditar.getRfc());
				if(!convenio.permitirRenovacion) {
					model.addAttribute("formErrorBenefMessage", "No se pueden realizar modificaciones debido a que no se encuentra en periodo de renovación.");
					model.addAttribute("disableFields","true");
				} else {
				//PREGUNTAR SI ES TIEMPO DE RENOVACIÓN
				//SI ES TRUE SE PERMITE LA MODIFICACIÓN DEL NOMBRE SOLAMENTE
				//model.addAttribute("disableFields","true");
					model.addAttribute("readOnly","readonly");
					model.addAttribute("disableRenew", "false");
				}
				System.out.println("Entra sin existentes");
			}
		}
		//2020-FEB-04
		
		return "user_form/cliente-view";
	}
	
	
	@GetMapping("/nuevoConvenio/{rfc}")
	public String nuevoConvenio(Model model, @PathVariable(name = "rfc")String rfc, @SessionAttribute("servicioses") String servicioses, 
			@SessionAttribute("paqueteses") String paqueteses)throws Exception{
		Convenios convform =  new Convenios();
		convform.setRfcTitular(rfc);
		convform.setClientes(new Clientes());
		convform.getClientes().setRfc(rfc);
		model.addAttribute("convenioForm", convform);
		
		//OBJETOS PARA EL FORMULARIO DE CIENTES Y LISTADO DE CLIENTES
		model.addAttribute("clienteForm", new Clientes());
		model.addAttribute("direccionesDto", new DireccionesDto());
		model.addAttribute("cpC", new Cat_cps());
		model.addAttribute("asentamientoC", new Asentamientos());
		model.addAttribute("estadoC", new Cat_estados());
		model.addAttribute("municipioC", new Cat_mnpios());
		model.addAttribute("clienteList", clientesService.getAllClientes());
		model.addAttribute("generos", Generos.values());
		
		String usuariolog="";
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			usuariolog = authentication.getName();
		}
		//model.addAttribute("convenioList", conveniosService.getAllConveniosActivosporOperador(usuariolog));
		model.addAttribute("convenioList", conveniosService.getAllConveniosporOperador(usuariolog));
		UsuariosOperador operador = new UsuariosOperador();
		Iterable<Cat_servicios> cat_servicios = null;
		
		Iterable<Paquetespfs> paquetesI = null;
		try {
			operador = usroperadorService.getUsuarioOperadorById(usuariolog);
			paquetesI = paquetesService.getAllPaquetespfsxVelatorio(operador.getIdVelatorio());
			cat_servicios = cat_servicioService.getAllCat_serviciosActivos();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//model.addAttribute("convenioForm", new Convenios());
		model.addAttribute("listaPaquetes", paquetesI);
		model.addAttribute("listaEstados", cat_estadosService.getAllCat_estados());
		model.addAttribute("litadoServicios", cat_servicios);
		VariablesDto variables = new VariablesDto();
		variables.setPaquete(paqueteses);
		variables.setServicio(servicioses);
		model.addAttribute("sesiondto", variables);
		//OBJETOS PARA EL FORMULARIO DE BENEFICIARIOS Y LISTADO DE CONVENIOS
		
		BeneficiariosTDto beneficiariosDto = new BeneficiariosTDto();
		for(int i = 1; i <= 3; i++) {
			beneficiariosDto.addBeneficiariosTitular(new Beneficiarios());
		}
		model.addAttribute("beneficiarioForm", beneficiariosDto);
		//model.addAttribute("beneficiarioForm", beneficiariosDto);
		model.addAttribute("listaParentescos", parentescoService.getAllParentescos());
		model.addAttribute("listaAsegurados", Sino.values());
		
		
		//2020-FEB-04
		if(servicioses.equals("1")) {
			Long existeconvenio = conveniosService.getConvenioInicialExistente(rfc);
			if(existeconvenio==1) {
				model.addAttribute("disableFields","true");
				model.addAttribute("formErrorMessage", "Ya existe un convenio inicial.");
			} else {
				existeconvenio = conveniosService.getConvenioPosteriorExistente(rfc);
				if(existeconvenio==1) {
					model.addAttribute("disableFields","true");
					model.addAttribute("formErrorMessage", "Existe un convenio posterior al inicial, revisar integridad de datos.");
				}
			}
		} else if(servicioses.equals("2")) {
			Long existeconveniopos = conveniosService.getConvenioPosteriorExistente(rfc);
			if(existeconveniopos==1) {
				model.addAttribute("disableFields","true");
				model.addAttribute("formErrorMessage", "Ya existe una renovación del convenio.");
				//model.addAttribute("disableRenew", "true");
				System.out.println("Entra con existentes");
			} else {
				
				model.addAttribute("disableFields","true");
				model.addAttribute("formErrorMessage", "Para renovación del convenio ir a listado de convenios y verificar si esta permitido la renovación.");
				//PREGUNTAR SI ES TIEMPO DE RENOVACIÓN
				//SI ES TRUE SE PERMITE LA MODIFICACIÓN DEL NOMBRE SOLAMENTE
				//model.addAttribute("disableFields","true");
				//model.addAttribute("readOnly","readonly");
				//th:readonly="${readOnly}"
				//model.addAttribute("disableRenew", "false");
				System.out.println("Entra sin existentes");
			}
		}
		//2020-FEB-04
		
		model.addAttribute("disableImp", "true");
		model.addAttribute("formcTab", "active");
		//return "user_form/cliente-view";
		
		return "user_form/cliente-view";
	}
	
	@GetMapping("/eliminarCliente/{id}")
	//public String deleteUser(Model model, @PathVariable(name="id")Integer id) {
	public String deleteUser(Model model, @PathVariable(name="id")Integer id, @SessionAttribute("servicioses") String servicioses, @SessionAttribute("paqueteses") String paqueteses) {
		try {
			//userService.deleteUser(id);
			int resp = -1;
			resp = clientesService.eliminarClienteLogica(id);
		} 
		catch (ClienteIdNoExiste in) {
			model.addAttribute("listErrorMessage",in.getMessage());
		}

		return userForm(model, servicioses, paqueteses);
	}
	
	@GetMapping("/clienteForm/cancelar")
	public String cancelarEditarUsuario(ModelMap model) {
		return "redirect:/clienteForm";
	}
	
	@RequestMapping("/obtCostoRetroactivo")
	public @ResponseBody ResponseEntity<?> obtCostoRetroactivoAjax(@RequestParam("rfctitular") String rfctitular,
			@RequestParam("cvpaq") String paquetepf, HttpSession session, Model model){
		
		Double costoretro = new Double(0);
		AjaxResponseBody resp = new AjaxResponseBody();
		try {
			costoretro = convenioServiceImpl.obtenerCostoRetro(rfctitular, paquetepf);
			if(Objects.isNull(costoretro)) {
				costoretro = new Double(0);
				resp.setMensaje("ERROR");
				resp.setValor("0");
			} else {
				resp.setMensaje("EXITO");
				resp.setValor(Double.toString(costoretro));
			}
		}catch(Exception e) {
			e.printStackTrace();
			resp.setMensaje("ERROR");
			resp.setValor("0");
		}
		return ResponseEntity.ok(resp);
	}
	
	@RequestMapping("/obtCostoBeneficiario")
	public @ResponseBody ResponseEntity<?> obtCostoBeneficiariosAjax(@RequestParam("rfctitular") String rfctitular,
			@RequestParam("cvpaq") String paquetepf,
			HttpSession session, Model model){

		LocalDateTime fechaHoy = LocalDateTime.now();
		Integer annioInt = fechaHoy.getYear();
		String annio = Integer.toString(annioInt);
		//String fechaVigPre = annio+"-12-31 00:00:00";
		String fechaVigPre = annio+"-12-31";
		DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		//LocalDateTime fechaPreVig = LocalDateTime.parse(fechaVigPre, formateador);
		
		//Clientes cliente = null;
		Long costo = (long) 0;
		Integer cantidadBenf = 0;
		Long costoTAsegurados = (long) 0;
		cantidadBenf = benefService.getCantidadAsegurados(rfctitular, annio);
		AjaxResponseBody resp = new AjaxResponseBody();
		if (cantidadBenf>0) {
			try {
				Date fechaPreVig = new SimpleDateFormat("yyyy-MM-dd").parse(fechaVigPre);
			costoTAsegurados = pfdetalleService.getCostoTotalAsegurados(paquetepf, fechaPreVig, annio, (double)cantidadBenf);
			if(Objects.isNull(costoTAsegurados))
				costoTAsegurados= (long)0;
			resp.setMensaje("EXITO");
			resp.setValor(Long.toString(costoTAsegurados));
			}catch(Exception e) {
				e.printStackTrace();
				resp.setMensaje("ERROR");
				resp.setValor("0");
			}
		} else {
			resp.setMensaje("EXITO");
			resp.setValor("0");
		}
		return ResponseEntity.ok(resp);
	}
	
	@RequestMapping("/obtbeneficiarioscosto")
	public @ResponseBody ResponseEntity<?> obtenerCostoBeneficiariosAjax(@RequestParam("rfctitular") String rfctitular, 
			HttpSession session, Model model){
		
		
		LocalDateTime fechaHoy = LocalDateTime.now();
		Integer annioInt = fechaHoy.getYear();
		String annio = Integer.toString(annioInt);
		Clientes cliente = null;
		Long costo = (long) 0;
		AjaxResponseBody resp = new AjaxResponseBody();
		try {
			cliente = clientesService.getClienteByRfc(rfctitular);
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(!Objects.isNull(cliente)) {
			costo = benefService.getCostoAsegurados(rfctitular, annio);
		//Json resp = new Json({Name=costo, Description=costo});
		//return new Respons;
		//Map<String, String> map = new HashMap<String,String>();
		//map.put("costo", Long.toString(costo));
		//map.put("men", "test1");
			resp.setMensaje("ClienteExiste");
			resp.setValor(Long.toString(costo));
		}else {
			resp.setMensaje("ClienteNoExiste");
			resp.setValor("0");
		}
		return ResponseEntity.ok(resp);
		
		//return null;
	}
	
	@ModelAttribute("dir")
	public Set<DireccionesDto> llenardireccionxcp(String cp) {
		String lcp = "";
		if (cp == null) { 
			lcp = "0"; 
		} else if (cp=="") {
			lcp = "0"; 
		} else 
			lcp = cp;
		Set<Direcciones> direcciones = direccionesrepo.findAllByCp(Long.parseLong(lcp));
		Set<DireccionesDto> listadirecciones = new HashSet<>();
		Iterator<Direcciones> direccion = direcciones.iterator();
		
		while(direccion.hasNext()) {
			Direcciones d = (Direcciones) direccion.next();
			DireccionesDto dtmp = new DireccionesDto();
			System.out.println("CP: " + d.getCp());
			dtmp.setCp(d.getCp());
			
			Cat_asentamientos catasent = d.getAsentamientos().getCat_asentamientos();
			dtmp.setIdRegTipoS(catasent.getIdRegTipoS());
			dtmp.setDescAsentamiento(catasent.getDescAsentamiento());
			
			Asentamientos a = d.getAsentamientos();
			dtmp.setIdRegAsentaS(a.getIdRegAsentaS());
			dtmp.setDescAsentaS(a.getDescAsentaS() + "/" + catasent.getDescAsentamiento());
			
			
			Cat_estados catestados = d.getCat_estados();
			dtmp.setIdRegEstadoS(catestados.getIdRegEstadoS());
			dtmp.setDescEstado(catestados.getDescEstado());
			Cat_mnpios catmnpio = d.getCat_mnpios();
			dtmp.setIdRegMnpioS(catmnpio.getIdRegMnpioS());
			dtmp.setDescMnpio(catmnpio.getDescMnpio());
			listadirecciones.add(dtmp);
		}
		
		return listadirecciones;
	}
	
}
