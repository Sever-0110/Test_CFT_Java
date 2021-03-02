package testCFT.merge;

public class MergeStringAscending extends AbstractMergingDataFromFiles {

    public MergeStringAscending(String nameOutputFile, String[] namesInputFiles) {
        super(nameOutputFile, namesInputFiles);
    }

    @Override
    boolean setOptionsOfSort(String element1, String element2) {
        if (element1.compareTo(element2) <= 0) {
            return true;
        } else {
            return false;
        }
    }
}
