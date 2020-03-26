package com.rodrigo.mf0966.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.rodrigo.mf0966.controller.Globales;
import com.rodrigo.mf0966.repository.FabricaDao;
import com.rodrigo.mf0966.repository.FabricaDaoProperties;

@WebListener
public class InicioListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent sce) {
	}

	public void contextInitialized(ServletContextEvent sce) {

		String pathConfiguracion = sce.getServletContext().getRealPath("/WEB-INF/") + "dao.properties";

		FabricaDao fabricaDao = FabricaDaoProperties.getInstancia(pathConfiguracion);

		Globales.daoCurso = fabricaDao.getCursoDao();
		Globales.daoCliente = fabricaDao.getClienteDao();
		Globales.daoAlumno = fabricaDao.getAlumnoDao();
		Globales.daoImparticion = fabricaDao.getImparticionDao();
		Globales.daoProfesor = fabricaDao.getProfesorDao();
		Globales.daoResena = fabricaDao.getResenaDao();
	}
}
