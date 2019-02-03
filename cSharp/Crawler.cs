using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace pyramidcrawl
{
    class Crawler
    {
        static void Main(string[] args)
        {

            List<string[]> parsedData = new List<string[]>();
            string[] temp = File.ReadAllLines("pyramid.txt");

            foreach (String text in temp)
            {
                parsedData.Add(text.Split(null));
            }

            Console.WriteLine("Contents of pyramid.txt:\n");

            foreach (string [] r in parsedData)
            {
                Console.WriteLine(string.Join(" ", r));
            }

            HashSet<string> nicelyFormattedFullPaths = new HashSet<string>();
            HashSet<string> usedAndDeadPaths = new HashSet<string>();
            bool reachedTheEND = false;
         
            while (!reachedTheEND)
            {
                    
                string[] buffer = new string[parsedData.Count];

                for (int i = 0, parent = 0; i < parsedData.Count; i++)
                {
                    if (i == 0)
                    {
                        buffer[i] = (parsedData[i])[i];
                    }
                    else
                    {

                        string[] children = new string[] { parsedData[i][parent], parsedData[i][parent + 1] };
                        bool needOddNow = int.Parse(buffer[i - 1]) % 2 == 0;
                        int oneOrZero = needOddNow ? 1 : 0;
                        string pathSoFar = getPathWithInt(i, buffer);

                        if (int.Parse(children[0]) % 2 == oneOrZero && !usedAndDeadPaths.Contains(pathSoFar))
                        {
                            buffer[i] = children[0];
                        }
                        else if (int.Parse(children[1]) % 2 == oneOrZero)
                        {
                            buffer[i] = children[1];
                            parent++;
                        }
                        else
                        {
                            if (!usedAndDeadPaths.Add(pathSoFar))
                            {
                                int repeatCount = 1;
                                while (i - repeatCount > 0 && !usedAndDeadPaths.Add(getPathWithInt(i - repeatCount, buffer))) repeatCount++;
                                if (i - repeatCount == 0) reachedTheEND = true;

                            }
                            break;
                        }

                        if (i == parsedData.Count - 1)
                        {

                            string goodPath = getPathWithIntSpaceDelimited(parsedData.Count, buffer);
                            nicelyFormattedFullPaths.Add(goodPath.Trim());
                            string badPath = getPathWithInt(parsedData.Count, buffer);
                            int repeatCount = 0;
                            while (!usedAndDeadPaths.Add(getPathWithInt(i - repeatCount, buffer))) repeatCount++;

                        }

                    }
                }



            }

            outputResults(nicelyFormattedFullPaths, parsedData.Count);


            Console.WriteLine("Press any key to exit.");
            Console.ReadKey();
        }

        private static string getPathWithInt(int depth, string[] stringArray)
        {
            StringBuilder path = new StringBuilder();
            for (int j = 0; j < depth; j++)
            {
                path.Append(stringArray[j]);
            }
            return path.ToString();
        }

        private static string getPathWithIntSpaceDelimited(int depth, string[] stringArray)
        {
            StringBuilder path = new StringBuilder();
            for (int j = 0; j < depth; j++)
            {
                path.Append(stringArray[j]);
                path.Append(" ");
            }
            return path.ToString();
        }

        private static void outputResults(HashSet<string> paths, int nrRows)
        {
            List<string> convertedFromSet = paths.ToList<string>();

            Console.WriteLine("\nNumber of valid paths found: " + convertedFromSet.Count + "\n");

            int max = 0;
            string winner = "";

            foreach (string entry in convertedFromSet)
            {

                int sum = 0;
                string[] array = entry.Split(null);
                Console.Write(string.Join(" ", array) + "\t\t\t");

                for (int k = 0; k < nrRows; k++)
                {
                    sum += int.Parse(array[k]);
                    winner = (sum > max) ? string.Join(" ", array) : winner;
                    max = (sum > max) ? sum : max;

                }

                Console.WriteLine("Sum of this path: " + sum);

            }

            Console.WriteLine("\n\n\nMaximum is " + max + " and it corresponds to the path: " + winner);

        }
    }
}
