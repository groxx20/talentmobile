package com.talentomobile.greendao_db;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MainGenerator {

    public static void main(String[] args) {

        createConfigSchema();
    }

    private static void createConfigSchema() {

        int schemaVersion = 5;
        String dataPackage = "com.talentomobile.assignment.dbdata.db";

        Schema configSchema = new Schema(schemaVersion, dataPackage);
        configSchema.setDefaultJavaPackageDao(dataPackage + ".dao");
        configSchema.enableKeepSectionsByDefault();

        addTables(configSchema);

        try {

            DaoGenerator daoGenerator = new DaoGenerator();
            daoGenerator.generateAll(configSchema, "../app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTables(Schema schema) {

        Entity place = schema.addEntity("PlaceDB");
        place.addIdProperty().primaryKey().autoincrement();
        place.addStringProperty("name");
        place.addDoubleProperty("east");
        place.addDoubleProperty("south");
        place.addDoubleProperty("north");
        place.addDoubleProperty("west");
        place.addDoubleProperty("lat");
        place.addDoubleProperty("lng");
        place.addStringProperty("countryCode");
        place.addIntProperty("population");


    }
}
