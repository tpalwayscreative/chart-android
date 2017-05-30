package co.tpcreative.portfolios.model;
import com.google.firebase.database.IgnoreExtraProperties;
import java.util.List;

@IgnoreExtraProperties
public class CData {

    public List<CObject> data ;

    public CData(){

    }

    public CData(List<CObject>data){
        this.data = data ;
    }
}
