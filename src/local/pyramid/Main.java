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

        List<String> fullPaths = new ArrayList<>();
        List<String> oldPaths = new ArrayList<>();
        int counter = 0;

        String [] temp = new String[parsedData.size()];
        Map<String,Integer> badCounts = new HashMap<>();

//        while(counter < 331) {
        while(counter < 10) {


            for (int i = 0, parent = 0; i < parsedData.size(); i++) {
                if (i == 0) {
                    temp[i] = (parsedData.get(i))[i];
                } else {
                    String[] children = new String[] {(parsedData.get(i))[parent], (parsedData.get(i))[parent+1]};
                    boolean needOddNow = Integer.parseInt(temp[i - 1]) % 2 == 0;
                    int oneOrZero = needOddNow ? 1 : 0;


                    String pathSoFar = getPathWithInt(i, temp);

                    if (Integer.parseInt(children[0])%2 == oneOrZero && !oldPaths.contains(pathSoFar)) {
                        temp[i] = children[0];
                    } else if (Integer.parseInt(children[1])%2 == oneOrZero) {
                        temp[i] = children[1];
                        parent++;
                    } else {
                        if (!oldPaths.contains(pathSoFar)){
                            oldPaths.add(pathSoFar);
                        } else {

                            String trimmedPath = getPathWithInt(i - 1, temp);
                            oldPaths.set(oldPaths.indexOf(pathSoFar), trimmedPath);

                        }

                        break;
                    }

                }
            }




            String goodPath = getPathWithIntSpaceDelimited(parsedData.size(), temp);


            if (temp[parsedData.size() - 1] != null) {
                if (!fullPaths.contains(goodPath.trim())) {

                    fullPaths.add(goodPath.trim());
                    String badPath = getPathWithInt(parsedData.size(), temp);
                    badCounts.put(badPath, 1);
                    oldPaths.add(badPath);

                } else {

                    String usedFullPath = getPathWithInt(parsedData.size(), temp);
                    String veryBadPath = getPathWithInt(parsedData.size() - badCounts.get(usedFullPath), temp);
                    badCounts.put(usedFullPath, badCounts.get(usedFullPath) + 1);
                    oldPaths.add(veryBadPath);

                }

            }


            System.out.println("INSIDE WHILE, counter - " + counter);
            System.out.println("VARIABLE fullPaths " + fullPaths);
            System.out.println("VARIABLE oldPaths " + oldPaths);
            System.out.println("VARIABLE badCounts" + badCounts);



            counter++;
        }



        System.out.println(fullPaths.size());
        long max = 0;
        String winner = "";
        for (String entry: fullPaths){
            String [] array = entry.split(" ");
            System.out.print(Arrays.toString(array) + "\t\t\t");
            long sum = 0;
            for (int k = 0; k < parsedData.size(); k++) {
                sum += Long.parseLong(array[k]);
                winner = (sum > max) ? Arrays.toString(array) : winner;
                max = (sum > max) ? sum : max;

            }
            System.out.println("Sum of this path: " + sum);

        }
        System.out.println("\n\n\nMaximum is " + max + " and it corresponds to the path: " + winner);



    }

    public static String getPathWithInt (int depth, String [] stringArray) {
        StringBuilder path = new StringBuilder();
        for (int j = 0; j < depth; j++) {
            path.append(stringArray[j]);
        }
        return path.toString();
    }

    public static String getPathWithIntSpaceDelimited (int depth, String [] stringArray) {
        StringBuilder path = new StringBuilder();
        for (int j = 0; j < depth; j++) {
            path.append(stringArray[j] + " ");
        }
        return path.toString();
    }
}
