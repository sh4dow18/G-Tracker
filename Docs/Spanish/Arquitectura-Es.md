# Arquitectura

## Historial de Revisión

| Fecha | Versión | Descripción | Autor |
| ----- | ------- | ----------- | ----- |
| 08/12/2023 | 1.0.0 | Creación de este documento y rellenado de todas las partes de este | sh4dow18 |

## Indice

- [Arquitectura](#arquitectura)
  - [Historial de Revisión](#historial-de-revisión)
  - [Indice](#indice)
  - [Introducción](#introducción)
    - [Propósito](#propósito)
    - [Alcance del documento de arquitectura](#alcance-del-documento-de-arquitectura)
  - [Metas y Limitaciones](#metas-y-limitaciones)
    - [Plataforma técnica](#plataforma-técnica)
    - [Portabilidad](#portabilidad)
      - [Guía de Instalación para Producción](#guía-de-instalación-para-producción)
      - [Guía de Instalación para Desarrolladores](#guía-de-instalación-para-desarrolladores)
    - [Seguridad](#seguridad)
      - [Registro de usuarios nuevos](#registro-de-usuarios-nuevos)
      - [Control de acceso de los usuarios y los componentes del sistema](#control-de-acceso-de-los-usuarios-y-los-componentes-del-sistema)
      - [Bitácora de transacciones](#bitácora-de-transacciones)
      - [Controles sobre la composición y asignación del password (políticas de seguridad)](#controles-sobre-la-composición-y-asignación-del-password-políticas-de-seguridad)
    - [Confiabilidad/Disponibilidad](#confiabilidaddisponibilidad)
    - [Desempeño](#desempeño)
  - [Representación de la arquitectura](#representación-de-la-arquitectura)
    - [Estilo de Arquitectura a implementar en el proyecto](#estilo-de-arquitectura-a-implementar-en-el-proyecto)
    - [Descomposición del Sistema](#descomposición-del-sistema)
    - [Descomposición lógica](#descomposición-lógica)
      - [Descomposición en Capas](#descomposición-en-capas)
        - [Capa de negocio](#capa-de-negocio)
        - [Capa de Datos](#capa-de-datos)

## Introducción

### Propósito

El propósito de este documento es resumir la arquitectura del sistema, así como presentar el manual técnico del sistema

### Alcance del documento de arquitectura

Identificar los elementos técnicos requeridos para el desarrollo integral del proyecto de software denominado "G-Tracker"
el cual será implementado

## Metas y Limitaciones

En este apartado se presentan los requerimientos técnicos y condiciones especiales que fueron consideradas en el desarrollo
e implementación del Sistema. Se tratan los requisitos no funcionales relacionados con la ejecución, disponibilidad,
tolerancia a fallos, integridad, etc.

### Plataforma técnica

El producto que estamos desarrollando se basa en una arquitectura sólida y bien definida, que abarca tanto el frontend como
el backend. A continuación, se presentan las principales plataformas y tecnologías que se utilizarán en el proyecto:

-	Arquitectura General: El proyecto estará organizado en diferentes capas para garantizar la modularidad y escalabilidad del sistema.
-	Arquitectura Frontend: Para el desarrollo del frontend, se usará el patrón MVVM. Esto permite separar la lógica de presentación de los datos y mejorar la experiencia del usuario final.
-	Arquitectura Backend: En el backend, se sigue un enfoque basado en capas para asegurar la separación de preocupaciones y facilitar el mantenimiento.
-	Lenguajes: En el frontend y el Backend, se usará Kotlin para crear interfaces de usuario atractivas y funcionales, así como a su robustez y eficiencia en el desarrollo.
-	Entornos de Desarrollo (IDE): Para el desarrollo del frontend, se confió en Android Studio, una herramienta altamente útil para el desarrollo de Aplicaciones Móviles en Android. En el caso del backend, se utilizará IntelliJ IDEA para aprovechar su amplio conjunto de funciones y soporte para Kotlin.
-	Frameworks y Bibliotecas: En el frontend, se construirá la interfaz de usuario utilizando las gran variedad de bibliotecas existentes para la creación de plataformas móviles. En el backend, se eligió Springboot, un framework ampliamente utilizado en el desarrollo de aplicaciones Java y Kotlin que acelera la creación de APIs robustas y seguras.
-	Base de Datos: La base de datos sigue un enfoque relacional para garantizar la integridad de los datos. Se usará PostgreSQL como gestor de base de datos debido a su confiabilidad y capacidades avanzadas.

### Portabilidad

Este aplicativo será accesible solamente para dispositivos móviles con Android integrado

#### Guía de Instalación para Producción

La siguiente guía proporciona los pasos necesarios para instalar el sistema en un entorno de producción utilizando versiones
estables y optimizadas tanto en el Frontend como en el Backend. Se hará uso de releases tipo builds para garantizar la
fiabilidad y el rendimiento del aplicativo.

- Preparativos
  - Java 11. Enlace de descarga: https://jdk.java.net/archive/
  - PostgreSQL 15.4 o superior. Enlace de descarga: https://www.postgresql.org/download/
- Descarga de Versiones Estables
  - El primer paso consiste en descargar las versiones estables tanto del Frontend como del Backend. Esto se puede realizar desde las siguientes fuentes proporcionadas en GitHub: https://github.com/sh4dow18/G-Tracker
  - Navegue a la sección “Releases” de cada enlace
  - Descargue la última versión estable tanto para el Frontend como para el Backend. Estas versiones serán los builds optimizados para producción.
- Configuración de la Base de Datos
  - En la instalación de PostgreSQL configure las credenciales de acceso según las necesidades y especificaciones del sistema
- Configuración del Backend
  - En una terminal, ingrese a la carpeta en la que se encuentre el ejecutable del Backend
  - Ejecute el comando “java -jar g-tracker-backend.jar”
  - Ahora podrá utilizar el backend del proyecto
- Configuración del Frontend
  - En el dispositivo android, instale el archivo "G-Tracker-App.apk"
  - Ahora podrá utilizar el Frontend del proyecto
- Acceso al Sistema
  - Abra la aplicación "G-Tracker" en su dispositivo
  - Una vez en el aplicativo móvil, debería poder visualizar y utilizar el sistema. La instalación ha finalizado con éxito

#### Guía de Instalación para Desarrolladores

- Preparativos
  - Android Studio Giraffe 2022.3.1: https://developer.android.com/studio
  - IntelliJ IDEA Community Edition 2023.2.4: https://www.jetbrains.com/es-es/idea/download/?section=linux
  - Java 11. Enlace de descarga: https://jdk.java.net/archive/
  - PostgreSQL 15.4 o superior. Enlace de descarga: https://www.postgresql.org/download/
- Clonar los Repositorios
  - El primer paso consiste en clonar los repositorios del sistema. Esta acción permitirá obtener el código fuente necesario para la instalación y ejecución del aplicativo. Esto se puede realizar desde las siguientes fuentes proporcionadas en GitHub
- Configuración de la Base de Datos
  - En la instalación de PostgreSQL configure las credenciales de acceso según las necesidades y especificaciones del sistema
- Configuración del Backend
  - Abra IntelliJ IDEA, el entorno de desarrollo integrado (IDE) preferido para el Backend.
  - A través de la opción "Import Project", seleccione la carpeta del Backend. Siguiendo las instrucciones, importe el proyecto en el entorno.
  - Ahora podrá configurar el Backend del proyecto
  - Desde el IDE, ejecute la aplicación Spring Boot. Esto permitirá que el Backend funcione y esté listo para su interacción.
- Configuración del Frontend
  - Abra Android Studio, el entorno de desarrollo integrado (IDE) preferido para el Frontend
  - A través de la opción "Abrir Proyecto Existente", seleccione la carpeta del Frontend. Siguiendo las instrucciones, importe el proyecto en el entorno.
  - Ahora podrá configurar el Frontend del proyecto
  - Desde el IDE, ejecute la aplicación Android. Esto permitirá que el Frontend funcione y esté listo para su interacción.
- Acceso al Sistema
  - Al encender el backend y el frontend, se podrá acceder al sistema a través del emulador de Android en Android Studio
  - La Instalación del proyecto ha sido exitosa

### Seguridad

En cuanto a la seguridad, se empleará el uso de JWT (Token de Web JSON) para autenticar a los usuarios. Para describir con más
detalle cómo se desarrolla este proceso, se puede recurrir a la explicación proporcionada por Luis Miguel López (2020) en el
sitio web de OpenWebinars. Según su descripción, el proceso se inicia cuando el cliente envía una solicitud POST con el fin de
enviar el nombre de usuario y la contraseña, iniciando así el proceso de inicio de sesión. Posteriormente, se verifica que la
información enviada sea precisa y, en caso afirmativo, se genera un token JWT para entregarlo al usuario.

A partir de ese momento, la aplicación del cliente realizará solicitudes de recursos, siempre acompañadas de ese token JWT en
el encabezado, que se presenta como "Authorization: Bearer XXXXXXX". "Bearer" constituye el prefijo que precede al contenido
del token. Es fundamental tener en mente que el token contiene información sobre la identidad del usuario que hizo la solicitud.

El servidor verifica el token para confirmar su autenticidad, y una vez comprobado y se conoce quién está realizando la
solicitud, se activa un mecanismo de control de acceso para determinar si se permite o no el acceso a la información protegida.

#### Registro de usuarios nuevos

En lo que respecta al proceso de registro de nuevos usuarios, es el propio usuario quien se registra en el sistema a través de un formulario. Además, el usuario tiene la capacidad de dar de baja su cuenta en cualquier momento.

#### Control de acceso de los usuarios y los componentes del sistema

- Inicio:
  - ¿Quiénes pueden acceder?: Todos los que no posean una sesión iniciada
- Registro:
  - ¿Quiénes pueden acceder?: Todos los que no posean una sesión iniciada
- Inicio de Sesión:
  - ¿Quiénes pueden acceder?: Todos los que no posean una sesión iniciada
- Restablecimiento de Contraseña:
  - ¿Quiénes pueden acceder?: Todos los que no posean una sesión iniciada
- Visualización de Registro de Videojuegos:
  - ¿Quiénes pueden acceder?: Todos los que posean una sesión iniciada
- Busqueda:
  - ¿Quiénes pueden acceder?: Todos los que posean una sesión iniciada
- Perfil:
  - ¿Quiénes pueden acceder?: Todos los que posean una sesión iniciada
- Seguimiento de Videojuegos:
  - ¿Quiénes pueden acceder?: Todos los que posean una sesión iniciada
- Actualización de Registro de Juego:
  - ¿Quiénes pueden acceder?: Todos los que posean una sesión iniciada
- Información de la aplicación:
  - ¿Quiénes pueden acceder?: Todos los que posean una sesión iniciada

#### Bitácora de transacciones

La bitácora de transacciones se refiere a la compilación y el registro de todas las acciones y modificaciones
significativas que se realizan en el sistema. Cada vez que se lleva a cabo una transacción, el sistema
automáticamente captura la fecha y hora exactas de la acción, así como los detalles específicos proporcionados
por el usuario responsable. Además, la bitácora categoriza el tipo de transacción, lo que permite una
organización eficiente y facilita la búsqueda de registros.

Toda la información pertinente se guarda en la bitácora de transacciones, lo que garantiza un seguimiento
preciso y una trazabilidad de las actividades realizadas en el sistema a lo largo del tiempo. Este proceso
asegura un historial completo y confiable de las acciones y modificaciones, lo que simplifica la supervisión,
el análisis y la toma de decisiones informadas.

#### Controles sobre la composición y asignación del password (políticas de seguridad)

En este apartado, se establecen las políticas de seguridad relacionadas con la composición y asignación de
contraseñas, garantizando una sólida defensa contra posibles amenazas cibernéticas. Se ha definido una
estrategia integral que aborda la asignación y gestión de contraseñas de manera efectiva y adecuada.
Para esto, se han establecido los siguientes aspectos clave:

-	**Longitud y Composición de Contraseñas**: Las contraseñas deben contener al menos 10 caracteres, incluyendo letras mayúsculas, minúsculas, números y caracteres especiales. Esto garantiza una combinación diversa que hace que las contraseñas sean resistentes a los ataques de fuerza bruta y diccionario. Sin embargo, por motivos de seguridad, en la contraseña se deben evitar los siguientes caracteres especiales:
    -	**\\ (Backslash)**: En algunos sistemas, el carácter de barra invertida puede ser utilizado para comandos especiales o secuencias de escape, lo que podría llevar a interpretaciones incorrectas y posibles vulnerabilidades.
    -	**\' (Comilla simple)**: Las comillas simples pueden ser utilizadas en ataques de inyección de SQL u otros tipos de ataques si no se gestionan adecuadamente en las aplicaciones.
    -	**\" (Comillas dobles)**: Al igual que las comillas simples, las comillas dobles pueden ser explotadas en ataques de inyección o interpretación de datos no seguros.
    -	**\; (Punto y coma)**: En algunos contextos, el punto y coma se utiliza para separar comandos, por lo que su presencia en una contraseña podría ser interpretada incorrectamente en ciertos sistemas.
    -	**\= (Igual)**: Al igual que el punto y coma, el carácter igual a menudo se utiliza en lenguajes de programación y bases de datos para asignar valores, por lo que puede causar problemas si se interpreta incorrectamente.
    -	**\% (Porcentaje)**: En algunos sistemas, el carácter de porcentaje se utiliza para indicar variables o sustituciones, lo que podría causar problemas si se maneja de manera incorrecta.
    -	**\< y \> (Menor que y Mayor que)**: Estos caracteres pueden ser explotados en ataques de inyección, como ataques de script cruzado (XSS), si no se manejan adecuadamente en aplicaciones web.
    -	**\& (Ampersand)**: En lenguajes de marcado o enlaces de consulta en URL, el ampersand se utiliza para separar parámetros, lo que podría causar problemas si no se escapa correctamente.
    -	**\? (Signo de interrogación)**: En URL y enlaces de consulta, el signo de interrogación se utiliza para indicar el inicio de los parámetros de consulta, lo que podría interferir con la interpretación de la contraseña en ciertos contextos.
    -	**\+ (Más)**: Algunos sistemas interpretan el signo más como un espacio en blanco o un operador de concatenación, lo que podría causar problemas en las contraseñas.
-	**Proceso de Cambio de Contraseña**: El sistema ofrecerá un proceso sencillo y seguro para cambiar las contraseñas.
-	**Almacenamiento Seguro de Contraseñas**: Las contraseñas de los usuarios se almacenarán utilizando técnicas avanzadas de cifrado, asegurando que incluso en caso de una posible violación de seguridad, las contraseñas permanezcan inaccesibles para los atacantes. Para almacenar las contraseñas se usará las siguientes técnicas:
    -	**Hashing**: El proceso de hashing implica convertir una contraseña en una cadena de caracteres de longitud fija, llamada hash, que parece aleatoria. Sin embargo, a diferencia de la encriptación, el proceso de hashing es irreversible, lo que significa que no se puede recuperar la contraseña original a partir del hash.
    -	**Salting**: Para evitar los ataques de fuerza bruta y las tablas de búsqueda de hashes precalculados, aplicamos el concepto de "salting". Cada contraseña se combina con una cadena única y aleatoria conocida como "sal" antes de aplicar el hash. Esto garantiza que las contraseñas idénticas tengan hashes diferentes debido a las diferentes sales, haciendo que los ataques de fuerza bruta sean significativamente más difíciles y menos efectivos.
    -	**Utilización de BCrypt**: Para implementar esta técnica de manera eficaz y confiable, se puede usar la biblioteca BCrypt. BCrypt es un algoritmo de hashing seguro y ampliamente utilizado que maneja tanto el proceso de hashing como la generación de sales de manera eficiente y segura.

### Confiabilidad/Disponibilidad

El nivel de disponibilidad establecido para el sistema se fija en un objetivo del 99.9% anual. Esto implica que el
sistema debe mantenerse operativo y accesible para los usuarios durante el 99.9% del tiempo a lo largo de un año,
lo que equivale a un tiempo de inactividad permitido de aproximadamente 8.76 horas anuales. Este nivel de exigencia 
garantiza que los usuarios puedan utilizar el sistema con interrupciones mínimas, ofreciendo una experiencia confiable
y satisfactoria. La arquitectura en capas (cliente-servidor) se encargará de implementar estrategias, como la
escalabilidad, para asegurar que el sistema cumpla con este nivel de disponibilidad, minimizando el impacto de
posibles fallos y manteniendo la continuidad operativa. Todo esto mientras el sistema está en funcionamiento.

Al dividir las funcionalidades en capas separadas, esta arquitectura proporciona un nivel de confiabilidad que
reduce el impacto de fallos en una capa sobre las demás, lo que permite que el sistema siga funcionando en áreas
no afectadas. Esta separación también simplifica la incorporación de mecanismos de tolerancia a fallos y sistemas
de monitoreo, detectando problemas de manera temprana y facilitando una respuesta ágil para prevenir interrupciones.

### Desempeño

El desempeño de un aplicativo móvil es un factor crucial para garantizar una experiencia satisfactoria para los
usuarios y para maximizar la eficiencia de las operaciones. En el caso de "G-Tracker", se establecen requisitos
específicos de desempeño para asegurar un funcionamiento óptimo y fluido, los cuales son:

-	Tiempos Máximos para una Experiencia de Usuario Óptima:
    -	Tiempo de Carga de Pantalla: Se espera que las pantallas principales se carguen en menos de 2 segundos para asegurar una navegación fluida y rápida.
    -	Procesamiento de Transacciones: Las transacciones, como el agregado de un registro para seguimiento, deben completarse en menos de 3 segundos para mantener la interacción eficiente con el sistema.
-	Capacidad de Usuarios Concurrentes: 
    -	La Concurrencia de Usuarios y el tiempo de respuesta promedio para las operaciones estará sujeta al servidor del usuario
-	Optimización de Consultas y Acciones:
    -	Las consultas a la base de datos, como la visualización de juegos y seguimiento de estos, deben ejecutarse de 3 a 4 segundos en un ambiente con conexión óptima para garantizar una respuesta ágil.
-	Escalabilidad:
    -	El sistema debe ser capaz de ser escalable en términos de datos para adaptarse a un aumento inesperado en la carga de usuarios

## Representación de la arquitectura

### Estilo de Arquitectura a implementar en el proyecto

El Estilo de la Arquitectura de este Proyecto es el Estilo Cliente-Servidor

### Descomposición del Sistema

Los Subsistemas del Programa son:

- **Inicio**: Este subsistema se encarga de la página inicial de la aplicación, proporcionando información general y opciones de navegación para los usuarios.
- **Registro**: En este subsistema, los usuarios pueden crear una nueva cuenta en la aplicación
- **Inicio de Sesión**: Permite a los usuarios acceder a sus cuentas existentes proporcionando sus credenciales de inicio de sesión.
- **Perfil**: En el perfil del usuario, se muestra información personal y ajustes de la cuenta
- **Seguimiento de Videojuegos**: Permite a los usuarios realizar un seguimiento de su progreso en los videojuegos registrados
- **Visualización de Registro de Videojuegos**: Este subsistema permite a los usuarios ver una lista de los videojuegos que pueden registrar en su cuenta para realizar seguimiento
- **Más Opciones de Registro de Juego**: Los usuarios pueden utilizar este subsistema para actualizar la información de los videojuegos registrados para seguimiento
- **Mejores Videojuegos**: Permite a los usuarios tener rápidamente los mejores juegos registrados según Metacritic
- **Búsqueda**: Facilita la búsqueda de videojuegos en la base de datos de la aplicación, permitiendo a los usuarios encontrar juegos específicos
- **Información de la Aplicación**: Este subsistema proporciona detalles sobre la aplicación en sí

### Descomposición lógica

Esta sección describe las partes de la arquitectura más significativas del modelo de diseño desde la perspectiva
de diseño del desarrollador, tal como su descomposición en paquetes y subsistemas, y su subsecuente descomposición
en clases

#### Descomposición en Capas

- **Capa de presentación**: La capa de presentación representa la interfaz a través de la cual los usuarios interactúan directamente con el sistema. En esta capa se encuentran las interfaces gráficas que permiten a los usuarios llevar a cabo acciones y visualizar resultados. Su función principal es presentar la información de manera comprensible y proporcionar los medios para que los usuarios interactúen con el sistema. Además, se encarga de la recopilación de información del usuario y transmite las solicitudes a la capa de servicio para su procesamiento.
- **Capa de servicio**: La capa de servicio desempeña un papel fundamental como intermediaria entre la capa de presentación y la capa de datos. En esta configuración específica, la capa de servicio se compone de tres componentes clave: los controladores REST, que funcionan como puntos de entrada para facilitar la comunicación entre la capa de presentación y la capa de solicitudes; el módulo de gestión, encargado de procesar las solicitudes y coordinar la interacción con la capa de negocio; y finalmente, la capa de negocio, que define la gestión y procesamiento de datos, aplicando reglas, cálculos y tomando decisiones basadas en la información proporcionada.
- **Capa de datos**: La capa de datos es una estructura organizada que incorpora patrones como el patrón repositorio y el patrón DAO (Objeto de Acceso a Datos). Estos patrones simplifican el acceso y la manipulación de los datos almacenados en la base de datos. Además, la capa de datos se integra con las entidades, que representan las estructuras de datos en el sistema, y estas entidades se conectan con los mappers en la capa de servicio. La capa de datos, con sus patrones y relaciones estructuradas, juega un papel esencial en garantizar la operación confiable y eficiente del sistema, asegurando la persistencia de datos y una integración efectiva con la capa de servicio para un funcionamiento coherente y sin interrupciones.
- **Capa de persistencia**: La capa de persistencia se encarga de administrar la forma en que los datos se almacenan y recuperan en la base de datos. En esta capa se definen los modelos de datos y se establecen las conexiones con la base de datos. Su función principal es mapear los objetos y estructuras del sistema a tablas y registros en la base de datos, garantizando que los datos se almacenen de manera eficiente y coherente. Además, esta capa gestiona la recuperación de datos y la sincronización con la capa de datos.

##### Capa de negocio

![G-Tracker-Business-UML](/Images/G-Tracker-Business-UML.png)

##### Capa de Datos

![G-Tracker-Data-UML](/Images/G-Tracker-Data-UML.png)