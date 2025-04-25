package hexlet.code;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(mixinStandardHelpOptions = true, name = "gendiff", version = "gendiff - 1.0.0", description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Integer> {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        // Ваш код здесь
        System.out.println("Hello and welcome!");
        return 0;
    }
}
