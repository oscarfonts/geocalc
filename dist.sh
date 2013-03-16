mvn install
mvn dependency:copy-dependencies
cp target/dependency/* target/
cd target
java -jar geocalc-1.0.jar
java -jar geocalc-1.0.jar EPSG:23031 EPSG:4326 438000 4642000
