from flask import Flask
from rest import api, db

app = Flask(__name__)

app.register_blueprint(api)

#app.config["SQLALCHEMY_DATABASE_URI"] = 'sqlite:///test.db'
app.config["SQLALCHEMY_DATABASE_URI"] = 'mysql+pymysql://db_user:password@localhost/test'
# 1. buat database test di mysql, buat user dan pass,
# 2. buka python -> import db -> db.create_all()
db.init_app(app)
app.app_context().push()