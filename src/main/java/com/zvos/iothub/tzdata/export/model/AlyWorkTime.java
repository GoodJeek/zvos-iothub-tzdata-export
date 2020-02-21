package com.zvos.iothub.tzdata.export.model;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
@Data
@Table(name = "ALY_WORKTIME_201910")
public class AlyWorkTime {
    @Id
    @Column(name = "CD_VP_UNIQUENO")
    private String cdVpUniqueno;

    @Column(name = "WT_NORMALWORKTIME")
    private BigDecimal wtNormalworktime;

    @Column(name = "WT_NORMALOFFSETWORKTIME")
    private BigDecimal wtNormaloffsetworktime;

    @Column(name = "WT_TOTALWORKTIME")
    private BigDecimal wtTotalworktime;

    @Column(name = "WT_LASTTIME")
    private Date wtLasttime;

    @Column(name = "WT_INSERTTIME")
    private Date wtInserttime;

    @Column(name = "WT_DAY")
    private String wtDay;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "CITY")
    private String city;

    @Column(name = "AREA")
    private String area;

    @Column(name = "FUELLEVEL")
    private BigDecimal fuellevel;

    @Column(name = "WT_THEORYOFFSETWORKTIME")
    private BigDecimal wtTheoryoffsetworktime;

}