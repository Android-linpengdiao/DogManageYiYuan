package com.dog.manage.yiyuan.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Hospital {

    private Integer id;
    private String hospitalName;
    private String hospitalAddress;
    private String hospitalPhone;
    private Object coordinate;
}
