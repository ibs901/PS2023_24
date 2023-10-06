package es.unican.ps.seguros.daoLayer;


import java.util.List;

import es.unican.ps.seguros.domain.Cliente;

public class ClientesDAO implements IClientesDAO {
	
	private Aseguradora aseguradora;
	
	public ClientesDAO() {
		aseguradora = Aseguradora.creaAseguradora();
	}

	public Cliente creaCliente(Cliente c) {
		aseguradora = Aseguradora.creaAseguradora();
		if (aseguradora.getClientes().contains(c))
			return null;
		aseguradora.getClientes().add(c);
		Aseguradora.flush(aseguradora);
		return cliente(c.getDni());
	}

	public Cliente cliente(String dni) {
		aseguradora = Aseguradora.creaAseguradora();
		for (Cliente c: aseguradora.getClientes()) {
			if (c.getDni().equals(dni)) {
				return c;
			}
		}
		return null;
	}


	public Cliente actualizaCliente(Cliente nuevo) {
		aseguradora = Aseguradora.creaAseguradora();
		if (aseguradora.getClientes().contains(nuevo)) {
			aseguradora.getClientes().remove(nuevo);
			aseguradora.getClientes().add(nuevo);
			Aseguradora.flush(aseguradora);
			return nuevo;
		}
		return null;
	}

	
	public Cliente eliminaCliente(String dni) {
		aseguradora = Aseguradora.creaAseguradora();
		Cliente c = cliente(dni);
		if (c!=null) {
			aseguradora.getClientes().remove(c);
			Aseguradora.flush(aseguradora);
			return c;
		}
		return null;
	}

	
	public List<Cliente> clientes() {
		aseguradora = Aseguradora.creaAseguradora();
		return aseguradora.getClientes();
	}

}
