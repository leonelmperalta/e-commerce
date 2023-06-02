package com.lperalta.ecommerce.infraestructure.in.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class QueryPurchaseDTO {

    private Long dni;
    private String dateFrom;
    private String dateTo;

    public QueryPurchaseDTO(Long dni, String dateFrom, String dateTo) {
        this.dni = dni;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
}
