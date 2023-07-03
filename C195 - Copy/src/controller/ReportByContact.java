package controller;

import helper.appointmentHelper;

/**
 * implementation of the generateReport method specific
 * to the ReportByContact class
 */
public class ReportByContact extends reports {
    // properties and methods specific to report by contact
    @Override
    public String generateReport() {
        return appointmentHelper.getReportContact();
    }
}
