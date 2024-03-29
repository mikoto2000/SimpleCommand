package jp.dip.oyasirazu.simplecommand.command;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommandTest {

    @Test
    public void testDefaultConstructorLs() throws CommandException {
        Command ls = new Command();
        ls.setCommand("ls");
        ls.setArgs("testDirectory1");
        String result = ls.execute();
        assertEquals(result, "file1\nfile2\n");

        ls.setArgs("testDirectory2");
        result = ls.execute();
        assertEquals(result, "test1\ntest2\n");
    }

    @Test
    public void testStringConstructorLs() throws CommandException {
        Command ls = new Command("ls");
        ls.setArgs("testDirectory1");
        String result = ls.execute();
        assertEquals(result, "file1\nfile2\n");

        ls.setArgs("testDirectory2");
        result = ls.execute();
        assertEquals(result, "test1\ntest2\n");
    }

    @Test
    public void testCommandCat() throws CommandException {
        String input = "aaa\nbbb\nccc";

        Command ls = new Command("cat");
        String result = ls.execute("aaa\nbbb\nccc");
        assertEquals(result, input + "\n");
    }
}
