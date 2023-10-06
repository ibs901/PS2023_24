package es.unican.ps.seguros.businessLayer;

import es.unican.ps.seguros.daoLayer.IClientesDAO;
import es.unican.ps.seguros.daoLayer.ISegurosDAO;
import es.unican.ps.seguros.domain.Cliente;
import es.unican.ps.seguros.domain.Seguro;

public class GestionSeguros implements IGestionClientes, IGestionSeguros, IInfoSeguros {
	
	private IClientesDAO clientes;
	private ISegurosDAO seguros;
	
	public GestionSeguros(IClientesDAO clientes, ISegurosDAO seguros) {
		this.clientes = clientes;
		this.seguros = seguros;
	}
	
	
	public Cliente nuevoCliente(Cliente c) {
		return clientes.creaCliente(c);
	}

	
	public Cliente bajaCliente(String dni) throws OperacionNoValida {
		Cliente c = clientes.cliente(dni);
		if (c == null)
			return null;
		if (c.getSeguros().size()>0)
			throw new OperacionNoValida("El cliente tiene seguros");
		return clientes.eliminaCliente(dni);	
	}
	
	public Cliente cliente(String dni) {
		return clientes.cliente(dni);
	}

	
	public Seguro nuevoSeguro(Seguro s, String dni) throws OperacionNoValida {
		
		if (seguros.seguro(s.getMatricula()) != null) {
			throw new OperacionNoValida("El seguro ya existe");
		}
		
		Cliente c = clientes.cliente(dni);
		
		if (c!=null) {
			c.getSeguros().add(s);
			clientes.actualizaCliente(c);
			return seguros.creaSeguro(s);
		}
		return null;	
	}

	
	public Seguro bajaSeguro(String matricula, String dni) throws OperacionNoValida {
		Cliente c = clientes.cliente(dni);
		Seguro s = seguros.seguro(matricula);
		if (c!=null && s!=null) {
			if (c.getSeguros().remove(s) ) {
				clientes.actualizaCliente(c);
				seguros.eliminaSeguro(matricula);
				return s;
			}
			throw new OperacionNoValida("El usuario no tiene un veh�culo con esa matr�cula");
		}
		return null;
	}

	
	public Seguro seguro(String matricula) {
		return seguros.seguro(matricula);
	}
}

