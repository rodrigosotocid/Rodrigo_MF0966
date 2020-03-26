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
import com.rodrigo.mf0966.models.Resena;

@Path("/resenias")
@Produces("application/json")
@Consumes("application/json")
public class ResenaREST {

	private static final Logger LOGGER = Logger.getLogger(ResenaREST.class.getCanonicalName());

	@Context
	private ServletContext context;
	
	static {
		LOGGER.info("Generando JAX-RS de Reseñas");
		}
	
	@GET
	public Iterable<Resena> getAll() {
		LOGGER.info("Get All!");
		LOGGER.info(context.toString());
		Iterable<Resena> resenias = Globales.daoResena.getAll();
		return resenias;
	}
	
	@GET
	@Path("/{id}")
	public Resena getById(@PathParam("id") Integer id) {
		if (id != null) {
			Resena resenias = Globales.daoResena.getById(id);
			return resenias;
		}
		return null;
	}
	
	@POST
	public Resena insert(Resena resenia) {
		Globales.daoResena.insert(resenia);
		return resenia;
	}

	@PUT
	@Path("/{id}")
	public Resena update(@PathParam("id") Integer id, Resena resenia) {
		LOGGER.info("update(" + id + ", " + resenia + ")");

		if (id != resenia.getId()) {
			
			LOGGER.warning("No concuerdan los id: " + id + ", " + resenia);			
			throw new WebApplicationException("No concuerdan los id", Status.BAD_REQUEST);
		}

		if (resenia.getId() == null) {
			
			LOGGER.warning("No se ha encontrado el id a modificar: " + id + ", " + resenia);			
			throw new WebApplicationException("No se ha encontrado el id a modificar", Status.NOT_FOUND);
		}

		Globales.daoResena.update(resenia);
		return resenia;
	}

	@DELETE
	@Path("/{id}")
	public String delete(@PathParam("id") Integer id) {
		Globales.daoResena.delete(id);
		return "{}";
	}
}
