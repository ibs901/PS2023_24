package es.unican.ps.seguros.presentationLayer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.fest.swing.fixture.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import es.unican.ps.seguros.businessLayer.IGestionClientes;
import es.unican.ps.seguros.businessLayer.IGestionSeguros;
import es.unican.ps.seguros.businessLayer.IInfoSeguros;
import es.unican.ps.seguros.domain.Cliente;
import es.unican.ps.seguros.domain.Cobertura;
import es.unican.ps.seguros.domain.DatoNoValidoException;
import es.unican.ps.seguros.domain.Seguro;


public class VistaAgenteTest {
	
	private FrameFixture demo;
	
	@Mock
	private IGestionClientes mockClientes;
	@Mock
	private IGestionSeguros mockSeguros;
	@Mock
	private IInfoSeguros mockInfo;

	private Cliente clienteExistente;
	private Seguro seguroExistente;

	private VistaAgente gui;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this); // Creación de los mocks definidos anteriormente con @Mock
		gui = new VistaAgente(mockClientes, mockSeguros, mockInfo);
		demo = new FrameFixture(gui);
		gui.setVisible(true);	

		// Inicializacion de las variables
		clienteExistente = new Cliente("12345678S", "Andres Ortega", true);
		try {
			seguroExistente = new Seguro(75, "PLL9597", Cobertura.TODORIESGO, LocalDate.of(2022, 01, 01));
		} catch (DatoNoValidoException e) {
			e.printStackTrace();
		}
		clienteExistente.getSeguros().add(seguroExistente);
		
		// Añadimos los comportamientos correctos de los mocks
		when(mockInfo.cliente("12345678S")).thenReturn(clienteExistente);
		when(mockInfo.cliente("77777777G")).thenReturn(null);
	}

	@AfterEach
	public void tearDown() {
		demo.cleanUp();
	}

	@Test
	public void clienteTest() {

		// Comprobamos que la interfaz tiene el aspecto deseado
		demo.button("btnBuscar").requireText("Buscar");

		// Probar caso valido DNI registrado
		demo.textBox("txtDNICliente").setText("12345678S");
		demo.button("btnBuscar").click();
		demo.textBox("txtNombreCliente").requireText("Andres Ortega");
		assertTrue(demo.list("listSeguros").valueAt(0).equals("PLL9597 TODORIESGO")); // Fallaba ya que listSeguros no tenia un setName
		demo.textBox("txtTotalCliente").requireText("675.0"); // Fallaba tenia un setText al nombre del cliente 
		// Ademas hay un problema en el segundo else if de precio que se utiliza mal la estructura añadiendo un ; al final


		// Probar caso DNI no existente
		demo.textBox("txtDNICliente").setText("77777777G");
		demo.button("btnBuscar").click();
		demo.textBox("txtNombreCliente").requireText("DNI No Valido"); // La tilde daba un problema se ha decidido quitarla
		assertThrows(IndexOutOfBoundsException.class, () -> demo.list("listSeguros").valueAt(0));
		demo.textBox("txtTotalCliente").requireText("");


		// Sleep para visualizar como se realiza el test
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}