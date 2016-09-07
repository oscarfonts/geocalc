/**
 * Geodetic Calculator based on GeoTools
 */
package co.geomati;

import org.geotools.geometry.DirectPosition2D;
import org.geotools.referencing.CRS;
import org.geotools.referencing.ReferencingFactoryFinder;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

/**
 * @author Oscar Fonts
 */
public class Geocalc {
	
	static {
		// Force XY
		System.setProperty("org.geotools.referencing.forceXY", "true");
		// Register the transform overrides
		ReferencingFactoryFinder.addAuthorityFactory(new WKTOperationFactory("transform_overrides.properties"));
	}
	
	public static double[] transform2DPoint(String fromEPSG, String toEPSG, double x, double y) throws FactoryException, TransformException {	
		MathTransform transform = CRS.findMathTransform(CRS.decode(fromEPSG), CRS.decode(toEPSG), false);
		DirectPosition srcPoint = new DirectPosition2D(x, y);
		DirectPosition dstPoint = new DirectPosition2D();
		transform.transform(srcPoint, dstPoint);

		/*
		System.out.println(fromEPSG + " => " + toEPSG);
		System.out.println(transform.toString());
		System.out.println("");
		System.out.println(srcPoint + " => " + dstPoint);
		System.out.println("");
		*/
		
		return dstPoint.getCoordinate();
	}

	public static void main(String[] args) throws Exception, Exception {
		/* Comprueba que se está cargando la rejilla */
		MathTransform transform = CRS.findMathTransform(CRS.decode("EPSG:4230"), CRS.decode("EPSG:4326"), false);		
		if(!transform.toString().contains("NTv2")) {
			throw new Exception("Error grave: No se localizó la rejilla de transformación entre ED50 y WGS84.\n");
		}
		
		if (args.length != 4) {
			/* Muestra ayuda */
			System.out.println("Geocalc");
			System.out.println("=======\n");
			System.out.println("Transforma un par de coordenadas entre dos sistemas de referencia.");
			System.out.println("");
			System.out.println("Recibe 4 parámetros:");
			System.out.println("  Geocalc <EPSG_origen> <EPSG_destino> <x> <y>");
			System.out.println("");
			System.out.println("Ejemplo de uso:");
			System.out.println("  Geocalc EPSG:23031 EPSG:4326 438000 4642000");
			System.out.println("");
			System.out.println("Resultado esperado:");
			System.out.println("  2.251109,41.925679");
		} else {
			/* Transforma según los parámetros entrados */
			double[] result = transform2DPoint(args[0], args[1], Double.parseDouble(args[2]), Double.parseDouble(args[3]));
			System.out.printf("%f %f\n", result[0], result[1]);
		}
	}
}
