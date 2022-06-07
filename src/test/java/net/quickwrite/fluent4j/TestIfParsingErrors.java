package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.parser.FluentParser;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TestIfParsingErrors {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {}

    @AfterClass
    public static void tearDown() {}

    public static List<String> data() {
        ClassLoader classLoader = TestIfParsingErrors.class.getClassLoader();
        File dir = new File(Objects.requireNonNull(classLoader.getResource("input")).getFile());
        List<String> filenames = new ArrayList<>();
        for (File res : Objects.requireNonNull(dir.listFiles())) {
            filenames.add(res.getName());
        }

        Collections.sort(filenames);
        return filenames;
    }

    @ParameterizedTest
    @MethodSource("data")
    public void testResults(String filename) throws IOException {
        ClassLoader classLoader = TestIfParsingErrors.class.getClassLoader();
        File result = new File(classLoader.getResource("input").getFile(), filename);

        String inputStr = getFile(result.getAbsolutePath());
        FluentParser parser = new FluentParser(inputStr);
        FluentResource output = parser.parse();

        Assertions.assertNotEquals(null, output);
    }

    private static String getFile(String path) throws IOException {
        Path fileName = Path.of(path);

        return Files.readString(fileName);
    }

}
