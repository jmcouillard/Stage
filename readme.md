Stage
==============

Bringing up the speed and efficacity of AS3 to the power and flexibilty of Processing 2.0. Inspired by the *Stage* and *DisplayObject* relationship, *Stage* make it easier to create concrete application, using stunning effect and/or shaders, at a high framerate (it uses OpenGL out of the box).

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

Don't forget to update it and draw it inside the *draw()* function:

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

You will find most of the properties you expect. Just remember that you are in Java, so you need to use function *layer.setX()* instead of plain properies *layer.x*:

```
layer.setX(200);
layer.setY(150);
layer.setZ(20);
layer.setRotation(PI/2.0f);
layer.setRotation(200);
layer.setBlendMode(ADD);
```

## Dependencies

You will need the *Ani* Library in order to use *Stage*. This library is used to tween properties (greensock-like). You may find more information about the library here : [www.looksgood.de](http://www.looksgood.de/libraries/Ani/).

## Compatibilty

Current version has been tested and updated for Processing 2.0.1.
