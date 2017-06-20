/*
 * UnicornFind tests
 */
package aucado;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.PriorityQueue;
import aucado.UnicornFind;

public class UnicornFindTest {
    @Test public void testPointDefaultConstructor() {
        UnicornFind ucf = new UnicornFind();
        UnicornFind.Point p1 = ucf.new Point();
        assertEquals("valid is true", true, p1.isValid());
        assertEquals("x coordinate should be zero", 0.0, p1.x, 0);
        assertEquals("y coordinate should be zero", 0.0, p1.y, 0);
        assertEquals("z coordinate should be zero", 0.0, p1.z, 0);
    }

    @Test public void testPointConstructorSuccess() {
        {
            UnicornFind ucf = new UnicornFind();
            UnicornFind.Point p1 = ucf.new Point( "(0,0,0)" );
            assertEquals("valid is true", true, p1.isValid());
            assertEquals("x coordinate should be zero", 0.0, p1.x, 0);
            assertEquals("y coordinate should be zero", 0.0, p1.y, 0);
            assertEquals("z coordinate should be zero", 0.0, p1.z, 0);
        }
        {
            UnicornFind ucf = new UnicornFind();
            UnicornFind.Point p1 = ucf.new Point( "(1,2,3)" );
            assertEquals("valid is true", true, p1.isValid());
            assertEquals("x coordinate should be 1.0", 1.0, p1.x, 0);
            assertEquals("y coordinate should be 2.0", 2.0, p1.y, 0);
            assertEquals("z coordinate should be 3.0", 3.0, p1.z, 0);
        }
        {
            UnicornFind ucf = new UnicornFind();
            UnicornFind.Point p1 = ucf.new Point( "(+1,+2,+3)" );
            assertEquals("valid is true", true, p1.isValid());
            assertEquals("x coordinate should be 1.0", 1.0, p1.x, 0);
            assertEquals("y coordinate should be 2.0", 2.0, p1.y, 0);
            assertEquals("z coordinate should be 3.0", 3.0, p1.z, 0);
        }
        {
            UnicornFind ucf = new UnicornFind();
            UnicornFind.Point p1 = ucf.new Point( "(-1,-2,-3)" );
            assertEquals("valid is true", true, p1.isValid());
            assertEquals("x coordinate should be -1.0", -1.0, p1.x, 0);
            assertEquals("y coordinate should be -2.0", -2.0, p1.y, 0);
            assertEquals("z coordinate should be -3.0", -3.0, p1.z, 0);
        }
        {
            UnicornFind ucf = new UnicornFind();
            UnicornFind.Point p1 = ucf.new Point( " ( 1  , 2, 3  )   " );
            assertEquals("valid is true", true, p1.isValid());
            assertEquals("x coordinate should be 1.0", 1.0, p1.x, 0);
            assertEquals("y coordinate should be 2.0", 2.0, p1.y, 0);
            assertEquals("z coordinate should be 3.0", 3.0, p1.z, 0);
        }
        {
            UnicornFind ucf = new UnicornFind();
            UnicornFind.Point p1 = ucf.new Point( "(999999 , 999999 , -999999)");
            assertEquals("valid is true", true, p1.isValid());
            assertEquals("x coordinate should be 999999.0", 999999.0, p1.x, 0);
            assertEquals("y coordinate should be 999999.0", 999999.0, p1.y, 0);
            assertEquals("z coordinate should be 999999.0", -999999.0, p1.z, 0);
        }
    }
    @Test public void testPointConstructorNotValid() {
        {
            UnicornFind ucf = new UnicornFind();
            UnicornFind.Point p1 = ucf.new Point( "(a,0,0)" );
            assertEquals("valid is false", false, p1.isValid());
        }
        {
            UnicornFind ucf = new UnicornFind();
            UnicornFind.Point p1 = ucf.new Point( "(0,b,0)" );
            assertEquals("valid is false", false, p1.isValid());
        }
        {
            UnicornFind ucf = new UnicornFind();
            UnicornFind.Point p1 = ucf.new Point( "(0,0,c)" );
            assertEquals("valid is false", false, p1.isValid());
        }
        {
            UnicornFind ucf = new UnicornFind();
            UnicornFind.Point p1 = ucf.new Point( "(1234567,0,0)" );
            assertEquals("valid is false", false, p1.isValid());
        }
        {
            UnicornFind ucf = new UnicornFind();
            UnicornFind.Point p1 = ucf.new Point( "(0,1234567,0)" );
            assertEquals("valid is false", false, p1.isValid());
        }
        {
            UnicornFind ucf = new UnicornFind();
            UnicornFind.Point p1 = ucf.new Point( "(0,0,1234567)" );
            assertEquals("valid is false", false, p1.isValid());
        }
    }
    @Test public void testLocationDistanceValidForX() {
        UnicornFind ucf = new UnicornFind();

        UnicornFind.Point origin = ucf.new Point( "(0,0,0)" );
        assertEquals("valid is true", true, origin.isValid());

        UnicornFind.Point target = ucf.new Point( "(10,0,0)" );
        assertEquals("valid is true", true, target.isValid());

        UnicornFind.Location l1 = ucf.new Location(origin, target);
        assertEquals("distance is 10", 10, l1.distance(), 0);
    }
    @Test public void testLocationDistanceValidForY() {
        UnicornFind ucf = new UnicornFind();

        UnicornFind.Point origin = ucf.new Point( "(0,0,0)" );
        assertEquals("valid is true", true, origin.isValid());

        UnicornFind.Point target = ucf.new Point( "(0,11,0)" );
        assertEquals("valid is true", true, target.isValid());

        UnicornFind.Location l1 = ucf.new Location(origin, target);
        assertEquals("distance is 11", 11, l1.distance(), 0);
    }
    @Test public void testLocationDistanceValidForZ() {
        UnicornFind ucf = new UnicornFind();

        UnicornFind.Point origin = ucf.new Point( "(0,0,0)" );
        assertEquals("valid is true", true, origin.isValid());

        UnicornFind.Point target = ucf.new Point( "(0,0,12)" );
        assertEquals("valid is true", true, target.isValid());

        UnicornFind.Location l1 = ucf.new Location(origin, target);
        assertEquals("distance is 12", 12, l1.distance(), 0);
    }
    @Test public void testLocationDistanceValidForXYZ() {
        UnicornFind ucf = new UnicornFind();

        UnicornFind.Point origin = ucf.new Point( "(1,1,1)" );
        assertEquals("valid is true", true, origin.isValid());

        UnicornFind.Point target = ucf.new Point( "(-1,-1,-1)" );
        assertEquals("valid is true", true, target.isValid());

        UnicornFind.Location l1 = ucf.new Location(origin, target);
        assertEquals("distance is 3.464102", 3.464102, l1.distance(), .000001);
    }

