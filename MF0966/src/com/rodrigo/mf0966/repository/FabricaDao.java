package com.rodrigo.mf0966.repository;

import com.rodrigo.mf0966.models.Alumno;
import com.rodrigo.mf0966.models.Cliente;
import com.rodrigo.mf0966.models.Curso;
import com.rodrigo.mf0966.models.Imparticion;
import com.rodrigo.mf0966.models.Profesor;
import com.rodrigo.mf0966.models.Resena;

public interface FabricaDao {
	
	Dao<Alumno> getAlumnoDao();
	Dao<Curso> getCursoDao();
	Dao<Cliente> getClienteDao();
	Dao<Profesor> getProfesorDao();
	Dao<Resena> getResenaDao();
	Dao<Imparticion> getImparticionDao();
}
