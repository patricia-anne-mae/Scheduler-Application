package controller;

import helper.appointmentHelper;

/**
 * implementation of the generateReport method specific
 * to the ReportByMonthType class
 */
public class ReportByMonthType extends reports {
    // properties and methods specific to report by month type
    @Override
    public String generateReport() {
        return appointmentHelper.getReportTypeMonth();
    }
}
