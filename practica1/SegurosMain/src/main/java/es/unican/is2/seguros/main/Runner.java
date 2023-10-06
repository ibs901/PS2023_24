package es.unican.is2.seguros.main;

import es.unican.ps.seguros.businessLayer.GestionSeguros;
import es.unican.ps.seguros.daoLayer.ClientesDAO;
import es.unican.ps.seguros.daoLayer.IClientesDAO;
import es.unican.ps.seguros.daoLayer.ISegurosDAO;
import es.unican.ps.seguros.daoLayer.SegurosDAO;
import es.unican.ps.seguros.presentationLayer.VistaAgente;

public class Runner {

	public static void main(String[] args) {
		IClientesDAO daoClientes = new ClientesDAO();
		ISegurosDAO daoSeguros = new SegurosDAO();
		GestionSeguros negocio = new GestionSeguros(daoClientes, daoSeguros);
		VistaAgente vista = new VistaAgente(negocio, negocio, negocio);
		vista.setVisible(true);

	}

}
