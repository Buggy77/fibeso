package com.fibeso.captacion.pfuneraria.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fibeso.captacion.pfuneraria.entity.Pfdetalle;
import com.fibeso.captacion.pfuneraria.repository.PfdetalleRepository;

@Service
public class PfdetalleServiceImpl implements PfdetalleService {

	@Autowired
	PfdetalleRepository pfdetalleRepository;

	@Override
	//public Pfdetalle getPFxIdAndFchVigPrec(String idPaqPF, LocalDateTime fechVigPrec, String anioPF) throws Exception {
	public Pfdetalle getPFxIdAndFchVigPrec(String idPaqPF, Date fechVigPrec, String anioPF) throws Exception {
		return pfdetalleRepository.findByIdPFAndActivoAndFchVigPrecPaqAndAnioPFActual(idPaqPF, fechVigPrec, anioPF);
	}

	@Override
	//public Long getCostoTotalAsegurados(String idPF, LocalDateTime fchVigPF, String anioPF, double cantAsegurados)
	public Long getCostoTotalAsegurados(String idPF, Date fchVigPF, String anioPF, double cantAsegurados)
			throws Exception {
		return pfdetalleRepository.sumCostBeneficiario(idPF, fchVigPF, anioPF, cantAsegurados);
	}
	
	
}
