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
import com.rodrigo.mf0966.models.Alumno;


public class AlumnoSQL implements Dao<Alumno> {
	
	private static final String SQL_GET_ALL = "CALL alumno_GET_All()";
	private static final String SQL_GET_ID = "CALL alumno_GET_ById(?)";
	private static final String SQL_INSERT = "CALL alumno_POST_Insert(?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SQL_UPDATE = "CALL alumno_PUT_Update(?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SQL_DELETE = "CALL alumno_DELETE(?)";
	
	private static String url, usuario, password;
	private static DataSource pool;
	
	// "SINGLETON"
				private AlumnoSQL(String url, String usuario, String password) {
					this.url = url;
					this.usuario = usuario;
					this.password = password;
				}

				private static AlumnoSQL instancia;

				public static AlumnoSQL getInstancia(String url, String usuario, String password) {
					
					if (instancia == null) {
						
						instancia = new AlumnoSQL(url, usuario, password);
						
					} else if (!instancia.url.equals(url) || !instancia.usuario.equals(usuario)
							|| !instancia.password.contentEquals(password)) {
						
						throw new RepositoriosException("No se pueden cambiar los valores de la instancia una vez inicializada");
					}

					return instancia;
				}

				public static AlumnoSQL getInstancia() {
					
					if (instancia == null) {
						
						throw new RepositoriosException("Necesito que me pases URL, usuario y password");
					}
					return instancia;
				}

				public static AlumnoSQL getInstancia(String entorno) {
					
					InitialContext initCtx;
					
					try {
						initCtx = new InitialContext();

						Context envCtx = (Context) initCtx.lookup("java:comp/env");
						DataSource dataSource = (DataSource) envCtx.lookup(entorno);

						AlumnoSQL.pool = dataSource;

						if(instancia == null) {
							instancia = new AlumnoSQL(null, null, null);
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
								.println("ERROR: Error de conexión a la base de datos: " + url + ":" + usuario + ":" + password);
						e.printStackTrace();

						throw new RepositoriosException("No se ha podido conectar a la base de datos", e);
					}
				}

				@Override
				public Iterable<Alumno> getAll() {
					try (Connection con = getConexion()) {
						try (CallableStatement s = con.prepareCall(SQL_GET_ALL)) {
							try (ResultSet rs = s.executeQuery()){
								
								ArrayList<Alumno> alumnos = new ArrayList<>();
								Alumno alumno;

								while(rs.next()) {
									alumno = new Alumno(
											rs.getInt("codigo"), rs.getString("nombre"), rs.getString("apellidos"),
											rs.getDate("fNacimiento"), rs.getString("direccion"),rs.getString("poblacion"),
											rs.getInt("codigopostal"),rs.getInt("telefono"), rs.getString("email"), 
											rs.getString("dni"), rs.getInt("nHermanos"), rs.getInt("activo")
											);
									
									alumnos.add(alumno);
								}
								
								return alumnos;
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
				public Alumno getById(Integer id) {
					try (Connection con = getConexion()) {
						try (PreparedStatement ps = con.prepareStatement(SQL_GET_ID)) {
							ps.setInt(1, id);

							try (ResultSet rs = ps.executeQuery()) {

								Alumno alumno = null;

								if (rs.next()) {
									alumno = new Alumno(
											rs.getInt("codigo"), rs.getString("nombre"), rs.getString("apellidos"),
											rs.getDate("fNacimiento"), rs.getString("direccion"),rs.getString("poblacion"),
											rs.getInt("codigopostal"),rs.getInt("telefono"), rs.getString("email"), 
											rs.getString("dni"), rs.getInt("nHermanos"), rs.getInt("activo")
											);
								}

								return alumno;
								
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
				public Integer insert(Alumno alumno) {
					try (Connection con = getConexion()) {
						try (CallableStatement s = con.prepareCall(SQL_INSERT)) {

							s.setString(1, alumno.getNombre());
							s.setString(2, alumno.getApellidos());
							s.setTimestamp(3, new Timestamp(alumno.getFechaNacimiento().getTime()));
							s.setString(4, alumno.getDireccion());
							s.setString(5, alumno.getPoblacion());
							s.setInt(6, alumno.getCodigoPostal());
							s.setInt(7, alumno.getTelefono());
							s.setString(8, alumno.getEmail());
							s.setString(9, alumno.getDni());
							s.setInt(10, alumno.getnHermanos());
							

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
				public void update(Alumno alumno) {
					try (Connection con = getConexion()) {
						try (CallableStatement s = con.prepareCall(SQL_UPDATE)) {

							s.setInt(1, alumno.getId());
							s.setString(2, alumno.getNombre());
							s.setString(3, alumno.getApellidos());
							s.setTimestamp(4, new Timestamp(alumno.getFechaNacimiento().getTime()));
							s.setString(5, alumno.getDireccion());
							s.setString(6, alumno.getPoblacion());
							s.setInt(7, alumno.getCodigoPostal());
							s.setInt(8, alumno.getTelefono());
							s.setString(9, alumno.getEmail());
							s.setString(10, alumno.getDni());
							s.setInt(11, alumno.getnHermanos());

							int numeroRegistrosModificados = s.executeUpdate();

							if (numeroRegistrosModificados != 1) {
								throw new AccesoDatosException("Se ha hecho más o menos de una update");
							}
						}
					} catch (Exception e) {
						throw new AccesoDatosException("Error al modificar el Alumno", e);
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
						throw new AccesoDatosException("Error al borrar el Alumno", e);
					}
				}
}
