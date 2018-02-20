package main;

import java.io.IOException;

public interface DataConnection {
    /**
     * Фуннкция загружаетс показатели из файла
     * @param fileInputName Путь до файла
     * @throws IOException
     */
    void loadDatas(String fileInputName) throws IOException;

    /**
     * Фуннкция выгружает статистику в файл
     * @param fileOutputName Путь до файла
     * @throws IOException
     */
    void saveData(String fileOutputName) throws IOException;
}
