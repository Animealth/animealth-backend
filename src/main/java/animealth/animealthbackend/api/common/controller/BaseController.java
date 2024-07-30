package animealth.animealthbackend.api.common.controller;

import animealth.animealthbackend.api.common.dto.ResponseDTO;
import animealth.animealthbackend.api.common.dto.ResultObject;

public abstract class BaseController {

    protected <T> ResponseDTO<T> ok() { return ok(null, ResultObject.getSuccess()); }

    protected <T> ResponseDTO<T> ok(T data) {
        return ok(data, ResultObject.getSuccess());
    }

    protected <T> ResponseDTO<T> ok(T data, ResultObject result) {
        ResponseDTO<T> obj = ResponseDTO.<T>resultDataBuilder()
                .data(data)
                .result(result)
                .build();

        return obj;
    }

}
