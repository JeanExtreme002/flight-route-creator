package airac;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

class AiracFile {
    private static boolean isNavigationData(String line) {
        return !(line.startsWith(";") || line.replace(" ", "").length() == 0);
    }

    private static List<String> readFile(FileInputStream file, boolean ignoreComments) {
        Scanner scanner = new Scanner(file);
        List<String> lines = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (!ignoreComments || AiracFile.isNavigationData(line)) {
                lines.add(line.trim());
            }
        }
        return lines;
    }

    public static List<String> getNavigationDataFrom(String filename, boolean ignoreComments) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(new File(filename))) {
            return AiracFile.readFile(fileInputStream, ignoreComments);
        }
    }
}
