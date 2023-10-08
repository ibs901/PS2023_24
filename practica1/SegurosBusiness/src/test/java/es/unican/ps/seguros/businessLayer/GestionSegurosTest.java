package es.unican.ps.seguros.businessLayer;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import es.unican.ps.seguros.daoLayer.IClientesDAO;
import es.unican.ps.seguros.daoLayer.ISegurosDAO;
import es.unican.ps.seguros.domain.Cliente;
import es.unican.ps.seguros.domain.Cobertura;
import es.unican.ps.seguros.domain.DatoNoValidoException;
import es.unican.ps.seguros.domain.Seguro;

class GestionSegurosTest {

	@Mock
	private IClientesDAO mockClientes;
	@Mock
	private ISegurosDAO mockSeguros;

	private GestionSeguros sut;
	private Seguro seguroExistente;
	private Seguro seguroExistenteNoAsignado;
	private Seguro seguroNoExistente; 
	private Cliente clienteExistente;
	private Cliente clienteNoExistente;
	private List<Seguro> segurosNuevo;
	private List<Seguro> segurosVacio;


	@BeforeEach
	public void inicializa() {
		MockitoAnnotations.openMocks(this); // Creación de los mocks definidos anteriormente con @Mock
		sut = new GestionSeguros(mockClientes, mockSeguros);

		// Inicializacion de las variables
		try {
			seguroExistente = new Seguro(75, "PLL9597", Cobertura.TODORIESGO, LocalDate.of(2022, 01, 01));
			seguroExistenteNoAsignado = new Seguro(105, "PLX9597", Cobertura.TODORIESGO, LocalDate.of(2005, 9, 18));
			seguroNoExistente = new Seguro(100, "2020AAA", Cobertura.TODORIESGO, LocalDate.now());
			clienteExistente = new Cliente("12345678S");
			clienteNoExistente = new Cliente("77777777G");
			segurosNuevo = new LinkedList<Seguro>();
			segurosVacio = new LinkedList<Seguro>();
			segurosNuevo.add(seguroExistente);
			segurosNuevo.add(seguroNoExistente);
			clienteExistente.getSeguros().add(seguroExistente);
		} catch(DatoNoValidoException e) {
		}

		// Añadimos los comportamientos correctos de los mocks
		when(mockSeguros.creaSeguro(seguroNoExistente)).thenReturn(seguroNoExistente);
		when(mockSeguros.creaSeguro(seguroExistente)).thenReturn(null);

		when(mockClientes.cliente("12345678S")).thenReturn(clienteExistente);
		when(mockClientes.cliente("77777777G")).thenReturn(null);

		when(mockSeguros.eliminaSeguro("PLL9597")).thenReturn(seguroExistente);
		when(mockSeguros.eliminaSeguro("2020AAA")).thenReturn(null);

		when(mockSeguros.seguro("PLL9597")).thenReturn(seguroExistente);
		when(mockSeguros.seguro("PLX9597")).thenReturn(seguroExistenteNoAsignado);
		when(mockSeguros.seguro("2020AAA")).thenReturn(null);
	}

	@Test
	void altaSeguroTest() {
		// Alta correcta
		assertTrue(sut.nuevoSeguro(seguroNoExistente, "12345678S").equals(seguroNoExistente));
		verify(mockClientes).actualizaCliente(clienteExistente);
		verify(mockSeguros).creaSeguro(seguroNoExistente);
		assertTrue(clienteExistente.getSeguros().equals(segurosNuevo));

		// Cliente no existe
		assertEquals(sut.nuevoSeguro(seguroNoExistente, "77777777G"), null);
		assertTrue(clienteNoExistente.getSeguros().equals(segurosVacio));

		// Seguro no existente
		assertThrows(OperacionNoValida.class, () -> sut.nuevoSeguro(seguroExistente, "12345678S")); // daba error hay que crear el throw
		assertTrue(clienteExistente.getSeguros().equals(segurosNuevo));
	}

	@Test
	void bajaSeguroTest() {
		// Baja correcta
		assertTrue(sut.bajaSeguro("PLL9597", "12345678S").equals(seguroExistente));
		assertTrue(clienteExistente.getSeguros().equals(segurosVacio));
		verify(mockClientes).actualizaCliente(clienteExistente);
		verify(mockSeguros).eliminaSeguro("PLL9597");

		// Seguro no existe
		assertEquals(sut.bajaSeguro("2020AAA", "12345678S"), null); // No retornaba null se añade al if
		assertTrue(clienteExistente.getSeguros().equals(segurosVacio));

		// Cliente no existe
		assertEquals(sut.bajaSeguro("PLL9597", "77777777G"), null);

		// Ni seguro ni cliente existe
		assertEquals(sut.bajaSeguro("2020AAA", "77777777G"), null);

		// El seguro no pertenece a ese cliente
		assertThrows(OperacionNoValida.class, () -> sut.bajaSeguro("PLX9597", "12345678S"));
		assertTrue(clienteExistente.getSeguros().equals(segurosVacio));
	}
}
