package com.rodrigo.mf0966.repository;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.rodrigo.mf0966.repository.RepositoriosException;
import com.rodrigo.mf0966.repository.AccesoDatosException;
import com.rodrigo.mf0966.models.Alumno;
import com.rodrigo.mf0966.models.Cliente;
import com.rodrigo.mf0966.models.Curso;
import com.rodrigo.mf0966.models.Profesor;


public class CursoSQL implements Dao<Curso>{

	private static final String SQL_GET_ALL = "CALL cursoAll()";
	private static final String SQL_GET_ID = "CALL cursoById2(?)";
	private static final String SQL_INSERT = "CALL cursoInsert(?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SQL_UPDATE = "CALL cursoUpdate(?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SQL_DELETE = "CALL cursoDelete(?)";

	private static String url, usuario, password;
	private static DataSource pool;
	
	// "SINGLETON"
		private CursoSQL(String url, String usuario, String password) {
			this.url = url;
			this.usuario = usuario;
			this.password = password;
		}

		private static CursoSQL instancia;

		public static CursoSQL getInstancia(String url, String usuario, String password) {
			// Si no existe la instancia...
			if (instancia == null) {
				// ...la creamos
				instancia = new CursoSQL(url, usuario, password);
				// Si existe la instancia, pero sus valores no concuerdan...
			} else if (!instancia.url.equals(url) || !instancia.usuario.equals(usuario)
					|| !instancia.password.contentEquals(password)) {
				// ...lanzar un error
				throw new RepositoriosException("No se pueden cambiar los valores de la instancia una vez inicializada");
			}

			return instancia;
		}

		/**
		 * Se usará para recuperar la instancia ya existente
		 * 
		 * @return devuelve la instancia ya existente
		 */
		public static CursoSQL getInstancia() {
			// Si no existe la instancia...
			if (instancia == null) {
				// ...no se puede obtener porque no sabemos los datos de URL, usuario y password
				throw new RepositoriosException("Necesito que me pases URL, usuario y password");
			}

			// Si ya existe, se devuelve
			return instancia;
		}

		/**
		 * Usaremos un pool de conexiones determinado
		 * 
		 * @return devuelve la instancia del pool de conexiones
		 */
		public static CursoSQL getInstancia(String entorno) {
			InitialContext initCtx;
			try {
				initCtx = new InitialContext();

				Context envCtx = (Context) initCtx.lookup("java:comp/env");
				DataSource dataSource = (DataSource) envCtx.lookup(entorno);

				CursoSQL.pool = dataSource;

				if (instancia == null) {
					instancia = new CursoSQL(null, null, null);
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
		public Iterable<Curso> getAll() {
			try (Connection con = getConexion()) {
				try (CallableStatement s = con.prepareCall(SQL_GET_ALL)) {
					try (ResultSet rs = s.executeQuery()) {
						
						ArrayList<Curso> cursos = new ArrayList<>();

						Curso curso;
						Cliente cliente;
						Profesor profesor;

						while (rs.next()) {
						
							cliente = new Cliente(
									rs.getInt("codigo"),rs.getString("nombre"),	rs.getString("email"),
									rs.getInt("telefono"), rs.getString("direccion"),rs.getString("poblacion"),
									rs.getInt("codigopostal"),rs.getString("identificador"), rs.getInt("activo"));
							
							profesor = new Profesor(
									rs.getInt("codigo"), rs.getLong("NSS"), rs.getString("nombre"),	
									rs.getString("apellidos"), rs.getTimestamp("fNacimiento"), rs.getString("DNI"), 
									rs.getString("direccion"),	rs.getString("poblacion"), rs.getInt("codigopostal"), 
									rs.getInt("telefono"),rs.getString("email"), rs.getInt("activo"));
							
							curso = new Curso(
									rs.getInt("codigo"), rs.getString("nombre"), rs.getString("identificador"), 
									rs.getTimestamp("fInicio"), rs.getTimestamp("fFin"), rs.getInt("nHoras"),
									rs.getString("temario"), rs.getInt("activo"), cliente, 
									rs.getDouble("precio"), profesor);

							cursos.add(curso);
						}

						return cursos;
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
		public Curso getById(Integer id) {
			try (Connection con = getConexion()) {
				try (PreparedStatement ps = con.prepareStatement(SQL_GET_ID)) {
					ps.setInt(1, id);

					try (ResultSet rs = ps.executeQuery()) {
						
						Cliente cliente;
						Profesor profesor;
						Curso curso = null;

						if (rs.next()) {
							cliente = new Cliente(
									rs.getInt("codigo"),rs.getString("nombre"),	rs.getString("email"),
									rs.getInt("telefono"), rs.getString("direccion"),rs.getString("poblacion"),
									rs.getInt("codigopostal"),rs.getString("identificador"), rs.getInt("activo"));
							
							profesor = new Profesor(
									rs.getInt("codigo"), rs.getLong("NSS"), rs.getString("nombre"),	
									rs.getString("apellidos"), rs.getTimestamp("fNacimiento"), rs.getString("DNI"), 
									rs.getString("direccion"),	rs.getString("poblacion"), rs.getInt("codigopostal"), 
									rs.getInt("telefono"),rs.getString("email"), rs.getInt("activo"));
							
							curso = new Curso(
									rs.getInt("codigo"), rs.getString("nombre"), rs.getString("identificador"), 
									rs.getTimestamp("fInicio"), rs.getTimestamp("fFin"), rs.getInt("nHoras"),
									rs.getString("temario"), rs.getInt("activo"), cliente, 
									rs.getDouble("precio"), profesor);
						}

						return curso;
						
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
		public Integer insert(Curso curso) {
			try (Connection con = getConexion()) {
				try (CallableStatement s = con.prepareCall(SQL_INSERT)) {

					s.setInt(1, curso.getId());
					s.setString(2, curso.getNombre());
					s.setString(3, curso.getIdentificador());
					s.setTimestamp(4, new Timestamp(curso.getfInicio().getTime()));
					s.setTimestamp(5, new Timestamp(curso.getfFin().getTime()));
					s.setInt(6, curso.getnHoras());
					s.setString(7, curso.getTemario());
					s.setInt(8, curso.getActivo());
					s.setInt(9, curso.getCliente().getId());
					s.setDouble(10, curso.getPrecio());
					s.setInt(11, curso.getProfesor().getId());

					s.registerOutParameter(12, java.sql.Types.INTEGER);

					int numeroRegistrosModificados = s.executeUpdate();

					if (numeroRegistrosModificados != 1) {
						throw new RepositoriosException("Número de registros modificados: " + numeroRegistrosModificados);
					}

					return s.getInt(12);

				} catch (SQLException e) {
					throw new RepositoriosException("Error al crear la sentencia (Insert)", e);
				}
			} catch (SQLException e) {
				throw new RepositoriosException("Error al conectar", e);
			}
		}

		@Override
		public void update(Curso curso) {
			try (Connection con = getConexion()) {
				try (CallableStatement s = con.prepareCall(SQL_UPDATE)) {

					s.setInt(1, curso.getId());
					s.setString(2, curso.getNombre());
					s.setString(3, curso.getIdentificador());
					s.setTimestamp(4, new Timestamp(curso.getfInicio().getTime()));
					s.setTimestamp(5, new Timestamp(curso.getfFin().getTime()));
					s.setInt(6, curso.getnHoras());
					s.setString(7, curso.getTemario());
					s.setInt(8, curso.getActivo());
					s.setInt(9, curso.getCliente().getId());
					s.setDouble(10, curso.getPrecio());
					s.setInt(11, curso.getProfesor().getId());

					int numeroRegistrosModificados = s.executeUpdate();

					if (numeroRegistrosModificados != 1) {
						throw new AccesoDatosException("Se ha hecho más o menos de una update");
					}
				}
			} catch (Exception e) {
				throw new AccesoDatosException("Error al modificar el curso", e);
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
				throw new AccesoDatosException("Error al borrar el Curso", e);
			}
			// throw new UnsupportedOperationException("NO ESTA IMPLEMENTADO");
		}
}
