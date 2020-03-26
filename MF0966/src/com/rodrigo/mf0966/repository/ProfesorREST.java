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
import com.rodrigo.mf0966.models.Profesor;

@Path("/profesores")
@Produces("application/json")
@Consumes("application/json")
public class ProfesorREST {

	private static final Logger LOGGER = Logger.getLogger(ProfesorREST.class.getCanonicalName());

	@Context
	private ServletContext context;

	static {
		LOGGER.info("Generando JAX-RS de Profesores");
		}
	
	@GET
	public Iterable<Profesor> getAll() {
		LOGGER.info("Get All!");
		LOGGER.info(context.toString());
		Iterable<Profesor> profesores = Globales.daoProfesor.getAll();
		return profesores;
	}
	
	@GET
	@Path("/{id}")
	public Profesor getById(@PathParam("id") Integer id) {
		if (id != null) {
			LOGGER.info("...generando Profesor Get_By_Id - id:" + id);
			Profesor profesor = Globales.daoProfesor.getById(id);
			return profesor;
		}
		LOGGER.warning("*** Atención ***...El id del Profesor que buscas es NULL!");
		return null;
	}
	
	@POST
	public Profesor insert(Profesor profesor) {
		LOGGER.info("...ejecutando POST/Insert de Profesor: " + profesor);
		Globales.daoProfesor.insert(profesor);
		return profesor;
	}
	
	@PUT
	@Path("/{id}")
	public Profesor update(@PathParam("id") Integer id, Profesor profesor) {
		LOGGER.info("...ejecutando PUT/Update: (" + id + ", " + profesor + ")");

		if (id != profesor.getId()) {
			
			LOGGER.warning("No concuerdan los id: " + id + ", " + profesor);			
			throw new WebApplicationException("No concuerdan los id", Status.BAD_REQUEST);
		}

		if (profesor.getId() == null) {
			
			LOGGER.warning("No se ha encontrado el id a modificar: " + id + ", " + profesor);			
			throw new WebApplicationException("No se ha encontrado el id a modificar", Status.NOT_FOUND);
		}

		Globales.daoProfesor.update(profesor);
		return profesor;
	}

	@DELETE
	@Path("/{id}")
	public String delete(@PathParam("id") Integer id) {
		LOGGER.info("Has Borrado/Desactivado el Profesor con id: " + id);
		Globales.daoProfesor.delete(id);
		return "{}";
	}
}
