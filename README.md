# Image Render Engine
As part of the course "Introduction to Software Engineering" we wrote from scratch a render engine in Java.
The program gets a XML file with a description about the scene and renders a picture from it.

# How it Works
In order to render a picture you need to define a scene. Basicly a scene is a camera in 3D with one or more of the folloing objects and lights:
1. Plane
2. Triangle
3. Sphere
4. Tube
5. Cylinder
6. Square
7. Cube
1. Ambient Light
2. Directional Light
3. Point Light
4. Spot Light

The camera is defined by a start point and a View Plane (Similar to the sensor in a camera).
Basicly the camera sends ray through eatch pixel in the View Plane of the camera and calculates the pixels color.

# Object Color & Material
In order to show objects in different color and different textures, eatch object has its own color and Material.
The Material defines how shinny or mat the object will look.

# Transparancy & Reflaction
There is olso an option to add the transparancy and reflaction to eatch object. 
For exsample if you set a high Transparancy, it will look like glass. If you set a high Reflaction , it will look like a mirror.

# How to Use
1. Download the folloing <a href="https://github.com/ElishaMayer/Execise_1_5779/raw/master/Run.zip">file<a/> and extract it.
2. To run a buildin example, run "Run_Test1.bat" or "Run_Test2.bat" file.
3. To run from XML, place the xml in /xml/Tests and run "Run_XML.bat".
4. When it finished rendering, the picture will be in /images.

# How to Write the XML

First the xml need to look like the fooloing example:
```xml
<scene name="images\\IMG_0001" version="1.0"
        background-color="0 0 0"
        screen-width="50"
        screen-height="50">
		 <resolution
            screen-width="2000"
            screen-height="2000"/>

<!--the lights and geometires-->
</scene>
```
Were the ```background-color``` is the backgound color of the scene,the first ```screen-width``` and ```screen-heigth``` are the camera size and the second are the pictures resolution.

# Examples 
![Alt text](readme/0001.jpg?raw=true "Title")
![Alt text](readme/0002.jpg?raw=true "Title")
![Alt text](readme/0003.jpg?raw=true "Title")
![Alt text](readme/0004.jpg?raw=true "Title")
![Alt text](readme/0005.jpg?raw=true "Title")
The are more pictures and in better resolution in <a href="https://github.com/ElishaMayer/Execise_1_5779/tree/master/images">this<a/> folder




