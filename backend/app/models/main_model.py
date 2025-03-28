import torch

def load_main_model():
    model = torch.load('models/MAIN_MODEL.pth') # load the model
    model.eval()  
    return model

def make_prediction(output):
    try:
        model = load_main_model()
        
        prediction = model(output)
        
        min_range = prediction.min().item()
        max_range = prediction.max().item()

        return min_range, max_range
    except Exception as e:
        raise Exception(f"Error in prediction: {e}")
