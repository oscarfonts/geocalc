Geocalc
=======

Geodetic Calculator based on GeoTools.

Includes spanish NTv2 Grid Shift for accurate conversion from ED50 to ETRS89 and WGS84.

Building
--------

Needs Maven 2::

  git clone git@github.com:oscarfonts/geocalc.git
  ./dist.sh

.. note:: There's a bug in GeoTools that prevents NTv2 grid files to be read from jar packages. The issue and the patch to gt-referencing: https://jira.codehaus.org/browse/GEOT-4420 

Running
-------

From commandline
................

::

  java -jar geocalc-1.0-jar <src_EPSG> <dst_EPSG> <x> <y>

Example::

  java -jar geocalc-1.0-jar EPSG:23031 EPSG:4326 438000 4642000


From java code
..............

Just a static ``transform2DPoint`` method::

  import co.geomati.Geocalc;

  double[] result = Geocalc.transform2DPoint("EPSG:23031", "EPSG:4326", 438000, 4642000);
  System.out.printf("x: %f - y: %f\n", result[0], result[1]);
