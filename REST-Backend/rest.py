from flask import Blueprint, request, make_response, json
from sqlalchemy import desc
from model import db, Result, User, Token, Article, Comment, History
from predict import predict_image
from PIL import Image
import io
import base64
import string
import random

token_size = 64



api = Blueprint('rest_api', __name__)
@api.route('/')
def index():
    return "index"

@api.route('/user', methods=['POST'])
def user_route():
    if request.method == 'POST':
        content = request.json
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

@api.route('/diagnose', methods=['GET', 'POST'])
def diagnose():
    if request.method == 'GET':
        content = request.json
        is_auth = authenticate(content["email"], content["token"]) 
        if is_auth :
            user = User.query.filter_by(email=content["email"]).first()
            results = list(Result.query.filter_by(user_id=user.id).order_by(desc(Result.date_added)).all())
            for result in results : 
                del result.image
            print(results)
            response = make_response(
                json.dumps(results),
                200
            )
            return response
        #TODO if auth fails response
    if request.method == 'POST':
        content = request.json
        is_auth = authenticate(content["email"], content["token"]) 
        if is_auth or content["email"]==None:
            # First we access the image that have been 
            # converted into string from frontend
            # and change it into bytes object
            image_blob = string_base64_to_blob(content["image"])
            # Then we change it into BytesIO so 
            # PIL can open and save the image
            # PIL is Python Imaging Library
            stream = io.BytesIO(image_blob)
            # Open and image using PIL.Image Class
            img = Image.open(stream)
            # TO-DO 
            # akses model
            prediction = predict_image(img)
            # Create a sample result
            # If the app is finished the cancer_proba is from model
            # and user_id from frontend
            # result is bytes (aka blob)

            user = User.query.filter_by(email=content["email"]).first()
            
            if is_auth :
                result = Result(
                    image=image_blob,
                    cancer_proba=prediction,
                    user_id=user.id
                )

                # Save the Result object to database
                db.session.add(result)
                db.session.commit()

            # and create a json object to be 
            # attached to the response 
            json_res = {
                "cancer_proba" : prediction
            }

            if is_auth : 
                json_res = {
                    "cancer_proba": prediction,  
                    "user_id"     : user.id,
                    "email"       : user.email,
                    "name"        : user.name
                }

            # Create the response
            response = make_response(
                json.dumps(json_res),
                200
            )
            response.headers["Content-Type"]='application/json'
            
            # Send the response
            return response
        #TODO if auth fails response
    #TODO default response


@api.route("/retrieve_result", methods=["GET"])
def retrieve_result_route():
    if request.method == 'GET':
        content = request.json  
        is_auth = authenticate(content["email"], content["token"])  
        if is_auth:
            result = Result.query.filter_by(id=content["result_id"]).first()
            user = User.query.filter_by(email=content["email"]).first()
            if user.id == result.user_id :  
                result.image = blob2string_base64(result.image)
                response = make_response(
                    json.dumps(result),
                    200
                )
                return response
            #TODO if auth failed response
        #TODO if not your result
    #TODO default response


@api.route("/articles", methods=["GET"])
def articles_route():
    if request.method == 'GET':
        content = request.json
        articles = Article.query.order_by(desc(Article.date_added)).all()
        response = make_response(
            json.dumps(articles),
            200
        )
        return response


@api.route("/article", methods=["GET"])
def article_route():
    if request.method == 'GET':
        content = request.json
        article = Article.query.filter_by(id=content["article_id"]).first()
        comments = list(Comment.query.filter_by(article_id=content["article_id"]))
        data = {
            "article" : article,
            "comments" : comments
        }
        response = make_response(
            json.dumps(data),
            200
        )
        return response
    #TODO default return


@api.route("/comment", methods=["POST"])
def comment_route():
    if request.method == 'POST':
        content = request.json
        is_auth = authenticate(content["email"], content["token"])
        if is_auth :
            user = User.query.filter_by(email=content["email"]).first()
            comment = Comment(
                user_id = user.id,
                article_id = content["article_id"],
                comment = content["comment"]
            )

            db.session.add(comment)
            db.session.commit()
            
            response = make_response(
                json.dumps(comment),
                200
            )
            return response
        #TODO return if failed to create token
    #TODO default return


@api.route("/login", methods=["POST"])
def login_route():
    if request.method == 'POST':
        content = request.json
        is_auth = auth_pass(content["email"], content["pass_hash"])  
        if is_auth :
            token = generate_token(content["email"])
            response = make_response(
                json.dumps(token),
                200
            )
            return response
        #TODO return if failed to create token
    #TODO default return

    
@api.route("/logout", methods=["POST"])
def logout_route():
    if request.method == 'POST':
        content = request.json
        is_auth = authenticate(content["email"], content["token"]) 
        if is_auth :
            deactivate_token(content["token"])
            token = Token.query.filter_by(token=content["token"]).first()
            response = make_response(
                json.dumps(token),
                200
            )
            return response
        #TODO return if failed to create token
    #TODO default return
        

def authenticate(email, token_str):
    if email == None:
        return False
    user = User.query.filter_by(email=email).first()
    token = Token.query.filter_by(token=token_str).first()
    if token.user_id == user.id and token.is_active:
        return True
    return False


def auth_pass(email, pass_hash):
    user = User.query.filter_by(email=email).first()
    if pass_hash == user.pass_hash:
        return True
    return False
    

def generate_token(email):
    user = User.query.filter_by(email=email).first()
    token_str = ''.join(random.choices(string.ascii_uppercase + string.digits, k = token_size))
    token = Token(
        user_id=user.id,
        token=token_str
    )
    db.session.add(token)
    db.session.commit()
    return token


def deactivate_token(token_str):
    token = Token.query.filter_by(token=token_str).first()
    token.is_active = False
    db.session.commit()


def blob2string_base64(blob_item):
    return base64.encodebytes(blob_item).decode('ascii')

def string_base64_to_blob(string_base64):
    return base64.b64decode(string_base64)
    