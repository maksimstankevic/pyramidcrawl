package local.pyramid;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class Main {

    public static void main(String[] args) {

        Path path = Paths.get("pyramid.txt");
        List<String[]> parsedData = new ArrayList<>();

        try {
            for (String text : Files.readAllLines(path)) parsedData.add(text.split(" "));
        } catch (IOException e) {
            System.out.println("Could not read " + path.toString());
        }

        for (String [] arr: parsedData) System.out.println(Arrays.toString(arr));

        System.out.println(parsedData.size());

        List<String> fullPaths = new ArrayList<>();
        List<String> oldPaths = new ArrayList<>();
        int counter = 0;

        String [] temp = new String[parsedData.size()];
        Map<String,Integer> badCounts = new HashMap<>();

        while(counter < 331) {



            for (int i = 0, parent = 0; i < parsedData.size(); i++) {
                if (i == 0) {
                    temp[i] = (parsedData.get(i))[i];
                } else {
                    String[] children = new String[] {(parsedData.get(i))[parent], (parsedData.get(i))[parent+1]};
                    boolean needOddNow = Integer.parseInt(temp[i - 1]) % 2 == 0;
                    if (needOddNow == true) {
                        StringBuilder pathSoFar = new StringBuilder();
                        for (int j = 0; j < i; j++){
                            pathSoFar.append(temp[j].toString());
                        }

                        if (Integer.parseInt(children[0])%2 == 1 && !oldPaths.contains(pathSoFar.toString())) {
                            temp[i] = children[0];
                        } else if (Integer.parseInt(children[1])%2 == 1) {
                            temp[i] = children[1];
                            parent++;
                        } else {
                            if (!oldPaths.contains(pathSoFar.toString())){
                                oldPaths.add(pathSoFar.toString());
                            } else {
                                StringBuilder trimmedPath = new StringBuilder();
                                for (int j = 0; j < i - 1; j++){
                                    trimmedPath.append(temp[j].toString());
                                }
                                oldPaths.set(oldPaths.indexOf(pathSoFar.toString()), trimmedPath.toString());

                            }

                            break;
                        }
                    } else {
                        StringBuilder pathSoFar = new StringBuilder();
                        for (int j = 0; j < i; j++){
                            pathSoFar.append(temp[j]);
                        }
                        if (Integer.parseInt(children[0])%2 == 0 && !oldPaths.contains(pathSoFar.toString())) {
                            temp[i] = children[0];
                        } else if (Integer.parseInt(children[1])%2 == 0) {
                            temp[i] = children[1];
                            parent++;
                        } else {
                            if (!oldPaths.contains(pathSoFar.toString())){
                                oldPaths.add(pathSoFar.toString());
                            } else {
                                StringBuilder trimmedPath = new StringBuilder();
                                for (int j = 0; j < i - 1; j++){
                                    trimmedPath.append(temp[j]);
                                }
                                oldPaths.set(oldPaths.indexOf(pathSoFar.toString()), trimmedPath.toString());
                            }
                            break;
                        }
                    }
                }
            }



            StringBuilder goodPath = new StringBuilder();
            for (int j = 0; j < parsedData.size(); j++) {
                goodPath.append(temp[j] + " ");
            }


            if (temp[parsedData.size() - 1] != null) {
                if (!fullPaths.contains(goodPath.toString().trim())) {

                    fullPaths.add(goodPath.toString().trim());

                    StringBuilder badPath = new StringBuilder();
                    for (int j = 0; j < parsedData.size(); j++) {
                        badPath.append(temp[j]);
                    }

                    badCounts.put(badPath.toString(), 1);

                    badPath.setLength(0);
                    for (int j = 0; j < parsedData.size(); j++) {
                        badPath.append(temp[j]);
                    }
                    oldPaths.add(badPath.toString());

                } else {
                    StringBuilder badPath = new StringBuilder();
                    for (int j = 0; j < parsedData.size(); j++) {
                        badPath.append(temp[j].toString());

                    }


                    StringBuilder veryBadPath = new StringBuilder();
                    for (int j = 0; j < parsedData.size() - badCounts.get(badPath.toString()); j++) {
                        veryBadPath.append(temp[j].toString());
                    }

                    badCounts.put(badPath.toString(), badCounts.get(badPath.toString()) + 1);
                    oldPaths.add(veryBadPath.toString());

                }

            }


            counter++;
        }
        System.out.println(fullPaths.size());
        long max = 0;
        for (String entry: fullPaths){
            String [] array = entry.split(" ");
            System.out.print(Arrays.toString(array) + "\t\t\t");
            long sum = 0;
            for (int k = 0; k < parsedData.size(); k++) {
                sum += Long.parseLong(array[k]);
                max = (sum > max) ? sum : max;
            }
            System.out.println(sum);
        }
        System.out.println("Maximum is " + max);
        


    }
}
