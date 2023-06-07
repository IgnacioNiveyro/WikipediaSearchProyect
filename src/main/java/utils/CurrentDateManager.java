package utils;

import java.util.Date;

public class CurrentDateManager implements DateManager {
    @Override
    public Date getDate(){
        return new Date();
    }

}