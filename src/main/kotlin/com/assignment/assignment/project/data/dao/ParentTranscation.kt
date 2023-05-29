package com.assignment.assignment.project.data.dao

data class ParentTransactions(
    val id: Long,
    val sender: String,
    val receiver: String,
    val totalAmount: Long,
    val totalAmountToBePaid: Long
)