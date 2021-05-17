from flask import Blueprint, request, make_response, json
from model import db, Result, User
from PIL import Image
import io
import base64


api = Blueprint('rest_api', __name__)
@api.route('/')
def index():
    return "index"

@api.route('/user', methods=['GET', 'POST', 'PUT', 'DELETE'])
def user_route():
    content = request.json

    if request.method == 'GET':
        users = list(User.query.all())  

        response = make_response(
            json.dumps(users),
            200
        )
        response.headers["Content-Type"]='application/json'
        
        return response
    if request.method == 'PUT':
        pass
    if request.method == 'POST':
        user = User(
            name=content["name"],
            email=content["email"],
            pass_hash=content["pass_hash"]
        )

        db.session.add(user)
        db.session.commit()

        response = make_response(
            json.dumps(user),
            200
        )
        response.headers["Content-Type"]='application/json'
        
        return response
    if request.method == 'DELETE':
        pass

@api.route('/diagnose', methods=['GET', 'POST', 'PUT', 'DELETE'])
def diagnose():
    content = request.json
    if request.method == 'GET':
        # TO-DO : 
        # Akses DB
        # Ubah JSON ke Image
        # Image di proses 
        # Ubah Image jadi Blob
        # simpan BLOB dan hasil proses
        pass
    if request.method == 'PUT':
        pass
    if request.method == 'POST':
        image_bytes = base64.b64decode(content['image'])
        stream = io.BytesIO(image_bytes)
        img = Image.open(stream)
        img.save("testimage.png", "PNG")
        # TO-DO 
        # akses model
        
        result = Result(
            image=image_bytes,
            cancer_proba=0.75,
            user_id=1
        )

        db.session.add(result)
        db.session.commit()
        json_res = {
            "cancer_proba":0.75,  
            "user_id":1
        }
        result.image = image_bytes
        response = make_response(
            json.dumps(json_res),
            200
        )
        response.headers["Content-Type"]='application/json'
        
        return response
    if request.method == 'DELETE':
        pass
    return "test"