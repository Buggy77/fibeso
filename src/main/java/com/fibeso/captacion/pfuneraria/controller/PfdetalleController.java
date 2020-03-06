package com.fibeso.captacion.pfuneraria.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fibeso.captacion.pfuneraria.entity.Pfdetalle;
import com.fibeso.captacion.pfuneraria.service.PfdetalleService;

@Controller
public class PfdetalleController {

	@Autowired
	PfdetalleService pfdetalleService;
	
	@RequestMapping("/obtinfopaquete")
	public @ResponseBody ResponseEntity<?> obtinfopaquete(@RequestParam("cvpaq") String cvpaq, 
			HttpSession session, Model model){
		
		LocalDateTime fechaHoy = LocalDateTime.now();
		LocalDateTime fchHoy = LocalDateTime.now();
		Integer annioInt = fechaHoy.getYear();
		String annio = Integer.toString(annioInt);
		//String fechaVigPre = annio+"-12-31 00:00:00";
		String fechaVigPre = annio+"-12-31";
		//DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		//LocalDateTime fechaPreVig = LocalDateTime.parse(fechaVigPre, formateador);
		//LocalDate fechaPreVig = LocalDate.parse(fechaVigPre, formateador);
		Pfdetalle pfdetalle = null;
		try {
			Date fechaPreVig =  new SimpleDateFormat("yyyy-MM-dd").parse(fechaVigPre);
			pfdetalle = pfdetalleService.getPFxIdAndFchVigPrec(cvpaq, fechaPreVig, annio);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(pfdetalle);
	}
}
