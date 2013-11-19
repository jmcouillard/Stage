Stage
==============

Bringing up the speed and efficacity of AS3 to the power and flexibilty of Processing 2.0. Inspired by the *Stage* and *DisplayObject* relationship of Flash, *Stage* make it easier to create concrete application, using stunning effect and/or shaders, at a high framerate (it uses OpenGL out of the box).

## Donwnload

The library doesn't have an official website for now. You can donwnload the *distribution* here : [http://www.jmcouillard.com/Stage.zip](http://www.jmcouillard.com/Stage.zip)

## Overview

A custom manager that is meant to be used be end-user. Easy to install and configure. Can be used to list and mange nodes as well as terms.

First, you need to make sure that you are using the OPENGL rederer:

```
size(400, 400, OPENGL);
```

You can then create a *Stage* object:

```
Stage stage;
stage = new Stage(this, width, height);
```

Don't forget to update it and draw the stage inside the *draw()* function:

```
void draw() {
  background(0);
  stage.update();
  stage.draw(this);
}
```

From there, you can add object to your Stage, and they will be handled automatically:

```
Sprite layer = new Sprite(this, 200, 200);
stage.addChild(layer);
```

You will find most of the properties you expect. Just remember that you are using Java, so you need to use function *layer.setX()* instead of plain properties *layer.x*:

```
layer.setX(200);
layer.setY(150);
layer.setZ(20);
layer.setRotation(PI/2.0f);
layer.setRotation(200);
layer.setBlendMode(ADD);
```

## Features

Each of the following features have an example in the library to  

- **Image** : Create a sprite by loading an image.

- **VideoPlayer** : Create a sprite by loading a video. Use loop() or play() to begin playback.

- **Shaders** : Add shaders to any Sprite by calling *addPass()*. Shaders need to be compatible with Processing. Check the ShaderSimple example for more information.
 
- **Blend mode** : Change sprite blend mode by calling *setBlendMode()*. This is compatible with the same blend modes than [Processing](http://processing.org/reference/blendMode_.html).
 
- **Keystone stage** : Allow you to create a basic deformation map (for projection on non-standard screen, or a non-optimal projector position)

- **Config** : Save and load settings easily.

- **Tween** : Tween properties such as position, scale and rotation easily by using *to()*. This require Ani library (see dependecies below). Check the TweenPosition example for more informations ont tweening.

- **Events** : Add event listeners like you espect them to be (MouseEvent, KeyboardEvent or CustomEvent). This allow communication between different children levels of the stage.

- **Textfield** : More details coming soon.

- **Websocket control** : Control your stage using a simple API with Stage-Websocket plugin. Available at [https://github.com/jmcouillard/Stage-Websocket](https://github.com/jmcouillard/Stage-Websocket).

## Dependencies

You will need the *Ani* Library in order to use the *to* shurtcut. This library is used to tween properties (greensock-like). It make it pretty simple to do things such as :

```
layer.to(1, "x:"+mouseX+",y:"+mouseY);
```

If you wish to use this feature, you will also need to initiate the Ani library, by calling this line on your setup() :

```
Ani.init(this);
```

You may find more information about the library here : [www.looksgood.de](http://www.looksgood.de/libraries/Ani/).

## Compatibilty

Current version has been tested and updated for Processing 2.0.3. It is known to be working on Windows 7 and OSX 10.8.

## Strange things

I still don't have understood why, but if you do a `background(0)` (or any other color) before drawing the stage, you will get a wrong alpha compositing. So, in orther to avoid that, simply **don't** clear background in `draw()`.

## Eclipse

I strongly recommand using Eclipse as IDE to get more of this library. The auto-complete and hot-swapping features make it amazingly fun to use! This library integrates correctly and easily with Eclipse.
