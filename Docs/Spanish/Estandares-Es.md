# Estandares

## Historial de Revisión

| Fecha | Versión | Descripción | Autor |
| ----- | ------- | ----------- | ----- |
| 08/12/2023 | 1.0.0 | Creación de este documento y rellenado de todas las partes de este | sh4dow18 |

## Indice

- [Estandares](#estandares)
  - [Historial de Revisión](#historial-de-revisión)
  - [Indice](#indice)
  - [Registro de Estandarés de Programación](#registro-de-estandarés-de-programación)
  - [Registro de Estandarés de Base de Datos](#registro-de-estandarés-de-base-de-datos)
  - [Registro de Estandarés de Diseño](#registro-de-estandarés-de-diseño)
  - [Anexos](#anexos)
    - [Anexo 1: Documentación](#anexo-1-documentación)
    - [Anexo 2: Cabeza de Pantalla](#anexo-2-cabeza-de-pantalla)
    - [Anexo 3: Cuerpo de Pantalla](#anexo-3-cuerpo-de-pantalla)
    - [Anexo 4: Pie de Pantalla](#anexo-4-pie-de-pantalla)

## Registro de Estandarés de Programación

| Descripción | Detalle | Ejemplo |
| ----------- | ------- | ------- |
| Documentación | Cada sección crucial de los archivos debe documentarse en inglés. | Ver Anexo 1 |
| Asignar nombres a variables, atributos y parámetros | El nombre, en formato camelCase y en inglés, debe ser explícito y claramente indicar su propósito de uso. | var counter = 0 |
| Definición de Funciones | El nombre debe adoptar el formato camelCase en inglés y ser explícito en cuanto a su utilidad. | fun findById() |
| Definición de Entidades de Springboot | El nombre debe seguir el formato PascalCase en inglés y ser explícito en cuanto a su utilidad. | User |
| Definición de Mapeadores, Servicios, Controladores REST y Repositorios de Springboot | El formato del nombre debe ser PascalCase en inglés, y el nombre de la entidad debe ir seguido de "Mapper", "Service", "Controller" o "Repository", según corresponda. | UserMapper |
| Definición de listas | El formato del nombre debe ser camelCase en inglés, y el nombre de la entidad en minúscula y en plural, seguido de la palabra "List". | usersList |
| Definición de DTOs | El formato del nombre debe ser en PascalCase en Inglés, y el nombre debe seguir la siguiente estructura: "(Qué función)(Hacia Qué)(Qué es)" | RegisterUserRequest |

## Registro de Estandarés de Base de Datos

| Descripción | Detalle | Ejemplo |
| ----------- | ------- | ------- |
| Nombre de la base de datos | El nombre del proyecto debe seguir el formato camelCase en ingles, reflejando con precisión la naturaleza del desarrollo. | GTracker |
| Nombres de las tablas | El nombre de la entidad debe adoptar el formato snake_case, ser plural y reflejar la naturaleza de la entidad a la que corresponde, todo en inglés | game_logs |
| Nombres de las Columnas de las Tablas | El nombre, en formato snake_case y en inglés, debe ser explícito y claramente indicar su propósito de uso. | action_type |
| Nombre de Tablas Intermedias | El formato del nombre debe ser snake_case en inglés, siguiendo la estructura de "PrimeraEntidad_SegundaEntidad" para reflejar la relación entre las entidades. | role_page |

## Registro de Estandarés de Diseño

| Descripción | Detalle | Ejemplo |
| ----------- | ------- | ------- |
| Cabeza de Pantalla | El diseño incluye un fondo de color Naranja G-Tracker (#B4F6DB9D), con texto en color negro (#000000), menú en formato hamburguesa, separación visual mediante una línea negra de 2 píxeles de alto. El menú debe ubicarse a la izquierda de la firma, la cual debe centrarse en el encabezado y mostrar el logo de G-Tracker diseñado por Sh4dow18. | Ver Anexo 2 |
| Cuerpo de Pantalla | La tipografía debe ser sans-serif, con color de letra negro (#000000), color primario en Naranja G-Tracker (#B4F6DB9D), y color de fondo en Naranja Fondo G-Tracker (#DFC397). | Ver Anexo 3 |
| Pie de Pantalla | El fondo debe ser negro (#000000), con tipografía sans-serif, y la firma debe presentar "Sh4dow18" y "Sh4dowtech". | Ver Anexo 4 |

## Anexos

### Anexo 1: Documentación

![G-Tracker-Docs](/Images/G-Tracker-Docs.png)

### Anexo 2: Cabeza de Pantalla

![G-Tracker-Header](/Images/G-Tracker-Header.png)

### Anexo 3: Cuerpo de Pantalla

![G-Tracker-Body](/Images/G-Tracker-Body.png)

### Anexo 4: Pie de Pantalla

![G-Tracker-Footer](/Images/G-Tracker-Footer.png)