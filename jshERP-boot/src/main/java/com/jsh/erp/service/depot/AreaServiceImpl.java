package com.jsh.erp.service.depot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.Area;
import com.jsh.erp.datasource.entities.Material;
import com.jsh.erp.datasource.entities.MaterialCurrentStock;
import com.jsh.erp.datasource.mappers.AreaMapper;
import com.jsh.erp.datasource.mappers.NMaterialMapper;
import com.jsh.erp.datasource.page.MaterialPage;
import com.jsh.erp.datasource.page.MaterialWithStockPage;
import com.jsh.erp.exception.ResultEnum;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.material.Interface.INMaterialService;
import com.jsh.erp.service.materialCategory.Interface.IMaterialCategoryService;
import com.jsh.erp.service.materialCurrentStock.Interface.IMaterialCurrentStockService;
import com.jsh.erp.service.unit.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService {

}
