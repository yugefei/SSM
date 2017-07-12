package com.tencent.seventeenShow.backend.service.impl;

import com.tencent.seventeenShow.backend.controller.form.NoteForm;
import com.tencent.seventeenShow.backend.controller.form.RecordForm;
import com.tencent.seventeenShow.backend.dao.ImageMapper;
import com.tencent.seventeenShow.backend.dao.RecordMapper;
import com.tencent.seventeenShow.backend.model.Record;
import com.tencent.seventeenShow.backend.service.RecordService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Edward on 2017/2/15 015.
 */
@Service("RecordService")
public class RecordServiceImpl implements RecordService {
    Logger logger = Logger.getLogger(RecordServiceImpl.class);
    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private ImageMapper imageMapper;

//    @Override
//    public List<Record> speciesRecordList(Long speciesId, Long count, Long startId) {
//        if(startId == 0)
//            return recordMapper.speciesRecord(speciesId,count);
//        else
//            return recordMapper.speciesRecordWithPageControl(speciesId,startId,count);
//    }

    @Override
    public boolean addRecord(RecordForm record) {
        if(record.getNotes().size() == 0)
            return false;

        if(recordMapper.insertRecord(record) != 1)
            return false;

        for(NoteForm note : record.getNotes()){
            note.setRecordId(record.getId());
            recordMapper.insertNote(note);
        }
        return true;
    }

    @Override
    public List<Record> userRecordList(Long userId, Long count, Long startId) {
        if(startId == 0)
            return recordMapper.userRecord(userId,count);
        else
            return recordMapper.userRecordWithPageControl(userId,startId,count);
    }

    @Override
    public Record recordById(Long recordId) {
        return recordMapper.recordById(recordId);
    }

    @Override
    public boolean deleteRecord(Long recordId, Long userId) {
        return recordMapper.deleteRecord(recordId,userId) == 1;
    }

    @Transactional
    @Override
    public boolean editRecord(RecordForm record) {
        recordMapper.removeNotes(record.getId());
        recordMapper.editRecord(record);
        for(NoteForm note : record.getNotes()){
            note.setRecordId(record.getId());
            recordMapper.insertNote(note);
        }

        recordMapper.editRecord(record);

        return true;
    }
}
