import torch
from torchvision import transforms
from PIL import Image

def load_cnn_model():
    model = torch.load('models/CNN_MODEL.pth') # load the model
    model.eval()  
    return model

def process_with_cnn(image):
    try:
        model = load_cnn_model()
        
        # transform if needed
        transform = transforms.Compose([
            transforms.Resize((224, 224)),
            transforms.ToTensor(),
            transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225])
        ])

        img_tensor = transform(image).unsqueeze(0)  

        with torch.no_grad():
            output = model(img_tensor) # prosseced image

        return output
    except Exception as e:
        raise Exception(f"Error processing image with CNN: {e}")
