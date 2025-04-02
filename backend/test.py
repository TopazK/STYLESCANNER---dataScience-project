import pickle

import os

model_path = os.path.abspath("backend/app/models/xgboost_model.pkl")
print("Trying to load model from:", model_path)

with open(model_path, "rb") as f:
    model = pickle.load(f)
    
print(type(model))
