package es.unican.ps.seguros.daoLayer;

import java.util.List;

import es.unican.ps.seguros.domain.Cliente;

/**
 * Interfaz DAO para los vehículos
 */
public interface IClientesDAO  {
	
	/** 
	 * Persite un nuevo Cliente
	 * @param c Cliente a añadir
	 * @return El cliente una vez añadido
	 *         null si no se puede añadir
	 */
	public Cliente creaCliente(Cliente c);
	
	/**
	 * Retorna el cliente cuyo dni se pasa 
	 * como parámetro
	 * @param dni
	 * @return El cliente
	 * 		   null si no se encuentra
	 */
	public Cliente cliente(String dni);
	
	/**
	 * Actualiza el estado del cliente que se pasa
	 * como parámetro
	 * @param nuevo Nuevo estado del cliente
	 * @return El cliente actualizado
	 */
	public Cliente actualizaCliente(Cliente nuevo);
	
	/**
	 * Elimina el cliente cuyo dni se pasa
	 * como parámetro
	 * @param dni
	 * @return El cliente eliminado
	 *         null si no se encuentra el cliente
	 */
	public Cliente eliminaCliente(String dni);
	
	/**
	 * Retorna la lista completa de clientes
	 * @return La lista de clientes
	 *         Lista vacía si no hay ninguno
	 */
	public List<Cliente> clientes();
	
}
