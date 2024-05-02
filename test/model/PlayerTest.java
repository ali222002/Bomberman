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
public class PlayerTest {
    
    private Player player;
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
        
        player = new Player(100, 100, 50, 50, upFrames, downFrames, leftFrames, rightFrames);
    }
    
  @Test
public void testSetSpeeds() {
    player.set_x_speed(5);
    assertEquals(5, player.get_x_speed(), 0.01);
    assertTrue(player.player_moves_on_x);
    assertFalse(player.player_moves_on_y);

    player.set_y_speed(-5);
    assertEquals(-5, player.get_y_speed(), 0.01);
    assertTrue(player.player_moves_on_y);
    assertFalse(player.player_moves_on_x);
}
@Test
public void testMovement() {
    player.set_x_speed(10);
    player.move_x();
    assertEquals(110, player.get_X());

    player.set_y_speed(-10);
    player.move_y();
    assertEquals(90, player.get_Y());
}
@Test
public void testCollisionDetection() {
    ActiveObject hostileObject = new ActiveObject(105, 105, 30, 30, downFrames[0]);
    player.set_x_speed(5);
    assertTrue(player.did_hit(hostileObject));

    player.set_x_speed(100);
    assertFalse(player.did_hit(hostileObject));
}
@Test
public void testAnimationFrameUpdate() {
    player.direction = Player.Direction.RIGHT;
    player.move();
    assertSame(player._image, rightFrames[0]);

    player.direction = Player.Direction.UP;
    player.move();
    assertSame(player._image, upFrames[0]);
}

    
}
