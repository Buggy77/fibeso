package com.fibeso.captacion.pfuneraria.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fibeso.captacion.pfuneraria.entity.Calendarioasueto;

@Repository
public interface CalendarioasuetoRepository extends CrudRepository<Calendarioasueto,Integer> {

	public Iterable<Calendarioasueto> findAllByAnioCalAndActivo(String aniocal, Boolean activo);
}
