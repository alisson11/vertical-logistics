# Execute build and run the application in local container
run:
	mvn spring-boot:run -f ../pom.xml
# Execute build and run the application in local container in debug mode.
debug:
	mvn spring-boot:run -Dagentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000 -f ../pom.xml
