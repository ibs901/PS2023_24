package es.unican.ps.seguros.businessLayer;

import es.unican.ps.seguros.domain.Seguro;

/**
 * Interfaz de negocio para gestionar los
 * seguros de la empresa de seguros
 */
public interface IGestionSeguros {
	
	/**
	 * Añade un nuevo seguro al cliente cuyo dni se pasa
	 * como parámetro.
	 * @param s Seguro que desea añadir
	 * @param dni DNI del cliente
	 * @return El seguro añadido
	 * 		   null si no se añade porque no se encuentra el cliente
	 * @throws OperacionNoValida si el seguro ya existe
	 */
	public Seguro nuevoSeguro(Seguro s, String dni) throws OperacionNoValida;
	
	/**
	 * Elimina el seguro cuya matrícula asociada se pasa como parámetro y 
	 * que pertenece al cliente cuyo dni se pasa como parámetro
	 * @param matrícula Identificador del seguro a eliminar
	 * @param dni DNI del propietario del vehículo
 	 * @return El seguro eliminado
 	 *         null si el seguro o el cliente no existen
 	 * @throws OperacionNoValida si el seguro no pertenece al dni indicado
	 */
	public Seguro bajaSeguro(String matricula, String dni) throws OperacionNoValida;

}
