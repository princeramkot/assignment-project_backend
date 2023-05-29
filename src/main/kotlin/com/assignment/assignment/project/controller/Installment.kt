package com.assignment.assignment.project.controller

import com.assignment.assignment.project.data.dao.ChildInstallment
import com.assignment.assignment.project.data.dao.ParentTransactions
import com.assignment.assignment.project.service.InstallmentService
import com.assignment.assignment.project.utils.Routes
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["http://localhost:3001"])
@RequestMapping(Routes.INSTALLMENT)
class Installment(private val installmentService: InstallmentService) {

    @GetMapping(Routes.PARENT, produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getParnetData():List<ParentTransactions>{
       return installmentService.getParentTransactions();
    }

    @GetMapping(Routes.CHILD, produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getChildInstallmentData():List<ChildInstallment>{
        return installmentService.getChildInstallment();
    }

}