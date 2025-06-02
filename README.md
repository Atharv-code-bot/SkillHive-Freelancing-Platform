# SkillHive – Freelancing Platform

SkillHive is a backend-driven freelancing platform designed to connect skilled professionals with clients. It supports job posting, bidding, and project management features with secure role-based access for both clients and freelancers.

## 🌟 Features

- 🔐 Role-based access control (Client, Freelancer)
- 📄 Job posting and browsing
- 💰 Bidding system for freelancers to apply on jobs
- 📬 Project management and workflow updates
- 🔍 Structured database design for scalability

## 🛠 Tech Stack

- **Language**: Java  
- **Framework**: Spring Boot  
- **Database**: MySQL  
- **Security**: Spring Security with JWT  
- **Architecture**: RESTful APIs  
- **Build Tool**: Maven  

## 🧱 Project Structure
skillhive/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com/skillhive/
│ │ │ ├── controller/
│ │ │ ├── model/
│ │ │ ├── repository/
│ │ │ ├── service/
│ │ │ └── SkillHiveApplication.java
│ │ └── resources/
│ │ ├── application.yml
│ │ └── schema.sql
└── pom.xml


## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven
- MySQL (with a schema named `hackathon`)

### Installation
Clone the repository:


git clone https://github.com/Atharv-code-bot/skillhive.git
cd skillhive
Set up the database:
Create a MySQL database named skillhive.

Configure database credentials:
Update the database connection details in src/main/resources/application.yml.

Run the application:


./mvnw spring-boot:run
The application will be available at http://localhost:8080.


🔐 Security
JWT-based authentication: Secure user login and session management.
Role-based authorization: Fine-grained access control using Spring Security.
📌 Future Enhancements
Frontend development with React or Angular.
Real-time chat system between clients and freelancers.
Dedicated admin dashboard for platform management.
🤝 Contributing
Pull requests are welcome! For major changes, please open an issue first to discuss your ideas and proposed changes.
