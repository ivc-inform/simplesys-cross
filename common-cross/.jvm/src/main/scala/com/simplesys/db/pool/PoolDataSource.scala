package com.simplesys.db.pool

import java.sql.Connection

import com.simplesys.sql.SQLDialect

trait PoolDataSource {
    def getConnection(): Connection
    def sqlDialect: SQLDialect

    val settings: PoolConfig
}
