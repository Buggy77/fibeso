package com.fibeso.captacion.pfuneraria.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.exolab.castor.types.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fibeso.captacion.pfuneraria.entity.Calendarioasueto;
import com.fibeso.captacion.pfuneraria.entity.Convenios;
import com.fibeso.captacion.pfuneraria.exception.ConveniosNoExiste;
import com.fibeso.captacion.pfuneraria.repository.CalendarioasuetoRepository;
import com.fibeso.captacion.pfuneraria.repository.ConveniosRepository;

@Service
public class ConveniosServiceImpl implements ConveniosService {

	@Autowired
	CalendarioasuetoRepository calendarioasuetorepo;
	
	@Autowired
	ConveniosRepository conveniosrepo;
	
	@PersistenceContext 
	EntityManager em;
	//public String QUERY_FOLIO = "SELECT folioconv()";
	public String QUERY_FOLIO = "SELECT CASE WHEN (folioconv() is null or folioconv() = '') THEN '00001' ELSE folioconv() END";
	public String QUERY_FOLIO_ADEN = "SELECT folioadenda(?1)";
	public String QUERY_RETROACTIVO = "SELECT fu_calc_retroactivo(?1, ?2)";
	
	public String obtenerFolio() {
		return (String) em.createNativeQuery(QUERY_FOLIO).getSingleResult();
		//return (String) em.createQuery(QUERY_FOLIO).getSingleResult();
	}
	
	public String obtenerFolioAden(String rfcT) {
		Query query = em.createNativeQuery(QUERY_FOLIO_ADEN);
		query.setParameter(1, rfcT);
		
		return (String) query.getSingleResult();
	}
	
	public Double obtenerCostoRetro(String rfcT, String cvePF) {
		Query query = em.createNativeQuery(QUERY_RETROACTIVO);
		query.setParameter(1, rfcT);
		query.setParameter(2, cvePF);
		return (Double) query.getSingleResult();
	}
	
	@Override
	public Iterable<Convenios> getAllConvenios() {
		return conveniosrepo.findAll();
	}

	@Override
	public Iterable<Convenios> getAllConveniosActivosporOperador(String idOperador) {
		return conveniosrepo.findAllByUsuariosOperadorIdOperadorAndActivo(idOperador, true);
		//return conveniosrepo.findAllByUsuariosOperadorIdOperadorAndActivoOrderByIdRegDesc(idOperador, true);
		
		//return conveniosrepo.findAllByUsuariosOperadorIdOperadorAndActivo(idOperador, true, Sort.by("IdReg").descending());
	}
	
