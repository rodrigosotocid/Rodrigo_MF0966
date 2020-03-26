package com.rodrigo.mf0966.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Profesor{
	
	private Integer id;
	private Long nss;
	private String nombre;
	private String apellidos;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private Date fechaNacimiento;
	private String dni;
	private String direccion, poblacion;
	private Integer codigoPostal, telefono;
	private String email;
	private Integer activo;
	
	
	public Profesor() {	}
	
	public Profesor(Integer id, Long nss, String nombre, String apellidos, String fechaNacimiento, String dni,
			String direccion, String poblacion, Integer codigoPostal, Integer telefono, String email, Integer activo) {
		
		setId(id);
		setNss(nss);
		setNombre(nombre);
		setApellidos(apellidos);
		setFechaNacimiento(fechaNacimiento);
		setDni(dni);
		setDireccion(direccion);
		setPoblacion(poblacion);
		setCodigoPostal(codigoPostal);
		setTelefono(telefono);
		setEmail(email);
		setActivo(activo);
	}
	
	private void setFechaNacimiento(String fechaNacimiento) {
		try {
			setFechaNacimiento(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(fechaNacimiento));
		} catch (ParseException e) {
			setErrorFecha("El formato de la fecha es incorrecto");
		}
		
	}

	private void setErrorFecha(String string) {
		throw new UnsupportedOperationException("setErrorFecha: NO ESTA IMPLEMENTADO");
	}

	public Profesor(Integer id, Long nss, String nombre, String apellidos, Date fechaNacimiento, String dni,
			String direccion, String poblacion, Integer codigoPostal, Integer telefono, String email, Integer activo) {
		
		setId(id);
		setNss(nss);
		setNombre(nombre);
		setApellidos(apellidos);
		setFechaNacimiento(fechaNacimiento);
		setDni(dni);
		setDireccion(direccion);
		setPoblacion(poblacion);
		setCodigoPostal(codigoPostal);
		setTelefono(telefono);
		setEmail(email);
		setActivo(activo);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getNss() {
		return nss;
	}

	public void setNss(Long nss) {
		this.nss = nss;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public Integer getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(Integer codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public Integer getTelefono() {
		return telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activo == null) ? 0 : activo.hashCode());
		result = prime * result + ((apellidos == null) ? 0 : apellidos.hashCode());
		result = prime * result + ((codigoPostal == null) ? 0 : codigoPostal.hashCode());
		result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((nss == null) ? 0 : nss.hashCode());
		result = prime * result + ((poblacion == null) ? 0 : poblacion.hashCode());
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
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
		Profesor other = (Profesor) obj;
		if (activo == null) {
			if (other.activo != null)
				return false;
		} else if (!activo.equals(other.activo))
			return false;
		if (apellidos == null) {
			if (other.apellidos != null)
				return false;
		} else if (!apellidos.equals(other.apellidos))
			return false;
		if (codigoPostal == null) {
			if (other.codigoPostal != null)
				return false;
		} else if (!codigoPostal.equals(other.codigoPostal))
			return false;
		if (direccion == null) {
			if (other.direccion != null)
				return false;
		} else if (!direccion.equals(other.direccion))
			return false;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fechaNacimiento == null) {
			if (other.fechaNacimiento != null)
				return false;
		} else if (!fechaNacimiento.equals(other.fechaNacimiento))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (nss == null) {
			if (other.nss != null)
				return false;
		} else if (!nss.equals(other.nss))
			return false;
		if (poblacion == null) {
			if (other.poblacion != null)
				return false;
		} else if (!poblacion.equals(other.poblacion))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Profesor [id=" + id + ", nss=" + nss + ", nombre=" + nombre + ", apellidos=" + apellidos
				+ ", fechaNacimiento=" + fechaNacimiento + ", dni=" + dni + ", direccion=" + direccion + ", poblacion="
				+ poblacion + ", codigoPostal=" + codigoPostal + ", telefono=" + telefono + ", email=" + email
				+ ", activo=" + activo + "]";
	}
	
	
}
