package com.debug.imooc.dubbo.one.server.service.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.debug.imooc.dubbo.one.api.enums.StatusCode;
import com.debug.imooc.dubbo.one.api.response.BaseResponse;
import com.debug.imooc.dubbo.one.api.service.IDubboItemInfoService;
import com.debug.imooc.dubbo.one.model.entity.ItemInfo;
import com.debug.imooc.dubbo.one.model.mapper.ItemInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Service(protocol = {"dubbo","rest"},validation = "true" ,version = "1.0",timeout = 3000)
@Path("imoocOne")
public class DubboItemService implements IDubboItemInfoService {

    private  static final Logger log = LoggerFactory.getLogger(DubboItemService.class);

    @Autowired
    private ItemInfoMapper itemInfoMapper;

    /**
     * 列表查询服务，实际的业务逻辑实现
     */
    @Override
    @GET
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("item/list")
    public BaseResponse listItems() {
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try{
            List<ItemInfo> infos = itemInfoMapper.selectAll();
            log.info("查询到的商品列表数据：{}",infos);
            baseResponse.setData(infos);
        }catch (Exception e){
            log.error("列表查询服务，实际的业务逻辑实现-发现异常：",e.fillInStackTrace());
            baseResponse = new BaseResponse(StatusCode.Fail);
        }
        return baseResponse;
    }

    /**
     * 列表查询    分页
     */
    @Path("item/page/list")
    public BaseResponse listPagesItems(@QueryParam("pageNo") Integer pageNo,
                                       @QueryParam("pageSize")Integer pageSize){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try{
            PageHelper.startPage(pageNo,pageSize);
            PageInfo<ItemInfo> pageInfo = new PageInfo<>(itemInfoMapper.selectAll());
            baseResponse.setData(pageInfo);
        }catch (Exception e){
            log.error("列表查询服务，实际的业务逻辑实现-发现异常：",e.fillInStackTrace());
            baseResponse = new BaseResponse(StatusCode.Fail);
        }
        return baseResponse;
    }

    /**
     * 列表查询-分页查询-模糊查询
     * @return
     */
    @Path("item/page/list/params")
    public BaseResponse listPageItemsParams(@QueryParam("pageNo") Integer pageNo,
                                            @QueryParam("PageSize") Integer PageSize,
                                            @QueryParam("search") String search) {
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            //TODO:分页组件-第pageNo页，pageSize条数目数据
            PageHelper.startPage(pageNo,PageSize);
            PageInfo info=new PageInfo<ItemInfo>(itemInfoMapper.selectAllByParams(search));
            response.setData(info);

        }catch (Exception e){
            log.error("列表查询-分页查询模糊查询服务-实际的业务实现逻辑-发生异常：",e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail);
        }
        return response;
    }
}
