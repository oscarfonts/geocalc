package co.geomati;

import static org.junit.Assert.*;

import org.junit.Test;
import co.geomati.Geocalc;

public class TestPuntsICGC {

	private static double max_error = 0.01; // Un centímetro, en metros
	
	/**
	 * Test de la transformación oficial del ICGC
	 * 
	 * Puntos de control extraídos del documento:
	 * "TRANSFORMACIÓ BIDIMENSIONAL DE SEMBLANÇA ENTRE ED50 I ETRS89"
	 * Joel Grau Bellet, Unitat de Geodèsia ICGC, Març de 2010.
	 * 
	 * @throws Exception Error en la transformación
	 */
	@Test
	public void test() throws Exception {
		// Puntos de test expresados en ED50 y ETRS89
		double[] a1 = {300000.000, 4500000.000, 299905.060, 4499796.515};
		double[] a2 = {315000.000, 4740000.000, 314906.904, 4739796.774};
		double[] a3 = {520000.000, 4680000.000, 519906.767, 4679795.125};
		double[] a4 = {420000.000, 4600000.000, 419906.005, 4599795.760};

		double[] b1 = {300000.000, 4500000.000, 300094.938, 4500203.485};
		double[] b2 = {315000.000, 4740000.000, 315093.094, 4740203.227};
		double[] b3 = {520000.000, 4680000.000, 520093.231, 4680204.876};
		double[] b4 = {420000.000, 4600000.000, 420093.993, 4600204.241};
		
		// Comprobar que el error de transformación está por debajo del error nominal
		assertTrue(distance(a1, "EPSG:23031", "EPSG:25831") < max_error);
		assertTrue(distance(a2, "EPSG:23031", "EPSG:25831") < max_error);
		assertTrue(distance(a3, "EPSG:23031", "EPSG:25831") < max_error);
		assertTrue(distance(a4, "EPSG:23031", "EPSG:25831") < max_error);

		assertTrue(distance(b1, "EPSG:25831", "EPSG:23031") < max_error);
		assertTrue(distance(b2, "EPSG:25831", "EPSG:23031") < max_error);
		assertTrue(distance(b3, "EPSG:25831", "EPSG:23031") < max_error);
		assertTrue(distance(b4, "EPSG:25831", "EPSG:23031") < max_error);
	}
	
	/**
	 * Transforma un punto a un CRS de destino, y mide la distancia con el segundo punto.
	 * 
	 * @param puntos Se esperan cuatro (dos pares de coordenadas)
	 * @param CRSDestino
	 * @return
	 * @throws Exception
	 */
	private double distance(double[] puntos, String CRSOrigen, String CRSDestino) throws Exception {
	    double[] pt = Geocalc.transform2DPoint(CRSOrigen, CRSDestino, puntos[0], puntos[1]);
	    
	    // Distancia euclidiana. Válida en las distancias cortas. :)
	    double d = Math.sqrt(Math.pow(pt[0]-puntos[2],2)+Math.pow(pt[1]-puntos[3],2));
	    System.out.printf("Test distance: %.3f m\n", d);
	    return d;
	}
	
}
