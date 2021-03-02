package testCFT.merge;

public class MergeIntDescending extends AbstractMergingDataFromFiles {
    public MergeIntDescending(String nameOutputFile, String[] namesInputFiles) {
        super(nameOutputFile, namesInputFiles);
    }

    @Override
    boolean setOptionsOfSort(String element1, String element2) {
        boolean compare = false;
        try {
            compare = Integer.parseInt(element1) >= Integer.parseInt(element2);
        } catch (NumberFormatException e) {
            System.out.println("Не верно выбран тип данных. В исходных файлах присутствуют не только натуральные числа.");
            closeStreams();
            System.exit(1);
        }
        return compare;
    }
}
