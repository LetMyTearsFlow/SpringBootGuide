package org.cqlin.pointcutexpressiondemo.service;

import org.cqlin.pointcutexpressiondemo.dto.SecretDTO;
import org.springframework.stereotype.Service;

@Service("extraService")
public class ExtraService {
    public void process(SecretDTO dto) {}
}
