package com.rodrigo.mf0966.controller;

import com.rodrigo.mf0966.models.Alumno;
import com.rodrigo.mf0966.models.Cliente;
import com.rodrigo.mf0966.models.Curso;
import com.rodrigo.mf0966.models.Imparticion;
import com.rodrigo.mf0966.models.Profesor;
import com.rodrigo.mf0966.models.Resena;
import com.rodrigo.mf0966.repository.Dao;

public class Globales {
	
	public static Dao<Alumno> daoAlumno;
	public static Dao<Profesor> daoProfesor;
	public static Dao<Cliente> daoCliente;
	public static Dao<Curso> daoCurso;
	public static Dao<Imparticion> daoImparticion;
	public static Dao<Resena> daoResena;
}
