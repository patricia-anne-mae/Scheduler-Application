package controller;

import helper.appointmentHelper;


public class ReportByLocation extends reports{

    @Override
    public String generateReport() {
        return appointmentHelper.reportAppointmentTypeLocation();
    }
}
