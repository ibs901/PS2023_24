package es.unican.ps.seguros.businessLayer;

@SuppressWarnings("serial")
public class OperacionNoValida extends RuntimeException {

	public OperacionNoValida(String string) {
		super(string);
	}

}
