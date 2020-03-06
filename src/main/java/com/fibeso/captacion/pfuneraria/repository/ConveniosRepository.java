package com.fibeso.captacion.pfuneraria.repository;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import com.fibeso.captacion.pfuneraria.entity.Convenios;

@Repository
public interface ConveniosRepository extends CrudRepository<Convenios, Integer> {

	
	
	public Iterable<Convenios> findAllByActivo(Boolean activo);
	
	
	
	//public Optional<Convenios> findAllByIdTipoContratacionAndIdOperador
	//public Iterable<Convenios> findAllByIdOperadorAndActivo(String idOperador, Boolean activo);
	public Iterable<Convenios> findAllByUsuariosOperadorIdOperadorAndActivo(String idOperador, Boolean activo);
	public Iterable<Convenios> findAllByUsuariosOperadorIdOperadorAndActivo(String idOperador, Boolean activo, Sort sort);
	public Iterable<Convenios> findAllByUsuariosOperadorIdOperadorAndActivoOrderByIdRegDesc(String idOperador, Boolean activo);
	public Optional<Convenios> findById(Integer id);
	
	//@Query("SELECT e FROM Convenios e INNER JOIN UsuariosOperador f ON e.usuariosOperador.idOperador = f.idOperador WHERE e.usuariosOperador.idOperador = (:pIdOper) AND e.activo = true ORDER BY e.idReg DESC")
	//@Query("SELECT g FROM Convenios g, (SELECT e FROM Convenios e INNER JOIN UsuariosOperador f ON e.usuariosOperador.idOperador = f.idOperador WHERE e.usuariosOperador.idOperador = (:pIdOper) AND e.activo = true) h WHERE g.idReg = h.idReg ORDER BY g.idReg DESC")
	//@Query("SELECT e FROM Convenios e WHERE e.usuariosOperador.idOperador = (:pIdOper) AND e.activo = true ORDER BY e.idReg DESC")
	@Query(value="SELECT * FROM convenios e WHERE e.\"IdOperador\" = (:pIdOper) AND e.\"Activo\" = true ORDER BY e.\"IdReg\" desc",nativeQuery = true)
	public Iterable<Convenios> getAllConveniosxOperador(@Param("pIdOper") String idOper);
	
	public Optional<Convenios> findByClientesRfc(String rfcT);
	
	@Query("SELECT COUNT(b) FROM Convenios b WHERE b.clientes.rfc = (:pRfcT)")
	public Long getCantidadConvenios(@Param("pRfcT") String rfcT);
	
	@Query("SELECT COUNT(b) FROM Convenios b WHERE b.clientes.rfc = (:pRfcT) and b.catservicios.idTipoContratacion = '1' and b.activo = true")
	public Long getContratacionInicial(@Param("pRfcT") String rfcT);
	
	@Query("SELECT COUNT(e) FROM Convenios e WHERE e.clientes.rfc = (:pRfcT) AND cast(EXTRACT(YEAR FROM e.fchContConve) as text) = cast(EXTRACT(YEAR FROM now()) as text) AND cast(EXTRACT(YEAR FROM e.fchCreReg) as text) = cast(EXTRACT(YEAR FROM now()) as text) AND e.catservicios.idTipoContratacion = '2' AND e.activo = true")
	public Long getContratacionPosterior(@Param("pRfcT") String rfcT);
	
	@Query(value="select * from convenios e where \"RfcTitular\" = (:pRfcT) and EXTRACT(YEAR FROM \"FchContConve\") = EXTRACT(YEAR FROM now() - cast('1 year' as interval)) limit 1", nativeQuery=true)
	public Optional<Convenios> getConvenioPrevio(@Param("pRfcT") String rfcT);

	@Query(value="select * from convenios where \"RfcTitular\" = 'QWERTYUIOPASL' and EXTRACT(YEAR FROM \"FchContConve\") = EXTRACT(YEAR FROM now() - cast('1 year' as interval)) and EXTRACT(YEAR FROM \"FchCreReg\") = EXTRACT(YEAR FROM now() - cast('1 year' as interval)) and EXTRACT(YEAR FROM \"FchPago\") = EXTRACT(YEAR FROM now() - cast('1 year' as interval)) order by \"FchContConve\" desc, \"FchCreReg\" desc, \"FchPago\" desc limit 1", nativeQuery=true)
	public Optional<Convenios> getConvenioPrevioAct(@Param("pRfcT") String rfcT);
	/*
	@Query("select e from Convenios e where e.clientes.rfc = (:pRfcT) and e.activo = true and EXTRACT(YEAR FROM e.fchContConve) = EXTRACT(YEAR FROM now() - cast('1 year' as interval))\r\n" + 
			"limit 1")
	public Optional<Convenios> getConvenioPrevio(@Param("pRfcT") String rfcT);
	*/
	
	//@Query("SELECT folioconv()")
	//public String getFolioConvenio();
	
}
