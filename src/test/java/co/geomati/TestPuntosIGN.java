package co.geomati;

import static org.junit.Assert.*;

import org.junit.Test;
import co.geomati.Geocalc;

public class TestPuntosIGN {

	// Según "accuracy" en transformación EPSG 15933 (ver www.epsg-registry.org)
	private static double max_error = 1; //metros
	
	/**
	 * Test de la rejilla NTv2 del IGN (transformación EPSG 15933), utilizando "Conjunto 3" de:
	 * Consejo Superior Geográfico, "Herramientas para facilitar el cambio", pág. 21.
	 * http://www.fomento.gob.es/NR/rdonlyres/CFCA42ED-0AF9-4DFD-A7E3-FA8B64A6DBC3/29826/hercam.pdf
	 * 
	 * Error de todas las transformaciones < 20 cm, excepto el punto de Baleares, por usar
	 * rejilla válida sólo en península.
	 * 
	 * @throws Exception Error en la transformación
	 */
	@Test
	public void test() throws Exception {
		// Puntos de test expresados en ED50 y ETRS89
		double[] a29 = {fromDegMinSec(42, 53, 11.84830), fromDegMinSec(-8, 41, 31.48700), fromDegMinSec(42, 53,  7.67389), fromDegMinSec(-8, 41, 36.97721)};
		double[] b29 = {fromDegMinSec(39, 26, 28.25490), fromDegMinSec(-6, 49, 18.48030), fromDegMinSec(39, 26, 23.83814), fromDegMinSec(-6, 49, 23.39162)};
		double[] c29 = {fromDegMinSec(37, 20, 58.65760), fromDegMinSec(-6, 37, 16.25000), fromDegMinSec(37, 20, 54.12275), fromDegMinSec(-6, 37, 21.12521)};
		double[] a30 = {fromDegMinSec(43,  5, 57.90840), fromDegMinSec(-2, 56, 56.23450), fromDegMinSec(43,  5, 53.96402), fromDegMinSec(-2, 57,  0.91159)};
		double[] b30 = {fromDegMinSec(39, 51,  7.98770), fromDegMinSec(-2, 59,  5.45070), fromDegMinSec(39, 51,  3.69061), fromDegMinSec(-2, 59, 10.07606)};
		double[] c30 = {fromDegMinSec(36, 54, 45.70300), fromDegMinSec(-3,  3, 22.66570), fromDegMinSec(36, 54, 41.17327), fromDegMinSec(-3,  3, 27.20765)};
		double[] a31 = {fromDegMinSec(42,  5, 42.19210), fromDegMinSec( 2, 53, 35.00670), fromDegMinSec(42,  5, 38.22914), fromDegMinSec( 2, 53, 30.94246)};
		double[] b31 = {fromDegMinSec(39, 35, 33.55500), fromDegMinSec( 3,  5,  5.62600), fromDegMinSec(39, 35, 29.32944), fromDegMinSec( 3,  5,  1.68655)};
		
		// Comprobar que el error de transformación está por debajo del error nominal
		assertTrue(distance(a29, "EPSG:23029") < max_error);
		assertTrue(distance(b29, "EPSG:23029") < max_error);
		assertTrue(distance(c29, "EPSG:23029") < max_error);
		assertTrue(distance(a30, "EPSG:23030") < max_error);
		assertTrue(distance(b30, "EPSG:23030") < max_error);
		assertTrue(distance(c30, "EPSG:23030") < max_error);
		assertTrue(distance(a31, "EPSG:23031") < max_error);
		
		// Ojo!! Baleares: Error de 1.17 metros.
		// Rejilla válida únicamente en Península.
		//assertTrue(distance(b31, "EPSG:23031") < max_error);
	}
	
	/**
	 * Transforma dos puntos (uno en ED50 y otro en ETRS89) a un CRS de destino,
	 * y calcula la distancia entre ambos puntos en las unidades del CRS destino. 
	 * 
	 * @param puntos Se esperan cuatro coordenadas: Lat_4230, Lon_4230, Lat_4326 y Lon_4326. 
	 * @param CRSDestino
	 * @return
	 * @throws Exception
	 */
	private double distance(double[] puntos, String CRSDestino) throws Exception {
	    double[] orig = Geocalc.transform2DPoint("EPSG:4230", CRSDestino, puntos[1], puntos[0]);
	    double[] dest = Geocalc.transform2DPoint("EPSG:4326", CRSDestino, puntos[3], puntos[2]);
	    
	    // Distancia euclidiana. Válida en las distancias cortas. :)
	    double d = Math.sqrt(Math.pow(orig[0]-dest[0],2)+Math.pow(orig[1]-dest[1],2));
	    System.out.printf("Test distance: %.2f m\n", d);
	    return d;
	}

	/**
	 * Convierte de grados, minutos y segundos a grados decimales.
	 */
	private static double fromDegMinSec(double deg, double min, double sec) {
		return Math.signum(deg) * (Math.abs(deg) + min/60.0 + sec/3600.0);
	}
	
	/**
	 * Convierte de grados decimales a grados, minutos y segundos.
	 * 
	 * @return un array de tres elementos: grados, minutos y segundos
	 */
	private static double[] toDegMinSec(double deg) {
		double[] gms = new double[3];
		gms[0] = (int)deg;
		double ms = Math.abs((deg - gms[0]) * 60);
		gms[1] = (int)ms;
		gms[2] = (ms - gms[1]) * 60;		
		return gms;
	}
}
