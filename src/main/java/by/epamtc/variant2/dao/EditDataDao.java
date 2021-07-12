package by.epamtc.variant2.dao;

import by.epamtc.variant2.entity.EditData;

import java.io.IOException;

public interface EditDataDao {

    EditData readEditData() throws IOException, ClassNotFoundException;

    EditData[] readAllEditData(int quantity) throws IOException, ClassNotFoundException;

    boolean writeEditData(EditData editData) throws IOException;

    boolean writeAllEditData(EditData[] editData) throws IOException;

}
