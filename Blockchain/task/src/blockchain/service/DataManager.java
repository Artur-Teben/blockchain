package blockchain.service;

import blockchain.model.data.Data;

import java.util.List;

public interface DataManager {

    List<Data> getData();

    void generateData();
}
