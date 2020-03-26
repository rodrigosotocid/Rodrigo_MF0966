package com.rodrigo.mf0966.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.rodrigo.mf0966.models.Cliente;

public class ClienteSQL implements Dao<Cliente> {
	
	private static final String SQL_GET_ALL = "CALL cliente_GET_All()";
	private static final String SQL_GET_ID = "CALL cliente_GET_ById(?)";
	private static final String SQL_INSERT = "CALL cliente_POST_Insert(?,?,?,?,?,?,?,?)";
	private static final String SQL_UPDATE = "CALL cliente_PUT_Update(?,?,?,?,?,?,?,?)";
	private static final String SQL_DELETE = "CALL cliente_DELETE(?)";

	private static String url, usuario, password;
	private static DataSource pool;
	
	// "SINGLETON"
		private ClienteSQL(String url, String usuario, String password) {
			this.url = url;
			this.usuario = usuario;
			this.password = password;
		}

		private static ClienteSQL instancia;

		public static ClienteSQL getInstancia(String url, String usuario, String password) {
			
			if (instancia == null) {
				instancia = new ClienteSQL(url, usuario, password);
				
			} else if (!instancia.url.equals(url) || !instancia.usuario.equals(usuario)
					|| !instancia.password.contentEquals(password)) {
				
				throw new RepositoriosException("No se pueden cambiar los valores de la instancia una vez inicializada");
			}

			return instancia;
		}

		public static ClienteSQL getInstancia() {
			
			if (instancia == null) {
				
				throw new RepositoriosException("Necesito que me pases URL, usuario y password");
			}

			return instancia;
		}

		public static ClienteSQL getInstancia(String entorno) {
			InitialContext initCtx;
			try {
				initCtx = new InitialContext();

				Context envCtx = (Context) initCtx.lookup("java:comp/env");
				DataSource dataSource = (DataSource) envCtx.lookup(entorno);

				ClienteSQL.pool = dataSource;

				if (instancia == null) {
					instancia = new ClienteSQL(null, null, null);
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
		public Iterable<Cliente> getAll() {
			try (Connection con = getConexion()) {
				try (CallableStatement s = con.prepareCall(SQL_GET_ALL)) {
					try (ResultSet rs = s.executeQuery()) {
						
						ArrayList<Cliente> clientes = new ArrayList<>();

						Cliente cliente;

						while (rs.next()) {
						
							cliente = new Cliente(
									rs.getInt("codigo"),rs.getString("nombre"), rs.getString("email"),
									rs.getInt("telefono"), rs.getString("direccion"),rs.getString("poblacion"),
									rs.getInt("codigopostal"),rs.getString("identificador"), rs.getInt("activo")
									);
							
							clientes.add(cliente);
						}
						return clientes;
						
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
		public Cliente getById(Integer id) {
			try (Connection con = getConexion()) {
				try (CallableStatement s = con.prepareCall(SQL_GET_ID)) {
					
					s.setInt(1, id);
					
					try (ResultSet rs = s.executeQuery()) {
						
						Cliente cliente = null;

						if (rs.next()) {
							cliente = new Cliente(
									rs.getInt("codigo"),rs.getString("nombre"),
									rs.getString("email"),rs.getInt("telefono"),
									rs.getString("direccion"),rs.getString("poblacion"),
									rs.getInt("codigopostal"),rs.getString("identificador"),
									rs.getInt("activo"));
						}
						
						return cliente;
						
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
		public Integer insert(Cliente cliente) {
			try (Connection con = getConexion()){
				try (CallableStatement s = con.prepareCall(SQL_INSERT)){
					
					s.setString(1, cliente.getNombre());
					s.setString(2, cliente.getEmail());
					s.setInt(3, cliente.getTelefono());
					s.setString(4, cliente.getDireccion());
					s.setString(5, cliente.getPoblacion());
					s.setInt(6, cliente.getCodigoPostal());
					s.setString(7, cliente.getIdentificador());
					
					s.registerOutParameter(8, java.sql.Types.INTEGER);
					
					int numeroRegistrosModificados = s.executeUpdate();				
					
					if (numeroRegistrosModificados != 1) {
						throw new RepositoriosException("Número de registros modificados: " + numeroRegistrosModificados);
					}
					
					return s.getInt(8);
					
				} catch (SQLException e) {
					throw new RepositoriosException("Error al crear la sentencia (Insert)", e);
				}
			} catch (SQLException e) {
				throw new RepositoriosException("Error al conectar", e);
			}
		}

		@Override
		public void update(Cliente cliente) {
			try (Connection con = getConexion()) {
				try (CallableStatement s = con.prepareCall(SQL_UPDATE)) {

					s.setInt(1, cliente.getId());
					s.setString(2, cliente.getNombre());
					s.setString(3, cliente.getEmail());
					s.setInt(4, cliente.getTelefono());
					s.setString(5, cliente.getDireccion());
					s.setString(6, cliente.getPoblacion());
					s.setInt(7, cliente.getCodigoPostal());
					s.setString(8, cliente.getIdentificador());

					int numeroRegistrosModificados = s.executeUpdate();

					if (numeroRegistrosModificados != 1) {
						throw new AccesoDatosException("Se ha hecho más o menos de una update");
					}
				}
			} catch (Exception e) {
				throw new AccesoDatosException("Error al modificar el cliente", e);
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
				throw new AccesoDatosException("Error: Cliente no borrado", e);
			}
		}

}
