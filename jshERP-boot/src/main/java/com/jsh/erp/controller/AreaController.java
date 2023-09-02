package com.jsh.erp.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.jsh.erp.datasource.entities.Account;
import com.jsh.erp.datasource.vo.AccountVo4InOutList;
import com.jsh.erp.service.account.AccountService;
import com.jsh.erp.service.depot.IAreaService;
import com.jsh.erp.service.systemConfig.SystemConfigService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.ErpInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

/**
 * @author jishenghua 75271*8920
 */
@RestController
@RequestMapping(value = "/area")
@Api(tags = {"区域管理"})
public class AreaController {
    private Logger logger = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    IAreaService areaService;

    /**
     * 获取仓库列表
     * @param
     * @return
     */
    @GetMapping(value = "/list")
    @ApiOperation(value = "获取仓库列表")
    public BaseResponseInfo getList() throws Exception {
        return BaseResponseInfo.data(areaService.list());
    }

}
