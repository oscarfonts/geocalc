mvn install
mvn dependency:copy-dependencies
cp target/dependency/* target/
cd target
java -jar geocalc-1.1-cat.jar
java -jar geocalc-1.1-cat.jar EPSG:23031 EPSG:4326 438000 4642000
