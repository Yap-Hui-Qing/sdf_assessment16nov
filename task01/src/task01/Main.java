package task01;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        // check if filename is provided
        if (args.length <= 0) {
            System.err.println("Missing csv file");
            System.exit(1);
        }

        int numOfRecords = 0;

        // map to store the app and category
        Map<String, List<App>> googleApps = new HashMap<>();
        List<App> allApps = new LinkedList<>();

        // read the csv file
        FileReader reader = new FileReader("task01/" + args[0]);
        BufferedReader br = new BufferedReader(reader);

        String header = br.readLine();

        String line;
        while ((line = br.readLine()) != null) {
            numOfRecords += 1;
            line = line.trim();
            String[] records = line.split(",");

            App app = new App(records[0], records[1].toUpperCase(), Double.parseDouble(records[2]));
            allApps.add(app);
        }

        // group by category
        googleApps = allApps.stream()
                .collect(Collectors.groupingBy(App::getAppCat));
        // for (Map.Entry<String, List<App>> entry : googleApps.entrySet()){
        // System.out.println(entry);

        // process the file
        for (Map.Entry<String, List<App>> entry : googleApps.entrySet()) {
            String category = entry.getKey();
            List<App> appList = entry.getValue();

            // initialise discarded and count for every category
            int discarded = 0;
            int count = 0;

            // process the records
            discarded += appList.stream().filter(app -> Double.isNaN(app.getRating())).count(); // Count discarded items
            appList.removeIf(app -> Double.isNaN(app.getRating())); // Remove elements with NaN ratings
            count = count + appList.size() + discarded;

            // App highest = appList.stream()
            // .max(Comparator.comparingDouble(App::getRating))
            // .orElseThrow(() -> new NoSuchElementException("No apps in the list"));

            // App lowest = appList.stream()
            // .min(Comparator.comparingDouble(App::getRating))
            // .orElseThrow(() -> new NoSuchElementException("No apps in the list"));

            // // average rating
            // double average = appList.stream()
            // .collect(Collectors.averagingDouble(App::getRating));

            // System.out.printf("Category: %s\n", category);
            // System.out.printf("\t Highest: %s, %1f\n", highest.getAppName(),
            // highest.getRating());
            // System.out.printf("\t Lowest: %s, %.1f\n", lowest.getAppName(),
            // lowest.getRating());
            // System.out.printf("\t Average: %1f\n", average);
            // System.out.printf("\t Count: %d\n", count);
            // System.out.printf("\t Discarded: %d\n", discarded);

            System.out.printf("Category: %s\n", category);
            try {
                // find the highest rated app
                App highest = Collections.max(appList, Comparator.comparingDouble(App::getRating));

                // find the lowest rated app
                App lowest = Collections.min(appList, Comparator.comparingDouble(App::getRating));
                
                System.out.printf("\t Highest: %s, %.1f\n", highest.getAppName(),
                        highest.getRating());
                System.out.printf("\t Lowest: %s, %.1f\n", lowest.getAppName(),
                        lowest.getRating());
                
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }

            // average rating
            double average = appList.stream()
                        .collect(Collectors.averagingDouble(App::getRating));
            System.out.printf("\t Average: %.3f\n", average);
            System.out.printf("\t Count: %d\n", count);
            System.out.printf("\t Discarded: %d\n", discarded);
        }

        System.out.printf("Total lines in file: %d", numOfRecords);
    }
}
