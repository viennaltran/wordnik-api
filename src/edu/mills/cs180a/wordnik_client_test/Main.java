package edu.mills.cs180a.wordnik_client_test;

import java.io.*;
import java.nio.charset.*;
import java.util.*;
import edu.mills.cs180a.wordnik.client.api.*;
import edu.mills.cs180a.wordnik.client.invoker.*;
import edu.mills.cs180a.wordnik.client.model.*;

public class Main {
    private static String getApiKey() throws IOException {
        return getResource("api-key.txt");
    }

    private static String getResource(String filename) throws IOException {
        try (InputStream is = Main.class.getResourceAsStream(filename)) {
            if (is == null) {
                throw new IOException("Unable to open file " + filename);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8).trim();
        }
    }

    public static void main(String[] args) throws IOException {
        ApiClient client = new ApiClient("api_key", getApiKey());
        WordsApi wordsApi = client.buildClient(WordsApi.class);
        WordOfTheDay word = wordsApi.getWordOfTheDay("2022-03-15");
        System.out.println(word);


        WordApi wordApi = client.buildClient(WordApi.class);
        WordObject randomWord =
                wordsApi.getRandomWord("true", "verb", "noun", 1000, -1, 100, -1, 5, -1);
        List<String> etymology = wordApi.getEtymologies(randomWord.getWord(), "false");
        System.out.print(randomWord);
        System.out.print(etymology);
    }
}
