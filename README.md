# ModelFlow

ModelFlow is a multi-service NLP inference application that serves a pretrained sentiment analysis model through a Java Spring Boot API and a Python FastAPI service.

The project demonstrates communication between independently running backend services, REST API development, model serving, and inference performance monitoring.

## Architecture

```text
Browser Dashboard
       |
       | POST /predict
       v
Spring Boot API
    Java :8080
       |
       | HTTP / JSON
       v
FastAPI Service
   Python :8000
       |
       v
RoBERTa Sentiment Model
      PyTorch
```

The browser sends text to the Spring Boot backend. Spring Boot forwards the request to the FastAPI model service, which runs inference using a pretrained RoBERTa sentiment classifier and returns the prediction to the dashboard.

## Features

- REST API built with Java and Spring Boot
- Python model-serving API built with FastAPI
- Java-to-Python service communication using HTTP and JSON
- Pretrained RoBERTa sentiment analysis model
- Positive, neutral, and negative sentiment classification
- Confidence score reporting
- Inference latency measurement
- Input validation
- Interactive browser dashboard
- Java unit testing with JUnit

## Tech Stack

**Backend**
- Java
- Spring Boot
- Maven

**Model Service**
- Python
- FastAPI
- PyTorch
- Hugging Face Transformers
- RoBERTa

**Frontend**
- HTML
- CSS
- JavaScript

## Model

ModelFlow uses the pretrained:

`cardiffnlp/twitter-roberta-base-sentiment-latest`

model through Hugging Face Transformers.

The model provides three sentiment classifications:

- Positive
- Neutral
- Negative

ModelFlow does not train the model itself. The focus of the project is serving an existing NLP model through a multi-service API architecture.

## API

### Spring Boot

`GET /health`

Checks whether the Java service is running.

`POST /predict`

Accepts text for sentiment analysis.

Example request:

```json
{
  "text": "I really enjoyed this movie."
}
```

Example response:

```json
{
  "label": "positive",
  "confidence": 0.95,
  "latency_ms": 25.4
}
```

### FastAPI

`GET /health`

Checks whether the Python model service is running.

`POST /predict`

Runs sentiment inference and returns the predicted label, confidence score, and model inference latency.

## Running Locally

### 1. Start the Python model service

From the project root:

```bash
cd pythonService
source .venv/bin/activate
pip install -r requirements.txt
python3 -m uvicorn app:app --port 8000
```

The FastAPI service will run on port `8000`.

### 2. Start the Spring Boot application

Open another terminal from the project root:

```bash
./mvnw spring-boot:run
```

The Spring Boot application will run on port `8080`.

### 3. Open ModelFlow

Open:

```text
http://localhost:8080
```

Enter text in the dashboard and select **Run inference** to send the request through the ModelFlow inference pipeline.

## Testing

Run the Java tests with:

```bash
./mvnw test
```

## Project Structure

```text
modelflow/
├── pythonService/
│   ├── app.py
│   └── requirements.txt
│
├── src/
│   ├── main/
│   │   ├── java/com/saba/modelflow/
│   │   │   ├── ModelflowApplication.java
│   │   │   └── PredictionController.java
│   │   │
│   │   └── resources/
│   │       └── static/
│   │           └── index.html
│   │
│   └── test/
│       └── java/com/saba/modelflow/
│           └── ModelflowApplicationTests.java
│
├── .gitignore
├── pom.xml
└── README.md
```

## Purpose

ModelFlow was built to explore how machine learning models can be exposed through APIs and integrated into a service-oriented backend. Rather than focusing on model training, the project focuses on the infrastructure around inference: API design, service communication, model serving, latency measurement, and presenting results through a lightweight web interface.