    @Test public void testLocationToString() {
        UnicornFind ucf = new UnicornFind();

        UnicornFind.Point origin = ucf.new Point( "(0,0,0)" );
        assertEquals("valid is true", true, origin.isValid());

        UnicornFind.Point target = ucf.new Point( " ( 1 ,2 , 3 )" );
        assertEquals("valid is true", true, target.isValid());

        UnicornFind.Location l1 = ucf.new Location(origin, target);
        assertEquals("string  is (1,2,3)", "(1,2,3)", l1.toString());
    }

    @Test public void testFindUnicornsOneOnly() {
        UnicornFind ucf = new UnicornFind();
        int quantity = 1;
        UnicornFind.Point origin = ucf.new Point( "(0,0,0)" );
        assertEquals("valid is true", true, origin.isValid());

        String expected;
        expected = "(1,0,0)";

        UnicornFind.UnicornLocator finder = ucf.new UnicornLocator(origin, quantity);
        finder.add(ucf.new Point("(1,0,0)"));
        finder.add(ucf.new Point("(2,0,0)"));
        assertEquals(expected, finder.toString());

        finder.clear();
        finder.add(ucf.new Point("(2,0,0)"));
        finder.add(ucf.new Point("(1,0,0)"));
        assertEquals(expected, finder.toString());

        finder.clear();
        finder.add(ucf.new Point("(1,0,0)"));
        finder.add(ucf.new Point("(1,0,0)"));
        assertEquals(expected, finder.toString());
    }
    @Test public void testFindUnicornsSameDistances() {
        UnicornFind ucf = new UnicornFind();
        int quantity = 2;
        UnicornFind.Point origin = ucf.new Point( "(0,0,0)" );
        assertEquals("valid is true", true, origin.isValid());

        String expected;
        expected = "(2,0,0)(2,0,0)";

        UnicornFind.UnicornLocator finder = ucf.new UnicornLocator(origin, quantity);
        finder.add(ucf.new Point("(2,0,0)"));
        finder.add(ucf.new Point("(2,0,0)"));
        finder.add(ucf.new Point("(3,0,0)"));
        assertEquals(expected, finder.toString());

        finder.clear();
        finder.add(ucf.new Point("(3,0,0)"));
        finder.add(ucf.new Point("(2,0,0)"));
        finder.add(ucf.new Point("(2,0,0)"));
        assertEquals(expected, finder.toString());

        finder.clear();
        finder.add(ucf.new Point("(2,0,0)"));
        finder.add(ucf.new Point("(3,0,0)"));
        finder.add(ucf.new Point("(2,0,0)"));
        assertEquals(expected, finder.toString());
     }
     @Test public void testFindUnicornsBestCase() {
        UnicornFind ucf = new UnicornFind();
        int quantity = 2;
        UnicornFind.Point origin = ucf.new Point( "(0,0,0)" );
        assertEquals("valid is true", true, origin.isValid());

        String expected;
        expected = "(1,0,0)(2,0,0)";

        UnicornFind.UnicornLocator finder = ucf.new UnicornLocator(origin, quantity);
        finder.add(ucf.new Point("(1,0,0)"));
        finder.add(ucf.new Point("(2,0,0)"));
        finder.add(ucf.new Point("(3,0,0)"));
        assertEquals(expected, finder.toString());
    }
    @Test public void testFindUnicornsWorstCase() {
        UnicornFind ucf = new UnicornFind();
        int quantity = 2;
        UnicornFind.Point origin = ucf.new Point( "(0,0,0)" );
        assertEquals("valid is true", true, origin.isValid());

        String expected;
        expected = "(1,0,0)(2,0,0)";

        UnicornFind.UnicornLocator finder = ucf.new UnicornLocator(origin, quantity);
        finder.add(ucf.new Point("(3,0,0)"));
        finder.add(ucf.new Point("(2,0,0)"));
        finder.add(ucf.new Point("(1,0,0)"));
        assertEquals(expected, finder.toString());
    }
    @Test public void testFindUnicornsLessPresentThanQuantity() {
        UnicornFind ucf = new UnicornFind();
        int quantity = 20;
        UnicornFind.Point origin = ucf.new Point( "(0,0,0)" );
        assertEquals("valid is true", true, origin.isValid());

        String expected;
        expected = "(3,0,0)";

        UnicornFind.UnicornLocator finder = ucf.new UnicornLocator(origin, quantity);
        finder.add(ucf.new Point("(3,0,0)"));
        assertEquals(expected, finder.toString());
    }
    @Test public void testFindUnicornsFirstInStay() {
        UnicornFind ucf = new UnicornFind();
        int quantity = 2;
        UnicornFind.Point origin = ucf.new Point( "(0,0,0)" );
        assertEquals("valid is true", true, origin.isValid());

        String expected;
        expected = "(1,0,0)(3,0,0)";

        UnicornFind.UnicornLocator finder = ucf.new UnicornLocator(origin, quantity);
        finder.add(ucf.new Point("(1,0,0)"));
        finder.add(ucf.new Point("(3,0,0)"));
        finder.add(ucf.new Point("(-3,0,0)"));
        finder.add(ucf.new Point("(0,3,0)"));
        assertEquals(expected, finder.toString());
    }
}
