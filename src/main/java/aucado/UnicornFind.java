package aucado;

import static java.lang.System.exit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Comparator;
import java.lang.Math;
import java.util.Scanner;
import java.util.PriorityQueue;
import java.text.DecimalFormat;

public class UnicornFind {
    // match a point string that can look like "(1,-1,1)" or " ( +1 , -1, 1 ) "
    static String pointRegx = "^\\s*\\(\\s*([+-]?\\d{1,6})\\s*,\\s*([+-]?\\d{1,6})\\s*,\\s*([+-]?\\d{1,6})\\s*\\)\\s*$";
    static Pattern pointPattern = Pattern.compile(pointRegx);
    static Matcher matcher;
    static int maxInvalidLinesReported = 100;

    /**
     * Class that represents an (x,y,z) coordinate
     */
    public class Point {
        double x;
        double y;
        double z;
        boolean valid;

        /**
         * Default Constructor
         */   
        public Point() {
            valid = true;
            x=0.0;
            y=0.0;
            z=0.0;
        }

        /**
         * Constructor
         * @param point A valid point (ie: (1,2,3), (-1,+2,3), (1  ,  2, 3), ...)
         */
        public Point(String point) {
            valid = true;
            matcher = pointPattern.matcher(point);
            if (matcher.find()) {
                    x = Integer.parseInt(matcher.group(1));
                    y = Integer.parseInt(matcher.group(2));
                    z = Integer.parseInt(matcher.group(3));
            } else {
                valid = false;
            }
        }

        /**
         * Is it a valid point
         * @return True if point was valid.
         */
        public boolean isValid() {return valid;}

        /**
         * Return a string representation of the coordinate
         * @return The string representation of the coordinate
         */   
        public String toString() {return "(" + (int)x + "," + (int)y + "," + (int)z + ")";}
    }

    /**
     * Class that represents a location based upon a point and its distance from a specified point
     */
     public class Location {
        private Point p;
        private double distance;

        /**
         * Constructor
         * @param origin The origin point
         * @param target The point to calculate distance from origin
         */
        Location(Point origin, Point target) {
            p = target;
            distance = Math.sqrt(Math.pow((origin.x - p.x), 2) +
                                 Math.pow((origin.y - p.y), 2) +
                                 Math.pow((origin.z - p.z), 2));
        }

        /**
         * Returns the distance of the target from the origin point
         * @return Distance of target from origin
         */
        public double distance() {return distance;}

        /**
         * Returns the target point as a printable string
         * @return The target point
         */
        public String toString() {return p.toString();}
    }

    /**
     * Comparator function for Location for use in storing Location in PriorityQueue
     */
    public static class LocationComparator implements Comparator<Location> {

        @Override
        public int compare(Location l1, Location l2) {
            return (l1.distance() < l2.distance()) ? 1 : -1;
        }
    }
    
    /**
     * Class that maintains the closest points to a given origin
     */
    public class UnicornLocator {
        private Point origin;
        private int quantity;
        private PriorityQueue<Location> pq;
        private double maximum;

        /**
         * Constructor
         * @param origin The origin point to calculate the distance from 
         * @param quantity The number of closest points to origin to store
         */
        public UnicornLocator(Point origin, int quantity) {
            this.origin = origin;
            this.quantity = quantity;
            pq = new PriorityQueue<Location>(quantity, new LocationComparator());
        }

        /**
         * Add a point if it is closer than the current furthest point
         * @param point Point to add if it is closer that the current furthest point
         */
        public void add(Point point) {
            Location location = new Location(origin, point);
            if(pq.size() < quantity) {
                pq.add(location);
                maximum = pq.peek().distance();
            }
            else {
                if(location.distance() < maximum) {
                    pq.add(location);
                    pq.poll();
                    maximum = pq.peek().distance();
                }
            }
        }

        /**
         * Print closest location coordinates to stdout in ascending order 
         */
        public void printAndClear() {
            while (pq.size() > 0)
                System.out.println(pq.poll().toString());
        }

        public void printVectorAndClear() {
            DecimalFormat format = new DecimalFormat("######.000");
            while (pq.size() > 0) {
                Location l = pq.poll();
                System.out.println(format.format(l.distance()) + ", " + l.toString());
            }
        }

