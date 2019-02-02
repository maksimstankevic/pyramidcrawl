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

        System.out.println("Contents of pyramid.txt:\n");
        for (String [] arr: parsedData) System.out.println(Arrays.toString(arr));

        Set<String> nicelyFormattedFullPaths = new HashSet<>();
        Set<String> usedAndDeadPaths = new HashSet<>();

        boolean reachedTheEND = false;

        while(!reachedTheEND) {

            String [] buffer = new String[parsedData.size()];

            for (int i = 0, parent = 0; i < parsedData.size(); i++) {
                if (i == 0) {
                    buffer[i] = (parsedData.get(i))[i];
                } else {

                    String[] children = new String[] {(parsedData.get(i))[parent], (parsedData.get(i))[parent+1]};
                    boolean needOddNow = Integer.parseInt(buffer[i - 1]) % 2 == 0;
                    int oneOrZero = needOddNow ? 1 : 0;
                    String pathSoFar = getPathWithInt(i, buffer);

                    if (Integer.parseInt(children[0])%2 == oneOrZero && !usedAndDeadPaths.contains(pathSoFar)) {
                        buffer[i] = children[0];
                    } else if (Integer.parseInt(children[1])%2 == oneOrZero) {
                        buffer[i] = children[1];
                        parent++;
                    } else {
                        if (!usedAndDeadPaths.add(pathSoFar)) {
                            int repeatCount = 1;
                            while (i - repeatCount > 0 && !usedAndDeadPaths.add(getPathWithInt(i - repeatCount, buffer))) repeatCount++;
                            if (i - repeatCount == 0) reachedTheEND = true;

                        }
                        break;
                    }

                    if (i == parsedData.size() - 1) {

                        String goodPath = getPathWithIntSpaceDelimited(parsedData.size(), buffer);
                        nicelyFormattedFullPaths.add(goodPath.trim());
                        String badPath = getPathWithInt(parsedData.size(), buffer);
                        int repeatCount = 0;
                        while (!usedAndDeadPaths.add(getPathWithInt(i - repeatCount, buffer))) repeatCount++;

                    }
                }
            }
        }

        outputResults(nicelyFormattedFullPaths, parsedData.size());


    }

    private static String getPathWithInt (int depth, String [] stringArray) {
        StringBuilder path = new StringBuilder();
        for (int j = 0; j < depth; j++) {
            path.append(stringArray[j]);
        }
        return path.toString();
    }

    private static String getPathWithIntSpaceDelimited (int depth, String [] stringArray) {
        StringBuilder path = new StringBuilder();
        for (int j = 0; j < depth; j++) {
            path.append(stringArray[j]);
            path.append(" ");
        }
        return path.toString();
    }

    private static void outputResults(Set<String> paths, int nrRows){

        System.out.println("\nNumber of valid paths found: " + paths.size() + "\n");

        int max = 0;
        String winner = "";

        for (String entry: paths){

            int sum = 0;
            String [] array = entry.split(" ");
            System.out.print(Arrays.toString(array) + "\t\t\t");

            for (int k = 0; k < nrRows; k++) {
                sum += Integer.parseInt(array[k]);
                winner = (sum > max) ? Arrays.toString(array) : winner;
                max = (sum > max) ? sum : max;

            }

            System.out.println("Sum of this path: " + sum);

        }

        System.out.println("\n\n\nMaximum is " + max + " and it corresponds to the path: " + winner);

    }
}
