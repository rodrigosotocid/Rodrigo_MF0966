package com.rodrigo.mf0966.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.rodrigo.mf0966.repository.RepositoriosException;
import com.rodrigo.mf0966.models.Profesor;

public class ProfesorSQL implements Dao<Profesor>{
	
	private static final String SQL_GET_ALL = "CALL profesor_GET_All()";
	private static final String SQL_GET_ID = "CALL profesor_GET_ById(?)";
	private static final String SQL_INSERT = "CALL profesor_POST_Insert(?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SQL_UPDATE = "CALL profesor_PUT_Update(?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SQL_DELETE = "CALL profesor_DELETE(?)";

	private static String url, usuario, password;
	private static DataSource pool;
	
	// "SINGLETON"
		private ProfesorSQL(String url, String usuario, String password) {
			this.url = url;
			this.usuario = usuario;
			this.password = password;
		}

		private static ProfesorSQL instancia;

		public static ProfesorSQL getInstancia(String url, String usuario, String password) {
			
			if (instancia == null) {
				instancia = new ProfesorSQL(url, usuario, password);
				
			} else if (!instancia.url.equals(url) || !instancia.usuario.equals(usuario)
					|| !instancia.password.contentEquals(password)) {
				
				throw new RepositoriosException("No se pueden cambiar los valores de la instancia una vez inicializada");
			}

			return instancia;
		}

		public static ProfesorSQL getInstancia() {
			
			if (instancia == null) {
				
				throw new RepositoriosException("Necesito que me pases URL, usuario y password");
			}

			return instancia;
		}

		public static ProfesorSQL getInstancia(String entorno) {
			InitialContext initCtx;
			try {
				initCtx = new InitialContext();

				Context envCtx = (Context) initCtx.lookup("java:comp/env");
				DataSource dataSource = (DataSource) envCtx.lookup(entorno);

				ProfesorSQL.pool = dataSource;

				if (instancia == null) {
					instancia = new ProfesorSQL(null, null, null);
				}

				return instancia;
			} catch (NamingException e) {
				throw new RepositoriosException("No se ha podido conectar al Pool de conexiones " + entorno);
			}
		}
		// FIN "SINGLETON"

		private Connection getConexion() {
			try {
				if (pool == null) {
					new com.mysql.cj.jdbc.Driver();
					return DriverManager.getConnection(url, usuario, password);
				} else {
					return pool.getConnection();
				}
			} catch (SQLException e) {
				System.err
						.println("IPARTEK: Error de conexión a la base de datos: " + url + ":" + usuario + ":" + password);
				e.printStackTrace();

				throw new RepositoriosException("No se ha podido conectar a la base de datos", e);
			}
		}

		@Override
		public Iterable<Profesor> getAll() {
			try (Connection con = getConexion()) {
				try (CallableStatement s = con.prepareCall(SQL_GET_ALL)) {
					try (ResultSet rs = s.executeQuery()) {
						
						ArrayList<Profesor> profesores = new ArrayList<>();

						Profesor profesor;

						while (rs.next()) {
						
							profesor = new Profesor(
									rs.getInt("codigo"), rs.getLong("NSS"), rs.getString("nombre"),	
									rs.getString("apellidos"), rs.getTimestamp("fNacimiento"), rs.getString("DNI"), 
									rs.getString("direccion"),	rs.getString("poblacion"), rs.getInt("codigopostal"), 
									rs.getInt("telefono"),rs.getString("email"), rs.getInt("activo")
									);
							
							profesores.add(profesor);
						}
						return profesores;
						
					} catch (SQLException e) {
						throw new RepositoriosException("Error al acceder a los registros", e);
					}
				} catch (SQLException e) {
					throw new RepositoriosException("Error al crear la sentencia", e);
				}
			} catch (SQLException e) {
				throw new RepositoriosException("Error al conectar", e);
			}
		}

		@Override
		public Profesor getById(Integer id) {
			try (Connection con = getConexion()) {
				try (CallableStatement s = con.prepareCall(SQL_GET_ID)) {
					
					s.setInt(1, id);
					
					try (ResultSet rs = s.executeQuery()) {
						
						Profesor profesor = null;

						if (rs.next()) {
							profesor = new Profesor(
									rs.getInt("codigo"), rs.getLong("NSS"), rs.getString("nombre"),	
									rs.getString("apellidos"), rs.getTimestamp("fNacimiento"), rs.getString("DNI"), 
									rs.getString("direccion"),	rs.getString("poblacion"), rs.getInt("codigopostal"), 
									rs.getInt("telefono"),rs.getString("email"), rs.getInt("activo")
									);
						}
						
						System.out.println("Profesor con id: " + profesor.getId());
						return profesor;
						
					} catch (SQLException e) {
						throw new RepositoriosException("Error al acceder a los registros", e);
					}
				} catch (SQLException e) {
					throw new RepositoriosException("Error al crear la sentencia", e);
				}
			} catch (SQLException e) {
				throw new RepositoriosException("Error al conectar", e);
			}
		}

		@Override
		public Integer insert(Profesor profesor) {
			try (Connection con = getConexion()){
				try (CallableStatement s = con.prepareCall(SQL_INSERT)){
					
					s.setLong(1, profesor.getNss());
					s.setString(2, profesor.getNombre());
					s.setString(3, profesor.getApellidos());
					s.setTimestamp(4, new Timestamp(profesor.getFechaNacimiento().getTime()));
					s.setString(5, profesor.getDni());
					s.setString(6, profesor.getDireccion());
					s.setString(7, profesor.getPoblacion());
					s.setInt(8, profesor.getCodigoPostal());
					s.setInt(9, profesor.getTelefono());
					s.setString(10, profesor.getEmail());
					
					s.registerOutParameter(11, java.sql.Types.INTEGER);
					
					int numeroRegistrosModificados = s.executeUpdate();				
					
					if (numeroRegistrosModificados != 1) {
						throw new RepositoriosException("Número de registros modificados: " + numeroRegistrosModificados);
					}
					
					return s.getInt(11);
					
				} catch (SQLException e) {
					throw new RepositoriosException("Error al crear la sentencia (Insert)", e);
				}
			} catch (SQLException e) {
				throw new RepositoriosException("Error al conectar", e);
			}
		}

		@Override
		public void update(Profesor profesor) {
			try (Connection con = getConexion()) {
				try (CallableStatement s = con.prepareCall(SQL_UPDATE)) {

					s.setLong(1, profesor.getId());
					s.setLong(2, profesor.getNss());
					s.setString(3, profesor.getNombre());
					s.setString(4, profesor.getApellidos());
					s.setTimestamp(5, new Timestamp(profesor.getFechaNacimiento().getTime()));
					s.setString(6, profesor.getDni());
					s.setString(7, profesor.getDireccion());
					s.setString(8, profesor.getPoblacion());
					s.setInt(9, profesor.getCodigoPostal());
					s.setInt(10, profesor.getTelefono());
					s.setString(11, profesor.getEmail());

					int numeroRegistrosModificados = s.executeUpdate();

					if (numeroRegistrosModificados != 1) {
						throw new AccesoDatosException("Se ha hecho más o menos de una update");
					}
				}
			} catch (Exception e) {
				throw new AccesoDatosException("Error al modificar el Profesor", e);
			}
		}

		@Override
		public void delete(Integer id) {
			try (Connection con = getConexion()) {
				try (CallableStatement s = con.prepareCall(SQL_DELETE)) {

					s.setLong(1, id);

					int numeroRegistrosModificados = s.executeUpdate();

					if (numeroRegistrosModificados != 1) {
						throw new AccesoDatosException("Se ha hecho más o menos de una delete");
					}
				}
			} catch (SQLException e) {
				throw new AccesoDatosException("Error: Profesor no borrado", e);
			}
		}

}
