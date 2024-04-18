package Classes.I18N;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pair <F, S> {

    private F first;

    private S second;

    public Pair(F f, S s){
        first = f;
        second = s;
    }

}
