package com.educluster.ctrl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class EmailLister {

    private final DbManager dbManager;
    List<String> emails;
    String[] exts = {".com", ".net", ".org", ".co.uk"};

    public EmailLister() {
        dbManager = new DbManager();
        emails = dbManager.getEmails();
    }

    public void printEmails() {
        emails.forEach((email) -> {
            System.out.println(email);
        });
    }

    public void printValidEmails() {
        List<String> list_ = filter(emails);
        Map<String, String> map = new HashMap<>();
        list_.forEach((email) -> {
            map.put(email, email.split("@")[1]);
        });
        Set<Entry<String, String>> set = map.entrySet();
        List<Entry<String, String>> list = new ArrayList<>(
                set);
        Collections.sort(list, (Map.Entry<String, String> o1, Map.Entry<String, String> o2) -> o2.getValue().compareTo(o1.getValue()));
        list.forEach((entry) -> {
            System.out.println(entry.getKey());
        });
    }

    private List<String> filter(List<String> emails) {
        List<String> filtered = new ArrayList<>();
        emails.forEach((email) -> {
            boolean is_valid = false;
            for (String ext : exts) {
                if (email.endsWith(ext)) {
                    is_valid = true;
                }
            }
            if (is_valid) {
                filtered.add(email);
            }
        });
        return filtered;
    }

    public void printDomains() {
        Map<String, Integer> domains = new HashMap<>();
        emails.stream().map((email) -> email.split("@")[1]).forEachOrdered((domain) -> {
            if (!domains.containsKey(domain)) {
                domains.put(domain, 1);
            } else {
                int val = domains.get(domain) + 1;
                domains.put(domain, val);
            }
        });
        Set<Entry<String, Integer>> set = domains.entrySet();
        List<Entry<String, Integer>> list = new ArrayList<>(
                set);
        Collections.sort(list, (Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) -> o2.getValue().compareTo(o1.getValue()));
        list.forEach((entry) -> {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        });
    }
}
