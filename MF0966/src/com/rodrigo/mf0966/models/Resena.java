package com.rodrigo.mf0966.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Resena {
	
	private Integer id;
	private Imparticion imparticion;
	private Alumno alumno;
	private String resena;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private Date fechaResenia;
	
	
	public Resena() { }
	
	public Resena(Integer id, Imparticion imparticion, Alumno alumno, String resena,  String fechaResenia) {

		setId(id);
		setImparticion(imparticion);
		setAlumno(alumno);
		setResena(resena);
		setFechaResenia(fechaResenia);
	}
	
	public Resena(Integer id, Imparticion imparticion, Alumno alumno, String resena,  Date fechaResenia) {

		setId(id);
		setImparticion(imparticion);
		setAlumno(alumno);
		setResena(resena);
		setFechaResenia(fechaResenia);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Imparticion getImparticion() {
		return imparticion;
	}

	public void setImparticion(Imparticion imparticion) {
		this.imparticion = imparticion;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public String getResena() {
		return resena;
	}

	public void setResena(String resena) {
		this.resena = resena;
	}

	public Date getFechaResenia() {
		return fechaResenia;
	}

	public void setFechaResenia(Date fechaResenia) {
		this.fechaResenia = fechaResenia;
	}
	
	public void setFechaResenia(String fechaResenia) {
		try {
			setFechaResenia(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(fechaResenia));
		} catch (ParseException e) {
			setErrorFecha("El formato de la fecha es incorrecto");
		}
	}

	private void setErrorFecha(String string) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("setErrorFecha: NO ESTA IMPLEMENTADO");
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alumno == null) ? 0 : alumno.hashCode());
		result = prime * result + ((fechaResenia == null) ? 0 : fechaResenia.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((imparticion == null) ? 0 : imparticion.hashCode());
		result = prime * result + ((resena == null) ? 0 : resena.hashCode());
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
		Resena other = (Resena) obj;
		if (alumno == null) {
			if (other.alumno != null)
				return false;
		} else if (!alumno.equals(other.alumno))
			return false;
		if (fechaResenia == null) {
			if (other.fechaResenia != null)
				return false;
		} else if (!fechaResenia.equals(other.fechaResenia))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (imparticion == null) {
			if (other.imparticion != null)
				return false;
		} else if (!imparticion.equals(other.imparticion))
			return false;
		if (resena == null) {
			if (other.resena != null)
				return false;
		} else if (!resena.equals(other.resena))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Resena [id=" + id + ", imparticion=" + imparticion + ", alumno=" + alumno + ", resena=" + resena
				+ ", fechaResenia=" + fechaResenia + "]";
	}


	
	
}
