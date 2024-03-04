# webapp15: MASTER TICKET



- Nombre de la Aplicación Web: Master Ticket

- Miembros del equipo:

| Nombre | Correo | Github |
| ------ | ------ | ------ |
| Carlos Solsona Álvarez | c.solsona.2020@alumnos.urjc.es | csolsona
| Diego Del Amo Infante | d.delamo.2018@alumnos.urjc.es | DiegoDAI
| Xavier Medina Salas | jx.medina@alumnos.urjc.es | XdeXavi
| Jesús Pérez Sánchez | j.perezsa.2020@alumnos.urjc.es | DiosYisus77
| Javier García Seller | j.garcias.2020@alumnos.urjc | Javarto

- Herramientas para la coordinación del equipo: 
	- Discord: https://discord.gg/jGRdubD6
 	- Link de Miro: https://miro.com/welcome/c2FuaktGN1pSNWV5UkVYU0Zjb05zdEZ1QUhWVHRxb1JrYktXVWJQTk9JblhRYWtLaG95ZTVYRXhnTWd4bDFnVnwzNDU4NzY0NTM0NTA5MDYwNzI1fDE=?share_link_id=638984367207

## Aspectos principales de la aplicación web:

- Entidades:
	- Usuario
	- Concierto
	- Entrada
	- Artista
   	- Género

- Relaciones entre entidades:
	- Usuarios pueden comprar entradas
	- El artista que realiza el concierto
	- Las entradas para poder ir al concierto

- Tipos de usuario:
	- Usuario anónimo
	- Usuario registrado
	- Usuario administrador

- Permisos de los usuarios:
	- Los conciertos previos que ha comprado

- Imágenes:
	- Foto del perfil del usuario
	- Foto del concierto
	- Foto del artista

- Gráficos:
	- Número de entradas vendidas por més

- Tecnología complementaria:
	- Generación de un pdf que corresponde a las entradas del concierto comprado

- Algoritmo o consulta avanzada:
	- Filtro de búsqueda
   	- Sección de recomendados en base al género musical favorito del usuario


## Funcionalidades de la aplicación web:

- Para usuarios no registrados:
	- Tener un buscador de conciertos
	- Visualizar el listado de conciertos
	- Poder registrarse para ser un usuario registrado
	- Poder loguearse

- Para usuarios registrados:
	- Poder comprar entradas
	- Tener un perfil de usuario con sus datos
	- Modificar su cuenta
   	- Descargar PDF recibo de entrada

- Para usuarios administradores:
	- Poder crear conciertos
	- Poder eliminar conciertos

## Explicaciones de Pantallas:

