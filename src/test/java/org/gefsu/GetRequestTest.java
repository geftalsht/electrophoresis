package org.gefsu;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class GetRequestTest {

    @Test
    void regexTest() {
        String clientRequest = "GET /sTEaLThisPaGe";
        String testString = "stealthispage";

        Pattern pattern = Pattern.compile("(?<=^GET\\s/)(\\S*)");
        Matcher matcher = pattern.matcher(clientRequest);
        if (matcher.find()) {
            assertEquals(testString, matcher.group(0).toLowerCase());
        }
        else fail();
    }
}
