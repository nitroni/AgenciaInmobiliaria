package co.com.udem.agenciainmobiliaria.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Propiedad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private double areaMetrosCuadros;
	private int numeroHabitaciones;
	private int numeroBanos;
	private String tipoRegistro;
	private double valor;
	private Long codigo;
	private Long idUsuario;
	

	public Propiedad(Long id, double areaMetrosCuadros, int numeroHabitaciones, int numeroBanos, String tipoRegistro,
			double valor, Long codigo, Long idUsuario) {
		super();
		this.id = id;
		this.areaMetrosCuadros = areaMetrosCuadros;
		this.numeroHabitaciones = numeroHabitaciones;
		this.numeroBanos = numeroBanos;
		this.tipoRegistro = tipoRegistro;
		this.valor = valor;
		this.codigo = codigo;
		this.idUsuario = idUsuario;
	}
	public Propiedad() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getAreaMetrosCuadros() {
		return areaMetrosCuadros;
	}
	public void setAreaMetrosCuadros(double areaMetrosCuadros) {
		this.areaMetrosCuadros = areaMetrosCuadros;
	}
	public int getNumeroHabitaciones() {
		return numeroHabitaciones;
	}
	public void setNumeroHabitaciones(int numeroHabitaciones) {
		this.numeroHabitaciones = numeroHabitaciones;
	}
	public int getNumeroBanos() {
		return numeroBanos;
	}
	public void setNumeroBanos(int numeroBanos) {
		this.numeroBanos = numeroBanos;
	}
	public String getTipoRegistro() {
		return tipoRegistro;
	}
	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
}
