package es.unican.ps.seguros.businessLayer;


import es.unican.ps.seguros.domain.Cliente;

/**
 * Interfaz de negocio para gestionar los 
 * clientes de la empresa de seguros
 */
public interface IGestionClientes {
	
	/**
	 * Añade un nuevo cliente
	 * @param c Cliente que desea añadir
	 * @return El cliente añadido
	 * 		   null si no se añade porque ya existe
	 */
	public Cliente nuevoCliente(Cliente c);
	
	/**
	 * Elimina el cliente cuyo dni se pasa como parámetro
	 * @param dni DNI del cliente que se quiere eliminar
	 * @return El cliente eliminado
	 * 		   null si no se elimina porque no se encuentra 
	 * @throws OperacionNoValida si el cliente existe 
	 *         pero tiene seguros a su nombre
	 */
	public Cliente bajaCliente(String dni) throws OperacionNoValida;
		
	
}
