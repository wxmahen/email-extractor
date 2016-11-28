package com.educluster.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailExtractor {

    FileManager fileManager;
    DbManager dbManager;

    public EmailExtractor() {
        fileManager = new FileManager();
        dbManager = new DbManager();
    }

    public void extractEmails() {
        List<String> files = fileManager.getInputFileNameList();
        files.forEach((file) -> {
            List<String> emails = getEmails(fileManager.readFile(file));
            System.out.println(file);
            dbManager.saveEmails(emails);
            fileManager.deleteFile(file);
        });
    }

    private List<String> getEmails(String text) {
        List<String> emails = new ArrayList<>();
        Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(text);
        while (m.find()) {
            emails.add(m.group());
        }
        return emails;
    }
}
