package com.rodrigo.mf0966.models;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Alumno {

	private Integer id;
	private String nombre, apellidos;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private Date fechaNacimiento;
	
	private String direccion, poblacion;
	private Integer codigoPostal, telefono;
	private String email, dni;
	private Integer nHermanos;
	private Integer activo;
	
	public Alumno() {}
	
	public Alumno(Integer id) {	
		setId(id);
	}

	public Alumno(Integer id, String nombre, String apellidos, String fechaNacimiento, String direccion, String poblacion,
			Integer codigoPostal, Integer telefono, String email, String dni, Integer nHermanos, Integer activo) {

		setId(id);
		setNombre(nombre);
		setApellidos(apellidos);
		setFechaNacimiento2(fechaNacimiento);
		setDireccion(direccion);
		setPoblacion(poblacion);
		setCodigoPostal(codigoPostal);
		setTelefono(telefono);
		setEmail(email);
		setDni(dni);
		setnHermanos(nHermanos);
		setActivo(activo);
	}

	@JsonProperty
	private void setFechaNacimiento2(String fechaNacimiento) {
		try {
			setFechaNacimiento((Date) new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(fechaNacimiento));
		} catch (ParseException e) {
			setErrorFecha("El formato de la fecha es incorrecto");
		}
		
	}

	private void setErrorFecha(String string) {
		throw new UnsupportedOperationException("setErrorFecha: NO ESTA IMPLEMENTADO");
	}

	public Alumno(Integer id, String nombre, String apellidos, Date fechaNacimiento, String direccion, String poblacion,
			Integer codigoPostal, Integer telefono, String email, String dni, Integer nHermanos, Integer activo) {

		setId(id);
		setNombre(nombre);
		setApellidos(apellidos);
		setFechaNacimiento(fechaNacimiento);
		setDireccion(direccion);
		setPoblacion(poblacion);
		setCodigoPostal(codigoPostal);
		setTelefono(telefono);
		setEmail(email);
		setDni(dni);
		setnHermanos(nHermanos);
		setActivo(activo);
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

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Integer getnHermanos() {
		return nHermanos;
	}

	public void setnHermanos(Integer nHermanos) {
		this.nHermanos = nHermanos;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}
	
	
	
	}
