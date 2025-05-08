package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.Map;
import java.util.concurrent.Callable;

import static hexlet.code.DataParser.parseData;
import static hexlet.code.Differ.generateDiffObject;
import static hexlet.code.formatters.Stylish.stylishFormat;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Integer> {

    @Option(names = {"-f", "--format"}, description = "output format. Default: stylish")
    private String format = "stylish";

    @Parameters(index = "0", description = "path to first file")
    private String filepath1;

    @Parameters(index = "1", description = "path to second file")
    private String filepath2;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).setUsageHelpAutoWidth(true).execute(args);
        System.exit(exitCode);
    }
    public static Map getContent(String path) throws Exception {
        return parseData(path);
    }
    @Override
    public Integer call() throws Exception {
        var content1 = getContent(filepath1);
        var content2 = getContent(filepath2);
        var differObject = generateDiffObject(content1, content2);
        System.out.println(differObject);
        System.out.println(stylishFormat(differObject));
        return 0;
    }



}
