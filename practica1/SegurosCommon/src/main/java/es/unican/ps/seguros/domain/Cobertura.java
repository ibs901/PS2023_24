package es.unican.ps.seguros.domain;

import javax.xml.bind.annotation.XmlEnum;
/**
 * Tipo de cobertura proporcionada por un seguro
 */
@XmlEnum
public enum Cobertura {
	
	TERCEROS(400), TODORIESGO(1000), TERCEROSLUNAS(600);
	
	public final double precioBase;

    private Cobertura(double precioBase) {
        this.precioBase= precioBase;
    }
    
    public double getPrecioBase() {
    	return precioBase;
    }
    

}
