package com.jsh.erp.service.document;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsh.erp.datasource.entities.DocumentItem;
import com.jsh.erp.datasource.mappers.DocumentItemMapper;
import com.jsh.erp.service.document.Interface.IDocumentItemService;
import org.springframework.stereotype.Service;

@Service
public class DocumentItemServiceImpl extends ServiceImpl<DocumentItemMapper, DocumentItem> implements IDocumentItemService {



}
