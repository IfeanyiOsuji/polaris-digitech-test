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
   git clone https://github.com/IfeanyiOsuji/polaris-digitech-test.git

2. Navigate to the project directory
   cd box_drone

Compile the Java files
javac BoxDroneApplication.java
or
Click on the run button at the top right if you are using vs code or intellij idea

4. Run the program
   java BoxDroneApplication.java

If the code runs successfully, you can use postman to call the endpoints

## Example
1. To create a box:
   http://localhost:8282/api/v1/box/create-box

2. To load a box with items:
   http://localhost:8282/api/v1/box/items/id/{txref}

3. To check available boxes for loading:
   http://localhost:8282/api/v1/box/available-boxes

4. To check battery level for a given box
   http://localhost:8282/api/v1/box/box/batterylevel/id/{txref}

5. To check loaded items for a given box
   http://localhost:8282/api/v1/box/items/box/id/{txref}

## Note:
The input data are in json format