import os
import pickle
import pandas as pd
from dotenv import load_dotenv

load_dotenv()

def load_main_model():
    model_path = os.getenv("MODEL_PATH")
    if not model_path:
        raise Exception("MODEL_PATH is not set in .env file.")

    with open(model_path, "rb") as f:
        model = pickle.load(f)

    return model

def make_prediction(input_data):
    try:
        model = load_main_model()

        feature_df = pd.DataFrame([input_data])

        prediction = model.predict(feature_df)

        # change later
        index = 15

        min_range = prediction - index
        max_range = prediction + index

        return min_range, max_range
    except Exception as e:
        raise Exception(f"Error in prediction: {e}")

# def make_prediction(output):
#     try:
#         min_range = 50
#         max_range = 100
#         return min_range, max_range
#     except Exception as e:
#         raise Exception(f"Error in prediction: {e}")
