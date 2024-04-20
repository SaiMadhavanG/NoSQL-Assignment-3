import java.io.IOException;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;
import org.apache.pig.Algebraic;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.DataBag;
import java.util.Iterator;

public class SPCOUNT extends EvalFunc<Long> implements Algebraic{
    public Long exec(Tuple input) throws IOException {return count(input);}
    public String getInitial() {return Initial.class.getName();}
    public String getIntermed() {return Intermed.class.getName();}
    public String getFinal() {return Final.class.getName();}
    static public class Initial extends EvalFunc<Tuple> {
        public Tuple exec(Tuple input) throws IOException {return TupleFactory.getInstance().newTuple(count(input));}
    }
    static public class Intermed extends EvalFunc<Tuple> {
        public Tuple exec(Tuple input) throws IOException {return TupleFactory.getInstance().newTuple(sum(input));}
    }
    static public class Final extends EvalFunc<Long> {
        public Long exec(Tuple input) throws IOException {return sum(input);}
    }
    static protected Long count(Tuple input) throws ExecException {
        Object values = input.get(0);
        String reqSubject = (String)input.get(1);
        String reqPredicate = (String)input.get(2);
        Long result = 0l;
        DataBag bag = (DataBag)values;
        for (Iterator<Tuple> it = bag.iterator(); it.hasNext();) {
            Tuple t = it.next();
            String subject = (String)t.get(0);
            String predicate = (String)t.get(1);
            if (subject.equals(reqSubject) && predicate.equals(reqPredicate)) {
            	result++;
            }
        }
        return result;
    }
    static protected Long sum(Tuple input) throws ExecException, NumberFormatException {
        DataBag values = (DataBag)input.get(0);
        long sum = 0;
        for (Iterator<Tuple> it = values.iterator(); it.hasNext();) {
            Tuple t = it.next();
            sum += (Long)t.get(0);
        }
        return sum;
    }
}
