package co.com.udem.agenciainmobiliaria.dto;

public class TiposDeIdentificacionDTO {

	private Long id;
	private String tipoIdentificacion;
	private String descripcion;
	public TiposDeIdentificacionDTO(Long id, String tipoIdentificacion, String descripcion) {
		super();
		this.id = id;
		this.tipoIdentificacion = tipoIdentificacion;
		this.descripcion = descripcion;
	}
	public TiposDeIdentificacionDTO() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
