package com.rodrigo.mf0966.repository;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.rodrigo.mf0966.repository.RepositoriosException;
import com.rodrigo.mf0966.models.Alumno;
import com.rodrigo.mf0966.models.Cliente;
import com.rodrigo.mf0966.models.Curso;
import com.rodrigo.mf0966.models.Imparticion;
import com.rodrigo.mf0966.models.Profesor;
import com.rodrigo.mf0966.models.Resena;


public class FabricaDaoProperties implements FabricaDao {
	
	private String tipo;
	private String url, usuario, password;
	
	//SINGLETON
		private static FabricaDaoProperties instancia;
		
		/**
		 * Recoger instancia existente
		 * @return (puede valer null)
		 */
		public static FabricaDaoProperties getInstancia() {
			return instancia;
		}
		
		/**
		 * Crear una nueva instancia con un path
		 * @param pathProperties
		 * @return
		 */
		public static FabricaDaoProperties getInstancia(String pathProperties) {
			if(instancia == null) {
				instancia = new FabricaDaoProperties(pathProperties);
			}
			
			return instancia;
		}
		
		private FabricaDaoProperties(String pathProperties) {
			Properties props = new Properties();

			try {
				props.load(new FileReader(pathProperties));

				tipo = props.getProperty("tipo");
				url = props.getProperty(tipo + ".url");
				usuario = props.getProperty(tipo + ".usuario");
				password = props.getProperty(tipo + ".password");
			} catch (IOException e) {
				throw new RepositoriosException("No se ha podido leer el fichero de properties: " + pathProperties, e);
			}
		}

		@Override
		public Dao<Alumno> getAlumnoDao() {
			if(tipo == null) {
				throw new RepositoriosException("No se ha recibido ningún tipo");
			}
			
			switch (tipo) {
			case "mysql": return AlumnoSQL.getInstancia(url, usuario, password);
			default:
				throw new RepositoriosException("No reconozco el tipo " + tipo);
			}
		}

		@Override
		public Dao<Curso> getCursoDao() {
			if(tipo == null) {
				throw new RepositoriosException("No se ha recibido ningún tipo");
			}
			
			switch (tipo) {
			case "mysql": return CursoSQL.getInstancia(url, usuario, password);
			default:
				throw new RepositoriosException("No reconozco el tipo " + tipo);
			}
		}

		@Override
		public Dao<Cliente> getClienteDao() {
			if(tipo == null) {
				throw new RepositoriosException("No se ha recibido ningún tipo");
			}
			
			switch (tipo) {
			case "mysql": return ClienteSQL.getInstancia(url, usuario, password);
			default:
				throw new RepositoriosException("No reconozco el tipo " + tipo);
			}
		}

		@Override
		public Dao<Profesor> getProfesorDao() {
			if(tipo == null) {
				throw new RepositoriosException("No se ha recibido ningún tipo");
			}
			
			switch (tipo) {
			case "mysql": return ProfesorSQL.getInstancia(url, usuario, password);
			default:
				throw new RepositoriosException("No reconozco el tipo " + tipo);
			}
		}

		@Override
		public Dao<Resena> getResenaDao() {
			if(tipo == null) {
				throw new RepositoriosException("No se ha recibido ningún tipo");
			}
			
			switch (tipo) {
			case "mysql": return ResenaSQL.getInstancia(url, usuario, password);
			default:
				throw new RepositoriosException("No reconozco el tipo " + tipo);
			}
		}

		@Override
		public Dao<Imparticion> getImparticionDao() {
			if(tipo == null) {
				throw new RepositoriosException("No se ha recibido ningún tipo");
			}
			
			switch (tipo) {
			case "mysql": return ImparticionSQL.getInstancia(url, usuario, password);
			default:
				throw new RepositoriosException("No reconozco el tipo " + tipo);
			}
		}
		
		
}
