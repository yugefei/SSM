package com.tencent.seventeenShow.backend.dao;

import com.tencent.seventeenShow.backend.controller.form.NoteForm;
import com.tencent.seventeenShow.backend.controller.form.RecordForm;
import com.tencent.seventeenShow.backend.model.Note;
import com.tencent.seventeenShow.backend.model.NoteCounter;
import com.tencent.seventeenShow.backend.model.Record;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Edward on 2017/2/15 015.
 */
public interface RecordMapper {
    int insertRecord(@Param("record")RecordForm record);
    int insertNote(@Param("note")NoteForm note);
//
//    List<Record> speciesRecord(@Param("speciesId")Long speciesId,@Param("count")Long count);
//    List<Record> speciesRecordWithPageControl(@Param("speciesId")Long speciesId,@Param("startId")Long startId,@Param("count")Long count);

    List<Record> userRecord(@Param("userId")Long userId, @Param("count")Long count);
    List<Record> userRecordWithPageControl(@Param("userId")Long userId,@Param("startId")Long startId,@Param("count")Long count);

    List<Note> notesWithRecordId(@Param("recordId")Long noteId);

    Record recordById(@Param("recordId")Long recordId);

    int deleteRecord(@Param("recordId")Long recordId, @Param("userId")Long userId);

    List<NoteCounter> noteCounts(@Param("recordId")Long recordId);

    void removeNotes(@Param("recordId")Long recordId);

    int editRecord(@Param("record")RecordForm record);
}
