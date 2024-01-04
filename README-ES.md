# G-Tracker

![G-Tracker-Logo](/Images/G-Tracker%20Documentación%20con%20Fondo.png)

[![Release](https://img.shields.io/badge/Lanzamiento-1.0.1-FFA500?style=for-the-badge&logo=gpl&labelColor=gray)](https://github.com/sh4dow18/G-Tracker/releases/tag/1.0.1)
[![Licencia GPL v3.0](https://img.shields.io/badge/Licencia-GPL%203.0-0000FF?style=for-the-badge&logo=gpl&labelColor=gray)](https://github.com/sh4dow18/G-Tracker/blob/main/LICENSE)

G-Tracker está construido con las siguientes tecnologías:

[![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&labelColor=gray)](https://kotlinlang.org)
[![Spring](https://img.shields.io/badge/Spring-80EA6E?style=for-the-badge&logo=spring&labelColor=gray)](https://spring.io)
[![Android Studio](https://img.shields.io/badge/Android%20Studio-50AD55?style=for-the-badge&logo=android-studio&labelColor=gray)](https://developer.android.com/studio)

## Versión en Inglés (English Version)

La versión en ingles de este archivo y de toda la documentación se puede acceder al hacer clic **[here](/README.md)**

## Resumen

**G-Tracker** es la solución definitiva para jugadores apasionados que buscan un seguimiento sin esfuerzo de su historial de juegos. ¿Cansado de perder datos importantes, desorganización y dificultades para compartir tus logros? Con G-Tracker, olvídate de los registros en papel y los archivos de texto. Esta innovadora plataforma ofrece una experiencia integral que no solo te permite realizar un seguimiento eficiente de tus juegos, sino que también aprovecha fuentes externas para proporcionar información actualizada, reseñas y calificaciones. ¡Maximiza tu experiencia de juego con G-Tracker y mantente motivado, organizado y completamente inmerso en el mundo de los videojuegos!

## Versión

Versión: **2.0.0**

## Licencia

Este proyecto está bajo la licencia de **GNU General Public License v3.0**.

Si deseas obtener más información sobre esta licencia, haz clic
**[aquí](https://www.gnu.org/licenses/gpl-3.0.html)**

## Documentación Completa

Para ver la documentación completa y actualizada de este proyecto, puedes hacer clic en cualquiera de los siguientes enlaces y te redirigirá al documento que deseas consultar:

- **[Arquitectura](/Docs/Spanish/Arquitectura-Es.md)**
- **[Mockups](/Docs/Spanish/Mockups-Es.md)**
- **[Requerimientos](/Docs/Spanish/Requerimientos-Es.md)**
- **[Plan de Administración de Sprints](/Docs/Spanish/Plan-de-Administración-de-Sprints-Es.md)**
- **[Estándares](/Docs/Spanish/Estandares-Es.md)**
- **[Visión](/Docs/Spanish/Vision-Es.md)**

## Actualizaciones

### 2.0.0 (2024-01-03)

- Se cambió el diseño de la interfaz de usuario del juego y el registro del juego.
- Se corrigió el error que existe cuando un juego no tiene una lista de géneros.
- Se cambió los tipos de datos "Finalizado" y "Finalizado en total" de Booleano a ZonedDateTime.
- Se añadió sección de Funciones Adicionales en el Registro del Juego.
- Se añadió función para actualizar fechas (Fecha de Creación, Fecha de Finalización y Fecha al 100%).
- Mejora en los manejadores de errores para utilizar los Errores de la API en lugar de errores locales.
- Se actualizó la configuración de seguridad JWT para el DSL personalizado utilizando "with" en lugar de "apply".
- Se añadieron funciones de respaldo para los registros de juegos.
- Se cambiaron los iconos de los menus
- Se añadió la funcionalidad de la bitacora de transacciones en el Backend
- Se añadió la función de eliminación de Registro de Juego y de Registro General
- Se añadió un aviso extra para la función de cerrar la cuenta en el Frontend

### 1.0.1 (2023-12-10)

- Se añadió el Modo de Transacción en los Servicios del Backend que crean y actualizan en la base de datos. Esto es para tener la misma información con 2 instancias de base de datos diferentes.
- Se añadió la Verificación de Conflicto de Plataformas y Géneros.
- Mejora en la optimización de la búsqueda de juegos y el seguimiento de juegos.
- Se añadió la sección de Mejores Juegos.
- Se añadió una explicación sobre los botones "Terminado" y "Terminado al 100%" en el Registro de Juegos.
- Se añadio la versión en español de "README.md"

Eso es todo por ahora. Come verduras y que tengas un buen día :D
