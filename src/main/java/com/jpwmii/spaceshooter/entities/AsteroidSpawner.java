package com.jpwmii.spaceshooter.entities;

import com.jpwmii.spaceshooter.graphics.RelativeBounds;

import java.util.Random;

public class AsteroidSpawner {
    private final Random random = new Random(System.currentTimeMillis());

    public AsteroidSpawner() {}

    public Asteroid spawnAsteroid() {
        double asteroidSize = 0.1 + random.nextDouble() * 0.2;
        double initialAsteroidHorizontalPosition = random.nextDouble();
        double asteroidSpeed = 1 + random.nextDouble() * 3;

        Asteroid asteroid = new Asteroid(
                new RelativeBounds(
                        initialAsteroidHorizontalPosition,
                        - asteroidSize,
                        asteroidSize,
                        asteroidSize
                ),
                asteroidSpeed
        );

        return asteroid;
    }
}
