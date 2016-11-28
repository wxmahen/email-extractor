package com.educluster.run;

import com.educluster.ctrl.EmailExtractor;
import com.educluster.ctrl.EmailLister;

public class Runner {

    public static void main(String[] args) {
        new EmailExtractor().extractEmails();
        new EmailLister().printValidEmails();
    }
}
