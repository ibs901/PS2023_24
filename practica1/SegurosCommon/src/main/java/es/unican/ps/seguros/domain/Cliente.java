package es.unican.ps.seguros.domain;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Clase que representa un cliente de la empresa de seguros
 * Un cliente se identifica por su dni
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Cliente")
public class Cliente {

	@XmlElement(required=true, name="seguro")
    private List<Seguro> seguros = new LinkedList<Seguro>();
    
    @XmlAttribute(required = true)
    private String nombre;
    
    @XmlAttribute(required = true)
    private String dni;
    
    @XmlAttribute(required = true)
    private boolean minusvalia;
    
    private double PORCENTAJE_MINUSVALIA = 0.75; 
    
    public Cliente(){}  
    
    public Cliente(String dni){
    	this.dni = dni;
    }  
    
    public Cliente(String dni, String nombre, boolean minusvalia){
    	this.dni = dni;
    	this.nombre = nombre;
    	this.minusvalia = minusvalia;
    }  

	/**
     * Retorna los seguros del cliente 
     */
    public List<Seguro> getSeguros() {
        if (seguros == null) {
        	seguros = new LinkedList<Seguro>();
        }
        return seguros;
    }

    /**
     * Retorna el nombre del cliente.   
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Retorna el dni del cliente.
     */
    public String getDni() {
        return dni;
    }
    
    /**
     * Indica si el cliente es minusv�lido
     */
    public boolean getMinusvalia() {
    	return minusvalia;
    }
    
    /**
     * Calcula el total a pagar por el cliente por 
     * todos los seguros a su nombre
     */
    public double totalSeguros() {
    	double total=0.0;
    	for (Seguro s: seguros) {
    		total+=s.precio();
    	}    	
    	if (minusvalia)
    		total = total*PORCENTAJE_MINUSVALIA;
    	return total;
    }


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}

	

}
