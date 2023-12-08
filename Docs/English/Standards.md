# Standards

## Revision History

| Date | Version | Description | Author |
| ----- | ------- | ----------- | ----- |
| 12/08/2023     | 1.0.0   | Creation of this document and filling in all its parts   | sh4dow18 |

## Index

- [Standards](#standards)
  - [Revision History](#revision-history)
  - [Index](#index)
  - [Programming Standards Record](#programming-standards-record)
  - [Database Standards Record](#database-standards-record)
  - [Design Standards Record](#design-standards-record)
  - [Appendices](#appendices)
    - [Appendix 1: Documentation](#appendix-1-documentation)
    - [Appendix 2: Screen Header](#appendix-2-screen-header)
    - [Appendix 3: Screen Body](#appendix-3-screen-body)
    - [Appendix 4: Screen Footer](#appendix-4-screen-footer)

## Programming Standards Record

| Description | Detail | Example |
| ----------- | ------- | ------- |
| Documentation | Every crucial section of the files must be documented in English. | See Appendix 1 |
| Naming Variables, Attributes, and Parameters | The name, in camelCase and in English, must be explicit and clearly indicate its purpose of use. | var counter = 0 |
| Function Definition | The name must adopt the camelCase format in English and be explicit about its utility. | fun findById() |
| Springboot Entity Definition | The name must follow the PascalCase format in English and be explicit about its utility. | User |
| Springboot Mapper, Service, REST Controller, and Repository Definition | The name format must be PascalCase in English, and the entity name must be followed by "Mapper," "Service," "Controller," or "Repository," as appropriate. | UserMapper |
| List Definition | The name format must be camelCase in English, and the entity name in lowercase and plural, followed by the word "List." | usersList |

## Database Standards Record

| Description | Detail | Example |
| ----------- | ------- | ------- |
| Database Name | The project name must follow the camelCase format in English, accurately reflecting the nature of the development. | GTracker |
| Table Names | The entity name must adopt the snake_case format, be plural, and reflect the nature of the entity to which it corresponds, all in English. | game_logs |
| Table Column Names | The name, in snake_case and in English, must be explicit and clearly indicate its purpose of use. | action_type |
| Intermediate Table Names | The name format must be snake_case in English, following the structure of "FirstEntity_SecondEntity" to reflect the relationship between entities. | role_page |

## Design Standards Record

| Description | Detail | Example |
| ----------- | ------- | ------- |
| Screen Header | The design includes an Orange G-Tracker color background (#B4F6DB9D), with black text (#000000), a menu in hamburger format, visual separation through a 2-pixel high black line. The menu should be located to the left of the signature, which should be centered in the header and display the G-Tracker logo designed by Sh4dow18. | See Appendix 2 |
| Screen Body | Typography should be sans-serif, with black font color (#000000), primary color in Orange G-Tracker (#B4F6DB9D), and background color in Orange G-Tracker Background (#DFC397). | See Appendix 3 |
| Screen Footer | The background should be black (#000000), with sans-serif typography, and the signature should present "Sh4dow18" and "Sh4dowtech." | See Appendix 4 |

## Appendices

### Appendix 1: Documentation

![G-Tracker-Docs](/Images/G-Tracker-Docs.png)

### Appendix 2: Screen Header

![G-Tracker-Header](/Images/G-Tracker-Header.png)

### Appendix 3: Screen Body

![G-Tracker-Body](/Images/G-Tracker-Body.png)

### Appendix 4: Screen Footer

![G-Tracker-Footer](/Images/G-Tracker-Footer.png)
