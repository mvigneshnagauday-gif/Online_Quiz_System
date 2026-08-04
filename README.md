# 🎯 Online Quiz System

A full-stack, role-based quiz platform that enables users to take timed, level-based assessments across multiple programming courses. The application provides secure authentication, automatic evaluation, progress tracking, and leaderboard rankings using a modern Spring Boot backend and a lightweight vanilla JavaScript frontend.

---

## 🚀 Features

### 📚 Multiple Courses
- 5 programming courses:
  - Java
  - Python
  - C++
  - Database
  - Spring Boot

### 🎯 Level-Based Learning
- 3 difficulty levels for every course:
  - Easy
  - Intermediate
  - Hard
- 10 multiple-choice questions per level
- Total of **150 MCQs**

### ⏱ Timed Assessments
- Easy – **10 minutes**
- Intermediate – **20 minutes**
- Hard – **30 minutes**
- Automatic submission when the timer expires

### 🔓 Progressive Unlocking
- Users must score **50% or above** to unlock the next level.
- Failed attempts require retaking the current level.
- Progress is maintained independently for each course.

### 🔐 Secure Authentication
- User Registration & Login
- JWT-based Authentication
- BCrypt Password Encryption
- Stateless Security using Spring Security

### 📊 Performance Tracking
- Automatic quiz evaluation
- Pass/Fail determination
- Complete attempt history
- User progress tracking
- Leaderboard based on levels completed and average score

### 🛡 Backend Validation
Progression rules are enforced on the server, preventing users from bypassing level restrictions by directly invoking API endpoints.

---

# 🛠 Tech Stack

## Backend
- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT Authentication (jjwt)
- MySQL
- Maven

## Frontend
- HTML5
- CSS3
- JavaScript (Vanilla)
- Fetch API

---

# 🏗 Architecture

## Database Design

The application follows a normalized relational database design consisting of five primary tables.

| Table | Description |
|--------|-------------|
| **User** | Stores user credentials and roles (USER / ADMIN) |
| **Course** | Stores available programming courses |
| **Question** | Stores MCQs mapped to courses and difficulty levels |
| **Result** | Stores every quiz attempt and score |
| **UserProgress** | Tracks the highest unlocked level for each user and course |

---

# 🌐 REST API

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Authenticate user and generate JWT |
| GET | `/api/user/me` | Get logged-in user details |
| GET | `/api/courses` | Retrieve all courses |
| GET | `/api/progress` | View unlocked levels |
| GET | `/api/quiz/questions` | Fetch quiz questions (without answers) |
| POST | `/api/quiz/submit` | Submit quiz and calculate score |
| GET | `/api/quiz/review` | Review previous quiz attempt |
| GET | `/api/results` | View quiz history |
| GET | `/api/leaderboard` | View leaderboard rankings |

---

# 📂 Project Structure

```
Online_Quiz_System
│
├── src
│   └── main
│       ├── java/com/project/Quiz
│       │   ├── Controller
│       │   ├── DTO
│       │   ├── Entity
│       │   ├── Repository
│       │   ├── Security
│       │   └── Service
│       │
│       └── resources
│           └── application.properties
│
└── FrontEnd
    ├── HTML
    ├── CSS
    └── JS
```

---

# ⚙ Getting Started

## Prerequisites

- Java 17 or later
- Maven
- MySQL Server

---

## Backend Setup

### 1. Clone the repository

```bash
git clone <repository-url>
```

### 2. Configure Database

Update the MySQL credentials inside:

```
src/main/resources/application.properties
```

### 3. Run the application

```bash
mvn spring-boot:run
```

On the first startup, the application automatically seeds:

- 5 Courses
- 150 Sample Questions

Backend runs at:

```
http://localhost:8080/api
```

---

## Frontend Setup

Open the **FrontEnd** folder using a local web server such as:

- VS Code Live Server

> **Note:** Opening HTML files directly using `file://` is not supported because API requests require an HTTP server.

---

# 🔒 Security

- BCrypt password hashing
- JWT-based authentication
- Stateless session management
- 24-hour JWT expiration
- Protected REST APIs
- Server-side authorization for level progression
- Prevents API-level bypass of locked content

---

# ✨ Key Highlights

- Role-Based Authentication
- Timed Quizzes
- Automatic Scoring
- Progressive Level Unlocking
- Leaderboard System
- Quiz Review
- Attempt History
- Secure REST APIs
- Responsive Frontend
- Clean Layered Architecture
