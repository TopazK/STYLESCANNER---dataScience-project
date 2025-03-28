import base64
from io import BytesIO
from PIL import Image
import torch
from torchvision import transforms

def decode_base64_image(image_base64):
    try:
        img_data = base64.b64decode(image_base64)
        img = Image.open(BytesIO(img_data))

        return img
    except Exception as e:
        raise Exception(f"Error decoding BASE64 image: {e}")
