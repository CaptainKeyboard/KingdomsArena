package net.velion.kingdoms_arena;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;

public class TestTest
{
    @Test
    public void test1() throws URISyntaxException
    {
        File folder = new File(Kingdoms_arena.class.getClassLoader().getResource("templates").toURI());

        File[] files = folder.listFiles();
        
        files.getClass();
    }
}
