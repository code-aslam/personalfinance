package com.hotdogcode.spendwise.common

enum class CategoryType(var typeName : String, val title : String) {
    INCOME(typeName = "Income Category", "Income"),
    EXPENSE(typeName = "Expense Category", "Expense")
}