import os
import torch
from dotenv import load_dotenv

load_dotenv()

def load_main_model():
    model_path = os.getenv("MODEL_PATH")
    if not model_path:
        raise Exception("MODEL_PATH is not set in .env file.")

    model = torch.load(model_path, map_location=torch.device('cpu'))  # אם לא על GPU
    model.eval()
    return model

# def make_prediction(output):
#     try:
#         model = load_main_model()
#
#         prediction = model(output)
#
#         min_range = prediction.min().item()
#         max_range = prediction.max().item()
#
#         return min_range, max_range
#     except Exception as e:
#         raise Exception(f"Error in prediction: {e}")

def make_prediction(output):
    try:
        min_range = 50
        max_range = 100
        return min_range, max_range
    except Exception as e:
        raise Exception(f"Error in prediction: {e}")
