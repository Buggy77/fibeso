package com.fibeso.captacion.pfuneraria.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.fibeso.captacion.pfuneraria.entity.Pfdetalle;

public interface PfdetalleService {

	//public Pfdetalle getPFxIdAndFchVigPrec(String idPaqPF, LocalDateTime fechVigPrec, String anioPF) throws Exception;
	public Pfdetalle getPFxIdAndFchVigPrec(String idPaqPF, Date fechVigPrec, String anioPF) throws Exception;
	
	//public Long getCostoTotalAsegurados(String idPF, LocalDateTime fchVigPF, String anioPF, double cantAsegurados) throws Exception;
	public Long getCostoTotalAsegurados(String idPF, Date fchVigPF, String anioPF, double cantAsegurados) throws Exception;
}
