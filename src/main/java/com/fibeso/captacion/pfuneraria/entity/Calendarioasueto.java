package com.fibeso.captacion.pfuneraria.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity(name="Calendarioasueto")
@Table(name="\"calendarioasueto\"", schema="esqfibeso")
public class Calendarioasueto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3438976146252227347L;

	@Id
	@SequenceGenerator(name="aliseq13",
			sequenceName="`calendarioasueto_IdRegCalen_seq`",
    allocationSize=1)
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "aliseq13")
	@Column(name = "`IdRegCalen`", updatable=false, nullable = false)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Integer idRegCalen;
	
	@Column(name = "`FechaAsueto`",nullable = false, columnDefinition = "DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	//@NotNull
	//private Date fechaAsueto;
	private LocalDate fechaAsueto;
	
	@Column(name = "`DescCelebracion`")
	//@NotNull
	//@NotEmpty
	@Size(min = 5, max = 200)
	private String descCelebracion;
	
	@Column(name = "`AnioCal`",nullable = false)
	//@NotNull
	//@NotEmpty
	@Size(min = 4, max = 4)
	private String anioCal;
	
	@Column(name = "`Activo`",nullable = false)
	@NotNull
	private Boolean activo;
	
	@Column(name = "`FchCreReg`")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fchCreReg;
	
	@Column(name = "`UsrCreReg`")
	@Size(min = 4, max = 20)
	private String usrCreReg;
	
	@Column(name = "`FchModReg`")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fchModReg;
	
	@Column(name = "`UsrModReg`")
	@Size(min = 4, max = 20)
	private String usrModReg;
	
	public Calendarioasueto() {
		super();
	}
	
	public Calendarioasueto(Integer id) {
		super();
		this.idRegCalen = id;
	}
	
}
