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
import com.rodrigo.mf0966.models.Imparticion;
import com.rodrigo.mf0966.models.Resena;


public class ResenaSQL implements Dao<Resena>{

	private static final String SQL_GET_ALL = "CALL Resenia_GET_All()";
	private static final String SQL_GET_ID = "CALL Resenia_GET_ById(?)";
	private static final String SQL_INSERT = "CALL Resenia_POST_Insert(?,?,?,?,?)";
	private static final String SQL_UPDATE = "CALL Resenia_PUT_Update(?,?,?,?,?)";
	private static final String SQL_DELETE = "CALL Resenia_DELETE(?)";
	
	private static String url, usuario, password;
	private static DataSource pool;
	
	// "SINGLETON"
			private ResenaSQL(String url, String usuario, String password) {
				this.url = url;
				this.usuario = usuario;
				this.password = password;
			}

			private static ResenaSQL instancia;

			/**
			 * Se usará para inicializar la instancia
			 * 
			 * @param url
			 * @param usuario
			 * @param password
			 * @return La instancia
			 */
			public static ResenaSQL getInstancia(String url, String usuario, String password) {
				// Si no existe la instancia...
				if (instancia == null) {
					// ...la creamos
					instancia = new ResenaSQL(url, usuario, password);
					// Si existe la instancia, pero sus valores no concuerdan...
				} else if (!instancia.url.equals(url) || !instancia.usuario.equals(usuario)
						|| !instancia.password.contentEquals(password)) {
					// ...lanzar un error
					throw new RepositoriosException("No se pueden cambiar los valores de la instancia una vez inicializada");
				}

				// Devolver la instancia recién creada o la existente (cuyos datos coinciden con
				// los que tiene)
				return instancia;
			}

			/**
			 * Se usará para recuperar la instancia ya existente
			 * 
			 * @return devuelve la instancia ya existente
			 */
			public static ResenaSQL getInstancia() {
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
			public static ResenaSQL getInstancia(String entorno) {
				InitialContext initCtx;
				try {
					initCtx = new InitialContext();

					Context envCtx = (Context) initCtx.lookup("java:comp/env");
					DataSource dataSource = (DataSource) envCtx.lookup(entorno);

					ResenaSQL.pool = dataSource;

					if(instancia == null) {
						instancia = new ResenaSQL(null, null, null);
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
			public Iterable<Resena> getAll() {
				try (Connection con = getConexion()) {
					try (CallableStatement s = con.prepareCall(SQL_GET_ALL)) {
						try (ResultSet rs = s.executeQuery()){
							
							ArrayList<Resena> resenias = new ArrayList<>();
							
							//Imparticion imparticion;
							Alumno alumno;
							Resena resena;

							while(rs.next()) {
								
								//imparticion =  new Imparticion(rs.getInt("codigo"));
								alumno = new Alumno(rs.getInt("codigo"));
								
								resena = new Resena(rs.getInt("codigo"), new Imparticion(rs.getInt("codigo")), alumno, rs.getString("resena"), rs.getTimestamp("fResena"));
								
								resenias.add(resena);
							}
							
							return resenias;
							
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
			public Resena getById(Integer id) {
				try (Connection con = getConexion()) {
					try (PreparedStatement ps = con.prepareStatement(SQL_GET_ID)) {
						
						ps.setInt(1, id);

						try (ResultSet rs = ps.executeQuery()) {

							Imparticion imparticion;
							Alumno alumno;
							Resena resena = null;

							if (rs.next()) {
								imparticion =  new Imparticion(rs.getInt("codigo"));
								alumno = new Alumno(rs.getInt("codigo"));
								
								resena = new Resena(rs.getInt("codigo"), imparticion, alumno, rs.getString("resena"), rs.getTimestamp("fResena"));
							}

							return resena;
							
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
			public Integer insert(Resena objeto) {
				throw new UnsupportedOperationException("NO ESTA IMPLEMENTADO");
			}

			@Override
			public void update(Resena objeto) {
				throw new UnsupportedOperationException("NO ESTA IMPLEMENTADO");
			}

			@Override
			public void delete(Integer id) {
				throw new UnsupportedOperationException("NO ESTA IMPLEMENTADO");
			}
}
