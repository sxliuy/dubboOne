package com.debug.imooc.dubbo.one.api.service;

import com.debug.imooc.dubbo.one.api.response.BaseResponse;

public interface IDubboItemInfoService {

    BaseResponse listItems();

    BaseResponse listPagesItems(Integer pageNo,
                                Integer pageSize);

    BaseResponse listPageItemsParams(Integer pageNo,
                                     Integer pageSize,
                                     String search);
}
