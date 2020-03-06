package com.fibeso.captacion.pfuneraria.service;

import java.util.Optional;

import com.fibeso.captacion.pfuneraria.entity.Convenios;

public interface ConveniosService {
	
	public Iterable<Convenios> getAllConvenios();
	
	public Iterable<Convenios> getAllConveniosActivosporOperador(String idOperador);
	
	//Fechas
	public Iterable<Convenios> getAllConveniosporOperador(String idOperador);
	
	public Iterable<Convenios> getAllConveniosxOperador(String idOperador);
	
	public Convenios getConvenioById(Integer idReg) throws Exception;
	
	public Convenios getConvenioByRfc(String rfcT) throws Exception;
	
	public Convenios getConvenioPrevio(String rfcT) throws Exception;
	
	public Convenios getConvenioPrevioAct(String rfcT) throws Exception;
	
	public Long getCantConveniosByRfc(String rfcT);
	
	public Long getConvenioInicialExistente(String rfcT);
	
	public Long getConvenioPosteriorExistente(String rfcT);
	
	public Convenios crearConvenio(Convenios convenio) throws Exception;
	
	public Convenios crearConvenioActualizado(Convenios convenio) throws Exception;
	//public String getFolioConvenio();

}
