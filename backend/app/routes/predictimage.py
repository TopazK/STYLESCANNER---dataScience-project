from flask import Blueprint, request, jsonify
from app.services.decode64 import decode_base64_image 
from app.models.cnn_model import process_with_cnn  
from app.models.main_model import make_prediction  

routes_bp = Blueprint('routes', __name__)

@routes_bp.route("/predictimage", methods=["POST"])
def predict_image():
    try:
        data = request.get_json()
        print("[DEBUG] Received data:", data)

        # Print what's received
        print("/predictimage endpoint hit")
        print(f"Keys received: {list(data.keys())}")
        print(f"Image (first 100 chars): {data.get('image', '')[:100]}")
        print(f"Country: {data.get('country')}, City: {data.get('locality')}")

        image_base64 = data.get("image")
        if not image_base64:
            return jsonify({"error": "No image provided"}), 400

        image_decoded = decode_base64_image(image_base64)

        # CNN
        cnn_output = process_with_cnn(image_decoded)

        # prediction
        min_range, max_range = make_prediction(cnn_output)

        return jsonify({
            "min_range": min_range,
            "max_range": max_range
        })

    except Exception as e:
        print(f"Server error: {str(e)}")  # Log the error
        return jsonify({"error": str(e)}), 500
