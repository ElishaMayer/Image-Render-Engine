# Image Render Engine
As part of the course "Introduction to Software Engineering" we wrote from scratch a render engine in Java.
The program gets a XML file with a description about the scene and renders a picture from it.

# Examples 
![Alt text](readme/0005.jpg?raw=true "Title")
![Alt text](readme/0001.jpg?raw=true "Title")
![Alt text](readme/0003.jpg?raw=true "Title")
![Alt text](readme/0004.jpg?raw=true "Title")
![Alt text](readme/0002.jpg?raw=true "Title")
For more examples go to <a href="https://github.com/ElishaMayer/Execise_1_5779/tree/master/images">here<a/> folder

# How to Use
1. Download the folloing <a href="https://github.com/ElishaMayer/Execise_1_5779/raw/master/Run.zip">file<a/> and extract it.
2. To run a buildin example, run "Run_Test1.bat" or "Run_Test2.bat" file.
3. To run from XML, place the xml in /xml/Tests and run "Run_XML.bat".
4. Once it is finished rendering, the picture will be in /images.
<br>
At the end of the page is a guid how to write the XML.

# How it Works
In order to render a picture you need to define a scene. Basicly, a scene is a camera in 3D with one or more of the folloing objects and lights:
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
Basicly the camera sends rays through eatch pixel in its View Plane and calculates the pixels color.

# Object Color & Material
In order to show objects in different color and different textures, eatch object has its own color and Material.
The Material defines how shinny or mat the object will look.

# Transparancy & Reflaction
There is olso an option to add the transparancy and reflaction to eatch object. 
For exsample if you set a high Transparancy, it will look like glass. If you set a high Reflaction , it will look like a mirror.
<br>
We added an option to make the reflacion and trancparcy look blurry by sending a beam of rays.

# Render Optimisations
We added to rendering optimisations.
1. Each row of the picture in rendered in a different thread. The threads are managed by a thread pool. With that the program can use all the cores of the processor.
2. We added Hiracical AABB Boxes. Each group of geometries is put in a AABB box, then all the boxes are grouped again recurcivly until there is only one box.
<br/>
To use this optimizations you need to add to the xml <optimised/> tag.
<br/>
Depending on the picture it is posibble to get a 9 times faster rendering.

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
## Add Camrea
To add the Camera add the follonig line inside "scene" tag:
```xml
  <camera p0="0 0 0" vTo="0 0 -1" vUp="0 -1 0" Screen-dist="50"/>
```
Where ```p0="0 0 0"``` is the cameras location, ```vTo="0 0 -1"``` is the direction towards the scene and ```vUp="0 -1 0"``` is the vector that is going up. vUp and vTo need to be othogonal.

To rotate the camera around the X Y or Z coordinate you can add the follonig row:
```xml
    <rotate x="0" y="30" z="22"/>
```
The numbers are in degrees.
## render settings
To optimise the runnig time, add the tag:
```xml
<optimised/>
```
To add a grid to the picture add:
```xml
<grid/>
```
To set the amount fo rays in a beam add:
```xml
<rays-beam num="10"/>
```
Stting this value too high can couse a very long rendering time.

## Add lights
To add Ambiet light
```xml
<ambient-light color="50 50 50" ka="0"/>
```
Where Ka is a factor of the britness.
<br>
<br>
To add Directional light add:
```xml
<directional-light color="100 100 100" direction="-0.8 0.3 -0.5"/>
```
To add Point light add:
```xml
<point-light color="255 100 100" position="0 0 0" kc="0" kl="0.0000001" kq="0.00001" />
```
Where Kc Kl Kq are attenuation factor.
<br>
<br>
To add Spot light add:
```xml
<spot-light color="255 255 100" position="0 0 0" kc="0" kl="0.0000001" kq="0.000006" direction="1 1 -1"/>
```
Where Kc Kl Kq are attenuation factor.
## Add Geometries
First you need to put them inside ```<geometries></geometries>``` tags.
### Sphere
```xml
<sphere center="0 0 -50" radius="50" emission="255 0 0" material="1 1 1"/>
```
### Triangle
```xml
<triangle p0="100 0 -49" p1="0 100 -49" p2="100 100 -49" emission="255 0 0" material="1 1 1"/>
```
### Plane
```xml
<plane p="-100 0 -49" v="0 -100 -49" emission="255 0 0" material="1 1 1"/>
```
### Tube
```xml
<tube Ray-p="-100 0 -49" Ray-v="0 -100 -49" radius="50" emission="255 0 0" material="1 1 1"/>
```
### Cylinder
1. Add by middle point and height:
```xml
<cylinder Ray-p="0 0 0" Ray-v="0 1 0" radius="50" heigth="30" emission="255 0 0" material="1 1 1"/>
```
2. Add by Two points:
```xml
<cylinder2p p1="-250 220 -800" p2="250 220 -800" radius="30" emission="0 0 20" material="1 1 1"/>
```
### Square
```xml
<square p0="10 10 0" p1="-10 10 0" p2="-10 -10 0" p3="10 -10 0" emission="50 100 50" material="1 1 1"/>
```
Where the four points eatch one is next to the other.

### Cube
```xml
<cube p0="10 10 0" p1="-10 10 0" p2="-10 -10 0" p3="10 -10 0" bp="10 10 -10" height="20" emission="0 20 20" material="1 1 1"/>
```
Whre the first points are a square and the bp is a point on the other side.
## Material
Each Geometry has a ```material``` tag.
There are 3 way to use it:
1. By Using only 3 parameters. Defusal, Spetcular and Shinnines.
```material="0.5 0.5 100"```
2. By using the the same 3 parameters and another 2 Transparcy and Reflaction.
```material="0.3 0.3 100 0.2 0.2"```
3. By using the same 5 parameters and The Transparcy blurry and Reflaction blurry
```material="0.3 0.3 100 0.2 0.2 0.012 0.011"```
## Example
A simple scene with a cube
```xml

<scene name="images\\IMG_0024_Cube2" version="1.0"
        background-color="0 0 0"
        screen-width="50"
        screen-height="50">
		 <resolution
            screen-width="2000"
            screen-height="2000"/>
    <ambient-light color="255 255 255" ka="0.1"/>
	<directional-light color="200 10 200" direction="-0.8 0.3 -0.5"/>
	<directional-light color="30 30 30" direction="0 0.2 -1"/>

  <camera p0="1200 -1000 800" vTo="0 0 -1" vUp="0 -1 0" Screen-dist="50"/>
    <rotate x="0" y="28.6479" z="17.1887"/>
    <geometries>
        <cube p0="-400 400 -1000" p1="-400 -400 -1000" p2="400 -400 -1000" p3="400 400 -1000" bp="400 400 -1800" height="-800" emission="0 20 20" material="0.7 0.9 30"/>
	    <plane p="0 500 0" v="0 1 0" emission="80 80 80" material="1 1 1"/>
		<plane p="0 0 -2600" v="0 0 1" emission="60 60 60" material="1 1 1"/>
		<plane p="-1700 0 0" v="1 0 0" emission="60 60 60" material="1 1 1"/>
 </geometries>
</scene>
```








