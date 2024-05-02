/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package model;

import java.awt.Graphics;
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
public class ActiveObjectTest {
@Test
public void testActiveObjectCreation() {
    BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
    ActiveObject obj = new ActiveObject(10, 20, 30, 40, image);
    
    assertEquals(10, obj.get_X());
    assertEquals(20, obj.get_Y());
    assertEquals(30, obj.getWidth());
    assertEquals(40, obj.getHeight());
    assertSame(image, obj._image);  // Checks if the image set in the object is the same as the one passed
}

@Test
public void testCollisionDetection() {
    BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
    ActiveObject obj1 = new ActiveObject(10, 10, 50, 50, image);
    ActiveObject obj2 = new ActiveObject(40, 40, 50, 50, image); // Should intersect with obj1
    ActiveObject obj3 = new ActiveObject(100, 100, 50, 50, image); // Should not intersect with obj1

    assertTrue(obj1.did_hit(obj2));
    assertFalse(obj1.did_hit(obj3));
}

@Test
public void testSetters() {
    BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
    ActiveObject obj = new ActiveObject(10, 10, 50, 50, image);

    obj.set_X(100);
    obj.set_Y(200);
    obj.setWidth(300);
    obj.setHeight(400);

    assertEquals(100, obj.get_X());
    assertEquals(200, obj.get_Y());
    assertEquals(300, obj.getWidth());
    assertEquals(400, obj.getHeight());
}  
}
