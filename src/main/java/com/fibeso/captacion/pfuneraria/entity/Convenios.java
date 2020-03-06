package com.fibeso.captacion.pfuneraria.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.StreamSupport;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PostLoad;
import javax.persistence.Query;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UpdateTimestamp;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

//import com.fibeso.captacion.pfuneraria.repository.CalendarioasuetoRepository;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity(name="Convenios")
@EntityListeners(AuditingEntityListener.class)
@Table(name="\"convenios\"", schema="esqfibeso")
public class Convenios implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5761043596588593119L;

	//@Transient
	//CalendarioasuetoRepository calendarioasuetoRepository = null;
	
	//@Transient
	//@PersistenceContext	
	//private EntityManager entityManager;
	
	@Id
	@SequenceGenerator(name="aliseq11",
    //sequenceName="convenios_IdReg_seq",
			sequenceName="`convenios_IdReg_seq`",
    allocationSize=1)
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "aliseq11")
	@Column(name = "`IdReg`", updatable=false, nullable = false)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Integer idReg;
	
	//@Column(name = "`IdTipoContratacion`",nullable = false, unique = true)
	@Transient
	//@Column(name = "`IdTipoContratacion`",nullable = false)
	//@NotNull
	//@NotEmpty
	@Size(min = 1, max = 1)
	//private String idTipoContratacion;
	private transient String idTipoContratacion;
	
	//@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	//@JoinColumn(name = "`IdTipoContratacion`", unique = true)
	//@JoinColumn(name = "`IdTipoContratacion`", referencedColumnName = "`IdTipoContratacion`", unique = true)
	//@JoinColumn(name = "`IdTipoContratacion`", referencedColumnName = "`IdTipoContratacion`", insertable=false, updatable=false)
	@JoinColumn(name = "`IdTipoContratacion`", referencedColumnName = "`IdTipoContratacion`", insertable=true, updatable=true)
	private Cat_servicios catservicios;
	
	@Transient
	//@Column(name = "`IdOperador`",nullable = false, unique = true)
	//@NotNull
	//@NotEmpty
	//@Size(min = 9, max = 11)
	//private String idOperador;
	private transient String idOperador;
	
	//@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	//@JoinColumn(name = "`IdOperador`", unique = true)
	//@JoinColumn(name = "`IdOperador`", referencedColumnName = "`IdOperador`", unique = true)
	//@JoinColumn(name = "`IdOperador`", referencedColumnName = "`IdOperador`", insertable=false, updatable=false)
	@JoinColumn(name = "`IdOperador`", referencedColumnName = "`IdOperador`", insertable=true, updatable=true)
	//@OrderBy(value = "`IdReg` DESC")
	private UsuariosOperador usuariosOperador;
	
	@Transient
	//@Column(name = "`IdVelatorio`",nullable = false, unique = true)
	//@NotNull
	//@NotEmpty
	//@Size(min = 1, max = 3)
	//private String idVelatorio;
	private transient String idVelatorio;
	
	//@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	//@JoinColumn(name = "`IdVelatorio`", unique = true)
	//@JoinColumn(name = "`IdVelatorio`", referencedColumnName = "`IdVelatorio`", unique = true)
	//@JoinColumn(name = "`IdVelatorio`", referencedColumnName = "`IdVelatorio`", insertable=false, updatable=false)
	@JoinColumn(name = "`IdVelatorio`", referencedColumnName = "`IdVelatorio`", insertable=true, updatable=true)
	private Velatorios velatorios;
	
	@Transient
	//@Column(name = "`RfcTitular`", nullable = false)
	//@NotNull
	//@NotEmpty
	//@Size(min = 10, max = 13)
	//private String rfcTitular;
	private transient String rfcTitular;
	
	//@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	//@JoinColumn(name = "`RfcTitular`", unique = true)
	//@JoinColumn(name = "`RfcTitular`", referencedColumnName = "`Rfc`", unique = true)
	//@JoinColumn(name = "`RfcTitular`", referencedColumnName = "`Rfc`", insertable=false, updatable=false)
	@JoinColumn(name = "`RfcTitular`", referencedColumnName = "`Rfc`", insertable=true, updatable=true)
	private Clientes clientes;
	
	@NaturalId
	@Column(name = "`FolioConvenio`",nullable = false, unique = true)
	//@NotNull
	//@NotEmpty
	//@Size(min = 28, max = 29)
	private String folioConvenio;
	
	//@OneToMany(mappedBy = "folioConvenio",
	//		cascade = CascadeType.ALL,
	//		orphanRemoval = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "convenio",
	cascade = CascadeType.ALL,
	orphanRemoval = true)
	private Set<Beneficiarios> beneficiarios;
	
	public void addBeneficiarios(Beneficiarios beneficiario) {
		beneficiarios.add(beneficiario);
		//beneficiario.setFolioConvenio(this);
		beneficiario.setConvenio(this);
	}
	
	public void removeBeneficiarios(Beneficiarios beneficiario) {
		beneficiarios.remove(beneficiario);
		//beneficiario.setFolioConvenio(this);
		//beneficiario.setConvenio(this);
		beneficiario.setConvenio(null);
	}
		
	@Column(name = "`FchContConve`",nullable = false)
	//@NotNull
	//@NotEmpty
	//@DateTimeFormat
	//private Date fchContConve;
	private LocalDateTime fchContConve;
	
	@Transient
	//@Column(name = "`IdPaqPF`",nullable = false)
	//@NotNull
	//@NotEmpty
	//@Size(min = 3, max = 10)
	//private String idPaqPF;
	private transient String idPaqPF;
	
	//@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	//@JoinColumn(name = "`IdPaqPF`", unique = true)
	//@JoinColumn(name = "`IdPaqPF`", referencedColumnName = "`IdPaqPF`", unique = true)
	//@JoinColumn(name = "`IdPaqPF`", referencedColumnName = "`IdPaqPF`", insertable=false, updatable=false)
	@JoinColumn(name = "`IdPaqPF`", referencedColumnName = "`IdPaqPF`", insertable=true, updatable=true)
	private Paquetespfs paquetespfs;
	
	@Column(name = "`PrecioPaq`",nullable = false)
	//@NotNull
	//@NotEmpty
	private Double precioPaq;
	
	@Column(name = "`ServiciosPaq`",nullable = false)
	//@NotNull
	@NotEmpty
	@Size(min = 3, max = 300)
	private String serviciosPaq;
	
	@Transient
	//@Column(name = "`IdCiudadContra`", nullable = false)
	//@NotNull
	//@NotEmpty
	//@Size(min = 3, max = 13)
	//private String idCiudadContra;
	private transient String idCiudadContra;
	
	//@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	//@JoinColumn(name = "`IdCiudadContra`", unique = true)
	//@JoinColumn(name = "`IdCiudadContra`", referencedColumnName = "`IdRegMnpioS`", insertable=false, updatable=false)
	@JoinColumn(name = "`IdCiudadContra`", referencedColumnName = "`IdRegMnpioS`", insertable=true, updatable=true)
	private Cat_mnpios municipios;
	
	@Transient
	//@Column(name = "`IdEstadoContra`", nullable = false)
	//@NotNull
	//@NotEmpty
	//@Size(min = 2, max = 2)
	//private String idEstadoContra;
	private transient String idEstadoContra;
	
	//@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	//@JoinColumn(name = "`IdEstadoContra`", unique = true)
	//@JoinColumn(name = "`IdEstadoContra`", referencedColumnName = "`IdRegEstadoS`", unique = true)
	//@JoinColumn(name = "`IdEstadoContra`", referencedColumnName = "`IdRegEstadoS`", insertable=false, updatable=false)
	@JoinColumn(name = "`IdEstadoContra`", referencedColumnName = "`IdRegEstadoS`", insertable=true, updatable=true)
	private Cat_estados estados;
	
	@Column(name = "`CostoAfiliacion`",nullable = false)
	//@NotNull
	//@NotEmpty
	private Double costoAfiliacion;
	
	@Column(name = "`SubTotalConvenio`",nullable = false)
	@NotNull
	//@NotEmpty
	private Double subTotalConvenio;
	
	@Column(name = "`TotalConvenio`",nullable = false)
	@NotNull
	//@NotEmpty
	private Double totalConvenio;
	
	@Column(name = "`DatosPago`",nullable = false, unique = true)
	//@NotNull
	@NotEmpty
	@Size(min = 5, max = 500)
	private String datosPago;
	
	@Column(name = "`FchPago`",nullable = false, columnDefinition = "DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private Date fchPago;
	
	@Column(name = "`FolioConvenioPrev`", unique = true)
	//@Size(min = 28, max = 29)
	private String folioConvenioPrev;
	
	@Column(name = "`CostoRetroConvenio`")
	private Double costoRetroConvenio;
	
	@Column(name = "`uuid`")
	//@Size(max = 40)
	private String uuid;
	
	@Column(name = "`foliofactura`")
	//@Size(max = 2)
	private String foliofactura;
	
	@Column(name = "`serie`")
	private Integer serie;
	
	@Column(name = "`datosfiscales`")
	private Boolean datosfiscales;
	
	@Column(name = "`Activo`",nullable = false)
	//@NotNull
	//@NotEmpty
	private Boolean activo;
	
	@CreatedDate
	@Column(name = "`FchCreReg`",nullable = false, updatable = false)
	//@NotNull
	//@NotEmpty
	//@DateTimeFormat(iso=ISO.DATE_TIME)
	@Temporal(TemporalType.TIMESTAMP)
	//@CreationTimestamp
	private Date fchCreReg;
	
	@CreatedBy
	@Column(name = "`UsrCreReg`",nullable = false, updatable = false)
	//@NotNull
	//@NotEmpty
	private String usrCreReg;
	
	@LastModifiedDate
	@Column(name = "`FchModReg`",nullable = true)
	//@DateTimeFormat(iso=ISO.DATE_TIME)
	@Temporal(TemporalType.TIMESTAMP)
	//@UpdateTimestamp
	private Date fchModReg;
	
	@LastModifiedBy
	@Column(name = "`UsrModReg`",nullable = true)
	private String usrModReg;
	
	@Transient
	public boolean esPeriodoRenovacion;
	
	@Transient
	public boolean dentroLimite;
	
	@Transient
	public LocalDateTime fechaVerificar;
	
	@Transient
	public boolean permitirRenovacion;
	
	@Transient
	public boolean getEsPeridoRenovacion() {
		return this.esPeriodoRenovacion;
	}
	
	@Transient
	public boolean getDentroLimite() {
		return this.dentroLimite;
	}
	
	@Transient
	public LocalDateTime getFechaVerificar() {
		return this.fechaVerificar;
	}
	
	
	
	@PostLoad
	private void postLoad() {
		LocalDateTime fecha = LocalDateTime.now();
		LocalDateTime fechaAux = fecha;
		//Boolean salir = false;
		
		//if(fchContConve.toLocalDate().getMonth().compareTo(fecha.getMonth()) == 0) {
		if(fchContConve.toLocalDate().getYear() == fecha.minusYears(1).getYear()) {
			if(fchContConve.toLocalDate().getMonth().compareTo(fecha.getMonth()) == 0) {
				this.esPeriodoRenovacion = true;
			} else {
				this.esPeriodoRenovacion = false;
			}
			if(fecha.getDayOfMonth() < 20) {
				this.dentroLimite = true;
			} else if(fecha.getDayOfMonth() <= 23) {
				if (fecha.getDayOfMonth() != 20) {
					fechaAux = fechaAux.minusDays(fecha.getDayOfMonth()-20);
				}
				//while(!salir) {
				//	switch (fechaAux.getDayOfMonth()) {
				//	case 20: 
						if(fechaAux.getDayOfWeek().getValue()==6) {
							fechaAux = fechaAux.plusDays(2);
							this.fechaVerificar = fechaAux;
							this.dentroLimite = true;
						} else if(fechaAux.getDayOfWeek().getValue()==7) {
							fechaAux = fechaAux.plusDays(1);
							this.fechaVerificar = fechaAux;
							this.dentroLimite = true;
						} else {
							this.dentroLimite = false;
						}
				//		break;
				//	case 21:
				//	}
					//if(fecha.getDayOfMonth() == 20 && fecha.getDayOfWeek().getValue())
					//	this.dentroLimite = true;
				//}
			} else {
				this.dentroLimite = false;
			}
		} else {
			this.esPeriodoRenovacion = false;
			this.dentroLimite = false;
		}
		//Session sesion = DatabaseManager
		//long meses = fchContConve.toLocalDate().until(LocalDateTime.now(), ChronoUnit.MONTHS);
		//calendarioasuetoRepository = new CalendarioasuetoRepository();
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory();
		
		//EntityManager em = emf.createEntityManager();
		
		//String qr = "SELECT e FROM Calendarioasueto e WHERE e.anioCal = (:pAnioCal)";
		//Query query = entityManager.createQuery(qr);
		//query.setParameter("pAnioCal", Integer.toString(LocalDateTime.now().getYear()));
		//@SuppressWarnings("unchecked")
		//List<Calendarioasueto> calendarioL = query.getResultList();
		//calendarioL.forEach(System.out::println);
        //em.close();
//		Iterable<Calendarioasueto> calendarioIt = calendarioasuetoRepository.findAllByAnioCalAndActivo(Integer.toString(LocalDateTime.now().getYear()), true);
		//Iterator<Calendarioasueto> calendarioI = calendarioIt.iterator();
//		long numbenef = StreamSupport.stream(calendarioIt.spliterator(), false).count();
//		System.out.println("Cantidad de registros recuperados del calendario: " + (Objects.isNull(numbenef)?"0":Long.toString(numbenef)));
		/*
		if(fchContConve.toLocalDate().getMonth().compareTo(LocalDateTime.now().getMonth()) == 0) {
			
		}
		*/
	}
	
	
	public Convenios() {
		super();
	}
	
	public Convenios(Integer id) {
		super();
		this.idReg = id;
	}

	/*
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Convenios other = (Convenios) obj;
		if (activo == null) {
			if (other.activo != null)
				return false;
		} else if (!activo.equals(other.activo))
			return false;
		if (costoAfiliacion == null) {
			if (other.costoAfiliacion != null)
				return false;
		} else if (!costoAfiliacion.equals(other.costoAfiliacion))
			return false;
		if (costoRetroConvenio == null) {
			if (other.costoRetroConvenio != null)
				return false;
		} else if (!costoRetroConvenio.equals(other.costoRetroConvenio))
			return false;
		if (datosPago == null) {
			if (other.datosPago != null)
				return false;
		} else if (!datosPago.equals(other.datosPago))
			return false;
		if (datosfiscales == null) {
			if (other.datosfiscales != null)
				return false;
		} else if (!datosfiscales.equals(other.datosfiscales))
			return false;
		if (fchContConve == null) {
			if (other.fchContConve != null)
				return false;
		} else if (!fchContConve.equals(other.fchContConve))
			return false;
		if (fchCreReg == null) {
			if (other.fchCreReg != null)
				return false;
		} else if (!fchCreReg.equals(other.fchCreReg))
			return false;
		if (fchModReg == null) {
			if (other.fchModReg != null)
				return false;
		} else if (!fchModReg.equals(other.fchModReg))
			return false;
		if (folioConvenio == null) {
			if (other.folioConvenio != null)
				return false;
		} else if (!folioConvenio.equals(other.folioConvenio))
			return false;
		if (folioConvenioPrev == null) {
			if (other.folioConvenioPrev != null)
				return false;
		} else if (!folioConvenioPrev.equals(other.folioConvenioPrev))
			return false;
		if (foliofactura == null) {
			if (other.foliofactura != null)
				return false;
		} else if (!foliofactura.equals(other.foliofactura))
			return false;
		if (idCiudadContra == null) {
			if (other.idCiudadContra != null)
				return false;
		} else if (!idCiudadContra.equals(other.idCiudadContra))
			return false;
		if (idEstadoContra == null) {
			if (other.idEstadoContra != null)
				return false;
		} else if (!idEstadoContra.equals(other.idEstadoContra))
			return false;
		if (idOperador == null) {
			if (other.idOperador != null)
				return false;
		} else if (!idOperador.equals(other.idOperador))
			return false;
		if (idPaqPF == null) {
			if (other.idPaqPF != null)
				return false;
		} else if (!idPaqPF.equals(other.idPaqPF))
			return false;
		if (idTipoContratacion == null) {
			if (other.idTipoContratacion != null)
				return false;
		} else if (!idTipoContratacion.equals(other.idTipoContratacion))
			return false;
		if (idVelatorio == null) {
			if (other.idVelatorio != null)
				return false;
		} else if (!idVelatorio.equals(other.idVelatorio))
			return false;
		if (precioPaq == null) {
			if (other.precioPaq != null)
				return false;
		} else if (!precioPaq.equals(other.precioPaq))
			return false;
		if (rfcTitular == null) {
			if (other.rfcTitular != null)
				return false;
		} else if (!rfcTitular.equals(other.rfcTitular))
			return false;
		if (serie == null) {
			if (other.serie != null)
				return false;
		} else if (!serie.equals(other.serie))
			return false;
		if (serviciosPaq == null) {
			if (other.serviciosPaq != null)
				return false;
		} else if (!serviciosPaq.equals(other.serviciosPaq))
			return false;
		if (subTotalConvenio == null) {
			if (other.subTotalConvenio != null)
				return false;
		} else if (!subTotalConvenio.equals(other.subTotalConvenio))
			return false;
		if (totalConvenio == null) {
			if (other.totalConvenio != null)
				return false;
		} else if (!totalConvenio.equals(other.totalConvenio))
			return false;
		if (usrCreReg == null) {
			if (other.usrCreReg != null)
				return false;
		} else if (!usrCreReg.equals(other.usrCreReg))
			return false;
		if (usrModReg == null) {
			if (other.usrModReg != null)
				return false;
		} else if (!usrModReg.equals(other.usrModReg))
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activo == null) ? 0 : activo.hashCode());
		result = prime * result + ((costoAfiliacion == null) ? 0 : costoAfiliacion.hashCode());
		result = prime * result + ((costoRetroConvenio == null) ? 0 : costoRetroConvenio.hashCode());
		result = prime * result + ((datosPago == null) ? 0 : datosPago.hashCode());
		result = prime * result + ((datosfiscales == null) ? 0 : datosfiscales.hashCode());
		result = prime * result + ((fchContConve == null) ? 0 : fchContConve.hashCode());
		result = prime * result + ((fchCreReg == null) ? 0 : fchCreReg.hashCode());
		result = prime * result + ((fchModReg == null) ? 0 : fchModReg.hashCode());
		result = prime * result + ((folioConvenio == null) ? 0 : folioConvenio.hashCode());
		result = prime * result + ((folioConvenioPrev == null) ? 0 : folioConvenioPrev.hashCode());
		result = prime * result + ((foliofactura == null) ? 0 : foliofactura.hashCode());
		result = prime * result + ((idCiudadContra == null) ? 0 : idCiudadContra.hashCode());
		result = prime * result + ((idEstadoContra == null) ? 0 : idEstadoContra.hashCode());
		result = prime * result + ((idOperador == null) ? 0 : idOperador.hashCode());
		result = prime * result + ((idPaqPF == null) ? 0 : idPaqPF.hashCode());
		result = prime * result + ((idTipoContratacion == null) ? 0 : idTipoContratacion.hashCode());
		result = prime * result + ((idVelatorio == null) ? 0 : idVelatorio.hashCode());
		result = prime * result + ((precioPaq == null) ? 0 : precioPaq.hashCode());
		result = prime * result + ((rfcTitular == null) ? 0 : rfcTitular.hashCode());
		result = prime * result + ((serie == null) ? 0 : serie.hashCode());
		result = prime * result + ((serviciosPaq == null) ? 0 : serviciosPaq.hashCode());
		result = prime * result + ((subTotalConvenio == null) ? 0 : subTotalConvenio.hashCode());
		result = prime * result + ((totalConvenio == null) ? 0 : totalConvenio.hashCode());
		result = prime * result + ((usrCreReg == null) ? 0 : usrCreReg.hashCode());
		result = prime * result + ((usrModReg == null) ? 0 : usrModReg.hashCode());
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Convenios [idTipoContratacion=" + idTipoContratacion + ", idOperador=" + idOperador + ", idVelatorio="
				+ idVelatorio + ", rfcTitular=" + rfcTitular + ", folioConvenio=" + folioConvenio + ", fchContConve="
				+ fchContConve + ", idPaqPF=" + idPaqPF + ", precioPaq=" + precioPaq + ", serviciosPaq=" + serviciosPaq
				+ ", idCiudadContra=" + idCiudadContra + ", idEstadoContra=" + idEstadoContra + ", costoAfiliacion="
				+ costoAfiliacion + ", subTotalConvenio=" + subTotalConvenio + ", totalConvenio=" + totalConvenio
				+ ", datosPago=" + datosPago + ", folioConvenioPrev=" + folioConvenioPrev + ", costoRetroConvenio="
				+ costoRetroConvenio + ", uuid=" + uuid + ", foliofactura=" + foliofactura + ", serie=" + serie
				+ ", datosfiscales=" + datosfiscales + ", activo=" + activo + ", fchCreReg=" + fchCreReg
				+ ", usrCreReg=" + usrCreReg + ", fchModReg=" + fchModReg + ", usrModReg=" + usrModReg + "]";
	}
	*/
	
}
