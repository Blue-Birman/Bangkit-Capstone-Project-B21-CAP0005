from flask_sqlalchemy import SQLAlchemy
from datetime import datetime
from dataclasses import dataclass

db = SQLAlchemy()

@dataclass
class Result(db.Model):
    id:int
    image:str
    cancer_proba:float
    user_id:int
    date_added:datetime

    id           = db.Column(db.Integer, primary_key=True)
    image        = db.Column(db.LargeBinary(length=(2**24)-1))
    cancer_proba = db.Column(db.Float)
    user_id      = db.Column(db.Integer, db.ForeignKey('user.id'))
    date_added   = db.Column(db.DateTime, default=datetime.utcnow, nullable=False)


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
    pass_hash    = db.Column(db.String(64), nullable=False)
    date_added   = db.Column(db.DateTime, default=datetime.utcnow, nullable=False)


@dataclass
class History(db.Model):
    id:int
    user_id:int
    activity:str
    date_added:datetime

    id         = db.Column(db.Integer, primary_key=True)
    user_id    = db.Column(db.Integer, db.ForeignKey('user.id'))
    activity   = db.Column(db.String(200), nullable=False)
    date_added = db.Column(db.DateTime, default=datetime.utcnow, nullable=False)


@dataclass
class Article(db.Model):
    id:int
    title:str
    image:str
    article:str
    date_added:datetime

    id         = db.Column(db.Integer, primary_key=True)
    title      = db.Column(db.String(100), nullable=False)
    image      = db.Column(db.LargeBinary(length=(2**24)-1))
    article    = db.Column(db.Text(2**24-1))
    date_added = db.Column(db.DateTime, default=datetime.utcnow, nullable=False)


@dataclass
class Comment(db.Model):
    id:int
    user_id:int
    article_id:int
    comment:str
    date_added:datetime

    id         = db.Column(db.Integer, primary_key=True)
    user_id    = db.Column(db.Integer, db.ForeignKey('user.id'))
    article_id = db.Column(db.Integer, db.ForeignKey('article.id'))
    comment    = db.Column(db.String(500), nullable=False)
    date_added = db.Column(db.DateTime, default=datetime.utcnow, nullable=False)


@dataclass
class Token(db.Model):
    id:int
    user_id:int
    token:str
    is_active:bool
    date_added:datetime

    id         = db.Column(db.Integer, primary_key=True)
    user_id    = db.Column(db.Integer, db.ForeignKey('user.id'))
    token      = db.Column(db.String(64), nullable=False, unique=True)
    is_active  = db.Column(db.Boolean, default=True)
    date_added = db.Column(db.DateTime, default=datetime.utcnow, nullable=False)
