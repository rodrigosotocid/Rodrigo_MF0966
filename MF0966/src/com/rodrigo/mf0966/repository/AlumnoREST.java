package com.rodrigo.mf0966.repository;

import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status;

import com.rodrigo.mf0966.controller.Globales;
import com.rodrigo.mf0966.models.Alumno;
import com.rodrigo.mf0966.models.Profesor;

@Path("/alumnos")
@Produces("application/json")
@Consumes("application/json")
public class AlumnoREST {

	private static final Logger LOGGER = Logger.getLogger(AlumnoREST.class.getCanonicalName());

	@Context
	private ServletContext context;
	
	static {
		LOGGER.info("Generando JAX-RS de Alumnos");
		}
	
	@GET
	public Iterable<Alumno> getAll() {
		LOGGER.info("...generando un Get All!");
		LOGGER.info(context.toString());
		Iterable<Alumno> alumnos = Globales.daoAlumno.getAll();
		return alumnos;
	}
	
	@GET
	@Path("/{id}")
	public Alumno getById(@PathParam("id") Integer id) {
		if (id != null) {
			LOGGER.info("...generando Alumnos Get_By_Id - id:" + id);
			Alumno alumnos = Globales.daoAlumno.getById(id);
			return alumnos;
		}
		LOGGER.warning("*** Atención ***...El id del Alumno que buscas es NULL!");
		return null;
	}
	
	@POST
	public Alumno insert(Alumno alumno) {
		LOGGER.info("...ejecutando POST/Insert de Alumno: " + alumno);
		Globales.daoAlumno.insert(alumno);
		return alumno;
	}
	
	@PUT
	@Path("/{id}")
	public Alumno update(@PathParam("id") Integer id, Alumno alumno) {
		LOGGER.info("...ejecutando PUT/Update: (" + id + ", " + alumno + ")");

		if (id != alumno.getId()) {
			
			LOGGER.warning("No concuerdan los id: " + id + ", " + alumno);			
			throw new WebApplicationException("No concuerdan los id", Status.BAD_REQUEST);
		}

		if (alumno.getId() == null) {
			
			LOGGER.warning("No se ha encontrado el id a modificar: " + id + ", " + alumno);			
			throw new WebApplicationException("No se ha encontrado el id a modificar", Status.NOT_FOUND);
		}

		Globales.daoAlumno.update(alumno);
		return alumno;
	}

	@DELETE
	@Path("/{id}")
	public String delete(@PathParam("id") Integer id) {
		LOGGER.info("Has Borrado/Desactivado el Profesor con id: " + id);
		Globales.daoAlumno.delete(id);
		return "{}";
	}
}
