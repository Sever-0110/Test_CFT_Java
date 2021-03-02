package testCFT.merge;


import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractMergingDataFromFiles {
    private List<BufferedReader> readersFromInputFiles;
    private List<String> readerLinesFromFiles;
    private FileWriter writer;
    private final String nameOutputFile;
    private final String[] namesInputFiles;


    public AbstractMergingDataFromFiles(String nameOutputFile, String[] namesInputFiles) {
        this.nameOutputFile = nameOutputFile;
        this.namesInputFiles = namesInputFiles;
    }

    public void mergerFiles() {
        try {
            writer = new FileWriter(nameOutputFile, false);
        } catch (IOException e) {
            System.out.println("Не удалось открыть файл для записи");
            closeStreams();
            System.exit(1);
        }
        readersFromInputFiles = openingReaders(namesInputFiles);
        readerLinesFromFiles = readLinesFromFiles(readersFromInputFiles);
        mergeSort(readerLinesFromFiles);

        closeStreams();
    }

    /*
     * Метод открывает для чтения файлы.
     */

    private List<BufferedReader> openingReaders(String[] namesFilesToRead) {
        readersFromInputFiles = new ArrayList<>();
        for (String nameFile : namesFilesToRead) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(nameFile)));
                readersFromInputFiles.add(reader);
            } catch (FileNotFoundException e) {
                System.out.println("Не удалось считать данные из файла: " + nameFile + ".\nСлияние файлов будет произведено без учета этого файла. ");


            }
        }
        return readersFromInputFiles;

    }

    /*
     * Метод считывает из файлов первые строки и вносит их в ArrayList
     */

    private List<String> readLinesFromFiles(List<BufferedReader> readers) {

        readerLinesFromFiles = new ArrayList<>();


        Iterator<BufferedReader> readersIterator = readers.iterator();
        while (readersIterator.hasNext()) {
            try {
                String string = readersIterator.next().readLine();

                if (string != null) {
                    readerLinesFromFiles.add(string);
                } else {
                    readersIterator.remove();
                }
            } catch (IOException e) {
                readersIterator.remove();
            }
        }
        if (readerLinesFromFiles.isEmpty()) {
            System.out.println("Ни с одного файла не удалось считать данные. Проверьте верно ли указаны пути к файлам");
            closeStreams();
            System.exit(1);
        }
        return readerLinesFromFiles;

    }

    /*
     * Реализация слияния данных из фйлов
     */
    private void mergeSort(List<String> listStringsFromInputFiles) {

        while (listStringsFromInputFiles.size() != 1) {
            int minIdx = 0;
            for (int i = 1; i < listStringsFromInputFiles.size(); i++) {
                if (setOptionsOfSort(listStringsFromInputFiles.get(i), listStringsFromInputFiles.get(minIdx))) {
                    minIdx = i;
                }
            }
            writeLineToFile(listStringsFromInputFiles.get(minIdx));
            setNewValueFromFileByIdxScanner(minIdx);
        }
        writeLineToFile(listStringsFromInputFiles.get(0));
        writeAllValuesToFile();
    }

    /*
     * Метод реализует способ сравнения элементов между собой в файлах
     */

    abstract boolean setOptionsOfSort(String element1, String element2);


    /*
     * Метод добавляет новое значение из файла
     * в буфер
     *
     * @param idxFile - указывает из какого файла добавлять
     */
    private void setNewValueFromFileByIdxScanner(int idxFile) {
        String string = null;
        try {
            BufferedReader reader = readersFromInputFiles.get(idxFile);
            string = reader.readLine();
        } catch (IOException e) {
            System.out.println("Не удалось считать значение из файла. Слияние не завершено до конца.");
            closeStreams();
            System.exit(1);

        }

        if (string != null) {
            if (setOptionsOfSort(readerLinesFromFiles.get(idxFile), string)) {
                readerLinesFromFiles.set(idxFile, string);
            } else {
                System.out.println("В входном файле нарушен порядок сортировки. Отсортируйте файлы, и попробуйте заново");
                closeStreams();
                System.exit(1);
            }
        } else {
            readerLinesFromFiles.remove(idxFile);
            try {
                readersFromInputFiles.get(idxFile).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            readersFromInputFiles.remove(idxFile);
        }
    }

    /*
     * Метод добавляет все данные из файла,
     * если файл остался один.
     */
    private void writeAllValuesToFile() {
        try {
            BufferedReader reader = readersFromInputFiles.get(0);
            String string = reader.readLine();
            while (string != null) {
                if (setOptionsOfSort(readerLinesFromFiles.get(0), string)) {
                    writeLineToFile(string);
                    string = reader.readLine();
                } else {
                    System.out.println("В входном файле нарушен порядок сортировки. Отсортируйте файлы, и попробуйте заново");
                    closeStreams();
                    System.exit(1);
                }
            }
        } catch (IOException e) {
            System.out.println("Не удалось считать значение из файла.");
            closeStreams();
            System.exit(1);
        }


    }

    /*
     * Метод записывает элементы из буфера
     * в исходный файл
     *
     * @param lineToWrite - указывает какой именно элемент записать
     */
    private void writeLineToFile(String lineToWrite) {
        try {
            writer.write(lineToWrite);
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Не удалось записать значения в файл");
            closeStreams();
            System.exit(1);
        }
    }

    /*
     * Метод закрывает открытые потоки.
     */

    void closeStreams() {
        try {
            writer.close();
            for (BufferedReader reader : readersFromInputFiles) {
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

