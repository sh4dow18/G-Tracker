# G-Tracker

![G-Tracker-Logo](/Images/G-Tracker%20Documentación%20con%20Fondo.png)

[![Release](https://img.shields.io/badge/Release-2.0.0-FFA500?style=for-the-badge&logo=gpl&labelColor=gray)](https://github.com/sh4dow18/G-Tracker/releases/tag/1.0.1)
[![GPLv3 license](https://img.shields.io/badge/License-GPL%203.0-0000FF?style=for-the-badge&logo=gpl&labelColor=gray)](https://github.com/sh4dow18/G-Tracker/blob/main/LICENSE)

G-Tracker is built with the following Technologies:

[![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&labelColor=gray)](https://kotlinlang.org)
[![Spring](https://img.shields.io/badge/Spring-80EA6E?style=for-the-badge&logo=spring&labelColor=gray)](https://spring.io)
[![Android Studio](https://img.shields.io/badge/Android%20Studio-50AD55?style=for-the-badge&logo=android-studio&labelColor=gray)](https://developer.android.com/studio)

## Spanish Version (Versión en Español)

The Spanish version of this readme and all documentation can be access if you click **[here](/README-ES.md)**

## Overview

**G-Tracker** is the ultimate solution for passionate gamers seeking effortless tracking of their gaming history. Tired of losing important data, disorganization, and difficulties in sharing your achievements? With G-Tracker, forget about paper records and text files. This innovative platform offers a comprehensive experience that not only allows you to efficiently track your games but also leverages external sources to provide updated information, reviews, and ratings. Maximize your gaming experience with G-Tracker and stay motivated, organized, and fully immersed in the world of video games!

## Version

Version: **2.0.0**

## License

This project is licensed under a **GNU General Public License v3.0**.

If you want to know about this license, click
**[here](https://www.gnu.org/licenses/gpl-3.0.html)**

## Full Documentation

To see the complete and updated documentation of this project, you can click on any of the following links and will redirect you the document that you want to see:

- **[Arquitecture](/Docs/English/Arquitecture.md)**
- **[Mockups](/Docs/English/Mockups.md)**
- **[Requirements](/Docs/English/Requirements.md)**
- **[Sprint Management Plan](/Docs/English/Sprint-Management-Plan.md)**
- **[Standarts](/Docs/English/Standards.md)**
- **[Vision](/Docs/English/Vision.md)**

## Updates (Sorted By Date)

### 2.0.0 (2024-01-03)

- Changed Game and Game log UI
- Fixed bug when a game has not a genres list
- Changed "Finished" and "Finished At All" data types from Boolean to ZonedDateTime
- Added Game Log More Functions Section
- Added function to update dates (Created Date, Finished Date and 100% Date)
- Improved the Error handlers to use the API Errors instead local errors
- Updated the JWT Security Configuration for Custom DSL using "with" instead of "apply"
- Added Backup Functions for Game Logs
- Changed Menus Icons
- Added Transaction log functionality to the backend.
- Added Game log and General log deletion functions
- Added an additional message the account closure function in the frontend

### 1.0.1 (2023-12-10)

- Added Transaction Mode in Backend Services that create and update in database. This is to have the same information with 2 different database instances
- Added Platform and Genres Conflict Verification
- Improved optimization in game searching and game tracking
- Added Top Games Section
- Added explanation about the "Finished" and "Finished At All" buttons in Game Log
- Added "README.md" Spanish Version

This is all for now. Eat vegetables and have a nice day :D
