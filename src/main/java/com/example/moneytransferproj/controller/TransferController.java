package com.example.moneytransferproj.controller;

import com.example.moneytransferproj.data_transfer_objects.ConfirmOperation;
import com.example.moneytransferproj.data_transfer_objects.TransferData;
import com.example.moneytransferproj.entitys.Transaction;
import com.example.moneytransferproj.service.TransferService;
import com.example.moneytransferproj.service.ValidationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TransferController {

    private final TransferService transferService;
    private final ValidationService validationService;
    private Transaction transactionBuffer;

    public TransferController(TransferService transferService, ValidationService validationService) {
        this.transferService = transferService;
        this.validationService = validationService;
    }


    @PostMapping("transfer")
    public String transfer(@RequestBody TransferData transferData) {
        transactionBuffer = transferService.transfer(transferData);
        return transactionBuffer.getOperationID();
    }

    @PostMapping("confirmOperation")
    public String confirmOperation(@RequestBody ConfirmOperation confirmOperation) {
        return validationService.confirm(confirmOperation, transactionBuffer);
    }

}
