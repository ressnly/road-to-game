package ru.ress.roadtogame.core;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 * Created by ress on 02.10.17.
 */
public class Entity {
    public final static int TDEFAULT = 0;
    public final static int TWALL = 1;
    public final static int TPLAYER = 2;

    protected double x,y;
    protected int width, height;
    protected double imageSpeed;
    protected double imageIndex;
    protected int framesInAnimate;
    protected Sprite sprite;
    protected int originX, originY;
    protected int xScale, yScale;
    protected int type;
    private int textureSize;

    public Entity() {
        type = TDEFAULT;
    }

    public Entity(Texture texture, int w, int h) {
        type = TDEFAULT;

        sprite = new Sprite();
        sprite.setTexture(texture);
        sprite.setTextureRect(new IntRect(0,0,w,h));
        sprite.setPosition(0,0);
        textureSize = texture.getSize().x;
        originY = originX = texture.getSize().y / 2;
        xScale = yScale = 1;

        width = w;
        height = h;
        x = y = 0;

        adaptAnimation();
    }

    public void step() {
        return;
    }

    public boolean isLocated(double _x, double _y) {
        if (_x >= x && _x <= x + width &&
                _y >= y && _y <= y + height) return true;
        return false;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private void updateAnimation() {
        sprite.setScale(xScale, yScale);
        sprite.setPosition((int)x,(int)y);
    }

    public void setCoords(int x, int y, int x1, int y1) {
        this.x = x;
        this.y = y;
        width = x1;
        height = y1;

        adaptAnimation();
    }

    public Vector2f getPosition() {
        return new Vector2f((float)x,(float)y);
    }

    public int getType() {
        return type;
    }

    public Sprite getSprite() {
        if (sprite != null) updateAnimation();
        return sprite;
    }

    private void adaptAnimation() {
        framesInAnimate = textureSize / width;
        imageSpeed = imageIndex = 0;
        if (sprite != null) {
            sprite.setTextureRect(new IntRect(0,0,width,height));
            sprite.setOrigin(originX,originY);
        }
    }

    protected void playAnimation(double imgSpeed) {
        int prevIndex = (int)Math.ceil(imageIndex);
        imageIndex+=imgSpeed;
        if (imageIndex > framesInAnimate) imageIndex = 0;

        int curIndex = (int)(imageIndex);
        if (curIndex != prevIndex) sprite.setTextureRect(new IntRect(curIndex*width,0,width,height));
    }
}
