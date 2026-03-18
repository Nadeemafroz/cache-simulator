# 🚀 Cache Simulator (LRU & LFU)

A full-stack application that simulates caching mechanisms using LRU (Least Recently Used) and LFU (Least Frequently Used) strategies with real-time visualization.

---

## 🔥 Features

- Implemented LRU & LFU caching algorithms with O(1) time complexity  
- Dynamic switching between cache strategies (LRU ↔ LFU)  
- RESTful APIs built with Spring Boot  
- Cache hit/miss tracking with performance metrics  
- Interactive UI built with React (Vite)  
- Real-time cache visualization (most → least recent order)  

---

## 🧠 How It Works

### LRU (Least Recently Used)
Removes the item that was not used for the longest time  

### LFU (Least Frequently Used)
Removes the item with the lowest access frequency  
If tie → removes least recently used among them  

---

## 🛠 Tech Stack

### Backend
- Java  
- Spring Boot  
- REST APIs  

### Frontend
- React (Vite)  
- JavaScript  

### Core Concepts
- HashMap  
- Doubly Linked List  
- System Design (Caching)  

---

## 📁 Project Structure

cache-simulator/
 ├── backend/        # Spring Boot application
 └── frontend/       # React (Vite) UI

---

## 🚀 Run Locally

### 1. Clone Repository

git clone <your-repo-url>
cd cache-simulator

---

### 2. Run Backend

cd backend
mvn spring-boot:run

Runs on: http://localhost:8080

---

### 3. Run Frontend

cd frontend
npm install
npm run dev

Runs on: http://localhost:5173

---

## 📌 API Endpoints

POST   /cache/strategy   → Set cache strategy (LRU/LFU)  
POST   /cache/put        → Insert key-value pair  
GET    /cache/get        → Retrieve value  
GET    /cache/metrics    → View hit/miss stats  
GET    /cache/keys       → Get current cache state  

---

## 🧪 Example Usage

POST /cache/strategy?type=LRU&capacity=3  
POST /cache/put?key=A&value=Apple  
GET  /cache/get?key=A  

---

## 📊 Sample Output

Cache: [A, D, C]  
Hits: 5  
Misses: 2  
Hit Ratio: 0.71  

---

## 🎯 Key Highlights

- Designed using clean architecture (Controller → Service → Core logic)  
- Achieved O(1) operations using optimized data structures  
- Demonstrates real-world backend concepts like caching & eviction policies  
- Built full-stack integration with Spring Boot + React  

---

## 🚀 Future Improvements

- Redis integration for distributed caching  
- Advanced analytics dashboard  
- UI animations for cache updates  
- Authentication for API access  

---

## 👨‍💻 Author

Nadeem Afroz  

---

## ⭐ If you like this project

Give it a ⭐ on GitHub!
