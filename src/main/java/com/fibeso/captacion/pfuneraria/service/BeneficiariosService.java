package com.fibeso.captacion.pfuneraria.service;

import java.util.Set;

import com.fibeso.captacion.pfuneraria.entity.Beneficiarios;
import com.fibeso.captacion.pfuneraria.exception.BeneficiarioNoExiste;

public interface BeneficiariosService {
	
	public Iterable<Beneficiarios> getAllBeneficiarios();
	
	public Iterable<Beneficiarios> getBenefXRfcAndAnioAsegurado(String rfcTitular, String anioAsegurado) throws Exception;
	//public Set<Beneficiarios> getBenefXRfcAndAnioAsegurado(String rfcTitular, String anioAsegurado) throws Exception;

	public Beneficiarios getBeneficiarioById(Integer id) throws Exception;
	
	public Beneficiarios crearBeneficiario(Beneficiarios benef) throws Exception;
	
	public Beneficiarios actualizarBeneficiario(Beneficiarios benef) throws Exception;
	
	public void eliminarBeneficiarioFisica(Integer id) throws BeneficiarioNoExiste;
	
	public Long getCostoAsegurados(String rfcT, String anioAseg);
	
	public Integer getCantidadAsegurados(String rfcT, String anioAseg); 
	
	public int actualizarFolioCostoBeneficiario(String rfcT, String anioAseg, String folioConv, double costoAseg) throws Exception;
	
	//VALIDACIONES
	public boolean verificarExisteBeneficiario(Beneficiarios benef) throws Exception;
	
}
