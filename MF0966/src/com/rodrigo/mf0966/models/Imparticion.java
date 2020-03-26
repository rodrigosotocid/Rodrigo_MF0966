package com.rodrigo.mf0966.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Imparticion {

	private Integer id;
	private Curso curso;
	private Alumno alumno;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private Date fechaMatricula;
	
	
	public Imparticion() {}
	
	public Imparticion(Integer id) {
		// Constructor para obtener solo el id
		this();
		this.id=id;
	}
	
	public Imparticion(Integer id, Curso curso, Alumno alumno, Date fechaMatricula) {

		setId(id);
		setCurso(curso);
		setAlumno(alumno);
		setFechaMatricula(fechaMatricula);
	}
	
	public Imparticion(Integer id, Curso curso, Alumno alumno, String fechaMatricula) {

		setId(id);
		setCurso(curso);
		setAlumno(alumno);
		setFechaMatricula(fechaMatricula);
	}
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Curso getCurso() {
		return curso;
	}


	public void setCurso(Curso curso) {
		this.curso = curso;
	}


	public Alumno getAlumno() {
		return alumno;
	}


	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}


	public Date getFechaMatricula() {
		return fechaMatricula;
	}


	public void setFechaMatricula(Date fechaMatricula) {
		this.fechaMatricula = fechaMatricula;
	}
	
	public void setFechaMatricula(String fechaMatricula) {
		try {
			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(fechaMatricula);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alumno == null) ? 0 : alumno.hashCode());
		result = prime * result + ((curso == null) ? 0 : curso.hashCode());
		result = prime * result + ((fechaMatricula == null) ? 0 : fechaMatricula.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Imparticion other = (Imparticion) obj;
		if (alumno == null) {
			if (other.alumno != null)
				return false;
		} else if (!alumno.equals(other.alumno))
			return false;
		if (curso == null) {
			if (other.curso != null)
				return false;
		} else if (!curso.equals(other.curso))
			return false;
		if (fechaMatricula == null) {
			if (other.fechaMatricula != null)
				return false;
		} else if (!fechaMatricula.equals(other.fechaMatricula))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Imparticion [id=" + id + ", curso=" + curso + ", alumno=" + alumno + ", fechaMatricula="
				+ fechaMatricula + "]";
	}
	
	
}
