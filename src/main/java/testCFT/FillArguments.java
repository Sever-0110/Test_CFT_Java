package testCFT;

public class FillArguments {

    private String inputDataType;
    private String sortingType;
    private String outputFileName;
    private String[] inputFileName;


    public void parsingArguments (String[] args) {

        int argumentsNumber = 0;

        errorChecking(argumentsNumber, args.length);
        if (args[0].equals("-a") || args[0].equals("-d")) {
            sortingType = args[0];
            argumentsNumber++;
        }
        if (args[argumentsNumber].equals("-i") || args[argumentsNumber].equals("-s")) {
            inputDataType = args[argumentsNumber];
            argumentsNumber++;
        } else {
            errorChecking(argumentsNumber, argumentsNumber);

        }
        errorChecking(argumentsNumber, args.length);

        outputFileName = args[argumentsNumber];
        argumentsNumber++;

        errorChecking(argumentsNumber, args.length);
        inputFileName = new String[args.length - argumentsNumber];
        for (int j = argumentsNumber, q = 0; j < args.length; j++, q++) {
            inputFileName[q] = args[j];
        }

    }

    public String getInputDataType() {
        return inputDataType;
    }

    public String getSortingType() {
        return sortingType;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public String[] getInputFileName() {
        return inputFileName;
    }

    private void errorChecking(int i, int b) {
        String ERROR_MESSAGE = "Не верно введены аргументы коммандной стоки. Повторите попытку заново";
        if (i == b) {
            System.out.println(ERROR_MESSAGE);
            System.exit(1);
        }
    }



}
