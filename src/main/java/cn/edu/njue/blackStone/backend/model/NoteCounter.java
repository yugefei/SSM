package cn.edu.njue.blackStone.backend.model;

/**
 * Created by EdCho
 * 2017/5/31 031.
 */
public class NoteCounter {
    private String speciesType;
    private Long count;

    public String getSpeciesType() {
        return speciesType;
    }

    public void setSpeciesType(String speciesType) {
        this.speciesType = speciesType;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
