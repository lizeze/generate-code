package org.lzz.generate.app.controller;

import org.lzz.generate.app.datasource.DataSourceWarpper;
import org.lzz.generate.app.service.BaseService;
import org.lzz.generate.app.vo.ColumnVo;
import org.lzz.generate.app.vo.TableVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ：lzz
 * @BelongsProject: org.lzz.generate.app.controller
 * @date ：Created in 2020/7/13 15:04
 * @description ：
 * @modified By：
 */
@RestController
@RequestMapping("/api/v1")
public class MainController {

    @Autowired
    private DataSourceWarpper dataSourceWarpper;


    @Autowired
    @Qualifier("mysql")
    BaseService mySqlService;


    @PostMapping("/columns/{sourceId}")
    public ResponseEntity getCploumns(@PathVariable String sourceId, @RequestBody Map<String, String> map) throws SQLException, ClassNotFoundException {

        List<ColumnVo> list = new ArrayList<>();
        Connection connection = dataSourceWarpper.getMap().get(sourceId);
        if (connection != null) {
            if (connection.getMetaData().getDatabaseProductName().toLowerCase().equals("mysql")) {

                list = mySqlService.getColumn(sourceId, map.get("tableName"));
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/database/{sourceId}")
    public ResponseEntity getDataBase(@PathVariable String sourceId) throws SQLException, ClassNotFoundException {

        List<String> dataBase = mySqlService.getDataBase(sourceId);

        return ResponseEntity.status(HttpStatus.OK).body(dataBase);

    }



    @GetMapping("/table/{sourceId}")
    public ResponseEntity getTables(@PathVariable String sourceId) throws SQLException, ClassNotFoundException {

        List<TableVo> tables = mySqlService.getTables(sourceId);

        return ResponseEntity.status(HttpStatus.OK).body(tables);

    }

}