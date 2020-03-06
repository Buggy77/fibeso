package com.fibeso.captacion.pfuneraria.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//import com.fibeso.captacion.pfuneraria.entity.Paquetespfs;
import com.fibeso.captacion.pfuneraria.entity.Pfdetalle;

@Repository
public interface PfdetalleRepository extends CrudRepository<Pfdetalle,Integer> {

	/*
	@Query("SELECT p FROM Pfdetalle p\r\n" + 
			"WHERE p.paquetespfs.idPaqPF = (:pIdPF) and p.activo = true and p.fchVigPrecPaq = (:pFchVigPF) and p.anioPF = (:pAnioPF)") 
	public Pfdetalle findByIdPFAndActivoAndFchVigPrecPaqAndAnioPFActual(@Param("pIdPF") String idPF, @Param("pFchVigPF") LocalDateTime fchVigPF, @Param("pAnioPF") String anioPF);
			*/
	@Query("SELECT p FROM Pfdetalle p\r\n" + 
			"WHERE p.paquetespfs.idPaqPF = (:pIdPF) and p.activo = true and cast(p.fchVigPrecPaq as date) = (:pFchVigPF) and p.anioPF = (:pAnioPF)")
	public Pfdetalle findByIdPFAndActivoAndFchVigPrecPaqAndAnioPFActual(@Param("pIdPF") String idPF, @Param("pFchVigPF") Date fchVigPF, @Param("pAnioPF") String anioPF);
	
	/*
	@Query("SELECT (:pCant)*b.precioAse FROM Pfdetalle b\r\n" + 
			"WHERE b.paquetespfs.idPaqPF = (:pIdPF) and b.fchVigPrecPaq = (:pFchVigPF) and b.anioPF = (:pAnioPF) and b.activo = true")
	public Long sumCostBeneficiario(@Param("pIdPF") String idPF, @Param("pFchVigPF") LocalDateTime fchVigPF, @Param("pAnioPF") String anioPF, @Param("pCant") double cantAsegurados);
	*/
	@Query("SELECT (:pCant)*b.precioAse FROM Pfdetalle b\r\n" + 
			"WHERE b.paquetespfs.idPaqPF = (:pIdPF) and cast(b.fchVigPrecPaq as date) = (:pFchVigPF) and b.anioPF = (:pAnioPF) and b.activo = true")
	public Long sumCostBeneficiario(@Param("pIdPF") String idPF, @Param("pFchVigPF") Date fchVigPF, @Param("pAnioPF") String anioPF, @Param("pCant") double cantAsegurados);
}