        public String toStringAndClear() {
            String s = new String();
            while (pq.size() > 0)
                s += pq.poll().toString();
            return s;
        }
        public void clear() {pq.clear();}
    }

    /**
     * Find a specified quantity of unicorn locations closest to an origin point.
     * @param origin The origin point to search from.
     * @param quantity The quantity of closest locations to find
     */
    private void findUnicorns(Point origin, int quantity) {
        Scanner stdin = new Scanner(System.in);
        int lineCount = 0, invalidCount = 0;

        UnicornLocator ul = new UnicornLocator(origin, quantity);

        while(stdin.hasNextLine()) {
            ++lineCount;
            Point point = new Point(stdin.nextLine());

            if(point.isValid()) {
                ul.add(point);
            }
            else {
                if(invalidCount++ < maxInvalidLinesReported) {
                    System.err.println("Invalid value discarded from input file at line " + lineCount +".");
                }
            }
        }
        ul.printAndClear();
    }

    /**
     *  Print simple usage message
     */
    private static void usage() {
        System.out.println("\nUsage: command <input> <\"point\"> <quantity> <output>\n");
        System.out.println("  input    - Fully qualified name of .txt input data file of unicorn locations. For example \"/Users/netapp/in.txt\" or \"\" to use stdin.");
        System.out.println("             Each line consists of a point in the format (x,y,z) where x,y and z are integers in the range of -999999 to 999999");
        System.out.println("  point    - Origin point to locate closest unicorns. For example \"(1,-1,1)\".");
        System.out.println("  quantity - Number of unicorn locations to output as a positive integer. For example: 10");
        System.out.println("             Note that the first points encountered are kept if more equisdistant points than quantity are present.");
        System.out.println("  output   - Fully qualified name of .txt output data file. For example \"/Users/netapp/out.txt\" or \"\" to use stdout.");
        System.out.println("             Each line consists of a point in the format (x,y,z) where x,y and z are integers in the range of -999999 to 999999");
        System.out.println("             The output file will be created if it does not exist or overwritten if it does exist.");
        exit(-1);
    }

    private void start(String[] args)
    {
        String point, outfile;
        int quantity = 0;

        if (args.length > 3) {
            // validate input file name is fully qualified .txt file otherwise read from stdin
            try {
                if(args[0].length() != 0) {
                    Path path = Paths.get(args[0]);
                    if(path.isAbsolute() && path.toString().endsWith(".txt")) {
                        System.setIn(new java.io.FileInputStream((args[0])));
                    }
                    else {
                        System.err.println("Error: Input file path must be an absolute path of a .txt file.");
                        usage();
                    }
                }
            } catch(FileNotFoundException e) {
                System.err.println("Error: Input file was not found or specified incorrectly.");
                usage();
            }

            // validate origin point
            Point origin = new Point(args[1]);
            if(!origin.isValid()) {
                System.err.println("Error: Origin point not specified correctly.");
                usage();
            }

            // validate quantity of points to output
            try {
                quantity = Integer.parseInt(args[2]);
                if(quantity < 1) {
                    System.err.println("Error: Quantity must be a positive integer.");
                    usage();
                }
            } catch (NumberFormatException e) {
                System.err.println("Error: Quantity must be a positive number.");
                usage();
            }

            // validate output file name is fully qualified .txt file otherwise write to stdout
            try {
                if(args[3].length() != 0) {
                    Path path = Paths.get(args[3]);
                    if(path.isAbsolute() && path.toString().endsWith(".txt")) {
                        File file = new File(args[3]);
                        FileOutputStream fos = new FileOutputStream(args[3]);
                        PrintStream ps = new PrintStream(fos);
                        System.setOut(ps);
                    }
                    else {
                        System.err.println("Error: Output file path must be an absolute path of a .txt file.");
                        usage();
                    }
                }
            } catch(FileNotFoundException e) {
                System.err.println("Error: Output file was not specified correctly.");
                usage();
            }

            // do the work
            System.err.println("Finding " + quantity + " unicorn" + (quantity == 1 ? " " : "s ") + "closest to " + args[1] + ".");
            findUnicorns(origin, quantity);

        } else {
            System.out.println("Error: More arguments must be specified.");
            usage();
        }
    }

    public static void main(String[] args) {
        try {
            UnicornFind u = new UnicornFind();
            u.start(args);
        }
        catch(Exception e) {
            System.err.println(e.toString());
        }
    }
}
