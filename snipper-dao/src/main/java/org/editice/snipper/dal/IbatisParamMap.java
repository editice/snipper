package org.editice.snipper.dal;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tinglang (editice@gmail.com)
 * @since 2014/11/8  16:54
 */
public class IbatisParamMap extends HashMap<String,Object> {

    private static final long serialVersionUID = 1L;

    private static final String pageIndex="pageIndex";

    private static final String pageSize="pageSize";

    public IbatisParamMap() {}

    public IbatisParamMap(String key, Object value, int begin, int size) {
        this.put(key, value);
        setMySQLLimit(begin, size);
    }

    public IbatisParamMap(String key, Object value){
        this.put(key, value);
    }

    public IbatisParamMap(Map<String, Object> map, int begin, int size) {
        this(map);
        setMySQLLimit(begin, size);
    }

    public void setQueryMap(Map<String,Object> map){
        if(map==null){
            return;
        }
        this.putAll(map);
    }

    public void setMap(Map<String,String> map){
        if(map==null){
            return;
        }
        this.putAll(map);
    }

    public IbatisParamMap(Map<String, Object> map) {
        for(String key : map.keySet()){
            this.put(key, map.get(key));
        }
    }

    public void setNoLimit(){
        remove(pageIndex);
        remove(pageSize);
    }

    public void setMySQLLimit(int begin, int size){
        if(begin<0 | size<=0){
            setNoLimit();
        }
        else{
            begin=begin*size;
            put(pageIndex, begin);
            put(pageSize, size);
        }
    }

}
