import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Closed {

    private static final Logger logger = Logger.getLogger(Closed.class.getName());

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        String filename = scanner.nextLine();
        File infile = new File(filename);
        Scanner input = new Scanner(infile);

        int T;
        int R;
        int C;
        int a;
        int b;
        int tdep;

        T = input.nextInt();
        R = input.nextInt();
        C = input.nextInt();
        a = input.nextInt();
        b = input.nextInt();
        tdep = input.nextInt();

        Set<Integer> towns = initilizeTowns(T);
        Map<Integer, List<Road>> roadStructure = initilizeRoadStructure(input, R);
        Map<Integer, List<Closure>> closures = getClosures(input, C);

        input.close();
        scanner.close();

        if (logger.isLoggable(Level.INFO)) {
            logger.info(Integer.toString(computeEarliestArrival(a, b, roadStructure, closures, tdep, towns)));
        }
    }

    private static Set<Integer> initilizeTowns(int T) {
        Set<Integer> towns = new HashSet<>();

        for (int i = 0; i < T; i++) {
            towns.add(i + 1);
        }
        return towns;
    }

    private static Map<Integer, List<Closure>> getClosures(Scanner input, int C) {
        Map<Integer, List<Closure>> closures = new HashMap<>();
        for (int i = 0; i < C; i++) {
            int road = input.nextInt();
            int cstart = input.nextInt();
            int cend = input.nextInt();
            closures.putIfAbsent(road, new ArrayList<>());
            closures.get(road).add(new Closure(road, cstart, cend));
        }
        return closures;
    }

    private static Map<Integer, List<Road>> initilizeRoadStructure(Scanner input, int R) {
        Map<Integer, List<Road>> roadStructure = new HashMap<>();

        for (int i = 0; i < R; i++) {
            int t1 = input.nextInt();
            int t2 = input.nextInt();
            int dur = input.nextInt();

            roadStructure.putIfAbsent(t1, new ArrayList<>());
            roadStructure.putIfAbsent(t2, new ArrayList<>());
            roadStructure.get(t1).add(new Road(i + 1, t1, t2, dur));
            roadStructure.get(t2).add(new Road(i + 1, t2, t1, dur));
        }
        return roadStructure;
    }

    private static int computeEarliestArrival(int a, int b, Map<Integer, List<Road>> roads,
            Map<Integer, List<Closure>> closures, int tdep, Set<Integer> towns) {

        Map<Integer, Integer> distance = new HashMap<>();

        for (int town : towns) {
            distance.put(town, Integer.MAX_VALUE);
        }
        distance.put(a, tdep);

        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(distance::get));
        queue.add(a);

        while (!queue.isEmpty()) {
            Integer town = queue.poll();
            if (town == b) {
                break;
            }
            List<Road> roadsFromTown = roads.get(town);
            for (Road road : roadsFromTown) {
                int time = distance.get(town);
                time = computeNextDeparuteTime(closures, road, time);
                if (time + road.dur < distance.get(road.t2)) {
                    distance.put(road.t2, time + road.dur);
                    queue.add(road.t2);
                }
            }

        }
        return distance.get(b) == Integer.MAX_VALUE ? 0 : distance.get(b);
    }

    private static int computeNextDeparuteTime(Map<Integer, List<Closure>> closures, Road road, int time) {
        if (closures.containsKey(road.id)) {
            List<Closure> closuresFromRoad = closures.get(road.id);
            Collections.sort(closuresFromRoad);
            for (Closure closure : closuresFromRoad) {
                if (isBeforeClosureStart(time, road, closure)) {
                    break;
                }
                if (isWithinClosure(time, road, closure) || isBeforClosureAndAfterOrBeforClosureEnd(time, road, closure)
                        || isDuringClosure(time, road, closure)) {
                    time = closure.end;
                }
            }
        }
        return time;
    }

    private static boolean isBeforeClosureStart(int time, Road road, Closure closure) {
        return time + road.dur <= closure.start;
    }

    private static boolean isWithinClosure(int time, Road road, Closure closure) {
        return time + road.dur >= closure.start && time + road.dur <= closure.end;
    }

    private static boolean isBeforClosureAndAfterOrBeforClosureEnd(int time, Road road, Closure closure) {
        return time <= closure.start && (time + road.dur >= closure.end || time + road.dur <= closure.end);
    }

    private static boolean isDuringClosure(int time, Road road, Closure closure) {
        return time < closure.end && time + road.dur > closure.end;
    }

    static class Road {
        int id;
        int t1;
        int t2;
        int dur;

        public Road(int id, int t1, int t2, int dur) {
            this.id = id;
            this.t1 = t1;
            this.t2 = t2;
            this.dur = dur;
        }

        @Override
        public String toString() {
            return "Road [ID=" + id + ", From=" + t1 + ", To=" + t2 + ", Duration=" + dur + "]";
        }
    }

    static class Closure implements Comparable<Closure> {
        int road;
        int start;
        int end;

        public Closure(int road, int start, int end) {
            this.road = road;
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "Closure [Start=" + start + ", End=" + end + ", Road=" + road + "]";
        }

        @Override
        public int compareTo(Closure o) {
            return Integer.compare(this.start, o.start);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Closure closure = (Closure) o;
            return road == closure.road && start == closure.start && end == closure.end;
        }

        @Override
        public int hashCode() {
            return Objects.hash(road, start, end);
        }
    }
}
