# RegistroDisp2


This app was created with Bootify.io - tips on working with the code [can be found here](https://bootify.io/next-steps/).
Feel free to contact us for further questions.

# Prueba la demo
	- la aplicación esta alojada en https://dispositivos-manuel-soto.up.railway.app/

# Características de la aplicacion
	-Multiples vistas
	-interfaz intuitiva
	-Diseño simple y amigable
	-interfaz responsiva
	-información alojada en base de datos relacional
	-consulta de listas de alumnos
	-creación de dispositivos	
	-asignación de dispositivos a usuarios
	-validación de entrada de datos

# Construido con
	-Spring Framework
	-JDBC
	-Hibernate
	-Thymeleaf
	-Lombok
	-HTML, Css, Bootstrap.
	-MySQL
	-Swagger

# Instrucciones
	
# primero clona el repositorio con:
	 git clone https://github.com/jomaso23/Dispositivos.git	

asegúrate de tener bien configurado tu  IDE, con las dependencias necesarias para su correcto funcionamiento, en mi caso use Intellij, de la misma forma este te proveerá la posibilidad de desplegarlo.
el proyecto se desplegara en el puerto 8080:

	 http://localhost:8080/

para poder usar la dependencia de swagger y probar los endpoints de la aplicaicon, una vez desplegada la aplicacion, puedes dirigirte a la url:

	 http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/

# Conexcion a la base de datos:
	
	 en aplication.properties puedes modificar la conexcion al base de deatos, entre otras características,
	 ten en cuenta que todo fue configurado con variable de entorno, las cuales se pueden modificar en el IDE,
	 o sino, puedes optar por configurar la base de datos que mas te sea útil.


## Development

Update your local database connection in `application.properties` or create your own `application-local.properties` file to override
settings for development.

During development it is recommended to use the profile `local`. In IntelliJ `-Dspring.profiles.active=local` can be
added in the VM options of the Run Configuration after enabling this property in "Modify options".

Lombok must be supported by your IDE. For IntelliJ install the Lombok plugin and enable annotation processing -
[learn more](https://bootify.io/next-steps/spring-boot-with-lombok.html).

After starting the application it is accessible under `localhost:8080`.

## Build

The application can be built using the following command:

```
mvnw clean package
```

Start your application with the following command - here with the profile `production`:

```
java -Dspring.profiles.active=production -jar ./target/RegistroDisp2-0.0.1-SNAPSHOT.jar
```

If required, a Docker image can be created with the Spring Boot plugin. Add `SPRING_PROFILES_ACTIVE=production` as
environment variable when running the container.

```
mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=registro/registro-disp2
```

## Further readings

* [Maven docs](https://maven.apache.org/guides/index.html)  
* [Spring Boot reference](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)  
* [Spring Data JPA reference](https://docs.spring.io/spring-data/jpa/reference/jpa.html)
