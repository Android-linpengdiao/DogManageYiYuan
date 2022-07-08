package com.dog.manage.yiyuan.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ImmuneApproveDetail {

    private Integer dogId = 0;
    private Integer immuneId;
    private String dogType;
    private String createTime;
    private Integer dogAge;
    private Integer lincenceStatus;
    private Integer userId;
    private String userName;
    private Integer hospitalId;
    private String hospitalName;
    private String hospitalAddress;
    private String hospitalPhone;
    private Object longitudeLatitude;
}
