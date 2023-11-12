# README
Este proyecto es un demo para creación de entidades de clase vehículo, el desarrollo del mismo no cuenta con controles de ingreso de valores en los campos del formulario, o un manejo de expeciones adecuado.
Para levantar este proyecto es necesario tomar en cuenta lo siguiente:
* El proyecto usa hibernate y el archivo persistence.xml tiene las configuraciones necesarias para que el proyecto funcione con una base de datos postgres y que la tabla se genere de forma automática
* En el archivo standalone.xml del servidor JBoss, es necesario agregar un Data Source que haga referencia a la base de datos a utilizar en el proyecto. Por defecto con el nombre demo.
