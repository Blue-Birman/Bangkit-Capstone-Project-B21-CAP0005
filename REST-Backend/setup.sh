#!/bin/bash

# Let's create and activate a virtual environment using python

# install virtualenv package
pip3 install virtualenv

# Create a virtual environment name venv
python3 -m venv venv

# Activate the environment
source venv/bin/activate

# Let install all dependencies of Flask
pip3 install flask
pip3 install flask-sqlalchemy
pip3 install pymysql
pip3 install cryptography
pip3 install pillow

# export the application entry point 
export FLASK_APP=app.py

# create the tables
python3 init.py

# Finally run the application
flask run
