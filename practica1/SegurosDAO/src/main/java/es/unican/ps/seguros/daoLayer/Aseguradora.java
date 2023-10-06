package es.unican.ps.seguros.daoLayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import es.unican.ps.seguros.domain.Cliente;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "aseguradora")
public class Aseguradora {
	
	// Reemplazar con el Path dónde almacenéis el fichero ayuntamiento.xml
	public static String DATA_FILE = "C:\\Temp\\aseguradora.xml";

	@XmlElement(required = true, name="cliente")
	private List<Cliente> clientes;

	/**
	 * Gets the value of the contribuyente property.
	 */
	public List<Cliente> getClientes() {
		if (clientes == null) {
			clientes = new ArrayList<Cliente>();
		}
		return this.clientes;
	}
	

	/**
	 * @param contribuyente the contribuyente to set
	 */
	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}


	public static void flush(Aseguradora aseguradora) {
		try {
			JAXBContext jaxbctx = JAXBContext.newInstance(Aseguradora.class);
			// Volcar la ifnoramciÃ›n a un fichero
			Marshaller marshaller = jaxbctx.createMarshaller();
			marshaller.marshal(aseguradora, new File(DATA_FILE));
		} catch (JAXBException e) {
			System.out.println("No se puede volcar la informacion al fichero");
			System.exit(0);
		}
	}

	public static Aseguradora creaAseguradora() {
		JAXBContext jaxbctx;
		try {
			jaxbctx = JAXBContext.newInstance(Aseguradora.class);
			// Procesamos el documento (Unmarshall)
			javax.xml.bind.Unmarshaller unmarshaller = jaxbctx
					.createUnmarshaller();
			return (Aseguradora) unmarshaller.unmarshal(new File(DATA_FILE));

		} catch (JAXBException j) {
			System.out.println("Error del JAXB");
			System.out.println(j);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return null;
	}
}
