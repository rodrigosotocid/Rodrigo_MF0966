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
import com.rodrigo.mf0966.models.Curso;

@Path("/cursos")
@Produces("application/json")
@Consumes("application/json")
public class CursoREST {

	private static final Logger LOGGER = Logger.getLogger(CursoREST.class.getCanonicalName());

	@Context
	private ServletContext context;

	@GET
	public Iterable<Curso> getAll() {
		LOGGER.info("Get All!");
		LOGGER.info(context.toString());
		Iterable<Curso> cursos = Globales.daoCurso.getAll();
		return cursos;
	}
	
	@GET
	@Path("/{id}")
	public Curso getById(@PathParam("id") Integer id) {
		LOGGER.info("getById(" + id + ")");
		if (id != null) {
			Curso curso = Globales.daoCurso.getById(id);
			return curso;
		}
		return null;
	}

	@POST
	public Curso insert(Curso curso) {
		Globales.daoCurso.insert(curso);
		return curso;
	}

	@PUT
	@Path("/{id}")
	public Curso update(@PathParam("id") Integer id, Curso curso) {
		LOGGER.info("update(" + id + ", " + curso + ")");

		if (id != curso.getId()) {
			
			LOGGER.warning("No concuerdan los id: " + id + ", " + curso);			
			throw new WebApplicationException("No concuerdan los id", Status.BAD_REQUEST);
		}

		if (curso.getId() == null) {
			
			LOGGER.warning("No se ha encontrado el id a modificar: " + id + ", " + curso);			
			throw new WebApplicationException("No se ha encontrado el id a modificar", Status.NOT_FOUND);
		}

		Globales.daoCurso.update(curso);
		return curso;
	}

	@DELETE
	@Path("/{id}")
	public String delete(@PathParam("id") Integer id) {
		Globales.daoCurso.delete(id);
		return "{}";
	}
}
