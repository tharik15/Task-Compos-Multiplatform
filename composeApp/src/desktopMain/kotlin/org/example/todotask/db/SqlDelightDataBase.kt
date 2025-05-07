package org.example.todotask.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.sql.DriverManager

fun createDataBaseDriver(): SqlDriver {
    val dbFilePath = "jdbc:sqlite:./TodoDatabase.db"
    if (!isTableExists(dbFilePath, "todo_table")) {
        val driver = JdbcSqliteDriver(dbFilePath)
        TodoDatabase.Schema.create(driver)  // Create the schema if it doesn't exist
        return driver
    } else {
        // Open the existing database
        val driver = JdbcSqliteDriver(dbFilePath)
        return driver
    }
}

fun isTableExists(dbPath: String, tableName: String): Boolean {
    val connection = DriverManager.getConnection(dbPath)
    val resultSet = connection.createStatement().executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='$tableName'")
    val exists = resultSet.next()  // Returns true if the table exists
    resultSet.close()
    connection.close()
    return exists
}
