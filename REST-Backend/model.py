from flask_sqlalchemy import SQLAlchemy
from datetime import datetime
from dataclasses import dataclass

db = SQLAlchemy()

#@dataclass
class Result(db.Model):
#    id:int
#    image:str
#    cancer_proba:float
#    user_id:int

    id           = db.Column(db.Integer, primary_key=True)
    image        = db.Column(db.LargeBinary(length=(2**32)-1))
    cancer_proba = db.Column(db.Float)
    user_id      = db.Column(db.Integer, db.ForeignKey('user.id'))

@dataclass
class User(db.Model):
    id:int
    name:str
    email:str
    pass_hash:str
    date_added:datetime

    id           = db.Column(db.Integer, primary_key=True)
    name         = db.Column(db.String(100), nullable=False)
    email        = db.Column(db.String(100), nullable=False, unique=True) 
    pass_hash    = db.Column(db.String(100), nullable=False)
    date_added   = db.Column(db.DateTime, default=datetime.utcnow, nullable=False)
