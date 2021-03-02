package testCFT;

import testCFT.merge.MergeIntAscending;
import testCFT.merge.MergeIntDescending;
import testCFT.merge.MergeStringAscending;
import testCFT.merge.MergeStringDescending;

/*
        * Created by Севериков on 24.02.2021.
        */
public class TestCFT {

    public static void main(String[] args){

        FillArguments arguments = new FillArguments();

        arguments.parsingArguments(args);

        String  sortingType= arguments.getSortingType();
        String inputDataType = arguments.getInputDataType();
        String outputFileName = arguments.getOutputFileName();
        String[] inputFileName = arguments.getInputFileName();

        switch (sortingType+inputDataType) {
            case "-a-s":
                MergeStringAscending msa = new MergeStringAscending(outputFileName,inputFileName);
                msa.mergerFiles();
                    break;
            case "-a-i":
                MergeIntAscending mia = new MergeIntAscending(outputFileName, inputFileName);
                mia.mergerFiles();
                break;
            case "-d-s":
                MergeStringDescending msd = new MergeStringDescending(outputFileName, inputFileName);
                msd.mergerFiles();
                break;
            case "-d-i":
                MergeIntDescending mid = new MergeIntDescending(outputFileName, inputFileName);
                mid.mergerFiles();
                break;
        }

    }
}
