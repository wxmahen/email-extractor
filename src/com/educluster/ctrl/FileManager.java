package com.educluster.ctrl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileManager {

    public static String INPUT_FOLDER = "input";

    private String[] getInputFileList() {
        return getFileList(INPUT_FOLDER);
    }

    public List<String> getInputFileNameList() {
        String[] files = getInputFileList();
        List<String> file_list = new ArrayList<>();
        file_list.addAll(Arrays.asList(files));
        return file_list;
    }

    private String[] getFileList(String folder) {
        return new File(folder).list();
    }

    public String readFile(String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FOLDER + "\\" + file))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void deleteFile(String file) {
        try {
            File f = new File((INPUT_FOLDER + "\\" + file));
            f.delete();
        } catch (Exception e) {
        }
    }
}
