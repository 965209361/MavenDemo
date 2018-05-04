package com.zqt.services;

import com.zqt.model.HeFeiBBS_Domain;

import java.util.List;

public interface HeFei_BBSServerce {

    void hefei_BBs();

    List<HeFeiBBS_Domain> QueryNews(int page ,int pageCount);

    Integer QueryPageCount();

}
