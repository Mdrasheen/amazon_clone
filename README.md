🛒 Amazon Clone – Full Stack E-Commerce Application

A full-stack Amazon-like e-commerce web application built using Spring Boot, Spring Security, Thymeleaf, and MySQL.
This project supports user authentication, cart management, checkout with multiple payment methods, order tracking, and admin order control.

🚀 Features
👤 User Features

User registration & login

Secure authentication using Spring Security

Browse products

Add products to cart

View & manage cart

Checkout with payment options:

Cash on Delivery (COD)

UPI

Net Banking

Place orders

View order history

Logout securely

🛠️ Admin Features

Admin login

View all orders

View order details

Update order status:

PLACED

SHIPPED

DELIVERED

Admin-only access using role-based authorization

🧱 Tech Stack
Backend

Java 17+

Spring Boot

Spring Security

Spring Data JPA (Hibernate)

MySQL

Frontend

Thymeleaf

HTML5

CSS3

Tools

Maven

Git & GitHub

MySQL Workbench

VS Code

🗂️ Project Structure
amazon_clone
│
├── src/main/java/com/example/demo
│   ├── config        → Security configuration
│   ├── controller    → Controllers (Auth, Cart, Order, Admin)
│   ├── entity        → JPA Entities
│   ├── repository    → JPA Repositories
│   ├── service       → Business logic
│
├── src/main/resources
│   ├── templates     → Thymeleaf HTML pages
│   ├── static/css    → CSS styles
│   └── application.properties
│
├── pom.xml
└── README.md

🔐 Security

Role-based access control:

ROLE_USER

ROLE_ADMIN

Passwords encrypted using BCrypt

CSRF protection enabled

Session-based cart handling

💳 Payment Handling

This project supports mock payment flow:

COD → Payment status set to PENDING

UPI / Net Banking → Payment status set to PAID

(Real payment gateway can be integrated later.)

🧪 Test Accounts
👤 User
Email: mohamed@gmail.com
Password: <encrypted in DB>
Role: USER

👑 Admin
Email: rasheen@gmail.com
Password: <encrypted in DB>
Role: ADMIN

⚙️ How to Run Locally
1️⃣ Clone Repository
git clone https://github.com/Mdrasheen/amazon_clone.git
cd amazon_clone

2️⃣ Configure Database

Create MySQL database:

CREATE DATABASE amazonclone_db;


Update application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/amazonclone_db
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

3️⃣ Run Application
mvn spring-boot:run


Access app:

http://localhost:8080/login

📸 Screens (Implemented Pages)

Login Page

Home Page

Products Page

Cart Page

Checkout Page

Order Success Page

My Orders Page

Admin Orders Dashboard

📈 Future Enhancements

Product image upload

Real payment gateway (Razorpay / Stripe)

Order cancellation

Email notifications

Pagination & search

Cloud deployment

👨‍💻 Author

Mohamed Rasheen
Final Year IT Student
Full Stack Java Developer (Spring Boot)

🔗 GitHub: https://github.com/Mdrasheen
