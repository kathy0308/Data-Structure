package bearmaps;

/*
public class KDTreeTest {
    private static void buildLectureTree() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));

    }

    private static void buildTreeWithDoubles() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(2, 3);

        KDTree kd = new KDTree(List.of(p1, p2));
    }

    @Test
    public void testNearestDemoSlides() {
        KDTree kd = buildLectureTree();
        Point actual = kd.nearest(0, 7);
        Point expected = new Point(1, 5);
        assertEquals(expected, actual);
    }

    private List<Point> randomPoint() {
        double x = r.nextDouble();
        double y = r.nextDouble();
        return new Point(x, y);
    }

    private List<Point> randomPoints(int N) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < N; i += 1) {
            points.add(randomPoint());
        }
        return points;
    }

    @Test
    public void testWith1000Points() {
        List<Point> points10000 = randomPoints(1000);
        NaivePointSet nps = new NaivePointSet(points10000);
        KDTree kd = new KDTree(points10000);

        List<Point> queries200 = randomPoints(200);
        for (Point p : queries200) {
            Point expected = nps.nearest(p.getX(), p.getY());
            Point actual = nps.nearest(p.getX(), p.getY());
            assertEquals(expected, actual);
        }
    }



    @Test
    public void testWithNPointsAndQQueries(int pointCount, int queryCount) {
        List<Point> points10000 = randomPoints(pointCount);
        NaivePointSet nps = new NaivePointSet(points);
        KDTree kd = new KDTree(points10000);

        List<Point> queries200 = randomPoints(queryCount);
        for (Point p : queries) {
            Point expected = nps.nearest(p.getX(), p.getY());
            Point actual = kd.nearest(p.getX(), p.getY());
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testWith1000PointsAnd300Queries() {
        int pointCount = 1000;
        int queryCount = 300;
        testWithNPointsAndQQueries(pointCount, queryCount);
    }

    @Test
    public void testWith10000PointsAnd3000Queries() {
        int pointCount = 100000;
        int queryCount = 30000;
        testWithNPointsAndQQueries(pointCount, queryCount);
    }

    @Test
    public void testWith100000PointsAnd30000Queries() {
        int pointCount = 100000;
        int queryCount = 30000;
        testWithNPointsAndQQueries(pointCount, queryCount);
    }
}
*/

