package main;

import model.Line;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class DataConnectionImpl implements DataConnection {
    private static final Logger LOG = LoggerFactory.getLogger(DataConnectionImpl.class);
    private static final int START_YEAR = 1990;
    private static final int END_YEAR = 2020;

    private final Map<Integer, List<Integer>> statisticMap = new HashMap<>();

    @Override
    public void loadDatas(String fileInputName) throws IOException {
        try (InputStream is = new FileInputStream(fileInputName)) {
            final Scanner scanner = new Scanner(is);
            while (scanner.hasNext()) {
                final String lineStr = scanner.nextLine();
                LOG.debug(lineStr);
                final Line line = new Line(lineStr);
                if (!statisticMap.containsKey(line.getYear())) {
                    LOG.debug("put");
                    statisticMap.put(line.getYear(), new ArrayList<>());
                }
                statisticMap.get(line.getYear()).add(line.getReults());
            }
        }
    }

    @Override
    public void saveData(String fileOutputName) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileOutputName)) {
            for (int year = START_YEAR; year <= END_YEAR; year++) {
                final int sum;
                if (statisticMap.containsKey(year)) {
                    sum = (int) statisticMap.get(year).stream()
                            .mapToDouble(d -> d)
                            .average()
                            .orElse(0);
                } else  {
                    sum = 0;
                }
                LOG.debug("i={}, sum={}", year, sum);
                final String out = (year - START_YEAR) + " " + year + " " + sum + "\n";
                fos.write(out.getBytes());
            }
        }
    }
}
