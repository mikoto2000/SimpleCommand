package jp.dip.oyasirazu.simplecommand;

import static org.junit.Assert.*;
import jp.dip.oyasirazu.simplecommand.command.Command;
import jp.dip.oyasirazu.simplecommand.command.CommandException;

import org.junit.Test;

public class CommandTest {

    @Test
    public void testCommandLs() throws CommandException {
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
