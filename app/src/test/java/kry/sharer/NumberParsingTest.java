package kry.sharer;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NumberParsingTest {

    @Test
    public void testNumberParsing() {
        String s = "Mickiewicza 555 testowy adres 123-123-123  asdf asdf 111 111 111 asdfasd fasd 777888999";
        Set<String> result = new MainActivity().parseNumbersFromText(s);
        assertEquals(3, result.size());
        assertTrue(result.contains("123-123-123"));
        assertTrue(result.contains("111-111-111"));
        assertTrue(result.contains("777-888-999"));
    }
}