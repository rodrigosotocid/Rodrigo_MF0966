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
import com.rodrigo.mf0966.models.Cliente;
import com.rodrigo.mf0966.models.Profesor;

@Path("/clientes")
@Produces("application/json")
@Consumes("application/json")
public class ClienteREST {
	
	private static final Logger LOGGER = Logger.getLogger(AlumnoREST.class.getCanonicalName());

	@Context
	private ServletContext context;
	
	static {
		LOGGER.info("Generando JAX-RS de Clientes");
		}
	
	@GET
	public Iterable<Cliente> getAll() {
		LOGGER.info("...ejecutando Clientes Get_All!");
		LOGGER.info(context.toString());
		Iterable<Cliente> clientes = Globales.daoCliente.getAll();
		return clientes;
	}
	
	@GET
	@Path("/{id}")
	public Cliente getById(@PathParam("id") Integer id) {
		if (id != null) {
			LOGGER.info("...ejecutando Clientes Get_By_Id - id:" + id);
			Cliente clientes = Globales.daoCliente.getById(id);
			return clientes;
		}
		LOGGER.warning("*** Atención ***...El id del Cliente que buscas es NULL!");
		return null;
	}
	
	@POST
	public Cliente insert(Cliente cliente) {
		LOGGER.info("...ejecutando POST/Insert de Cliente: " + cliente);
		Globales.daoCliente.insert(cliente);
		return cliente;
	}
	
	@PUT
	@Path("/{id}")
	public Cliente update(@PathParam("id") Integer id, Cliente cliente) {
		LOGGER.info("...ejecutando PUT/Update: (" + id + ", " + cliente + ")");

		if (id != cliente.getId()) {
			
			LOGGER.warning("No concuerdan los id: " + id + ", " + cliente);			
			throw new WebApplicationException("No concuerdan los id", Status.BAD_REQUEST);
		}

		if (cliente.getId() == null) {
			
			LOGGER.warning("No se ha encontrado el id a modificar: " + id + ", " + cliente);			
			throw new WebApplicationException("No se ha encontrado el id a modificar", Status.NOT_FOUND);
		}

		Globales.daoCliente.update(cliente);
		return cliente;
	}

	@DELETE
	@Path("/{id}")
	public String delete(@PathParam("id") Integer id) {
		LOGGER.info("Has Borrado/Desactivado el Cliente: " + id);
		Globales.daoCliente.delete(id);
		return "{}";
	}
}
