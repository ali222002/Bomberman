/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package model;

import java.awt.Image;
import java.awt.image.BufferedImage;
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
public class MonsterTest {
    private Monster monster;
    private Image[] upFrames;
    private Image[] downFrames;
    private Image[] leftFrames;
    private Image[] rightFrames;

    @Before
    public void setUp() {
        upFrames = new Image[] {new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB)};
        downFrames = new Image[] {new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB)};
        leftFrames = new Image[] {new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB)};
        rightFrames = new Image[] {new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB)};
        
        monster = new Monster(100, 100, 50, 50, upFrames, downFrames, leftFrames, rightFrames);
    }
    
@Test
public void testAnimationFrameUpdate() {
    monster.direction = Monster.Direction.RIGHT;
    monster.move();
    assertSame(monster._image, rightFrames[0]);

    monster.direction = Monster.Direction.UP;
    monster.move();
    assertSame(monster._image, upFrames[0]);
}
@Test
public void testChangeDirection() {
    Monster testMonster = new Monster(100, 100, 50, 50, upFrames, downFrames, leftFrames, rightFrames) {
        @Override
        public void ChangeDirection() {
            x_speed = -1;
            direction = Direction.LEFT;
        }
    };

    testMonster.ChangeDirection();
    assertEquals(-1, testMonster.x_speed, 0.0);
    assertEquals(Monster.Direction.LEFT, testMonster.direction);
}
@Test
public void testCollisionDetection() {
    ActiveObject hostileObject = new ActiveObject(102, 100, 30, 30, downFrames[0]);
    assertTrue(monster.did_hit(hostileObject));
}

    
}
