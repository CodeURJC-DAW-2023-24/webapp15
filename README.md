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

### Jesús Pérez

Durante la fase 2 he estado trabajando principalmente en la implementación y despliege de la base de datos en MySQL. He realizado todas las entidades y participado activamente en la resolucción de errores relacionados con la BBDD. (Subir y cargar imágenes dió muchos problemas, por ejemplo). Posteriormente, he programado algunas de las funcionalidades como el proceso de pago y el algoritmo para la sección de recomendados en la pantalla de inicio.

**5 Commits principales

**5 Ficheros donde más he trabajado
