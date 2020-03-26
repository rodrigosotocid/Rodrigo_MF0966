package com.rodrigo.mf0966.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Curso {
	
	private Integer id;
	private String nombre, identificador;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private Date fInicio;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private Date fFin;
	private Integer nHoras;
	private String temario;
	private Integer activo;
	private Cliente cliente;
	private double precio;
	private Profesor profesor;
	
	public Curso() {
		// TODO Auto-generated constructor stub
	}
	
	public Curso(Integer id){
		setId(id);
	}
	public Curso(Integer id, String nombre, String identificador, Date fInicio, Date fFin, Integer nHoras,
			String temario, Integer activo, Cliente cliente, double precio, Profesor profesor) {
		
		setId(id);
		setNombre(nombre);
		setIdentificador(identificador);
		setfInicio(fInicio);
		setfFin(fFin);
		setnHoras(nHoras);
		setTemario(temario);
		setActivo(activo);
		setCliente(cliente);
		setPrecio(precio);
		setProfesor(profesor);
	}

	public Curso(Integer id, String nombre, String identificador, String fInicio, String fFin, Integer nHoras,
			String temario, Integer activo, Cliente cliente, double precio, Profesor profesor) {
		
		setId(id);
		setNombre(nombre);
		setIdentificador(identificador);
		setfInicio(fInicio);
		setfFin(fFin);
		setnHoras(nHoras);
		setTemario(temario);
		setActivo(activo);
		setCliente(cliente);
		setPrecio(precio);
		setProfesor(profesor);
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public Date getfInicio() {
		return fInicio;
	}

	public void setfInicio(Date fInicio) {
		this.fInicio = fInicio;
	}
	
	public void setfInicio(String fInicio) {
		try {
			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(fInicio);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public Date getfFin() {
		return fFin;
	}

	public void setfFin(Date fFin) {
		this.fFin = fFin;
	}
	
	public void setfFin(String fFin) {
		try {
			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(fFin);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Integer getnHoras() {
		return nHoras;
	}

	public void setnHoras(Integer nHoras) {
		this.nHoras = nHoras;
	}

	public String getTemario() {
		return temario;
	}

	public void setTemario(String temario) {
		this.temario = temario;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activo == null) ? 0 : activo.hashCode());
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((fFin == null) ? 0 : fFin.hashCode());
		result = prime * result + ((fInicio == null) ? 0 : fInicio.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((identificador == null) ? 0 : identificador.hashCode());
		result = prime * result + ((nHoras == null) ? 0 : nHoras.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		long temp;
		temp = Double.doubleToLongBits(precio);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((profesor == null) ? 0 : profesor.hashCode());
		result = prime * result + ((temario == null) ? 0 : temario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Curso other = (Curso) obj;
		if (activo == null) {
			if (other.activo != null)
				return false;
		} else if (!activo.equals(other.activo))
			return false;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (fFin == null) {
			if (other.fFin != null)
				return false;
		} else if (!fFin.equals(other.fFin))
			return false;
		if (fInicio == null) {
			if (other.fInicio != null)
				return false;
		} else if (!fInicio.equals(other.fInicio))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (identificador == null) {
			if (other.identificador != null)
				return false;
		} else if (!identificador.equals(other.identificador))
			return false;
		if (nHoras == null) {
			if (other.nHoras != null)
				return false;
		} else if (!nHoras.equals(other.nHoras))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (Double.doubleToLongBits(precio) != Double.doubleToLongBits(other.precio))
			return false;
		if (profesor == null) {
			if (other.profesor != null)
				return false;
		} else if (!profesor.equals(other.profesor))
			return false;
		if (temario == null) {
			if (other.temario != null)
				return false;
		} else if (!temario.equals(other.temario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Curso [id=" + id + ", nombre=" + nombre + ", identificador=" + identificador + ", fInicio=" + fInicio
				+ ", fFin=" + fFin + ", nHoras=" + nHoras + ", temario=" + temario + ", activo=" + activo + ", cliente="
				+ cliente + ", precio=" + precio + ", profesor=" + profesor + "]";
	}
	
	
	
}
