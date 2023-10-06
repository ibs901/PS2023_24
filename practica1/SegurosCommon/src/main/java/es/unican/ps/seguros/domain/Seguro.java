package es.unican.ps.seguros.domain;


import java.time.LocalDate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import es.unican.ps.seguros.utils.*;

/**
 * Clase que representa un seguro de coche.
 * Un seguro se identifica por la matr�cula
 * del coche para el que se contrata el seguro
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Seguro")
public class Seguro {
	
	private static final double PORCENTAJE_TRAMO_1 = 1.05;
	private static final double PORCENTAJE_TRAMO_2 = 1.20;
	private static final int INICIO_TRAMO_1= 90;
	private static final int FIN_TRAMO_1=110;
	private static final double DESCUENTO_PRIMER_ANHO = 0.8;
	private static final double DESCUENTO_SEGUNDO_ANHO = 0.9;
	
    
    @XmlAttribute(required = true)
    private int potencia;
    
    @XmlAttribute(required = true)
    private String matricula;
    
    @XmlAttribute(required = true)
    private Cobertura cobertura;
    
    @XmlAttribute(name="fecha", required=true)
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate fechaContratacion;
    
    public Seguro() {}
    
    public Seguro(int potencia, String matricula, Cobertura cobertura, LocalDate fechaContratacion) throws DatoNoValidoException 
    {
    	if (potencia<=0 || matricula == null || fechaContratacion.isAfter(LocalDate.now()))
    		throw new DatoNoValidoException();
    	this.potencia=potencia;
    	this.cobertura=cobertura;
    	this.fechaContratacion= fechaContratacion;
    	this.matricula=matricula;
    }

	/**
	 * Retorna la matr�cula del coche 
	 * asociado al seguro
	 */
	public String getMatricula() {
		return matricula;
	}

	/**
	 * Retorna el tipo de cobertura del seguro
	 */
	public Cobertura getCobertura() {
		return cobertura;
	}


	/**
     * Retorna la potencia del coche asociado 
     * al seguro. 
     */
    public int getPotencia() {
        return potencia;
    }
    
    
    /**
     * Retorna el precio del seguro
     */
	public double precio() {
		double precio = cobertura.getPrecioBase();
		if (potencia>=INICIO_TRAMO_1 && potencia <= FIN_TRAMO_1)
			precio = precio*PORCENTAJE_TRAMO_1;
		else if (potencia>FIN_TRAMO_1)
			precio = precio*PORCENTAJE_TRAMO_2;
		if (fechaContratacion.isBefore(LocalDate.now().minusYears(2)))
			return precio;
		else if (fechaContratacion.isBefore(LocalDate.now().minusYears(1)))
			return precio*DESCUENTO_SEGUNDO_ANHO;
		else 
			return precio*DESCUENTO_PRIMER_ANHO;		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
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
		Seguro other = (Seguro) obj;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals(other.matricula))
			return false;
		return true;
	};
}
