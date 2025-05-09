# FinScopeAI

💡 Smart Credit Evaluation System — Built for Ignosis Fintech Hackathon

FinScopeAI is an intelligent creditworthiness assessment platform built for financial institutions and loan processing teams. It leverages user-uploaded PDFs and simulated AA data to evaluate financial behavior, generate a credit score, and provide actionable suggestions — all within a real-time dashboard experience.

---

## 🚀 Core Features

- 🔍 Naive Bayes–based categorization of user transactions (Food, Entertainment, Bills, etc.)
- 📊 Credit score generation based on income, expenses, and EMI behavior
- 📈 Personalized suggestions and simulations to help users improve credit scores
- 📄 Accepts PDF bank statements & consent ID inputs from frontend
- 🧠 Seamless future-ready support for Account Aggregator (AA) framework integration

---

## 🧠 Account Aggregator Alignment

We fully understand the Account Aggregator (AA) Framework established by RBI and have engineered our system with forward compatibility in mind. In a typical flow, an RBI-registered FIU (Financial Information User) uses AA to collect user consent and receive structured transaction data via a consentId.

Currently, since we are not an FIU and don’t have AA access, we simulate this process responsibly:

- We built a custom consent form on the frontend, where the user enters their details and Consent ID
- The backend treats this input just like a real consent-based data retrieval
- We use mock JSON responses that reflect real AA output structure
- The entire data flow is modeled to plug directly into real AA APIs when access is granted — with minimal or no refactoring

On the transaction side, we accept PDF bank statements for now. Once AA data is available, we will directly parse transaction entries from the AA JSON response instead of PDFs. Our PDF parser is designed to work on structured statements with consistent formatting.

💬 In short: We’ve built everything around the real AA framework model — with current constraints handled using smart simulations. We are AA-ready by design.

---

## 📈 How It Works

1. User submits their bank statement (PDF) + Consent ID
2. Python ML engine extracts transaction history and classifies each entry
3. Simulated AA data feeds EMI and income behavior
4. Credit score + suggestions and what-if simulations are generated using ML engine and stored in MySQL via Spring Boot
5. React dashboard shows breakdown pie-charts, scores, and what-if simulations

---

## 📄 Sample PDF Formats

As we do not currently have access to the live Account Aggregator (AA) system (since we are not an FIU), users upload PDF bank statements for transaction analysis. This is a temporary and intentional simulation step — not a limitation of our system.

📂 Reference sample PDFs are available in the [`./Sample-Pdfs`](./Sample-Pdfs) folder.  
These PDFs follow a standard tabular format with columns such as:

- **Datetime**
- **Description**
- **Account Number**
- **Type [debited/credited]**
- **amount**

Please ensure uploaded statements are digital, not scanned, and structured similarly for best results.

---

## 🧪 Tech Stack

- React.js – Dynamic and responsive frontend
- Spring Boot – Backend API & business logic
- Python (Flask) – ML for categorization & suggestion engine
- MySQL – Persistent storage

---

## 📦 Project Structure

FinScopeAI/
├── Backend (Java Spring Boot)
│ └── FinTech Module – APIs, Database Services
├── Frontend (React)
│ └── FinScopeAI UI – Dashboard & User Interface
├── Machine Learning (Python)
│ ├── app.py – PDF Parsing & Categorization
│ └── Suggestion/
│ └── GenerateSuggestion.py – Suggestion Logic & Simulation

---

## 👥 Contributors

- 🎯 Vishnu Burkhawala – Complete Python & Machine Learning, Spring Boot support
- 🛠️ Urvi Ponda – Complete Spring Boot backend
- 🎨 Harshil Karia – Complete React frontend

---

📍 Built for Ignosis Fintech Hackathon – Ahmedabad, Gujarat  
🔧 Designed with real-world finance workflows & RBI-backed systems in mind
