package com.example.moneytransferproj.controller;

import com.example.moneytransferproj.dataclasses.ConfirmOperation;
import com.example.moneytransferproj.dataclasses.TransferData;
import com.example.moneytransferproj.service.TransferService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("transfer")
    public String transfer(@RequestBody TransferData transferData) {
        return transferService.transfer(transferData);
    }

    @PostMapping("confirmOperation")
    public String confirmOperation(@RequestBody ConfirmOperation confirmOperation) {
        return transferService.confirm(confirmOperation);
    }
}
