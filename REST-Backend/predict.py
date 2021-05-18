from tensorflow.keras.models import load_model
from numpy import asarray
from tensorflow.keras.applications.vgg16 import preprocess_input


MODEL_NAME = "model/test_model"

model = load_model(MODEL_NAME)

def predict_image(img):
    img_array = asarray(img)
    print("PREPARE THE PREDICTION")
    img_array = img_array.reshape((1,img_array.shape[0],img_array.shape[1],img_array.shape[2]))
    print(img_array.shape)
    processed_img = preprocess_input(img_array)
    print("Processed----------------------------------------------------")
    prediction = model.predict(x=processed_img)
    print("PREDICTION :", prediction)
    return float(prediction[0][0])