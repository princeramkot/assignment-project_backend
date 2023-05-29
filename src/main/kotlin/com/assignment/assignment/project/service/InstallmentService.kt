package com.assignment.assignment.project.service

import com.assignment.assignment.project.data.dao.Child
import com.assignment.assignment.project.data.dao.ChildInstallment
import com.assignment.assignment.project.data.dao.Parent
import com.assignment.assignment.project.data.dao.ParentTransactions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.springframework.stereotype.Service
import java.io.File

@Service
interface  InstallmentService{
    fun getParentData():List<Parent>;

    fun getChildData():List<Child>;

    fun getParentTransactions():List<ParentTransactions>;

    fun getTotalPaid(parentId: Long, childData: List<Child>):Long

    fun getChildInstallment():List<ChildInstallment>

    fun getParentDataById(id: Long):Parent?
}

@Service
class InstallmentServiceImpl:InstallmentService {
    override fun getParentData():List<Parent> {
        val gson = Gson()
        val jsonFile = File("/Users/prince/Downloads/assignment-project/src/main/kotlin/com/assignment/assignment/project/data/Parent.json")
        val jsonContent = jsonFile.readText()

        val dataListType = object : TypeToken<List<Parent>>() {}.type
        return gson.fromJson(jsonContent, dataListType)
    }

    override fun getChildData():List<Child> {
        val gson = Gson()
        val jsonFile = File("/Users/prince/Downloads/assignment-project/src/main/kotlin/com/assignment/assignment/project/data/Child.json")
        val jsonContent = jsonFile.readText()

        val dataListType = object : TypeToken<List<Child>>() {}.type
        return gson.fromJson(jsonContent, dataListType)
    }

    override fun getParentTransactions(): List<ParentTransactions> {
        val parentData = getParentData()  //getting parent json data
        val childData = getChildData() //getting child json data
        val parentTransactionsData: MutableList<ParentTransactions> = mutableListOf()
        for(data in parentData){
            parentTransactionsData.add(ParentTransactions(
                data.id,
                data.sender,
                data.receiver,
                data.totalAmount,
                getTotalPaid(data.id,childData)))
        }
        return parentTransactionsData.sortedBy { it.id } //we will return this list of object, sorted by id
    }

    override fun getTotalPaid(parentId: Long, childData: List<Child>): Long { //calculating how much total money we have received for certain parent
        var totalPaid = 0
        for(data in childData){
            if(parentId == data.parentId.toLong())
                totalPaid += data.paidAmount.toInt()
        }
        return totalPaid.toLong()
    }

    override fun getChildInstallment(): List<ChildInstallment> {
        val childData = getChildData() //getting child json data
        val childInstallment: MutableList<ChildInstallment> = mutableListOf()
        for(data in childData){
            val parent =  getParentDataById(data.id)
            parent?.let {
            childInstallment.add(ChildInstallment(
                data.id,
                parent.sender,
                parent.receiver,
                parent.totalAmount,
                data.paidAmount))
            }
        }
        return childInstallment.sortedBy { it.id }
    }

    override fun getParentDataById(id: Long):Parent? {
        val parentData = getParentData()
        for(data in parentData)
            if(id==data.id)
                return data
        return null
    }
}