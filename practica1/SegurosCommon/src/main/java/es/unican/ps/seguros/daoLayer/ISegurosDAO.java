package es.unican.ps.seguros.daoLayer;

import java.util.List;

import es.unican.ps.seguros.domain.Seguro;

/**
 * Interfaz DAO para los seguros
 */
public interface ISegurosDAO  {
	
	/** 
	 * Persite un nuevo seguro
	 * @param v Seguro a añadir
	 * @return El seguro una vez añadido
	 *         null si no se añade el seguro
	 */
	public Seguro creaSeguro(Seguro v);
	
	/**
	 * Elimina el seguro cuya matrícula se pasa
	 * como parámetro
	 * @param matricula
	 * @return El seguro eliminado
	 *         null si no se encuentra el seguro
	 */
	public Seguro eliminaSeguro(String matricula);
	
	/**
	 * Actualiza el estado del seguro que se pasa
	 * como parámetro
	 * @param nuevo Nuevo estado del seguro
	 * @return El seguro actualizado
	 */
	public Seguro actualizaSeguro(Seguro nuevo);
	
	/**
	 * Retorna el seguro cuya matrícula asociada
	 * se pasa como parámetro
	 * @param matricula
	 * @return El seguro
	 * 		   null si no se encuentra
	 */
	public Seguro seguro(String matricula);
	
	/**
	 * Retorna la lista completa de seguros
	 * @return La lista de seguros
	 *         Lista vacía si no hay ninguno
	 */
	public List<Seguro> seguros();
}
