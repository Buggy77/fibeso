/**
 * 
 */
package com.fibeso.captacion.pfuneraria.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;


import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fibeso.captacion.pfuneraria.service.ImprimeConveniosService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
public class ImprimeConveniosController {

	@Autowired
	private ImprimeConveniosService imprimeConveniosService;

	@RequestMapping(value = { "/imprimeConvenios", "" }, method = RequestMethod.GET)
	 public ModelAndView home() {
		  ModelAndView model = new ModelAndView();

		  model.setViewName("home");
		  return model;
		 }

	
	 @RequestMapping(value = "/imprimeConvenios/export/{folioconv}", method = RequestMethod.GET)
	 //public void export(@PathVariable(name = "folioconv")String folioConv, ModelAndView model, HttpServletResponse response) throws IOException, JRException, SQLException {
	 public void export(@PathVariable(name = "folioconv")String folioConv, ModelMap model, HttpServletResponse response) throws IOException, JRException, SQLException {
	  JasperPrint jasperPrint = null;
		//TODO 	setter de objeto de convenio
	  try {
		  response.setContentType("application/x-download");
		  response.setHeader("Content-Disposition", String.format("attachment; filename=\"Convenio.pdf\""));
	
		  OutputStream out = response.getOutputStream();
		  jasperPrint = imprimeConveniosService.exportPdfFile(folioConv);
		  JasperExportManager.exportReportToPdfStream(jasperPrint, out);
	  }catch(Exception e) {
		  e.printStackTrace();
		  model.addAttribute("formErrorMessage", e.getMessage());
	  }
	  //model.addAttribute("disableFields", "false");
	  //return "redirect:/clienteForm";
	 }
	 
	 @RequestMapping(value = "/imprimeConvenios/exportadenda/{rfcconv}", method = RequestMethod.GET)
	 //public void export(@PathVariable(name = "folioconv")String folioConv, ModelAndView model, HttpServletResponse response) throws IOException, JRException, SQLException {
	 public void exportadenda(@PathVariable(name = "rfcconv")String rfcConv, ModelMap model, HttpServletResponse response) throws IOException, JRException, SQLException {
	  JasperPrint jasperPrint = null;
	  try {
		  response.setContentType("application/x-download");
		  response.setHeader("Content-Disposition", String.format("attachment; filename=\"Adenda.pdf\""));
	
		  OutputStream out = response.getOutputStream();
		  jasperPrint = imprimeConveniosService.exportPdfFileAdenda(rfcConv);
		  JasperExportManager.exportReportToPdfStream(jasperPrint, out);
	  }catch(Exception e) {
		  e.printStackTrace();
		  model.addAttribute("formErrorMessage", e.getMessage());
	  }

	 }
	 

}