- Pantalla Inicio: Es una pantalla de presentación de la aplicación donde habrá una lista de conciertos y otra de recomendados por perfil.
![home](https://github.com/CodeURJC-DAW-2023-24/webapp15/assets/80209861/85c04995-b4f2-4d7b-a0d3-e3270e5b3119)

- Pantalla Login: Pantalla que se utilizará para que se registren los usuarios.
![login](https://github.com/CodeURJC-DAW-2023-24/webapp15/assets/80209861/80bfd7ef-0add-420a-a487-894231632499)

- Pantalla Registro: Pantalla para que se registren nuevos usuarios.
![registro](https://github.com/CodeURJC-DAW-2023-24/webapp15/assets/80209861/e75dd819-6535-4f69-a088-00d13e7336bc)

- Pantalla Búsqueda: Pantalla donde los usuarios podrán buscar concierto por filtros e irte a comprar las entradas.
![busqueda](https://github.com/CodeURJC-DAW-2023-24/webapp15/assets/80209861/c455fafc-bbf0-4bf6-b870-36e5e04e755f)

- Pantalla Información del Artista: Pantalla donde se mostrará la información del artista con una lista de conciertos próximos donde puedas ir a comprar entradas.
![infoArtista](https://github.com/CodeURJC-DAW-2023-24/webapp15/assets/80209861/2165e765-8bb8-4b41-866c-595441fa27d3)

- Pantalla Comprar Entrada: Pantalla donde podrás comprar el número de entradas metiendo la información de la tarjeta.
![compra](https://github.com/CodeURJC-DAW-2023-24/webapp15/assets/80209861/218e7d3a-7e80-4ed1-9528-e999e787e504)

- Pantalla Creación Artista: Pantalla donde el administrador podrá crear el artista con su información.
![creacionArtista](https://github.com/CodeURJC-DAW-2023-24/webapp15/assets/80209861/e7b5b2f6-6d2e-4db1-a955-d0ac4d394f0e)

- Pantalla Creación Concierto: Pantalla donde el administrador podrá crear el concierto con su información.
![creacionConcierto](https://github.com/CodeURJC-DAW-2023-24/webapp15/assets/80209861/4c33d06e-2bdd-4c3a-b22f-630af8aa243a)

### Diagrama de Navegación de Pantallas

<img width="995" alt="DiagramaNavegacion" src="https://github.com/CodeURJC-DAW-2023-24/webapp15/assets/80209861/50c79558-a6de-43a0-a348-f01fb8783c1a">

### Diagrama de entidades bases de datos
![DiagramaUML](https://github.com/CodeURJC-DAW-2023-24/webapp15/assets/80209861/77e64ce6-355a-424c-8cb6-2203ab79a148)

### Diagrama de templates y clases
![DiagramaTemplate](https://github.com/CodeURJC-DAW-2023-24/webapp15/assets/80209861/d8815952-1149-4a8a-8c6e-355bd5348b24)

## Participación de los miembros del equipo
### Participación componentes del grupo

	- Diego Del Amo Infante:
		
		- Durante esta fase, me he encargado de cambiar los templates a mustache así como de toda la parte de creación de artista y de concierto. Además, me he encargado de los AJAX de las 		pagínas de index y de search y la funcionalidad de borrar conciertos.
		
		- Commits más significativos:
			- Eliminar Concierto: https://github.com/CodeURJC-DAW-2023-24/webapp15/commit/f109c09dae1e16592d3f0ffc448ee7e05279b0f4
			- AJAX Search: https://github.com/CodeURJC-DAW-2023-24/webapp15/commit/106d0f1008357647eae7a3d69eaa4f83fee39f8b
			- AJAX Index: https://github.com/CodeURJC-DAW-2023-24/webapp15/commit/8f1f06fb7a2563769efcde41bfbf1f66142b535e
			- Controller adminArtistController: https://github.com/CodeURJC-DAW-2023-24/webapp15/commit/21daace72216218605b95248977a8fbfc012146c
			- Controller adminConcertController: https://github.com/CodeURJC-DAW-2023-24/webapp15/commit/db97b46f2ff0bb01fccd30a22bc7fdb27ada8767


		- Ficheros más significativos:
			- adminArtistController.java:https://github.com/CodeURJC-DAW-2023-24/webapp15/blob/main/src/main/java/es/codeurjc/webapp15/controller/adminArtistController.java
			- adminConcertController.java:https://github.com/CodeURJC-DAW-2023-24/webapp15/blob/main/src/main/java/es/codeurjc/webapp15/controller/adminConcertController.java
			- IndexController.java:https://github.com/CodeURJC-DAW-2023-24/webapp15/blob/main/src/main/java/es/codeurjc/webapp15/controller/IndexController.java
			- perfil.js: https://github.com/CodeURJC-DAW-2023-24/webapp15/blob/main/src/main/resources/static/perfil.js
			- searchController.java:https://github.com/CodeURJC-DAW-2023-24/webapp15/blob/main/src/main/java/es/codeurjc/webapp15/controller/SearchController.java


	- Jesús Pérez

		- Durante la fase 2 he estado trabajando principalmente en la implementación
  		y despliege de la base de datos en MySQL. He realizado todas las entidades 
    		y participado activamente en la resolucción de errores relacionados con la BBDD.
      		(Subir y cargar imágenes dió muchos problemas, por ejemplo).Posteriormente, he 
		programado algunas de las funcionalidades como el proceso de pago y el algoritmo
  		para la sección de recomendados en la pantalla de inicio.

		- Commits más significativos:
			- artist y concert models: https://github.com/CodeURJC-DAW-2023-24/webapp15/commit/628e5f516676b809caed4fc18910a7deff48ea9c
			- model user entity: https://github.com/CodeURJC-DAW-2023-24/webapp15/commit/d467a63921bc66e54485254f23c8996fc6970850
			- payment process: https://github.com/CodeURJC-DAW-2023-24/webapp15/commit/3fdae4e1e96bdc491e081389cc5dc586e316c7bc
			- algoritmoSeccionRecomendados: https://github.com/CodeURJC-DAW-2023-24/webapp15/commit/158b2b520be87643cd9d61b813e3c5e7402a45bd
			- (PROGRAMACION COLABORATIVA) Controller adminConcertController: https://github.com/CodeURJC-DAW-2023-24/webapp15/commit/db97b46f2ff0bb01fccd30a22bc7fdb27ada8767

		- Ficheros más significativos:
			- TicketController.java src/main/java/es/codeurjc/webapp15/controller/TicketController.java
			- IndexController.java src/main/java/es/codeurjc/webapp15/controller/IndexController.java
			- Concert.java src/main/java/es/codeurjc/webapp15/model/Concert.java
			- User.java src/main/java/es/codeurjc/webapp15/model/User.java
   			- adminArtistController.java src/main/java/es/codeurjc/webapp15/controller/adminArtistController.java

      
      - Xavier Medina Salas:
		
		- Durante esta fase, me he encargado de la parte de seguridad y de lanzar el proyecto en spring boot 
		
		- Commits más significativos:
			- Creacion de SecurityConfiguration: https://github.com/CodeURJC-DAW-2023-24/webapp15/commit/6142404fe6955aae40242296a3ba08e9833017ef
			- Cambio de la base de datos: https://github.com/CodeURJC-DAW-2023-24/webapp15/commit/647231420c9cc8ede356abfb39296350a8e3cca1
			- Actualizacion de security: https://github.com/CodeURJC-DAW-2023-24/webapp15/commit/f98b1dda422094c0b0ed7a557feaa5eb0aab5c99
			- Creacion de repositorios: https://github.com/CodeURJC-DAW-2023-24/webapp15/commit/80e3874cc4ec6b698aa18ed9c7e87d643cd87e1e
   			- Creacion de paquetes model y securityhttps://github.com/CodeURJC-DAW-2023-24/webapp15/commit/3215b7d8f1d4b8c0b0275353c887612e5de23820


		- Ficheros más significativos:
