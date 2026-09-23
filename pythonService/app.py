import time

from transformers import pipeline

from fastapi import FastAPI

app = FastAPI()

@app.get("/health")
def health():
    return {"status": "Python service is running"}

from pydantic import BaseModel

class PredictionInput(BaseModel):
    text: str

classifier = pipeline(
    "sentiment-analysis",
    model="cardiffnlp/twitter-roberta-base-sentiment-latest"
)

@app.post("/predict")
def predict(data: PredictionInput):

    start = time.perf_counter()

    prediction = classifier(data.text)

    latency_ms = (time.perf_counter() - start) * 1000

    return {
        "label": prediction[0]["label"],
        "confidence": prediction[0]["score"],
        "latency_ms": latency_ms
    }