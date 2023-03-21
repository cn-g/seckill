package com.gcp.luatest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class SecKillReqDto {

    public Integer userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public Long goodsId;

    public Integer num;

}
