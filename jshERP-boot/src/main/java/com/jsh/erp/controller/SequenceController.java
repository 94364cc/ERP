package com.jsh.erp.controller;

import javax.annotation.Resource;

import com.jsh.erp.service.sequence.ISequenceService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ji-sheng-hua 752*718*920
 */
@RestController
@RequestMapping(value = "/sequence")
@Api(tags = {"单据编号"})
public class SequenceController {
    private Logger logger = LoggerFactory.getLogger(SequenceController.class);

    @Resource
    private ISequenceService sequenceService;

}
