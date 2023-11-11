# Functional and Non-Functional Requirements

## Revision History

| Date  | Version | Description                                               | Author    |
| ----- | ------- | --------------------------------------------------------- | --------- |
| ?     | 0.0.1   | Creation of this document and filling in all its parts   | sh4dow18 |

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

| ID     | Name and Description                                                                                                            |
| ------ | --------------------------------------------------------------------------------------------------------------------------------- |
| RF-001 | **Home**: The system must allow users to access the platform and view a frequently asked questions section. This functionality will be implemented in a home section upon entering the application. Additionally, this section must be visible to anyone accessing the application. Finally, the section must align with the design standards documented. |
| RF-002 | **Account Registration**: The system must allow users to create an account to access the platform. This is done through a form consisting of: Email, Profile Name, Password, and Password Confirmation. This functionality will be implemented through a registration section. Additionally, this section must be visible to anyone entering the application and must conform to the established design standards. |
| RF-003 | **Login**: The system must allow users to log in to their existing accounts. This is done through a form consisting of: Email and Password. This functionality will be implemented through a login section. Additionally, this section must be visible to all users entering the application and must conform to the established design standards. |
| RF-004 | **Game Viewing**: The system must allow viewing all games in the database. For each game entry, it must display: Game Image, Game Name, and the platforms it is available on. This functionality will be implemented through a Game Registration section. Additionally, this section must be visible to any profile of an account with an active session and must conform to the established design standards. |
| RF-005 | **Content Search**: The system must allow searching for games within the platform. This is done through a form consisting of: Game Name. This functionality will be implemented through a search section. Additionally, this section must be visible to any account with an active session and must conform to the established design standards. |
| RF-006 | **Adding Game Tracking Entry**: The system must allow adding a game tracking entry. The form must consist of: Button to Select the Platform and Button to Add Entry. This functionality will be implemented through a Game Tracking Entry section. Additionally, this section must be visible to any account with an active session and must conform to the established design standards. |
| RF-007 | **Profile Viewing**: The system must allow viewing the information of the selected profile. It must display: Profile Image, Profile Name, a button to update the current profile, a button to log out, and another to close the account. This functionality will be implemented through a profile section. Additionally, this section must be visible to any profile of an account with an active session and must conform to the established design standards. |
| RF-008 | **Profile Update**: The system must allow modifying the information of the current profile. This is done through a form consisting of: Profile Image and Profile Name. This functionality will be implemented through a profile section. Additionally, this section must be visible to any account with an active session and must conform to the established design standards. |
| RF-009 | **Account Closure**: The system must allow permanently closing an existing account. This is done through a button. This functionality will be implemented through an account settings section. Additionally, this section must be visible to any account with an active session and must conform to the established design standards. |
| RF-010 | **Log Out**: The system must allow logging out of the current account and returning to the login screen. This is done through a button. This functionality will be implemented through an account settings section. Additionally, this section must be visible to any profile of an account with an active session. Finally, the section must have the application logo at the top of the application. |
| RF-011 | **Game Viewing**: The system must allow viewing all games that have been registered by the user for tracking. For each game entry, it must display: Game Image, Game Name, the platform it was played on, whether the main story is completed or not, and whether it is 100% completed. This functionality will be implemented through a Game Tracking section. Additionally, this section must be visible to any profile of an account with an active session and must conform to the established design standards. |
| RF-012 | **Update Game Tracking Entry**: The system must allow updating the game tracking entry. This is done through a form consisting of: Game Entry to Update, Game Completed, and Game Completed 100%. This functionality will be implemented through a Game Tracking Entry Update section. Additionally, this section must be visible to any account with an active session and must conform to the established design standards. |
| RF-013 | **Application Information Viewing**: The system must allow viewing details of the application. It must display: the application name and program version. This functionality will be implemented through an application information section. Additionally, this section must be visible to any account with an active session and must conform to the established design standards. |
| RF-014 | **Credits**: The system must allow displaying the creator's credits. It must display: Creator's Name, Creator's Page, Creator's Github Page, Creator's Company Name, Company's Page, Company's Facebook Page, Company's Phone Number, and Company's Email. This functionality will be implemented through an application information section. Additionally, this section must be visible to any account with an active session and must conform to the established design standards. |

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
