package es.unican.ps.seguros.daoLayer;

import java.util.LinkedList;
import java.util.List;

import es.unican.ps.seguros.domain.Cliente;
import es.unican.ps.seguros.domain.Seguro;

public class SegurosDAO implements ISegurosDAO {
	
	private Aseguradora aseguradora;
	
	public SegurosDAO() {
		aseguradora = Aseguradora.creaAseguradora();
	}

	// Interfaz IVehiculosDAO
	public Seguro creaSeguro(Seguro s) {	
		// Por como está implementado el almacenamineto 
		// anidado en el xml este metodo no necesita hacer nada.
		return seguro(s.getMatricula());
	}

	
	public Seguro eliminaSeguro(String matricula) {
		aseguradora = Aseguradora.creaAseguradora();
		for (Cliente c:aseguradora.getClientes()) {
			for (Seguro s: c.getSeguros()) {
				if (s.getMatricula().equals(matricula)) {
					c.getSeguros().remove(s);
					Aseguradora.flush(aseguradora);
					return s;
				}
			}
		}
		return null;
	}

	
	public Seguro actualizaSeguro(Seguro nuevo) {  
		aseguradora = Aseguradora.creaAseguradora();
		for (Cliente c:aseguradora.getClientes()) {
			if (c.getSeguros().contains(nuevo)) {
				c.getSeguros().remove(nuevo);
				c.getSeguros().add(nuevo);
				Aseguradora.flush(aseguradora);
				return nuevo;
			}
		}
		return null;
	}

	
	public Seguro seguro(String matricula) {
		aseguradora = Aseguradora.creaAseguradora();
		for (Cliente c:aseguradora.getClientes()) {
			for (Seguro s: c.getSeguros()) {
				if (s.getMatricula().equals(matricula)) {
					return s;
				}
			}
		}
		return null;
	}

	
	public List<Seguro> seguros() {
		aseguradora = Aseguradora.creaAseguradora();
		List<Seguro> seguros = new LinkedList<Seguro>();
		for (Cliente c:aseguradora.getClientes()) {
			seguros.addAll(c.getSeguros());
		}
		return seguros;
	}

	

}
