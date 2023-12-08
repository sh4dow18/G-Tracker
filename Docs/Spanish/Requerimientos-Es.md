# Requerimientos Funcionales y no Funcionales

## Historial de Revisión

| Fecha | Versión | Descripción | Autor |
| ----- | ------- | ----------- | ----- |
| 08/12/2023 | 1.0.0 | Creación de este documento y rellenado de todas las partes de este | sh4dow18 |

## Indice

- [Requerimientos Funcionales y no Funcionales](#requerimientos-funcionales-y-no-funcionales)
  - [Historial de Revisión](#historial-de-revisión)
  - [Indice](#indice)
  - [Introducción](#introducción)
  - [Alcance del Documento](#alcance-del-documento)
  - [Glosario](#glosario)
  - [Requerimientos Funcionales](#requerimientos-funcionales)
  - [Requerimientos No Funcionales](#requerimientos-no-funcionales)
  - [Conclusión](#conclusión)

## Introducción

El presente documento tiene como objetivo definir el alcance del sistema de la plataforma de seguimiento
de videojuegos, así como los requerimientos funcionales y no funcionales asociados. Esta plataforma se
orienta a brindar a los usuarios una experiencia de seguimiento de sus actividades de juego en tiempo
real, permitiéndoles acceder y visualizar de manera efectiva y sencilla el historial de sus partidas y
logros.

Con este documento, se da continuidad a una serie de archivos destinados a documentar el avance
y desarrollo de la plataforma de seguimiento de los videojuegos de una persona, estableciendo las
características principales del mismo y brindando una visión general de su funcionamiento y alcance.

## Alcance del Documento

Son todos los requisitos del sistema de “G-Tracker” que han sido consensuados entre todas las partes
involucradas, y describe la funcionalidad y las restricciones que ofrece el sistema. En síntesis, se detallan
dos elementos fundamentales: La lista de requerimientos funcionales y la lista de los requerimientos no
funcionales

## Glosario

- Sistema: Sistema Informático

## Requerimientos Funcionales

| ID | Nombre y Descripción |
|----|----------------------|
| RF-001 | **Inicio**: El sistema debe permitir a los usuarios acceder a la plataforma y ver una explicación de qué es el sistema **G-Tracker**. Esta funcionalidad se implementará en una sección de inicio al ingresar a la aplicativo. Además, este apartado deberá ser visto por toda persona al entrar al aplicativo. Por último, la sección debe estar acorde con el diseño establecido en el documento de estándares |
| RF-002 | **Registro de Cuenta**: El sistema debe permitir a los usuarios crear una cuenta para acceder a la plataforma. Este debe realizarse por un formulario. El formulario debe estar conformado por: Correo Electrónico, Nombre del Perfil, Contraseña y Confirmación de Contraseña. Esta funcionalidad se implementará mediante una sección de registro. Además, este apartado deberá ser visto por toda persona que entre al aplicativo. Por último, la sección debe estar acorde con el diseño establecido en el documento de estándares |
| RF-003 | **Inicio de Sesión**: El sistema debe permitir a los usuarios ingresar a sus cuentas existentes. Este debe realizarse por un formulario. El formulario debe estar conformado por: Correo Electrónico y Contraseña. Esta funcionalidad se implementará mediante una sección de inicio de sesión. Además, este apartado deberá ser visto por todo usuario que entre al aplicativo. Por último, la sección debe estar acorde con el diseño establecido en el documento de estándares |
| RF-004 | **Visualización de Videojuegos**: El sistema debe permitir visualizar los 20 mejores juegos según la crítica. Este debe mostrar por cada registro de juego: Imagen de Juego, Nombre de Juego, rating de metacritic y rating de usuarios. Esta funcionalidad se implementará mediante una sección de Juegos. Además, este apartado deberá ser visto por cualquier perfil de una cuenta con sesión iniciada. Por último, la sección debe estar acorde con el diseño establecido en el documento de estándares |
| RF-005 | **Buscador de Contenido**: El sistema debe permitir buscar juegos dentro de la plataforma. Este debe realizarse por un formulario. El formulario debe estar conformado por: Nombre del Juego. Esta funcionalidad se implementará mediante una sección de Juegos. Además, este apartado deberá ser visto por cualquier cuenta con sesión iniciada. Por último, la sección debe estar acorde con el diseño establecido en el documento de estándares |
| RF-006 | **Agregado de Registro de Seguimiento de Juego**: El sistema debe permitir agregar un registro de juego para su seguimiento. El formulario debe estar conformado por: Botón para Agregar Registro. Esta funcionalidad se implementará mediante una sección de Información de Juego. Además, este apartado deberá ser visto por cualquier cuenta con sesión iniciada. Por último, la sección debe estar acorde con el diseño establecido en el documento de estándares |
| RF-007 | **Visualización de Perfil**: El sistema debe permitir ver la información del perfil seleccionado. Este debe mostrar mínimo: Imagen de Perfil, Nombre de Perfil, un botón para actualizar el perfil actual, un botón para cerrar sesión y otro para cerrar la cuenta. Esta funcionalidad se implementará mediante una sección de perfil. Además, este apartado deberá ser visto por cualquier perfil de una cuenta con sesión iniciada. Por último, la sección debe estar acorde con el diseño establecido en el documento de estándares |
| RF-008 | **Actualización de Perfil**: El sistema debe permitir modificar la información del perfil actual. Este debe realizarse por un formulario. El formulario debe estar conformado por: Imagen de Perfil y Nombre de Perfil. Esta funcionalidad se implementará mediante una sección de actualización de perfil. Además, este apartado deberá ser visto por cualquier cuenta con sesión iniciada. Por último, la sección debe estar acorde con el diseño establecido en el documento de estándares |
| RF-009 | **Cierre de una Cuenta**: El sistema debe permitir cerrar permanentemente una cuenta existente. Este debe realizarse por un botón. Esta funcionalidad se implementará mediante una sección de perfil. Además, este apartado deberá ser visto por cualquier cuenta con sesión iniciada. Por último, la sección debe estar acorde con el diseño establecido en el documento de estándares |
| RF-010 | **Cerrar Sesión**: El sistema debe permitir salir de la cuenta actual y volver a la pantalla de inicio de sesión. Este debe realizarse por un botón. Esta funcionalidad se implementará mediante una sección de perfil. Además, este apartado deberá ser visto por cualquier perfil de una cuenta con sesión iniciada. Por último, la sección debe estar acorde con el diseño establecido en el documento de estándares |
| RF-011 | **Seguimiento de Videojuegos**: El sistema debe permitir visualizar todos los juegos que se han registrado por el usuario para hacer un seguimiento de estos. Este debe mostrar por cada registro de juego: Imagen de Juego, Nombre de Juego, rating de usuarios y si el juego está finalizado. Esta funcionalidad se implementará mediante una sección de Seguimiento de Juegos. Además, este apartado deberá ser visto por cualquier perfil de una cuenta con sesión iniciada. Por último, la sección debe estar acorde con el diseño establecido en el documento de estándares |
| RF-012 | **Actualización de Registro de Seguimiento de Juego**: El sistema debe permitir actualizar el registro en el seguimiento de juego. Este debe realizarse por un formulario. El formulario debe estar conformado por: Botón de Juego Terminado y Botón de Juego Terminado al 100%. Esta funcionalidad se implementará mediante una sección de Actualización de Registro de Juego. Además, este apartado deberá ser visto por cualquier cuenta con sesión iniciada. Por último, la sección debe estar acorde con el diseño establecido en el documento de estándares |
| RF-013 | **Visualización de la Información de la Aplicación**: El sistema debe permitir ver detalles de la aplicación. Este debe mostrar: El nombre de la aplicacion y la versión del programa. Esta funcionalidad se implementará mediante una sección de información de la aplicación. Además, este apartado deberá ser visto por cualquier cuenta con sesión iniciada. Por último, la sección debe estar acorde con el diseño establecido en el documento de estándares |
| RF-014 | **Créditos**: El sistema debe permitir mostrar los créditos del creador. Este debe mostrar: Nombre del creador, Página del creador, Página de Github, Nombre de la empresa del creador, página de la empresa, página de Facebook de la empresa, número telefónico de la empresa y correo electrónico de la empresa. Esta funcionalidad se implementará mediante una sección de información de la aplicación. Además, este apartado deberá ser visto por cualquier cuenta con sesión iniciada. Por último, la sección debe estar acorde con el diseño establecido en el documento de estándares |


## Requerimientos No Funcionales

| ID | Nombre y Descripción |
|----|----------------------|
| RNF-001 | **Usabilidad**: El sistema debe garantizar que sea intuitivo y fácil de usar para los usuarios, promoviendo una experiencia amigable y eficiente en su interacción |
| RNF-002 | **Seguridad**: El sistema debe asegurar la protección de los datos y recursos del sistema contra accesos no autorizados y ataques maliciosos, garantizando la integridad, confidencialidad y disponibilidad de la información |
| RNF-003 | **Actualización en Tiempo Real**: El sistema debe permitir que se actualice y refleje los cambios en tiempo real, asegurando que la información sea siempre precisa y actualizada |
| RNF-004 | **Escalabilidad**: El sistema debe garantizar que este pueda adaptarse y expandirse sin perder rendimiento a medida que aumenta la cantidad de usuarios, volumen de datos o caracteristicas del aplicativo |
| RNF-005 | **Mantenibilidad y Facilidad de Mantenimiento**: El sistema debe facilitar la tarea de mantener y mejorar el sistema a lo largo del tiempo, con un diseño limpio y estructurado que facilite las modificaciones y correcciones |

## Conclusión

En conclusión, los requerimientos funcionales y no funcionales presentados para el sistema de seguimiento
de videojuegos se muestran fundamentales para el desarrollo de una plataforma robusta y altamente eficiente.
La claridad y especificidad de los requerimientos funcionales aseguran que las funcionalidades clave se
implementen adecuadamente.

Asimismo, los requerimientos no funcionales garantizan una experiencia de usuario óptima y satisfactoria.
La consideración meticulosa de estos aspectos durante todo el ciclo de desarrollo permitirá la creación de
un sistema de seguimiento de videojuegos capaz de satisfacer las necesidades del mercado actual y superar
las expectativas de los usuarios, posicionándolo como una opción líder en la industria del seguimiento de
juegos
