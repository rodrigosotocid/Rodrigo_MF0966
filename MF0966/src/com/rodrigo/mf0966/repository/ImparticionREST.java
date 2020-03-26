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
import com.rodrigo.mf0966.models.Imparticion;

@Path("/imparticiones")
@Produces("application/json")
@Consumes("application/json")
public class ImparticionREST {

	private static final Logger LOGGER = Logger.getLogger(ImparticionREST.class.getCanonicalName());

	@Context
	private ServletContext context;
	Dao<Imparticion> imparticionesDao;
	
	static {
		LOGGER.info("Generando JAX-RS de Imparticion");
		}

	@GET
	public Iterable<Imparticion> getAll() {
		LOGGER.info("...Realizando un Get All!");
		LOGGER.info(context.toString());
		Iterable<Imparticion> imparticiones = Globales.daoImparticion.getAll();
		//imparticionesDao = (Dao<Imparticion>) context.getAttribute("DaoImparticiones");
		return imparticiones;
	}
	
	@GET
	@Path("/{id}")
	public Imparticion getById(@PathParam("id") Integer id) {
		LOGGER.info("getById(" + id + ")");
		if (id != null) {
			Imparticion imparticion = Globales.daoImparticion.getById(id);
			return imparticion;
		}
		return null;
	}

	@POST
	public Imparticion insert(Imparticion imparticion) {
		Globales.daoImparticion.insert(imparticion);
		return imparticion;
	}

	@PUT
	@Path("/{id}")
	public Imparticion update(@PathParam("id") Integer id, Imparticion imparticion) {
		LOGGER.info("update(" + id + ", " + imparticion + ")");

		if (id != imparticion.getId()) {
			
			LOGGER.warning("No concuerdan los id: " + id + ", " + imparticion);			
			throw new WebApplicationException("No concuerdan los id", Status.BAD_REQUEST);
		}

		if (imparticion.getId() == null) {
			
			LOGGER.warning("No se ha encontrado el id a modificar: " + id + ", " + imparticion);			
			throw new WebApplicationException("No se ha encontrado el id a modificar", Status.NOT_FOUND);
		}

		Globales.daoImparticion.update(imparticion);
		return imparticion;
	}

	@DELETE
	@Path("/{id}")
	public String delete(@PathParam("id") Integer id) {
		Globales.daoImparticion.delete(id);
		return "{}";
	}
}
