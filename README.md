## Deployment
The steps of deploy the backend and machine learning model:
1. First We created the database for the application, We used Cloud SQL with MySQL instance as our database. Then we connect to it using our local PHP My Admin tools to import the SQL files that we have created from the local database. Our database consist of : 
Instance id : db-skin-cancer
  - 2 vCPU
  - 3.75 GB of Memory
  - 100 GB of Hard Disk Drive Storage 
2. After the database is created, we create a VM instance with compute engine. Here’s the specification:
  - Machine type: E2-medium (2 vCPUs, 4 GB memory)
  - Location: asia-southeast2 (Jakarta)

  After creating the instance, we install git, venv, python in SSH
3. Then, we create firewall rules to allow the android app to access the API. Here’s the specification:
  - direction: ingress
  - target tags: "http-server" and "flask" 
  - source filter (IP ranges): 0.0.0.0/0 
  - protocol:tcp:22, tcp:80, and tcp:5000
4. We deploy the application with this step:
  - First we update and clone the repository into our machine. We also upload the tensorflow model (since it is too big for our repo)
  - After that we install the required packages to setup a flask environment 
  - We also created a virtual environment for the app. 
  - After that we test the app by using “flask run --host=0.0.0.0” command
  - After we are sure that the app is working fine, we created a service that can run the app automatically
  - After the service is created, all we need is to make sure that the service is started and enabled

