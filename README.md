# 💱 Currency Converter API

A simple Spring Boot application that converts currency amounts using real-time exchange rates and stores the conversions in MongoDB.

---

## 🚀 What the App Does

- Converts an amount from one currency to another.
- Fetches real-time exchange rates from a currency API.
- Stores conversion records in MongoDB.
- Provides REST API endpoints to:
    - Add a new conversion
    - Retrieve one or all conversions
    - Delete a conversion

---

## 🛠️ How to Run It

### ✅ Prerequisites

- Java 17+
- Maven
- Docker (for MongoDB, optional)
- API key from [FreeCurrencyAPI](https://freecurrencyapi.com)

### 🔧 Setup Steps

1. **Clone the repo**
   ```bash
   git clone https://github.com/yourusername/currency-converter-api.git
   cd currency-converter-api
