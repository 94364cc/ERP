package com.jsh.erp.service.document;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.DocumentHead;
import com.jsh.erp.datasource.entities.Material;
import com.jsh.erp.datasource.mappers.DocumentHeadMapper;
import com.jsh.erp.datasource.mappers.NMaterialMapper;
import com.jsh.erp.datasource.page.MaterialPage;
import com.jsh.erp.exception.ResultEnum;
import com.jsh.erp.service.document.Interface.IDocumentHeadService;
import com.jsh.erp.service.document.Interface.IDocumentItemService;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.material.Interface.INMaterialService;
import com.jsh.erp.service.materialCategory.Interface.IMaterialCategoryService;
import com.jsh.erp.service.unit.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocumentHeadServiceImpl extends ServiceImpl<DocumentHeadMapper, DocumentHead> implements IDocumentHeadService {



}
