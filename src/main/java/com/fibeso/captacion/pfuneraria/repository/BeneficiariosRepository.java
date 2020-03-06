package com.fibeso.captacion.pfuneraria.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fibeso.captacion.pfuneraria.entity.Beneficiarios;
import com.fibeso.captacion.pfuneraria.entity.Clientes;

@Repository
public interface BeneficiariosRepository extends CrudRepository<Beneficiarios, Integer> {

	//public Set<Beneficiarios> findAllByRfcTitular(Clientes rfcTitular);
	
	//public Set<Beneficiarios> findAllByClientesRfcAndActivoAndAnioAsegurado(String rfcTitular, Boolean activo, String anioAsegurado);
	public Iterable<Beneficiarios> findAllByClientesRfcAndActivoAndAnioAsegurado(String rfcTitular, Boolean activo, String anioAsegurado);
	
	@Query("SELECT SUM(b.costoAsegurado) FROM Beneficiarios b\r\n" + 
			"WHERE b.clientes.rfc = (:pRfcT) and b.activo = true and b.anioAsegurado = (:pAnioAseg)")
	public Long sumBeneficiario(@Param("pRfcT") String rfcT, @Param("pAnioAseg") String anioAseg);
	
	@Query("SELECT COUNT(*) FROM Beneficiarios b\r\n" + 
			"WHERE b.clientes.rfc = (:pRfcT) and b.activo = true and b.anioAsegurado = (:pAnioAseg) and b.asegurado = true")
	public Integer cantBenefAsegurados(@Param("pRfcT") String rfcT, @Param("pAnioAseg") String anioAseg);
	
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Beneficiarios c SET c.convenio.folioConvenio = (:pFolioConv), c.costoAsegurado = (:pCostoAseg) WHERE c.clientes.rfc = (:pRfcT) and c.activo = true and c.anioAsegurado = (:pAnioAseg) and c.convenio.folioConvenio is null")
	int actualizarFolioCostoBeneficiarios(@Param("pRfcT") String rfcT, @Param("pAnioAseg") String anioAseg, @Param("pFolioConv") String folioConv, @Param("pCostoAseg") double costoAseg);
	
	//VALIDACIONES
	public Optional<Beneficiarios> findByIdBeneficiario(Integer id);
}
