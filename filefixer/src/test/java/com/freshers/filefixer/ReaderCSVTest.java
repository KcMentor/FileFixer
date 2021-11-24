package com.freshers.filefixer;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;

import org.assertj.core.api.WithAssertions;

public class ReaderCSVTest implements WithAssertions{
    String repoDir = "./src/test/java/com/freshers/filefixer/testData/fileRepo".replace("/", File.separator);
       
}
