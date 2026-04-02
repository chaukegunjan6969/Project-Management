# Project Management Application

A comprehensive Spring Boot-based project management system that enables teams to collaborate efficiently on projects, manage issues, handle payments, and maintain real-time communication.

## 📋 Table of Contents

- [Overview](#overview)
- [Key Features](#key-features)
- [Tech Stack](#tech-stack)
- [System Architecture](#system-architecture)
- [Database Design](#database-design)
- [API Endpoints](#api-endpoints)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [How It Works](#how-it-works)
- [Security](#security)
- [Payment Integration](#payment-integration)
- [Email Service](#email-service)

## 🎯 Overview

This Project Management Application serves as a collaborative platform where users can:
- Create and manage projects with team members
- Track issues with priorities and due dates
- Communicate via comments and real-time messaging
- Handle payments and subscription plans
- Invite team members to projects

The application is built with Spring Boot 3.3.2, MySQL database, and JWT-based authentication.

## ✨ Key Features

### 1. **User Management & Authentication**
- User registration (Sign Up) and login (Sign In)
- JWT token-based authentication
- User profile management
- Secure password encryption using Spring Security

### 2. **Project Management**
- Create, update, and delete projects
- Project categorization and tagging
- Team member management
- Project search functionality
- Project filtering by category and tags
- Project team collaboration

### 3. **Issue Tracking**
- Create issues with detailed information
  - Title and description
  - Priority levels
  - Due dates
  - Status tracking
  - Tags for categorization
- Assign users to issues
- Update issue status
- Delete issues
- Retrieve issues by project

### 4. **Team Collaboration**
- Real-time chat system per project
- Comments on issues
- Private messaging between team members
- User invitations to projects

### 5. **Subscription System**
- Plan-based subscriptions
- Subscription management
- Automatic subscription creation upon registration

### 6. **Payment Processing**
- Razorpay payment gateway integration
- Generate payment links
- Handle payment transactions

### 7. **Email Notifications**
- Gmail SMTP integration
- Email-based communications
- Notification system via email

## 🛠 Tech Stack

| Component | Technology |
|-----------|-----------|
| **Framework** | Spring Boot 3.3.2 |
| **Java Version** | Java 17 |
| **Database** | MySQL |
| **ORM** | JPA/Hibernate |
| **Security** | Spring Security |
| **Authentication** | JWT (JSON Web Token) |
| **Build Tool** | Maven |
| **Additional Libraries** | Lombok, JJWT |
| **Payment Gateway** | Razorpay |
| **Email Service** | Gmail SMTP |

## 🏗 System Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                      REST API Layer                          │
│  (AuthController, ProjectController, IssueController, etc.)  │
└──────────────────────────────┬──────────────────────────────┘
                               │
┌──────────────────────────────┴──────────────────────────────┐
│                      Service Layer                           │
│  (ProjectService, IssueService, UserService, ChatService)   │
└──────────────────────────────┬──────────────────────────────┘
                               │
┌──────────────────────────────┴──────────────────────────────┐
│               Security & JWT Authentication                  │
│        (JwtProvider, JwtTokenValidator, JwtConstant)        │
└──────────────────────────────┬──────────────────────────────┘
                               │
┌──────────────────────────────┴──────────────────────────────┐
│                  Repository Layer (DAL)                      │
│  (UserRepository, ProjectRepository, IssueRepository, etc.)  │
└──────────────────────────────┬──────────────────────────────┘
                               │
┌──────────────────────────────┴──────────────────────────────┐
│                    MySQL Database                            │
│  (Users, Projects, Issues, Comments, Chat, Subscriptions)   │
└─────────────────────────────────────────────────────────────┘
```

## 🗄 Database Design

### Entity Relationships

```
User (1) ──→ (N) Project
  │                 │
  ├─→ (N) Issue     └─→ (1) Chat
  │         │              │
  │         └─→ (N) Comment └─→ (N) Messages
  │
  ├─→ (1) Subscription
  │
  └─→ (N) Invitation

Issue
  ├─→ (1) User (assignee)
  ├─→ (1) Project
  └─→ (N) Comment

Project
  ├─→ (1) User (owner)
  ├─→ (N) User (team members)
  ├─→ (1) Chat
  └─→ (N) Issue
```

### Key Entities

| Entity | Purpose | Key Fields |
|--------|---------|-----------|
| **User** | Represents a system user | id, fullname, email, password, projectSize |
| **Project** | Team projects | id, name, description, category, tags, owner, team, issues, chat |
| **Issue** | Work items to track | id, title, description, status, priority, dueDate, tags, assignee, project, comments |
| **Comment** | Issue feedback | Comments attached to issues |
| **Chat** | Project communication | Messages and discussions per project |
| **Messages** | User communications | Private messages between team members |
| **Invitation** | Team invitations | Invites to join projects |
| **Subscription** | User subscriptions | Subscription plans and management |

## 📡 API Endpoints

### Authentication Endpoints (`/auth`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/signup` | Register a new user |
| POST | `/auth/signing` | Login user and get JWT token |

**Request Example (Signup):**
```json
{
  "fullname": "John Doe",
  "email": "john@example.com",
  "password": "securePassword123"
}
```

**Response:**
```json
{
  "message": "SignUp Success",
  "jwt": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### User Endpoints (`/api/users`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|----------------|
| GET | `/api/users/profile` | Get user profile | Yes (JWT) |

### Project Endpoints (`/api/projects`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|----------------|
| GET | `/api/projects` | Get all user projects | Yes |
| GET | `/api/projects/{projectId}` | Get specific project | Yes |
| POST | `/api/projects` | Create new project | Yes |
| PATCH | `/api/projects/{projectId}` | Update project | Yes |
| DELETE | `/api/projects/{projectId}` | Delete project | Yes |
| GET | `/api/projects/search` | Search projects | Yes |
| GET | `/api/projects/{projectId}/chat` | Get project chat | Yes |

**Project Request Body:**
```json
{
  "name": "Mobile App Development",
  "description": "Build mobile application",
  "category": "Mobile",
  "tags": ["iOS", "Android", "React Native"]
}
```

### Issue Endpoints (`/api/issues`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|----------------|
| GET | `/api/issues/{issueId}` | Get specific issue | No |
| GET | `/api/issues/project/{projectId}` | Get all project issues | No |
| POST | `/api/issues` | Create new issue | Yes |
| DELETE | `/api/issues/{issueId}` | Delete issue | Yes |
| PUT | `/api/issues/{issueId}/assignee/{userId}` | Assign user to issue | Yes |
| PUT | `/api/issues/{issueId}/status/{status}` | Update issue status | Yes |

**Issue Request Body:**
```json
{
  "title": "Fix login bug",
  "description": "User cannot login with email",
  "priority": "HIGH",
  "status": "TODO",
  "dueDate": "2024-12-31",
  "tags": ["bug", "urgent"],
  "projectId": 1
}
```

### Comment Endpoints (`/api/comments`)

| Method | Endpoint | Purpose |
|--------|----------|---------|
| POST | `/api/comments` | Add comment to issue |
| DELETE | `/api/comments/{commentId}` | Delete comment |

### Message/Chat Endpoints (`/api/messages`, `/api/chat`)

| Method | Endpoint | Purpose |
|--------|----------|---------|
| GET | `/api/chat/{chatId}` | Get chat messages |
| POST | `/api/messages` | Send message |

### Subscription Endpoints (`/api/subscriptions`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/subscriptions/{userId}` | Get user subscription |
| POST | `/api/subscriptions/{userId}/upgrade` | Upgrade subscription |

### Payment Endpoints (`/api/payments`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/payments/create-link` | Generate payment link (Razorpay) |

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6.0 or higher
- Git

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Project-Management
   ```

2. **Configure Database**
   - Create a MySQL database:
   ```sql
   CREATE DATABASE student_tracker;
   ```
   
   - Update database credentials in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/student_tracker
   spring.datasource.username=springstudent
   spring.datasource.password=springstudent
   ```

3. **Configure Email Service**
   - Update Gmail credentials in `application.properties`:
   ```properties
   spring.mail.username=your-email@gmail.com
   spring.mail.password=your-app-password
   ```

4. **Configure Razorpay Payment**
   - Add Razorpay API keys in `application.properties`:
   ```properties
   razorpay.api.key=your-api-key
   razorpay.api.secret=your-api-secret
   ```

5. **Build and Run**
   ```bash
   # Build project
   mvn clean install
   
   # Run application
   mvn spring-boot:run
   ```

6. **Access the application**
   - The application will run on: `http://localhost:8080`

## 📁 Project Structure

```
src/main/java/com/Aadyy/projectManagement/
├── ProjectManagementApplication.java      # Main Spring Boot application
├── config/                                 # Configuration classes
│   ├── Appconfig.java                     # Application configuration
│   ├── JwtProvider.java                   # JWT token generation
│   ├── JwtTokenvalidator.java             # JWT token validation
│   └── JwtConstant.java                   # JWT constants
├── Controller/                             # REST Controllers
│   ├── AuthController.java                # Authentication endpoints
│   ├── ProjectController.java             # Project management endpoints
│   ├── IssueController.java               # Issue tracking endpoints
│   ├── CommentController.java             # Comment endpoints
│   ├── ChatController.java                # Chat endpoints
│   ├── MessageController.java             # Messaging endpoints
│   ├── UserController.java                # User profile endpoints
│   ├── PaymentController.java             # Payment endpoints
│   └── SubscriptionController.java        # Subscription endpoints
├── Modal/                                 # Entity classes (Models)
│   ├── User.java                          # User entity
│   ├── Project.java                       # Project entity
│   ├── Issue.java                         # Issue entity
│   ├── Comment.java                       # Comment entity
│   ├── Chat.java                          # Chat entity
│   ├── Messages.java                      # Message entity
│   ├── Invitation.java                    # Invitation entity
│   ├── Subscription.java                  # Subscription entity
│   ├── PlanType.java                      # Subscription plan types
│   └── IssueDTO.java                      # Issue data transfer object
├── Repository/                             # Data Access Objects
│   ├── UserRepository.java                # User data access
│   ├── ProjectRepository.java             # Project data access
│   ├── IssueRepository.java               # Issue data access
│   ├── CommentRepository.java             # Comment data access
│   ├── ChatRepository.java                # Chat data access
│   ├── MessageRepository.java             # Message data access
│   ├── InvitationRepository.java          # Invitation data access
│   └── SubscriptionRepository.java        # Subscription data access
├── Request/                                # Request DTOs
│   ├── LoginRequest.java                  # Login request body
│   ├── IssueRequest.java                  # Issue creation request
│   ├── CreateCommentRequest.java          # Comment creation request
│   ├── CreateMessageRequest.java          # Message creation request
│   └── MessageResponse.java               # Generic message response
├── Response/                               # Response DTOs
│   ├── AuthResponse.java                  # Authentication response
│   ├── PaymentLinkResponse.java           # Payment link response
│   └── MessageResponse.java               # Message response
└── services/                               # Business logic services
    ├── UserService.java/UserServiceImpl.java
    ├── ProjectService.java/ProjectServiceImpl.java
    ├── IssueService.java/IssueServiceImpl.java
    ├── CommentService.java/CommentServiceImpl.java
    ├── ChatService.java/ChatserviceImpl.java
    ├── MessageService.java/MessageServiceImpl.java
    ├── InvitationService.java/InvitationserviceImpl.java
    ├── SubscriptionService.java/SubscriptionServiceImpl.java
    ├── ServiceEmail.java/ServiceEmailImpl.java
    └── CustomUserDetailsImpl.java          # Custom user details provider

src/main/resources/
└── application.properties                  # Configuration file

src/test/
└── java/...                               # Unit tests
```

## 🔄 How It Works

### 1. User Authentication Flow

```
1. User registers via /auth/signup
   └─→ Password encrypted and saved
   └─→ Subscription created automatically
   └─→ JWT token generated

2. User logs in via /auth/signing
   └─→ Credentials validated
   └─→ JWT token generated
   └─→ Token sent to client

3. Client includes JWT in Authorization header for all requests
   └─→ Token validated by JwtTokenValidator
   └─→ User identity extracted from token
```

### 2. Project Workflow

```
1. User creates a project
   └─→ User becomes project owner
   └─→ User added to project team
   └─→ Dedicated chat channel created for project

2. User invites team members
   └─→ Invitation sent to team members
   └─→ Team members can accept/reject
   └─→ Accepted members added to project team

3. Team creates issues within project
   └─→ Issues tracked with status and priority
   └─→ Issues can be assigned to team members
   └─→ Team discusses via comments
```

### 3. Issue Management Flow

```
1. Team member creates issue
   └─→ Issue associated with project
   └─→ Initial status set (TODO/IN_PROGRESS/DONE)

2. Issue assignment
   └─→ Issue assigned to responsible team member
   └─→ Assignee notified

3. Issue update
   └─→ Status updated as work progresses
   └─→ Comments added for discussion
   └─→ Due dates tracked

4. Issue closure
   └─→ Issue marked as DONE
   └─→ Historical record maintained
```

### 4. Communication Flow

```
Project Chat
├─→ All team members can see messages
└─→ Real-time communication

Private Messages
├─→ Direct messages between users
└─→ One-to-one communication

Comments on Issues
├─→ Discussions within issue context
└─→ Threaded conversations (nested within issue)
```

## 🔐 Security

### JWT Authentication
- Token-based authentication using JWT
- Each request must include valid JWT in `Authorization` header
- Token validated before accessing protected endpoints
- Password encrypted using Spring Security's PasswordEncoder

### Password Security
- Passwords encrypted using BCrypt
- Never stored in plain text
- Validated against encrypted hashes during login

### Request Validation
- Email validation during signup
- Duplicate email prevention
- Credentials validation during signin
- Authorization checks on protected resources

### Header Requirements
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## 💳 Payment Integration

### Razorpay Integration
- Payment gateway for handling transactions
- Secure payment link generation
- API credentials stored in configuration

### Payment Flow
```
1. User initiates payment
2. Payment link generated via Razorpay API
3. User directed to Razorpay payment page
4. Payment processed securely
5. Confirmation webhook received
6. Subscription updated on successful payment
```

## 📧 Email Service

### Gmail SMTP Configuration
- SMTP server: smtp.gmail.com
- Port: 587
- TLS enabled for secure communication

### Email Use Cases
- User registration confirmation
- Team invitation notifications
- Payment receipts
- Password reset links
- Issue assignment notifications

## 🧪 Testing

Run tests using Maven:
```bash
mvn test
```

Tests are located in: `src/test/java/com/Aadyy/projectManagement/`

## 📝 Configuration File

Key configurations in `application.properties`:

```properties
# Application
spring.application.name=projectManagement

# Database
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/student_tracker
spring.datasource.username=springstudent
spring.datasource.password=springstudent

# Email
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password

# Payment
razorpay.api.key=your-api-key
razorpay.api.secret=your-api-secret
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For issues and questions, please:
1. Check existing GitHub issues
2. Create a new issue with detailed description
3. Include error messages and steps to reproduce

## 🎉 Acknowledgments

- Spring Boot framework and community
- JWT for secure authentication
- MySQL for reliable data persistence
- Razorpay for payment processing
- Gmail for email service

---

**Last Updated:** April 2, 2026
**Version:** 0.0.1-SNAPSHOT
**Java Version:** 17
**Spring Boot:** 3.3.2
