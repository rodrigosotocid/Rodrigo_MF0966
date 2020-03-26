package com.rodrigo.mf0966.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.rodrigo.mf0966.models.Alumno;
import com.rodrigo.mf0966.models.Cliente;
import com.rodrigo.mf0966.models.Curso;
import com.rodrigo.mf0966.models.Imparticion;
import com.rodrigo.mf0966.models.Profesor;

public class ImparticionSQL implements Dao<Imparticion>{


	private static final String SQL_GET_ALL = "CALL imparticionGetAll()";
	private static final String SQL_GET_ID = "CALL imparticionGetById(?)";
	private static final String SQL_INSERT = "CALL imparticionInsert(?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SQL_UPDATE = "CALL imparticionUpdate(?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SQL_DELETE = "CALL imparticionDelete(?)";

	private static String url, usuario, password;
	private static DataSource pool;
	
	// "SINGLETON"
		private ImparticionSQL(String url, String usuario, String password) {
			this.url = url;
			this.usuario = usuario;
			this.password = password;
		}

		private static ImparticionSQL instancia;

		public static ImparticionSQL getInstancia(String url, String usuario, String password) {
			if (instancia == null) {
				instancia = new ImparticionSQL(url, usuario, password);
			} else if (!instancia.url.equals(url) || !instancia.usuario.equals(usuario)
					|| !instancia.password.contentEquals(password)) {
				throw new RepositoriosException("No se pueden cambiar los valores de la instancia una vez inicializada");
			}

			return instancia;
		}

		public static ImparticionSQL getInstancia() {
			if (instancia == null) {
				throw new RepositoriosException("Necesito que me pases URL, usuario y password");
			}

			return instancia;
		}

		public static ImparticionSQL getInstancia(String entorno) {
			InitialContext initCtx;
			try {
				initCtx = new InitialContext();

				Context envCtx = (Context) initCtx.lookup("java:comp/env");
				DataSource dataSource = (DataSource) envCtx.lookup(entorno);

				ImparticionSQL.pool = dataSource;

				if (instancia == null) {
					instancia = new ImparticionSQL(null, null, null);
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
		public Iterable<Imparticion> getAll() {
			try (Connection con = getConexion()) {
				try (CallableStatement s = con.prepareCall(SQL_GET_ALL)) {
					try (ResultSet rs = s.executeQuery()){
						
						ArrayList<Imparticion> imparticiones = new ArrayList<>();
						
						Curso curso;
						Alumno alumno;
						Imparticion imparticion;

						while(rs.next()) {
							
							curso = new Curso(rs.getInt("codigo"));							
							alumno = new Alumno(rs.getInt("codigo"));
							
							imparticion = new Imparticion(rs.getInt("codigo"), curso, alumno, rs.getTimestamp("fMatriculacion"));
							
							imparticiones.add(imparticion);
						}
						
						return imparticiones;
						
					} catch (SQLException e) {
						throw new RepositoriosException("Error al acceder a los registros", e);
					}
				} catch (SQLException e) {
					throw new RepositoriosException("Error al crear la sentencia", e);
				}
			} catch (SQLException e) {
				throw new RepositoriosException("Error al conectar", e);
			}		}

		@Override
		public Imparticion getById(Integer id) {
			try (Connection con = getConexion()) {
				try (PreparedStatement ps = con.prepareStatement(SQL_GET_ID)) {
					ps.setInt(1, id);

					try (ResultSet rs = ps.executeQuery()) {
						
						Curso curso;
						Alumno alumno;
						Imparticion imparticion = null;

						if (rs.next()) {
							curso = new Curso(rs.getInt("codigo"));							
							alumno = new Alumno(rs.getInt("codigo"));
							
							imparticion = new Imparticion(rs.getInt("codigo"), curso, alumno, rs.getTimestamp("fMatriculacion"));
						}

						return imparticion;
						
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
		public Integer insert(Imparticion objeto) {
			throw new UnsupportedOperationException("NO ESTA IMPLEMENTADO");
		}

		@Override
		public void update(Imparticion objeto) {
			throw new UnsupportedOperationException("NO ESTA IMPLEMENTADO");
		}

		@Override
		public void delete(Integer id) {
			throw new UnsupportedOperationException("NO ESTA IMPLEMENTADO");
		}

		
}
