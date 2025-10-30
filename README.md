# FitMate: Full Stack Workout Tracking Application

## Project Overview
FitMate is a full-stack web application designed to help gym enthusiasts manage their training progress and create custom workout plans. It serves as a comprehensive portfolio piece, showcasing proficiency in modern JavaScript/TypeScript on the frontend and Java/Spring Boot on the backend.

## 🚀 Key Features

* **Secure User Management:** Registration and authentication implemented using **Spring Security** and **JSON Web Tokens (JWT)**.
* **User Profile:** Functionality to edit user data, including avatar management.
* **Exercise Encyclopedia:** A database module for browsing and managing a comprehensive list of exercises.
* **Workout Templates:** Business logic implementation for creating reusable workout templates based on the exercise encyclopedia.
* **Full CRUD Operations** (Create, Read, Update, Delete) implemented across key data models.
* **Future Development:** Planned modules include tracking real-time training sessions and progress analytics.

## 💻 Tech Stack

The application is built using a modern decoupled architecture:

### Frontend
| Technology | Description |
| :--- | :--- |
| **React JS** | Core UI library. |
| **TypeScript** | For improved code quality and scalability. |
| **Tailwind CSS** | Used for fast, utility-first styling. |

### Backend & Database
| Technology | Description |
| :--- | :--- |
| **Java** | Core backend language. |
| **Spring Boot** | Framework used for building the REST API. |
| **Spring Security (JWT)** | Handling authentication and authorization. |
| **PostgreSQL** | Primary relational database for storing user and training data. |

## 🛠️ How to Run Locally

### Prerequisites
* Java Development Kit (JDK 17+)
* Node.js (LTS version) & npm
* PostgreSQL installed locally or running via Docker

### 1. Backend Setup (Spring Boot)
1.  Navigate to the `FitMate-Backend` directory.
2.  Configure your PostgreSQL connection details (database name, username, password) in `application.properties` or `application.yml`.
3.  Run the application: `mvn spring-boot:run`

The API will be available at `http://localhost:8080`.

### 2. Frontend Setup (React/TypeScript)
1.  Navigate to the `FitMate-Frontend` directory.
2.  Install dependencies: `npm install`
3.  Start the client: `npm start` (or `npm run dev`, depending on your setup).

The React application will be available at `http://localhost:3000`.

---
*Created by Kaboox*
