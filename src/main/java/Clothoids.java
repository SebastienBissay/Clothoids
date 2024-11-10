import processing.core.PApplet;
import processing.core.PVector;

import static parameters.Parameters.*;
import static save.SaveUtil.saveSketch;

public class Clothoids extends PApplet {
    PVector v;
    float a;
    float hue;

    public static void main(String[] args) {
        PApplet.main(Clothoids.class);
    }

    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
        randomSeed(SEED);
    }

    @Override
    public void setup() {
        colorMode(COLOR_MODE.mode(), COLOR_MODE.max1(), COLOR_MODE.max2(), COLOR_MODE.max3(), COLOR_MODE.maxA());
        background(BACKGROUND_COLOR.red(), BACKGROUND_COLOR.green(), BACKGROUND_COLOR.blue());
        noStroke();
        fill(FILL_COLOR.red(), FILL_COLOR.green(), FILL_COLOR.blue(), FILL_COLOR.alpha());
        rect(MARGIN, MARGIN, WIDTH - 2 * MARGIN, HEIGHT - 2 * MARGIN);

        a = random(TWO_PI);
        hue = random(1);
        v = new PVector(0, 0);
    }

    @Override
    public void draw() {
        hue = (hue + HUE_GAUSSIAN_FACTOR * randomGaussian() + 1) % 1;
        stroke(hue, STROKE_COLOR.green(), STROKE_COLOR.blue(), STROKE_COLOR.alpha());
        PVector p = v;
        float c = 0;
        float r = SPIRAL_DIMINISHING_RADIUS_FACTOR * sin(random(PI));

        for (int i = 0; i < MAX_SPIRAL_LENGTH; i++) {
            point((p.x + WIDTH - 2 * MARGIN) % (WIDTH - 2 * MARGIN) + MARGIN,
                    (p.y + HEIGHT - 2 * MARGIN) % (HEIGHT - 2 * MARGIN) + MARGIN);
            c += r;
            p.add(new PVector(cos(a + sq(c)), sin(a + sq(c))));
        }
        v.x = (p.x + WIDTH - 2 * MARGIN) % (WIDTH - 2 * MARGIN);
        v.y = (p.y + HEIGHT - 2 * MARGIN) % (HEIGHT - 2 * MARGIN);
        a += sq(c);
        if (frameCount >= MAX_ITERATIONS) {
            noLoop();
            saveSketch(this);
        }

    }
}
