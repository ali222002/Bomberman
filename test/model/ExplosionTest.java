/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package model;

import java.awt.Graphics;
import java.awt.Image;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ali
 */
public class ExplosionTest {
    
    private Explosion explosion;
    private Image[] frames = new Image[3]; // Assuming the explosion uses 3 frames.

    @Before
    public void setUp() {
        // Initialize the explosion object with dummy coordinates and size
        explosion = new Explosion(100, 100, 50, 50, frames);
    }

    /**
     * Test of update method, of class Explosion.
     */
    @Test
    public void testUpdate() {
        explosion.update();
        // Test what should happen after one update
        assertEquals("Frame should increment by 1", 1, explosion._currentFrame);
    }

    /**
     * Test of isFinished method, of class Explosion.
     */
    @Test
    public void testIsFinished() {
        assertFalse("Initially, isFinished should return false", explosion.isFinished());
        // Simulate the explosion going through all frames
        for (int i = 0; i < frames.length; i++) {
            explosion.update();
        }
        assertTrue("Explosion should be finished after last frame", explosion.isFinished());
    }
}
