package com.assignment.assignment.project.data.dao

data class ChildInstallment(
    val id: Long,
    val sender: String,
    val receiver: String,
    val totalAmount: Long,
    val totalPaid: Long)