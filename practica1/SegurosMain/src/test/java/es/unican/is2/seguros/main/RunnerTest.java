package es.unican.is2.seguros.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.unican.ps.seguros.businessLayer.GestionSeguros;
import es.unican.ps.seguros.businessLayer.OperacionNoValida;
import es.unican.ps.seguros.daoLayer.ClientesDAO;
import es.unican.ps.seguros.daoLayer.IClientesDAO;
import es.unican.ps.seguros.daoLayer.ISegurosDAO;
import es.unican.ps.seguros.daoLayer.SegurosDAO;
import es.unican.ps.seguros.domain.Cobertura;
import es.unican.ps.seguros.domain.DatoNoValidoException;
import es.unican.ps.seguros.domain.Seguro;


class RunnerTest {

	// Rellenar con el Path donde se guarde el fichero aseguradora.xml
	private static final String DATOS = "C:\\Temp\\aseguradora.xml";

	private static final String DATOS_BACKUP = "C:\\Temp\\aseguradoraBackup.xml";

	static IClientesDAO daoClientes;
	static ISegurosDAO daoSeguros;
	static GestionSeguros negocio;

	static Seguro seguroNoExistente;
	static Seguro seguroNoExistente2;
	static Seguro seguroExistente;

	static GestionSeguros gestionSeguros;

	@BeforeEach
	public void init(){
		backup(DATOS, DATOS_BACKUP);
		daoClientes = new ClientesDAO();
		daoSeguros = new SegurosDAO();
		gestionSeguros = new GestionSeguros(daoClientes, daoSeguros);

		seguroExistente = daoSeguros.seguro("PLL9597");
		try {
			seguroNoExistente = new Seguro(100, "2020AAA", Cobertura.TODORIESGO, LocalDate.now());
			seguroNoExistente2 = new Seguro(100, "2020AAB", Cobertura.TODORIESGO, LocalDate.now());
		} catch (DatoNoValidoException e) {
			e.printStackTrace();
		}
	}

	@AfterEach
	public void end(){
		backup(DATOS_BACKUP, DATOS);
		File f = new File(DATOS_BACKUP);
		f.delete();
	}

	@Test
	public void nuevoSeguroTest(){
		// Operacion correcta
		assertEquals(gestionSeguros.nuevoSeguro(seguroNoExistente, "12345678S"), seguroNoExistente);

		// Cliente no exitente 
		// Aunque en el plan de pruebas se busque el "2020AAA", utilizo uno distinto porque se ha añadido en el caso de prueba anterior
		assertNull(gestionSeguros.nuevoSeguro(seguroNoExistente2, "77777777G"));

		// El seguro ya existe
		assertThrows(OperacionNoValida.class, () -> gestionSeguros.nuevoSeguro(seguroExistente, "12345678S"));

	}

	@Test
	public void eliminaSeguroTest(){
		//Operacion correcta
		assertEquals(gestionSeguros.bajaSeguro("PLL9597", "12345678S"), seguroExistente);

		// Seguro no existente
		assertNull(gestionSeguros.bajaSeguro("2020AAA", "77777777G"));

		// Cliente no existente
		assertNull(gestionSeguros.bajaSeguro("PLL9597", "77777777G"));

		// Cliente y seguro no existente
		assertNull(gestionSeguros.bajaSeguro("2020AAA", "77777777G"));

		// El seguro no pertenece al cliente
		assertThrows(OperacionNoValida.class, () -> gestionSeguros.bajaSeguro("PLX9597", "12345678S"));
	}

	private static void backup(String filePath, String backupFilePath) {
		Path sourceFile = Paths.get(filePath);
		Path targetFile = Paths.get(backupFilePath);

		try {
			Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}