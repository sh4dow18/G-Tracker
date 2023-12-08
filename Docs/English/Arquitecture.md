# Architecture

## Revision History

| Date  | Version | Description                                               | Author    |
| ----- | ------- | --------------------------------------------------------- | --------- |
| 12/08/2023     | 1.0.0   | Creation of this document and filling in all its parts   | sh4dow18 |

## Table of Contents

- [Architecture](#architecture)
  - [Revision History](#revision-history)
  - [Table of Contents](#table-of-contents)
  - [Introduction](#introduction)
    - [Purpose](#purpose)
    - [Scope of the Architecture Document](#scope-of-the-architecture-document)
  - [Goals and Limitations](#goals-and-limitations)
    - [Technical Platform](#technical-platform)
    - [Portability](#portability)
      - [Production Installation Guide](#production-installation-guide)
      - [Developers Installation Guide](#developers-installation-guide)
    - [Security](#security)
      - [Registration of New Users](#registration-of-new-users)
      - [User and System Component Access Control](#user-and-system-component-access-control)
      - [Transaction Logs](#transaction-logs)
      - [Password Composition and Assignment Controls (Security Policies)](#password-composition-and-assignment-controls-security-policies)
    - [Reliability/Availability](#reliabilityavailability)
    - [Performance](#performance)
  - [Architecture Representation](#architecture-representation)
    - [Architecture Style to Implement in the Project](#architecture-style-to-implement-in-the-project)
    - [System Decomposition](#system-decomposition)
    - [Logical Decomposition](#logical-decomposition)
      - [Layer Decomposition](#layer-decomposition)
        - [Business Layer](#business-layer)
        - [Data Layer](#data-layer)

## Introduction

### Purpose

The purpose of this document is to summarize the architecture of the system and present the technical manual for the "G-Tracker" system.

### Scope of the Architecture Document

Identify the technical elements required for the comprehensive development of the software project called "G-Tracker," which will be implemented.

## Goals and Limitations

In this section, technical requirements and special conditions considered in the development and implementation of the System are presented. Non-functional requirements related to execution, availability, fault tolerance, integrity, etc., are addressed.

### Technical Platform

The product being developed is based on a solid and well-defined architecture, covering both the frontend and the backend. The main platforms and technologies to be used in the project are:

- General Architecture: The project will be organized into different layers to ensure the modularity and scalability of the system.
- Frontend Architecture: The MVVM pattern will be used for frontend development. This allows the separation of presentation logic from data and improves the end-user experience.
- Backend Architecture: The backend follows a layered approach to ensure separation of concerns and facilitate maintenance.
- Languages: Kotlin will be used for both frontend and backend to create attractive and functional user interfaces, as well as for its robustness and development efficiency.
- Development Environments (IDE): Android Studio will be used for frontend development, and IntelliJ IDEA will be used for backend development.
- Frameworks and Libraries: The frontend user interface will be built using various existing libraries for mobile platform development. In the backend, Spring Boot, a widely used framework in Java and Kotlin application development, will be used to accelerate the creation of robust and secure APIs.
- Database: A relational database approach will be followed to ensure data integrity. PostgreSQL will be used as the database management system due to its reliability and advanced capabilities.

### Portability

This application will be accessible only for mobile devices with integrated Android.

#### Production Installation Guide

The following guide provides the necessary steps to install the system in a production environment using stable and optimized versions for both the Frontend and Backend. Stable releases will be used to ensure reliability and performance.

- Preparations
  - Java 11. Download link: [https://jdk.java.net/archive/](https://jdk.java.net/archive/)
  - PostgreSQL 15.4 or higher. Download link: [https://www.postgresql.org/download/](https://www.postgresql.org/download/)
- Download Stable Versions
  - The first step is to download stable versions of both the Frontend and Backend. This can be done from the following GitHub sources: [https://github.com/sh4dow18/G-Tracker](https://github.com/sh4dow18/G-Tracker)
  - Navigate to the "Releases" section of each link.
  - Download the latest stable version for both the Frontend and Backend. These versions will be the optimized builds for production.
- Database Configuration
  - During the PostgreSQL installation, configure the access credentials according to the needs and specifications of the system.
- Backend Configuration
  - In a terminal, navigate to the folder containing the Backend executable.
  - Run the command "java -jar g-tracker-backend.jar."
  - You can now use the backend of the project.
- Frontend Configuration
  - On the Android device, install the "G-Tracker-App.apk" file.
  - You can now use the Frontend of the project.
- Accessing the System
  - Open the "G-Tracker" application on your device.
  - Once in the mobile application, you should be able to view and use the system. The installation is successful.

#### Developers Installation Guide

- Preparations
  - Android Studio Giraffe 2022.3.1: [https://developer.android.com/studio](https://developer.android.com/studio)
  - IntelliJ IDEA Community Edition 2023.2.4: [https://www.jetbrains.com/es-es/idea/download/?section=linux](https://www.jetbrains.com/es-es/idea/download/?section=linux)
  - Java 11. Download link: [https://jdk.java.net/archive/](https://jdk.java.net/archive/)
  - PostgreSQL 15.4 or higher. Download link: [https://www.postgresql.org/download/](https://www.postgresql.org/download/)
- Clone the Repositories
  - The first step is to clone the system repositories. This action will obtain the necessary source code for the installation and execution of the application. This can be done from the following GitHub sources.
- Database Configuration
  - During the PostgreSQL installation, configure the access credentials according to the needs and specifications of the system.
- Backend Configuration
  - Open IntelliJ IDEA, the preferred Integrated Development Environment (IDE) for the Backend.
  - Using the "Import Project" option, select the Backend folder. Follow the instructions to import the project into the environment.
  - You can now configure the Backend of the project.
  - From the IDE, run the Spring Boot application. This will allow the Backend to function and be ready for interaction.
- Frontend Configuration
  - Open Android Studio, the preferred Integrated Development Environment (IDE) for the Frontend.
  - Using the "Open Existing Project" option, select the Frontend folder. Follow the instructions to import the project into the environment.
  - You can now configure the Frontend of the project.
  - From the IDE, run the Android application. This will allow the Frontend to function and be ready for interaction.

- Accessing the System
  - Once the backend and frontend are running, you can access the system through the Android emulator in Android Studio.
  - The project installation is successful.

### Security

Regarding security, JWT (JSON Web Token) will be used to authenticate users. For a detailed description of how this process is developed, Luis Miguel López's (2020) explanation on the OpenWebinars website can be referenced. According to his description, the process begins when the client sends a POST request to send the username and password, initiating the login process. Subsequently, it verifies that the information sent is accurate, and if so, it generates a JWT token to deliver to the user.

From that moment on, the client application will make resource requests, always accompanied by that JWT token in the header, presented as "Authorization: Bearer XXXXXXX." "Bearer" constitutes the prefix preceding the token content. It is crucial to keep in mind that the token contains information about the identity of the user making the request.

The server verifies the token to confirm its authenticity, and once verified and the user making the request is known, an access control mechanism is activated to determine whether access to the protected information is allowed or not.

#### Registration of New Users

As for the process of registering new users, the user registers in the system through a form. Additionally, the user has the ability to deactivate their account at any time.

#### User and System Component Access Control

- Login:
  - Who can access: Everyone without an active session.
- Registration:
  - Who can access: Everyone without an active session.
- Login:
  - Who can access: Everyone without an active session.
- Password Reset:
  - Who can access: Everyone without an active session.
- Viewing Game Records:
  - Who can access: Everyone with an active session.
- Search:
  - Who can access: Everyone with an active session.
- Profile:
  - Who can access: Everyone with an active session.
- Game Tracking:
  - Who can access: Everyone with an active session.
- Game Record Update:
  - Who can access: Everyone with an active session.
- Application Information:
  - Who can access: Everyone with an active session.

#### Transaction Logs

The transaction log refers to the compilation and logging of all significant actions and modifications performed in the system. Each time a transaction takes place, the system automatically captures the exact date and time of the action, as well as the specific details provided by the responsible user. Additionally, the log categorizes the type of transaction, allowing efficient organization and facilitating record retrieval.

All relevant information is stored in the transaction log, ensuring precise tracking and traceability of activities performed in the system over time. This process ensures a complete and reliable history of actions and modifications, simplifying monitoring, analysis, and informed decision-making.

#### Password Composition and Assignment Controls (Security Policies)

In this section, security policies related to password composition and assignment are established, ensuring a robust defense against potential cyber threats. A comprehensive strategy addressing password assignment and management has been defined. The following key aspects have been established:

- **Password Length and Composition**: Passwords must contain at least 10 characters, including uppercase letters, lowercase letters, numbers, and special characters. This ensures a diverse combination that makes passwords resistant to brute force and dictionary attacks. However, for security reasons, the following special characters should be avoided in the password:
    - `\\ (Backslash)`: In some systems, the backslash character can be used for special commands or escape sequences, which could lead to incorrect interpretations and possible vulnerabilities.
    - `\' (Single Quote)`: Single quotes can be used in SQL injection attacks or other types of attacks if not handled properly in applications.
    - `\" (Double Quote)`: Like single quotes, double quotes can be exploited in injection attacks or unsafe data interpretation.
    - `\; (Semicolon)`: In some contexts, the semicolon is used to separate commands, so its presence in a password could be interpreted incorrectly in certain systems.
    - `\= (Equal)`: Like the semicolon, the equal sign is often used in programming languages and databases to assign values, so it can cause problems if interpreted incorrectly.
    - `\% (Percent)`: In some systems, the percent character is used to indicate variables or substitutions, which could cause problems if not handled correctly.
    - `\< and \> (Less than and Greater than)`: These characters can be exploited in injection attacks, such as cross-site scripting (XSS), if not handled properly in web applications.
    - `\& (Ampersand)`: In markup languages or URL query links, the ampersand is used to separate parameters, which could cause problems if not escaped correctly.
    - `\? (Question Mark)`: In URLs and query links, the question mark is used to indicate the start of query parameters, which could interfere with the interpretation of the password in certain contexts.
    - `\+ (Plus)`: Some systems interpret the plus sign as a whitespace or concatenation operator, which could cause problems in passwords.
- **Password Change Process**: The system will offer a simple and secure process for changing passwords.
- **Secure Password Storage**: User passwords will be stored using advanced encryption techniques, ensuring that even in the event of a possible security breach, passwords remain inaccessible to attackers. The following techniques will be used for password storage:
    - **Hashing**: The hashing process involves converting a password into a fixed-length string of characters, called a hash, that appears random. However, unlike encryption, the hashing process is irreversible, meaning that the original password cannot be recovered from the hash.
    - **Salting**: To prevent brute force attacks and precomputed hash lookup tables, the concept of "salting" is applied. Each password is combined with a unique and random string known as "salt" before applying the hash. This ensures that identical passwords have different hashes due to different salts, making brute force attacks significantly more difficult and less effective.
    - **Use of BCrypt**: To implement this technique effectively and reliably, the BCrypt library can be used. BCrypt is a secure and widely used hashing algorithm that handles both the hashing process and salt generation efficiently and securely.

### Reliability/Availability

The availability level set for the system is fixed at a target of 99.9% annually. This implies that the system must remain operational and accessible to users for 99.9% of the time over a year, equivalent to an allowed downtime of approximately 8.76 hours annually. This level of requirement ensures that users can use the system with minimal interruptions, providing a reliable and satisfactory experience. The client-server architecture will implement strategies such as scalability to ensure the system meets this availability level, minimizing the impact of potential failures and maintaining operational continuity while the system is running.

By dividing functionalities into separate layers, this architecture provides a level of reliability that reduces the impact of failures in one layer on the others, allowing the system to continue functioning in unaffected areas. This separation also simplifies the integration of fault tolerance mechanisms and monitoring systems, detecting issues early and facilitating a responsive approach to prevent disruptions.

### Performance

The performance of a mobile application is a crucial factor in ensuring a satisfactory experience for users and maximizing operational efficiency. For "G-Tracker," specific performance requirements are established to ensure optimal and smooth operation:

- **Maximum Times for Optimal User Experience**:
  - Screen Load Time: Main screens are expected to load in less than 2 seconds to ensure smooth and fast navigation.
  - Transaction Processing: Transactions, such as adding a record for tracking, must be completed in less than 3 seconds to maintain efficient interaction with the system.
- **Concurrent User Capacity**:
  - User concurrency and average response time for operations will depend on the user's server.
- **Query and Action Optimization**:
  - Database queries, such as viewing and tracking games, should execute within 3 to 4 seconds in an environment with optimal connection to ensure responsive feedback.
- **Scalability**:
  - The system must be scalable in terms of data to accommodate an unexpected increase in user load.

## Architecture Representation

### Architecture Style to Implement in the Project

The architecture style for this project is the Client-Server Style.

### System Decomposition

The Program's Subsystems are:

- **Home**: This subsystem handles the application's home page, providing general information and navigation options for users.
- **Registration**: In this subsystem, users can create a new account in the application.
- **Login**: Allows users to access their existing accounts by providing their login credentials.
- **Password Reset**: Offers users the ability to reset their password if forgotten.
- **Viewing Game Records**: This subsystem allows users to view a list of games they can register in their account for tracking.
- **Search**: Facilitates the search for games in the application's database, allowing users to find specific games.
- **Profile**: In the user's profile, personal information and account settings are displayed.
- **Game Tracking**: Allows users to track their progress in registered games.
- **Game Record Update**: Users can use this subsystem to update information about registered games for tracking.
- **Application Information**: This subsystem provides details about the application itself.

### Logical Decomposition

This section describes the most significant parts of the architecture design model from the developer's design perspective, such as its decomposition into packages and subsystems, and its subsequent decomposition into classes.

#### Layer Decomposition

- **Presentation Layer**: The presentation layer represents the interface through which users directly interact with the system. It includes graphical interfaces that allow users to perform actions and view results. Its main function is to present information understandably and provide means for users to interact with the system. Additionally, it handles user information collection and transmits requests to the service layer for processing.
- **Service Layer**: The service layer plays a crucial role as an intermediary between the presentation layer and the data layer. In this specific configuration, the service layer consists of three key components: REST controllers, serving as entry points to facilitate communication between the presentation layer and request layer; the management module, responsible for processing requests and coordinating interaction with the business layer; and finally, the business layer, defining data management and processing, applying rules, calculations, and making decisions based on the provided information.
- **Data Layer**: The data layer is an organized structure that incorporates patterns such as the repository pattern and the data access object (DAO) pattern. These patterns simplify access and manipulation of data stored in the database. Additionally, the data layer integrates with entities, representing data structures in the system, and these entities connect with mappers in the service layer. The data layer, with its patterns and structured relationships, plays an essential role in ensuring the reliable and efficient operation of the system, ensuring data persistence and effective integration with the service layer for coherent and uninterrupted operation.
- **Persistence Layer**: The persistence layer is responsible for managing how data is stored and retrieved in the database. In this layer, data models are defined, and connections with the database are established. Its primary function is to map objects and structures of the system to tables and records in the database, ensuring data is stored efficiently and consistently. Additionally, this layer manages data retrieval and synchronization with the data layer.

##### Business Layer

![G-Tracker-Business-UML](/Images/G-Tracker-Business-UML.png)

##### Data Layer

![G-Tracker-Data-UML](/Images/G-Tracker-Data-UML.png)
