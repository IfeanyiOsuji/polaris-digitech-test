# polaris-digitech-test


This project aims to develop a drone application capable of delivering small items to remote locations. These boxes have the potential
to leapfrog traditional transportation infrastructure.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Examples](examples)

## Installation
This is a server side project so in order to set up the project, you need to JDK installed. You also need to have maven installed and a host of dependencies such as spring data jpa, spring data web, h2 database

## Usage

To run this project, make sure you have JDK 15 or higher installed

1. Clone the repository:
   git clone https://github.com/IfeanyiOsuji/Drone-App.git

2. Navigate to the project directory
   cd Drone-App

Compile the Java files
javac DroneServiceApplication.java
or
Click on the run button at the top right if you are using vs code or intellij idea

4. Run the program
   java DroneServiceApplication.java

If the code runs successfully, you can use postman to call the endpoints

## Example
1. To register a drone:
   http://localhost:8282/api/v1/DroneService/register

2. To load medications to a drone:
   http://localhost:8282/api/v1/DroneService/loadMedications/id/3a82a30cc82

3. To check drones available for loading:
   http://localhost:8282/api/v1/DroneService/availabledrones