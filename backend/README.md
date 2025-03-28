# Backend - Item Price Prediction
## Description
This system is based on Flask and provides an API for image processing and prediction using pre-trained models. The system accepts an image in Base64 format, decodes the image, processes it using a CNN model, and then passes the processed image to the main prediction model, which returns the minimum and maximum range values.

## Backend Structure
backend/  
│
├── app/  
│   ├── __init__.py  
│   ├── routes  
│   ├── services  
│   └── models  
│
├── .env  
├── run.py  
└── requirements.txt  

## Folders
app: The core backend logic folder.  

routes: Contains route definitions for the API (e.g., /predictimage).  

services: Contains functions for image processing, including Base64 decoding and processing with the CNN model.  

models: Folder where the pre-trained models are stored.

.env: Contains environment variables for configuring the app.

## Files
run.py: The entry point for starting the Flask application.  

requirements.txt: Contains a list of required dependencies for the backend.  

## Environment Variables
The .env file should contain the following environment variables:  

FLASK_APP_PORT=5000  
MODEL_PATH=app/models/MAIN_MODEL.pth  
CNN_MODEL_PATH=app/models/CNN_MODEL.pth  
FLASK_DEBUG=True  
ENABLE_CORS=True  
FLASK_APP_PORT: Port to run the Flask app on (default: 5000).  

MODEL_PATH: Path to the main prediction model.  

CNN_MODEL_PATH: Path to the CNN model for image processing.  

FLASK_DEBUG: Set to True to enable Flask debug mode (recommended during development).  

ENABLE_CORS: Set to True to enable Cross-Origin Resource Sharing (CORS).  

## Installation
To get the backend up and running, follow these steps:  

Clone the repository or download the backend files.  

### Install the required dependencies:  

pip install -r requirements.txt  

Create a .env file in the root directory and configure it with the appropriate values (as shown above).

## Run the backend application:

python run.py  
The server will start on http://localhost:5000 (or the port you configured in .env).

## API Endpoints
### POST /predictimage  
#### Request body:  

{  
  "image": "base64_encoded_image_string"  
}  
image: The Base64 encoded image string that you want to process.

#### Response:

{  
  "min_range": 10,  
  "max_range": 50  
}  
min_range: The minimum range value predicted by the model.  

max_range: The maximum range value predicted by the model.  

## Requirements
The following Python packages are required to run the backend:  

- flask: Web framework for building the API.  

- python-dotenv: For loading environment variables from .env file.  

- flask-cors: To enable Cross-Origin Resource Sharing (CORS).  

- torch: PyTorch for machine learning models.  

- torchvision: PyTorch vision for image processing utilities.  

These dependencies are listed in requirements.txt and can be installed using:  
  
pip install -r requirements.txt  

## Notes

- Ensure the models (CNN_MODEL.pth and MAIN_MODEL.pth) are placed in the app/models/ folder.  

- The backend processes images via a CNN model and then predicts using the main model, both of which should be pre-trained and saved as .pth files.  

- Make sure the .env file is properly configured with the correct paths to your models and other configurations.

