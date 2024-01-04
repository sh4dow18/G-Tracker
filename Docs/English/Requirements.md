# Functional and Non-Functional Requirements

## Revision History

| Date  | Version | Description                                               | Author    |
| ----- | ------- | --------------------------------------------------------- | --------- |
| 12/08/2023     | 1.0.0   | Creation of this document and filling in all its parts   | sh4dow18 |

## Table of Contents

- [Functional and Non-Functional Requirements](#functional-and-non-functional-requirements)
  - [Revision History](#revision-history)
  - [Table of Contents](#table-of-contents)
  - [Introduction](#introduction)
  - [Document Scope](#document-scope)
  - [Glossary](#glossary)
  - [Functional Requirements](#functional-requirements)
  - [Non-Functional Requirements](#non-functional-requirements)
  - [Conclusion](#conclusion)

## Introduction

This document aims to define the scope of the video game tracking platform system, as well as the associated functional and non-functional requirements. This platform is designed to provide users with a real-time tracking experience of their gaming activities, allowing them to access and visualize their game history and achievements effectively and easily.

With this document, we continue a series of files intended to document the progress and development of the video game tracking platform, establishing its key features and providing an overview of its functionality and scope.

## Document Scope

The document encompasses all the requirements of the "G-Tracker" system that have been agreed upon by all involved parties, describing the functionality and constraints offered by the system. In summary, it details two fundamental elements: the list of functional requirements and the list of non-functional requirements.

## Glossary

- System: Computer System

## Functional Requirements

| ID | Name and Description |
|----|----------------------|
| RF-001 | **Home**: The system must allow users to access the platform and view an explanation of what the G-Tracker system is. This functionality will be implemented in a home section upon entering the application. Additionally, this section should be visible to everyone entering the application. Finally, the section must adhere to the design established in the standards document. |
| RF-002 | **Account Registration**: The system must allow users to create an account to access the platform. This should be done through a form, consisting of: Email, Profile Name, Password, and Password Confirmation. This functionality will be implemented through a registration section. Additionally, this section should be visible to everyone entering the application. Finally, the section must adhere to the design established in the standards document. |
| RF-003 | **Login**: The system must allow users to log in to their existing accounts. This should be done through a form, consisting of: Email and Password. This functionality will be implemented through a login section. Additionally, this section should be visible to every user entering the application. Finally, the section must adhere to the design established in the standards document. |
| RF-004 | **Profile View**: The system must allow viewing the information of the selected profile. This should display at least: Profile Picture, Profile Name, a button to update the current profile, a button to log out, and another to close the account. This functionality will be implemented through a profile section. Additionally, this section should be visible to any profile of an account with an active session. Finally, the section must adhere to the design established in the standards document. |
| RF-005 | **Profile Update**: The system must allow modifying the information of the current profile. This should be done through a form, consisting of: Profile Picture and Profile Name. This functionality will be implemented through a profile update section. Additionally, this section should be visible to any account with an active session. Finally, the section must adhere to the design established in the standards document. |
| RF-006 | **Logout**: The system must allow users to log out of the current account and return to the login screen. This should be done through a button. This functionality will be implemented through a profile section. Additionally, this section should be visible to any profile of an account with an active session. Finally, the section must adhere to the design established in the standards document. |
| RF-007 | **Account Closure**: The system must allow permanently closing an existing account. This should be done through a button. This functionality will be implemented through a profile section. Additionally, this section should be visible to any account with an active session. Finally, the section must adhere to the design established in the standards document. |
| RF-008 | **Game Tracking**: The system must allow users to view all games registered for tracking. This should display for each game entry: Game Image, Game Name, whether the game is finished, and whether the game is 100% completed. This functionality will be implemented through a Game Tracking section. Additionally, this section should be visible to any profile of an account with an active session. Finally, the section must adhere to the design established in the standards document. |
| RF-009 | **Game Tracking Register Information View**: The system must allow viewing the information of a specific game tracking entry. This should display all attributes of a game tracking entry. This functionality will be implemented through a Game Tracking Entry Information section. Finally, the section must adhere to the design established in the standards document. |
| RF-010 | **Game Tracking Register Update**: The system must allow updating the game tracking entry. This should be done through a form, consisting of: Game Finished Button and 100% Completed Button. This functionality will be implemented through a Game Entry section. Additionally, this section should be visible to any account with an active session. Finally, the section must adhere to the design established in the standards document. |
| RF-011 | **Update of a Date of Game Tracking Register**: The system must allow the user to change dates in a video game entry they have in their game tracking. This should be done through a form, consisting of: Date to be updated, new date selector, and new time selector. This functionality will be implemented through a More Functions section in the Game Log. Finally, the section must adhere to the design established in the standards document. |
| RF-012 | **Deletion of a Game Tracking Entry**: The system must allow the user to delete a video game entry they have in their game tracking. This should be done through a form, consisting of: Delete Entry Button. This functionality will be implemented through a More Functions section in the Game Log. Finally, the section must adhere to the design established in the standards document. |
| RF-013 | **Top Games View**: The system must allow viewing the top 10 registered games. This should be done through a list. Each element should contain: Game Image, Game Name, user rating, and Metacritic rating. This functionality will be implemented through a Top Games section. Finally, the section must adhere to the design established in the standards document. |
| RF-014 | **Game Search**: The system must allow searching for games within the platform. This should be done through a form, consisting of: Game Name. Additionally, this section should be visible to any account with an active session. Finally, the section must adhere to the design established in the standards document. |
| RF-015 | **Game Information View**: The system must allow viewing the information of a specific game. This should display all attributes of a game. This functionality will be implemented through a Game Information section. Finally, the section must adhere to the design established in the standards document. |
| RF-016 | **Addition of Game Tracking Entry**: The system must allow adding a game entry for tracking. The form should consist of: Add Entry Button. Additionally, this section should be visible to any account with an active session. Finally, the section must adhere to the design established in the standards document. |
| RF-017 | **App Information View**: The system must allow viewing details of the application. This should display: The name of the application and the program version. This functionality will be implemented through an Application Information section. Additionally, this section should be visible to any account with an active session. Finally, the section must adhere to the design established in the standards document. |
| RF-018 | **Credits**: The system must allow displaying the creator's credits. This should display: Creator's Name, Creator's Page, Creator's Github Page, Creator's Company Name, Company's Page, Company's Facebook Page, Company's Phone Number, and Company's Email. This functionality will be implemented through an Application Information section. Additionally, this section should be visible to any account with an active session. Finally, the section must adhere to the design established in the standards document. |


## Non-Functional Requirements

| ID     | Name and Description                                                                                              |
| ------ | ------------------------------------------------------------------------------------------------------------------- |
| RNF-001 | **Usability**: The system must ensure it is intuitive and easy to use for users, promoting a friendly and efficient user experience. |
| RNF-002 | **Security**: The system must ensure the protection of data and system resources against unauthorized access and malicious attacks, guaranteeing the integrity, confidentiality, and availability of information. |
| RNF-003 | **Real-Time Updates**: The system must allow real-time updates to reflect changes, ensuring that information is always accurate and up-to-date. |
| RNF-004 | **Scalability**: The system must ensure it can adapt and expand without a loss of performance as the number of users, data volume, or application features increase. |
| RNF-005 | **Maintainability and Ease of Maintenance**: The system must facilitate the task of maintaining and improving the system over time, with a clean and structured design that facilitates modifications and corrections. |

## Conclusion

In conclusion, the presented functional and non-functional requirements for the video game tracking system are essential for the development of a robust and highly efficient platform. The clarity and specificity of the functional requirements ensure that key functionalities are implemented properly.

Similarly, the non-functional requirements ensure an optimal and satisfying user experience. Careful consideration of these aspects throughout the development cycle will allow the creation of a video game tracking system capable of meeting the needs of the current market and exceeding user expectations, positioning it as a leading option in the game tracking industry.
