# Wireless-Control-of-Home-Appliance
Wireless Control of Home Appliance or Home Automation is a product which have the capability of turning power supply on and off. It not only handles DC supply it can control AC home supply too.
## Introduction
Wireless Control of Home Appliance is a home automation project which is powered by a 5-volt relay. To turn the relay on and off, cloud API of the ESP8266 Wi-Fi module manufacturer is used. It is a Restful Web API which requests using HTTP protocol and receives JSON object response.
To send and receive API requests Volley library for Android is used. The android application used as the user interface to switch the relay on and off.

### Things Used in This Product
Hardware components used in this project are listed as follows.

	Bolt Wi-Fi Module (ESP8266MOD)
	Micro USB cable with power adapter (5 Volt power adapter)
	Breadboard and Jumping wires
	A Light Emitting Diode
	A (33x10¹) ± 20% resistor and two diodes
	A CT BC547B JS NPN transistor
	A 5 Volt Relay
	An AC circuit which can be switched on and off.

Besides hardware component, software used for programming and developing are,

	Java Development Kit (JDK)
	Android Studio, IDE used for coding.
	Windows Subsystem for Android, for testing application.
	Android Debug Bridge (ADB)
	Bolt API from Bolt Cloud (over AWS)
	Volley dependency for Java Android


## Building The Project Hardware
Building the project hardware means setting up and assembling all the hardware components and establishing the core infrastructure. 

### Steps For Building the Project
#### Step 1
    Take a male-to-male jumping wire. Insert one of its ends in the 5V point of Bolt module and other end on the breadboard.
    Connect p-terminal of a diode to the same breadboard column and n-terminal to another empty column.
    Then take a male to female jumping wire and connect male side to the n diode terminal. The female end of wire should be connected to positive side of relay coil. 

 
![Semantic description of image](/images/image.png "Simplified Circuit Diagram")*Simplified Circuit Diagram*

#### Step 2
    Take another male-to-male jumping wire, insert one end in the GND point of Bolt module and other end on the board.
    Now take the transistor and place it on the board so that each of its three legs are inserted in different columns of the breadboard.
    The emitter leg of the transistor should be connected to that column in which we connected ground wire.
    Take another male to female wire, connect male end to the collector of the transistor and female end should be connected to the negative of relay coil.

#### Step 3
    Now, the circuit of the relay module is complete so, let’s connect the switching line to the circuit.
    Take a male-to-male jumper wire insert it in any one of the GPIO point and connect other end to a column of board.
    Connect 330Ω resistor to the same column terminal, other end to the positive leg of LED, and negative of LED to the ground.
    This LED will indicate whether the GPIO pin is at HIGH voltage or at LOW voltage.
    Now, take a diode connect its p-terminal to the column in which GPIO wire was connected and connect n-terminal to the base line of the transistor.
    All the connections are now ready and only the outer circuit which needs to be switched on and off is left.
    Both the diodes are only connected to avoid any reverse current during connections or running of the product that can damage the Bolt module.

#### Step 4
    Connect the relay to an AC circuit by cutting a wire of the circuit. After cutting we will get two end and circuit becomes open.
    Connect one end to the common terminal of the relay and another end to the normally closed of the relay.
    We are connecting to the normally closed as AC line should be switched on when AC supply is switched on and it can be switched off by turning the GPIO pin to HIGH voltage.
    We could have also connected it to normally open if AC line should be off when AC supply is switched on and then it can be switched on by turning the GPIO pin to HIGH voltage.

#### Step 5
The final step is to create an Android application which contains the ON and OFF buttons. These buttons will help to turn our AC supply on and off wirelessly.