	@Override
	public Iterable<Convenios> getAllConveniosporOperador(String idOperador) {
		Iterable<Convenios> conveniosI = conveniosrepo.findAllByUsuariosOperadorIdOperadorAndActivo(idOperador, true);
		Iterable<Calendarioasueto> calendarioasuetoI = calendarioasuetorepo.findAllByAnioCalAndActivo(Integer.toString(LocalDateTime.now().getYear()), true);
		Iterator<Convenios> conveniosIT = conveniosI.iterator();
		Iterator<Calendarioasueto> calendarioIT = calendarioasuetoI.iterator();
		
		//Set<Convenios> conveniosList = new HashSet<Convenios>();
		List<Convenios> conveniosListB = new ArrayList<Convenios>();
		Set<Convenios> conveniosList = new HashSet<>();
		//conveniosList.
		//conveniosIF.
		try {
			while(conveniosIT.hasNext()) {
				Convenios convenio = (Convenios) conveniosIT.next();
				//11Feb2020
				if (!convenio.getFchContConve().toString().split("-", 2)[0].equals(Integer.toString(LocalDateTime.now().getYear())) && 
						!convenio.getFchCreReg().toString().split("-", 2)[0].equals(Integer.toString(LocalDateTime.now().getYear())) &&
						convenio.getActivo()) {
				//11Feb2020
					if (convenio.esPeriodoRenovacion && convenio.dentroLimite) {
						if (LocalDateTime.now().getDayOfMonth()<=20) {
							convenio.setPermitirRenovacion(true);
						} else if (LocalDateTime.now().getDayOfMonth()>20 && LocalDateTime.now().getDayOfMonth()<24) {
							if(convenio.getFechaVerificar().equals(LocalDateTime.now())) {
								convenio.setPermitirRenovacion(true);
							} else {
								convenio.setPermitirRenovacion(false);
								while(calendarioIT.hasNext()) {
									Calendarioasueto calendarioasueto = (Calendarioasueto) calendarioIT.next();
									DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
									String fechaConvVerificarStr = Integer.toString(convenio.getFechaVerificar().getYear())
											+"-"+(Integer.toString(convenio.getFechaVerificar().getMonthValue()).length()<2?"0"+Integer.toString(convenio.getFechaVerificar().getMonthValue()):Integer.toString(convenio.getFechaVerificar().getMonthValue()))
											+"-"+(Integer.toString(convenio.getFechaVerificar().getDayOfMonth()).length()<2?"0"+Integer.toString(convenio.getFechaVerificar().getDayOfMonth()):Integer.toString(convenio.getFechaVerificar().getDayOfMonth()));
									LocalDate fechaConvVerificar = LocalDate.parse(fechaConvVerificarStr, formateador);
									//String fechaAsuetoStr = Integer.toString(calendarioasueto.getFechaAsueto(). getYear())
									//		+"-"+(Integer.toString(convenio.getFechaVerificar().getMonthValue()).length()<2?"0"+Integer.toString(convenio.getFechaVerificar().getMonthValue()):Integer.toString(convenio.getFechaVerificar().getMonthValue()))
									//		+"-"+(Integer.toString(convenio.getFechaVerificar().getDayOfMonth()).length()<2?"0"+Integer.toString(convenio.getFechaVerificar().getDayOfMonth()):Integer.toString(convenio.getFechaVerificar().getDayOfMonth()));
									//Date fechaConvVerificarB = Date.parse(fechaConvVerificarStr);
									
									//if(convenio.getFechaVerificar().equals(calendarioasueto.getFechaAsueto().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())) {
									//if(fechaConvVerificar.equals(calendarioasueto.getFechaAsueto().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
									if(fechaConvVerificar.equals(calendarioasueto.getFechaAsueto())) {
										convenio.setPermitirRenovacion(true);
										System.out.println("Coincide con fecha de asueto, la fecha límite sería hoy 23");
										System.out.println("Fecha de asueto es:" + calendarioasueto.getFechaAsueto().toString());
										System.out.println("El día que se conmemora: " + calendarioasueto.getDescCelebracion());
										break;
									}
								}
							}
						} else {
							convenio.setPermitirRenovacion(false);
						}
					}
				//11Feb2020
				} else {
					convenio.setPermitirRenovacion(false);
				}
				//11Feb2020
				
//				conveniosList.add(convenio);
				conveniosListB.add(convenio);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		//Iterable<Convenios> conveniosIFS = new ArrayList<Convenios>();
		//Iterable<Convenios> conveniosIF = conveniosList;
		Iterable<Convenios> conveniosIF = conveniosListB;
		return conveniosIF;
	}
	
	@Override
	public Iterable<Convenios> getAllConveniosxOperador(String idOperador) {
		return conveniosrepo.getAllConveniosxOperador(idOperador);
	}

	@Override
	public Convenios getConvenioById(Integer idReg) throws ConveniosNoExiste {
		return conveniosrepo.findById(idReg).orElseThrow(() -> new ConveniosNoExiste("Id de Convenio no existe."));
	}

	@Override
	public Convenios getConvenioByRfc(String rfcT) throws Exception {
		return conveniosrepo.findByClientesRfc(rfcT).orElseThrow(() -> new ConveniosNoExiste("RFC de Convenio no existe."));
	}

	@Override
	public Convenios getConvenioPrevio(String rfcT) throws ConveniosNoExiste {
		return conveniosrepo.getConvenioPrevio(rfcT).orElseThrow(() -> new ConveniosNoExiste("No existe convenio previo para realizar renovación, revisar información."));
	}
	
	@Override
	public Convenios getConvenioPrevioAct(String rfcT) throws Exception {
		Convenios convenio = conveniosrepo.getConvenioPrevioAct(rfcT).orElseThrow(() -> new ConveniosNoExiste("No existe convenio previo para realizar renovación, revisar información."));
		Iterable<Calendarioasueto> calendarioasuetoI = calendarioasuetorepo.findAllByAnioCalAndActivo(Integer.toString(LocalDateTime.now().getYear()), true);
		Iterator<Calendarioasueto> calendarioIT = calendarioasuetoI.iterator();
		try {
			if (convenio.esPeriodoRenovacion && convenio.dentroLimite) {
				if (LocalDateTime.now().getDayOfMonth()<=20) {
					convenio.setPermitirRenovacion(true);
				} else if (LocalDateTime.now().getDayOfMonth()>20 && LocalDateTime.now().getDayOfMonth()<24) {
					if(convenio.getFechaVerificar().equals(LocalDateTime.now())) {
						convenio.setPermitirRenovacion(true);
					} else {
						convenio.setPermitirRenovacion(false);
						while(calendarioIT.hasNext()) {
							Calendarioasueto calendarioasueto = (Calendarioasueto) calendarioIT.next();
							DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
							String fechaConvVerificarStr = Integer.toString(convenio.getFechaVerificar().getYear())
									+"-"+(Integer.toString(convenio.getFechaVerificar().getMonthValue()).length()<2?"0"+Integer.toString(convenio.getFechaVerificar().getMonthValue()):Integer.toString(convenio.getFechaVerificar().getMonthValue()))
									+"-"+(Integer.toString(convenio.getFechaVerificar().getDayOfMonth()).length()<2?"0"+Integer.toString(convenio.getFechaVerificar().getDayOfMonth()):Integer.toString(convenio.getFechaVerificar().getDayOfMonth()));
							LocalDate fechaConvVerificar = LocalDate.parse(fechaConvVerificarStr, formateador);
							
							if(fechaConvVerificar.equals(calendarioasueto.getFechaAsueto())) {
								convenio.setPermitirRenovacion(true);
								break;
							}
						}
					}
				} else {
					convenio.setPermitirRenovacion(false);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return convenio;
	}
	
	@Override
	public Long getCantConveniosByRfc(String rfcT) {
		return conveniosrepo.getCantidadConvenios(rfcT);
	}

	@Override
	public Long getConvenioInicialExistente(String rfcT) {
		return conveniosrepo.getContratacionInicial(rfcT);
	}
	
	@Override
	public Long getConvenioPosteriorExistente(String rfcT) {
		return conveniosrepo.getContratacionPosterior(rfcT);
	}
	
	private boolean rerificarRfcConvenioNuevaContratacion(Convenios convenio) throws Exception{
		Optional<Convenios> conveniosExistentes = conveniosrepo.findByClientesRfc(convenio.getClientes().getRfc());
		if(conveniosExistentes.isPresent()) {
			throw new Exception("Convenio inicial ya existe.");
		}
		return false;
	}
	
	@Override
	public Convenios crearConvenio(Convenios convenio) throws Exception {
		if(!rerificarRfcConvenioNuevaContratacion(convenio)) {
			convenio = conveniosrepo.save(convenio);
		}
		return convenio;
	}

	@Override
	public Convenios crearConvenioActualizado(Convenios convenio) throws Exception {
		if(conveniosrepo.getContratacionPosterior(convenio.getClientes().getRfc())==0) {
			convenio = conveniosrepo.save(convenio);
		} else {
			throw new Exception("Ya existe convenio actual.");
		}
		return convenio;
	}

	

	

	

	

	

	

	//@Override
	//public String getFolioConvenio() {
	//	return conveniosrepo.getFolioConvenio();
	//}

	
}
